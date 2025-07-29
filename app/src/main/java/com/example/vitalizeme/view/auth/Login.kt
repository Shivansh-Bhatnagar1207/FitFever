package com.example.vitalizeme.view.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vitalizeme.R
import com.example.vitalizeme.databinding.FragmentLoginBinding
import com.example.vitalizeme.view.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class Login : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient


    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credientials = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credientials).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            } else {
                Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show()
            }
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)

        binding.loginbtn.setOnClickListener {
            val email = binding.etemail.text.toString()
            val password = binding.etpassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                binding.emailLayout.error = "Email Required"
                binding.passwordLayout.error = "Password Required"
            } else {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(requireContext(), MainActivity::class.java))
                            requireActivity().finish()
                        } else {
                            Toast.makeText(requireContext(), "Failed to SignIn", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }

        }
        binding.signupLink.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_signUp)
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.webClientId))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        var clientLauncher = registerForActivityResult(
            ActivityResultContracts
                .StartActivityForResult()
        ) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(java.lang.Exception::class.java)
                firebaseAuthWithGoogle(account.idToken)
            } catch (e: ApiException) {
                e.printStackTrace()
            }
        }
            binding.magicbtn.setOnClickListener {
                clientLauncher.launch(googleSignInClient.signInIntent)
            }



        return binding.root
    }

}