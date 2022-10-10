package com.example.bestii10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //hide title bar
        getSupportActionBar()?.hide()
        // instance
        val btnLogin: Button = findViewById(R.id.buttonLogin_main)
        val btnSignUp: Button = findViewById(R.id.buttonSignup_main)
        // event button login_login
        btnLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        btnSignUp.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}