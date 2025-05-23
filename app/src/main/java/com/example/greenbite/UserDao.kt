package com.example.greenbite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM users")
    suspend fun fetch():List<UserEntity>

    @Query("SELECT * FROM users where email = :email")
    suspend fun get(email:String): UserEntity?

    @Update
    suspend fun updateUser(user: UserEntity)
}