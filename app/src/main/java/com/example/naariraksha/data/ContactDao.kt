package com.example.naariraksha.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM emergency_contacts")
    fun getAllContacts(): Flow<List<Contact>>

    @Query("SELECT * FROM emergency_contacts")
    suspend fun getContactsOnce(): List<Contact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("SELECT * FROM emergency_contacts LIMIT 1")
    suspend fun getFirstContact(): Contact?
}
