package com.example.retrofit.ViewModels


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofit.Models.MyData
import com.example.retrofit.Repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody

import javax.inject.Inject

@HiltViewModel
class MainViewModel  @Inject constructor(private val repository: MainRepository) : ViewModel() {
    fun MyDataItem():MutableLiveData<ArrayList<MyData>>{
        return repository.getData()
    }
    fun MyPostData():MutableLiveData<ArrayList<MyData>>{
        return repository.getCommentsById(3)
    }

    fun MyPost():MutableLiveData<MyData>{
        return repository.getPost(4)
    }

    fun uploadFile(image: MultipartBody.Part):MutableLiveData<MyData>{
        return repository.callUploadApi(image)
    }


    fun UpLoadAudioFile(audio: MultipartBody.Part):MutableLiveData<String>{
        return repository.UpLoadAudioFile(audio)
    }
}