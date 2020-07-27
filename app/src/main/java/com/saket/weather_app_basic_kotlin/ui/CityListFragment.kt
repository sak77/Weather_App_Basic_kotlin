package com.saket.weather_app_basic_kotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.saket.weather_app_basic_kotlin.R
import com.saket.weather_app_basic_kotlin.databinding.FragmentCitylistBinding
import com.saket.weather_app_basic_kotlin.viewmodel.CityWeatherViewModel
import com.saket.weather_app_basic_kotlin.viewmodel.CityWeatherViewModelFactory
import java.util.function.Consumer

/**
 * Fragment to display list of cities and some basic weather info.
 */
class CityListFragment : Fragment() {

    private val TAG = "CityListFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCitylistBinding.inflate(inflater, container, false)
        //Set lifecycle owner for this binding - why is this required?
        binding.lifecycleOwner = this

        //
        //val cityViewModel = activity?.let { CityWeatherViewModelFactory().getViewModel(it) }

        val cityViewModel: CityWeatherViewModel by viewModels()

        //Update binding to set city list recyclerview's adapter
        //Using consumer to handle city click events...
        val cityListAdapter = CityListAdapter(Consumer {
            city -> city.let {
            //Log.v(TAG, "City Clicked - ${city.cityName}")
            //Handle fragment navigation view viewmodel
            cityViewModel.navigateToWeatherDetails(city)
        }
        })
        //navigate to weather detail fragment if current selected city is not null
        cityViewModel?.liveCurrentSelectedCity?.observe(viewLifecycleOwner, Observer {
            city -> city?.let {
            //Navigate to weather detail fragment
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, WeatherDetailFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
        })

        binding.cityList.adapter = cityListAdapter

        cityViewModel?.liveCityList?.observe(viewLifecycleOwner, Observer { city_list ->
            city_list.let {
                //Update city list - Note - since data is not coming from RoomDB, need to
                //provide a new list instance for listadapter to work..
                //cityListAdapter.submitList(cityList)
                cityListAdapter.submitList(city_list.toList())
            }
        })

        //If viewmodel's shouldFetchCityInfo is true then make the API request...
        cityViewModel?.shouldFetchCityInfo?.observe(viewLifecycleOwner, Observer {
            shouldFetchCityInfo ->
            run {
                if (shouldFetchCityInfo) {
                    //Prevent API being re-invoked on device rotation...
                    if (savedInstanceState == null) {
                        cityViewModel?.getCityWeatherDetails()
                    }
                }
            }
        })
        return binding.root
    }
}