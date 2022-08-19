package com.example.easytravel.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.easytravel.Models.Trip
import com.example.easytravel.R

class TripAdapter(private var list: ArrayList<Trip>) : RecyclerView.Adapter<TripAdapter.ViewHolder>() {
    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(
            position: Int,
            Date: String?,
            time: String?,
            Amount: String?,
            deaparture: String?,
            duration: String?,
            tripName: String?,
            Description: String?
        )
    }

    fun setOnItemClickListener( listener: OnItemClickListener) {
        mListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.show_trips, parent, false)
        return ViewHolder(view, mListener!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trip : Trip = list[position]

        holder.date.text = trip.date
        holder.time.text = trip.time
        holder.amount.text = trip.amount
        holder.departure.text = trip.departure
        holder.duration.text = trip.duration
        holder.tripName.text = trip.tripName
        holder.description.text = trip.description
    }

    override fun getItemCount(): Int {
       return  list.size
    }
    class ViewHolder (itemView: View, listener : OnItemClickListener) : RecyclerView.ViewHolder(itemView){
        val date: TextView = itemView.findViewById(R.id.tvDate)
        val time: TextView = itemView.findViewById(R.id.tvTime)
        val amount: TextView = itemView.findViewById(R.id.tvAmount)
        val departure: TextView = itemView.findViewById(R.id.tvDeparture)
        val duration : TextView= itemView.findViewById(R.id.tvDuration)
        val tripName: TextView = itemView.findViewById(R.id.tvTripName)
        val description: TextView = itemView.findViewById(R.id.tvDescription)

        val hello = itemView.setOnClickListener(
            object : View.OnClickListener {
                override fun onClick(v: View) {
                    if (listener != null) {
                        val position: Int = getAdapterPosition()
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position, date.text.toString(), time.text.toString()
                                , amount.text.toString(), departure.text.toString(),
                                duration.text.toString(),tripName.text.toString(),
                                description.text.toString())
                        }
                    }
                }
            })
    }
}