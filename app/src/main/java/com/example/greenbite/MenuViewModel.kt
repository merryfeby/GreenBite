package com.example.greenbite


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MenuViewModel : ViewModel() {

    private val menuDao = App.db.menuDao()

    val allMenuItems: LiveData<List<MenuEntity>> = menuDao.getAllMenu()

    val topMenuItems: LiveData<List<MenuEntity>> = menuDao.getTopMenu(4)
}