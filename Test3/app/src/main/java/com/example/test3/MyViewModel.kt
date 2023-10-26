package com.example.test3

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit


class MyViewModel(application:Application): ViewModel() {


//
//    val allWeather: LiveData<List<GPSData>> = repository.allLocations

    private val workManager = WorkManager.getInstance(application)

    private val myLiveData = MutableLiveData<GPSData>()

//    internal val outputWorkInfos: LiveData<List<WorkInfo>> = workManager.getWorkInfosByTagLiveData("OUTPUT")


    fun startGPSWork() {
        var continuation = PeriodicWorkRequestBuilder<GPSWorker>(3, TimeUnit.SECONDS)
                .setConstraints(
                    Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .build()
                )
                .build()
        workManager.enqueueUniquePeriodicWork(
            "getBackgroundLocation",
            ExistingPeriodicWorkPolicy.KEEP,
            continuation
        )

        workManager.getWorkInfoById(continuation.id)
//        repository.checkWeather(city)
    }

    fun showLastGPS() {

        Log.i("showLastGPS", "we in here")
    }

    fun loadDataFromWorker(applicationContext: Context?) {


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