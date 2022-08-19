package com.example.easytravel

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.easytravel.Repository.MainRepository
import com.example.easytravel.ViewModels.UsersViewModel
import com.example.easytravel.databinding.ActivityUpdateDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UpdateDetailsActivity : AppCompatActivity() {
    lateinit var usersViewModel: UsersViewModel
    private lateinit var binding : ActivityUpdateDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.include.textView.text = "Update Details"
        binding.include.imgback.visibility = View.VISIBLE


        usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        usersViewModel.getUserDetail().observe(this, Observer {
            binding.include.tvUserBalance.text = "Rs. " +  it.payment
            binding.etUserName.setText(it.userName)
            binding.etContact.setText(it.contact)
        })
        binding.btnUpdateName.setOnClickListener {
            usersViewModel.updateUserName(binding.etUserName.text.toString())
            Toast.makeText(this,"UserName Updated",Toast.LENGTH_SHORT).show()
        }
        binding.btnUpdateContact.setOnClickListener {
            usersViewModel.updatePhoneNumber(binding.etContact.text.toString())
            Toast.makeText(this,"Phone Number Updated",Toast.LENGTH_SHORT).show()
        }
    }
}