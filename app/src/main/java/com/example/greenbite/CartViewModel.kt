package com.example.greenbite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.greenbite.checker.Order
import com.example.greenbite.checker.OrderAddon
import com.example.greenbite.checker.OrderDetail
import com.example.greenbite.checker.OrderDetailReq
//import com.example.greenbite.checker.Order
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val latitudeIstts = -7.2913783
    private val longitudeIstts = 112.758697211606
    private val delivery_rate = 5000.0
    private val EARTH_RADIUS_KM = 6371.0

    private val cartDao = App.db.cartDao()

    private val _deliveryTimeEstimate = MutableLiveData<Int>()
    val deliveryTimeEstimate: LiveData<Int> = _deliveryTimeEstimate

    private val AVERAGE_DELIVERY_SPEED_KMH = 30.0
    private val PREPARATION_TIME_MINUTES = 15

    private val _cartTotal = MutableLiveData<Double>()
    val cartTotal: LiveData<Double> = _cartTotal

    private val _deliveryFee = MutableLiveData<Double>()
    val deliveryFee: LiveData<Double> = _deliveryFee

    private val _grandTotal = MutableLiveData<Double>()
    val grandTotal: LiveData<Double> = _grandTotal



    suspend fun getCartItemByMenuIdAndUser(userEmail: String, menuId: Int): CartEntity? {
        return cartDao.getCartItemByMenuIdAndUser(userEmail, menuId)
    }

    fun calculateDeliveryTime(distanceKm: Double) {
        viewModelScope.launch {
            val travelTimeMinutes = (distanceKm / AVERAGE_DELIVERY_SPEED_KMH) * 60
            val totalEstimateMinutes = PREPARATION_TIME_MINUTES + travelTimeMinutes
            val roundedTotalEstimateMinutes = Math.ceil(totalEstimateMinutes).toInt()
            val estimate = roundedTotalEstimateMinutes
            _deliveryTimeEstimate.value = estimate
        }
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
            calculateDeliveryTime(distance)
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

    fun createOrder(userID: Int, emailUser: String) {
        viewModelScope.launch {
            Log.e("order", "ini grand total: ${_deliveryFee.value}")
            val cartItems = cartDao.getListCart(emailUser)
            Log.e("order", "ini grand total: ${_deliveryFee.value}")
            val order = Order(
                orderID = 0,
                userID = userID,
                customer_name = "",
                customer_phone = "",
                subtotal = _cartTotal.value!!,
                prep_time = 0,
                shipping_fee = _deliveryFee.value!!,
                total = _grandTotal.value!!,
                status = "pending",
                created_at = "",
                updated_at = "",
                address = "",
                order_details = cartItems.map { item ->
                    OrderDetail(
                        orderDetailID = 0,
                        productID = item.id_menu,
                        product_name = "",
                        quantity = item.jumlah,
                        price = item.harga,
                        total = 0,
                        addon = item.add_on,
                        addons = listOf()
                    )
                }
            )
            Log.e("order", "yey berhasil1")
            viewModelScope.launch {
                val response = App.retrofitService.createOrder(order)
                Log.e("order", "yey berhasil")
            }
            cartDao.deleteCart(emailUser)
        }
    }


}
