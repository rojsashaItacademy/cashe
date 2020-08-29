package com.trinitydigital.network.data.model.model.forecast

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.trinitydigital.network.data.model.model.current.Weather

@Entity
data class ForecastItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val dt: Int,
    val visibility: Int,
    val pop: Double,
    val dt_txt: String,
    val main: ForecastMain,
    @SerializedName("weather") val weather: List<Weather>,
)