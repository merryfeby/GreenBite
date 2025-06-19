package com.example.greenbite.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenbite.App
import com.example.greenbite.User
import kotlinx.coroutines.launch

class AdminViewModel: ViewModel() {
    private val _activeUser = MutableLiveData<Employee>()
    val activeUser: LiveData<Employee>
        get() = _activeUser

    private val _employees = MutableLiveData<List<Employee>>()
    val employees: LiveData<List<Employee>>
        get() = _employees

    fun setActiveUser(employee: Employee) {
        _activeUser.value = employee
    }

    suspend fun getEmployees() {
        _employees.value = App.retrofitService.getEmployees()
    }

    fun updateUser(id: Int, name: String, email: String) {
        viewModelScope.launch {
            val newUser = User(
                userID = null,
                name = name,
                email = email,
                password = "admin123",
                phone = "-",
                address = "-",
                postcode = "60235",
                pfp_url = "-",
                role = 0,
                credit = 0.0,
                deleted_at = null,
            )
            App.retrofitService.updateEmployees(id, newUser)
        }
    }

}