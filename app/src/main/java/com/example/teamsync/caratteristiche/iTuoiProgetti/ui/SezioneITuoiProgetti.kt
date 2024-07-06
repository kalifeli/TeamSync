package com.example.teamsync.caratteristiche.iTuoiProgetti.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.model.Progetto
import com.example.teamsync.ui.theme.Grey50
import com.example.teamsync.ui.theme.White

@Composable
fun SezioneITUoiProgetti(
    progetti: List<Progetto>,
    navController: NavController
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "I Tuoi Progetti",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    if(progetti.isEmpty()){
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),

            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            colors = CardDefaults.outlinedCardColors(
                containerColor = White
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center, // Center the content within the box
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Text(
                    text = "Oops! Sembra che non ci siano progetti.\nInizia ora creandone uno.",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelSmall,
                    color = Grey50,
                )
            }
        }
    }else {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(progetti) { progetto ->
                ITuoiProgettiItem(navController = navController, progetto = progetto)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewSezioneITuoiProgetti(){
    SezioneITUoiProgetti(navController = rememberNavController(), progetti = emptyList())
}
