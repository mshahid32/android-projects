package com.example.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)
    @Delete
    suspend fun deleteContact(contact: Contact)
    @Query("Select * from contact")
    fun getContact():LiveData<List<Contact>>
}