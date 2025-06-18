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

    private val _selectedProduct = MutableLiveData<Product?>()
    val selectedProduct: LiveData<Product?> = _selectedProduct

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

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

//    fun fetchProductDetail(productID: Int) {
//        viewModelScope.launch {
//            _isLoading.value = true
//            val product = App.retrofitService.getProductById(productID)
//            _selectedProduct.value = product
//            _isLoading.value = false
//        }
//    }
    fun fetchProductDetail(productID: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val product = App.retrofitService.getProductById(productID)
                _selectedProduct.value = product
            } catch (e: Exception) {
                // Log the error
                Log.e("ProductViewModel", "Error fetching product: ${e.message}", e)

                // You can also set an error state to display to the user
                // _errorMessage.value = "Product not found or connection error"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearSelectedProduct() {
        _selectedProduct.value = null
    }
}


