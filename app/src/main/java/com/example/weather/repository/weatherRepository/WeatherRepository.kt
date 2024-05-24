package com.example.weather.repository.weatherRepository

import android.util.Log
import com.example.weather.data.DataOrException
import com.example.weather.model.WeatherResponse
import com.example.weather.network.WeatherApi
import com.example.weather.utils.Constants.API_KEY
import javax.inject.Inject


class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi
){
    private  var dataOrException =
        DataOrException<WeatherResponse,Boolean,Exception>()
    suspend fun getCurrentWeather(location :String) : DataOrException<WeatherResponse,Boolean,Exception>
    {
        try {
            dataOrException.loading = true
            dataOrException.data = weatherApi.getCurrentWeather(location = location)

            if(dataOrException.data.toString().isNotEmpty())
            {
                dataOrException.loading = false
            }
        }catch (exception:Exception)
        {
            dataOrException.e = exception
            Log.d("TAG","getQuestions : ${exception.toString()}")
        }
        return dataOrException
    }
}