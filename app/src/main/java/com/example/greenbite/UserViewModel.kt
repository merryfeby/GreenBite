package com.example.greenbite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel:ViewModel() {
    private val _activeUser = MutableLiveData<UserEntity>()
    val activeUser: LiveData<UserEntity>
        get() = _activeUser

    private val _users = MutableLiveData<List<UserEntity>>()
    val users: LiveData<List<UserEntity>>
        get() = _users

    fun setActiveUser(email: String) {
        viewModelScope.launch {
            val user = App.db.userDao().get(email)
            if (user != null) {
                _activeUser.value = user!!
            } else {
                _activeUser.value = UserEntity("","","","","","")
            }
        }
    }
    private fun refreshList() {
        viewModelScope.launch {
            _users.value = App.db.userDao().fetch()
        }
    }

    fun init() {
        setActiveUser("")
        refreshList()
    }

    fun insertUser(user: UserEntity) {
        viewModelScope.launch {
            App.db.userDao().insert(user)
            setActiveUser("")
            refreshList()
        }
    }

    fun checkEmailExists(email: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = App.db.userDao().get(email)
            callback(user != null)
        }
    }

    fun checkLogin(email: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = App.db.userDao().get(email)
            if (user != null && user.password == password) {
                callback(true)
            } else {
                callback(false)
            }
        }
    }

    fun logout() {
        _activeUser.value = UserEntity("","","","","","")
    }

    fun updateUser(user: UserEntity) {
        viewModelScope.launch {
            App.db.userDao().updateUser(user)
            setActiveUser(user.email)
            refreshList()
        }
    }
}