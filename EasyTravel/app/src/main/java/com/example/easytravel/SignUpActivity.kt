package com.example.easytravel

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.easytravel.Models.Users
import com.example.easytravel.ViewModels.UsersViewModel
import com.example.easytravel.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    lateinit var usersViewModel: UsersViewModel
    var progressDialog: ProgressDialog? = null
    private lateinit var binding : ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        progressDialog = ProgressDialog(this)
        progressDialog!!.setTitle("Creating Account")
        progressDialog!!.setMessage("We're creating your account")

        binding.btnSignUp.setOnClickListener (View.OnClickListener {
            if ( binding.etUserName.text.toString().isEmpty()){
                binding.etUserName.error = "Username is Required"
                binding.etUserName.requestFocus()
                return@OnClickListener
            }else if (binding.etPhone.text.toString().isEmpty()){
                binding.etPhone.error = "Contact Number is Required"
                binding.etPhone.requestFocus()
                return@OnClickListener
            } else if (binding.etEmail.text.toString().isEmpty()){
                binding.etEmail.error = "Email is Required"
                binding.etEmail.requestFocus()
                return@OnClickListener
            } else if (binding.etPassword.text.toString().isEmpty()){
                binding.etPassword.error = "Password is Required"
                binding.etPassword.requestFocus()
                return@OnClickListener
            } else if (binding.etConfirmPassword.text.toString().isEmpty()){
                binding.etConfirmPassword.error = "Password is Required"
                binding.etConfirmPassword.requestFocus()
                return@OnClickListener
            }
            else if (binding.etPassword.text.toString()
                    .contains(binding.etConfirmPassword.text.toString())
            ) {
                progressDialog!!.show()

                usersViewModel.register(binding.etEmail.text.toString(),binding.etPassword.text.toString()).observe(this,
                    Observer {
                        if (it.contains("error")){
                            progressDialog!!.dismiss()
                            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                        }else{
                            val payment = "0.0"
                            val user = Users(
                                binding.etUserName.text.toString(),
                                binding.etPhone.text.toString(),
                                binding.etEmail.text.toString(),
                                binding.etPassword.text.toString(),
                                payment
                            )
                            usersViewModel.setUser(it,user)
                            Toast.makeText(this, "User Created Successfully", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                    })
            } else {
                Toast.makeText(this, "Password does not match!", Toast.LENGTH_SHORT)
                    .show()
            }
        })

    }
}