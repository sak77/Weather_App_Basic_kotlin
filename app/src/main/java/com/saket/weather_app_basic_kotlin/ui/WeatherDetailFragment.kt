package com.saket.weather_app_basic_kotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.saket.weather_app_basic_kotlin.databinding.FragmentWeatherDetailsBinding
import com.saket.weather_app_basic_kotlin.viewmodel.CityWeatherViewModel
import com.saket.weather_app_basic_kotlin.viewmodel.CityWeatherViewModelFactory

/**
Created by sshriwas on 2020-07-24
 */
class WeatherDetailFragment : Fragment() {

    private lateinit var cityViewModel : CityWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWeatherDetailsBinding.inflate(inflater, container, false)
        //We pass the activity as the viewmodel store container here so that the same viewmodel is used
        //across both CityListFragment and WeatherDetailsFragment
        cityViewModel = activity?.let { CityWeatherViewModelFactory().getViewModel(it) }!!
        binding.lifecycleOwner = this   //Why is this required??
        val current_City = cityViewModel?.liveCurrentSelectedCity?.value
        binding.city = current_City
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        //Update selected city to null
        cityViewModel.navigateToWeatherDetails(null)
    }
}