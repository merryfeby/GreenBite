package com.example.greenbite.admin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenbite.App
import com.example.greenbite.User
import com.example.greenbite.checker.Order
import kotlinx.coroutines.launch

class ReportViewModel: ViewModel() {
    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>>
        get() = _orders

    suspend fun getOrders() {
        _orders.value = App.retrofitService.getAllOrders()
    }
}