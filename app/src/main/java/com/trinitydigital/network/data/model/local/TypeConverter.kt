package com.trinitydigital.network.data.model.local

import android.text.TextUtils
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.trinitydigital.network.data.model.model.current.Weather
import com.trinitydigital.network.data.model.model.forecast.CityModel
import com.trinitydigital.network.data.model.model.forecast.Coord
import com.trinitydigital.network.data.model.model.forecast.ForecastItem
import com.trinitydigital.network.data.model.model.forecast.ForecastMain

object TypeConverter {


    //Object
    @JvmStatic
    @TypeConverter
    fun coordToString(model: Coord): String {
        return Gson().toJson(model)
    }

    @JvmStatic
    @TypeConverter
    fun coordToObject(text: String): Coord? {
        if (TextUtils.isEmpty(text))
            return null
        return Gson().fromJson(text, Coord::class.java)
    }
//
    @JvmStatic
    @TypeConverter
    fun cityToString(model: CityModel): String {
        return Gson().toJson(model)
    }

    @JvmStatic
    @TypeConverter
    fun cityToObject(text: String): CityModel? {
        if (TextUtils.isEmpty(text))
            return null
        return Gson().fromJson(text, CityModel::class.java)
    }

    @JvmStatic
    @TypeConverter
    fun forecastMainToString(model: ForecastMain): String {
        return Gson().toJson(model)
    }

    @JvmStatic
    @TypeConverter
    fun forecastMainToObject(text: String): ForecastMain? {
        if (TextUtils.isEmpty(text))
            return null
        return Gson().fromJson(text, ForecastMain::class.java)
    }

    @JvmStatic
    @TypeConverter
    fun listForecastItemToString(model: List<ForecastItem>): String {
        return Gson().toJson(model)
    }

    @JvmStatic
    @TypeConverter
    fun listForecastItemToObject(text: String?): List<ForecastItem>? {
        if (text == null) return mutableListOf()
        val typeToken = object : TypeToken<List<ForecastItem>>() {}.type
        return Gson().fromJson(text, typeToken)
    }
// //array of object
    @JvmStatic
    @TypeConverter
    fun weatherToString(model: List<Weather>): String {
        return Gson().toJson(model)
    }

    @JvmStatic
    @TypeConverter
    fun weatherToObject(text: String?): List<Weather>? {
        if (text == null) return mutableListOf()
        val typeToken = object : TypeToken<List<Weather>>() {}.type
        return Gson().fromJson(text, typeToken)
    }
}