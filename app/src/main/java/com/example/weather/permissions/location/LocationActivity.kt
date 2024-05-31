package com.example.weather.permissions.location

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import com.example.weather.MainActivity


// Display location Activity
@Composable
fun LocationActivity(
    locationUtils: LocationUtils,
    context: Context
) {
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
}