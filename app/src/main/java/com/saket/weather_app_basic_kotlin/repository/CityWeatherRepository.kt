package com.saket.weather_app_basic_kotlin.repository

import android.util.Log
import com.saket.weather_app_basic_kotlin.model.City
import com.saket.weather_app_basic_kotlin.model.WeatherInfo
import com.saket.weather_app_basic_kotlin.network.MetaWeatherAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A repository is basically a class that does all data operations (pull/push)
 */

private const val TAG = "CityWeatherRepository"

class CityWeatherRepository {
    private val weatherapiInstance = MetaWeatherAPI.retrofitService

    //Passing a callback function (Consumer) as a parameter to update City details
    fun getCityInfo(cityName: String, cityUpdateCallback: (city: City) -> Unit) {
        val callCityInfo = weatherapiInstance.getCityInfo(cityName)
        //Anonymous class in kotlin - object : Callback<Array<City>> {}
        callCityInfo.enqueue(object : Callback<Array<City>> {
            override fun onFailure(call: Call<Array<City>>, t: Throwable) {
                Log.v(TAG, "Get cityInfo failed : " + t.message)
            }

            override fun onResponse(call: Call<Array<City>>, response: Response<Array<City>>) {
                if (response.isSuccessful) {
                    val cityresponse = response.body()
                    //Assuming array has only one city item
                    val city = cityresponse?.get(0)
                    //Using string templates or string interpolation - ${}
                    Log.v(TAG, "Get CityInfo success :  ${city?.cityName}")
                    city?.let { cityUpdateCallback(it) }
                }
            }
        })
    }

    //Passing a callback function (Consumer) as a parameter to update City weather details
    fun getCityWeatherInfo(
        woeid: String,
        weatherUpdateCallback: (weatherInfo: WeatherInfo) -> Unit
    ) {
        val date = "2020/08/01"
        val callCityWeatherInfo = weatherapiInstance.getCityWeatherInfo(woeid, date)
        callCityWeatherInfo.enqueue(object : Callback<Array<WeatherInfo>> {
            override fun onResponse(
                call: Call<Array<WeatherInfo>>,
                response: Response<Array<WeatherInfo>>
            ) {
                if (response.isSuccessful) {
                    val arrResponse = response.body()
                    //Assuming first item is latest weather info
                    val weatherInfo = arrResponse?.get(0)
                    Log.v(TAG, "Get CityWeather Info - ${weatherInfo?.weatherName}")
                    weatherInfo?.let { weatherUpdateCallback(it) }
                }
            }

            override fun onFailure(call: Call<Array<WeatherInfo>>, t: Throwable) {
                Log.v(TAG, "Get CityWeather failed - ${t.localizedMessage}")
            }
        })
    }
}