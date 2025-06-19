//package com.example.greenbite
//
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.switchMap
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.launch
//
//class ProductViewModel : ViewModel() {
//    private val _activeProduct = MutableLiveData<Product>()
//    val activeProduct: LiveData<Product>
//        get() = _activeProduct
//    private val _products = MutableLiveData<List<Product>>()
//    val products: LiveData<List<Product>>
//        get() = _products
//
//
//    private val _currentCategory = MutableLiveData<String>("All")
//    val currentCategory: LiveData<String> = _currentCategory
//
//    private val _currentCategoryMenu = MutableLiveData<List<Product>>()
//    val currentCategoryMenu: LiveData<List<Product>> = _currentCategoryMenu
//
//    private val _selectedProduct = MutableLiveData<Product?>()
//    val selectedProduct: LiveData<Product?> = _selectedProduct
//
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading
//
//    private val _topMenus = MutableLiveData<List<Product>>()
//    val topMenus: LiveData<List<Product>> = _topMenus
//
//    init {
//        fetchProducts("All")
//
//    }
//    fun setActiveProduct(product: Product) {
//        _activeProduct.value = product
//    }
//
//    private fun fetchTopMenus() {
//        viewModelScope.launch {
//            App.retrofitService.getTopMenus()
//        }
//    }
//
//    fun setCategory(category: String) {
//        if (_currentCategory.value != category) {
//            _currentCategory.value = category
//            fetchProducts(category)
//        }
//    }
//
//    suspend fun getProducts() {
//        _products.value = App.retrofitService.getAllProducts()
//    }
//
//    suspend fun getTopMenus() {
//        _topMenus.value = App.retrofitService.getTopMenus()
//    }
//
//    private fun fetchProducts(category: String) {
//        viewModelScope.launch {
//            val fetchedProducts = if (category == "All") {
//                App.retrofitService.getAllProducts()
//            } else {
//                App.retrofitService.getProductsByCategory(category)
//            }
//            val sorted = fetchedProducts.sortedBy { it.name }
//            _currentCategoryMenu.value = sorted
//        }
//    }
//
//    //BUAT ADMIN
//    //ambil data buat admin edit
//    fun deleteProduct(productID: Int){
//        viewModelScope.launch {
//            App.retrofitService.deleteProduct(productID)
//        }
//    }
//    fun addProduct(name: String, desc: String, price: String, categoryId: Int, fat: Int, protein: Int, calories: Int) {
//        viewModelScope.launch {
//            val newProduct = Product(
//                productID = 0,
//                name = name,
//                description = desc,
//                price = price,
//                categoryID = categoryId,
//                rating = 0.0,
//                img_url = null,
//                fat = fat,
//                protein = protein,
//                calories = calories,
//                deleted_at = null,
//                created_at = "",
//                updated_at = "",
//                category = Category(
//                    categoryID = 99,
//                    name = "none",
//                    description = "none"
//                ),
//                total_rating = 0
//            )
//            App.retrofitService.addProduct(newProduct)
//        }
//    }
//    fun updateProduct(productID: Int, name: String, desc: String, price: String) {
//        viewModelScope.launch {
//            val newProduct = Product(
//                productID = productID,
//                name = name,
//                description = desc,
//                price = price,
//                categoryID = 1,
//                rating = 0.0,
//                img_url = null,
//                fat = 0,
//                protein = 0,
//                calories = 0,
//                deleted_at = null,
//                created_at = "",
//                updated_at = "",
//                category = Category(
//                    categoryID = 99,
//                    name = "none",
//                    description = "none"
//                ),
//                total_rating = 0
//            )
//            viewModelScope.launch {
//                App.retrofitService.updateProduct(productID, newProduct)
//            }
//        }
//    }
//
//
//    fun fetchProductDetail(productID: Int) {
//        viewModelScope.launch {
//            _isLoading.value = true
//            try {
//                val product = App.retrofitService.getProductById(productID)
//                _selectedProduct.value = product
//            } catch (e: Exception) {
//                Log.e("ProductViewModel", "Error fetching product: ${e.message}", e)
//
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
//
//    fun clearSelectedProduct() {
//        _selectedProduct.value = null
//    }
//}
//
//
package com.example.greenbite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val _activeProduct = MutableLiveData<Product>()
    val activeProduct: LiveData<Product>
        get() = _activeProduct
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

    private val _currentCategory = MutableLiveData<String>("All")
    val currentCategory: LiveData<String> = _currentCategory

    private val _currentCategoryMenu = MutableLiveData<List<Product>>()
    val currentCategoryMenu: LiveData<List<Product>> = _currentCategoryMenu

    private val _selectedProduct = MutableLiveData<Product?>()
    val selectedProduct: LiveData<Product?> = _selectedProduct

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _topMenus = MutableLiveData<List<Product>>()
    val topMenus: LiveData<List<Product>> = _topMenus

    init {
        fetchProducts("All")
        fetchTopMenus()
    }

    fun setActiveProduct(product: Product) {
        _activeProduct.value = product
    }

    fun fetchTopMenus() {
        viewModelScope.launch {
            try {
                val fetchedTopMenus = App.retrofitService.getTopMenus()
                _topMenus.value = fetchedTopMenus
                Log.d("ProductViewModel", "Fetched ${fetchedTopMenus.size} top menus.")
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error fetching top menus: ${e.message}", e)
                _topMenus.value = emptyList()
            }
        }
    }

    fun setCategory(category: String) {
        if (_currentCategory.value != category) {
            _currentCategory.value = category
            fetchProducts(category)
        }
    }

    suspend fun getProducts() {
        _products.value = App.retrofitService.getAllProducts()
    }

    suspend fun getTopMenus(): List<Product> {
        return App.retrofitService.getTopMenus()
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

    // BUAT ADMIN
    fun deleteProduct(productID: Int) {
        viewModelScope.launch {
            App.retrofitService.deleteProduct(productID)
        }
    }

    fun addProduct(name: String, desc: String, price: String, categoryId: Int, fat: Int, protein: Int, calories: Int) {
        viewModelScope.launch {
            val newProduct = Product(
                productID = 0,
                name = name,
                description = desc,
                price = price,
                categoryID = categoryId,
                rating = 0.0,
                img_url = null,
                fat = fat,
                protein = protein,
                calories = calories,
                deleted_at = null,
                created_at = "",
                updated_at = "",
                category = Category(categoryID = 99, name = "none", description = "none"),
                total_rating = 0
            )
            App.retrofitService.addProduct(newProduct)
        }
    }

    fun updateProduct(productID: Int, name: String, desc: String, price: String) {
        viewModelScope.launch {
            val newProduct = Product(
                productID = productID,
                name = name,
                description = desc,
                price = price,
                categoryID = 1,
                rating = 0.0,
                img_url = null,
                fat = 0,
                protein = 0,
                calories = 0,
                deleted_at = null,
                created_at = "",
                updated_at = "",
                category = Category(categoryID = 99, name = "none", description = "none"),
                total_rating = 0
            )
            viewModelScope.launch {
                App.retrofitService.updateProduct(productID, newProduct)
            }
        }
    }

    fun fetchProductDetail(productID: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val product = App.retrofitService.getProductById(productID)
                _selectedProduct.value = product
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error fetching product: ${e.message}", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearSelectedProduct() {
        _selectedProduct.value = null
    }
}