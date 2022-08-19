package com.example.easytravel

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.easytravel.Repository.MainRepository
import com.example.easytravel.ViewModels.UsersViewModel
import com.example.easytravel.databinding.ActivityConfirmTripBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class ConfirmTripActivity : AppCompatActivity() {
    lateinit var usersViewModel: UsersViewModel
    private lateinit var binding : ActivityConfirmTripBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmTripBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.include.imgback.visibility = View.VISIBLE
        binding.include.imgback.setOnClickListener {
            startActivity(Intent(this,TripActivity::class.java))
        }

        usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        usersViewModel.getUserDetail().observe(this, Observer {
            binding.include.tvUserBalance.text = "Rs. " +  it.payment
        })

        val extras = intent.extras
            val tripName = extras!!.getString("tripName")
            val description = extras.getString("description")
            val date = extras.getString("date")
            val time = extras.getString("time")
            val  departure = extras.getString("departure")
            val duration = extras.getString("duration")
            val amount = extras.getString("amount")
        binding.include.textView.text = tripName
        binding.tvDescription.text = description
        binding.tvDate.text = date
        binding.tvTime.text = time
        binding.tvDeparture.text = departure
        binding.tvDuration.text = duration
        binding.tvAmount.text = "Rs. $amount"

        binding.btnCheckout.setOnClickListener {
            val title = "Trip"
            val intent = Intent(this@ConfirmTripActivity, ConfirmTicketActivity::class.java)
            intent.putExtra("key", amount)
            intent.putExtra("key1", title)
            intent.putExtra("key2", tripName)
            startActivity(intent)
        }
    }
}