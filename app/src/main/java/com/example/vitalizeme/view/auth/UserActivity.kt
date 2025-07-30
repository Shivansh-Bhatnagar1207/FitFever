package com.example.vitalizeme.view.auth

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vitalizeme.R
import com.example.vitalizeme.databinding.ActivityUserBinding
import com.example.vitalizeme.view.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()
        sp = this.getSharedPreferences("User", MODE_PRIVATE)

        val currentUser = FirebaseAuth.getInstance().currentUser?.uid

        binding.loginbtn.setOnClickListener {
            val name = binding.etname.text.toString()
            val phone = binding.etphone.text.toString()
            val DOB = binding.etDOB.text.toString()
            val height = binding.etHeight.text.toString()
            val weight = binding.etWeight.text.toString()
            val checked = binding.radiogroup.checkedRadioButtonId
            val selected = binding.root.findViewById<RadioButton>(checked)
            val gender = selected.text.toString()
            val userMap = hashMapOf(
                "name" to name,
                "phone" to phone,
                "DOB" to DOB,
                "height" to height,
                "weight" to weight,
                "gender" to gender,
                "createdAt" to FieldValue.serverTimestamp()
            )
            firestore.collection("User")
                .document(currentUser!!)
                .set(userMap)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        sp.edit().apply {
                            putString("name", name)
                            putString("height", height)
                            putString("weight", weight)
                            putString("UserID", currentUser)
                            putBoolean("isComplete", true)
                            apply()
                        }
                        Toast.makeText(this, "Welcome ${name}", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
}