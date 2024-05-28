package com.example.weather.screen.main


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weather.data.WeatherState

@Composable
fun MainScreen(navController: NavController, weatherViewModel: WeatherViewModel) {
        var location by remember { mutableStateOf("") }
        val weatherState by weatherViewModel.weatherState.collectAsState()

        MaterialTheme {
                Column(modifier = Modifier.padding(16.dp)) {
                        OutlinedTextField(
                                value = location,
                                onValueChange = { location = it },
                                label = {Text("Enter location") },
                                modifier = Modifier.fillMaxWidth()
                        )
                        Button(
                                onClick = { weatherViewModel.fetchWeather(location) },
                                modifier = Modifier.padding(top = 8.dp)
                        ) {
                                Text("Search")
                        }
                        when (val state = weatherState) {
                                is WeatherState.Loading -> {
                                        CircularProgressIndicator()
                                }
                                is WeatherState.Success -> {
                                        Text(text = "Temperature: ${state.weather.current.temp_c}°C",
                                                modifier = Modifier.padding(top = 8.dp))
                                }
                                is WeatherState.Error -> {
                                        Text(
                                                text = "Error: ${state.exception.message}",
                                                modifier = Modifier.padding(top = 8.dp)
                                        )
                                }
                        }
                }
        }

}