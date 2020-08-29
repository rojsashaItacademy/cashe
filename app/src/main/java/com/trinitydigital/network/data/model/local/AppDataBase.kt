package com.trinitydigital.network.data.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.trinitydigital.network.data.model.model.forecast.*

@Database(
    entities = [CityModel::class, Coord::class, ForecastItem::class,
        ForecastMain::class, ForecastModel::class], version = 1, exportSchema = false
)
@TypeConverters(value = [TypeConverter::class])
abstract class AppDataBase : RoomDatabase() {
    abstract fun getDao(): WeatherDao
}