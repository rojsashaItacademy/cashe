package com.trinitydigital.network

import android.app.Application
import androidx.room.Room
import com.trinitydigital.network.data.model.local.AppDataBase

class WeatherApp : Application() {

    private var db: AppDataBase? = null

    override fun onCreate() {
        super.onCreate()

        app = this

        db = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java, DB_NAME
        )
            .allowMainThreadQueries()
            .build()
    }

    fun getDb() = db

    companion object {
        private var app: WeatherApp? = null

        fun getApp() = app

        private const val DB_NAME = "MY_DB"
    }
}