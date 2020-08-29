package com.trinitydigital.network.ui

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.trinitydigital.network.R
import com.trinitydigital.network.WeatherApp
import com.trinitydigital.network.data.model.remote.RetrofitBuilder
import com.trinitydigital.network.data.model.model.current.CurrentWeather
import com.trinitydigital.network.data.model.model.forecast.ForecastModel
import com.trinitydigital.network.utils.ConnectionUtils
import com.trinitydigital.network.utils.PermissionUtils
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val adapter = RvAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycerView.adapter = adapter

        val isHasNetwork = ConnectionUtils.isNetworkAvailable(this)

        forecastWeather("Bishkek")

        if (!isHasNetwork) {
            showShackBar()
        }

        if (PermissionUtils.checkLocationPermission(this)) {
            loadLocation()
        }

        WeatherApp.getApp()?.getDb()?.getDao()?.getAll()?.observe(this, {
            if (it.isNotEmpty()) {
                val item = it.first()
                adapter.update(item.list)
            }
        })
    }

    private fun forecastWeather(city: String) {
        RetrofitBuilder.getService()?.forecast(city, getString(R.string.api_key), "metric")
            ?.enqueue(object : Callback<ForecastModel> {
                override fun onResponse(
                    call: Call<ForecastModel>,
                    response: Response<ForecastModel>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        response.body()?.let {
                            WeatherApp.getApp()?.getDb()?.getDao()?.addForecast(it)
                        }
                    }
                }

                override fun onFailure(call: Call<ForecastModel>, t: Throwable) {
                    Log.d("adasdasd", "asdsadasdasd")
                }

            })
    }


    @SuppressLint("MissingPermission")
    private fun loadLocation() {
        val fpc =
            LocationServices.getFusedLocationProviderClient(applicationContext)

        fpc.lastLocation.addOnSuccessListener {
            loadByLocation(it)
        }.addOnFailureListener {
            Log.d("asdsadasdas", "asdasdasdasd")
        }
    }

    fun loadByLocation(location: Location) {
        RetrofitBuilder.getService()?.getWeatherByCoordinates(
            location.latitude.toString(), location.longitude.toString(),
            getString(R.string.api_key), "metric"
        )?.enqueue(object : Callback<CurrentWeather> {
            override fun onResponse(
                call: Call<CurrentWeather>,
                response: Response<CurrentWeather>
            ) {


                val city = response.body()?.name
                val temp = response.body()?.main?.temp
                val cloud = response.body()?.clouds?.all // %


                Toast.makeText(applicationContext, response.body()?.name, Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Toast.makeText(applicationContext, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionUtils.LOCATION_REQUEST_CODE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                loadLocation()
            }
        }
    }

    private fun showShackBar() {
        Snackbar.make(parentLayout, "ASdasdasdasd", Snackbar.LENGTH_INDEFINITE)
            .setAction("обновить") {
                if (!ConnectionUtils.isNetworkAvailable(this)) {
                    showShackBar()
                }
            }.show()
    }
}