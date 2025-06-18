package com.example.greenbite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.greenbite.admin.Employee
import kotlinx.coroutines.launch
import kotlin.Int

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

    //BUAT ADMIN
    //ambil data buat admin edit
    private val _activeProduct = MutableLiveData<Product>()
    val activeProduct: LiveData<Product>
        get() = _activeProduct
    fun setActiveProduct(product: Product) {
        _activeProduct.value = product
    }

    fun deleteProduct(productID: Int){
        viewModelScope.launch {
            App.retrofitService.deleteProduct(productID)
        }
    }
    fun updateProduct(productID: Int, name: String, desc: String, price: Int) {
        viewModelScope.launch {
            val newProduct = Product(
                productID = productID,
                name = name,
                description = desc,
                price = price.toString(),
                categoryID = 1,
                rating = 0.0,
                img_url = null,
                deleted_at = null,
                created_at = "",
                updated_at = "",
                category = Category(
                    categoryID = 99,
                    name = "none",
                    description = "none"
                ),
                total_rating = 0
            )
            viewModelScope.launch {
                App.retrofitService.updateProduct(productID, newProduct)
            }
        }
    }

}


