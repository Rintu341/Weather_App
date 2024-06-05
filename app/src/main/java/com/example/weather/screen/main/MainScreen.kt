package com.example.weather.screen.main

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.example.weather.MainActivity
import com.example.weather.R
import com.example.weather.component.BottomSection
import com.example.weather.component.TopSection
import com.example.weather.data.WeatherState
import com.example.weather.navigation.WeatherScreens
import com.example.weather.network.networkState.ConnectivityObserver
import com.example.weather.permissions.location.LocationUtils
import com.example.weather.permissions.location.LocationViewModel
import com.example.weather.screen.search.WeatherViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController,
               weatherViewModel: WeatherViewModel,
               context: Context,
               locationUtils: LocationUtils,
               locationViewModel: LocationViewModel,
               networkState: ConnectivityObserver.Status
)
{
    val weatherState by weatherViewModel.weatherState.collectAsState()
    val location = locationViewModel.location.value
    val address = location?.let { locationUtils.getAddressFromLatLng(it) }

    var isOffLine by remember {
        mutableStateOf(false)
    }
    var isShowLoading by remember{
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    // it start an Activity for getting the result
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        // after getting the result check user grant the permissions or not
        if(permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
            &&
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        ) {
            // I have access to location
                locationUtils.requestLocationUpdates(viewModel = locationViewModel)
        }else{
            // ask for permission
            // I should rationalize why app need permission
            val rationalRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                context as MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) || ActivityCompat.shouldShowRequestPermissionRationale(
                context ,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )

            if(rationalRequired)
            {
                Toast.makeText(context,
                    "Location permission is require for getting current location weather",
                    Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context,
                    "Location permission is required go to setting go to setting to grant location permission",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(stringResource(id = R.string.app_name))
            },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(WeatherScreens.SearchScreen.name)
                    }) {
                        Icon(imageVector = Icons.Filled.Search,
                            contentDescription = "Search")
                    }
                })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if(networkState == ConnectivityObserver.Status.Available) {
                    if (locationUtils.hasLocationPermission()) {
                        //I have access the location
                        locationUtils.requestLocationUpdates(locationViewModel)
                    } else {
                        // ask for permission
                        requestPermissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            )
                        )
                    }
                    isOffLine = false
                }else{
                    isOffLine = true
                }
                weatherViewModel.fetchWeather(
                    address.toString()
                )
            }) {
                Icon(imageVector = Icons.Filled.Refresh, contentDescription = "refresh")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,


    ) {
        Column(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF24BBCE),
                            Color(0xFF4B19B1)
                        )
                    )
                )
                .fillMaxSize()
                .padding(it)
                .padding(start = 16.dp,end = 16.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            if(isOffLine)
            {
                Text("You are currently Offline")
            }else{
                // Show the UI
                when(location)
                {
                    null ->{
                        Text("location not available")
                    }
                    else->{
                        LaunchedEffect(Unit) {
                            coroutineScope.launch {
                                weatherViewModel.fetchWeather(
                                    address.toString()
                                )
                            }
                        }
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
                                Spacer(modifier = Modifier.height(10.dp))
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
    }
}