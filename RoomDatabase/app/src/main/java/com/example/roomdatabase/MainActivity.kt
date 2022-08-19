package com.example.roomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var database: ContactDataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = ContactDataBase.getInstance(this)
        GlobalScope.launch {
            database.contactDoa().insertContact(Contact(1,"Shahid","242342"))
            database.contactDoa().insertContact(Contact(3,"Shdfsahid","242342"))
        }

    }
}