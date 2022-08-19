package com.example.easytravel.Repository

import androidx.lifecycle.MutableLiveData
import com.example.easytravel.Models.*
import com.example.easytravel.remote.FirebaseSource
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val firebaseSource: FirebaseSource
){

    fun currentUser() = firebaseSource.currentUser()

    fun login(email: String, password: String) : MutableLiveData<Boolean>{
        return firebaseSource.login(email,password)
    }

    fun register(email: String, password: String) : MutableLiveData<String>{
        return firebaseSource.register(email,password)
    }

    fun setUser(id : String , user : Users){
        firebaseSource.setUser(id,user)
    }

    fun getBusesDetail(busTime : String) : MutableLiveData<ArrayList<Buses>> {
        return firebaseSource.getBusesDetail(busTime)
    }
    fun getUserDetail() : MutableLiveData<Users> {
        return firebaseSource.getUserDetail()
    }
    fun CheckBusPoint(pointName : String) : MutableLiveData<Int> {
        return firebaseSource.CheckBusPoint(pointName)
    }
    fun BusesTiming(busTime: String,pointName : String) : MutableLiveData<Buses> {
        return firebaseSource.BusesTiming(busTime,pointName)
    }

    fun getUserPaymentDetail() : MutableLiveData<ArrayList<PaymentHistory>> {
        return firebaseSource.getUserPaymentDetail()
    }
    fun getTripDetail() : MutableLiveData<ArrayList<Trip>> {
        return firebaseSource.getTripDetail()
    }

    fun updateUserName(name : String) = firebaseSource.updateUserName(name)


    fun updatePhoneNumber (phone : String) = firebaseSource.updatePhoneNumber(phone)

    fun getLocation() : MutableLiveData<ArrayList<Marker>>{
        return firebaseSource.getLocation()
    }


}