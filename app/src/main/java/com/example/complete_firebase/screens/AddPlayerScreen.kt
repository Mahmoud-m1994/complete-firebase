package com.example.complete_firebase.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.complete_firebase.R
import com.example.complete_firebase.components.PrimaryButton
import com.example.complete_firebase.components.TextButton
import com.example.complete_firebase.models.Player
import com.example.complete_firebase.navigation.Screen
import com.google.firebase.database.DatabaseReference

@Composable
fun AddPlayerScreen(databaseRef: DatabaseReference, navController: NavController) {
    // add player
    val playerId = databaseRef.push().key.toString()
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var nationality by remember { mutableStateOf("") }
    var currentClub by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            Text(
                text = "Add player",
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = age,
                    onValueChange = { age = it },
                    label = { Text(text = "Age") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = height,
                    onValueChange = { height = it },
                    label = { Text(text = "Height") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = nationality,
                    onValueChange = { nationality = it },
                    label = { Text(text = "Nationality") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = currentClub,
                    onValueChange = { currentClub = it },
                    label = { Text(text = "Current club") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text(text = "Image url") },
                    modifier = Modifier.fillMaxWidth()
                )

                // button to add player to firebase real time database
                PrimaryButton(
                    text = "Add player",
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 12.dp)
                        .fillMaxWidth(),
                    enabled = name.isNotEmpty() && age.isNotBlank() && height.isNotBlank() && nationality.isNotEmpty()
                            && currentClub.isNotEmpty() && imageUrl.isNotEmpty(),
                    onClick = {
                        // add player to firebase real time database
                        val player = Player(playerId, name, age.toInt(), height.toInt(), nationality, currentClub, imageUrl)

                        databaseRef.child(playerId).setValue(player).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(
                                    TAG,
                                    "onComplete: Player added successfully {${player.id}, ${player.name}}"
                                )
                                navController.navigate(Screen.MainScreen.getArg())
                            } else {
                                Log.e(TAG, "onComplete err: ${task.exception?.message}")
                            }
                        }
                    }
                )
                TextButton(
                    text = stringResource(R.string.back_to_main_screen),
                    fontSize = 16,
                    textColor = Color.Blue,
                    onClick = { navController.popBackStack() }
                )
            }
        }
    }
}