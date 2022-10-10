package com.example.bestii10

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class EditMenuActivity : AppCompatActivity() {

    //object dr image view
    lateinit var image: ImageView
    companion object{
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_menu)

        //hide title bar
        getSupportActionBar()?.hide()

        //instance button
        image = findViewById(R.id.imageMenu)
        val textId : EditText = findViewById(R.id.MenuID)
        val textName : EditText = findViewById(R.id.MenuName)
        val textPrice : EditText = findViewById(R.id.MenuPrice)
        val btnAddImage : Button = findViewById(R.id.buttonAddImage)
        val btnSaveMenu : Button = findViewById(R.id.buttonSaveMenu)

        val databaseHelper = DatabaseHelper(this)

        val extras = intent.extras
        if (extras != null) {
            val menuId = extras.getString("id")

            if (menuId != null) {
                val result: ArrayList<MenuModel> = databaseHelper.getMenu(menuId.toString())

                textId.setText(result[0].idMenu)
                textName.setText(result[0].namaMenu)
                textPrice.setText(result[0].hargaMenu.toString())
                image.setImageBitmap(result[0].imageMenu)

            }


        }

        btnSaveMenu.setOnClickListener {
            val MenuId = extras?.getString("id")
            val databaseHelper = DatabaseHelper(this)

            val id : String = textId.text.toString().trim()
            val name : String = textName.text.toString().trim()
            val price : Int = textPrice.text.toString().toInt()
            val bitmapDrawable : BitmapDrawable = image.drawable as BitmapDrawable
            val bitmap : Bitmap = bitmapDrawable.bitmap

            val menuModel = MenuModel(id,name,price, bitmap)
            if (MenuId != null) {
                databaseHelper.updateMenu(menuModel, MenuId.toString())
            }
            val intent = Intent( this, HomeActivity::class.java)
            startActivity(intent)

        }


    }
}