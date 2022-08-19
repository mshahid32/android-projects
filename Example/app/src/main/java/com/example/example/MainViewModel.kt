package com.example.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {
    fun insertData(student: Student){
        viewModelScope.launch {
            repository.insertStudentRecord(student)
        }
    }
}