package com.example.complete_firebase.navigation

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.complete_firebase.screens.AddPlayerScreen
import com.example.complete_firebase.screens.ForgotPasswordScreen
import com.example.complete_firebase.screens.LoginScreen
import com.example.complete_firebase.screens.MainScreen
import com.example.complete_firebase.screens.PlayerScreen
import com.example.complete_firebase.screens.SplashScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

@Composable
fun MyNavigation(firebaseAuth: FirebaseAuth, databaseReference: DatabaseReference) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.screenName,
    ) {
        composable(route = Screen.SplashScreen.screenName) {
            SplashScreen(navController = navController, auth = firebaseAuth)
        }
        composable(route = Screen.LoginScreen.screenName) {
            LoginScreen(navController = navController, auth = firebaseAuth)
        }
        composable(route = Screen.MainScreen.screenName) {
            MainScreen(navController = navController, auth = firebaseAuth, databaseRef = databaseReference)

            BackHandler(enabled = true) {
                Log.d("TAG", "MyNavigation: Back pressed")
            }
        }
        composable(route = Screen.ForgotPasswordScreen.screenName) {
            ForgotPasswordScreen(navController = navController, auth = firebaseAuth)
        }
        composable(route = Screen.PlayerScreen.screenName + "/{playerId}") {
            val playerId = it.arguments?.getString("playerId")
            playerId?.let { id ->
                PlayerScreen(playerId = id, databaseRef = databaseReference, navController = navController)
            }
        }
        composable(route = Screen.AddPlayerScreen.screenName) {
            AddPlayerScreen(databaseRef = databaseReference, navController = navController)
        }
    }
}