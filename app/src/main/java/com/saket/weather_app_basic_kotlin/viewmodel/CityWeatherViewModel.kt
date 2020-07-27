package com.saket.weather_app_basic_kotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.saket.weather_app_basic_kotlin.model.City
import com.saket.weather_app_basic_kotlin.model.WeatherInfo
import com.saket.weather_app_basic_kotlin.repository.CityWeatherRepository

/**
 * Viewmodel class survives some configuration changes of an activity/fragment. Hence,
 * they can be used to persist data across configuration changes that may happen due to device
 * rotation etc. Also, they are good place to store any logic related to fragment navigation..
 */
class CityWeatherViewModel(private val cityWeatherRepository: CityWeatherRepository) : ViewModel() {

    private val cityList = ArrayList<City>()

    //Backing property for livedata of city list
    private val _liveCityList = MutableLiveData<List<City>>()

    //Live data is exposed to the calling class so its value can only be read but not be set..
    val liveCityList: LiveData<List<City>>
        get() = _liveCityList

    //Current selected city - backing property
    //stores current city selected by user to view its weather details
    //also used to navigate to weather details fragment...
    private val _currentSelectedCity = MutableLiveData<City?>()

    //Public livedata for current selected city
    val liveCurrentSelectedCity: LiveData<City?>
        get() = _currentSelectedCity

    //We also add a last selected city to determine if user has navigated back from
    // WeatherDetailsFragment to CityListFragment
    private var lastSelectedCity: City? = null

    //LiveData which tracks if we need to make API request to fetch City Info data..
    val shouldFetchCityInfo: LiveData<Boolean> = Transformations.map(liveCurrentSelectedCity) { currentCity ->
        //User lands on city list for first time - yes
        if (lastSelectedCity == null && currentCity == null)
            true
        else {
            //For any other case simply update previous city but do not fetch data
            lastSelectedCity = currentCity
            false
        }
    }

    init {
        _currentSelectedCity.value = null
    }

    fun getCityWeatherDetails() {
        //List of city names
        val cityNames = listOf("Gothenburg", "Stockholm")

        cityNames.forEach { cityName ->
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
                cityList.add(city)
                //Update live data value
                _liveCityList.value = cityList
            }
        }
    }

    fun navigateToWeatherDetails(city: City?) {
        _currentSelectedCity.value = city
    }
}