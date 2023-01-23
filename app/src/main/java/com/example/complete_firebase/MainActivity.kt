package com.example.complete_firebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.complete_firebase.navigation.MyNavigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

const val TAG = "MainActivity"
private lateinit var auth: FirebaseAuth

class MainActivity : ComponentActivity() {
    private val firebase = Firebase.database
    private val databaseRef = firebase.getReference("players")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White
            ) {
                MyNavigation(
                    firebaseAuth = auth,
                    databaseReference = databaseRef
                )
            }
        }
    }
}
