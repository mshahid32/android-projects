package com.example.easytravel

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easytravel.Adapters.TripAdapter
import com.example.easytravel.Repository.MainRepository
import com.example.easytravel.ViewModels.BusesViewModel
import com.example.easytravel.ViewModels.UsersViewModel
import com.example.easytravel.databinding.ActivityTripBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TripActivity : AppCompatActivity() {
    lateinit var usersViewModel: UsersViewModel
    lateinit var busesViewModel: BusesViewModel
    private lateinit var binding : ActivityTripBinding
    private lateinit var tripAdapter : TripAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.include.textView.text = "Trips"
        binding.include.imgback.visibility = View.VISIBLE
        binding.include.imgback.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        busesViewModel = ViewModelProvider(this).get(BusesViewModel::class.java)
        usersViewModel.getUserDetail().observe(this, Observer {
            binding.include.tvUserBalance.text = "Rs. " +  it.payment
        })
        busesViewModel.getTripDetail().observe(this, Observer {
            binding.TripRecycler.layoutManager = LinearLayoutManager(this)
            tripAdapter = TripAdapter(it)
            binding.TripRecycler.adapter = tripAdapter
            tripAdapter.setOnItemClickListener(object : TripAdapter.OnItemClickListener {
                override fun onItemClick(position: Int, Date: String?, time: String?, Amount: String?, deaparture: String?, duration: String?, tripName: String?, Description: String?) {
                    val intent = Intent(
                        this@TripActivity,
                        ConfirmTripActivity::class.java
                    )
                    intent.putExtra("tripName", tripName)
                    intent.putExtra("description", Description)
                    intent.putExtra("date", Date)
                    intent.putExtra("time", time)
                    intent.putExtra("amount", Amount)
                    intent.putExtra("departure", deaparture)
                    intent.putExtra("duration", duration)
                    startActivity(intent)
                }
            })
        })
    }
}