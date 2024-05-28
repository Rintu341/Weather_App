package com.example.weather.repository.weatherRepository

import com.example.weather.model.WeatherResponse
import com.example.weather.network.WeatherApi
import javax.inject.Inject


class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi
){
    suspend fun getCurrentWeather(query: String): Result<WeatherResponse> {
        return try {
            val response = weatherApi.getCurrentWeather(location =  query)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}