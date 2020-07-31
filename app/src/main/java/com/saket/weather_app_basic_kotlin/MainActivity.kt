package com.saket.weather_app_basic_kotlin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.saket.weather_app_basic_kotlin.databinding.ActivityMainBinding
import com.saket.weather_app_basic_kotlin.ui.CityListFragment

/**
 * Date started - 20 July 2020.
 * Date completed - 31 July 2020.
 * Purpose of this app is to have a simple and clean implementation of the Weather app in Kotlin.
 * All logic is in the viewmodel. No logic in the activity/fragment.
 * Functional programming - Yes
 * Rxjava2 - No
 * Retrofit - Yes
 * Viewbinding - Yes
 * Databinding - Yes
 * Binding adapter - No
 * Navigation graph - No
 * MVVM pattern - Yes
 * LiveData & ViewModel- Yes
 * Repository - Yes
 * Room DB - No
 * ListAdapter for Recyclerview - Yes
 * WorkManager API - No
 *
 */

//Note: In Java we used extends and implements keywords. But in Kotlin, there are no such keywords.
//Simply class inheritance and interface implementation are simply separated by ,
class MainActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {

    /*
     * I want to make viewbinding instance available to all methods in this class.
     * So i moved its initialization outside onCreate()
     * But since it is a val, i cannot declare it as null here and later assign it a value.
     * So, there are 2 options -
     */
    //1. Use val with kotlin delegate lazy....
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    //2. Convert binding to lateinit var and then initalize it in onCreate()
    //private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityMainBinding.inflate(layoutInflater) --only used for lateinit var binding
        setContentView(binding.root)
        //Remember to call setSupportActionBar() before setNavigationOnClickListener()
        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = "My Title"
        binding.toolbar.setNavigationIcon(android.R.drawable.ic_menu_mylocation)
        binding.toolbar.setNavigationOnClickListener {
            Log.v("MainActivity", "setNavigationOnClickListener")
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            }
        }
        supportFragmentManager.addOnBackStackChangedListener { onBackStackChanged() }
        //This prevents the fragment from being added again when device rotates..
        if (savedInstanceState == null) {
            /* OLD Way to use perform fragment transactions....
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.fragment_container, CityListFragment(), "CityList")
            transaction.commit()*/

            //Instead now we use Android KTX Module - Fragment KTX which provides kotlin extension
            //to navigate using fragmentManager class...
            supportFragmentManager.commit {
                replace(R.id.fragment_container, CityListFragment(), "CityList")
            }
        }
    }

    override fun onBackStackChanged() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            //Set home icon
            binding.toolbar.setNavigationIcon(android.R.drawable.ic_menu_mylocation)
        } else {
            //set back icon
            binding.toolbar.setNavigationIcon(android.R.drawable.ic_media_previous)
        }
    }
}