package com.example.teamsync.caratteristiche.iTuoiProgetti.ui

import androidx.compose.foundation.border
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.teamsync.ui.theme.Red70
import com.example.teamsync.ui.theme.White
import androidx.compose.ui.text.font.FontWeight
import com.example.teamsync.ui.theme.Grey35
import com.example.teamsync.R


@Composable
fun SezioneProgressiProgetti(progettiCompletati: Int, progettiUtente: Int, isDarkTheme: Boolean){
    val progressi = progettiCompletati/progettiUtente.toFloat() // la conversione a float è necessaria per la circular progress indicator
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),
        modifier = Modifier
            .size(width = 160.dp, height = 200.dp)
            .border(1.dp, if (isDarkTheme) White else White, shape = RoundedCornerShape(16.dp)),
        colors = CardDefaults.outlinedCardColors(
            containerColor = if(isDarkTheme) Color.Black else White
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
                text = stringResource(id = R.string.progressi),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = if(isDarkTheme) White else Color.Black
            )
            Spacer(modifier = Modifier.size(8.dp))

            Box(
                modifier = Modifier.size(100.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { progressi },
                    modifier = Modifier.size(80.dp),
                    color = Red70,
                    strokeWidth = 8.dp,
                    trackColor = if(isDarkTheme) White else Grey35,
                )
                Text(
                    text = "$progettiCompletati / $progettiUtente",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = if(isDarkTheme) White else Color.Black
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = stringResource(id = R.string.continua),
                style = MaterialTheme.typography.bodyMedium,
                color = if(isDarkTheme) White else Color.Black
            )
        }
    }
}

@Preview
@Composable
fun PreviewSezioneProgressiProgetti(){
    SezioneProgressiProgetti(3, 10, isDarkTheme = false)
}