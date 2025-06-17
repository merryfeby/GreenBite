package com.example.greenbite.admin

import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Employee (
    @Json(name = "userID")
    val userID: Int? = null,

    @Json(name = "name")
    var name: String,

    @Json(name = "email")
    var email: String,

    @Json(name = "password")
    var password: String,

    @Json(name = "phone")
    var phone: String,

    @Json(name = "address")
    var address: String,

    @Json(name = "postcode")
    var postcode: String,

    @Json(name = "pfp_url")
    val pfp_url: String? = "-",

    @Json(name = "role")
    val role: Int = 2,

    @Json(name = "credit")
    var credit: Double = 0.0,

    @Json(name = "created_at")
    val created_at: String? = null,

    @Json(name = "updated_at")
    val updated_at: String? = null,

    @Json(name = "deleted_at")
    var deleted_at: String? = null,
)