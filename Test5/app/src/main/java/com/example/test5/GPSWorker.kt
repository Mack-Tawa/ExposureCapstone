package com.example.test5

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import kotlinx.coroutines.runBlocking

class GPSWorker(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {
    private val context: Context = appContext
    private var outputLocationData = workDataOf("lat" to "error", "lon" to "error")


    @SuppressLint("MissingPermission")
    override fun doWork(): Result {

        val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        Log.d("testest", "inside Worker doWork")

        fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, object : CancellationToken() {
            override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token

            override fun isCancellationRequested() = false
        })
            .addOnSuccessListener { location: Location? ->
                if (location == null)
                    Log.d("GPSWorker", "location is null uh oh")
                else {
                    Log.d("GPSWorker", "location is not null!")

                    var lat = location.latitude
                    var lon = location.longitude
                    Log.d("GPSWorker", "lat is...$lat\n lon is...$lon")
                    val outputLocationData = workDataOf("lat" to lat, "lon" to lon)
//                    val tempData = GPSData(System.currentTimeMillis(), lat, lon)
//                    runBlocking {
//                        GPSDatabase.getDatabase(context).GPSDao().addGPSData(tempData)
//                        Log.i("mydatabasehelp", "it ran potentially idk lets see")
//                    }

                    //Todo what is application context in worker thread






                }
            }
            .addOnFailureListener {
                Log.d("GPSWorker", "GPS Listener failed")
            }
        return Result.success(outputLocationData)
    }
}