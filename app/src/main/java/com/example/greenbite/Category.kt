package com.example.greenbite

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Category(
    val categoryID: Int,
    val name: String,
    val description: String
)