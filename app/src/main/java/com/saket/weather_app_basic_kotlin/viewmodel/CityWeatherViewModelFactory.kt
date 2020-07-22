package com.saket.weather_app_basic_kotlin.viewmodel

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saket.weather_app_basic_kotlin.repository.CityWeatherRepository
import java.lang.IllegalArgumentException

class CityWeatherViewModelFactory : ViewModelProvider.Factory {

    fun getViewModel(activity: FragmentActivity): CityWeatherViewModel {
        return ViewModelProvider(activity, this).get(CityWeatherViewModel::class.java)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CityWeatherViewModel::class.java) ) {
            return CityWeatherViewModel(CityWeatherRepository()) as T
        }
        throw IllegalArgumentException("Unkown viewmodel class")
    }

}