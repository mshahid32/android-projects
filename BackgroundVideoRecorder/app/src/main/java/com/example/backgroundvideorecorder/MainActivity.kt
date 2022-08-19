package com.example.backgroundvideorecorder

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.backgroundvideorecorder.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), SurfaceHolder.Callback {

    companion object{

        @JvmStatic var mSurfaceHolder : SurfaceHolder? = null
    }




    lateinit var binding: ActivityMainBinding
    private val TAG = "Recorder"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mSurfaceHolder = binding.surfaceView1.holder
        Log.d("Holder", mSurfaceHolder.toString())
        mSurfaceHolder!!.addCallback(this)

        startRecorderService(this)
        binding.StartService.setOnClickListener {
            if(isMyServiceRunning(RecorderService::class.java)){
                Toast.makeText(this,"Running",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Not Running",Toast.LENGTH_SHORT).show()

            }


        }
        binding.StopService.setOnClickListener {
            stopService(Intent(this, RecorderService::class.java))
        }
    }
    private fun startRecorderService(context: Context) {
        Toast.makeText(this,context.toString(),Toast.LENGTH_SHORT).show()
        val serviceIntent = Intent(context, RecorderService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent)
        } else
            context.startService(serviceIntent)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
    }



    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

    }
    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }
}