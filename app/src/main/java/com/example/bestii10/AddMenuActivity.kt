package com.example.bestii10

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

class AddMenuActivity : AppCompatActivity() {
    //object dr image view
    lateinit var image: ImageView
    companion object{
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_menu)

        //hide title bar
        getSupportActionBar()?.hide()

        //instance button
        image = findViewById(R.id.imageMenu)
        val textId : EditText = findViewById(R.id.MenuID)
        val textName : EditText = findViewById(R.id.MenuName)
        val textPrice : EditText = findViewById(R.id.MenuPrice)
        val btnAddImage : Button = findViewById(R.id.buttonAddImage)
        val btnSaveMenu : Button = findViewById(R.id.buttonSaveMenu)

        //event button upload (+)
        btnAddImage.setOnClickListener {
            pickImageGalery()
        }

        btnSaveMenu.setOnClickListener {
            val databaseHelper = DatabaseHelper(this)

            val idMenu : String = textId.text.toString().trim()
            val name : String = textName.text.toString().trim()
            val price : Int = textPrice.text.toString().toInt()
            val bitmapDrawable : BitmapDrawable = image.drawable as BitmapDrawable
            val bitmap : Bitmap = bitmapDrawable.bitmap

            val MenuModel = MenuModel(idMenu, name,price, bitmap)
            databaseHelper.addMenu(MenuModel)
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)

        }


    }

    private fun pickImageGalery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        image = findViewById(R.id.imageMenu)
        val code = IMAGE_REQUEST_CODE.toInt()
        if(requestCode == code && resultCode == Activity.RESULT_OK){
            image.setImageURI(data?.data)
        }
    }



}