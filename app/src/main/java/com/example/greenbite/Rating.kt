package com.example.greenbite

import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
class Rating(
    @PrimaryKey(autoGenerate = false)
    val ratingID: Int,
    val userID: Int,
    val productID: Int,
    var orderDetailID: Int,
    var rating: Int,
    var created_at: String,
    var updated_at: String,
)