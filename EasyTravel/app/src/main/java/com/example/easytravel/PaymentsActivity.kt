package com.example.easytravel

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easytravel.Adapters.PaymentHistoryAdapter
import com.example.easytravel.Models.PaymentHistory
import com.example.easytravel.ViewModels.UsersViewModel
import com.example.easytravel.databinding.ActivityPaymentsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

@AndroidEntryPoint
class PaymentsActivity : AppCompatActivity() {
    lateinit var usersViewModel: UsersViewModel
    private lateinit var binding : ActivityPaymentsBinding
    private lateinit var paymentAdapter : PaymentHistoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.include.textView.text = "Payment History"
        binding.include.imgback.visibility = View.VISIBLE
        binding.include.imgback.setOnClickListener {
            startActivity(Intent(this,TicketDetailActivity::class.java))
        }

        usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        usersViewModel.getUserDetail().observe(this, Observer {
            binding.include.tvUserBalance.text = "Rs. " +  it.payment
        })

        usersViewModel.getUserPaymentDetail().observe(this, Observer {
            binding.PaymentHistoryRecycler.layoutManager = LinearLayoutManager(this)
            paymentAdapter = PaymentHistoryAdapter(it)
            binding.PaymentHistoryRecycler.adapter = paymentAdapter
            paymentAdapter.setOnItemClickListener(object : PaymentHistoryAdapter.OnItemClickListener {
                override fun onItemClick(position: Int, date: String?, time: String?, destination: String?, price: String?, ticketType: String?) {
                    changeItem(position, date, time, destination, price, ticketType)
                }
            })
        })
    }
    fun changeItem(
        position: Int,
        date: String?,
        time: String?,
        destination: String?,
        price: String?,
        ticketType: String?
    ) {
        val builder = AlertDialog.Builder(this)
        val layout_dialog: View = LayoutInflater.from(this).inflate(R.layout.show_details, null)
        val textView = layout_dialog.findViewById<TextView>(R.id.tvDate)
        textView.text = date
        val textView1 = layout_dialog.findViewById<TextView>(R.id.tvTime)
        textView1.text = time
        val textView2 = layout_dialog.findViewById<TextView>(R.id.tvDestination)
        textView2.text = destination
        val textView3 = layout_dialog.findViewById<TextView>(R.id.tvTicketType)
        textView3.text = ticketType
        val textView4 = layout_dialog.findViewById<TextView>(R.id.tvPrice)
        textView4.text = price
        val btn = layout_dialog.findViewById<Button>(R.id.btnOk)
        btn.setOnClickListener {
            val intent = Intent(this, PaymentsActivity::class.java)
            startActivity(intent)
        }
        builder.setView(layout_dialog)
        val dialog = builder.create()
        dialog.show()
    }


}