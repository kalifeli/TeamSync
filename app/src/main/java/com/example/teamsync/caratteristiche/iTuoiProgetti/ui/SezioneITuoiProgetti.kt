package com.example.teamsync.caratteristiche.iTuoiProgetti.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.model.Progetto

@Composable
fun SezioneITUoiProgetti(progetti: List<Progetto>){
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
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(progetti) { progetto ->
            if(progetti.isNotEmpty()) {
                ITuoiProgettiItem(progetto = progetto)
            }else{
                Text(
                    text = "Non partecipi ancora a nessun progetto! Creane uno cliccando il \"+\" o partecipa ad un progetto esistente",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewSezioneITuoiProgetti(){
    SezioneITUoiProgetti(progetti = emptyList())
}
