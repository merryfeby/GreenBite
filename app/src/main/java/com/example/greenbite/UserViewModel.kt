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

    fun setActiveUser(username: String) {
        viewModelScope.launch {
            val user = App.db.userDao().get(username)
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
}