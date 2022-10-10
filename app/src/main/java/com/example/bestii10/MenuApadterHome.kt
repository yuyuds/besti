package com.example.bestii10

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bestii10.DatabaseHelper
import com.example.bestii10.EditMenuActivity
import com.example.bestii10.R
import com.example.bestii10.MenuModel



class MenuAdapterHome(private val list:ArrayList<MenuModel>):
    RecyclerView.Adapter<MenuAdapterHome.MenuViewHolder>(){


    inner class MenuViewHolder(v: View):RecyclerView.ViewHolder(v) {
        val textName : TextView
        val textHarga : TextView
        val imageVoucher : ImageView
        val btnBeli: ImageView


        init{
            textName = v.findViewById(R.id.textNamaMenu)
            textHarga = v.findViewById(R.id.textHargaMenu)
            imageVoucher = v.findViewById(R.id.imageMenu)
            btnBeli = v.findViewById(R.id.buttonTambah)


        }


        fun bind(data : MenuModel){
            val id:String = data.idMenu
            val nama:String = data.namaMenu
            val harga:Int = data.hargaMenu
            val gambar: Bitmap = data.imageMenu

            textName.text = nama
            textHarga.text = harga.toString()
            imageVoucher.setImageBitmap(gambar)

            btnBeli.setOnClickListener {
//                    Toast.makeText(btnEditVoucher.getContext(), "id" + data.id, Toast.LENGTH_SHORT).show()
                val intent = Intent(btnBeli.context, FragmentKeranjang::class.java)
                btnBeli.context.startActivity(intent)
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapterHome.MenuViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.cardview_menu,
            parent, false)

        return MenuViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(list[position])

    }



}