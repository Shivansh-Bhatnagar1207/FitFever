package com.example.vitalizeme.service

import android.app.Activity
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class OTP(
    private val auth: FirebaseAuth,
    private val activity: Activity
) {
    var storedVerificationId: String? = null
    fun sendVerificationCode(
        phone: String,
        onCodeSent: (String) -> Unit
    ) {
        Log.d("FirebaseOTP", "sendVerificationCode on $phone ")
        val number = "+91$phone"
        val option = PhoneAuthOptions
            .newBuilder(auth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    auth.signInWithCredential(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val user = task.result?.user
                            } else {
                                Log.d("LoginWithOTP", "onVerificationCompleted: ")
                            }
                        }
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Log.e("LoginWithOTP", "onVerificationFailed: ${e.message} ")
                }


                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    super.onCodeSent(verificationId, token)
                    storedVerificationId = verificationId
                    Log.d("FirebaseOTP", "📩 OTP Sent. VerificationId = $verificationId")
                    onCodeSent(verificationId)
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(option)
    }


    fun verifyOTP(otp: String, onResult: (Boolean) -> Unit) {
        val cred = storedVerificationId?.let { id ->
            PhoneAuthProvider.getCredential(id, otp)
        }

        if (cred != null) {
            auth.signInWithCredential(cred)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        onResult(true)
                    }
                    else
                        onResult(false)
                }
        }else
            onResult(false)
    }
}