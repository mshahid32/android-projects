package com.example.example

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class], version = 1)
abstract class CreateDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
    companion object{
        private var INSTANCE: CreateDatabase? = null

        fun CreateInstance(context: Context): CreateDatabase {
            @Volatile
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        CreateDatabase::class.java, "StudentRecord"
                    ).build()
                }

            }
            return INSTANCE!!

        }
    }

}