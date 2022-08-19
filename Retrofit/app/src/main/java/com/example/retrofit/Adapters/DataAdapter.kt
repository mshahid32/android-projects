package com.example.retrofit.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.Models.MyData
import com.example.retrofit.R


class DataAdapter(private var list: ArrayList<MyData>) :
    RecyclerView.Adapter<DataAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_data, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record: MyData = list[position]
        holder.id.text = record.id
        holder.title.text = record.title
        holder.body.text = record.body
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val id: TextView = itemView.findViewById(R.id.tvid)
        val title: TextView = itemView.findViewById(R.id.tvtitle)
        val body: TextView = itemView.findViewById(R.id.tvbody)
    }
}