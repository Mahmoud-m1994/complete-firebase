package com.example.complete_firebase.navigation

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.complete_firebase.screens.LoginScreen
import com.example.complete_firebase.screens.MainScreen
import com.example.complete_firebase.screens.SplashScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MyNavigation(firebaseAuth: FirebaseAuth) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SplashScreen.screenName) {
        composable(route = Screen.SplashScreen.screenName) {
            SplashScreen(navController = navController, auth = firebaseAuth)
        }
        composable(route = Screen.LoginScreen.screenName) {
            LoginScreen(navController = navController, auth = firebaseAuth)
        }
        composable(route = Screen.MainScreen.screenName) {
            MainScreen(navController = navController, auth = firebaseAuth)

            BackHandler(enabled = true) {
                Log.d("TAG", "MyNavigation: Back pressed")
            }
        }
    }
}