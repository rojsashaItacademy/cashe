package com.trinitydigital.network.data.model.model.forecast

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityModel(
    @PrimaryKey
    val id: Int,
    val name: String,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Int,
    val sunset: Int,
    val coord: Coord
)