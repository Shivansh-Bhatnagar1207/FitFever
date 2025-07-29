package com.example.vitalizeme.view.auth

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import com.example.vitalizeme.R
import com.example.vitalizeme.databinding.FragmentUserInfoBinding
import com.example.vitalizeme.model.Users
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date

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

        binding.loginbtn.setOnClickListener {
            val name = binding.etname.text.toString()
            val phone = binding.etphone.text.toString()
            val DOB = binding.etDOB.text.toString()
            val height = binding.etHeight.text.toString()
            val weight = binding.etWeight.text.toString()
            val checked = binding.radiogroup.checkedRadioButtonId
            val selected = binding.root.findViewById<RadioButton>(checked)
            val gender = selected.text.toString()

            if (name.isEmpty()) {
                binding.nameLayout.error = "Please Enter Name"
            } else if (height.isEmpty() || weight.isEmpty()) {
                binding.HeightLayout.error = "Please Enter Height"
                binding.WeightLayout.error = "Please Enter Weight"
            } else {
                val user: Users = Users(name, phone, DOB, height, weight, gender)
                firestore.collection("Users_data").add(user)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful)
                            sp.edit().apply {
                                putString("name", name)
                                putString("phone", phone)
                                putString("DOB", DOB)
                                putString("height", height)
                                putString("weight", weight)
                                putString("gender", gender)
                                apply()
                            }
                        else {
                            Toast.makeText(context, "Data cannot be uploaded", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }

        }
        return binding.root
    }

}