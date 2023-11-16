package com.example.test5

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager

import java.util.concurrent.TimeUnit


class MyViewModel(application:Application): ViewModel() {


//
//    val allWeather: LiveData<List<GPSData>> = repository.allLocations

    private val workManager = WorkManager.getInstance(application)



//    internal val outputWorkInfos: LiveData<List<WorkInfo>> = workManager.getWorkInfosByTagLiveData("OUTPUT")


    fun startGPSWork() {
        Log.i("startGPSWork", "inside start gps work")
        var continuation = PeriodicWorkRequestBuilder<GPSWorker>(PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MICROSECONDS)
            .setConstraints(
                Constraints.Builder()
                    .build()
            )
            .build()
        workManager.enqueueUniquePeriodicWork(
            "getBackgroundLocation",
            ExistingPeriodicWorkPolicy.KEEP,
            continuation
        )

//        repository.checkWeather(city)
    }

}


class MyViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            MyViewModel(application) as T
        } else {
            throw IllegalArgumentException("pppp Unknown ViewModel class")
        }
    }
}