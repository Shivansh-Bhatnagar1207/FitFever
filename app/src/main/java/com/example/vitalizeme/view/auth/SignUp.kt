package com.example.vitalizeme.view.auth

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.vitalizeme.R
import com.example.vitalizeme.databinding.FragmentSignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class SignUp : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sp : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignUpBinding.inflate(inflater)

        sp =  requireContext().getSharedPreferences("User",MODE_PRIVATE)


        binding.loginLink.setOnClickListener {
            findNavController().navigate(R.id.action_signUp_to_login)
        }

        binding.SignInbtn.setOnClickListener {
            val email = binding.etemail.text.toString()
            val password = binding.etpassword.text.toString()
            val confirmPassword = binding.etcnfpassword.text.toString()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                binding.emailLayout.error = "Please enter Email"
                binding.passwordLayout.error = "Please enter Password"
                binding.cnfpasswordLayout.error = "Please enter Confirm password"
            } else if (password != confirmPassword) {
                binding.passwordLayout.error = "Password Mismatch"
                binding.cnfpasswordLayout.error = "Password"
            } else {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            findNavController().navigate(R.id.action_signUp_to_userInfo)
                            sp.edit().putBoolean("isComplete", false)
                            requireActivity()
                        } else {
                            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }
        }


        return binding.root
    }
}