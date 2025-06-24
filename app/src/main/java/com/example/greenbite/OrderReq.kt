package com.example.greenbite.checker

import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderReq(
    @PrimaryKey(autoGenerate = true)
    val orderID: Int,
    val userID: Int,
    val customer_name: String,
    val customer_phone: String,
    val subtotal: Double,
    val shipping_fee: Double,
    val total: Double,
    val status: String,
    val prep_time: Int,
    val created_at: String,
    val updated_at: String,
    val order_details: List<OrderDetailReq>
)