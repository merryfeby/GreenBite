package com.example.greenbite.Table

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenbite.App
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val cartDao = App.db.cartDao()
    private val _cartTotal = MutableLiveData<Double>()
    val cartTotal: LiveData<Double> = _cartTotal

    suspend fun getCartItemByMenuIdAndUser(userEmail: String, menuId: Int): CartEntity? {
        return cartDao.getCartItemByMenuIdAndUser(userEmail, menuId)
    }

    fun addToCart(cartItem: CartEntity) {
        viewModelScope.launch {
            val existingItem = getCartItemByMenuIdAndUser(cartItem.user_email, cartItem.id_menu)

            if (existingItem != null) {
                val updatedItem = existingItem.copy(jumlah = existingItem.jumlah + 1)
                cartDao.updateCartItem(updatedItem)
            } else {
                cartDao.addToCart(cartItem)
            }
        }
    }
    fun getCartByUser(userEmail: String): LiveData<List<CartEntity>> {
        return cartDao.getCartByUser(userEmail)
    }

    fun removeFromCart(userEmail: String, menuId: Int) {
        viewModelScope.launch {
            cartDao.deleteItemFromCart(userEmail, menuId)
        }
    }

    fun updateCartItemQuantity(cartItem: CartEntity, newQuantity: Int) {
        viewModelScope.launch {
            val updatedItem = cartItem.copy(jumlah = newQuantity)
            cartDao.updateCartItem(updatedItem)
        }
    }

    fun calculateCartTotal(cartItems: List<CartEntity>) {
        var total = 0.0
        for (item in cartItems) {
            total += item.harga * item.jumlah
        }
        _cartTotal.value = total
    }
}
