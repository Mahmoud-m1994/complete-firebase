package com.example.complete_firebase.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.complete_firebase.screens.LoginScreen
import com.example.complete_firebase.screens.MainScreen

@Composable
fun MyNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.LoginScreen.screenName) {
        composable(route = Screen.LoginScreen.screenName) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.MainScreen.screenName) {
            MainScreen(navController = navController)
        }
    }
}