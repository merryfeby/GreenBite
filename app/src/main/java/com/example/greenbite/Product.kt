package com.example.greenbite

import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Product(
    @PrimaryKey(autoGenerate = false)
    val productID: Int,
    var name: String,
    var categoryID: Int,
    var price: String,
    var rating: Double,
    var description: String,
    var img_url: String?,
    var fat: Int,
    var calorie: Int?,
    var protein: Int,
    var deleted_at: String?,
    var created_at: String,
    var updated_at: String,
    val category: Category,
    val total_rating :Int?,
)