package com.example.weather.screen.search


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weather.component.BottomSection
import com.example.weather.component.TopSection
import com.example.weather.data.WeatherState

@Composable
fun SearchScreen(navController: NavController, weatherViewModel: WeatherViewModel) {
        var location by remember { mutableStateOf("") }
        val weatherState by weatherViewModel.weatherState.collectAsState()
        var isShowLoading by remember{
                mutableStateOf(false)
        }
        MaterialTheme {
                Column(modifier = Modifier
                        .background(
                                Brush.verticalGradient(
                                        colors = listOf(
                                                Color(0xFF24BBCE),
                                                Color(0xFF4B19B1)
                                        )
                                )
                        )
                        .padding(16.dp)
                        .fillMaxSize()
                ){
                        Column (
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                        ){
                                OutlinedTextField(
                                        value = location,
                                        onValueChange = {value->
                                                location = value
                                        },
//                                        label = {Text("Enter location") },
                                        placeholder = {Text("Enter location")},
                                        modifier = Modifier.fillMaxWidth(),
                                        maxLines = 1,
                                        leadingIcon = {
                                                Icon(imageVector = Icons.Filled.LocationOn, contentDescription = null)
                                        },
                                        trailingIcon = {
                                                IconButton(onClick = {
                                                        if (location.isNotEmpty()) {
                                                                weatherViewModel.fetchWeather(
                                                                        location
                                                                )
                                                                isShowLoading = true
                                                        }
                                                }) {
                                                        Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                                                }
                                        },
                                        shape = RoundedCornerShape(50)
                                )
                                /*
//                                Button(
//                                        onClick = {
//                                                if(location.isNotEmpty()) {
//                                                        weatherViewModel.fetchWeather(location)
//                                                        isShowLoading = true
//                                                }
//                                        },
//                                        modifier = Modifier.padding(top = 8.dp)
//                                ) {
//                                        Text("Search")
//                                }
                                 */

                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Column (
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceBetween,
                                horizontalAlignment = Alignment.CenterHorizontally
                        ){
                                when (val state = weatherState) {
                                        is WeatherState.Loading -> {
                                                if(isShowLoading)
                                                        CircularProgressIndicator()
                                        }
                                        is WeatherState.Success -> {
                                                TopSection(temp_c = state.weather.current.temp_c.toInt(),
                                                        text = state.weather.current.condition.text,
                                                        icon = ("https:"+state.weather.current.condition.icon).replace("64x64", newValue = "128x128")
                                                )
                                                Spacer(modifier = Modifier.height(30.dp))
                                                BottomSection(state = state.weather)


                                                isShowLoading = false
                                        }
                                        is WeatherState.Error -> {
                                                Text(
                                                        text = "Error: ${state.exception.message}",
                                                        modifier = Modifier.padding(top = 8.dp)
                                                )
                                                isShowLoading = false
                                        }
                                }
                        }

                }
        }
}