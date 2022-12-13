package com.example.complete_firebase.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.complete_firebase.models.Player

@Composable
fun PlayerProfileCard(player: Player) {
    Card(
        modifier = Modifier
            .wrapContentSize()
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(color = Color.White)
            .padding(16.dp),
    ) {
        Row(modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max)) {
            PlayerPicturePicture(player = player)
            PlayerContent(player = player)
        }
    }
}

@Composable
fun PlayerPicturePicture(player: Player) {
    Card(
        shape = CircleShape,
        border = BorderStroke(2.dp, color = Color.Green),
        modifier = Modifier.size(48.dp),
        elevation = 4.dp
    ) {
        Image(
            painter = rememberAsyncImagePainter(player.imageUrl),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(48.dp),
            contentDescription = "Profile picture for ${player.name}"
        )
    }
}

@Composable
fun PlayerContent(player: Player) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(start = 8.dp),
        verticalArrangement = Arrangement.aligned(Alignment.CenterVertically)
    ) {
        Text(player.name, fontWeight = FontWeight.Bold)
        Text(
            text = player.nationality,
            style = MaterialTheme.typography.body1
        )
        Text(
            text = player.currentClub,
            style = MaterialTheme.typography.body2.copy(color = Color.Gray)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlayerCardPreview() {
    val player = Player(
        name = "Lionel Messi",
        age = 36,
        height = 170,
        nationality = "Argentina",
        currentClub = "PSG",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/c/c1/Lionel_Messi_20180626.jpg"
    )
    PlayerProfileCard(player = player)
}