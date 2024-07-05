package com.example.teamsync.caratteristiche.iTuoiProgetti.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.teamsync.ui.theme.Red70
import com.example.teamsync.ui.theme.White

@Composable
fun SezioneProgressiProgetti(progress: Float, completedProjects: Int){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),
        modifier = Modifier
            .size(width = 160.dp, height = 200.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = White
        ),
        shape = RoundedCornerShape(16.dp)

    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Progressi",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.size(8.dp))
            Box(
                modifier = Modifier.size(100.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.size(80.dp),
                    color = Red70,
                    strokeWidth = 8.dp,
                )
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "Continua così!",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
    }
}

@Preview
@Composable
fun PreviewSezioneProgressiProgetti(){
    SezioneProgressiProgetti(0.55f, 3)
}