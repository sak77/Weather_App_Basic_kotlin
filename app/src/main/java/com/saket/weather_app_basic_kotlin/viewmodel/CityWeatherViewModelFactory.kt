package com.saket.weather_app_basic_kotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saket.weather_app_basic_kotlin.repository.CityWeatherRepository

class CityWeatherViewModelFactory : ViewModelProvider.Factory {

    //Using delegate viewModels in Kotlin, we do not need this method....
/*
    fun getViewModel(activity: FragmentActivity): CityWeatherViewModel {
        return ViewModelProvider(activity, this).get(CityWeatherViewModel::class.java)
    }
*/
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CityWeatherViewModel::class.java)) {
            return CityWeatherViewModel(CityWeatherRepository()) as T
        }
        throw IllegalArgumentException("Unkown viewmodel class")
    }

}