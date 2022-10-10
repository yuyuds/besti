package com.example.bestii10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    // for nav bottom
    private val homeFragment = FragmentHome()
    private val discoveryFragment = FragmentDiscovery()
    private val keranjangFragment = FragmentKeranjang()
    private val profileFragment = FragmentProfile()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        //hide title bar
        getSupportActionBar()?.hide()

        // for nav bottom
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        replaceFragment(homeFragment)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.fragmentHome -> replaceFragment(homeFragment)
                R.id.fragmentDiscovery -> replaceFragment(discoveryFragment)
                R.id.fragmentKeranjang -> replaceFragment(keranjangFragment)
                R.id.FragmentProfile -> replaceFragment(profileFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        if (fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}