package com.example.greenbite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val latitudeIstts = -7.2913783
    private val longitudeIstts = 112.758697211606
    private val delivery_rate = 5000.0
    private val EARTH_RADIUS_KM = 6371.0

    private val cartDao = App.db.cartDao()

    private val _cartTotal = MutableLiveData<Double>()
    val cartTotal: LiveData<Double> = _cartTotal

    private val _deliveryFee = MutableLiveData<Double>()
    val deliveryFee: LiveData<Double> = _deliveryFee

    private val _grandTotal = MutableLiveData<Double>()
    val grandTotal: LiveData<Double> = _grandTotal

    suspend fun getCartItemByMenuIdAndUser(userEmail: String, menuId: Int): CartEntity? {
        return cartDao.getCartItemByMenuIdAndUser(userEmail, menuId)
    }


    fun calculateDeliveryFee(postcode: String) {
        viewModelScope.launch {
            val response = App.retrofitService.getCoordinates(postcode)
            val dLat = Math.toRadians(response.latitude - latitudeIstts)
            val dLon = Math.toRadians(response.longitude - longitudeIstts)
            val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.cos(Math.toRadians(latitudeIstts)) * Math.cos(Math.toRadians(response.latitude)) *
                    Math.sin(dLon / 2) * Math.sin(dLon / 2)
            val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
            val distance = EARTH_RADIUS_KM * c
            _deliveryFee.value = distance * delivery_rate
        }
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

    fun calculateGrandTotal(cartItems: List<CartEntity>, postcode: String) {
        viewModelScope.launch {
            var total = 0.0

            for (item in cartItems) {
                total += item.harga * item.jumlah
            }
            var subtotal = total

            val response = App.retrofitService.getCoordinates(postcode)
            val dLat = Math.toRadians(response.latitude - latitudeIstts)
            val dLon = Math.toRadians(response.longitude - longitudeIstts)
            val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.cos(Math.toRadians(latitudeIstts)) * Math.cos(Math.toRadians(response.latitude)) *
                    Math.sin(dLon / 2) * Math.sin(dLon / 2)
            val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
            val distance = EARTH_RADIUS_KM * c

             val deliveryFee = distance * delivery_rate
            _deliveryFee.value = deliveryFee

            subtotal += deliveryFee

            _grandTotal.value = subtotal
        }


    }
}
