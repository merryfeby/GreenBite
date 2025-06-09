package com.example.greenbite

import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Postcode(
    @PrimaryKey(autoGenerate = false)
    val postcodeID: String,
    val latitude: Double,
    val longitude : Double
)