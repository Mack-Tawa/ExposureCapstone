package com.example.test5

import android.content.Context
import android.util.Log
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Date


class GPSRepository(val scope: CoroutineScope, val dao: GPSDAO) {

    //    val currentPainting = dao.latestPainting().asLiveData()
    val allCoords = dao.allRecentLocations().asLiveData()
    suspend fun addPainting(latitude: Double, longitude: Double) {
        scope.launch{
            dao.addGPSData(GPSData(System.currentTimeMillis(), latitude, longitude))
        }
        Log.d("PaintingRepository", "in addPainting")
    }

}