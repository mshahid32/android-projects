package com.example.backgroundvideorecorder

import java.io.IOException;
import java.util.List;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.media.MediaRecorder;
import android.os.Environment
import android.os.IBinder;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;
import java.io.File


class RecorderService : Service() {
    private var mSurfaceView: SurfaceView? = null
    private var mSurfaceHolder: SurfaceHolder? = null
    private var mRecordingStatus = false
    private var mMediaRecorder: MediaRecorder? = null
    override fun onCreate() {
        mRecordingStatus = false
        //mServiceCamera = CameraRecorder.mCamera;
        mServiceCamera = Camera.open(1)
       // mSurfaceView = MainActivity.mSurfaceView
        mSurfaceHolder = MainActivity.mSurfaceHolder
        super.onCreate()
        Log.d("Status",mRecordingStatus.toString())
        if (mRecordingStatus == false){
            Log.d("Status",mRecordingStatus.toString())
            startRecording()
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        // TODO Auto-generated method stub
        return null
    }

    override fun onDestroy() {
        stopRecording()
        mRecordingStatus = false
        super.onDestroy()
    }

    fun startRecording(): Boolean {
        return try {
            Toast.makeText(baseContext, "Recording Started", Toast.LENGTH_SHORT).show()

            //mServiceCamera = Camera.open();
            val params: Camera.Parameters = mServiceCamera!!.getParameters()
            mServiceCamera?.setParameters(params)
            val p: Camera.Parameters = mServiceCamera!!.getParameters()
            val listSize: MutableList<Size>? = p.getSupportedPreviewSizes()
            val mPreviewSize: Size = listSize!![2]

            p.setPreviewSize(mPreviewSize.width, mPreviewSize.height)
            p.setPreviewFormat(PixelFormat.YCbCr_420_SP)
            mServiceCamera!!.setParameters(p)
            try {
                mServiceCamera!!.setPreviewDisplay(mSurfaceHolder)
                mServiceCamera!!.startPreview()
            } catch (e: IOException) {
                Log.e(TAG, e.message.toString())
                e.printStackTrace()
            }
            mServiceCamera?.unlock()
            mMediaRecorder = MediaRecorder()
            mMediaRecorder!!.setCamera(mServiceCamera)
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                    .toString()
                        + File.separator + "ActivTrak"
            )
            if (!file.exists()) {
                file.mkdirs()
            }
            val fileName = "Record"
            var audiofile: File? = null
            try {
                audiofile = File.createTempFile(fileName, ".mp4", file)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (audiofile != null) {
                Log.d("FileName",audiofile.absolutePath.toString())
            }
            mMediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            mMediaRecorder!!.setVideoSource(MediaRecorder.VideoSource.CAMERA)
            mMediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            mMediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
            mMediaRecorder!!.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT)
            mMediaRecorder!!.setOutputFile(audiofile?.absolutePath)
            mMediaRecorder!!.setVideoFrameRate(30)
            mMediaRecorder!!.setVideoSize(mPreviewSize.width, mPreviewSize.height)
            mMediaRecorder!!.setPreviewDisplay(mSurfaceHolder!!.surface)
            mMediaRecorder!!.prepare()
            mMediaRecorder!!.start()
            mRecordingStatus = true
            true
        } catch (e: IllegalStateException) {
            Log.d(TAG, e.message.toString())
            e.printStackTrace()
            false
        } catch (e: IOException) {
            Log.d(TAG, e.message.toString())
            e.printStackTrace()
            false
        }
    }

    fun stopRecording() {
        Toast.makeText(baseContext, "Recording Stopped", Toast.LENGTH_SHORT).show()
        try {
            mServiceCamera!!.reconnect()
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        mMediaRecorder!!.stop()
        mMediaRecorder!!.reset()
        mServiceCamera!!.stopPreview()
        mMediaRecorder!!.release()
        mServiceCamera!!.release()
        mServiceCamera = null
    }

    companion object {
        private const val TAG = "RecorderService"
        private var mServiceCamera: Camera? = null
    }
}