package com.example.weather

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weather.navigation.WeatherNavigation
import com.example.weather.permissions.location.LocationUtils
import com.example.weather.screen.search.WeatherViewModel
import com.example.weather.ui.theme.WeatherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val locationUtils = LocationUtils(context)
            val weatherViewModel: WeatherViewModel = hiltViewModel()
            WeatherApp(weatherViewModel = weatherViewModel,
                context = context,
                locationUtils = locationUtils)
        }
    }
}

@Composable
fun WeatherApp(modifier: Modifier = Modifier,
               weatherViewModel: WeatherViewModel,
               context: Context,
               locationUtils: LocationUtils) {
    WeatherTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherNavigation(weatherViewModel = weatherViewModel,
                    context,
                    locationUtils)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherTheme {

    }
}