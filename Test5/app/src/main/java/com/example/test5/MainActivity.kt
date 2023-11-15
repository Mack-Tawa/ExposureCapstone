package com.example.test5

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test5.ui.theme.Test5Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.lang.Exception

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

                        var latText by remember { mutableStateOf("No Latitude Data") }
                        var longText by remember { mutableStateOf("No Longitude Data") }

                        Text(text = latText)
                        Text(text = longText)





                        Button(onClick = {



                            Log.i("myTests", "it worked")



                            vm.startGPSWork()

                        }) {
                            Text(text = "Click to Start GPS Tracking")

                        }

                        Button(onClick = {
                            lifecycleScope.launch {

                                val coords =
                                    GPSDatabase.getDatabase(applicationContext).GPSDao().latestGPS()

                                Log.i("getLatestGPS", coords.toString())
                                coords.collect{data: GPSData ->
                                    run {
                                        latText = data.latitude.toString()
                                        longText = data.longitude.toString()
                                        Log.i("getLatestGPS", "latitude..."+data.latitude.toString())
                                        Log.i("getLatestGPS", "longitude..." + data.longitude.toString())
                                    }
                                }
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

//        coords.toList()
        coords.collectLatest { data: GPSData ->
            tempLat = data.latitude
            tempLong = data.longitude
        }

        Log.i("getLastGPS", "made it after the collectLatest")




        Log.i("getLastGPS", "after last converesion")


//        runBlocking {
//            coords.collect {
//
//                tempLat = it.latitude
//                tempLong = it.longitude
//                Log.i("ShowLastGPS", "latitude is...." + it.latitude.toString())
//                Log.i(
//                    "ShowLastGPS",
//                    "longitdue is...." + it.longitude.toString()
//                )
//            }
//        }
        return@coroutineScope coords
    }

}


