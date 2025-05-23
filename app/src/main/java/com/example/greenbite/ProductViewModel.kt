package com.example.greenbite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val _currentCategory = MutableLiveData<String>("All")
    val currentCategory: LiveData<String> = _currentCategory

    private val _currentCategoryMenu = MutableLiveData<List<Product>>()
    val currentCategoryMenu: LiveData<List<Product>> = _currentCategoryMenu


    init {
        fetchProducts("All")
    }

    fun setCategory(category: String) {
        if (_currentCategory.value != category) {
            _currentCategory.value = category
            fetchProducts(category)
        }
    }

    private fun fetchProducts(category: String) {
        viewModelScope.launch {
            val fetchedProducts = if (category == "All") {
                App.retrofitService.getAllProducts()
            } else {
                App.retrofitService.getProductsByCategory(category)
            }
            val sorted = fetchedProducts.sortedBy { it.name }
            _currentCategoryMenu.value = sorted
            }
        }
}


