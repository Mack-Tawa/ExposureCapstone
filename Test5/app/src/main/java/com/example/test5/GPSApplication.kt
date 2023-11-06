package com.example.test5

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class GPSApplication: Application() {

    val scope = CoroutineScope(SupervisorJob())
    val _db by lazy {GPSDatabase.getDatabase(applicationContext)}

}