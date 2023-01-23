package com.example.complete_firebase.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.complete_firebase.components.PlayerPicture
import com.example.complete_firebase.components.TextButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

@Composable
fun PlayerScreen(playerId: String, databaseRef: DatabaseReference, navController: NavController) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        PlayerDataFromFirebase(databaseReference = databaseRef, playerId = playerId, navController = navController)
    }
}

@Composable
private fun PlayerDataFromFirebase(
    databaseReference: DatabaseReference,
    playerId: String,
    navController: NavController
) {
    var playerImage by remember { mutableStateOf("") }
    var playerName by remember { mutableStateOf("") }
    LaunchedEffect(key1 = Unit) {
        databaseReference.child(playerId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d(TAG, "hmm: ${snapshot.value}")
                playerImage = snapshot.child("imageUrl").value.toString()
                playerName = snapshot.child("name").value.toString()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: ${error.message}")
            }
        })
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        PlayerPicture(
            imageUrl = playerImage,
            playerName = playerName,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .size(200.dp)
        )
        Text(text = playerName)
        Spacer(modifier = Modifier
            .height(16.dp)
            .fillMaxWidth())
        UpdatePlayerName(
            databaseReference = databaseReference,
            playerId = playerId,
            playerName = playerName,
            navController = navController
        )
    }
}

@Composable
private fun UpdatePlayerName(
    databaseReference: DatabaseReference,
    playerId: String,
    playerName: String,
    navController: NavController,
) {
    var updatedPlayerName by remember { mutableStateOf(playerName) }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        Text(text = "Update player name")
        OutlinedTextField(
            value = playerName,
            onValueChange = { updatedPlayerName = it },
            label = { Text(text = "Player name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        )
        if (updatedPlayerName != playerName && updatedPlayerName.isNotBlank()) {
            databaseReference.child(playerId).child("name").setValue(updatedPlayerName).addOnSuccessListener {
                Log.d(TAG, "onSuccess: ")
            }.addOnFailureListener {
                Log.d(TAG, "onFailure: ")
            }
        }

        TextButton(
            text = "Delete player",
            fontSize = 16,
            onClick = {
                databaseReference.child(playerId).removeValue().addOnSuccessListener {
                    Log.d(TAG, "onSuccess: ")
                    navController.popBackStack()
                }.addOnFailureListener {
                    Log.d(TAG, "onFailure: ")
                }
            },
            textColor = Color.Red
        )
    }
}