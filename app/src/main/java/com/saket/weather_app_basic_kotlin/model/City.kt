package com.saket.weather_app_basic_kotlin.model

import com.squareup.moshi.Json

/**
 * Data class is a container class for data.
 * Properties of data class -
 * Primary constructor needs to have atleast one parameter
 * All primary constructor parameters need to be marked as val or var
 * Data classes cannot be abstract, open, sealed or inner;
 * (before 1.1) Data classes may only implement interfaces.
 *
 * Data class come with their own set of helper methods such as
 * equals()/hashCode()
 * toString()
 * copy()
 *
 * Properties defined outside the primary constructor are excluded from
 * the automatically generated functions. They are not considered when comparing 2 objects.
 * So in below class, cityName and woeid will be used to compare 2 instances of City class
 * when using equals().
 *
 * For more details - https://kotlinlang.org/docs/reference/data-classes.html
 */
data class City(@Json(name = "title") val cityName: String,val woeid: String) {
    lateinit var weatherInfo: WeatherInfo
}