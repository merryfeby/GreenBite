package com.example.greenbite.checker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenbite.App
import com.example.greenbite.User
import kotlinx.coroutines.launch

class CheckerViewModel: ViewModel(){
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
            getPendingOrders()
        }
    }
    fun setActiveOrder(id: Int){
        viewModelScope.launch {
            var response = App.retrofitService.getOrderById(id.toString())
            _activeOrder.value = response
        }
    }
    fun sortOrderAsc(){
        _orders.value = _orders.value?.sortedBy { it.orderID }
    }

    fun sortOrderDesc(){
        _orders.value = _orders.value?.sortedByDescending { it.orderID }
    }

    fun acceptOrder(time: Int){
        val body = mapOf("prep_time" to time)
        viewModelScope.launch {
            App.retrofitService.acceptOrder(_activeOrder.value!!.orderID, body)
            var orders = App.retrofitService.getAllPendingOrder()
            _orders.value = orders
        }
    }

    fun rejectOrder(){
        viewModelScope.launch {
            App.retrofitService.rejectOrder(_activeOrder.value!!.orderID)
            var orders = App.retrofitService.getAllPendingOrder()
            _orders.value = orders
        }
    }

    fun getActiveOrders(){
        viewModelScope.launch {
            var orders = App.retrofitService.getActiveOrders()
            _orders.value = orders
        }
    }

    fun getPendingOrders(){
        viewModelScope.launch {
            var orders = App.retrofitService.getAllPendingOrder()
            _orders.value = orders
        }
    }
}