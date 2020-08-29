package com.trinitydigital.network.data.model.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

    private var service: WeatherService? = null

    fun getService(): WeatherService? {
        if (service == null)
            service = buildRetrofit()

        return service
    }

    private fun buildRetrofit(): WeatherService {
        return Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildOkHttp())
            .build()
            .create(WeatherService::class.java)
    }

    private fun buildOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
    }
}

