package com.example.test5

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test5.ui.theme.Test5Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            0
        )

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION), 0
        )





        setContent {
            Test5Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val vm = viewModel {
                        MyViewModelFactory(this@MainActivity.application).create(MyViewModel::class.java)
                    }

                    Column(

                    ) {


                        Button(onClick = {


//                            vm.loadDataFromWorker(applicationContext)

                            val testLat: Double = 44.0
                            val testLon: Double = 44.0

                            var testData = GPSData(System.currentTimeMillis(), testLat, testLon)

                            lifecycleScope.launch {
                                withContext(Dispatchers.IO) {
                                    GPSDatabase.getDatabase(applicationContext).GPSDao()
                                        .addGPSData(testData)
                                }
                            }



                            Log.i("myTests", "it worked")


                            vm.startGPSWork()

//                            WorkManager.

//                        vm.startGPSWork()
//                            Log.i("after startGPSWork", "after startGPSWork")
//
//                            val constraints: Constraints = Constraints.Builder()
//                                .setRequiresCharging(false)
//                                .build()
//                            val periodicWorkRequest: PeriodicWorkRequest = PeriodicWorkRequest.Builder(
//                                GPSWorker::class.java, 3, TimeUnit.SECONDS
//                            )
//                                .setConstraints(constraints)
//                                .build()
//
//
//                            val sendLogsWorkRequest =
//                                PeriodicWorkRequestBuilder<GPSWorker>(3, TimeUnit.SECONDS)
//                                    .setConstraints(Constraints.Builder()
//                                        .setRequiresBatteryNotLow(true)
//                                        .build()
//                                    )
//                                    .build()
//                            Log.d("macktivity", "after request build")
//                            WorkManager.getInstance().enqueueUniquePeriodicWork(
//                                "getBackgroundLocation",
//                                ExistingPeriodicWorkPolicy.KEEP,
//                                sendLogsWorkRequest
//                            )
//                            Log.d("macktivity", "after Workmanager")
                        }) {
                            Text(text = "Click to Start GPS Tracking")

                        }

                        Button(onClick = {

                            runBlocking {
                                var coords = getLastGPS()
                            }
                            Log.i("ShowLastGPS", "done")


                        }) {
                            Text("ShowLastGPS")
                        }

                    }
                }
            }
        }
    }

    suspend fun getLastGPS() = coroutineScope {

        var tempLat: Double = -999.0
        var tempLong: Double = -999.0

//        launch {


        val coords =
            GPSDatabase.getDatabase(applicationContext).GPSDao().latestGPS()
//
//        Log.i("getLastGPS", "latitude is..." + coords.last().latitude.toString())
//        Log.i("getLastGPS", "longitude is..." + coords.last().longitude.toString())

            runBlocking {
                coords.collect {

                    tempLat = it.latitude
                    tempLong = it.longitude
                    Log.i("ShowLastGPS", "latitude is...." + it.latitude.toString())
                    Log.i(
                        "ShowLastGPS",
                        "longitdue is...." + it.longitude.toString()
                    )
                }
            }
        val result: GPSData = GPSData(System.currentTimeMillis(), tempLat, tempLong)
        return@coroutineScope result
    }
}


