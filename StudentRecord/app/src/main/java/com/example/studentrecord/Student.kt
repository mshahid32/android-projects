package com.example.studentrecord

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student")
data class Student(
    @PrimaryKey
    val rollNo : Int,
    val name : String,
    val fatherName : String,
    val address : String
)
