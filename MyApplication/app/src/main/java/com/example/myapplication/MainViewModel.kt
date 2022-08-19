package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(val intialValue : Int) : ViewModel() {
    val count = MutableLiveData<Int?>(intialValue)
    val name = MutableLiveData<String>("Muhammad Shahid Aslam")

    fun increment(){
        var c = count.value
        if (c != null) {
            c = c + 1
        }
        count.value = c
    }
    fun updateName(){
        name.value = "Syed Murtaza Ali"
    }
}