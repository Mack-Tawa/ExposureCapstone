package com.example.test2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity(), LocationListener {
    var locationManager: LocationManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val serviceIntent = Intent(
            this,
            LocationService::class.java
        )
//        startService(serviceIntent)
        requestLocation()
//        if (Build.VERSION.SDK_INT >= 23) {
//            requestPermissions(PERMISSIONS, PERMISSIONS_ALL);
//        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val timingHandler = Handler()
            timingHandler.postDelayed(object : Runnable {
                override fun run() {
                    requestLocation()
                    timingHandler.postDelayed(this, 5000)
                }
            }, 1000)
        }
    }

    override fun onLocationChanged(location: Location) {
        Log.d(
            "onLocationChanged", """
     latitude is...${location.latitude}
     longitude is...${location.longitude}
     """.trimIndent()
        )
        Toast.makeText(
            this,
            "Location Received. Lat..." + location.latitude + " Lon..." + location.longitude,
            Toast.LENGTH_SHORT
        ).show()
        locationManager!!.removeUpdates(this)
    }

    fun requestLocation() {
        if (locationManager == null) {
            locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        }
        if (locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                locationManager!!.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    2000,
                    20f,
                    this
                )
            }
        }
    }

    companion object {
        val PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        const val PERMISSIONS_ALL = 1
    }
}