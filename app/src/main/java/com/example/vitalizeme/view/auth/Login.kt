package com.example.vitalizeme.view.auth

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vitalizeme.R
import com.example.vitalizeme.databinding.FragmentLoginBinding
import com.example.vitalizeme.model.Users
import com.example.vitalizeme.view.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class Login : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient


    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credentials = GoogleAuthProvider.getCredential(idToken, null)

        firebaseAuth.signInWithCredential(credentials)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // ✅ Get Firebase user AFTER successful sign-in
                    val firebaseUser = FirebaseAuth.getInstance().currentUser
                    val userId = firebaseUser?.uid

                    if (userId == null) {
                        Toast.makeText(context, "User ID is null", Toast.LENGTH_SHORT).show()
                        return@addOnCompleteListener
                    }

                    Log.d("FirebaseData", "Authenticated userId: $userId")

                    // 🔥 Now safely query Firestore with UID
                    FirebaseFirestore.getInstance()
                        .collection("User")
                        .document(userId)
                        .get()
                        .addOnSuccessListener { doc ->
                            Log.d("FirebaseData", "${doc}")
                            if (doc.exists()) {
                                Log.d("FirebaseData", "doc : ${doc.data}")
                                val data = doc.toObject(Users::class.java)
                                val sp = requireContext().getSharedPreferences("User", MODE_PRIVATE)
                                sp.edit().apply {
                                    putString("name", data?.name)
                                    putLong("phone", data?.phone!!)
                                    putString("gender", data.gender)
                                    putString("height", data.height)
                                    putString("weight", data.weight)
                                    putString("DOB",data.DOB.toString())
                                    putBoolean("isComplete", true)
                                    apply()
                                }
                                startActivity(Intent(requireContext(), MainActivity::class.java))
                                requireActivity().finish()
                            } else {
                                // No profile info — go to UserActivity
                                startActivity(Intent(requireContext(), UserActivity::class.java))
                                requireActivity().finish()
                            }
                        }.addOnFailureListener {
                            Toast.makeText(context, "Error checking user info", Toast.LENGTH_SHORT)
                                .show()
                        }

                } else {
                    Toast.makeText(context, "Firebase sign-in failed", Toast.LENGTH_SHORT).show()
                    Log.e("FirebaseAuth", "Error: ${task.exception?.message}")
                }
            }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        Log.d("CurrentUser", "${firebaseAuth.currentUser} ")
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
                        } else if (task.exception is FirebaseTooManyRequestsException) {
                            Toast.makeText(
                                requireContext(),
                                "Too many attempts. Try again later.",
                                Toast.LENGTH_LONG
                            ).show()
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
            .requestIdToken(getString(R.string.webClientId)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        var clientLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
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