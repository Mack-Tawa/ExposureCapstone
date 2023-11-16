package com.example.test6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.test6.ui.theme.Test6Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//            ),
//            0
//        )
//
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION), 0
//        )

        super.onCreate(savedInstanceState)
        setContent {
            Test6Theme {
                // A surface container using the 'background' color from the theme

            }
        }
    }
}
