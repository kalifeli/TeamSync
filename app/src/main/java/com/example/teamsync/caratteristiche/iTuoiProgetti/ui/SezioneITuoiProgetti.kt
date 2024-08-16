package com.example.teamsync.caratteristiche.iTuoiProgetti.ui


import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.model.Progetto
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
import com.example.teamsync.util.Priorita
import com.example.teamsync.util.theme.Grey50
import com.example.teamsync.util.theme.Red70
import com.example.teamsync.util.theme.White

/**
 * Composable che visualizza la sezione "I Tuoi Progetti" con una lista di progetti.
 * I progetti possono essere ordinati per data di creazione, scadenza o priorità,
 * e possono essere filtrati per mostrare o nascondere i progetti completati.
 *
 * @param progetti Lista di progetti dell'utente.
 * @param navController Controller di navigazione per navigare tra le schermate.
 * @param attivitaProgetti Mappa delle attività non completate per ciascun progetto.
 * @param viewModelProgetto ViewModel per gestire i dati e le operazioni sui progetti.
 * @param isDarkTheme Indica se l'app è in modalità tema scuro.
 */
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

    // Comparator per ordinare i progetti in base alla priorità
    val comparatore = Comparator<Progetto> { progetto1, progetto2 ->
        val priorita1 = progetto1.priorita
        val priorita2 = progetto2.priorita

        // Confronto basato sull'ordine dell'enumerazione Priorità
        when {
            priorita1 == Priorita.ALTA && priorita2 != Priorita.ALTA -> -1 // ALTA prima di qualsiasi altra
            priorita1 != Priorita.ALTA && priorita2 == Priorita.ALTA -> 1  // ALTA prima di qualsiasi altra

            priorita1 == Priorita.MEDIA && priorita2 == Priorita.BASSA -> -1 // MEDIA prima di BASSA
            priorita1 == Priorita.BASSA && priorita2 == Priorita.MEDIA -> 1  // MEDIA prima di BASSA

            priorita1 == Priorita.MEDIA && priorita2 == Priorita.NESSUNA -> -1 // MEDIA prima di NESSUNA
            priorita1 == Priorita.NESSUNA && priorita2 == Priorita.MEDIA -> 1  // MEDIA prima di NESSUNA

            priorita1 == Priorita.BASSA && priorita2 == Priorita.NESSUNA -> -1 // BASSA prima di NESSUNA
            priorita1 == Priorita.NESSUNA && priorita2 == Priorita.BASSA -> 1  // BASSA prima di NESSUNA

            else -> 0 // Rimane invariato
        }
    }

    val visualizzaCompletati by remember { mutableStateOf(preferences.getBoolean("preferenza_progetti_completati", false)) }
    val ordineProgetti by remember {
        mutableStateOf(
            preferences.getString(
                "ordine_progetti",
                "cronologico"
            )
        )
    }

    val caricamentoProgetti by viewModelProgetto.isLoading.observeAsState()


    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.iTuoiProgetti),
            style = MaterialTheme.typography.titleLarge,
            color = if(isDarkTheme) White else Color.Black,
        )
    }
    Spacer(modifier = Modifier.height(8.dp))

    if(caricamentoProgetti == true){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isDarkTheme) Color.Black else Color.White),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    color = Grey50,
                    trackColor = Red70,
                    strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap
                )
                Text(
                    text = stringResource(id = R.string.CaricamentoProgetti),
                    color = if (isDarkTheme) Color.White else Color.Black,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }

    }else if(progetti.isEmpty()){
        // Se non ci sono progetti, mostra un messaggio vuoto
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),

            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .border(1.dp, White, shape = RoundedCornerShape(16.dp)),
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
                    text = stringResource(id = R.string.senzaProgetti),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelMedium,
                    color = if(isDarkTheme) White else Color.Black,
                )
            }
        }
    }else {
        // Mostra i progetti in una riga orizzontale con ordinamento e filtraggio
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when(ordineProgetti){
                "cronologico" ->
                {
                    items(progetti.sortedByDescending { it.dataCreazione }) { progetto ->
                        val attivitaNonCompletate = attivitaProgetti[progetto.id] ?: 0
                        if(visualizzaCompletati)
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
                        if(visualizzaCompletati)
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
                        if(visualizzaCompletati)
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

/**
 * Anteprima del composable SezioneITUoiProgetti.
 */
@Preview(showSystemUi = true)
@Composable
fun PreviewSezioneITuoiProgetti(){
    //SezioneITUoiProgetti(navController = rememberNavController(), progetti = emptyList(), viewModelProgetto = ViewModelProgetto(), attivitaProgetti = )
}
