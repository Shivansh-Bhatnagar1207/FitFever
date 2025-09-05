package com.example.vitalizeme.view.auth

import android.app.DatePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import com.example.vitalizeme.constants.PrefConstants
import com.example.vitalizeme.constants.USERDATA
import com.example.vitalizeme.databinding.ActivityUserBinding
import com.example.vitalizeme.view.main.MainActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()
        sp = this.getSharedPreferences(PrefConstants.USER, MODE_PRIVATE)


        forminflater()

        val currentUser = FirebaseAuth.getInstance().currentUser?.uid


        binding.etDOB.setOnClickListener {
            showDatePicker(binding.etDOB)
        }

        binding.loginbtn.setOnClickListener {
            val name = binding.etname.text.toString()
            val phone = binding.etphone.text.toString().toLong()
            val height = binding.etHeight.text.toString()
            val weight = binding.etWeight.text.toString()
            val checked = binding.radiogroup.checkedRadioButtonId
            val selected = binding.root.findViewById<RadioButton>(checked)
            val gender = selected.text.toString()
            val DOB = binding.etDOB.text.toString()
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val dobDate = dateFormat.parse(DOB)


            val userMap = hashMapOf(
                "name" to name,
                "phone" to phone,
                "DOB" to dobDate,
                "height" to height,
                "weight" to weight,
                "gender" to gender,
            )
            firestore.collection("User").document(currentUser!!).set(userMap)
                .addOnSuccessListener {
                    sp.edit().apply {
                        putString(USERDATA.NAME, name)
                        putString(USERDATA.HEIGHT, height)
                        putString(USERDATA.WEIGHT, weight)
                        putLong(USERDATA.PHONE, phone)
                        putString(USERDATA.DOB, DOB)
                        putString(USERDATA.GENDER, gender)
                        putString(USERDATA.USERID, currentUser)
                        putBoolean(USERDATA.ISCOMPLETE, true)
                        apply()
                    }
                    Toast.makeText(this, "Welcome $name", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    e.printStackTrace() // Logcat will show the full error
                }

        }

    }

    private fun forminflater() {
        binding.apply {
            etname.setText(sp.getString(USERDATA.NAME, ""))
            etHeight.setText(sp.getString(USERDATA.HEIGHT, ""))
            etWeight.setText(sp.getString(USERDATA.WEIGHT, ""))
            etphone.setText(sp.getLong(USERDATA.PHONE, 0).takeIf { it != 0L }?.toString() ?: "")
            etDOB.setText(sp.getString(USERDATA.DOB, ""))
        }
    }


    private fun showDatePicker(etDob: TextInputEditText) {
        val calandar = Calendar.getInstance()
        val year = calandar.get(Calendar.YEAR)
        val month = calandar.get(Calendar.MONTH)
        val day = calandar.get(Calendar.DAY_OF_MONTH)


        val datepicker = DatePickerDialog(
            this, { _, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                etDob.setText(date)
            },
            year,
            month,
            day
        )

        datepicker.show()
    }

}