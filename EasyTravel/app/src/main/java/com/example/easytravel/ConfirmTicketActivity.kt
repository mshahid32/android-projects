package com.example.easytravel

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.easytravel.Models.PaymentHistory
import com.example.easytravel.Repository.MainRepository
import com.example.easytravel.ViewModels.UsersViewModel
import com.example.easytravel.databinding.ActivityConfirmTicketBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ConfirmTicketActivity : AppCompatActivity() {
    lateinit var usersViewModel: UsersViewModel
    private lateinit var binding : ActivityConfirmTicketBinding
    var value : String? = null
    var balance : Double? = null
    var ticketType : String? = null
    var destination : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.include.imgback.visibility = View.VISIBLE
        binding.include.imgback.setOnClickListener {
            startActivity(Intent(this,TicketDetailActivity::class.java))
        }

        usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        usersViewModel.getUserDetail().observe(this, Observer {
            binding.include.tvUserBalance.text = "Rs. " +  it.payment
            balance= it.payment!!.toDouble()
        })

        val extras = intent.extras
        if (extras != null) {
            value = extras.getString("key")
            ticketType = extras.getString("key1")
            destination = extras.getString("key2")
        }

        binding.cdWallet.setOnClickListener(View.OnClickListener {
            val value1: Double = value!!.toDouble()
            if (balance!! < value1) {
                SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Sorry...")
                    .setContentText("Insufficient Balance!")
                    .show()
            } else {
                balance = balance!! - value1
                SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Are you sure?")
                    .setContentText("Rs.$value will be deducted from your Wallet!")
                    .setConfirmText("Confirm")
                    .setConfirmClickListener { sDialog ->
                        sDialog.dismissWithAnimation()
                        val dbUpdate = FirebaseDatabase.getInstance()
                        FirebaseAuth.getInstance().getCurrentUser()?.let { it1 ->
                            dbUpdate.getReference("Users").child(it1.getUid())
                                .child("payment").setValue(balance.toString())
                        }
                        Toast.makeText(
                            this,
                            "Transaction Successful! ",
                            Toast.LENGTH_SHORT
                        ).show()


                        //Save Payment History
                        val currentDate =
                            SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                                .format(Date())
                        val currentTime =
                            SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                                .format(Date())
                        val paymentHistory = FirebaseAuth.getInstance().getCurrentUser()?.let { it1 ->
                            FirebaseDatabase.getInstance().getReference("Users")
                                .child(it1.getUid())
                        }
                        val key: String? = paymentHistory?.push()?.getKey()
                        val paymentDetails = PaymentHistory(currentDate,destination,currentTime,value,ticketType)

                        paymentHistory!!.child("paymentHistory").child(key!!).setValue(paymentDetails)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    .setCancelButton(
                        "Cancel"
                    ) { sDialog -> sDialog.dismissWithAnimation() }
                    .show()
            }
        })

        binding.cdJazzCash.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@ConfirmTicketActivity, JazzCashPaymentActivity::class.java)
            intent.putExtra("price", value.toString())
            startActivity(intent)
        })
    }
}