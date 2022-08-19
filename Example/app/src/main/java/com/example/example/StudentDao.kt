package com.example.example

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface StudentDao {
    @Insert
    suspend fun insertStudent(student: Student)
}