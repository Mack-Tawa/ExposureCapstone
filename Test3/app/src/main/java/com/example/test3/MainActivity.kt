package com.example.test3

import android.Manifest
import android.location.Location
import android.location.LocationListener
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
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.WorkManager
import com.example.test3.ui.theme.Test3Theme


class MainActivity : ComponentActivity(), LocationListener {


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

        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION), 0)


        val workManager: WorkManager = WorkManager.getInstance(application)
        setContent {
            Test3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    val vm = viewModel { MyViewModelFactory(this@MainActivity.application).create(MyViewModel::class.java) }


                    Column(

                    ) {


                        Button(onClick = {


//                            vm.loadDataFromWorker(applicationContext)

                            val testLat: Double = 44.0
                            val testLon: Double = 44.0

                            var testData = GPSData(System.currentTimeMillis(), testLat, testLon)

//                            runBlocking {
//                                GPSDatabase.getDatabase(applicationContext).GPSDao().addGPSData(testData)
//                            }

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
                        
                        Button(onClick = { vm.showLastGPS() }) {
                            Text("ShowLastGPS")
                        }
//                    val workInfo = WorkManager.getInstance().getWorkInfosForUniqueWork("getBackgroundLocation").get()
////                    for (work in workInfo) {
//////                        Text(text = work.)
////                    }
//                    Log.d("workInfo", "work is...${workInfo.get(0)}")
                    }
                }



                // A surface container using the 'background' color from the theme

            }
        }
    }

    override fun onLocationChanged(location: Location) {
        TODO("Not yet implemented")
    }
}
