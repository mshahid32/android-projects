package com.example.retrofit.Repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.retrofit.Api.ApiInterface
import com.example.retrofit.Models.MyData
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Part
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiInterface: ApiInterface) {

    fun getData(): MutableLiveData<ArrayList<MyData>> {
        val result = apiInterface.getData()
        val loginLiveData = MutableLiveData<ArrayList<MyData>>()
        result.enqueue(object : Callback<ArrayList<MyData>?> {
            override fun onResponse(
                call: Call<ArrayList<MyData>?>,
                response: Response<ArrayList<MyData>?>
            ) {
                val responseBody = response.body()!!
                loginLiveData.postValue(responseBody)
            }

            override fun onFailure(call: Call<ArrayList<MyData>?>, t: Throwable) {
               Log.d("Error","Api Not Response")
            }
        })
        return loginLiveData
    }
    fun getCommentsById(postId : Int) : MutableLiveData<ArrayList<MyData>>{
        val result = apiInterface.getCommentsById(postId)
        val commentLiveData = MutableLiveData<ArrayList<MyData>>()
        result.enqueue(object : Callback<ArrayList<MyData>?> {
            override fun onResponse(
                call: Call<ArrayList<MyData>?>,
                response: Response<ArrayList<MyData>?>
            ) {
                val responseBody = response.body()!!
                commentLiveData.postValue(responseBody)
            }

            override fun onFailure(call: Call<ArrayList<MyData>?>, t: Throwable) {
                Log.d("Error","Api Not Response")
            }
        })
        return commentLiveData
    }

    fun getPost(postId: Int) : MutableLiveData<MyData>{
        val result = apiInterface.getPost(postId)
        val postLiveData = MutableLiveData<MyData>()
        result.enqueue(object : Callback<MyData> {
            override fun onResponse(
                call: Call<MyData>,
                response: Response<MyData>
            ) {
                val responseBody = response.body()!!
                postLiveData.postValue(responseBody)
            }

            override fun onFailure(call: Call<MyData>, t: Throwable) {
                Log.d("Error",t.message.toString())
            }
        })
        return postLiveData
    }


    fun callUploadApi(image: MultipartBody.Part): MutableLiveData<MyData>{
        val result = apiInterface.callUploadApi(image)
        val postLiveData = MutableLiveData<MyData>()
        result.enqueue(object : Callback<MyData> {
            override fun onResponse(
                call: Call<MyData>,
                response: Response<MyData>
            ) {
                val responseBody = response.body()!!
                postLiveData.postValue(responseBody)
            }

            override fun onFailure(call: Call<MyData>, t: Throwable) {
                Log.d("Error",t.message.toString())
            }
        })
        return postLiveData
    }


    fun UpLoadAudioFile(audio : MultipartBody.Part): MutableLiveData<String> {
        val result = apiInterface.UpLoadAudioFile(audio)
        val stringLiveData = MutableLiveData<String>()
        result.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response != null){
                    stringLiveData.postValue(response.toString())
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                stringLiveData.postValue(t.message.toString())
            }

        })
        return stringLiveData
    }
}