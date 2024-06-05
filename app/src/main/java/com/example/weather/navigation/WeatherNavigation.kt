package com.example.weather.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weather.network.networkState.ConnectivityObserver
import com.example.weather.permissions.location.LocationUtils
import com.example.weather.permissions.location.LocationViewModel
import com.example.weather.screen.main.MainScreen
import com.example.weather.screen.search.SearchScreen
import com.example.weather.screen.search.WeatherViewModel
import com.example.weather.screen.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation(
                      weatherViewModel: WeatherViewModel,
                      context: Context,
                      locationUtils: LocationUtils,
                      networkState: ConnectivityObserver.Status
) {

    val navController = rememberNavController()
    val locationViewModel = viewModel<LocationViewModel>()

    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(route = WeatherScreens.SplashScreen.name){
            WeatherSplashScreen(navController = navController)
        }
        composable(route = WeatherScreens.SearchScreen.name){
            SearchScreen(navController = navController,weatherViewModel)
        }
        composable(route = WeatherScreens.MainScreen.name){
            MainScreen(navController = navController,
                weatherViewModel,
                context,
                locationUtils,
                locationViewModel,
                networkState)
        }
    }

}