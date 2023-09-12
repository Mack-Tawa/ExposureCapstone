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
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var latTextView: TextView
    private lateinit var lonTextView: TextView
    private lateinit var button: Button

    lateinit var locationByGps: Location
    lateinit var locationByNetwork: Location

    lateinit var locationManager: LocationManager

    // FusedLocationProviderClient - Main class for receiving location updates.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // LocationRequest - Requirements for the location updates, i.e.,
// how often you should receive updates, the priority, etc.
    private lateinit var locationRequest: LocationRequest

    // LocationCallback - Called when FusedLocationProviderClient
// has a new Location
    private lateinit var locationCallback: LocationCallback

    // This will store current location info
    private var currentLocation: Location? = null


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
    fun getNewLocation(fusedLocationProviderClient: FusedLocationProviderClient) {

        locationRequest = LocationRequest().apply {
            // Sets the desired interval for
            // active location updates.
            // This interval is inexact.
            interval = TimeUnit.SECONDS.toMillis(60)

            // Sets the fastest rate for active location updates.
            // This interval is exact, and your application will never
            // receive updates more frequently than this value
            fastestInterval = TimeUnit.SECONDS.toMillis(30)

            // Sets the maximum time when batched location
            // updates are delivered. Updates may be
            // delivered sooner than this interval
            maxWaitTime = TimeUnit.MINUTES.toMillis(2)

            priority = LocationRequest.PRIORITY_HIGH_ACCURACY

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    if (p0 != null) {
                        super.onLocationResult(p0)
                    }
                    p0?.lastLocation?.let {
                        currentLocation = locationByGps
                        var latitude = currentLocation!!.latitude
                        var longitude = currentLocation!!.longitude
                        // use latitude and longitude as per your need
                    } ?: {
                        Log.d("gpsError", "Location information isn't available.")
                    }
                }
            }

            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
        }





    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



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

        // ***************************This works***************************
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
//
////        fusedLocationProviderClient.lastLocation
//        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
//            latTextView = findViewById(R.id.latTextView)
//            lonTextView = findViewById(R.id.lonValueTextView)
//            latTextView.text = location!!.latitude.toString()
//            lonTextView.text = location!!.longitude.toString()
        // ************************************************************************

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getNewLocation(fusedLocationProviderClient)

        }
    }

    fun onClick(view: View) {
        Log.d("test", "idk")
    }
