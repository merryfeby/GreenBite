package com.example.greenbite

import androidx.room.ColumnInfo
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

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
}