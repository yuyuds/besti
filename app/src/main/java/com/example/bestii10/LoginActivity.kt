package com.example.bestii10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //hide title bar
        getSupportActionBar()?.hide()

        // instance EditText & Button Login & text view
        val emailLayout = findViewById<EditText>(R.id.editTextEmail)
        val passwordLayout = findViewById<EditText>(R.id.editTextPassword)
        val btnLogin = findViewById<Button>(R.id.button_Login_login)
        val tvSignUp = findViewById<TextView>(R.id.tv_signup_login)

        // event button login
        btnLogin.setOnClickListener{
            // object class database helper
            val databaseHelper = DatabaseHelper(this)

            val email = emailLayout.text.toString().trim()
            val password = passwordLayout.text.toString().trim()

            // check login
            val result: Boolean = databaseHelper.loginCheck(email,password)
            if (result == true){
                Toast.makeText(
                    this@LoginActivity, "Login Berhasil",
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(this, HomeActivity::class.java))
            }else{
                Toast.makeText(
                    this@LoginActivity, "Login Gagal, Coba Lagi!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // event text view sign up
        tvSignUp.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
