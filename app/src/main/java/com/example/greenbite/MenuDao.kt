package com.example.greenbite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MenuDao {
    @Query("SELECT * FROM Menu")
    fun getAllMenu(): LiveData<List<MenuEntity>>

    @Query("SELECT * FROM Menu WHERE rating_menu >= :ratingItem")
    fun getTopMenu(ratingItem: Int): LiveData<List<MenuEntity>>

    @Insert
    suspend fun insertMenu(menu: MenuEntity)

    @Insert
    suspend fun insertAllMenu(menu: List<MenuEntity>)

    @Query("SELECT * FROM Menu WHERE category_menu = :category")
    fun getMenuByCategory(category: String): LiveData<List<MenuEntity>>
}