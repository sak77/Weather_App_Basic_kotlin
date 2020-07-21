package com.saket.weather_app_basic_kotlin.model

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
 * Properties defined outside the primary constructor are exculded from
 * the automatically generated functions.
 * For more details - https://kotlinlang.org/docs/reference/data-classes.html
 */
data class City(val cityName: String, val weatherInfo: WeatherInfo)