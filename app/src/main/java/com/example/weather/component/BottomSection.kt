package com.example.weather.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather.R
import com.example.weather.model.WeatherResponse

@Composable
fun BottomSection(modifier: Modifier = Modifier,state:WeatherResponse) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10),
//        colors = CardDefaults.cardColors(Color.Transparent),
        elevation = CardDefaults.cardElevation(10.dp),
        border = BorderStroke(1.dp, color = Color.Gray)
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                WeatherInformation(
                    key = state.current.humidity.toString()+"%",
                    value = painterResource(
                     (R.drawable.humidity_sensor)
                ))
                WeatherInformation(
                    key = state.current.feelslike_c.toString()+"°C",
                    value = painterResource(
                        (R.drawable.temperature_feels)
                    )
                )
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                WeatherInformation(key = state.current.cloud.toString()+"%",
                    value = painterResource(
                    (R.drawable.cloudy)
                ))
                WeatherInformation(key = state.current.wind_kph.toString(),
                    value = painterResource(
                        (R.drawable.windy)
                    ))
            }

        }
    }
}

@Composable
fun WeatherInformation(modifier: Modifier = Modifier, key:String, value: Painter) {
    Column (
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier = Modifier
                .height(40.dp)
                .width(40.dp),
        )
        {
            Image(painter = value,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.matchParentSize()
                )
        }
        Text(text = key ,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomSectionPreView() {
//    BottomSection()
}