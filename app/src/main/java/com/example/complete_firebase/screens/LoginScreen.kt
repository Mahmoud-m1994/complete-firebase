package com.example.complete_firebase.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.complete_firebase.R
import com.example.complete_firebase.components.PrimaryButton
import com.example.complete_firebase.components.TextButton
import com.example.complete_firebase.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException

const val TAG = "LoginScreen"
@Composable
fun LoginScreen(navController: NavController, auth: FirebaseAuth) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        Spacer(modifier = Modifier.height(32.dp))
        Icon(
            painter = painterResource(id = R.drawable.firebase),
            contentDescription = "App logg",
            tint = Color(0xFFD7901C),
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Username") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            modifier = Modifier.fillMaxWidth()
        )
        PrimaryButton(
            text = stringResource(R.string.sign_in),
            icon = Icons.Rounded.Person,
            onClick = {
                login(username, password, auth, navController)
            }
        )

        PrimaryButton(
            text = stringResource(R.string.sign_up_with_google),
            onClick = {
                Log.d(TAG, "sign up with google clicked")
            }
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = stringResource(R.string.sign_up_text))
            TextButton(
                text = stringResource(R.string.sign_up),
                fontSize = 16,
                textColor = Color.Blue,
                onClick = {
                    signUpUser(username, password, auth, navController)
                }
            )
        }

        TextButton(
            text = stringResource(R.string.forgot_password),
            fontSize = 16,
            textColor = Color.Red,
            onClick = {
                // navController.navigate(Screen.SignUpScreen.route)
            }
        )
    }
}

// Sign up user
private fun signUpUser(
    email: String,
    password: String,
    auth: FirebaseAuth,
    navController: NavController,
) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(com.example.complete_firebase.TAG, "createUserWithEmailAndPassword:success")
            } else {
                Log.w(com.example.complete_firebase.TAG, "createUserWithEmailAndPassword:failure", task.exception)
                if (task.exception is FirebaseAuthUserCollisionException) {
                    Log.d(com.example.complete_firebase.TAG, "User already exists")
                }
            }
        }
}

// Sign in user
private fun login(
    email: String,
    password: String,
    auth: FirebaseAuth,
    navController: NavController,
){
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "login: $task")
                navController.navigate(Screen.MainScreen.getArg())
                navController.currentDestination?.let {
                    navController.graph.remove(navController.currentDestination!!)
                }
            } else {
                Log.w(TAG, "signInWithEmailAndPassword:failure", task.exception)
            }
        }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController(), auth = FirebaseAuth.getInstance())
}