package com.saket.weather_app_basic_kotlin.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.saket.weather_app_basic_kotlin.model.City
import com.saket.weather_app_basic_kotlin.model.WeatherInfo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Notice how there is no WeahterAPIClient class here. Just the retrofit instance,
 * The MetaWeatherAPI interface and the object class which holds instance of the retrofit
 * for MetaWeatherAPI service.
 *
 * This is because in Kotlin, functions are first class members. Which means they do not have
 * to be enclosed in a class.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//Get retrofit instance
private val retrofitInstance = Retrofit.Builder()
    .baseUrl("https://www.metaweather.com/api/")
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    //.addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

//Meta Weather API client
interface Metaweatherservice {
    @GET("location/search/")
    fun getCityInfo(@Query("query") cityName: String): Call<Array<City>>

    @GET("location/{woeid}/{date}/")
    fun getCityWeatherInfo(
        @Path("woeid") woeid: String,
        @Path("date") date: String
    ): Call<Array<WeatherInfo>>
}

//Object class is a singleton class in kotlin
object MetaWeatherAPI {
    //by lazy is used for late initialization. The val is initialized first time it is called.
    //Subsequent calls will return the same instance. It is similar to lateinit used for var.
    val retrofitService: Metaweatherservice by lazy {
        retrofitInstance.create(Metaweatherservice::class.java)
    }
}

