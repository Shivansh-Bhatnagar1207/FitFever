package com.example.vitalizeme.view

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.vitalizeme.constants.PrefConstants
import com.example.vitalizeme.databinding.ActivityOnboardingBinding
import com.example.vitalizeme.view.auth.AuthActivity
import com.example.vitalizeme.view.auth.UserActivity
import com.example.vitalizeme.view.main.MainActivity
import com.google.firebase.auth.FirebaseAuth

class onboarding : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var sp: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = FirebaseAuth.getInstance().currentUser

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.FOREGROUND_SERVICE,
                Manifest.permission.FOREGROUND_SERVICE_CONNECTED_DEVICE,
                Manifest.permission.FOREGROUND_SERVICE_LOCATION,
            ),0
        )

        binding.button.setOnClickListener {
            if (user != null) {
                sp = getSharedPreferences(PrefConstants.USER, MODE_PRIVATE)
                val isComplete = sp.getBoolean("isComplete", false)
                if (isComplete) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    startActivity(
                        Intent(this, UserActivity::class.java)
                    )
                }
            } else {
                startActivity(Intent(this, AuthActivity::class.java))
            }
            finish()
        }

    }
}