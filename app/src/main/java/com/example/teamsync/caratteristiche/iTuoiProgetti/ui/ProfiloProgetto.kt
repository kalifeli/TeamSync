package com.example.teamsync.caratteristiche.iTuoiProgetti.ui
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.autentificazione.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.autentificazione.data.viewModel.ViewModelUtente
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.model.Progetto
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
import com.example.teamsync.caratteristiche.notifiche.data.viewModel.ViewModelNotifiche
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.util.NoInternetScreen
import com.example.teamsync.util.ThemePreferences
import com.example.teamsync.util.isInternetAvailable
import com.example.teamsync.util.theme.Grey50
import com.example.teamsync.util.theme.Red70
import com.example.teamsync.util.theme.White
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Funzione composable che visualizza i dettagli di un progetto.
 *
 * @param viewModelUtente ViewModel per gestire le operazioni dell'utente.
 * @param navController Controller per la navigazione tra le schermate.
 * @param viewModelProgetto ViewModel per gestire le operazioni del progetto.
 * @param idProg ID del progetto.
 * @param viewNotifiche ViewModel per gestire le notifiche.
 * @param provenienza Provenienza della navigazione.
 * @param sottoprovenienza Sottoprovenienza della navigazione.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Progetto(
    viewModelUtente: ViewModelUtente,
    navController: NavHostController,
    viewModelProgetto: ViewModelProgetto,
    idProg : String,
    viewNotifiche: ViewModelNotifiche,
    provenienza : String,
    sottoprovenienza: String
) {
    var progetto by remember { mutableStateOf<Progetto?>(null) }
    var partecipanti by remember { mutableStateOf<List<String>?>(emptyList()) }
    var nomeProgetto by remember { mutableStateOf("") }
    val context = LocalContext.current
    val isDarkTheme = ThemePreferences.getTheme(context)
    val isConnected = remember {mutableStateOf(isInternetAvailable(context)) }

    // Effettua un controllo periodico sulla connessione ad internet
    LaunchedEffect(Unit) {
        // Periodicamente avviene un controllo per verificare che ci sia la connessione ad internet
        while(true){
            isConnected.value = isInternetAvailable(context)
            delay(5000) // controllo ogni 5 secondi
        }
    }

    // Carica i dettagli del progetto e la lista dei partecipanti
    LaunchedEffect(Unit) {
        progetto = viewModelProgetto.getProgettoById(idProg)
        partecipanti = viewModelProgetto.getListaPartecipanti(idProg)
        nomeProgetto = viewModelProgetto.getnomeProgetto(idProg)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.InfoProgetto),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = if(isDarkTheme) Color.White else Color.Black

                    )
                },
                navigationIcon = {
                    IconButton(onClick = {

                        when (provenienza) {
                            "notifiche" -> navController.navigate(Schermate.Notifiche.route)
                            "progetto" -> {
                                when (sottoprovenienza) {
                                    "progetto"->navController.navigate("progetto/${idProg}")
                                    "notifiche"->navController.navigate(Schermate.Notifiche.route)

                                }
                            }
                        }
                    }) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "icona indietro",
                            tint = if (isDarkTheme)Color.White else Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = if(isDarkTheme) Color.DarkGray else White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isDarkTheme) Color.DarkGray else White)
                .padding(padding)
                .padding(16.dp)
        ) {
            if(!isConnected.value){
                NoInternetScreen(
                    isDarkTheme = isDarkTheme,
                    onRetry = {isConnected.value = isInternetAvailable(context) }
                )
            }else {
                progetto?.let {
                    InformazioniProgetto(
                        viewModelProgetto,
                        it,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                partecipanti?.let {
                    ListaColleghi(
                        viewModelUtente,
                        navController,
                        viewModelProgetto,
                        idProg,
                        it,
                        nomeProgetto,
                        viewNotifiche,
                        sottoprovenienza,
                        provenienza,
                        context
                    )
                }
            }
        }
    }
}


/**
 * Funzione composable per visualizzare le informazioni di un progetto.
 *
 * @param viewModelProgetto ViewModel per gestire le operazioni del progetto.
 * @param progetto Dati del progetto.
 */
@Composable
fun InformazioniProgetto(viewModelProgetto: ViewModelProgetto, progetto: Progetto) {

    val isLoading by viewModelProgetto.isLoading.observeAsState()
    val formatoData = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val dataCreazione = formatoData.format(progetto.dataCreazione)
    val dataScadenza = formatoData.format(progetto.dataScadenza)
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(1.dp, if (isDarkTheme) White else White, shape = RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),
        shape = RoundedCornerShape(16.dp),
        colors = if(isDarkTheme)CardDefaults.cardColors(Color.Black) else CardDefaults.cardColors(
            Red70
        ),

    ) {
        Text(
            text = stringResource(id = R.string.Informazioni),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        if (isLoading == true) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                color = Grey50,
                trackColor = Red70,
                strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap
            )
        } else{
            InfoText(informazione = stringResource(id = R.string.nome), testo = progetto.nome)
            InfoText(informazione = stringResource(id = R.string.descrizioneEdit), testo = progetto.descrizione)
            InfoText(informazione = stringResource(id = R.string.priorità), testo = progetto.priorita.nomeTradotto())
            InfoText(informazione = stringResource(id = R.string.datadicreazione), testo = dataCreazione)
            InfoText(informazione = stringResource(id = R.string.datadiscadenza), testo = dataScadenza)
        }
    }
}

/**
 * Funzione composable per visualizzare un'informazione di un progetto.
 *
 * @param informazione Nome dell'informazione.
 * @param testo Testo dell'informazione.
 */
@Composable
fun InfoText(informazione: String, testo: String?){
    Text(
        text = "$informazione: $testo",
        style = MaterialTheme.typography.labelLarge,
        color = White,
        modifier = Modifier.padding(8.dp)
    )
}

/**
 * Funzione composable per visualizzare la lista dei colleghi partecipanti a un progetto.
 *
 * @param viewModelUtente ViewModel per gestire le operazioni dell'utente.
 * @param navController Controller per la navigazione tra le schermate.
 * @param viewModelProgetto ViewModel per gestire le operazioni del progetto.
 * @param idProgetto ID del progetto.
 * @param partecipanti Lista degli ID dei partecipanti.
 * @param nomeProgetto Nome del progetto.
 * @param viewNotifiche ViewModel per gestire le notifiche.
 * @param sottoprovenienza Sottoprovenienza della navigazione.
 * @param provenienza Provenienza della navigazione.
 */
@Composable
fun ListaColleghi(
    viewModelUtente: ViewModelUtente,
    navController: NavHostController,
    viewModelProgetto : ViewModelProgetto,
    idProgetto : String,
    partecipanti : List<String>,
    nomeProgetto : String,
    viewNotifiche: ViewModelNotifiche,
    sottoprovenienza : String,
    provenienza: String,
    context: Context
) {
    val userProfile by viewModelUtente.userProfilo.observeAsState()
    val isLoading by viewModelProgetto.isLoading.observeAsState()
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    val cache = remember { mutableStateMapOf<String, ProfiloUtente?>() } // Utilizzo di mutableStateMapOf per la cache
    val visualizzaPartecipanti by remember { mutableStateOf(true) }

    val mostraPulsante = remember { mutableStateOf(false) } // Pulsante per partecipare al progetto

    LaunchedEffect(Unit) {
        userProfile?.let { profile ->
            viewModelProgetto.getProgettiUtenteByIdUtente(profile.id ) { progetti, _ ->
                mostraPulsante.value = !(viewModelProgetto.utentePartecipa(progetti, idProgetto))
            }
        }
    }

    if(visualizzaPartecipanti)
    {
        OutlinedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .border(1.dp, if (isDarkTheme) White else White, shape = RoundedCornerShape(16.dp)),
            colors = if(isDarkTheme)CardDefaults.cardColors(containerColor = Color.Black) else  CardDefaults.cardColors(containerColor = White)
        ) {
            Text(
                text = stringResource(id = R.string.Partecipanti),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = if(isDarkTheme) Color.White else Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )
            if(isLoading == true){
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                    color = Grey50,
                    trackColor = Red70,
                    strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap
                )
            }else {
                LazyColumn {
                    items(partecipanti) { amico ->

                        var userAmico by remember { mutableStateOf<ProfiloUtente?>(null) }

                        if (cache.contains(amico)) {
                            userAmico = cache[amico]
                        } else {
                            viewModelUtente.ottieniUtente(amico) { profile ->
                                userAmico = profile
                                cache[amico] = profile // Aggiunge il profilo alla cache
                                Log.d("Singolo Collega", "Dati: $userAmico")
                            }
                        }

                        userAmico?.let { collega ->
                            CollegaItem(
                                collega,
                                color = Red70,
                                navController,
                                userProfile,
                                idProgetto,
                                sottoprovenienza,
                                provenienza,
                                isDarkTheme
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

        if (mostraPulsante.value) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(horizontal = 16.dp),
                onClick = {
                    viewModelProgetto.aggiungiPartecipanteAlProgetto(idProgetto, userProfile?.id ?: "")
                    for (p in partecipanti) {
                        if (p != userProfile?.id) {
                            val contenuto = userProfile?.nome + " " + (userProfile?.cognome
                                    ?: "") + " " + "è entrato nel progetto " + nomeProgetto
                            userProfile?.id?.let {
                                viewNotifiche.creaNotifica(
                                    it,
                                    p,
                                    "Entra_Progetto",
                                    contenuto,
                                    idProgetto
                                )
                            }
                        }
                    }
                    mostraPulsante.value = false
                          },
                shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(
                    containerColor = Red70,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = stringResource(id = R.string.entraNelProcetto),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }
        }
    }
}

/**
 * Funzione composable per visualizzare le informazioni di un collega.
 *
 * @param utente Profilo del collega.
 * @param color Colore per l'elemento della lista.
 * @param navController Controller per la navigazione tra le schermate.
 * @param userLoggato Profilo dell'utente loggato.
 * @param idProg ID del progetto.
 * @param sottoprovenienza Sottoprovenienza della navigazione.
 * @param provenienza Provenienza della navigazione.
 * @param isDarkTheme Booleano per determinare se il tema è scuro.
 */
@Composable
fun CollegaItem(
    utente : ProfiloUtente,
    color: Color,
    navController: NavHostController,
    userLoggato: ProfiloUtente?,
    idProg: String,
    sottoprovenienza : String,
    provenienza: String,
    isDarkTheme: Boolean
) {

    val amicizia = utente.amici.contains(userLoggato?.id)

    ElevatedCard(
        onClick = {
            navController.navigate("utente/${utente.id}/${amicizia}/${provenienza}/1/${idProg}/${sottoprovenienza}")},
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = if(isDarkTheme)CardDefaults.cardColors(Color.Gray) else CardDefaults.cardColors(
            White
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        if (isDarkTheme) Color.White else color.copy(alpha = 0.2f),
                        CircleShape
                    )
                    .padding(8.dp),
                painter = painterResource(id = R.drawable.logo_white),
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "${utente.nome} ${utente.cognome}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = if(isDarkTheme) Color.White else Color.Black
                )
                Text(
                    text = utente.matricola,
                    fontSize = 14.sp,
                    color = if(isDarkTheme) Color.White else Color.Black
                )
            }
        }
    }
}





















