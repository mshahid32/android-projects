package com.example.easytravel.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easytravel.Adapters.BusesAdapter
import com.example.easytravel.Models.Buses
import com.example.easytravel.R
import com.example.easytravel.Repository.MainRepository
import com.example.easytravel.ViewModels.BusesViewModel
import com.example.easytravel.databinding.FragmentBuses1Binding
import com.example.easytravel.databinding.FragmentBuses4Binding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Buses4Fragment : Fragment() {
    private lateinit var binding: FragmentBuses4Binding
    lateinit var busesViewModel : BusesViewModel
    private var list = ArrayList<Buses>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("HelloWorld","4")
        binding = FragmentBuses4Binding.inflate(inflater,container,false)
        busesViewModel = ViewModelProvider(this).get(BusesViewModel::class.java)
        busesViewModel.getBusesDetail("Buses4").observe(viewLifecycleOwner, Observer {
            binding.busRecycler.layoutManager = LinearLayoutManager(activity)
            binding.busRecycler.adapter = BusesAdapter(it)
        })
        binding.Search.setOnClickListener (View.OnClickListener{
            list.clear()
            if (binding.etSearch.text.isEmpty()){
                binding.etSearch.error = "Enter the Search Query"
                binding.etSearch.requestFocus()
                return@OnClickListener
            }else{
                busesViewModel.BusesTimimg("Buses4",binding.etSearch.text.toString()).observe(viewLifecycleOwner,
                    Observer {
                        list.add(it)
                        binding.busRecycler.layoutManager = LinearLayoutManager(activity)
                        binding.busRecycler.adapter = BusesAdapter(list)
                    })
            }
            binding.etSearch.text = null

        })

        return binding.root
    }

}