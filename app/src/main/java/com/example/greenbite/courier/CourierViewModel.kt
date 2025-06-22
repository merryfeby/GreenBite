package com.example.greenbite.courier

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenbite.App
import com.example.greenbite.User
import com.example.greenbite.checker.Order
import kotlinx.coroutines.launch

class CourierViewModel: ViewModel() {
    private val _activeUser = MutableLiveData<User>()
    val activeUser: LiveData<User>
        get() = _activeUser

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>>
        get() = _orders

    private val _activeOrder = MutableLiveData<Order>()
    val activeOrder: LiveData<Order>
        get() = _activeOrder

    fun init(userEmail: String){
        viewModelScope.launch {
            val user = App.retrofitService.getUserByEmail(userEmail)
            if (user != null) {
                _activeUser.value = user!!
                Log.d("Login", "Active user set: ${user.email}")
            } else {
                Log.e("Login", "User not found or error occurred")
            }
            getCookingOrders()
        }
    }

    fun setActiveOrder(id: Int){
        viewModelScope.launch {
            var response = App.retrofitService.getOrderById(id.toString())
            _activeOrder.value = response
        }
    }

    fun getCookingOrders(){
        viewModelScope.launch {
            var orders = App.retrofitService.getCookingOrders()
            _orders.value = orders
        }
    }

    fun getShippingOrders(){
        viewModelScope.launch {
            var orders = App.retrofitService.getShippingOrders()
            _orders.value = orders
        }
    }


    fun getCompletedOrders(){
        viewModelScope.launch {
            var orders = App.retrofitService.getCompletedOrders()
            _orders.value = orders
        }
    }

    fun sortOrderAsc(){
        _orders.value = _orders.value?.sortedBy { it.orderID }
    }

    fun sortOrderDesc(){
        _orders.value = _orders.value?.sortedByDescending { it.orderID }
    }

    fun shipOrder(orderId: Int){
        viewModelScope.launch {
            App.retrofitService.shipOrder(orderId)
        }
    }

    fun finishOrder(){
        viewModelScope.launch {
            App.retrofitService.finishOrder(_activeOrder.value!!.orderID)
        }
    }
}