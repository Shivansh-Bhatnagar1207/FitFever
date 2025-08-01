package com.example.vitalizeme.view.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.vitalizeme.R
import com.example.vitalizeme.constants.PrefConstants
import com.example.vitalizeme.databinding.ActivityMainBinding
import com.example.vitalizeme.view.auth.AuthActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sp = this.getSharedPreferences(PrefConstants.USER,MODE_PRIVATE)
        val isCompeleted = sp.getBoolean("isComplete",false)

        if(isCompeleted == false){
            startActivity(Intent(this, AuthActivity::class.java).putExtra("start","user"))
        }


        val navHostFragment = supportFragmentManager.findFragmentById(R.id
            .MainFragmentController) as NavHostFragment

        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
    }
}