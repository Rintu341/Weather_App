package com.example.weather.screen.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.DataOrException
import com.example.weather.model.WeatherResponse
import com.example.weather.repository.weatherRepository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel(){

    val data : MutableState<DataOrException<WeatherResponse, Boolean, Exception>>
            = mutableStateOf(DataOrException(null,null,Exception("")))

    private lateinit var _location : String

    init{
        getCurrentResponse(_location)
    }
    private fun getCurrentResponse(location:String){
        viewModelScope.launch {
            data.value.loading = true
            _location = location
            data.value = weatherRepository.getCurrentWeather(location)

            if(data.value.toString().isNotEmpty())
            {
                data.value.loading = false
            }else{
                Log.d("TAG"," Exception ${data.value.e}")
            }
        }
    }
}