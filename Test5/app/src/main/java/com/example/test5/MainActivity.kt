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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test5.ui.theme.Test5Theme

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

        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION), 0)





        setContent {
            Test5Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val vm = viewModel { MyViewModelFactory(this@MainActivity.application).create(MyViewModel::class.java) }

                    Column(

                    ) {


                        Button(onClick = {


//                            vm.loadDataFromWorker(applicationContext)

                            val testLat: Double = 44.0
                            val testLon: Double = 44.0


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
            }
        }
    }
}

