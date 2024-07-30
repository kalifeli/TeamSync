package com.example.teamsync.caratteristiche.Notifiche.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.Notifiche.data.model.Notifiche
import com.example.teamsync.caratteristiche.Notifiche.data.viewModel.ViewModelNotifiche
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
import com.example.teamsync.caratteristiche.login.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.login.data.repository.RepositoryUtente
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.ui.theme.Grey35
import com.example.teamsync.ui.theme.Grey50
import com.example.teamsync.ui.theme.Red70
import com.example.teamsync.ui.theme.White
import com.example.teamsync.ui.theme.WhiteFacebook
import com.example.teamsync.util.ThemePreferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    viewModel: ViewModelUtente,
    navController: NavHostController,
    notificheModel: ViewModelNotifiche = viewModel()
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(stringResource(id = R.string.nonLette), stringResource(id = R.string.lette))
    val userProfile by viewModel.userProfilo.observeAsState()
    val notificheList by remember { notificheModel.notificheList }
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    val context = LocalContext.current
    val preferences = context.getSharedPreferences("preferenze_notifiche", Context.MODE_PRIVATE)
    val isEntraProgettoSelected by remember { mutableStateOf(preferences.getBoolean("utente_entra_progetto", true)) }
    val isCompletaTaskSelected by remember { mutableStateOf(preferences.getBoolean("utente_completa_task", true)) }
    val isModificaTaskSelected by remember { mutableStateOf(preferences.getBoolean("utente_modifica_mia_task", true)) }

    val erroreEliminazioneNotifiche by notificheModel.erroreEliminazioneNotifiche
    val eliminazioneNotificheStato by notificheModel.eliminazioneNotificheStato
    val erroreLetturaNotifiche by notificheModel.erroreLetturaNotifiche
    val letturaNotificheStato by notificheModel.letturaNotificheStato

    Log.d("SharedPreferences", "5: $isEntraProgettoSelected")
    Log.d("SharedPreferences", "6: $isCompletaTaskSelected")
    Log.d("SharedPreferences", "7: $isModificaTaskSelected")


    LaunchedEffect(Unit) {
        notificheModel.fetchNotifiche()
    }

    LaunchedEffect(erroreEliminazioneNotifiche) {
        if(erroreEliminazioneNotifiche != null){
            Toast.makeText(context, erroreEliminazioneNotifiche, Toast.LENGTH_LONG).show()
            notificheModel.resetErroreEliminazioneNotifiche()
        }

    }

    LaunchedEffect(eliminazioneNotificheStato) {
        if(eliminazioneNotificheStato){
            notificheModel.fetchNotifiche()
            Toast.makeText(context, "Notifiche cancellate con successo!", Toast.LENGTH_LONG).show()
            notificheModel.resetEliminazioneNotificheStato()
        }

    }

    LaunchedEffect(erroreLetturaNotifiche) {
        if(erroreLetturaNotifiche != null){
            Toast.makeText(context, erroreLetturaNotifiche, Toast.LENGTH_LONG).show()
            notificheModel.resetErroreLetturaNotifiche()
        }
    }

    LaunchedEffect(letturaNotificheStato) {
        if(letturaNotificheStato && !notificheList.any { !it.aperto }){
            notificheModel.fetchNotifiche()
            Toast.makeText(context, "Non hai nuove notifiche da leggere!", Toast.LENGTH_LONG).show()
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
                    IconButton(onClick = {
                        // Aggiorna l'elenco delle notifiche
                        notificheModel.fetchNotifiche()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh Notifications",
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = if(isDarkTheme) Color.DarkGray else White,
                    titleContentColor = if(isDarkTheme) White else Color.Black,
                    actionIconContentColor = if(isDarkTheme) White else Color.Black,
                    navigationIconContentColor = if(isDarkTheme) White else Color.Black
                )
            )
        },

    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(color = if (isDarkTheme) Color.DarkGray else White)
        ) {

            // Tabs
            TabRow(
                selectedTabIndex = selectedTab,
                indicator = @Composable { posizioneTab ->
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
                        onClick = {
                            selectedTab = index
                        },
                        text = { Text(text = title.toString()) },
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
            )
            {
                if (notificheModel.isLoading.value) {
                    CircularProgressIndicator(modifier = Modifier.size(40.dp))

                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if(!notificheModel.isLoading.value) {

                if (notificheList.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .background(if(isDarkTheme) Color.Black else White),
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
                            text = "Nessuna notifica disponibile. Prova a controllare più tardi.",
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelLarge,
                            color = if (isDarkTheme) White else Color.Black
                        )
                    }
                } else if (!notificheList.any { !it.aperto } && selectedTab == 0) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .background(if(isDarkTheme) Color.Black else White),
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
                            text = "Non hai notifiche al momento. Torna più tardi per verificare se ci sono novità.",
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelLarge,
                            color = if (isDarkTheme) White else Color.Black
                        )
                    }
                }
            }

            LazyColumn(modifier = Modifier.fillMaxWidth()) {

                item{
                    if(selectedTab == 0 && notificheList.any { !it.aperto }){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.End
                        ){
                            TextButton(
                                onClick = {
                                    notificheList.forEach { notifica ->
                                        notificheModel.cambiaStatoNotifica(notifica.id)
                                    }
                                },
                                enabled = notificheList.isNotEmpty(),
                                border = BorderStroke(0.5.dp, if(isDarkTheme) White else Color.Black),
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = if(isDarkTheme) White else Color.Black,
                                    containerColor = if(isDarkTheme) Color.Black else White,
                                    disabledContainerColor = if(isDarkTheme) Color.DarkGray else Grey35,
                                    disabledContentColor = if(isDarkTheme) White else Grey50,
                                ),
                            ) {
                                Text(text = "Contrassegna come lette")

                            }

                        }
                    }
                }

                item{
                    if(selectedTab == 1 && notificheList.isNotEmpty()){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.End
                        ){
                            TextButton(
                                onClick = { userProfile?.let {
                                    notificheModel.eliminaNotificheUtente(
                                        it.id)
                                } },
                                border = BorderStroke(0.5.dp, if(isDarkTheme) White else Color.Black),
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = if(isDarkTheme) White else Color.Black,
                                    containerColor = if(isDarkTheme) Color.Black else White,
                                ),
                            ) {
                                Text(text = "cancella notifiche")

                            }

                        }
                    }
                }

                // le notifiche vengono ordinate per data di arrivo
                val notificheOrdinata = notificheList.sortedByDescending { it.data }

                items(notificheOrdinata.filter { notifica ->
                    when (selectedTab) {
                        0 -> !notifica.aperto // Non lette
                        1 -> true // Tutte
                        else -> true // Fallback
                    }
                }) { notifica ->
                    if (userProfile != null) {
                        when(notifica.Tipo){
                            "Completamento_Task" -> {
                                if(isCompletaTaskSelected)
                                {
                                    NotificationItem(
                                        iconColor = if (notifica.aperto) WhiteFacebook else  Red70,
                                        notifica = notifica,
                                        navController = navController,
                                        vmNotifiche = notificheModel,
                                        userProfile = userProfile!!
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                            "Modifica_Task" -> {

                                if(isModificaTaskSelected)
                                {
                                    NotificationItem(
                                        iconColor = if (notifica.aperto) WhiteFacebook else  Red70,
                                        notifica = notifica,
                                        navController = navController,
                                        vmNotifiche = notificheModel,
                                        userProfile = userProfile!!
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                            "Entra_Progetto" -> {

                                if(isEntraProgettoSelected)
                                {
                                    NotificationItem(
                                        iconColor = if (notifica.aperto) WhiteFacebook else  Red70,
                                        notifica = notifica,
                                        navController = navController,
                                        vmNotifiche = notificheModel,
                                        userProfile = userProfile!!
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                            else -> {
                                NotificationItem(
                                    iconColor = if (notifica.aperto) WhiteFacebook else  Red70,
                                    notifica = notifica,
                                    navController = navController,
                                    vmNotifiche = notificheModel,
                                    userProfile = userProfile!!
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun NotificationItem(iconColor: Color, notifica: Notifiche, navController: NavHostController, vmNotifiche : ViewModelNotifiche, userProfile: ProfiloUtente) {
    val apertura = remember { mutableStateOf(false) }
    val viewmodelUtente = ViewModelUtente(RepositoryUtente())
    val viewmodelProgetto = ViewModelProgetto()
    var listap by remember { mutableStateOf<List<String>?>(emptyList()) }
    var nomeProgetto by remember { mutableStateOf("") }
    val isLoading by viewmodelProgetto.carica_nome.collectAsState()
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    val loadingRichiestaAmicizia = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewmodelUtente.getUserProfile()
        listap = viewmodelProgetto.getLista_Partecipanti(notifica.progetto_id)
        nomeProgetto = if (notifica.progetto_id.isNotEmpty()) {
            viewmodelProgetto.getnome_progetto(notifica.progetto_id)
        } else {
            "Nome progetto non disponibile"
        }
    }

    // Esegui l'azione di apertura notifica quando apertura cambia a true
    LaunchedEffect(apertura.value) {
        if (apertura.value) {
            vmNotifiche.cambiaStatoNotifica(notifica.id)
        }
    }

        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            onClick = {
                if (!isLoading && !loadingRichiestaAmicizia.value) {
                    apertura.value = true
                    when (notifica.Tipo) {
                        "Richiesta_Amicizia" -> {
                            loadingRichiestaAmicizia.value = true
                            viewmodelUtente.sonoAmici(notifica.mittente) { amicizia ->
                            loadingRichiestaAmicizia.value = false
                                navController.navigate("utente/${notifica.mittente}/${amicizia}/notifiche/2/0/Richiesta_Amicizia")

                            }
                        }

                        "Accetta_Amicizia" -> navController.navigate(Schermate.Profilo.route)
                        "Richiesta_Progetto" -> navController.navigate("progetto_da_accettare/${notifica.progetto_id}/notifiche/progetto")
                        "Assegnazione_Task" -> navController.navigate("progetto/${notifica.progetto_id}")
                        "Entra_Progetto" -> navController.navigate("progetto/${notifica.progetto_id}")
                        "Completamento_Task" -> navController.navigate("progetto/${notifica.progetto_id}")
                        "Modifica_Task" -> navController.navigate("progetto/${notifica.progetto_id}")
                    }
                }

            },
            colors = CardDefaults.outlinedCardColors(
                containerColor = if (isDarkTheme) Color.Black else White
            ),
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
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.icon), // Sostituisci con l'icona della notifica
                            contentDescription = "Notification Icon",
                            tint = if (notifica.aperto) Color.Black else Color.White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = notifica.Contenuto,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isDarkTheme) Color.White else Color.Black
                )
            }


            if (notifica.Tipo == "Richiesta_Amicizia" || notifica.Tipo == "Richiesta_Progetto")
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
                            tint = if(isDarkTheme) White else Color.Gray,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    if (notifica.Tipo == "Richiesta_Progetto") {
                                        vmNotifiche.cambiaStato_Accettato_Notifica(notifica.id)
                                        viewmodelProgetto.aggiungiPartecipanteAlProgetto(
                                            notifica.progetto_id,
                                            userProfile.id
                                        )

                                        for (p in listap!!) {
                                            if (p != userProfile.id) {
                                                val contenuto =
                                                    userProfile.nome + " " + (userProfile.cognome) + " " + "è entrato nel progetto " + nomeProgetto
                                                vmNotifiche.creaNotificaViewModel(
                                                    userProfile.id,
                                                    p,
                                                    "Entra_Progetto",
                                                    contenuto,
                                                    notifica.progetto_id
                                                )
                                            }

                                            navController.navigate(Schermate.Notifiche.route)
                                        }
                                    } else {
                                        viewmodelUtente.fai_amicizia(
                                            userProfile.id,
                                            notifica.mittente
                                        ) {
                                            viewmodelUtente.getUserProfile()
                                            vmNotifiche.cambiaStato_Accettato_Notifica(notifica.id)

                                            viewmodelUtente.ottieni_utente(userProfile.id) {
                                                val contenuto =
                                                    (userProfile.nome) + " " + (userProfile.cognome) + " " + "ha accettato la tua richiesta di amicizia"
                                                vmNotifiche.creaNotificaViewModel(
                                                    userProfile.id,
                                                    notifica.mittente,
                                                    "Accetta_Amicizia",
                                                    contenuto,
                                                    ""
                                                )
                                            }
                                            navController.navigate(Schermate.Notifiche.route)
                                        }
                                    }
                                }
                        )
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    vmNotifiche.cambiaStatoNotifica(notifica.id)
                                },
                            imageVector = Icons.Default.Close, contentDescription = "Rifiuta richiesta",
                            tint = if(isDarkTheme) White else Color.Gray,
                        )
                    }
                }
        }

    }
