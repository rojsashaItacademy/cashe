package com.trinitydigital.network.data.model.remote

import com.trinitydigital.network.data.model.model.current.CurrentWeather
import com.trinitydigital.network.data.model.model.forecast.ForecastModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("data/2.5/weather")
    fun getWeather(
        @Query("q") city: String,
        @Query("appid") appId: String,
        @Query("units") units: String
    ): Call<CurrentWeather>

    @GET("data/2.5/weather")
    fun getWeatherByCoordinates(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appId: String,
        @Query("units") units: String
    ): Call<CurrentWeather>

    @GET("data/2.5/forecast")
    fun forecast(
        @Query("q") city: String,
        @Query("appid") appId: String,
        @Query("units") units: String
    ): Call<ForecastModel>
}
