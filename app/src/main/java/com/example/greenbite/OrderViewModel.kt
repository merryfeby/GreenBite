package com.example.greenbite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.greenbite.checker.Order

class OrderViewModel : ViewModel() {
    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>>
        get() = _orders

    private val _selectedOrder = MutableLiveData<Order?>()
    val selectedOrder: LiveData<Order?>
        get() = _selectedOrder

    suspend fun getAllOrders() {
        _orders.value = App.retrofitService.getAllOrders()
    }

    fun selectOrder(orderID: Int) {
        _selectedOrder.value = _orders.value?.find { it.orderID == orderID }
    }

    fun getOrderById(orderID: Int): Order? {
        return _orders.value?.find { it.orderID == orderID }
    }
}
