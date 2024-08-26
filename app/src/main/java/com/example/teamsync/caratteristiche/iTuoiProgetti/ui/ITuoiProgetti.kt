package com.example.teamsync.caratteristiche.iTuoiProgetti.ui


import android.app.DatePickerDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.autentificazione.data.viewModel.ViewModelUtente
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.util.NoInternetScreen
import com.example.teamsync.util.Priorita
import com.example.teamsync.util.ThemePreferences
import com.example.teamsync.util.isInternetAvailable
import com.example.teamsync.util.theme.Grey20
import com.example.teamsync.util.theme.Grey35
import com.example.teamsync.util.theme.Grey50
import com.example.teamsync.util.theme.Red70
import com.example.teamsync.util.theme.White
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Funzione Composable che visualizza la schermata dei progetti dell'utente.
 *
 * @param navController Controller di navigazione per la navigazione tra le schermate.
 * @param viewModelProgetto ViewModel per gestire i dati relativi ai progetti.
 * @param viewModelUtente ViewModel per gestire i dati relativi all'utente.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ITuoiProgetti(
    navController: NavController,
    viewModelProgetto: ViewModelProgetto,
    viewModelUtente: ViewModelUtente,
) {
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    // Stati mutabili per vari componenti dell'interfaccia utente
    var expended by remember { mutableStateOf(false) }
    var mostraCreaProgettoDialog by remember { mutableStateOf(false) }
    val aggiungiProgettoRiuscito = viewModelProgetto.aggiungiProgettoRiuscito.value
    val progettiCompletati by viewModelProgetto.progettiCompletati

    var utenteCorrenteId by viewModelProgetto.utenteCorrenteId
    val utente by viewModelUtente.userProfilo.observeAsState()
    val context = LocalContext.current
    val isLoading by viewModelProgetto.isLoading.observeAsState()

    val progetti by viewModelProgetto.progetti.observeAsState(emptyList())
    val attivitaProgetti by viewModelProgetto.attivitaProgetti.observeAsState(emptyMap())
    val erroreCaricamentoProgetto by viewModelProgetto.erroreCaricamentoProgetto

    val statoAggiuntaPartecipante by viewModelProgetto.statoAggiuntaPartecipante.observeAsState()
    val erroreAggiuntaPartecipante by viewModelProgetto.erroreAggiuntaPartecipante.observeAsState()

    val isConnected = remember { mutableStateOf(isInternetAvailable(context)) }

    // Stato per gestire il logout
    var isLoggedOut by remember { mutableStateOf(false) }

    // Controllo periodico della connessione internet
    LaunchedEffect(Unit) {
        // Periodicamente avviene un controllo per verificare che ci sia la connessione ad internet
        while (true) {
            isConnected.value = isInternetAvailable(context)
            delay(5000) // controllo ogni 5 secondi
        }
    }

    LaunchedEffect(Unit) {
        var retryCount = 0
        while (utente == null && retryCount < 5) {
            delay(2000) // Attendi 2 secondi prima di riprovare
            viewModelUtente.getUserProfile()
            retryCount++
            Log.d("iTuoiProgetti", "Tentativo di caricamento profilo utente #$retryCount")
        }
        if (utente == null) {
            Toast.makeText(context, "Impossibile caricare il profilo utente. Riprova più tardi.", Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(utente) {
        if (utente != null) {
            viewModelProgetto.resetDatiProgetti() // Resetta i dati prima di caricarne di nuovi
            utente?.id?.let {
                utenteCorrenteId = it
                viewModelProgetto.caricaProgettiUtente(it, true)
                viewModelProgetto.caricaProgettiCompletatiUtente(it)
            }
        } else {
            Log.e("iTuoiProgetti", "Utente non caricato correttamente.")
            viewModelUtente.getUserProfile()
        }
    }

    LaunchedEffect(viewModelUtente.isLoading, utente) {
        if (!isLoggedOut && viewModelUtente.isLoading.value == false) {
            // Aspetta un momento per assicurarti che l'utente abbia avuto tempo per essere caricato
            delay(500)
            if (utente == null) {
                Toast.makeText(context, "Errore nel caricamento del profilo utente.", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Visualizza il messaggio di errore se il caricamento del progetto fallisce
    LaunchedEffect(erroreCaricamentoProgetto) {
        if (!erroreCaricamentoProgetto.isNullOrEmpty()) {
            Toast.makeText(context, erroreCaricamentoProgetto, Toast.LENGTH_LONG).show()
            viewModelProgetto.resetErroreCaricamentoProgetto()
        }

    }

    LaunchedEffect(statoAggiuntaPartecipante) {
        statoAggiuntaPartecipante?.let {
            if (it) {
                Toast.makeText(context, "Partecipante aggiunto con successo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(erroreAggiuntaPartecipante) {
        erroreAggiuntaPartecipante?.let {
            Toast.makeText(context, "Errore: $it", Toast.LENGTH_SHORT).show()
        }
    }

    if (utente == null && !isLoggedOut) {
        // Mostra schermata di caricamento fino a quando i dati del profilo utente non sono caricati
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
                    text = "Stiamo preparando il tuo account per l'utilizzo...",
                    color = if (isDarkTheme) Color.White else Color.Black,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    } else {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Home",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = if (isDarkTheme) White else Color.Black,
                        )
                    },

                    actions = {
                        if (isConnected.value) {
                            Box {
                                IconButton(
                                    onClick = { expended = true },
                                    colors = IconButtonDefaults.iconButtonColors(
                                        contentColor = if (isDarkTheme) White else Color.Black,
                                    )
                                ) {
                                    Icon(
                                        Icons.Default.MoreVert,
                                        contentDescription = "Menu a tendina",
                                        tint = if (isDarkTheme) White else Color.Black
                                    )
                                }
                                DropdownMenu(
                                    expanded = expended,
                                    onDismissRequest = { expended = false },
                                    modifier = Modifier.background(if (isDarkTheme) Color.DarkGray else Grey20)
                                ) {
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = stringResource(id = R.string.sincronizza),
                                                color = if (isDarkTheme) White else Color.Black
                                            )
                                        },
                                        onClick = {
                                            expended = false
                                            utente?.id?.let {
                                                viewModelProgetto.caricaProgettiUtente(it, true)
                                                viewModelProgetto.caricaProgettiCompletatiUtente(it)
                                            }
                                        },
                                        leadingIcon = {
                                            Icon(
                                                Icons.Default.Refresh,
                                                contentDescription = "icona sincronizzazione",
                                                tint = if (isDarkTheme) White else Color.Black
                                            )
                                        },
                                        modifier = Modifier.background(if (isDarkTheme) Color.DarkGray else Grey20)
                                    )

                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = stringResource(id = R.string.impostazioni),
                                                color = if (isDarkTheme) White else Color.Black
                                            )
                                        },
                                        onClick = {
                                            expended = false
                                            navController.navigate(Schermate.Impostazioni.route)
                                        },
                                        leadingIcon = {
                                            Icon(
                                                Icons.Default.Settings,
                                                contentDescription = "icona Impostazioni",
                                                tint = if (isDarkTheme) White else Color.Black
                                            )
                                        },
                                        modifier = Modifier.background(if (isDarkTheme) Color.DarkGray else Grey20)
                                    )
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = stringResource(id = R.string.Esci),
                                                color = if (isDarkTheme) White else Color.Black
                                            )
                                        },
                                        onClick = {
                                            isLoggedOut = true
                                            expended = false
                                            viewModelUtente.signOut()
                                            navController.navigate(Schermate.Login.route)
                                        },
                                        leadingIcon = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_logout),
                                                contentDescription = "icona Logout",
                                                tint = if (isDarkTheme) White else Color.Black,
                                                modifier = Modifier.size(20.dp),
                                            )
                                        },
                                        modifier = Modifier.background(if (isDarkTheme) Color.DarkGray else Grey20)
                                    )

                                }
                            }
                        }

                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = if (isDarkTheme) Color.DarkGray else White,
                        titleContentColor = if (isDarkTheme) White else Color.Black,
                        actionIconContentColor = if (isDarkTheme) White else Color.Black,
                        navigationIconContentColor = if (isDarkTheme) White else Color.Black
                    )
                )
            },
            floatingActionButton = {
                if (isConnected.value) {
                    FloatingActionButton(
                        onClick = {
                            mostraCreaProgettoDialog = true
                        },
                        containerColor = Red70,
                        contentColor = White,
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Aggiungi progetto")
                    }
                }
            },
            content = { padding ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(if (isDarkTheme) Color.DarkGray else White)
                        .padding(padding)
                        .padding(horizontal = 16.dp)
                ) {

                    if (!isConnected.value) {
                        NoInternetScreen(
                            isDarkTheme = isDarkTheme,
                            onRetry = { isConnected.value = isInternetAvailable(context) }
                        )
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            if (isLoading == true) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .align(Alignment.Top),
                                    color = Grey50,
                                    trackColor = Red70,
                                    strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap
                                )
                            }
                        }


                        SezioneProfiloUtente(navController, viewModelUtente, isDarkTheme)

                        Spacer(modifier = Modifier.height(16.dp))

                        SezioneITUoiProgetti(
                            navController = navController,
                            progetti = progetti,
                            viewModelProgetto = viewModelProgetto,
                            attivitaProgetti = attivitaProgetti,
                            isDarkTheme = isDarkTheme
                        )

                        Spacer(modifier = Modifier.height(16.dp))


                        Row(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.spacedBy(32.dp)
                        ) {
                            SezioneProgressiProgetti(
                                progettiCompletati = progettiCompletati.size,
                                progettiUtente = progetti.size,
                                isDarkTheme = isDarkTheme
                            )

                            SezioneCalendario(isDarkTheme)
                        }
                    }
                }
            }
        )
    }

    if (mostraCreaProgettoDialog) {
        CreaProgettoDialog(
            onDismissRequest = { mostraCreaProgettoDialog = false },
            viewModelProgetto = viewModelProgetto,
            isDarkTheme = isDarkTheme
        )
    }



    // Visualizza messaggio di successo se la creazione del progetto è riuscita
    LaunchedEffect(viewModelProgetto.erroreAggiungiProgetto.value) {
        if(viewModelProgetto.erroreAggiungiProgetto.value != null){
            Toast.makeText(context, viewModelProgetto.erroreAggiungiProgetto.value, Toast.LENGTH_LONG).show()
            viewModelProgetto.resetErroreAggiungiProgetto()
        }
    }
    val progettoAggiuntoString = context.getString(R.string.ProgettoAggiunto)

    LaunchedEffect(aggiungiProgettoRiuscito) {
        if(aggiungiProgettoRiuscito){
            Toast.makeText(context, progettoAggiuntoString, Toast.LENGTH_LONG).show()
            utente?.id?.let {
                viewModelProgetto.caricaProgettiUtente(it, false)
                viewModelProgetto.caricaProgettiCompletatiUtente(it)
            }
            viewModelProgetto.resetAggiugniProgettoRiuscito()
            mostraCreaProgettoDialog = false
        }
    }

}

/**
 * Funzione Composable che visualizza il dialog per creare un nuovo progetto.
 *
 * @param onDismissRequest Funzione da chiamare quando il dialog viene chiuso.
 * @param viewModelProgetto ViewModel per gestire i dati relativi ai progetti.
 * @param isDarkTheme Booleano per determinare se il tema scuro è attivo.
 */
@Composable
fun CreaProgettoDialog(
    onDismissRequest: () -> Unit,
    viewModelProgetto: ViewModelProgetto,
    isDarkTheme: Boolean
) {
    var nome by remember { mutableStateOf("") }
    var descrizione by remember { mutableStateOf("") }
    var dataScadenza by remember { mutableStateOf(Date()) }
    var priorita by remember { mutableStateOf(Priorita.NESSUNA) }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var mostraAggiungiProgetto by remember {
        mutableStateOf(false)
    }

    val datePickerDialog = DatePickerDialog(
        context,
        if(isDarkTheme) R.style.CustomDatePickerDialogDark else R.style.CustomDatePickerDialog,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            dataScadenza = calendar.time
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val dataScadenzaStr = sdf.format(dataScadenza)
    val maxCharsNome = 20
    val maxCharsDescrizione = 200

    val isConnected = remember { mutableStateOf(isInternetAvailable(context)) }

    LaunchedEffect(Unit) {
        // Periodicamente avviene un controllo per verificare che ci sia la connessione ad internet
        while(true){
            isConnected.value = isInternetAvailable(context)
            delay(5000) // controllo ogni 5 secondi
        }
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                stringResource(id = R.string.creaNuovoProgetto),
                color = if(isDarkTheme) White else Color.Black
            )
                },
        containerColor = if(isDarkTheme) Color.Black else White,
        text = {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (!isConnected.value) {
                    NoInternetScreen(
                        isDarkTheme = isDarkTheme,
                        onRetry = { isConnected.value = isInternetAvailable(context) }
                    )
                } else {
                    OutlinedTextField(
                        value = nome,
                        onValueChange = {
                            if (it.length <= maxCharsNome) {
                                nome = it
                            }
                        },
                        label = {
                            Text(
                                stringResource(id = R.string.nome),
                            )
                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Red70,
                            unfocusedBorderColor = if (isDarkTheme) White else Color.Black,
                            focusedContainerColor = if (isDarkTheme) Color.Black else White,
                            unfocusedLabelColor = if (isDarkTheme) White else Color.Black,
                            focusedLabelColor = if (isDarkTheme) White else Color.Black,
                            focusedTextColor = if (isDarkTheme) Color.White else Color.Black,
                            unfocusedTextColor = if (isDarkTheme) Color.White else Color.Black,
                            unfocusedTrailingIconColor = if (isDarkTheme) Color.White else Color.Black,
                            unfocusedLeadingIconColor = if (isDarkTheme) Color.White else Color.Black,
                            focusedTrailingIconColor = if (isDarkTheme) Color.White else Color.Black,
                        ),
                        maxLines = 2
                    )
                    Text(
                        text = "${nome.length} / $maxCharsNome",
                        color = if (isDarkTheme) Color.White else Color.Black,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = descrizione,
                        onValueChange = {
                            if (it.length <= maxCharsDescrizione) {
                                descrizione = it
                            }
                        },
                        label = {
                            Text(
                                stringResource(id = R.string.descrizioneEdit)
                            )
                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Red70,
                            unfocusedBorderColor = if (isDarkTheme) White else Color.Black,
                            focusedContainerColor = if (isDarkTheme) Color.Black else White,
                            unfocusedLabelColor = if (isDarkTheme) White else Color.Black,
                            focusedLabelColor = if (isDarkTheme) White else Color.Black,
                            focusedTextColor = if (isDarkTheme) Color.White else Color.Black,
                            unfocusedTextColor = if (isDarkTheme) Color.White else Color.Black,
                            unfocusedTrailingIconColor = if (isDarkTheme) Color.White else Color.Black,
                            unfocusedLeadingIconColor = if (isDarkTheme) Color.White else Color.Black,
                            focusedTrailingIconColor = if (isDarkTheme) Color.White else Color.Black,
                        ),
                        maxLines = 4
                    )
                    Text(
                        text = "${descrizione.length} / $maxCharsDescrizione",
                        color = if (isDarkTheme) Color.White else Color.Black,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = dataScadenzaStr,
                        onValueChange = {},
                        readOnly = true,
                        label = {
                            Text(
                                stringResource(id = R.string.datadiscadenza),
                            )
                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth(),
                        trailingIcon = {
                            IconButton(
                                onClick = { datePickerDialog.show() },
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_calendario_evento),
                                    contentDescription = "scegli data di scadenza progetto",
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Red70,
                            unfocusedBorderColor = if (isDarkTheme) White else Color.Black,
                            focusedContainerColor = if (isDarkTheme) Color.Black else White,
                            unfocusedLabelColor = if (isDarkTheme) White else Color.Black,
                            focusedLabelColor = if (isDarkTheme) White else Color.Black,
                            focusedTextColor = if (isDarkTheme) Color.White else Color.Black,
                            unfocusedTextColor = if (isDarkTheme) Color.White else Color.Black,
                            unfocusedTrailingIconColor = if (isDarkTheme) Color.White else Color.Black,
                            unfocusedLeadingIconColor = if (isDarkTheme) Color.White else Color.Black,
                            focusedTrailingIconColor = if (isDarkTheme) Color.White else Color.Black,
                        ),
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    var expanded by remember { mutableStateOf(false) }
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = priorita.nomeTradotto(),
                            onValueChange = {},
                            readOnly = true,
                            label = {
                                Text(
                                    stringResource(id = R.string.priorità),
                                )
                            },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth(),
                            trailingIcon = {
                                Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown",
                                    modifier = Modifier.clickable { expanded = true })
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Red70,
                                unfocusedBorderColor = if (isDarkTheme) White else Color.Black,
                                focusedContainerColor = if (isDarkTheme) Color.Black else White,
                                unfocusedLabelColor = if (isDarkTheme) White else Color.Black,
                                focusedLabelColor = if (isDarkTheme) White else Color.Black,
                                focusedTextColor = if (isDarkTheme) Color.White else Color.Black,
                                unfocusedTextColor = if (isDarkTheme) Color.White else Color.Black,
                                unfocusedTrailingIconColor = if (isDarkTheme) Color.White else Color.Black,
                                unfocusedLeadingIconColor = if (isDarkTheme) Color.White else Color.Black,
                                focusedTrailingIconColor = if (isDarkTheme) Color.White else Color.Black,
                            ),
                        )

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.background(if (isDarkTheme) Color.Black else Grey20),
                        ) {
                            Priorita.entries.forEach { p ->
                                DropdownMenuItem(
                                    text = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(10.dp)
                                                    .clip(CircleShape)
                                                    .border(
                                                        0.5.dp,
                                                        if (isDarkTheme) White else Color.Black,
                                                        CircleShape
                                                    )
                                                    .background(p.colore)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(p.nomeTradotto())

                                        }
                                    },
                                    onClick = {
                                        priorita = p
                                        expanded = false
                                    },
                                    modifier = Modifier.background(if (isDarkTheme) Color.Black else Grey20),
                                    colors = MenuDefaults.itemColors(
                                        textColor = if (isDarkTheme) White else Color.Black,
                                        leadingIconColor = if (isDarkTheme) White else Color.Black,
                                        trailingIconColor = if (isDarkTheme) White else Color.Black,
                                    )
                                )
                            }
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        color = Red70,
                        thickness = 2.dp
                    )
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.uniscitiAUnProgetto),
                            textAlign = TextAlign.Center,
                            color = if (isDarkTheme) Color.White else Color.Black
                        )
                        Text(
                            text = stringResource(id = R.string.conUnCodice),
                            textDecoration = TextDecoration.Underline,
                            textAlign = TextAlign.Center,
                            color = Red70,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { mostraAggiungiProgetto = true }
                                .padding(horizontal = 2.dp),
                        )
                    }
                }
            }

        },
        confirmButton = {
            if(isConnected.value) {
                Button(
                    onClick = {
                        viewModelProgetto.creaProgetto(
                            nome = nome,
                            descrizione = descrizione,
                            dataScadenza = dataScadenza,
                            priorita = priorita
                        )
                    },
                    colors = ButtonDefaults.buttonColors(Red70)
                ) {
                    Text(stringResource(id = R.string.crea), color = White)
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest,
            ) {
                Text(
                    stringResource(id = R.string.annullaEdit),
                    color = if(isDarkTheme) White else Color.Black)

            }
        }
    )
    // Visualizza la dialog per l'aggiunta di un progetto tramite codice
    if(mostraAggiungiProgetto){
        AggiungiProgettoDialog(
            onDismissRequest = { mostraAggiungiProgetto = false },
            viewModelProgetto = viewModelProgetto ,
            isDarkTheme = isDarkTheme
        )
    }

    // Visualizza messaggio di errore se aggiungere un progetto fallisce
    LaunchedEffect(viewModelProgetto.erroreAggiungiProgetto.value) {
        if(viewModelProgetto.erroreAggiungiProgetto.value != null){
            Toast.makeText(context, viewModelProgetto.erroreAggiungiProgetto.value, Toast.LENGTH_LONG).show()
            viewModelProgetto.resetErroreAggiungiProgetto()
        }
    }
}

/**
 * Funzione Composable che visualizza il dialog per aggiungere un progetto esistente tramite codice.
 *
 * @param onDismissRequest Funzione da chiamare quando il dialog viene chiuso.
 * @param viewModelProgetto ViewModel per gestire i dati relativi ai progetti.
 * @param isDarkTheme Booleano per determinare se il tema scuro è attivo.
 */
@Composable
fun AggiungiProgettoDialog(
    onDismissRequest: () -> Unit,
    viewModelProgetto: ViewModelProgetto,
    isDarkTheme: Boolean,
){
    var codiceProgetto by remember { mutableStateOf("") }
    val utenteId = viewModelProgetto.utenteCorrenteId.value
    AlertDialog(
        title = {
            Text(
                text = stringResource(id = R.string.AggiungiUnProgetto),
                color = if(isDarkTheme) White else Color.Black
            )
        },
        containerColor = if(isDarkTheme) Color.Black else White,
        text = {
            Column {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = stringResource(id = R.string.perUnirti),
                    textAlign = TextAlign.Center,
                    color = if(isDarkTheme) White else Color.Black
                )
                OutlinedTextField(
                    value = codiceProgetto ,
                    onValueChange = {codiceProgetto = it},
                    label = {
                        Text(
                            stringResource(id = R.string.codiceProgetto),
                            color = if(isDarkTheme) White else Color.Black
                        ) },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Red70,
                        unfocusedBorderColor = if(isDarkTheme) White else Color.Black,
                        focusedContainerColor = if(isDarkTheme) Color.Black else White ,
                        unfocusedLabelColor = if(isDarkTheme) White else Color.Black,
                        focusedLabelColor = if(isDarkTheme) White else Color.Black,
                        focusedTextColor = if(isDarkTheme) Color.White else Color.Black,
                        unfocusedTextColor = if(isDarkTheme) Color.White else Color.Black,
                        unfocusedTrailingIconColor = if(isDarkTheme) Color.White else Color.Black,
                        unfocusedLeadingIconColor = if(isDarkTheme) Color.White else Color.Black,
                        focusedTrailingIconColor = if(isDarkTheme) Color.White else Color.Black,
                    ),
                    maxLines = 1
                )
            }
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = {
                    utenteId?.let {
                        viewModelProgetto.aggiungiPartecipanteConCodice(it,codiceProgetto)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Red70,
                    contentColor = White,
                    disabledContainerColor = if(isDarkTheme) White else Grey35,
                    disabledContentColor = Color.Black
                ),
                enabled = codiceProgetto.length >= 8
            ) {
                Text(text = stringResource(id = R.string.unisciti))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ){
                Text(
                    text = stringResource(id = R.string.annullaEdit),
                    color = if(isDarkTheme) White else Color.Black
                )
            }
        }
    )
}



