package com.example.easytravel

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.easytravel.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding : ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnForgot.setOnClickListener {
            if (binding.etForgetEmail.text.isEmpty()){
                binding.etForgetEmail.error = "Email is Required!"
                binding.etForgetEmail.requestFocus()
                return@setOnClickListener
            }else{
                resetPassword(binding.etForgetEmail.text.toString())
            }
        }


    }

    private fun resetPassword(email : String) {
        val auth= FirebaseAuth.getInstance()
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    this@ForgotPasswordActivity,
                    "Check your Email to reset password.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this@ForgotPasswordActivity,
                    task.exception.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}