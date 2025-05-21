package com.example.greenbite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "email") val email:String,
    @ColumnInfo(name = "name") var name:String,
    @ColumnInfo(name = "password") var password:String,
    @ColumnInfo(name = "address") var address:String,
    @ColumnInfo(name = "phone") var phone:String,
    @ColumnInfo(name = "postcode") var postcode:String,
)
