package com.example.example

class Repository(private val studentDao: StudentDao) {
    suspend fun insertStudentRecord(student: Student){
        studentDao.insertStudent(student)
    }
}