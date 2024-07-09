package com.example.teamsync.caratteristiche.iTuoiProgetti.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.model.Progetto
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
import com.example.teamsync.ui.theme.White

@Composable
fun SezioneITUoiProgetti(
    progetti: List<Progetto>,
    navController: NavController,
    viewModelProgetto: ViewModelProgetto
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
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
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.im_relax),
                    contentDescription = "immagine nessun progetto",
                    modifier = Modifier.size(150.dp)
                )
                Text(
                    text = "Oops! Sembra che non ci siano progetti. Inizia ora creandone uno.",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Black,
                )
            }
        }
    }else {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(progetti) { progetto ->
                ITuoiProgettiItem(navController = navController, progetto = progetto, viewModelProgetto = viewModelProgetto)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewSezioneITuoiProgetti(){
    SezioneITUoiProgetti(navController = rememberNavController(), progetti = emptyList(), viewModelProgetto = ViewModelProgetto())
}
