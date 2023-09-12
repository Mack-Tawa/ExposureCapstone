package com.example.test2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {
    LocationManager locationManager;

    final static String[] PERMISSIONS = {android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION};
    final static int PERMISSIONS_ALL = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(PERMISSIONS, PERMISSIONS_ALL);
        }
        else {
            requestLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Handler timingHandler = new Handler();
            timingHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    requestLocation();
                    timingHandler.postDelayed(this, 5000);
                }
            }, 1000);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Log.d("onLocationChanged", "latitude is..." + location.getLatitude() + "\nlongitude is..." + location.getLongitude());
        Toast.makeText(this, "Location Received. Lat..." + location.getLatitude() + " Lon..." + location.getLongitude(), Toast.LENGTH_SHORT).show();
        locationManager.removeUpdates(this);
    }

    public void requestLocation() {
        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 20, this);
            }
        }
    }
}