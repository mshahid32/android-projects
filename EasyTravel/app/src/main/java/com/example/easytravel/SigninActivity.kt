package com.example.easytravel

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.easytravel.ViewModels.UsersViewModel
import com.example.easytravel.databinding.ActivitySigninBinding
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class SigninActivity : AppCompatActivity() {
    lateinit var usersViewModel: UsersViewModel
    var progressDialog: ProgressDialog? = null
    private lateinit var binding : ActivitySigninBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        progressDialog = ProgressDialog(this)
        progressDialog!!.setTitle("Login")
        progressDialog!!.setMessage("Login to your Account")

        binding.btnSignIn.setOnClickListener(View.OnClickListener {
            if (binding.etEmail.text.toString().trim().isEmpty()) {
                binding.etEmail.error = "Email Address is Required"
                binding.etEmail.requestFocus()
                return@OnClickListener
            } else if (binding.etPassword.text.toString().trim().isEmpty()) {
                binding.etPassword.error = "Password is Required"
                binding.etPassword.requestFocus()
                return@OnClickListener
            } else {
                progressDialog!!.show()
                usersViewModel.login(binding.etEmail.text.toString(),binding.etPassword.text.toString()).observe(this,
                    Observer {
                        if (it) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            progressDialog!!.dismiss()
                            Toast.makeText(this, "Email or Password is incorrect", Toast.LENGTH_SHORT).show()
                        }
                    })


            }
        })


        binding.register.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this@SigninActivity,ForgotPasswordActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        usersViewModel.user?.let {
            startActivity(Intent(this@SigninActivity,MainActivity::class.java))
        }
    }
    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}