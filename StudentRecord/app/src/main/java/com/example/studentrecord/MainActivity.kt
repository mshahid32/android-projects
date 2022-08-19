package com.example.studentrecord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var database: CreateDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = CreateDatabase.getInstance(this)
        GlobalScope.launch {
            database.studentDao().insertStudent(Student(1,"Murtaza","Ali","Islamabad"))
        }

    }
}