package com.example.vitalizeme.view.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.vitalizeme.R
import com.example.vitalizeme.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id
            .authFragmentController) as NavHostFragment

        val navController = navHostFragment.navController


        setContentView(binding.root)


    }
}