package com.example.easytravel.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.easytravel.Models.Buses
import com.example.easytravel.R

class BusesAdapter(private var list: ArrayList<Buses>) :
    RecyclerView.Adapter<BusesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sample_show_bus, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val busesRecord = list[position]
        holder.busNo.text = busesRecord.busNo
        holder.point.text = busesRecord.points
        holder.departure.text = busesRecord.departure
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val busNo: TextView = itemView.findViewById(R.id.tvBusNo)
        val point : TextView = itemView.findViewById(R.id.tvPoint)
        val departure: TextView = itemView.findViewById(R.id.tvDeparture)
    }
}