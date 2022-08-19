package com.example.easytravel.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.easytravel.Models.PaymentHistory
import com.example.easytravel.R
import java.util.*
import kotlin.collections.ArrayList

class PaymentHistoryAdapter (private var list: ArrayList<PaymentHistory>):
    RecyclerView.Adapter<PaymentHistoryAdapter.ViewHolder>() {
    private var mListener: OnItemClickListener? = null
    interface OnItemClickListener {
        fun onItemClick(
            position: Int,
            Date: String?,
            time: String?,
            Destination: String?,
            price: String?,
            ticketType: String?
        )
    }

    fun setOnItemClickListener( listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.payment_history, parent, false)
        return ViewHolder(view,mListener!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val payment : PaymentHistory = list[position]
        holder.date.text = payment.date
        holder.time.text = payment.time
        holder.destination.text = payment.destination!!.substring(0, 1).uppercase(Locale.getDefault()) + payment.destination!!.substring(1)
        holder.payment.text = "Rs. " + payment.price
        holder.ticketType.text = payment.ticketType
    }

    override fun getItemCount(): Int {
        return list.size
    }
    class ViewHolder (itemView: View, listener : OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
       val  date : TextView = itemView.findViewById(R.id.tvDate)
       val  time : TextView = itemView.findViewById(R.id.tvTime)
       val  destination : TextView = itemView.findViewById(R.id.tvDestination)
       val  payment : TextView = itemView.findViewById(R.id.tvPayment)
       val  ticketType : TextView = itemView.findViewById(R.id.tvTicketType)
        val hello = itemView.setOnClickListener(
            object : View.OnClickListener {
                override fun onClick(v: View) {
                    if (listener != null) {
                        val position: Int = getAdapterPosition()
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position, date.text.toString(), time.text.toString()
                                , destination.text.toString(), payment.text.toString(),
                                ticketType.text.toString())
                        }
                    }
                }
            })
    }
}