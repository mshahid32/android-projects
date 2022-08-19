package com.example.easytravel.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.easytravel.Adapters.FragmentsAdapter
import com.example.easytravel.Repository.MainRepository
import com.example.easytravel.ViewModels.UsersViewModel
import com.example.easytravel.databinding.FragmentRouteBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RouteFragment : Fragment() {
    private lateinit var binding: FragmentRouteBinding
    lateinit var usersViewModel: UsersViewModel
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRouteBinding.inflate(inflater,container,false)
        Log.d("HelloWorld", "createView")
        binding.include.textView.text = "Buses Route"
        usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        usersViewModel.getUserDetail().observe(viewLifecycleOwner, Observer {
            binding.include.tvUserBalance.text = "Rs. " + it.payment
        })
        binding.viewPager.adapter = FragmentsAdapter(childFragmentManager)
        binding.tablayout.setupWithViewPager(binding.viewPager)
        return binding.root
    }


}