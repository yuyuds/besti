package com.example.bestii10

import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
//import com.example.bestii10.FormEditActivity


class MenuAdapter(private val list:ArrayList<MenuModel>):
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>(){


    inner class MenuViewHolder(v: View):RecyclerView.ViewHolder(v) {
        val textName : TextView
        val textHarga : TextView
        val imageMenu : ImageView
        val btnEditMenu : Button
        val btnDeleteMenu : Button
        val textIdMenu: TextView


        init{
            textName = v.findViewById(R.id.textNamaMenu)
            textHarga = v.findViewById(R.id.textHargaMenu)
            textIdMenu = v.findViewById(R.id.textIdMenu)
            imageMenu = v.findViewById(R.id.imageMenu)
            btnEditMenu = v.findViewById(R.id.buttonEditMenu)
            btnDeleteMenu = v.findViewById(R.id.buttonHapusMenu)


        }


        fun bind(data : MenuModel){
            val id: String = data.idMenu
            val nama:String = data.namaMenu
            val harga:Int = data.hargaMenu
            val gambar: Bitmap = data.imageMenu

            textName.text = nama
            textIdMenu.text= id.toString()
            textHarga.text = harga.toString()
            imageMenu.setImageBitmap(gambar)

            btnEditMenu.setOnClickListener {
//                    Toast.makeText(btnEditVoucher.getContext(), "id" + data.id, Toast.LENGTH_SHORT).show()
                val intent = Intent(btnEditMenu.context, EditMenuActivity::class.java)
                intent.putExtra("id", id)
                btnEditMenu.context.startActivity(intent)
            }

            btnDeleteMenu.setOnClickListener {
                val databaseHelper = DatabaseHelper(it.context)
                databaseHelper.deleteMenu(id)

                val fragment: Fragment = FragmentDiscovery()

                val activity: AppCompatActivity = it.context as AppCompatActivity

                activity.supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.MenuViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.cardview_menu_discovery,
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