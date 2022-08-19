package com.example.easytravel.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easytravel.Models.Buses
import com.example.easytravel.Models.Trip
import com.example.easytravel.Repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BusesViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {
    fun getBusesDetail(busTime : String): MutableLiveData<ArrayList<Buses>> {
        return repository.getBusesDetail(busTime)
    }
    fun checkPoint(pointName : String) : MutableLiveData<Int> {
        return repository.CheckBusPoint(pointName)
    }
    fun BusesTimimg(busTime: String,pointName : String) : MutableLiveData<Buses> {
        return repository.BusesTiming(busTime,pointName)
    }
    fun getTripDetail(): MutableLiveData<ArrayList<Trip>> {
        return repository.getTripDetail()
    }
}