package com.example.greenbite

import retrofit2.http.GET
import retrofit2.http.Path

data class TopMenu(
    val id_menu:Int,
    val name_menu: String,
    val price_menu:Int,
    val image_menu:String,
    val category_menu:String,
    val rating_menu:Int,
    val totalrating_menu:Int,
)

interface WebService {
    @GET("topmenus")
    suspend fun getTopMenus(): List<TopMenu>

    @GET("products")
    suspend fun getAllProducts(): List<Product>

    @GET("products/category/name/{categoryName}")
    suspend fun getProductsByCategory(@Path("categoryName") category: String): List<Product>
}