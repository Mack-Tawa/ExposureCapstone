package com.example.test5

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigate() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "HomeScreen") {
        composable(route = "HomeScreen") {
//            HomeScreenFragment(navController)
        }
        composable(route = "PastLocationsScreen") {
//            PastLocationsFragment(navController)
        }
    }
}