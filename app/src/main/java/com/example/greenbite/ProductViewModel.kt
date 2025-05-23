package com.example.greenbite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

//class ProductViewModel : ViewModel() {
//    private val _currentCategory = MutableLiveData<String>("All")
//    val currentCategory: LiveData<String> = _currentCategory
//
//    private val _products = MutableLiveData<List<Product>>()
//    val products: LiveData<List<Product>> = _products
//
//    val currentCategoryMenu: LiveData<List<Product>> = _currentCategory.switchMap { category ->
//        MutableLiveData<List<Product>>().apply {
//            viewModelScope.launch {
//                val fetchedProducts = if (category == "All") {
//                    App.retrofitService.getAllProducts()
//                } else {
//                    App.retrofitService.getProductsByCategory(category)
//                }
//                val temp = fetchedProducts.sortedBy { it.name }
//                _products.value = temp
//            }
//        }
//    }
//
//    fun setCategory(category: String) {
//        _currentCategory.value = category
//    }
//}

class ProductViewModel : ViewModel() {
    private val _currentCategory = MutableLiveData<String>("All")
    val currentCategory: LiveData<String> = _currentCategory

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    val currentCategoryMenu: LiveData<List<Product>> = _currentCategory.switchMap { category ->
        MutableLiveData<List<Product>>().apply {
            viewModelScope.launch {
                try {
                    val fetchedProducts = if (category == "All") {
                        App.retrofitService.getAllProducts()
                    } else {
                        App.retrofitService.getProductsByCategory(category)
                    }
                    val sortedProducts = fetchedProducts.sortedBy { it.name }
                    _products.postValue(sortedProducts)
                    postValue(sortedProducts)
                } catch (e: Exception) {
                    Log.e("ProductViewModel", "Error fetching products: ${e.message}")
                    postValue(emptyList())
                }
            }
        }
    }

    fun setCategory(category: String) {
        _currentCategory.value = category
    }
}