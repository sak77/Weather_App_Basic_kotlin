package com.saket.weather_app_basic_kotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
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

        //Old way to get viewmodel instance via viewmodel factory...
        //val cityViewModel = activity?.let { CityWeatherViewModelFactory().getViewModel(it) }

        //Instead now we use kotlin delegates viewModels (or activityViewModels in this case, since
        // we need a common viewmodel to share data between both fragments.)
        val cityViewModel: CityWeatherViewModel by activityViewModels { CityWeatherViewModelFactory() }

        //Using consumer to handle city click events...
        val cityListAdapter = CityListAdapter(Consumer { city ->
            city.let {
                //Set current selected city in the viewmodel
                cityViewModel.navigateToWeatherDetails(city)
            }
        })
        //navigate to weather detail fragment if current selected city is not null
        cityViewModel.liveCurrentSelectedCity.observe(viewLifecycleOwner, Observer { city ->
            city?.let {
                //Navigate to weather detail fragment - OLD WAY
                /*
                    val transaction = fragmentManager?.beginTransaction()
                    transaction?.replace(R.id.fragment_container, WeatherDetailFragment())
                    transaction?.addToBackStack(null)
                    transaction?.commit()
                    */

                //Instead now we use Android KTX Module - Fragment KTX which provides kotlin extension
                //to navigate using fragmentManager class...
                parentFragmentManager.commit {
                    replace(R.id.fragment_container, WeatherDetailFragment())
                    addToBackStack(null)
                }
            }
        })

        //Update binding to set city list recyclerview's adapter
        binding.cityList.adapter = cityListAdapter

        cityViewModel.liveCityList.observe(viewLifecycleOwner, Observer { city_list ->
            city_list.let {
                //Update city list - Note - since data is not coming from RoomDB, need to
                //pass a new list instance for listadapter to work..
                //cityListAdapter.submitList(cityList)  //won't work
                cityListAdapter.submitList(city_list.toList())
            }
        })

        //If viewmodel's shouldFetchCityInfo is true then make the API request...
        cityViewModel.shouldFetchCityInfo.observe(
            viewLifecycleOwner,
            Observer { shouldFetchCityInfo ->
                run {
                    if (shouldFetchCityInfo) {
                        //Prevent API being re-invoked on device rotation...
                        if (savedInstanceState == null) {
                            cityViewModel.getCityWeatherDetails()
                        }
                    }
                }
            })
        return binding.root
    }
}