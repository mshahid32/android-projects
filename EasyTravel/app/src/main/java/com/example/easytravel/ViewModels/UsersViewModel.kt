package com.example.easytravel.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easytravel.Models.Buses
import com.example.easytravel.Models.PaymentHistory
import com.example.easytravel.Models.Users
import com.example.easytravel.Repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {
    val user by lazy {
        repository.currentUser()
    }

    fun login(email: String, password: String) : MutableLiveData<Boolean>{
        return repository.login(email,password)
    }

    fun register(email: String, password: String) : MutableLiveData<String>{
        return repository.register(email,password)
    }

    fun setUser(id : String , user : Users){
        repository.setUser(id,user)
    }

    fun getUserDetail(): MutableLiveData<Users>{
        return repository.getUserDetail()
    }

    fun getUserPaymentDetail(): MutableLiveData<ArrayList<PaymentHistory>> {
        return repository.getUserPaymentDetail()
    }
    fun updateUserName(name : String){
        repository.updateUserName(name)
    }
    fun updatePhoneNumber(phone : String){
        repository.updatePhoneNumber(phone)
    }
}