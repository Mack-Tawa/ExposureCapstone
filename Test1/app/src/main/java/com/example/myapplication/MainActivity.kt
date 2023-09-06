package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.work.WorkerParameters
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.work.CoroutineWorker
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY


class MainActivity : AppCompatActivity() {

    private lateinit var textView : TextView
    private lateinit var latTextView: TextView
    private lateinit var lonTextView: TextView
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var button: Button
    private var currentLocation: Location? = null
    lateinit var locationManager: LocationManager
    lateinit var locationByGps: Location
    lateinit var locationByNetwork: Location






    @SuppressLint("MissingPermission")

    private fun requestPermissions() {
        var permissionId = 2
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }

    private fun hasPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }



    @SuppressLint("MissingPermission")
    fun getNewLocation() {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

//        fusedLocationProviderClient.lastLocation
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {location : Location? ->
            latTextView = findViewById(R.id.latTextView)
            lonTextView = findViewById(R.id.lonValueTextView)
            latTextView.text = location!!.latitude.toString()
            lonTextView.text = location!!.longitude.toString()
        }
    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    textView = findViewById(R.id.test)
                    textView.text = "location has been setup"
                    Log.d("PermissionGranted", "inside permissiosn")
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // feature requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            }
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)


//        val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//        val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
//        val gpsLocationListener: LocationListener = object : LocationListener {
//            override fun onLocationChanged(location: Location) {
//                var locationByGps = location
//            }
//
//            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
//            override fun onProviderEnabled(provider: String) {}
//            override fun onProviderDisabled(provider: String) {}
//        }
////------------------------------------------------------//
//        val networkLocationListener: LocationListener = object : LocationListener {
//            override fun onLocationChanged(location: Location) {
//                locationByNetwork= location
//            }
//
//            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
//            override fun onProviderEnabled(provider: String) {}
//            override fun onProviderDisabled(provider: String) {}
//        }
//
//        val lastKnownLocationByGps =
//            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//        lastKnownLocationByGps?.let {
//            var locationByGps = lastKnownLocationByGps
//        }
////------------------------------------------------------//
//        val lastKnownLocationByNetwork =
//            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
//        lastKnownLocationByNetwork?.let {
//            var locationByNetwork = lastKnownLocationByNetwork
//        }
////------------------------------------------------------//
//        if (locationByGps != null && locationByNetwork != null) {
//            if (locationByGps.accuracy > locationByNetwork!!.accuracy) {
//                currentLocation = locationByGps
//                var latitude = currentLocation!!.latitude
//                var longitude = currentLocation!!.longitude
//                // use latitude and longitude as per your need
//            } else {
//                currentLocation = locationByNetwork
//               var latitude = currentLocation!!.latitude
//               var longitude = currentLocation!!.longitude
//                // use latitude and longitude as per your need
//            }
//        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

//        fusedLocationProviderClient.lastLocation
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {location : Location? ->
            latTextView = findViewById(R.id.latTextView)
            lonTextView = findViewById(R.id.lonValueTextView)
            latTextView.text = location!!.latitude.toString()
            lonTextView.text = location!!.longitude.toString()
        }
    }
    fun onClick(view: View) {
        Log.d("test", "idk")
    }
}