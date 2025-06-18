package com.example.greenbite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_cart") val id_cart: Int = 0,
    @ColumnInfo(name = "user_email") val user_email:String,
    @ColumnInfo(name = "id_menu") val id_menu:Int,
    @ColumnInfo(name = "nama_menu") val nama_menu:String,
    @ColumnInfo(name = "harga_menu") val harga:Double,
    @ColumnInfo(name = "jumlah_menu") val jumlah:Int,
    @ColumnInfo(name = "add_on") val add_on:String,
)
