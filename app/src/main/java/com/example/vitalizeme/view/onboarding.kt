package com.example.vitalizeme.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.vitalizeme.databinding.ActivityOnboardingBinding
import com.example.vitalizeme.view.auth.AuthActivity
import com.example.vitalizeme.view.main.MainActivity
import com.google.firebase.auth.FirebaseAuth

class onboarding : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var intent: Intent
    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = FirebaseAuth.getInstance().currentUser


        binding.button.setOnClickListener {
            if (user != null) {
                sp = getSharedPreferences("User", MODE_PRIVATE)
                val isComplete = sp.getBoolean("isComplete", false)
                if (isComplete) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    startActivity(
                        Intent(this, AuthActivity::class.java).putExtra(
                            "start",
                            "userInfo"
                        )
                    )
                }
            } else {
                startActivity(Intent(this, AuthActivity::class.java).putExtra("start", "login"))
            }
            finish()
        }

    }
}