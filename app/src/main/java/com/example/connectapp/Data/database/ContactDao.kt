package com.example.connectapp.Data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Upsert
    suspend fun upsertContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)


    @Query("SELECT * FROM contact_table ORDER BY `user name` ASC")
    fun getContactsSortName(): Flow<List<Contact>>


    @Query("SELECT * FROM contact_table ORDER BY dateOfCreation DESC")
    fun getContactSortDate(): Flow<List<Contact>>










}