package com.example.greenbite


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap

class MenuViewModel : ViewModel() {

    private val menuDao = App.db.menuDao()

    private val _currentCategory = MutableLiveData<String>("All")
    val currentCategory: LiveData<String> = _currentCategory

    val allMenuItems: LiveData<List<MenuEntity>> = menuDao.getAllMenu()

    val topMenuItems: LiveData<List<MenuEntity>> = menuDao.getTopMenu(4)

    val currentCategoryMenu: LiveData<List<MenuEntity>> = _currentCategory.switchMap { category ->
        if (category == "All") {
            allMenuItems
        } else {
            menuDao.getMenuByCategory(category)
        }
    }

    fun setCategory(category: String) {
        _currentCategory.value = category
    }

}