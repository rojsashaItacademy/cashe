package com.trinitydigital.network.data.model.model.forecast

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Coord(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val lat: Double,
    val lon: Double
)
