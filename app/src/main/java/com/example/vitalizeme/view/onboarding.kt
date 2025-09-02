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
import com.example.vitalizeme.constants.USERDATA
import com.example.vitalizeme.databinding.ActivityOnboardingBinding
import com.example.vitalizeme.view.auth.AuthActivity
import com.example.vitalizeme.view.auth.UserActivity
import com.example.vitalizeme.view.main.MainActivity
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class onboarding : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var sp: SharedPreferences
    private lateinit var userId: String

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)




        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACTIVITY_RECOGNITION),
            200
        )

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            userId = user.uid
            val firebaseData = FirebaseFirestore
                .getInstance()
                .collection("User")
                .document(userId)

            firebaseData
                .get().addOnSuccessListener { doc ->
                    if (doc != null && doc.exists()) {

                        doc.data?.let {
                            val name = it[USERDATA.NAME] as String
                            val phone = it[USERDATA.PHONE] as Long
                            val height = it[USERDATA.HEIGHT] as String
                            val weight = it[USERDATA.WEIGHT] as String
                            val gender = it[USERDATA.GENDER] as String
                            val DOB = it[USERDATA.DOB] as Timestamp
                            sp.edit().apply {
                                putString(USERDATA.NAME, name)
                                putLong(USERDATA.PHONE, phone)
                                putString(USERDATA.HEIGHT, height)
                                putString(USERDATA.WEIGHT, weight)
                                putString(USERDATA.DOB, DOB.toString())
                                putString(USERDATA.GENDER, gender)
                                putBoolean(USERDATA.ISCOMPLETE, true)
                            }
                        }

                    }
                }
        }
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.FOREGROUND_SERVICE,
                Manifest.permission.FOREGROUND_SERVICE_CONNECTED_DEVICE,
                Manifest.permission.FOREGROUND_SERVICE_LOCATION,
            ), 0
        )

        sp = getSharedPreferences(PrefConstants.USER, MODE_PRIVATE)
        val isComplete = sp.getBoolean(USERDATA.ISCOMPLETE, false)

        binding.button.setOnClickListener {
            if (user != null) {
                if (isComplete) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    startActivity(
                        Intent(this, UserActivity::class.java)
                    )
                    finish()
                }
            } else {
                startActivity(Intent(this, AuthActivity::class.java))
            }
            finish()
        }

    }
}