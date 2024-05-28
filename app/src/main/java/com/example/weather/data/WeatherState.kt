package com.example.weather.data

import com.example.weather.model.WeatherResponse

//It manage the state of the data we fetch from Api
//data class DataOrException<T,Boolean,E:Exception>(
//    var data:T? = null, /* Weather_Response */
//    var loading:Boolean? = null, /* loading  */
//    var e:E? = null /* Exception */
//)
sealed class WeatherState {
    data object Loading : WeatherState()
    data class Success(val weather: WeatherResponse) : WeatherState()
    data class Error(val exception: Throwable) : WeatherState()
}