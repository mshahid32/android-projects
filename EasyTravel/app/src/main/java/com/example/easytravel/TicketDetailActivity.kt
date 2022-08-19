package com.example.easytravel

import android.content.Intent
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.easytravel.Repository.MainRepository
import com.example.easytravel.ViewModels.BusesViewModel
import com.example.easytravel.ViewModels.UsersViewModel
import com.example.easytravel.databinding.ActivityTicketDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
@AndroidEntryPoint
class TicketDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTicketDetailBinding
    lateinit var usersViewModel: UsersViewModel
    lateinit var busesViewModel: BusesViewModel
    private var tiketType : String? = null
    var bill : Double? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.include.imgback.visibility = View.VISIBLE

        binding.include.imgback.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        usersViewModel.getUserDetail().observe(this, Observer {
            binding.include.tvUserBalance.text = "Rs. " +  it.payment
        })

        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString("key")
            tiketType = extras.getString("titleName")
            binding.tvDescription.text = value
        }

        binding.btnSearch.setOnClickListener {

            val pointName : String = binding.etDestination.text.toString().trim()
            if (pointName.isEmpty()){
                binding.etDestination.error = "Please enter a Destination! "
                binding.etDestination.requestFocus()
                return@setOnClickListener
            }else{
                busesViewModel = ViewModelProvider(this).get(BusesViewModel::class.java)
                busesViewModel.checkPoint(pointName).observe(this, Observer { distance ->
                   if (distance == 0){
                       Toast.makeText(this,"Not Found",Toast.LENGTH_SHORT).show()
                   }else{
                       binding.SearchLayout.visibility = View.VISIBLE
                       binding.tvPointName.text = pointName.substring(0, 1).uppercase(Locale.getDefault()) + pointName.substring(1)
                       busesViewModel.BusesTimimg("Buses1",pointName).observe(this, Observer {
                           binding.tvBusNo.text = it.busNo
                           binding.tvDeparture.text = it.departure
                       })
                       busesViewModel.BusesTimimg("Buses4",pointName).observe(this, Observer {
                           binding.tvBusNo4.text = it.busNo
                           binding.tvDeparture4.text = it.departure
                       })
                       busesViewModel.BusesTimimg("Buses6",pointName).observe(this, Observer {
                           binding.tvBusNo6.text = it.busNo
                           binding.tvDeparture6.text = it.departure
                       })
                      when(tiketType!!){
                          "Single" ->{
                              if (distance <= 15) {
                                  bill = 10.0
                              } else if (distance in 16..30) {
                                  bill = 20.0
                              } else {
                                  bill = 30.0
                              }
                              binding.tvBill.text = "Rs." + bill.toString()
                          }
                          "Day" ->{
                              bill = if (distance <= 15) {
                                  20.0
                              } else if (distance in 16..30) {
                                  30.0
                              } else {
                                  50.0
                              }
                              binding.tvBill.text = "Rs." + bill.toString()
                          }
                          "Week" ->{
                              bill = if (distance <= 15) {
                                  20.0 * 7
                              } else if (distance in 16..30) {
                                  30.0 * 7
                              } else {
                                  50.0 * 7
                              }
                              binding.tvBill.text = "Rs." + bill.toString()
                          }
                          "Month" ->{
                              bill = if (distance <= 15) {
                                  20.0 * 30
                              } else if (distance in 16..30) {
                                  30.0 * 30
                              } else {
                                  50.0 * 30
                              }
                              binding.tvBill.text = "Rs." + bill.toString()
                          }
                          "Semester" ->{
                              bill = if (distance <= 15) {
                                  20.0 * 120
                              } else if (distance in 16..30) {
                                  30.0 * 120
                              } else {
                                  50.0 * 120
                              }
                              binding.tvBill.text = "Rs." + bill.toString()
                          }
                          "Trip" ->{
                              Toast.makeText(this, "Trip is called", Toast.LENGTH_SHORT).show()
                          }
                      }
                   }
                })
            }
        }

        binding.btnCheckout.setOnClickListener {
            val intent = Intent(this, ConfirmTicketActivity::class.java)
            intent.putExtra("key", bill.toString())
            intent.putExtra("key1", tiketType )
            intent.putExtra("key2", binding.etDestination.text.toString())
            startActivity(intent)
        }
    }
    fun showmore(view: View?) {
        if (binding.expandableView.visibility === View.GONE) {
            binding.showtext.visibility = View.GONE
            binding.showtext1.visibility = View.VISIBLE
            TransitionManager.beginDelayedTransition(binding.cardviewExpandable, AutoTransition())
            binding.expandableView.visibility = View.VISIBLE
        } else {
            binding.showtext1.visibility = View.GONE
            binding.showtext.visibility = View.VISIBLE
            TransitionManager.beginDelayedTransition(binding.cardviewExpandable, AutoTransition())
            binding.expandableView.visibility = View.GONE
        }
    }
}