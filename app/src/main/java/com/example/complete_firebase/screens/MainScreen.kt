package com.example.complete_firebase.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.complete_firebase.components.PrimaryButton
import com.example.complete_firebase.navigation.Screen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainScreen(navController: NavController, auth: FirebaseAuth) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Welcome ${auth.currentUser?.email}",
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif
        )
        PrimaryButton(
            text = "Sign Out",
            onClick = {
                if (auth.currentUser != null) {
                    auth.signOut()
                    navController.navigate(Screen.LoginScreen.screenName)
                }
            }
        )
    }
}