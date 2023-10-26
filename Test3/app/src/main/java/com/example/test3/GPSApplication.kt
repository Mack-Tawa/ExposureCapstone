package com.example.test3

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class GPSApplication: Application() {

    val _scope = CoroutineScope(SupervisorJob())
    val _db by lazy {GPSDatabase.getDatabase(applicationContext)}
    val _gpsRepository by lazy {GPSRepository(_scope, _db.GPSDao(), applicationContext)}
}