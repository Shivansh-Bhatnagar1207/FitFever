package com.example.vitalizeme.view.auth

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.vitalizeme.databinding.FragmentUserInfoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class UserInfo : Fragment() {

    private lateinit var binding: FragmentUserInfoBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var sp: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserInfoBinding.inflate(inflater)

        sp = requireContext().getSharedPreferences("User", MODE_PRIVATE)

        val currentUser = FirebaseAuth.getInstance().currentUser?.uid

//        binding.loginbtn.setOnClickListener {
//            val name = binding.etname.text.toString()
//            val phone = binding.etphone.text.toString()
//            val DOB = binding.etDOB.text.toString()
//            val height = binding.etHeight.text.toString()
//            val weight = binding.etWeight.text.toString()
//            val checked = binding.radiogroup.checkedRadioButtonId
//            val selected = binding.root.findViewById<RadioButton>(checked)
//            val gender = selected.text.toString()
//            val createdAt = FieldValue.serverTimestamp().toString()
//
//            if (name.isEmpty()) {
//                binding.nameLayout.error = "Please Enter Name"
//            } else if (height.isEmpty() || weight.isEmpty()) {
//                binding.HeightLayout.error = "Please Enter Height"
//                binding.WeightLayout.error = "Please Enter Weight"
//            } else {
//                val user: Users = Users(name, phone, DOB, height, weight, gender,createdAt)
//                firestore.collection("Users_data").add(user)
//                    .addOnCompleteListener { task ->
//                        if (task.isSuccessful)
//                            sp.edit().apply {
//                                putString("name", name)
//                                putString("phone", phone)
//                                putString("DOB", DOB)
//                                putString("height", height)
//                                putString("weight", weight)
//                                putString("gender", gender)
//                                putString("createdAt",createdAt)
//                                apply()
//                            }
//                        else {
//                            Toast.makeText(context, "Data cannot be uploaded", Toast.LENGTH_SHORT)
//                                .show()
//                        }
//                    }
//            }
//
// }

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
                            putString("phone", phone)
                            putString("DOB", DOB)
                            putString("height", height)
                            putString("weight", weight)
                            putString("gender", gender)
                            apply()
                        }
                    }else{
                        Toast.makeText(context, "Failed to save data", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        return binding.root
    }

}