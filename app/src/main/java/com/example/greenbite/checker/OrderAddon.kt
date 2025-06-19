package com.example.greenbite.checker

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderAddon (
    val orderAddonID: Int,
    val addon_name: String
)