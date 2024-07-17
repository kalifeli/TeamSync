package com.example.teamsync.caratteristiche.iTuoiProgetti.ui


import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.model.Progetto
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
import com.example.teamsync.data.models.Priorità
import com.example.teamsync.ui.theme.White

@Composable
fun SezioneITUoiProgetti(
    progetti: List<Progetto>,
    navController: NavController,
    attivitaProgetti: Map<String, Int>,
    viewModelProgetto: ViewModelProgetto,
    isDarkTheme: Boolean
){
    val contesto = LocalContext.current
    val preferences = contesto.getSharedPreferences("preferenze_progetti", Context.MODE_PRIVATE)

    val comparatore = Comparator<Progetto> { Progetto1, Progetto2 ->
        val priorita1 = Progetto1.priorita
        val priorita2 = Progetto2.priorita

        // Confronto basato sull'ordine dell'enumerazione Priorità
        when {
            priorita1 == Priorità.ALTA && priorita2 != Priorità.ALTA -> -1 // ALTA prima di qualsiasi altra
            priorita1 != Priorità.ALTA && priorita2 == Priorità.ALTA -> 1  // ALTA prima di qualsiasi altra

            priorita1 == Priorità.MEDIA && priorita2 == Priorità.BASSA -> -1 // MEDIA prima di BASSA
            priorita1 == Priorità.BASSA && priorita2 == Priorità.MEDIA -> 1  // MEDIA prima di BASSA

            priorita1 == Priorità.MEDIA && priorita2 == Priorità.NESSUNA -> -1 // MEDIA prima di NESSUNA
            priorita1 == Priorità.NESSUNA && priorita2 == Priorità.MEDIA -> 1  // MEDIA prima di NESSUNA

            priorita1 == Priorità.BASSA && priorita2 == Priorità.NESSUNA -> -1 // BASSA prima di NESSUNA
            priorita1 == Priorità.NESSUNA && priorita2 == Priorità.BASSA -> 1  // BASSA prima di NESSUNA

            else -> 0 // Rimane invariato
        }
    }

    val visualizza_completati by remember { mutableStateOf(preferences.getBoolean("preferenza_progetti_completati", false)) }
    val ordine_progetti by remember {
        mutableStateOf(
            preferences.getString(
                "ordine_progetti",
                "cronologico"
            )
        )
    }


    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "I Tuoi Progetti",
            style = MaterialTheme.typography.titleLarge,
            color = if(isDarkTheme) White else Color.Black,
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
                .height(140.dp)
                .border(1.dp, White,shape = RoundedCornerShape(16.dp)),
            colors = CardDefaults.outlinedCardColors(
                containerColor = if(isDarkTheme) Color.Black else White
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
                    color = if(isDarkTheme) White else Color.Black,
                )
            }
        }
    }else {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when(ordine_progetti){
                "cronologico" ->
                {
                    items(progetti.sortedByDescending { it.dataCreazione }) { progetto ->
                        val attivitaNonCompletate = attivitaProgetti[progetto.id] ?: 0
                        if(visualizza_completati)
                        {
                            ITuoiProgettiItem(navController = navController, progetto = progetto, viewModelProgetto = viewModelProgetto, attivitaNonCompletate = attivitaNonCompletate, isDarkTheme = isDarkTheme)

                        }
                        else if (!(progetto.completato))
                        {
                            ITuoiProgettiItem(navController = navController, progetto = progetto, viewModelProgetto = viewModelProgetto, attivitaNonCompletate = attivitaNonCompletate, isDarkTheme = isDarkTheme)
                        }



                    }
                }
                "scadenza" ->
                {
                    items(progetti.sortedBy { it.dataScadenza }) { progetto ->
                        val attivitaNonCompletate = attivitaProgetti[progetto.id] ?: 0
                        if(visualizza_completati)
                        {
                            ITuoiProgettiItem(navController = navController, progetto = progetto, viewModelProgetto = viewModelProgetto, attivitaNonCompletate = attivitaNonCompletate, isDarkTheme = isDarkTheme)

                        }
                        else if (!(progetto.completato))
                        {
                            ITuoiProgettiItem(navController = navController, progetto = progetto, viewModelProgetto = viewModelProgetto, attivitaNonCompletate = attivitaNonCompletate, isDarkTheme = isDarkTheme)
                        }
                    }
                }
                "priorità" ->
                {
                    items(progetti.sortedWith(comparatore)) { progetto ->
                        val attivitaNonCompletate = attivitaProgetti[progetto.id] ?: 0
                        if(visualizza_completati)
                        {
                            ITuoiProgettiItem(navController = navController, progetto = progetto, viewModelProgetto = viewModelProgetto, attivitaNonCompletate = attivitaNonCompletate, isDarkTheme = isDarkTheme)

                        }
                        else if (!(progetto.completato))
                        {
                            ITuoiProgettiItem(navController = navController, progetto = progetto, viewModelProgetto = viewModelProgetto, attivitaNonCompletate = attivitaNonCompletate, isDarkTheme = isDarkTheme)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewSezioneITuoiProgetti(){
    //SezioneITUoiProgetti(navController = rememberNavController(), progetti = emptyList(), viewModelProgetto = ViewModelProgetto(), attivitaProgetti = )
}
