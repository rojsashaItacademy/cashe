package com.trinitydigital.network.data.model.model.forecast

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ForecastModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val cod: String,
    val message: Int,
    val cnt: Int,
    val city: CityModel,
    val list: List<ForecastItem>
)