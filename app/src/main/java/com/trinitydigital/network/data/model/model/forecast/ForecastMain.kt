package com.trinitydigital.network.data.model.model.forecast

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ForecastMain(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val temp: Double
)