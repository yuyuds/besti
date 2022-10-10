package com.example.bestii10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //hide title bar
        getSupportActionBar()?.hide()

        // instance edit text & button
        val etEmailRegister = findViewById<EditText>(R.id.editTextTextPersonName4)
        val etNamaLengkapRegister = findViewById<EditText>(R.id.editTextTextPersonName)
        val etPassRegister = findViewById<EditText>(R.id.editTextTextPersonName5)
        val btnSignUp: Button = findViewById(R.id.button_SignUp_signup)

        // even button register
        btnSignUp.setOnClickListener{
            // object class database helper
            val databaseHelper = DatabaseHelper(this)

            val email = etEmailRegister.text.toString().trim()
            val name = etNamaLengkapRegister.text.toString().trim()
            val password = etPassRegister.text.toString().trim()

            databaseHelper.addUsers(email,name,password)

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}