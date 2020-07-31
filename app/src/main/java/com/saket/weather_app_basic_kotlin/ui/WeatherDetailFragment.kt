package com.saket.weather_app_basic_kotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.saket.weather_app_basic_kotlin.databinding.FragmentWeatherDetailsBinding
import com.saket.weather_app_basic_kotlin.viewmodel.CityWeatherViewModel
import com.saket.weather_app_basic_kotlin.viewmodel.CityWeatherViewModelFactory

/**
Created by sshriwas on 2020-07-24
 */
class WeatherDetailFragment : Fragment() {

    //private lateinit var cityViewModel : CityWeatherViewModel
    private val cityViewModel: CityWeatherViewModel by activityViewModels { CityWeatherViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWeatherDetailsBinding.inflate(inflater, container, false)
        //We pass the activity as the viewmodel store container here so that the same viewmodel is used
        //across both CityListFragment and WeatherDetailsFragment
        //Old way to get instance of CityViewModel, instead we should use Kotlin delegates viewModels as shown above...
        //cityViewModel = activity?.let { CityWeatherViewModelFactory().getViewModel(it) }!!

        binding.lifecycleOwner = this   //Set binding lifecycleowner if binding with liveData..
        binding.city = cityViewModel.liveCurrentSelectedCity.value
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        //Reset selected city to null
        cityViewModel.navigateToWeatherDetails(null)
    }
}