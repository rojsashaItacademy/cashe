package com.trinitydigital.network.data.model.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.trinitydigital.network.data.model.model.forecast.ForecastModel

@Dao
interface WeatherDao {

    @Insert
    fun add(data: ForecastModel)

    @Query("SELECT * FROM ForecastModel")
    fun getAll(): LiveData<List<ForecastModel>>

    @Query("DELETE FROM ForecastModel")
    fun deleteAll()

    @Transaction
    fun addForecast(data: ForecastModel) {
        deleteAll()
        add(data)
    }
}