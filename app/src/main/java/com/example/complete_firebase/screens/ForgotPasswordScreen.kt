package com.example.complete_firebase.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.complete_firebase.components.PrimaryButton
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ForgotPasswordScreen(auth: FirebaseAuth, navController: NavController) {
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        var mail by remember { mutableStateOf("") }
        OutlinedTextField(
            value = mail,
            onValueChange = { mail = it },
            label = { Text(text = "Mail") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        PrimaryButton(
            text = "Reset password",
            onClick = {
                 sendPasswordResetEmail(mail, auth, context)
            },
            enabled = mail.isNotEmpty()
        )
        PrimaryButton(
            text = "Back to login",
            onClick = { navController.popBackStack() }
        )
    }
}


private fun sendPasswordResetEmail(mail: String, auth: FirebaseAuth, context: Context) : Boolean {
    var mailSent = false
    auth.sendPasswordResetEmail(mail)
        .addOnCompleteListener { task ->
            mailSent = if (task.isSuccessful) {
                Log.d(TAG, "Email sent.")
                Toast.makeText(
                    context,
                    "Email sent.",
                    Toast.LENGTH_SHORT
                ).show()
                true
            } else {
                Log.d(TAG, "Email not sent.")
                false
            }
        }
    return mailSent
}