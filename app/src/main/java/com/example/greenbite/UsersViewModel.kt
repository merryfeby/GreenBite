package com.example.greenbite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UsersViewModel:ViewModel() {
    private val _activeUser = MutableLiveData<User?>()
    val activeUser: LiveData<User?>
        get() = _activeUser

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    private val _postcodes = MutableLiveData<List<Postcode>>()
    val postcodes: LiveData<List<Postcode>> = _postcodes

    private val _selectedPostcode = MutableLiveData<Postcode?>()
    val selectedPostcode: LiveData<Postcode?> = _selectedPostcode

    fun init(){

    }

    fun fetchPostcodes() {
        viewModelScope.launch {
            try {
                val response = App.retrofitService.getPostcodes()
                _postcodes.postValue(response)
            } catch (e: Exception) {
                Log.e("UsersViewModel", "Error fetching postcodes: ${e.message}")
            }
        }
    }

    fun setSelectedPostcode(postcode: Postcode) {
        _selectedPostcode.value = postcode
    }

    fun registerUser(user: User, callback: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val result = App.retrofitService.createUser(user)
            if (result != null) {
                callback(true, "User registered successfully!")
            } else {
                callback(false, "Registration failed")
            }
        }
    }

    private fun getDefaultErrorMessage(code: Int): String {
        return when (code) {
            409 -> "Email is already registered"
            400 -> "Invalid input data"
            422 -> "Validation failed"
            500 -> "Server error"
            else -> "Registration failed"
        }
    }

    fun checkEmailExists(email: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = App.retrofitService.getUserByEmail(email)
            if (user != null) {
                Log.d("Login", "Email exists: $email")
                onResult(true)
            } else {
                Log.d("Login", "Email not found: $email")
                onResult(false)
            }
        }
    }

    fun loginUser(email: String, password: String, onResult: (Boolean, String, User?) -> Unit) {
        viewModelScope.launch {
            try {
                val user = App.retrofitService.loginUser(email, password)

                if (user != null) {
                    _activeUser.value = user
                    Log.d("Login", "Login successful for: $email, Role: ${user.role}")
                    onResult(true, "Login successful!", user)
                } else {
                    Log.e("Login", "Login failed - user is null")
                    onResult(false, "Login failed", null)
                }
            } catch (e: retrofit2.HttpException) {
                val errorMessage = when (e.code()) {
                    401 -> "Invalid email or password"
                    404 -> "Email not registered"
                    500 -> "Server error. Please try again later"
                    else -> "Login failed. Please try again"
                }
                Log.e("Login", "HTTP error ${e.code()}: ${e.message()}")
                onResult(false, errorMessage, null)
            } catch (e: Exception) {
                Log.e("Login", "Login error: ${e.message}", e)
                onResult(false, "Network error. Please check your connection", null)
            }
        }
    }

    fun checkLogin(email: String, password: String, onResult: (Boolean) -> Unit) {
        loginUser(email, password) { success, _, _ ->
            onResult(success)
        }
    }

    fun setActiveUser(email: String) {
        viewModelScope.launch {
            val user = App.retrofitService.getUserByEmail(email)
            if (user != null) {
                _activeUser.value = user
                Log.d("Login", "Active user set: ${user.email}")
            } else {
                Log.e("Login", "Error setting active user")
            }
        }
    }

    fun setActiveUser(user: User) {
        _activeUser.value = user
    }

    fun getUserRoleName(role: Int): String {
        return when (role) {
            1 -> "Customer"
            2 -> "Employee"
            3 -> "Admin"
            else -> "Unknown"
        }
    }

    fun hasRole(requiredRole: Int): Boolean {
        return _activeUser.value?.role == requiredRole
    }

    fun isLoggedIn(): Boolean {
        return _activeUser.value != null
    }

    fun logout() {
        _activeUser.value = null
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            val currentUser = _activeUser.value ?: return@launch
            val updatedUser = App.retrofitService.updateUser(currentUser.userID!!, user)
            _activeUser.value = updatedUser!!.copy(userID = currentUser.userID, role = currentUser.role)
        }
    }

    fun refreshActiveUser() {
        val currentUser = _activeUser.value ?: return
        viewModelScope.launch {
            val user = App.retrofitService.getUserByEmail(currentUser.email)
            if (user != null) {
                _activeUser.value = user
            } else {
                Log.e("UserRefresh", "Error refreshing user")
            }
        }
    }

    fun deleteUser(userID: Int){
        viewModelScope.launch {
            App.retrofitService.deleteUser(userID)
        }
    }
}