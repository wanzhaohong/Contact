package com.example.contact.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.contact.Model.User

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM User ORDER BY id ASC")
    fun readAllUser(): LiveData<List<User>>

    @Delete
    fun deleteUser(user: User)
}