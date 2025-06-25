package com.example.greenbite.checker

import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderDetail(
    @PrimaryKey(autoGenerate = true)
    val orderDetailID: Int,
    val productID: Int,
    val product_name: String,
    val quantity: Int,
    val price: Double,
    val addon: String? = null,
    val total: Int,
    val addons: List<OrderAddon>
)