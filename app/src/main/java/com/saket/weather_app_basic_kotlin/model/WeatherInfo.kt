package com.saket.weather_app_basic_kotlin.model

import com.squareup.moshi.Json

/**
 * Using Moshi library, you can use @Json annotation for custom fields.
 * It is similar to @Serialized used with Gson library.
 */
data class WeatherInfo(@Json(name = "weather_state_name") val weatherName: String,
                       @Json(name = "min_temp") val minTemp: String,
                       @Json(name = "max_temp") val maxTemp: String,
                       @Json(name = "wind_speed") val windspeed: String)