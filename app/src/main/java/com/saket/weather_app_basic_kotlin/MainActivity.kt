package com.saket.weather_app_basic_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saket.weather_app_basic_kotlin.databinding.ActivityMainBinding
import com.saket.weather_app_basic_kotlin.ui.CityListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setTitle("My Title")
        setSupportActionBar(binding.toolbar)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, CityListFragment(), "CityList")
        transaction.commit()
    }
}