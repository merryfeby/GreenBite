package com.example.greenbite.checker

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderDetailReq(
    val orderDetailID: Int,
    val productID: Int,
    val product_name: String,
    val quantity: Int,
    val price: Double,
    val total: Int,
    val addons: String
)