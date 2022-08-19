package com.example.easytravel.Fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.easytravel.Adapters.ImageAdapter
import com.example.easytravel.MapActivity
import com.example.easytravel.R
import com.example.easytravel.Repository.MainRepository
import com.example.easytravel.TicketDetailActivity
import com.example.easytravel.TripActivity
import com.example.easytravel.ViewModels.UsersViewModel
import com.example.easytravel.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    lateinit var usersViewModel: UsersViewModel
    var timer: Timer? = null
    var handler: Handler? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)


        val imageList: MutableList<Int> = ArrayList()
        imageList.add(R.drawable.one)
        imageList.add(R.drawable.main_image)
        imageList.add(R.drawable.three)
        imageList.add(R.drawable.four)
        val myAdapter = ImageAdapter(imageList)
        binding.imgPager.setAdapter(myAdapter)
        handler = Handler()
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                handler!!.post {
                    var i: Int = binding.imgPager.getCurrentItem()
                    if (i == imageList.size - 1) {
                        i = 0
                    } else {
                        i++
                    }
                    binding.imgPager.setCurrentItem(i, true)
                }
            }
        }, 4000, 4000)



        usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        usersViewModel.getUserDetail().observe(viewLifecycleOwner, Observer {
            binding.include.tvUserBalance.text = "Rs. " +  it.payment
        })
        binding.cdSingleTrip.setOnClickListener {
            val intent = Intent(activity,TicketDetailActivity::class.java)
            val value = "Single Trip: User can use this pass for getting onboard the bus for on single trip in twin cities. "
            val title = "Single"
            intent.putExtra("key", value)
            intent.putExtra("titleName", title)
            startActivity(intent)
        }
        binding.cdDayTicket.setOnClickListener {
            val intent = Intent(activity,TicketDetailActivity::class.java)
            val title = "Day"
            val value = "Day Ticket: This pass will be valid throughout the day during which any route can used by the user with the pass in a 24 hour time slot. "
            intent.putExtra("key", value)
            intent.putExtra("titleName", title)
            startActivity(intent)
        }
        binding.cdWeeklyTicket.setOnClickListener {
            val intent = Intent(activity,TicketDetailActivity::class.java)
            val value = "Weekly Ticket: This pass will be valid throughout the week for using QAU transport. "
            val title = "Week"
            intent.putExtra("key", value)
            intent.putExtra("titleName", title)
            startActivity(intent)
        }
        binding.cdMonthlyTicket.setOnClickListener {
            val intent = Intent(activity,TicketDetailActivity::class.java)
            val value = "Monthly Ticket: This pass will be valid throughout the month for using QAU transport. "
            val title = "Month"
            intent.putExtra("key", value)
            intent.putExtra("titleName", title)
            startActivity(intent)
        }
        binding.cdSemesterTicket.setOnClickListener {
            val intent = Intent(activity,TicketDetailActivity::class.java)
            val title = "Semester"
            val value = "Semester Ticket: This pass will be valid throughout the semester for using QAU transport. "
            intent.putExtra("key", value)
            intent.putExtra("titleName", title)
            startActivity(intent)
        }
        binding.cdPreBook.setOnClickListener {
            val intent = Intent(activity,TripActivity::class.java)
           /* val title = "Trip"
            val value = "Trip/pre-booked: This pass can be used by the user for specified trip/pre-booked transport"
            intent.putExtra("key", value)
            intent.putExtra("titleName", title)*/
            startActivity(intent)
        }

        binding.imgMap.setOnClickListener {
            startActivity(Intent(activity,MapActivity::class.java))
        }
        return binding.root
    }


}