package com.example.teamsync.caratteristiche.notifiche.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.example.teamsync.caratteristiche.autentificazione.data.repository.RepositoryUtente
import com.example.teamsync.caratteristiche.autentificazione.data.viewModel.ViewModelUtente
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.repository.RepositoryProgetto
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
import com.example.teamsync.caratteristiche.leMieAttivita.data.repository.ToDoRepository
import com.example.teamsync.caratteristiche.notifiche.data.model.Notifiche
import com.example.teamsync.caratteristiche.notifiche.data.viewModel.ViewModelNotifiche
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.util.NoInternetScreen
import com.example.teamsync.util.ThemePreferences
import com.example.teamsync.util.isInternetAvailable
import com.example.teamsync.util.theme.*
import kotlinx.coroutines.delay

/**
 * Schermata delle notifiche per l'applicazione.
 *
 * Questa funzione mostra le notifiche dell'utente corrente, consentendo di visualizzare,
 * contrassegnare come lette o eliminare le notifiche. La schermata supporta il tema chiaro e scuro
 * e gestisce automaticamente lo stato della connessione internet e il caricamento dei dati.
 *
 * @param viewModelUtente Il ViewModel associato al profilo utente.
 * @param navController Il controller per la navigazione tra le schermate.
 * @param notificheModel Il ViewModel che gestisce le notifiche.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    viewModelUtente: ViewModelUtente,
    navController: NavHostController,
    notificheModel: ViewModelNotifiche
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(stringResource(id = R.string.nonLette), stringResource(id = R.string.lette))
    val userProfile by viewModelUtente.userProfilo.observeAsState()
    val notificheList by remember { notificheModel.notificheList }
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    val context = LocalContext.current

    // Rimozione del recupero delle preferenze in modo sincrono. Questo dovrebbe idealmente essere gestito in un ViewModel.
    val preferences = context.getSharedPreferences("preferenze_notifiche", Context.MODE_PRIVATE)
    val isEntraProgettoSelected by remember { mutableStateOf(preferences.getBoolean("utente_entra_progetto", true)) }
    val isCompletaTaskSelected by remember { mutableStateOf(preferences.getBoolean("utente_completa_task", true)) }
    val isModificaTaskSelected by remember { mutableStateOf(preferences.getBoolean("utente_modifica_mia_task", true)) }

    val erroreEliminazioneNotifiche by notificheModel.erroreEliminazioneNotifiche
    val eliminazioneNotificheStato by notificheModel.eliminazioneNotificheStato
    val erroreLetturaNotifiche by notificheModel.erroreLetturaNotifiche
    val letturaNotificheStato by notificheModel.letturaNotificheStato

    val isConnected = remember { mutableStateOf(isInternetAvailable(context)) }

    // LaunchedEffect ora si attiva solo quando il profilo utente è disponibile.
    LaunchedEffect(userProfile) {
        if (userProfile != null) {
            notificheModel.fetchNotifiche()
        } else {
            Toast.makeText(context, context.getString(R.string.caricamento_profilo), Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        while(true){
            isConnected.value = isInternetAvailable(context)
            delay(5000) // controllo ogni 5 secondi
        }
    }

    LaunchedEffect(erroreEliminazioneNotifiche) {
        erroreEliminazioneNotifiche?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            notificheModel.resetErroreEliminazioneNotifiche()
        }
    }

    LaunchedEffect(eliminazioneNotificheStato) {
        if(eliminazioneNotificheStato){
            notificheModel.fetchNotifiche()
            Toast.makeText(context, context.getString(R.string.notifiche_cancellate_successo), Toast.LENGTH_LONG).show()
            notificheModel.resetEliminazioneNotificheStato()
        }
    }

    LaunchedEffect(erroreLetturaNotifiche) {
        erroreLetturaNotifiche?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            notificheModel.resetErroreLetturaNotifiche()
        }
    }

    LaunchedEffect(letturaNotificheStato) {
        if(letturaNotificheStato && !notificheList.any { !it.aperto }){
            notificheModel.fetchNotifiche()
            Toast.makeText(context, context.getString(R.string.nessuna_nuova_notifica), Toast.LENGTH_LONG).show()
            notificheModel.resetLetturaNotificheStato()
        }else{
            notificheModel.fetchNotifiche()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.notifiche),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                },
                actions = {
                    if(isConnected.value) {
                        IconButton(onClick = {
                            notificheModel.fetchNotifiche()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh delle notifiche",
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = if(isDarkTheme) Color.DarkGray else White,
                    titleContentColor = if(isDarkTheme) White else Color.Black,
                    actionIconContentColor = if(isDarkTheme) White else Color.Black,
                    navigationIconContentColor = if(isDarkTheme) White else Color.Black
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(color = if (isDarkTheme) Color.Black else White)
        ) {

            TabRow(
                selectedTabIndex = selectedTab,
                indicator = { posizioneTab ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier
                            .tabIndicatorOffset(posizioneTab[selectedTab])
                            .height(4.dp),
                        color = Red70
                    )
                },
                containerColor = if(isDarkTheme) Color.Black else White,
                contentColor = if(isDarkTheme) White else Color.Black
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(text = title) },
                        selectedContentColor = Red70,
                        unselectedContentColor = if(isDarkTheme) Color.White else Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp)) // Spazio tra le tabs e l'indicatore di caricamento

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (notificheModel.isLoading.value && isConnected.value) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(8.dp),
                        color = Grey50,
                        trackColor = Red70,
                        strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            when {
                !isConnected.value -> {
                    NoInternetScreen(
                        isDarkTheme = isDarkTheme,
                        onRetry = { isConnected.value = isInternetAvailable(context) }
                    )
                }
                userProfile == null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(8.dp),
                            color = Grey50,
                            trackColor = Red70,
                            strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap
                        )
                    }
                }
                notificheModel.isLoading.value.not() -> {
                    if (notificheList.isEmpty()) {
                        SchermataVuotaNotifiche(isDarkTheme = isDarkTheme, padding = padding)
                    } else if (!notificheList.any { !it.aperto } && selectedTab == 0) {
                        SchermataVuotaNotifiche(isDarkTheme = isDarkTheme, padding = padding, messaggio = stringResource(
                            id = R.string.noNotificheDaLeggere
                        ))
                    }
                }
            }

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                item {
                    if (selectedTab == 0 && notificheList.any { !it.aperto }) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(
                                onClick = {
                                    notificheList.forEach { notifica ->
                                        notificheModel.cambiaStatoNotifica(notifica.id)
                                    }
                                },
                                enabled = notificheList.isNotEmpty(),
                                border = BorderStroke(0.5.dp, if (isDarkTheme) White else Color.Black),
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = if (isDarkTheme) White else Color.Black,
                                    containerColor = if (isDarkTheme) Color.Black else White,
                                    disabledContainerColor = if (isDarkTheme) Color.DarkGray else Grey35,
                                    disabledContentColor = if (isDarkTheme) White else Grey50,
                                ),
                            ) {
                                Text(text = stringResource(id = R.string.contrassegnaComeLette))
                            }
                        }
                    }
                }

                item {
                    if (selectedTab == 1 && notificheList.isNotEmpty()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(
                                onClick = {
                                    userProfile?.let {
                                        notificheModel.eliminaNotificheUtente(it.id)
                                    }
                                },
                                border = BorderStroke(0.5.dp, if (isDarkTheme) White else Color.Black),
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = if (isDarkTheme) White else Color.Black,
                                    containerColor = if (isDarkTheme) Color.Black else White,
                                ),
                            ) {
                                Text(text = stringResource(id = R.string.cancellaNotifiche))
                            }
                        }
                    }
                }

                // Ordinamento e filtraggio delle notifiche
                val notificheOrdinata = notificheList.sortedByDescending { it.data }
                items(notificheOrdinata.filter { notifica ->
                    when (selectedTab) {
                        0 -> !notifica.aperto // Non lette
                        1 -> true // Tutte
                        else -> true // Fallback
                    }
                }) { notifica ->
                    userProfile?.let {
                        when (notifica.tipo) {
                            "Completamento_Task" -> if (isCompletaTaskSelected) NotificationItem(WhiteFacebook, notifica, navController, notificheModel, it)
                            "Modifica_Task" -> if (isModificaTaskSelected) NotificationItem(WhiteFacebook, notifica, navController, notificheModel, it)
                            "Entra_Progetto" -> if (isEntraProgettoSelected) NotificationItem(WhiteFacebook, notifica, navController, notificheModel, it)
                            else -> NotificationItem(WhiteFacebook, notifica, navController, notificheModel, it)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

/**
 * Composabile per visualizzare la schermata vuota delle notifiche.
 *
 * @param isDarkTheme Booleano che indica se il tema corrente è scuro.
 * @param padding I padding per la schermata.
 * @param messaggio Il messaggio da mostrare quando non ci sono notifiche disponibili.
 */
@Composable
fun SchermataVuotaNotifiche(
    isDarkTheme: Boolean,
    padding: PaddingValues,
    messaggio: String = stringResource(id = R.string.noNotifiche)
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDarkTheme) Color.Black else White)
            .padding(padding),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.im_nessunanotifica1),
            contentDescription = "immagine nessuna notifica",
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = messaggio,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelLarge,
            color = if (isDarkTheme) White else Color.Black
        )
    }
}

/**
 * Composabile per visualizzare un singolo elemento di notifica.
 *
 * @param iconColor Il colore dell'icona della notifica.
 * @param notifica La notifica da visualizzare.
 * @param navController Il controller per la navigazione tra le schermate.
 * @param vmNotifiche Il ViewModel che gestisce le notifiche.
 * @param userProfile Il profilo utente corrente.
 */
@Composable
fun NotificationItem(iconColor: Color, notifica: Notifiche, navController: NavHostController, vmNotifiche: ViewModelNotifiche, userProfile: ProfiloUtente) {
    val contesto = LocalContext.current
    val apertura = remember { mutableStateOf(false) }
    val viewmodelUtente = ViewModelUtente(RepositoryUtente(contesto), contesto)
    val viewmodelProgetto = ViewModelProgetto(repositoryProgetto = RepositoryProgetto(), repositoryLeMieAttivita = ToDoRepository(), viewModelUtente = ViewModelUtente(RepositoryUtente(contesto), contesto), contesto)
    var listap by remember { mutableStateOf<List<String>?>(emptyList()) }
    var nomeProgetto by remember { mutableStateOf("") }
    val isLoading by viewmodelProgetto.caricaNome.observeAsState()
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    val loadingRichiestaAmicizia = remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        viewmodelUtente.getUserProfile()
        listap = viewmodelProgetto.getListaPartecipanti(notifica.progettoId)
        nomeProgetto = if (notifica.progettoId.isNotEmpty()) {
            viewmodelProgetto.getnomeProgetto(notifica.progettoId)
        } else {
            "Nome progetto non disponibile"
        }
    }

    LaunchedEffect(apertura.value) {
        if (apertura.value) {
            vmNotifiche.cambiaStatoNotifica(notifica.id)
        }
    }

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = {
            if (isLoading == false && !loadingRichiestaAmicizia.value) {
                apertura.value = true
                gestisciClickNotifca(navController, notifica, viewmodelUtente)
            }
        },
        colors = CardDefaults.outlinedCardColors(containerColor = if (isDarkTheme) Color.Black else White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(iconColor, CircleShape)
                    .padding(8.dp)
            ) {
                if (isLoading == true) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp)
                            .padding(8.dp),
                        color = Grey50,
                        trackColor = Red70,
                        strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.icon),
                        contentDescription = "Notification Icon",
                        tint = if (notifica.aperto) Color.Black else Red70,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = notifica.contenuto,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDarkTheme) Color.White else Color.Black
            )
        }

        if (notifica.tipo == "Richiesta_Amicizia" || notifica.tipo == "Richiesta_Progetto") {
            if (notifica.accettato == "false" && !notifica.aperto) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "Aggiungi",
                        tint = if (isDarkTheme) White else Color.Gray,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                gestisciCheckNotifica(
                                    navController,
                                    notifica,
                                    userProfile,
                                    vmNotifiche,
                                    viewmodelProgetto,
                                    viewmodelUtente,
                                    listap,
                                    nomeProgetto
                                )
                            }
                    )
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Rifiuta richiesta",
                        tint = if (isDarkTheme) White else Color.Gray,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                vmNotifiche.cambiaStatoNotifica(notifica.id)
                            }
                    )
                }
            }
        }
    }
}

/**
 * Gestisce il click sulle notifiche e naviga verso la schermata appropriata.
 *
 * @param navController Il controller per la navigazione tra le schermate.
 * @param notifica La notifica selezionata.
 * @param viewmodelUtente Il ViewModel dell'utente che gestisce le operazioni relative all'utente.
 */
fun gestisciClickNotifca(navController: NavHostController, notifica: Notifiche, viewmodelUtente: ViewModelUtente) {
    when (notifica.tipo) {
        "Richiesta_Amicizia" -> {
            viewmodelUtente.sonoAmici(notifica.mittente) { amicizia ->
                navController.navigate("utente/${notifica.mittente}/${amicizia}/notifiche/2/0/Richiesta_Amicizia")
            }
        }
        "Accetta_Amicizia" -> navController.navigate(Schermate.Profilo.route)
        "Richiesta_Progetto" -> navController.navigate("progetto_da_accettare/${notifica.progettoId}/notifiche/progetto")
        "Assegnazione_Task", "Entra_Progetto", "Completamento_Task", "Modifica_Task" -> navController.navigate("progetto/${notifica.progettoId}")
    }
}

/**
 * Gestisce il click sull'icona di check nelle notifiche per accettare una richiesta di progetto o amicizia.
 *
 * @param navController Il controller per la navigazione tra le schermate.
 * @param notifica La notifica selezionata.
 * @param userProfile Il profilo utente corrente.
 * @param vmNotifiche Il ViewModel delle notifiche.
 * @param viewmodelProgetto Il ViewModel del progetto.
 * @param viewModelUtente Il ViewModel dell'utente.
 * @param listap La lista dei partecipanti al progetto.
 * @param nomeProgetto Il nome del progetto associato alla notifica.
 */
fun gestisciCheckNotifica(navController: NavHostController, notifica: Notifiche, userProfile: ProfiloUtente, vmNotifiche: ViewModelNotifiche, viewmodelProgetto: ViewModelProgetto, viewModelUtente: ViewModelUtente, listap: List<String>?, nomeProgetto: String) {
    if (notifica.tipo == "Richiesta_Progetto") {
        vmNotifiche.cambiastatoAccettatoNotifica(notifica.id)
        viewmodelProgetto.aggiungiPartecipanteAlProgetto(notifica.progettoId, userProfile.id)

        listap?.forEach { p ->
            if (p != userProfile.id) {
                val contenuto = "${userProfile.nome} ${userProfile.cognome} è entrato nel progetto $nomeProgetto"
                vmNotifiche.creaNotifica(userProfile.id, p, "Entra_Progetto", contenuto, notifica.progettoId)
            }
        }

        navController.navigate(Schermate.Notifiche.route)
    } else {
        viewModelUtente.faiAmicizia(userProfile.id, notifica.mittente) {
            viewModelUtente.getUserProfile()
            vmNotifiche.cambiastatoAccettatoNotifica(notifica.id)

            viewModelUtente.ottieniUtente(userProfile.id) {
                val contenuto = "${userProfile.nome} ${userProfile.cognome} ha accettato la tua richiesta di amicizia"
                vmNotifiche.creaNotifica(userProfile.id, notifica.mittente, "Accetta_Amicizia", contenuto, "")
            }
            navController.navigate(Schermate.Notifiche.route)
        }
    }
}
