package com.example.retrofit.Api

import com.example.retrofit.Models.MyData
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("posts")
    fun getData(): Call<ArrayList<MyData>>

    @GET("comments")
    fun getCommentsById(@Query("postId") postId : Int):Call<ArrayList<MyData>>

    @GET("posts/{id}")
    fun getPost(@Path("id") postId: Int) : Call<MyData>


    @Multipart
    @POST("upload_file/RestApi/upload_api.php")
    fun callUploadApi(@Part image: MultipartBody.Part): Call<MyData>

    @Multipart
    @POST("upload_file/RestApi/upload_api.php")
    fun UpLoadAudioFile(@Part audio : MultipartBody.Part) : Call<String>
}