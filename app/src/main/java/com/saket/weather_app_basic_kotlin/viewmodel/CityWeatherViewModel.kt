package com.saket.weather_app_basic_kotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saket.weather_app_basic_kotlin.model.City
import com.saket.weather_app_basic_kotlin.model.WeatherInfo
import com.saket.weather_app_basic_kotlin.repository.CityWeatherRepository

/**
 * Viewmodel class survives some configuration changes of an activity/fragment. Hence,
 * they can be used to persist data across configuration changes that may happen due to device
 * rotation etc. Also, they are good place to store any logic related to fragment navigation..
 */
class CityWeatherViewModel(val cityWeatherRepository: CityWeatherRepository) : ViewModel() {

    private val city_list = ArrayList<City>()

    //Backing property for livedata of city list
    private val _live_city_list = MutableLiveData<List<City>>()

    //Live data is exposed to the calling class so its value can only be read but not be set..
    val live_city_list : LiveData<List<City>>
    get() = _live_city_list

    //Current setected city - backing property
    //stores current city selected by user to view its weather details
    //also used to navigate to weather details fragment...
    private val _current_selected_city = MutableLiveData<City>()
    //Public livedata for current selected city
    val live_current_selected_city : LiveData<City>
    get() = _current_selected_city

    fun getCityWeatherDetails() {
        //List of city names
        val city_names = listOf("Gothenburg", "Stockholm")

        city_names.forEach { cityName ->
            //Passing anonymous method (Consumer) for cityUpdateCallback {}.
            //Remember Lambda argument should be moved out of parenthesis
            cityWeatherRepository.getCityInfo(cityName) { city: City ->
                city.let {
                    //Create new city instance
                    val currCity = City(cityName = city.cityName, woeid = city.woeid)
                    //Now call city weather info
                    getCityWeatherInfo(currCity)
                }
            }
        }
    }

    //Passing anonymous method (Consumer) for weatherUpdateCallback
    private fun getCityWeatherInfo(city: City) {
        cityWeatherRepository.getCityWeatherInfo(city.woeid) { weatherInfo: WeatherInfo ->
            weatherInfo.let {
                city.weatherInfo = weatherInfo
                //Add city to the list of cities
                city_list.add(city)
                //Update live data value
                _live_city_list.value = city_list
            }
        }
    }

    fun navigateToWeatherDetails(city: City) {
        _current_selected_city.value = city
    }
}