package com.example.vitalizeme.view.main

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vitalizeme.R
import com.example.vitalizeme.constants.USERDATA
import com.example.vitalizeme.databinding.FragmentProfileBinding
import com.example.vitalizeme.service.StepCounterService
import com.example.vitalizeme.view.auth.UserActivity
import com.example.vitalizeme.view.onboarding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var sp: SharedPreferences
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        sp = requireContext().getSharedPreferences("User", MODE_PRIVATE)

        val name = sp.getString(USERDATA.NAME, "")
        val phone = sp.getLong(USERDATA.PHONE, 0L)
        val height = sp.getString(USERDATA.HEIGHT, "")
        val weight = sp.getString(USERDATA.HEIGHT, "")
        val gender = sp.getString(USERDATA.GENDER, "")
        val DOB = sp.getString(USERDATA.DOB, "")
        firebaseAuth = FirebaseAuth.getInstance()

        binding.name.text = name
        binding.phone.text = phone.toString()
        binding.height.text = "$height m"
        binding.weight.text = "$weight kg"
        binding.gender.text = gender
        binding.dob.text = DOB




        binding.user.setOnClickListener {
            startActivity(Intent(requireContext(), UserActivity::class.java))
        }

        binding.signout.setOnClickListener {
            firebaseAuth.signOut()
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.webClientId)) // replace with your web client ID
                .requestEmail()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

            googleSignInClient.signOut()
            startActivity(Intent(requireContext(), onboarding::class.java))
            StepCounterService.restStep()
            requireContext().stopService(Intent(requireContext(), StepCounterService::class.java))
            requireActivity().finish()
        }

        return binding.root
    }

}