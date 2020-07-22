package com.saket.weather_app_basic_kotlin.viewmodel

import androidx.lifecycle.ViewModel
import com.saket.weather_app_basic_kotlin.model.City
import com.saket.weather_app_basic_kotlin.model.WeatherInfo
import com.saket.weather_app_basic_kotlin.repository.CityWeatherRepository

class CityWeatherViewModel(val cityWeatherRepository: CityWeatherRepository) : ViewModel() {

    fun getCityWeatherDetails() {


        //Passing anonymous method (Consumer) for cityUpdateCallback {}.
        //Remember Lambda argument should be moved out of parenthesis
        cityWeatherRepository.getCityInfo("London") { city: City ->
            city.let {
                //Create new city instance
                val currCity = City(cityName = city.cityName, woeid = city.woeid)
                //Now call city weather info
                getCityWeatherInfo(currCity)
            }
        }
    }

    //Passing anonymous method (Consumer) for weatherUpdateCallback
    private fun getCityWeatherInfo(city: City) {
        cityWeatherRepository.getCityWeatherInfo(city.woeid){weatherInfo: WeatherInfo ->
            weatherInfo.let {
                city.weatherInfo = weatherInfo
            }
        }
    }
}