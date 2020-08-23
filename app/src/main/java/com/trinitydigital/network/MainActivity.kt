package com.trinitydigital.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.trinitydigital.network.data.RetrofitBuilder
import com.trinitydigital.network.data.RvAdapter
import com.trinitydigital.network.data.model.current.CurrentWeather
import com.trinitydigital.network.data.model.forecast.ForecastModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val adapter = RvAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.adapter = adapter

        btnGo.setOnClickListener {
            forecastWeather(etCity.text.toString())
        }


//        RetrofitBuilder.getService()
//            ?.getWeather("Bishkek", getString(R.string.api_key), "metric")
//            ?.enqueue(object : Callback<CurrentWeather> {
//                override fun onResponse(
//                    call: Call<CurrentWeather>,
//                    response: Response<CurrentWeather>
//                ) {
//                    if (response.isSuccessful && response.body() != null) {
//                        val data = response.body()
//                        weather.text = data?.wind?.speed.toString()
//                    }
//                }
//
//                override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
//                    Log.d("adasdasd", "asdsadasdasd")
//                }
//            })


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
}