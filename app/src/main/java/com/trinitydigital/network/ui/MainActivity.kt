package com.trinitydigital.network.ui

import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.trinitydigital.network.R
import com.trinitydigital.network.data.RetrofitBuilder
import com.trinitydigital.network.data.RvAdapter
import com.trinitydigital.network.data.model.current.CurrentWeather
import com.trinitydigital.network.data.model.forecast.ForecastModel
import com.trinitydigital.network.utils.ConnectionUtils
import com.trinitydigital.network.utils.PermissionUtils
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val adapter = RvAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        formatDate()

        val isHasNetwork = ConnectionUtils.isNetworkAvailable(this)

        if (!isHasNetwork) {
            showShackBar()
        }

        if (PermissionUtils.checkLocationPermission(this)) {
            loadLocation()
        }

        RetrofitBuilder.getService()
            ?.getWeather("Bishkek", getString(R.string.api_key), "metric")
            ?.enqueue(object : Callback<CurrentWeather> {
                override fun onResponse(
                    call: Call<CurrentWeather>,
                    response: Response<CurrentWeather>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val data = response.body()
                    } else {
//                        Toast.makeText(applicationContext, " no data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
//                   Toast.makeText(applicationContext, t.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            })


    }

    private fun formatDate() {
        val sfdDay = SimpleDateFormat("d", Locale.getDefault())
        val date = Date()
        val day = sfdDay.format(date)
        tvDay.text = day
        val sfdMonth = SimpleDateFormat("MMMM\nyyyy", Locale.getDefault())
        val month = sfdMonth.format(date)
        tvMonth.text = month
    }


    private fun forecastWeather(city: String) {
        RetrofitBuilder.getService()?.forecast(city, getString(R.string.api_key), "metric")
            ?.enqueue(object : Callback<ForecastModel> {

                override fun onResponse(
                    call: Call<ForecastModel>,
                    response: Response<ForecastModel>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        adapter.update(response.body()?.list)
                    }
                }

                override fun onFailure(call: Call<ForecastModel>, t: Throwable) {
                    Log.d("adasdasd", "asdsadasdasd")
                }

            })
    }


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