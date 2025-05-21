package com.example.greenbite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Menu")
data class MenuEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id_menu") val id_menu:Int,
    @ColumnInfo("name_menu") val nama_menu:String,
    @ColumnInfo("price_menu") val price_menu:Int,
    @ColumnInfo("image_menu") val image_menu:String,
    @ColumnInfo("desc_menu") val desc_menu:String,
    @ColumnInfo("category_menu") val category_menu:String,
    @ColumnInfo("stock_menu") val stock_menu:Int,
    @ColumnInfo("rating_menu") val rating_menu:Int,
)