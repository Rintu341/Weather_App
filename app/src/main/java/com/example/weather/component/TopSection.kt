package com.example.weather.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter

@Composable
fun TopSection(modifier: Modifier = Modifier,
               temp_c: Int = 31,
               text:String = "Partly cloudy",
               icon:String = "//cdn.weatherapi.com/weather/64x64/day/116.png"
) {
    val painter = rememberAsyncImagePainter(model = icon)

    Surface(
        modifier = Modifier
            .padding(all = 8.dp)
            .height(200.dp)
            .fillMaxWidth()
            .background(Color.Transparent),
        border = BorderStroke(1.dp, color = Color.LightGray),
        shape = RoundedCornerShape(10),

    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        )
        {
            Text(text = "$temp_c°C",
                fontSize = 56.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.ExtraBold
            )
            Box (
                modifier = Modifier.height(100.dp)
                    .width(100.dp)
            ){
                AsyncImage(
                    model = icon,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )
            }
            Text(text = text ,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )



        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShowHighLowTemperaturePreView() {
    TopSection()
}