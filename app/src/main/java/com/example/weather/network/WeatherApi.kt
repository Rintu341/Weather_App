package com.example.weather.network

import com.example.weather.model.WeatherResponse
import com.example.weather.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

/* getCurrentWeather
data class WeatherResponse(
    val current: Current,
    val location: Location
)
 */
@Singleton
interface WeatherApi {
    @GET(value = "current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String = API_KEY,
        @Query("q") location: String = "Kolkata"
    ): WeatherResponse
}