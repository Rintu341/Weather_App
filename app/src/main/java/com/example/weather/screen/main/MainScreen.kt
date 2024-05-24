package com.example.weather.screen.main

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController, weatherViewModel: WeatherViewModel) {
    Text("MainScreen ${weatherViewModel.data.value.data?.current?.cloud}")
    Log.d("weather","${weatherViewModel.data.value.data}")

}