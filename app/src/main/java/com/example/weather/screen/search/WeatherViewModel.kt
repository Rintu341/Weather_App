package com.example.weather.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.WeatherState
import com.example.weather.repository.weatherRepository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel(){


    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val weatherState: StateFlow<WeatherState> = _weatherState

    fun fetchWeather(query: String) {
        viewModelScope.launch {
            _weatherState.value = WeatherState.Loading
            val result = weatherRepository.getCurrentWeather(query)
            _weatherState.value = result.fold(
                onSuccess = { WeatherState.Success(it) },
                onFailure = { WeatherState.Error(it) }
            )
        }
    }
}