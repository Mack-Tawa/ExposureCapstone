package com.example.test3

import android.content.Context
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GPSRepository( val scope: CoroutineScope, val dao: GPSDAO, context: Context) {

    val currentGPS = dao.latestGPS().asLiveData()

    val allLocations = dao.allLocations().asLiveData()
    fun checkWeather(city: String){
        scope.launch {
            delay(1000) // pretend this is a slow network call
//            dao.addGPSData(
//                GPSData(Date(), city, Random.Default.nextDouble(0.0,105.0)))
        }
    }
}
