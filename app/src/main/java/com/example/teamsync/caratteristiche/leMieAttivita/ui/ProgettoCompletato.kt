package com.example.teamsync.caratteristiche.leMieAttivita.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.autentificazione.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.autentificazione.data.viewModel.ViewModelUtente
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.model.Progetto
import com.example.teamsync.caratteristiche.iTuoiProgetti.ui.ImmagineProfiloUtente
import com.example.teamsync.util.theme.Grey50
import com.example.teamsync.util.theme.Grey70
import com.example.teamsync.util.theme.Red70
import java.text.SimpleDateFormat
import java.util.Locale


/**
 * Funzione composable per visualizzare la schermata di progetto completato.
 *
 * @param progetto Il progetto da visualizzare.
 * @param partecipanti La lista degli ID dei partecipanti.
 * @param viewModelUtente Il ViewModel per recuperare i profili utente.
 * @param isDarkTheme Flag per indicare se il tema scuro è abilitato.
 * @param paddingValues I padding per la schermata.
 */
@Composable
fun ProgettoCompletatoScreen(
    progetto: Progetto,
    partecipanti: List<String>,
    viewModelUtente: ViewModelUtente,
    isDarkTheme: Boolean,
    paddingValues: PaddingValues,
) {
    val partecipantiProfilo = remember { mutableStateOf<List<ProfiloUtente>>(emptyList()) }
    val caricamentoPartecipanti by viewModelUtente.isLoading.observeAsState()

    // LaunchedEffect per recuperare i profili utente
    LaunchedEffect(partecipanti) {
        val profili = partecipanti.mapNotNull { id ->
            viewModelUtente.getUserProfileById(id)
        }
        partecipantiProfilo.value = profili
    }

    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val dataConsegnaFormatted = progetto.dataConsegna.let { dateFormatter.format(it) } ?: "N/A"

    // Schermata di recap del progetto
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDarkTheme) Color.DarkGray else Color.White)
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = stringResource(id = R.string.progettoCompletato),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = if (isDarkTheme) Color.White else Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.im_progettocompletato),
                contentDescription = "immagine progetto completato",
                alignment = Alignment.Center,
                modifier = Modifier.size(150.dp),
                contentScale = ContentScale.Crop
            )

            progetto.descrizione?.let {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (isDarkTheme) Color.White else Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.dataConsegna) + ":",
                        style = MaterialTheme.typography.titleMedium,
                        color = if (isDarkTheme) Color.White else Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = dataConsegnaFormatted,
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (isDarkTheme) Color.White else Color.Black
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.voto) + ":",
                        style = MaterialTheme.typography.titleMedium,
                        color = if (isDarkTheme) Color.White else Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = progetto.voto,
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (isDarkTheme) Color.White else Color.Black
                    )
                }
            }
            Text(
                text = stringResource(id = R.string.Partecipanti)+ ":",
                style = MaterialTheme.typography.titleMedium,
                color = if (isDarkTheme) Color.White else Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

        if (caricamentoPartecipanti == true) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(8.dp),
                    color = Grey50,
                    trackColor = Red70,
                    strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap
                )
            }
        } else {
            items(partecipantiProfilo.value) { profilo ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isDarkTheme) Color.DarkGray else Color.White
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {
                            ImmagineProfiloUtente(
                                imageUrl = profilo.immagine,
                                defaultImage = R.drawable.logo_rotondo,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, Color.White, CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "${profilo.nome} ${profilo.cognome}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = if (isDarkTheme) Color.White else Color.Black
                                )
                                Text(
                                    text = profilo.email,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (isDarkTheme) Grey50 else Grey70
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}






