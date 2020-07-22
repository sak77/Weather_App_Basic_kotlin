package com.saket.weather_app_basic_kotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.saket.weather_app_basic_kotlin.databinding.FragmentCitylistBinding
import com.saket.weather_app_basic_kotlin.model.City
import com.saket.weather_app_basic_kotlin.viewmodel.CityWeatherViewModelFactory

/**
 * Fragment to display list of cities and some basic weather info.
 */
class CityListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCitylistBinding.inflate(inflater, container, false)
        //
        val cityViewModel = activity?.let { CityWeatherViewModelFactory().getViewModel(it) }
        //cityViewModel?.getCityInfo()
        //cityViewModel?.getCityWeatherInfo()
        return binding.root
    }
}