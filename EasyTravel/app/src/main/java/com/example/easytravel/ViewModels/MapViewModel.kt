package com.example.easytravel.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easytravel.Models.Marker
import com.example.easytravel.Repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class MapViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {
    fun MyPost(): MutableLiveData<ArrayList<Marker>> {
        return repository.getLocation()
    }
}