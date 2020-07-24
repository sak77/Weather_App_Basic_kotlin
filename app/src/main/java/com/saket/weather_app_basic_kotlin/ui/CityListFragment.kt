package com.saket.weather_app_basic_kotlin.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.saket.weather_app_basic_kotlin.databinding.FragmentCitylistBinding
import com.saket.weather_app_basic_kotlin.model.City
import com.saket.weather_app_basic_kotlin.viewmodel.CityWeatherViewModelFactory

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
        //Set lifecycle owner for this binding - why is this important?
        binding.lifecycleOwner = this

        //
        val cityViewModel = activity?.let { CityWeatherViewModelFactory().getViewModel(it) }
        //Update binding to set city list recyclerview's adapter
        val cityListAdapter = CityListAdapter()
        binding.cityList.adapter = cityListAdapter

        cityViewModel?.live_city_list?.observe(viewLifecycleOwner, Observer { city_list ->
            city_list.let {
                val cityList = ArrayList<City>()
                //cityList.add(City("My City", "1234"))
                //Update city list
                //cityListAdapter.submitList(cityList)
                cityListAdapter.submitList(city_list.toList())
            }
        })

        cityViewModel?.getCityWeatherDetails()
        return binding.root
    }
}