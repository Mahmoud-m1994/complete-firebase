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
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.complete_firebase.components.PlayerProfileCard
import com.example.complete_firebase.components.PrimaryButton
import com.example.complete_firebase.models.Player
import com.example.complete_firebase.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

@Composable
fun MainScreen(navController: NavController, auth: FirebaseAuth, databaseRef: DatabaseReference) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "Welcome ${auth.currentUser?.email}",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
            )
            PrimaryButton(
                text = "Sign Out",
                modifier = Modifier.padding(start = 8.dp),
                onClick = {
                    if (auth.currentUser != null) {
                        auth.signOut()
                        navController.navigate(Screen.LoginScreen.screenName)
                    }
                }
            )
        }
        Divider(thickness = 2.dp, color = Color.Black)
        ListPlayers(databaseRef = databaseRef, navController = navController)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddPlayerScreen.getArg())
                },
                modifier = Modifier.padding( end =  16.dp, bottom = 16.dp),
                backgroundColor = Color(0xFF00BFA6)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add player")
            }
        }
    }
}

// list all players from firebase
@Composable
private fun ListPlayers(databaseRef: DatabaseReference, navController: NavController) {
    // list players
    var players by remember { mutableStateOf(listOf<Player>()) }
    LaunchedEffect(key1 = Unit) {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val playersList = mutableListOf<Player>()
                Log.d(TAG, "onDataChange: ${snapshot.value}")
                snapshot.children.forEach { player ->
                    player?.let {
                        val playerId = player.key.toString()
                        val age = it.child("age").value.toString().toInt()
                        val currentClub = it.child("currentClub").value.toString()
                        val height = it.child("height").value.toString().toInt()
                        val imageUrl = it.child("imageUrl").value.toString()
                        val name = it.child("name").value.toString()
                        val nationality= it.child("nationality").value.toString()
                        playersList.add(Player(playerId,name,age,height,nationality,currentClub,imageUrl))
                    }
                }
                players = playersList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: ${error.message}")
            }
        })
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        players.forEach { player ->
            PlayerProfileCard(player = player, navController = navController)
        }
        if (players.isEmpty()) {
            Text(text = "No players found")
        }
    }
}