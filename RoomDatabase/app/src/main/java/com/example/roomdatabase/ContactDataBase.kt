package com.example.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1)
abstract class ContactDataBase : RoomDatabase() {

    abstract fun contactDoa():ContactDao

    //Singleton Implemention (create only one instance of database)

    companion object{
        @Volatile
        private var INSTANTCE : ContactDataBase? = null

        fun getInstance(context :Context):ContactDataBase{
            synchronized(this){
                if(INSTANTCE == null){
                    INSTANTCE= Room.databaseBuilder(context.applicationContext,
                        ContactDataBase::class.java,"Contact").build()
                }
            }
            return INSTANTCE!!
        }
    }
}