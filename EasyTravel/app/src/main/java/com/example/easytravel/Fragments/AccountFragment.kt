package com.example.easytravel.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.easytravel.AboutActivity
import com.example.easytravel.PaymentsActivity
import com.example.easytravel.Repository.MainRepository
import com.example.easytravel.SigninActivity
import com.example.easytravel.UpdateDetailsActivity
import com.example.easytravel.ViewModels.UsersViewModel
import com.example.easytravel.databinding.FragmentAccountBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding
    lateinit var usersViewModel: UsersViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater,container,false)
        usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        usersViewModel.getUserDetail().observe(viewLifecycleOwner, Observer {
            binding.include.tvUserBalance.text = "Rs. " + it.payment
            binding.tvAccountName.text = it.userName
            binding.tvAccountNumber.text = it.contact
            binding.tvBalance.text = "Rs. " + it.payment
        })
        binding.include.textView.text = "Account"

        binding.btnPayment.setOnClickListener {
            startActivity(Intent(activity,PaymentsActivity::class.java))
        }
        binding.btnUpdate.setOnClickListener {
            startActivity(Intent(activity,UpdateDetailsActivity::class.java))
        }
        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity, SigninActivity::class.java)
            startActivity(intent)
        }
        binding.btnAbout.setOnClickListener {
            startActivity(Intent(activity,AboutActivity::class.java))
        }

        return binding.root
    }

}