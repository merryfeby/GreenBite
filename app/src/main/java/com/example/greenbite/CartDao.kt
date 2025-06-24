package com.example.greenbite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CartDao {

    @Insert
    suspend fun addToCart(cartEntity: CartEntity)

    @Query("SELECT * FROM cart WHERE user_email = :email")
    fun getCartByUser(email: String): LiveData<List<CartEntity>>

    @Query("SELECT * FROM cart WHERE user_email = :email")
    suspend fun getListCart(email: String): List<CartEntity>

    @Query("DELETE FROM cart WHERE user_email = :email AND id_menu = :menuId")
    suspend fun deleteItemFromCart(email: String, menuId: Int)

    @Update
    suspend fun updateCartItem(cartEntity: CartEntity)

    @Query("SELECT * FROM cart WHERE user_email = :email AND id_menu = :menuId LIMIT 1")
    suspend fun getCartItemByMenuIdAndUser(email: String, menuId: Int): CartEntity?

    @Query("DELETE FROM cart WHERE user_email = :email")
    suspend fun deleteCart(email: String)
}
