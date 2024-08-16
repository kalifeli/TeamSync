package com.example.teamsync.caratteristiche.impostazioni.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.impostazioni.data.model.Terms
import com.example.teamsync.caratteristiche.impostazioni.data.repository.RepositoryTerms
import com.example.teamsync.caratteristiche.impostazioni.data.viewModel.TerminiCondizioniViewModel
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.util.ThemePreferences
import com.example.teamsync.util.theme.ColorePrioritaAlta
import com.example.teamsync.util.theme.White
import kotlinx.coroutines.launch

/**
 * Composable che rappresenta un singolo elemento della lista dei termini.
 *
 * @param singolo Oggetto [Terms] contenente titolo e descrizione di un singolo termine.
 */
@Composable
fun Punti(singolo: Terms) {
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        // Titolo del singolo termine
        Text(
            text = singolo.titolo,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = if (isDarkTheme) Color.White else Color.Black,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        // Descrizione del singolo termine, suddivisa in paragrafi
        val paragrafi = singolo.descrizione.split("\\n")
        paragrafi.forEachIndexed { index, paragrafo ->
            Text(
                text = paragrafo,
                fontSize = 16.sp,
                softWrap = true,
                modifier = Modifier.padding(bottom = 5.dp),
                color = if (isDarkTheme) Color.White else Color.Black
            )
            if (index < paragrafi.size - 1) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

/**
 * Schermata che mostra i Termini e le Condizioni dell'applicazione.
 * Utilizza un ViewModel per gestire lo stato e la logica di business.
 *
 * @param nav [NavHostController] per la navigazione tra le schermate.
 * @param viewModel [TerminiCondizioniViewModel] per gestire la logica e lo stato della schermata.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerminiCondizioniScreen(
    nav: NavHostController,
    viewModel: TerminiCondizioniViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    val listaTermini by viewModel.listaTermini.observeAsState(emptyList())
    val ultimaModifica by viewModel.ultimaModifica.observeAsState()
    val loading by viewModel.loading.observeAsState(true)
    val error by viewModel.error.observeAsState()
    var showButton by remember { mutableStateOf(false) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { index ->
                showButton = index == (listaTermini.size - 1)
            }
    }

    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.tiroloTerm),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (isDarkTheme) Color.White else Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { nav.navigate(Schermate.Impostazioni.route) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = if (isDarkTheme) Color.White else Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = if (isDarkTheme) Color.DarkGray else White
                )
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(if (isDarkTheme) Color.Black else Color.White)
                    .padding(16.dp)
            ) {
                when {
                    loading -> {
                        Text("Caricamento in corso...", fontSize = 16.sp, color = Color.Gray)
                    }
                    error != null -> {
                        Text("Errore: $error", fontSize = 16.sp, color = Color.Red)
                    }
                    else -> {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Text(
                                text = stringResource(id = R.string.ultimaMod) + ultimaModifica.toString(),
                                fontSize = 14.sp,
                                color = Color.Gray,
                            )

                            LazyColumn(state = listState, modifier = Modifier.weight(1f)) {
                                items(listaTermini) { term ->
                                    Punti(singolo = term)
                                }
                            }

                            if (showButton) {
                                Spacer(modifier = Modifier.height(20.dp))

                                Button(
                                    onClick = {
                                        coroutineScope.launch {
                                            listState.animateScrollToItem(0)
                                        }
                                    },
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(bottom = 16.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = ColorePrioritaAlta,
                                        contentColor = White
                                    )
                                ) {
                                    Text(text = stringResource(id = R.string.tornaSu))
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

/**
 * Funzione di anteprima per la schermata dei Termini e Condizioni.
 */
@Preview
@Composable
fun PreviewTermsOfServiceScreen() {
    TerminiCondizioniScreen(
        nav = rememberNavController(),
        viewModel = TerminiCondizioniViewModel(
            RepositoryTerms()
        )
    )
}
