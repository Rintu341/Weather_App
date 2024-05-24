package com.example.weather.data

//It manage the state of the data we fetch from Api
data class DataOrException<T,Boolean,E:Exception>(
    var data:T? = null, /* Weather_Response */
    var loading:Boolean? = null, /* loading  */
    var e:E? = null /* Exception */
)
