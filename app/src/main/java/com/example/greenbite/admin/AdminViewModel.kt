package com.example.greenbite.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.greenbite.User

class AdminViewModel: ViewModel() {
    private val _activeUser = MutableLiveData<User>()
    val activeUser: LiveData<User>
        get() = _activeUser

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users
}