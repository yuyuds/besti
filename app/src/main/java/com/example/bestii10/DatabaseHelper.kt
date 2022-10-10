package com.example.bestii10

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class DatabaseHelper(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object{
        private val DATABASE_NAME = "besti"
        private val DATABASE_VERSION = 1
        // table user
        private val TABLE_USERS = "user"
        // column table user
        private val COLUMN_EMAIL = "email"
        private val COLUMN_NAME = "name"
        private val COLUMN_PASSWORD = "password"
        // column table menu
        private val TABLE_MENU = "MENU"
        private val COLUMN_NAMA_MENU = "namaMenu"
        private val COLUMN_ID_MENU = "idMenu"
        private val COLUMN_HARGA_MENU = "hargaMenu"
        private val COLUMN_IMAGE_MENU = "imageMenu"
    }

    // create table user sql query
    private val CREATE_USERS_TABLE = ("CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_EMAIL + " TEXT PRIMARY KEY, " + COLUMN_NAME + " TEXT, "
            + COLUMN_PASSWORD + " TEXT)")
    //create table menu
    private val CREATE_MENU_TABLE = ("CREATE TABLE " + TABLE_MENU + "("
            + COLUMN_ID_MENU  + " TEXT PRIMARY KEY, " + COLUMN_NAMA_MENU + " TEXT, "
            + COLUMN_HARGA_MENU + " INT, " + COLUMN_IMAGE_MENU + " BLOB)")

    // drop table user sql query
    private val DROP_USERS_TABLE = "DROP TABLE IF EXISTS $TABLE_USERS"
    private val DROP_MENU_TABLE = "DROP TABLE IF EXISTS $TABLE_MENU"

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(CREATE_USERS_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(DROP_USERS_TABLE)
        p0?.execSQL(DROP_MENU_TABLE)
        onCreate(p0)
    }


    // login check
    fun loginCheck(email: String, password: String): Boolean{
        val columns = arrayOf(COLUMN_NAME)
        val db = this.readableDatabase

        // selection criteria
        val selection = "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
        // selection arguments
        val selectionArgs = arrayOf(email,password)

        val cursor = db.query(TABLE_USERS, // table to query
            columns, // columns to return
            selection, // columns for WHERE clause
            selectionArgs, // values for the WHERE clause
            null,
            null,
            null
        )

        val cursorCount = cursor.count
        cursor.close()
        db.close()
        return cursorCount > 0
    }

    // add user
    fun addUsers(email: String, name: String, password: String){
        val db = this.readableDatabase

        val values = ContentValues()
        values.put(COLUMN_EMAIL, email)
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_PASSWORD, password)

        db.insert(TABLE_USERS, null, values)
        db.close()
    }

    // check data
    fun checkData(email: String): String{
        val columns = arrayOf(COLUMN_NAME)
        val db = this.readableDatabase
        val selection = "$COLUMN_EMAIL = ?"
        val selectionArgs = arrayOf(email)
        var name: String = ""

        val cursor = db.query(TABLE_USERS, // table to query
            columns, // columns to return
            selection, // columns for WHERE clause
            selectionArgs, // the values for the WHERE clause
            null,
            null,
            null
        )

        if (cursor.moveToFirst()){
            name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
        }
        cursor.close()
        db.close()
        return name
    }
    //FUNCTION INSERT DATA MENU
    fun addMenu(menu : MenuModel){
        val db = this.writableDatabase
        val values = ContentValues()
        //input data
        values.put(COLUMN_ID_MENU, menu.idMenu)
        values.put(COLUMN_NAMA_MENU, menu.namaMenu)
        values.put(COLUMN_HARGA_MENU, menu.hargaMenu )
        //gambar
        val bous = ByteArrayOutputStream()
        val fotoByteArray : ByteArray
        menu.imageMenu.compress(Bitmap.CompressFormat.JPEG, 100,bous)
        fotoByteArray = bous.toByteArray()
        //input data gambar
        values.put(COLUMN_IMAGE_MENU, fotoByteArray)
        //insert data
        db.insert(TABLE_MENU, null, values)
    }
    fun getMenu(voucherId: String): ArrayList<MenuModel>{
        val db = this.readableDatabase
        val listModel = arrayListOf<MenuModel>()
        val selectQuery = "SELECT  * FROM $TABLE_MENU WHERE $COLUMN_ID_MENU = ?"

        var id:String
        var name:String
        var price:Int
        var imageArray:ByteArray
        var imageBmp:Bitmap

        db.rawQuery(selectQuery, arrayOf(voucherId)).use{
            if (it.moveToFirst()){
                id = it.getString(it.getColumnIndexOrThrow(COLUMN_ID_MENU))
                name = it.getString(it.getColumnIndexOrThrow(COLUMN_NAMA_MENU))
                price= it.getInt(it.getColumnIndexOrThrow(COLUMN_HARGA_MENU))
                imageArray= it.getBlob(it.getColumnIndexOrThrow(COLUMN_IMAGE_MENU))
                val byteInputStream = ByteArrayInputStream(imageArray)
                imageBmp= BitmapFactory.decodeStream(byteInputStream)
                val model = MenuModel( idMenu = id, hargaMenu = price, imageMenu = imageBmp, namaMenu = name)
                listModel.add(model)
            }

        }
        return listModel

    }

    fun showAllMenu():ArrayList<MenuModel>{
        val listModel = ArrayList<MenuModel>()
        val db = this.readableDatabase
        var cursor: Cursor?=null
        try{
            cursor = db.rawQuery("SELECT * FROM $TABLE_MENU", null)
        } catch (se: SQLException){
            db.execSQL(CREATE_MENU_TABLE)
            return ArrayList()
        }

        var menuid:String
        var name:String
        var price:Int
        var imageArray:ByteArray
        var imageBmp:Bitmap

        if(cursor.moveToFirst()){
            do {
                menuid = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID_MENU))
                name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA_MENU))
                price = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HARGA_MENU))
                imageArray = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_MENU))

                val byteInputStream = ByteArrayInputStream(imageArray)
                imageBmp = BitmapFactory.decodeStream(byteInputStream)
                val model = MenuModel(
                    idMenu = menuid, namaMenu = name, hargaMenu = price,
                    imageMenu = imageBmp)
                listModel.add(model)
            } while ( cursor.moveToNext())
        }
        return listModel
    }
    fun updateMenu(menu: MenuModel , MenuId: String): Int {
        val db = this.readableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAMA_MENU, menu.namaMenu)
        values.put(COLUMN_HARGA_MENU, menu.hargaMenu)
        values.put(COLUMN_ID_MENU, menu.idMenu)

        val byteOutputStream = ByteArrayOutputStream()
        val imageInByte:ByteArray
        menu.imageMenu.compress(Bitmap.CompressFormat.JPEG, 100, byteOutputStream)
        imageInByte = byteOutputStream.toByteArray()
        values.put(COLUMN_IMAGE_MENU, imageInByte)

        val result = db.update(TABLE_MENU, values, "$COLUMN_ID_MENU= $MenuId",null)

        return result
        db.close()

    }
    fun deleteMenu(MenuId: String): Int {
        val db = this.readableDatabase

        val result = db.delete(TABLE_MENU,"$COLUMN_ID_MENU= $MenuId",null)

        return result
        db.close()
    }

}