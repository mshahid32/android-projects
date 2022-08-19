package com.example.studentrecord

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class], version = 1)
abstract class CreateDatabase : RoomDatabase(){
    abstract fun studentDao() : StudentDao

    companion object{
        @Volatile
        private var INSTANCE : CreateDatabase? = null

        fun getInstance(context: Context):CreateDatabase{
            synchronized(this){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,CreateDatabase::class.java,"Student Record").build()
                }
            }
            return INSTANCE!!
        }
    }
}