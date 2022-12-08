package com.example.complete_firebase.screens

import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.complete_firebase.R
import com.example.complete_firebase.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, auth: FirebaseAuth) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    // AnimationEffect
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        delay(1000L)
        if (auth.currentUser != null) {
            navController.navigate(Screen.MainScreen.getArg())
            Log.d(TAG, "SplashScreen: ${auth.currentUser?.email}")
        } else navController.navigate(Screen.LoginScreen.getArg())
    }

    // Image
    Column(verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,)
    {
        Image(
            painter = painterResource(id = R.drawable.firebase),
            contentDescription = "Firebase App",
            modifier = Modifier
                .fillMaxSize()
                .scale(scale.value)
        )
    }
}
