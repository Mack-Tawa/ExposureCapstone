package com.example.test5

import android.Manifest
import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test5.ui.theme.Test5Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import java.util.concurrent.TimeUnit


class MainActivity : ComponentActivity() {

    val url = "http://10.0.2.2:8080/getAQI"




    @SuppressLint("CoroutineCreationDuringComposition")
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

                        var listText by remember { mutableStateOf(("")) }
                        var latText by remember { mutableStateOf("") }
                        var longText by remember { mutableStateOf("") }

                        Text(text = latText)
                        Text(text = longText)








                        Button(onClick = {


                            Log.i("myTests", "it worked")



                            vm.startGPSWork()

                        }) {
                            Text(text = "Click to Start GPS Tracking")

                        }

                        Button(onClick = {

//                            try {
//                                lifecycleScope.launch {
//                                    GPSDatabase.getDatabase(applicationContext).GPSDao().latestGPS()
//                                }
//                            }catch(e : Exception) {
//                                e.printStackTrace()
//                            }

                            lifecycleScope.launch {

                                val coords = GPSDatabase.getDatabase(applicationContext).GPSDao().latestGPS()

                                Log.i("getLatestGPS", coords.toString())
                                coords.collect { data: GPSData ->
                                    run {
                                        latText = data.latitude.toString()
                                        longText = data.longitude.toString()
//                                        Log.i(
//                                            "getLatestGPS",
//                                            "latitude..." + data.latitude.toString()
//                                        )
//                                        Log.i(
//                                            "getLatestGPS",
//                                            "longitude..." + data.longitude.toString()
//                                        )
                                    }
                                }
                            }

                            Log.i("ShowLastGPS", "done")


                        }) {
                            Text("ShowLastGPS")
                        }
                        Button(onClick = {
                            var tempLat = ""
                            var tempLon = ""
                            lifecycleScope.launch {
                                var userCoordinates = getLatestGPS()
                                tempLat = userCoordinates[0]
                                tempLon = userCoordinates[1]
                                Log.i("getAQI", "giving latitude...${tempLat}")
                                Log.i("getAQI", "giving longitude...${tempLon}")
                                //sendRequest("40.7139467", "-111.9719233")
                                getAQI(tempLat, tempLon)


                            }

                        }) {
                            Text(text = "Get AQI From Last Position")

                        }
                        Button(onClick = {
                            Log.i(
                                "showAll",
                                "**********************inside showAll**********************\nshow all has been clicked"
                            )

                            val coordsList = GPSDatabase.getDatabase(applicationContext).GPSDao()
                                .allRecentLocations()
                            val simpleDateFormat = SimpleDateFormat("MM/dd/yyyy/H:m::s")

                            lifecycleScope.launch {
                                coordsList.collect { data: List<GPSData> ->
                                    Log.i("showAll", "size of retrieved list is...${data.size}")
                                    for (datum in data) {
                                        val dateString = simpleDateFormat.format(datum.timestamp)

                                        Log.i(
                                            "showAll",
                                            "data is...${datum.latitude}\n${datum.longitude}\n$dateString"
                                        )
                                        listText += "\nLatitude...${datum.latitude}\nLongitude...${datum.longitude}\nTime...${dateString}"
                                        listText += "\n--------------------------"
                                    }
                                }


                            }
                            Log.i("showAll", "list contains...\n${coordsList.toString()}")

                        }) {
                            Text(text = "Show All Previous Locations")

                        }
                        Text(text = listText,
                            modifier = Modifier
                            .verticalScroll(rememberScrollState()))

                    }
                }
            }
        }
    }

    private fun getAQI(latitude: String, longitude: String) {

        // setup client
        val client = OkHttpClient().newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS).build()

        //setup request
        val request = Request.Builder()
            .url("$url?latitude=$latitude&longitude=$longitude")
            .build()

        //call the request
        client.newCall(request).enqueue(object : Callback {
            //throw if fail
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            //work if response is received
            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful)
                        throw IOException("Unexpected code $response")

                    for ((name, value) in response.headers) {
                        Log.i("flask stuff", ("name is...$name: value is...$value"))
                    }
                    val result = response.body!!.string()
                    Log.i("flask stuff", "result is...$result")
                    // looper needed to access ui thread to Toast
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
                    }
                    // if I uncomment the line below it breaks. I don't know why.
                    //Log.i("flask stuff", "THIS body is..." + (response.body!!.string()))

                }
            }
        })
    }
    suspend fun getLatestGPS(): ArrayList<String> {

    var coordinates = ArrayList<String>()
    var test: Boolean = lifecycleScope.launch {

        val coords = GPSDatabase.getDatabase(applicationContext).GPSDao().latestGPS()


        Log.i("getLatestGPSFun", coords.toString())
        coords.collect { data: GPSData ->
            run {
                //add to arrayList
                coordinates.add(data.latitude.toString())
                coordinates.add(data.longitude.toString())
                Log.i("getLatestGPSFun", "coordinates array size should be 2??? and it's...${coordinates.size}")
                Log.i("getLatestGPSFun", "coord[0]is...${coordinates[0]}")
                Log.i("getLatestGPSFun", "coord[1]is...${coordinates[1]}")


                Log.i(
                    "getLatestGPSFun",
                    "latitude..." + data.latitude.toString()
                )
                Log.i(
                    "getLatestGPSFun",
                    "longitude..." + data.longitude.toString()
                )
            }
        }
    }.isCompleted
    Log.i("getLatestGPSFun", "before delay isCompleted is...${test}")
    // needed this to wait for the last coroutine to finish
    delay(1000)
    Log.i("getLatestGPSFun", "after delay isCompleted is...${test}")

    Log.i("getLatestGPSFun", "done")
    Log.i("getLatestGPSFun", "size of result array is...${coordinates.size}")

    return coordinates

    }

}


