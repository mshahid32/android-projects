package com.example.retrofit

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit.Adapters.DataAdapter
import com.example.retrofit.Models.MyData
import com.example.retrofit.Repository.MainRepository
import com.example.retrofit.ViewModels.MainViewModel
import com.example.retrofit.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    @Inject
    lateinit var repository: MainRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

      //  val apiInterface = RetrofitHelper.getInstance().create(ApiInterface::class.java)
      //  val repository = MainRepository(apiInterface)
       // mainViewModel= ViewModelProvider(this,MainViewModelFactory(repository)).get(MainViewModel::class.java)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.MyPost().observe(this, Observer{
            val list = ArrayList<MyData>()
            list.add(it)
            binding.mydataRecycler.layoutManager = LinearLayoutManager(this)
            binding.mydataRecycler.adapter = DataAdapter(list)
        })

        val file = File(Uri.parse("url").path)

        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val filePart = MultipartBody.Part.createFormData("sendimage", file.name, requestBody)
        mainViewModel.uploadFile(filePart)
        mainViewModel.UpLoadAudioFile(filePart)
    }
}