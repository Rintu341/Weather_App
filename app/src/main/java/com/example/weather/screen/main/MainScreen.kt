package com.example.weather.screen.main

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.example.weather.MainActivity
import com.example.weather.permissions.location.LocationUtils
import com.example.weather.permissions.location.LocationViewModel
import com.example.weather.screen.search.WeatherViewModel

@Composable
fun MainScreen(navController: NavController,
               weatherViewModel: WeatherViewModel,
               context: Context,
               locationUtils: LocationUtils,
               locationViewModel: LocationViewModel
)
{
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
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Button(onClick = {
            if(locationUtils.hasLocationPermission())
            {
                //I have access the location
                
            }else{
                // ask for permission
                    requestPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    )
            }
        }) {
            Text("location")
        }
    }
}