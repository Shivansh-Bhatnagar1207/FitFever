package com.example.vitalizeme.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vitalizeme.R
import com.example.vitalizeme.databinding.ActivityOnboardingBinding
import com.example.vitalizeme.view.auth.AuthActivity
import com.example.vitalizeme.view.main.MainActivity

class onboarding : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var intent : Intent
    private lateinit var sp: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sp = this.getSharedPreferences("User", MODE_PRIVATE)

        val name = sp.getString("name", null)
        if(name.isNullOrEmpty()){
            intent = Intent(this, AuthActivity::class.java)
        }else{
            intent = Intent(this, MainActivity::class.java)
        }


        binding.button.setOnClickListener {
            startActivity(intent)
            finish()
        }


    }
}