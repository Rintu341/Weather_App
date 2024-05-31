package com.example.weather.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weather.screen.search.SearchScreen
import com.example.weather.screen.search.WeatherViewModel
import com.example.weather.screen.main.MainScreen
import com.example.weather.screen.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation(modifier: Modifier = Modifier,weatherViewModel: WeatherViewModel) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(route = WeatherScreens.SplashScreen.name){
            WeatherSplashScreen(navController = navController)
        }
        composable(route = WeatherScreens.SearchScreen.name){
            SearchScreen(navController = navController,weatherViewModel)
        }
        composable(route = WeatherScreens.MainScreen.name){
            MainScreen(navController = navController,weatherViewModel)
        }
    }

}