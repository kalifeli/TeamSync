package com.example.teamsync.caratteristiche.leMieAttivita.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.autentificazione.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.autentificazione.data.repository.RepositoryUtente
import com.example.teamsync.caratteristiche.autentificazione.data.viewModel.ViewModelUtente
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.model.Progetto
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
import com.example.teamsync.caratteristiche.leMieAttivita.data.model.LeMieAttivita
import com.example.teamsync.caratteristiche.leMieAttivita.data.repository.ToDoRepository
import com.example.teamsync.caratteristiche.leMieAttivita.data.viewModel.LeMieAttivitaViewModel
import com.example.teamsync.caratteristiche.notifiche.data.viewModel.ViewModelNotifiche
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.util.NoInternetScreen
import com.example.teamsync.util.Priorita
import com.example.teamsync.util.ThemePreferences
import com.example.teamsync.util.isInternetAvailable
import com.example.teamsync.util.theme.Green50
import com.example.teamsync.util.theme.Grey20
import com.example.teamsync.util.theme.Grey35
import com.example.teamsync.util.theme.Grey50
import com.example.teamsync.util.theme.Grey70
import com.example.teamsync.util.theme.Red70
import com.example.teamsync.util.theme.White
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


/**
 * Composable principale per la gestione dell'interfaccia utente delle attività.
 *
 * @param navController Controller di navigazione per spostarsi tra le schermate.
 * @param viewModel ViewModel per la gestione delle attività.
 * @param viewModelUtente ViewModel per la gestione del profilo utente.
 * @param viewmodelprogetto ViewModel per la gestione del progetto.
 * @param idProg ID del progetto corrente.
 * @param viewModelNotifiche ViewModel per la gestione delle notifiche.
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("StateFlowValueCalledInComposition")
@ExperimentalMaterial3Api
@Composable
fun LeMieAttivitaUI(navController: NavHostController, viewModel: LeMieAttivitaViewModel, viewModelUtente: ViewModelUtente, viewmodelprogetto: ViewModelProgetto, idProg: String, viewModelNotifiche: ViewModelNotifiche) {

    // Stato delle variabili di composizione
    val utente by viewModelUtente.userProfilo.observeAsState()
    val coroutineScope = rememberCoroutineScope()
    var addTodoDialog by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }
    val currentTodoItem = remember { mutableStateOf<LeMieAttivita?>(null) }
    var dialogComplete by remember { mutableStateOf(false) }
    val isClicked = remember { mutableStateOf(true) }
    val isClicked1 = remember { mutableStateOf(false) }
    val isClicked2 = remember { mutableStateOf(false) }
    var sezione by remember { mutableIntStateOf(1) }
    var carica by remember { mutableStateOf(false) }
    var isCompletedSection by remember { mutableStateOf(false) }
    val progettoNome = remember { mutableStateOf("") }
    val progetto = remember { mutableStateOf<Progetto?>(null) }
    var isLoadingNonCompletate by remember { mutableStateOf(false) }
    var isLoadingCompletate by remember { mutableStateOf(true) }
    var isLoadingNonCompletateUtente by remember { mutableStateOf(true) }
    val partecipanti = remember { mutableStateOf<List<String>>(emptyList()) }
    val progettoCompletato = remember { mutableStateOf(false) }
    val progressione by viewModel.progressione.observeAsState()
    val attivitaCompletate by viewModel.attivitaCompletate.observeAsState()
    val attivitaTotali by viewModel.attivitaTotali.observeAsState()
    var expended by remember { mutableStateOf(false) }
    var mostraDialogAbbandono by remember { mutableStateOf(false) }
    var mostraDialogCodiceProgetto by remember { mutableStateOf(false) }
    val contesto = LocalContext.current
    val preferences = contesto.getSharedPreferences("preferenze_task", Context.MODE_PRIVATE)
    val isDarkTheme = ThemePreferences.getTheme(contesto)
    val caricamentoProgetto by viewmodelprogetto.isLoading.observeAsState()
    val caricamentoAttivita by viewModel.isLoading.observeAsState()
    val isConnected = remember { mutableStateOf(isInternetAvailable(contesto)) }
    val erroreAbbandonaProgetto by viewmodelprogetto.erroreAbbandonaProgetto.observeAsState()
    val abbandonaProgettoRiuscito by viewmodelprogetto.abbandonaProgettoRiuscito.observeAsState()
    val isLoading by viewmodelprogetto.isLoading.observeAsState()

    val ordine by remember { mutableStateOf(preferences.getString("ordine_task", "creazione")) }


    // Effetto per caricare i dati del progetto
    LaunchedEffect(idProg) {
        progetto.value = viewmodelprogetto.getProgettoById(idProg)
        if(progetto.value != null){
            progettoNome.value = progetto.value!!.nome
            partecipanti.value = progetto.value!!.partecipanti
            progettoCompletato.value = progetto.value!!.completato
            viewModel.getNonCompletedTodoByProject(idProg)
            viewModel.getTodoCompletateByProject(idProg)
        }
    }

    // Effetto per controllare periodicamente la connessione a internet
    LaunchedEffect(Unit) {
        // Periodicamente avviene un controllo per verificare che ci sia la connessione ad internet
        while(true){
            isConnected.value = isInternetAvailable(contesto)
            delay(5000) // controllo ogni 5 secondi
        }
    }

    // Effetto per aggiornare la lista dei partecipanti quando cambia
    LaunchedEffect(viewmodelprogetto.cambiaListaPartecipanti.value) {
        if (viewmodelprogetto.cambiaListaPartecipanti.value) {
            viewModel.getAllTodoByProject(idProg)
            val progettoId = viewModel.leMieAttivita.firstOrNull()?.progetto

            if (progettoId != null) {
                partecipanti.value = viewmodelprogetto.getListaPartecipanti(progettoId)
            } else {
                Log.d("PartecipantiLog", "Nessuna attività trovata")
            }
        }
    }

    LaunchedEffect(erroreAbbandonaProgetto) {
        erroreAbbandonaProgetto?.let { errore ->
            Toast.makeText(contesto, errore, Toast.LENGTH_LONG).show()
            viewmodelprogetto.resetErroreAbbandonaProgetto()
        }
    }

    LaunchedEffect(abbandonaProgettoRiuscito) {
        if (abbandonaProgettoRiuscito == true) {
            mostraDialogAbbandono = false
            navController.navigate(Schermate.ItuoiProgetti.route) {
                popUpTo(Schermate.ItuoiProgetti.route) { inclusive = true }
            }
            viewmodelprogetto.resetAbbandonaProgettoRiuscito()
        }
    }


    // Comparatore per l'ordinamento delle attività
    val comparatore = Comparator<LeMieAttivita> { attivita1, attivita2 ->
        val priorita1 = attivita1.priorita
        val priorita2 = attivita2.priorita

        // Confronto basato sull'ordine dell'enumerazione Priorità
        when {
            priorita1 == Priorita.ALTA && priorita2 != Priorita.ALTA -> -1 // ALTA prima di qualsiasi altra
            priorita1 != Priorita.ALTA && priorita2 == Priorita.ALTA -> 1  // ALTA prima di qualsiasi altra
            priorita1 == Priorita.MEDIA && priorita2 == Priorita.BASSA -> -1 // MEDIA prima di BASSA
            priorita1 == Priorita.BASSA && priorita2 == Priorita.MEDIA -> 1  // MEDIA prima di BASSA
            else -> 0 // Rimane invariato
        }
    }

    // Effetto per caricare le attività completate
    LaunchedEffect(isClicked1.value) {
        if (isClicked1.value) {
            carica = true
            isLoadingCompletate = true
            isLoadingNonCompletate = true
            isLoadingNonCompletateUtente = true
            viewModel.getTodoCompletateByProject(idProg)
            carica = false
            isLoadingCompletate = false
        }

    }

    // Effetto per caricare le attività non completate
    LaunchedEffect(isClicked.value) {
        if (isClicked.value) {
            carica = true
            isLoadingNonCompletate = true
            isLoadingCompletate = true
            isLoadingNonCompletateUtente = true
            viewModel.getNonCompletedTodoByProject(idProg)
            carica = false
            isLoadingNonCompletate = false
        }
    }

    // Effetto per caricare le attività dell'utente
    LaunchedEffect(isClicked2.value) {
        if (isClicked2.value) {
            carica = true
            isLoadingNonCompletate = true
            isLoadingCompletate = true
            isLoadingNonCompletateUtente = true
            utente?.let {
                viewModel.getTodoUtente(idProg, it.id)
            }
            carica = false
            isLoadingNonCompletateUtente = false

        }
    }

    // Gestione degli errori
    val erroreAggiungiTask by viewModel.erroreAggiungiAttivita.observeAsState()
    LaunchedEffect(erroreAggiungiTask) {
        erroreAggiungiTask?.let { message ->
            Toast.makeText(contesto, message, Toast.LENGTH_LONG).show()
            viewModel.resetErroreAggiungiTask()
        }
    }

    // Dialog per aggiungere una nuova attività
    if (addTodoDialog) {
        AddTodoDialog(
            viewModel = LeMieAttivitaViewModel(ToDoRepository(), RepositoryUtente(contesto)),
            onDismiss = { addTodoDialog = false },
            onSave = { newTodo ->
                coroutineScope.launch {
                    utente?.let {
                        progetto.value?.let { it1 ->
                            viewModel.addTodo(
                                newTodo.titolo,
                                newTodo.descrizione,
                                newTodo.dataScadenza,
                                newTodo.priorita,
                                it.id,
                                idProg,
                                sezione,
                                it1.dataScadenza,
                                contesto
                            )
                        }
                    }
                    addTodoDialog = if (viewModel.erroreAggiungiAttivita.value == null) {
                        // Se il salvataggio è riuscito, chiudi la dialog
                        false
                    } else {
                        // Mostra un messaggio di errore
                        true
                    }

                }
            }
        )
    }


    // Dialog per modificare un'attività esistente
    if (openDialog) {
        progetto.value?.let {
            EditTodoDialog(
                todoItem = currentTodoItem.value!!,
                onDismiss = {
                    openDialog = false
                },
                onSave = { updatedItem ->
                    // Questa parte può essere semplificata ora
                    val fileUri = viewModel.uploadResult.value

                    viewModel.updateTodo(
                        id = updatedItem.id ?: "",
                        titolo = updatedItem.titolo,
                        descrizione = updatedItem.descrizione,
                        dataScad = updatedItem.dataScadenza,
                        priorita = updatedItem.priorita,
                        sezione,
                        idProg,
                        updatedItem.utenti,
                        fileUri = fileUri,
                        contesto
                    )
                    // Non è più necessario gestire l'apertura o chiusura della dialog qui
                },
                navController = navController,
                progettoNome = progettoNome.value,
                viewModelNotifiche = viewModelNotifiche,
                viewModel = viewModel,
                userProfile = utente,
                progetto = it
            )
        }
    }

    // Dialog per completare un'attività
    if (dialogComplete && currentTodoItem.value != null) {
        CompleteDialog(
            sezione,
            onDismiss = { dialogComplete = false },
            onSave = { completeTodo ->
                coroutineScope.launch {
                    viewModel.completeTodo(
                        idTask = completeTodo.id ?: "",
                        completeTodo.completato,
                        sezione,
                        idProg
                    )
                    for (p in partecipanti.value) {
                        if (p != utente?.id) {
                            val contenuto = (utente?.nome
                                ?: " ") + " " + (utente?.cognome
                                ?: " ") + " ha completato una task del progetto: " + progettoNome.value
                            viewModelNotifiche.creaNotifica(
                                utente?.id ?: " ",
                                p,
                                "Completamento_Task",
                                contenuto,
                                idProg
                            )
                        }
                    }
                }
                dialogComplete = false
            },
            item = currentTodoItem.value!!
        )
    }

    // UI principale con la barra superiore e il contenuto della schermata
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    if (caricamentoProgetto == false && isConnected.value) {
                        Text(
                            text = progettoNome.value,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = if(isDarkTheme)Color.White else Color.Black
                        )
                    }
                },
                navigationIcon = {
                    if (caricamentoProgetto == false) {
                        IconButton(onClick = { navController.navigate(Schermate.ItuoiProgetti.route) }) {
                            Icon(
                                Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = "torna indietro",
                                tint = if(isDarkTheme)Color.White else Color.Black
                            )
                        }
                    }
                },
                actions = {
                    if ((caricamentoProgetto == false)&& isConnected.value) {
                        Box {
                            IconButton(onClick = { expended = true }) {
                                Icon(
                                    Icons.Default.MoreVert,
                                    contentDescription = "menu a tendina",
                                    tint = if(isDarkTheme)Color.White else Color.Black
                                )
                            }
                            DropdownMenu(
                                expanded = expended,
                                onDismissRequest = { expended = false },
                                modifier = Modifier.background(if(isDarkTheme) Color.DarkGray else Grey20)
                            ) {
                                DropdownMenuItem(
                                    text = { Text(text = stringResource(id = R.string.Condividi),  color = if(isDarkTheme)Color.White else Color.Black) },
                                    onClick = {
                                        expended = false
                                        mostraDialogCodiceProgetto = true
                                    },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.Share,
                                            contentDescription = "condividi progetto",
                                            tint = if (isDarkTheme) White else Color.Black
                                        )
                                    },
                                    modifier = Modifier.background(if(isDarkTheme) Color.DarkGray else Grey20)
                                )
                                DropdownMenuItem(
                                    text = { Text(text = stringResource(id = R.string.Info), color = if(isDarkTheme)Color.White else Color.Black) },
                                    onClick = {
                                        expended = false
                                        navController.navigate("progetto_da_accettare/${idProg}/progetto/progetto")
                                    },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.Info,
                                            contentDescription = "informazioni progetto",
                                            tint = if (isDarkTheme) White else Color.Black
                                        )
                                    },
                                    modifier = Modifier.background(if(isDarkTheme) Color.DarkGray else Grey20)
                                )
                                DropdownMenuItem(
                                    text = { Text(text = stringResource(id = R.string.modificaProgetto), color = if(isDarkTheme)Color.White else Color.Black) },
                                    onClick = {
                                        expended = false
                                        navController.navigate("modificaProgetto/${idProg}")
                                    },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.Create,
                                            contentDescription = "modifica informazioni progetto",
                                            tint = if (isDarkTheme) White else Color.Black
                                        )
                                    },
                                    modifier = Modifier.background(if(isDarkTheme) Color.DarkGray else Grey20)
                                )
                                DropdownMenuItem(
                                    text = { Text(text = stringResource(id = R.string.Abbandona), color = Red70) },
                                    onClick = {
                                        expended = false
                                        mostraDialogAbbandono = true
                                    },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_logout),
                                            contentDescription = "informazioni progetto",
                                            modifier = Modifier.size(20.dp),
                                            tint = Red70
                                        )
                                    },
                                    modifier = Modifier.background(if(isDarkTheme) Color.DarkGray else Grey20)
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = if (isDarkTheme) Color.DarkGray else Color.White,
                    titleContentColor = if (isDarkTheme) Color.DarkGray else Color.White,
                    actionIconContentColor = if (isDarkTheme) Color.DarkGray else Color.White,
                )
            )
        },
        floatingActionButton = {
            // Pulsante per aggiungere una nuova attività
            if ((sezione == 1 || sezione == 2) && !progettoCompletato.value && caricamentoProgetto == false) {
                FloatingActionButton(
                    containerColor = Red70,
                    shape = FloatingActionButtonDefaults.shape,
                    modifier = Modifier
                        .padding(8.dp),
                    onClick = { addTodoDialog = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "add Todo",
                        tint = Color.White
                    )
                }
            }
        }
    ) { padding ->
        if(!isConnected.value){
            // Schermata mostrata se non c'è connessione internet
            Box(
                modifier = Modifier
                    .background(if (isDarkTheme) Color.Black else White)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .background(if (isDarkTheme) Color.DarkGray else Color.White),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NoInternetScreen(
                        isDarkTheme = isDarkTheme,
                        onRetry = {isConnected.value = isInternetAvailable(contesto) }
                    )
                }
            }
        }
        else if (caricamentoProgetto == true || caricamentoAttivita == true) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(if (isDarkTheme) Color.DarkGray else Color.White)
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.CaricamentoAttivita),
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isDarkTheme) Color.White else Color.Black,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    CircularProgressIndicator(
                        color = Grey50,
                        trackColor = Red70,
                        strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap
                    )
                }
            }
        } else if(progettoCompletato.value){
            progetto.value?.let {
                ProgettoCompletatoScreen(
                    progetto = it,
                    partecipanti = it.partecipanti,
                    viewModelUtente = viewModelUtente,
                    isDarkTheme = isDarkTheme,
                    paddingValues = padding,
                )
            }

        }else if (caricamentoProgetto == false) {
            // Schermata principale se c'è connessione internet e i dati sono caricati
            Box(
                modifier = Modifier
                    .background(if (isDarkTheme) Color.DarkGray else Color.White)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .background(if (isDarkTheme) Color.DarkGray else Color.White),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // una piccola descrizione della schermata
                    Text(
                        textAlign = TextAlign.Center,
                        text = stringResource(id = R.string.project_tasks_description),
                        style = MaterialTheme.typography.labelLarge,
                        color = if (isDarkTheme)Color.White else Grey70,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

                    // Divisore orizzontale
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        color = Red70,
                        thickness = 2.dp
                    )

                    // Card di riepilogo delle attività
                    progressione?.let { attivitaCompletate?.let { it1 ->
                        attivitaTotali?.let { it2 ->
                            ProgressiProgettoCard(it,
                                it1, it2
                            )
                        }
                    } }

                    // Pulsanti di selezione delle attività
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Start)
                    ) {
                        Button(
                            onClick = {
                                viewModel.getTodoCompletateByProject(idProg)
                                isCompletedSection = true
                                isClicked1.value = true
                                isClicked.value = false
                                isClicked2.value = false
                                sezione = 0
                            },
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isClicked1.value) Red70 else if(isDarkTheme) Color.Black else Grey35,
                                contentColor = if (isClicked1.value) White else if(isDarkTheme) White else Color.Black
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.bottoneCompletate),
                                fontSize = 12.sp
                            )
                        }
                        Button(
                            onClick = {
                                viewModel.getNonCompletedTodoByProject(idProg)
                                isCompletedSection = false
                                isClicked.value = true
                                isClicked1.value = false
                                isClicked2.value = false
                                sezione = 1
                            },
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isClicked.value) Red70 else if(isDarkTheme) Color.Black else Grey35,
                                contentColor = if (isClicked.value) White else if(isDarkTheme) White else Color.Black
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.bottoneNonCompletate),
                                fontSize = 12.sp,
                            )
                        }
                        Button(
                            onClick = {
                                utente?.let {
                                    viewModel.getTodoUtente(idProg, it.id)
                                }
                                isCompletedSection = false
                                isClicked.value = false
                                isClicked1.value = false
                                isClicked2.value = true
                                sezione = 2
                            },
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isClicked2.value) Red70 else if(isDarkTheme) Color.Black else Grey35,
                                contentColor = if (isClicked2.value) White else if(isDarkTheme) White else Color.Black
                            )

                        ) {
                            Text(
                                text = stringResource(id = R.string.bottoneLeMie),
                                fontSize = 12.sp
                            )
                        }
                    }

                    // Indicatore di caricamento
                    if (carica) {
                        CircularProgressIndicator(color =  if(isDarkTheme)Color.White else Color.Black)
                    }

                    // Lista delle attività non completate
                    if (!isLoadingNonCompletate) {
                        when (ordine) {
                            "data_di_scadenza" -> {
                                LazyColumn {
                                    items(viewModel.leMieAttivitaNonCompletate) { attivita ->
                                        TodoItem(
                                            item = attivita,
                                            onDelete = { id ->
                                                viewModel.deleteTodo(id, sezione, idProg)
                                            },
                                            onEdit = { item ->
                                                currentTodoItem.value = item
                                                openDialog = true
                                            },
                                            onComplete = { item ->
                                                currentTodoItem.value = item
                                                dialogComplete = true
                                            },
                                            viewModelUtente,
                                            userProfile = utente
                                        )
                                    }
                                }
                            }

                            "priorità" -> {

                                LazyColumn {
                                    items(
                                        viewModel.leMieAttivitaNonCompletate.sortedWith(
                                            comparatore
                                        )
                                    ) { attivita ->
                                        TodoItem(
                                            item = attivita,
                                            onDelete = { id ->
                                                viewModel.deleteTodo(id, sezione, idProg)
                                            },
                                            onEdit = { item ->
                                                currentTodoItem.value = item
                                                openDialog = true
                                            },
                                            onComplete = { item ->
                                                currentTodoItem.value = item
                                                dialogComplete = true
                                            },
                                            viewModelUtente,
                                            userProfile = utente
                                        )
                                    }
                                }
                            }

                            "creazione" -> {
                                LazyColumn {
                                    items(viewModel.leMieAttivitaNonCompletate.sortedByDescending { it.dataCreazione }) { attivita ->
                                        TodoItem(
                                            item = attivita,
                                            onDelete = { id ->
                                                viewModel.deleteTodo(id, sezione, idProg)
                                            },
                                            onEdit = { item ->
                                                currentTodoItem.value = item
                                                openDialog = true
                                            },
                                            onComplete = { item ->
                                                currentTodoItem.value = item
                                                dialogComplete = true
                                            },
                                            viewModelUtente,
                                            userProfile = utente
                                        )
                                    }
                                }
                            }

                        }

                    }

                    // Lista delle attività non completate per utente
                    if (!isLoadingNonCompletateUtente) {
                        when (ordine) {
                            "data_di_scadenza" -> {
                                LazyColumn {
                                    items(viewModel.leMieAttivitaPerUtente) { attivita ->
                                        TodoItem(
                                            item = attivita,
                                            onDelete = { id ->
                                                viewModel.deleteTodo(id, sezione, idProg)
                                            },
                                            onEdit = { item ->
                                                currentTodoItem.value = item
                                                openDialog = true
                                            },
                                            onComplete = { item ->
                                                currentTodoItem.value = item
                                                dialogComplete = true
                                            },
                                            viewModelUtente,
                                            userProfile = utente
                                        )
                                    }
                                }
                            }

                            "priorità" -> {
                                LazyColumn {
                                    items(viewModel.leMieAttivitaPerUtente.sortedWith(comparatore)) { attivita ->
                                        TodoItem(
                                            item = attivita,
                                            onDelete = { id ->
                                                viewModel.deleteTodo(id, sezione, idProg)
                                            },
                                            onEdit = { item ->
                                                currentTodoItem.value = item
                                                openDialog = true
                                            },
                                            onComplete = { item ->
                                                currentTodoItem.value = item
                                                dialogComplete = true
                                            },
                                            viewModelUtente,
                                            userProfile = utente
                                        )
                                    }
                                }
                            }

                            "creazione" -> {
                                LazyColumn {
                                    items(viewModel.leMieAttivitaPerUtente.sortedByDescending { it.dataCreazione }) { attivita ->
                                        TodoItem(
                                            item = attivita,
                                            onDelete = { id ->
                                                viewModel.deleteTodo(id, sezione, idProg)
                                            },
                                            onEdit = { item ->
                                                currentTodoItem.value = item
                                                openDialog = true
                                            },
                                            onComplete = { item ->
                                                currentTodoItem.value = item
                                                dialogComplete = true
                                            },
                                            viewModelUtente,
                                            userProfile = utente
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Lista delle attività completate
                    if (!isLoadingCompletate) {

                        when (ordine) {
                            "data_di_scadenza" -> {
                                LazyColumn {
                                    items(viewModel.leMieAttivitaCompletate) { attivita ->
                                        TodoItem(
                                            item = attivita,
                                            onDelete = { id ->
                                                viewModel.deleteTodo(id, sezione, idProg)
                                            },
                                            onEdit = { item ->
                                                currentTodoItem.value = item
                                                openDialog = true
                                            },
                                            onComplete = { item ->
                                                currentTodoItem.value = item
                                                dialogComplete = true
                                            },
                                            viewModelUtente,
                                            userProfile = utente
                                        )
                                    }
                                }
                            }

                            "priorità" -> {


                                LazyColumn {
                                    items(viewModel.leMieAttivitaCompletate.sortedWith(comparatore)) { attivita ->
                                        TodoItem(
                                            item = attivita,
                                            onDelete = { id ->
                                                viewModel.deleteTodo(id, sezione, idProg)
                                            },
                                            onEdit = { item ->
                                                currentTodoItem.value = item
                                                openDialog = true
                                            },
                                            onComplete = { item ->
                                                currentTodoItem.value = item
                                                dialogComplete = true
                                            },
                                            viewModelUtente,
                                            userProfile = utente
                                        )
                                    }
                                }
                            }

                            "creazione" -> {
                                LazyColumn {
                                    items(viewModel.leMieAttivitaCompletate.sortedByDescending { it.dataCreazione }) { attivita ->
                                        TodoItem(
                                            item = attivita,
                                            onDelete = { id ->
                                                viewModel.deleteTodo(id, sezione, idProg)
                                            },
                                            onEdit = { item ->
                                                currentTodoItem.value = item
                                                openDialog = true
                                            },
                                            onComplete = { item ->
                                                currentTodoItem.value = item
                                                dialogComplete = true
                                            },
                                            viewModelUtente,
                                            userProfile = utente
                                        )
                                    }

                                }
                            }

                        }

                    }

                }
            }
        }

        // Dialog per abbandonare il progetto
        if (mostraDialogAbbandono) {
            AbbandonaProgettoDialog(
                onDismissRequest = { mostraDialogAbbandono = false },
                viewModelProgetto = viewmodelprogetto,
                progettoId = idProg,
                isLoading!!
            )
        }

        // Dialog per condividere il codice del progetto
        if (mostraDialogCodiceProgetto) {
            CondividiProgettoDialog(
                onDismissRequest = { mostraDialogCodiceProgetto = false },
                viewModelProgetto = viewmodelprogetto,
                contesto = contesto,
                progettoId = idProg
            )
        }

    }
}

/**
 * Composable per rappresentare un singolo elemento Attività.
 *
 * @param item L'attività da visualizzare.
 * @param onDelete Funzione da chiamare quando l'attività viene eliminata.
 * @param onEdit Funzione da chiamare quando l'attività viene modificata.
 * @param onComplete Funzione da chiamare quando l'attività viene completata.
 * @param viewModelUtente ViewModel per la gestione del profilo utente.
 * @param userProfile Profilo dell'utente corrente.
 */
@Composable
fun TodoItem(
    item: LeMieAttivita,
    onDelete: (String) -> Unit,
    onEdit: (LeMieAttivita) -> Unit,
    onComplete: (LeMieAttivita) -> Unit,
    viewModelUtente: ViewModelUtente,
    userProfile: ProfiloUtente?
) {
    var dialogDelete by remember { mutableStateOf(false) }
    var dialogExpanded by remember { mutableStateOf(false) }
    var listaUtenti by remember { mutableStateOf("") }
    val modifica = remember {mutableStateOf(false) }

    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)


    // Effetto per caricare i dati degli utenti partecipanti
    LaunchedEffect(item.utenti) {
        listaUtenti = ""
        val size = item.utenti.size
        var contatore = 0
        for (u in item.utenti) {
            viewModelUtente.ottieniUtente(u) { userProfile ->
                contatore++
                if (userProfile != null) {
                    listaUtenti += if (contatore == size){
                        "${userProfile.nome} ${userProfile.cognome}"
                    }else {
                        "${userProfile.nome} ${userProfile.cognome}\n"
                    }
                    }
                }
            }

        if(userProfile?.let { item.utenti.contains(it.id) } == true)
            modifica.value = true
    }

    // Layout del singolo elemento Attività
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(if (isDarkTheme) Color.Black else Grey35)
        .border(1.dp, color = if (isDarkTheme) White else White, shape = RoundedCornerShape(8.dp))
        .clickable {
            dialogExpanded = true
        }
    ) {
        // Colonna per il pulsante di completamento
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            if (modifica.value) {
                if (!item.completato) {
                    IconButton(onClick = {
                        onComplete(item)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Completata",
                            tint = Green50,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                } else {
                    IconButton(onClick = { onComplete(item) }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Non Completata",
                            tint = Red70,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            } else {
                Spacer(modifier = Modifier.size(50.dp))
            }
        }

        // Colonna per le informazioni dell'attività
        Column(modifier = Modifier
            .weight(1f)
            .padding(vertical = 8.dp)) {
            Text(
                maxLines = 1,
                modifier = Modifier.padding(end = 10.dp),
                text = item.titolo,
                color = if (isDarkTheme)Color.White else Color.Black,
                textAlign = TextAlign.Left,
                fontSize = 16.sp
            )
            Text(
                maxLines = 1,
                modifier = Modifier
                    .padding(end = 10.dp),
                text = item.descrizione,
                color = if (isDarkTheme) Grey35 else Grey70
            )
            Text(
                modifier = Modifier
                    .padding(end = 10.dp),
                text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(item.dataScadenza),
                fontSize = 12.sp,
                color = if (item.dataScadenza <= Date()) Red70 else if (isDarkTheme) Grey20 else Color.Black,
                textAlign = TextAlign.Center
            )
            Text(
                text = listaUtenti,
                color = if(isDarkTheme) Grey35 else Grey70,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .padding(bottom = 3.dp),
            )
            if (item.fileUri != null) {
                Text(
                    text = stringResource(id = R.string.fileAllegato),
                    color = if (isDarkTheme) Color.White else Color.Black,
                )
            }

                Canvas(
                    modifier = Modifier.size(16.dp)
                ) {
                    drawCircle(
                        color = item.priorita.colore,
                        radius = size.minDimension / 2
                    )
                }


            }

            // Colonna per i pulsanti di azione (Elimina e Modifica)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                if (modifica.value) {
                    IconButton(onClick = { dialogDelete = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_delete_24),
                            contentDescription = "Delete",
                            tint = Color.Red
                        )
                    }
                }
                else
                {
                    Spacer(modifier = Modifier.size(25.dp))
                }
                if (dialogDelete) {
                    AlertDialog(
                        containerColor = if(isDarkTheme) Color.Black else Grey35,
                        textContentColor = if (isDarkTheme) White else Color.Black,
                        onDismissRequest = { dialogDelete = false },
                        confirmButton = {
                            Button(
                                onClick = {
                                    onDelete(item.id ?: "")
                                    dialogDelete = false
                                },
                                colors = ButtonDefaults.buttonColors(Red70),
                            ) {
                                Text(stringResource(id = R.string.conferma))
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {dialogDelete = false}) {
                                Text(stringResource(id = R.string.annullaEdit), color = if (isDarkTheme) White else Color.Black)
                            }
                        },
                        title = { Text(stringResource(id = R.string.eliminaTodoTitle), color = if (isDarkTheme) White else Color.Black) },
                        text = { Text(stringResource(id = R.string.eliminaTodoDescrizione), color = if (isDarkTheme) Grey35 else Grey70) },
                    )
                }
                if(modifica.value)
                {
                    IconButton(onClick = { onEdit(item) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_edit_24),
                            contentDescription = "Edit",
                            tint = Grey70
                        )
                    }
                }

            }
        }

        // Dialog espanso per visualizzare i dettagli dell'attività
        if (dialogExpanded) {
            ExpandedDialog(item = item, onDismiss = { dialogExpanded = false }, viewModelUtente)
        }
}


/**
 * Composable per la dialog di modifica di un'attività.
 *
 * @param todoItem L'attività da modificare.
 * @param onDismiss Funzione da chiamare quando il dialogo viene chiuso.
 * @param onSave Funzione da chiamare quando l'attività viene salvata.
 * @param navController Controller di navigazione per spostarsi tra le schermate.
 * @param viewModel ViewModel per la gestione delle attività.
 * @param viewModelNotifiche ViewModel per la gestione delle notifiche.
 * @param progettoNome Nome del progetto associato all'attività.
 * @param userProfile Profilo dell'utente corrente.
 */
@Composable
fun EditTodoDialog(
    todoItem: LeMieAttivita,
    onDismiss: () -> Unit,
    onSave: (LeMieAttivita) -> Unit,
    navController: NavHostController,
    viewModel: LeMieAttivitaViewModel,
    viewModelNotifiche: ViewModelNotifiche,
    progettoNome: String,
    userProfile: ProfiloUtente?,
    progetto: Progetto
) {
    var titolo by remember { mutableStateOf(todoItem.titolo) }
    var descrizione by remember { mutableStateOf(todoItem.descrizione) }
    var dataScadenza by remember { mutableStateOf(todoItem.dataScadenza) }
    var priorita by remember { mutableStateOf(todoItem.priorita) }
    val context = LocalContext.current
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    val coroutineScope = rememberCoroutineScope()
    var fileContent by remember { mutableStateOf<String?>(null) }
    var caricamentoFile by remember { mutableStateOf(false) } // stato per il caricamento del file
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    val maxCharsTitolo = 50
    val maxCharsDescrizione = 1000

    val erroreEditTask by viewModel.erroreEditAttivita.observeAsState()
    val editRiuscito by viewModel.editAttivitaRiuscito.observeAsState()

    var expanded by remember { mutableStateOf(false) }

    val calendar = Calendar.getInstance()
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    var dataScadenzaStr = sdf.format(dataScadenza)
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

    // Gestione degli errori senza chiudere la dialog
    LaunchedEffect(erroreEditTask) {
        erroreEditTask?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            viewModel.resetErroreEditTask()
        }
    }

    // Osserva il successo dell'operazione di salvataggio per chiudere la dialog
    LaunchedEffect(editRiuscito) {
        if (editRiuscito == true) {
            onDismiss()
            viewModel.resetEditAttivitaRiuscito()
        }
    }

    // Launcher per selezionare un file
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedFileUri = it
            viewModel.setFileUri(it)
            caricamentoFile = true // Inizia il caricamento del file
            coroutineScope.launch {
                try {
                    fileContent = viewModel.readFileContent(context, it)
                    caricamentoFile = false // Fine del caricamento del file
                }catch (e: Exception) {
                    caricamentoFile = false // Fine del caricamento in caso di errore
                    Toast.makeText(context, "Errore durante il caricamento del file: ${e.message}", Toast.LENGTH_LONG ).show()
                }
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            launcher.launch("*/*")
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }


    // Dialog di modifica dell'attività
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(id = R.string.modificaAttività), color = if (isDarkTheme) White else Color.Black) },
        containerColor = if(isDarkTheme) Color.Black else Grey35,
        textContentColor = if (isDarkTheme) White else Color.Black,
        text = {
            Column {
                // Campo di input del titolo
                OutlinedTextField(
                    value = titolo,
                    maxLines = 2,
                    onValueChange = {
                        if (it.length <= maxCharsTitolo) {
                        titolo = it
                    } },
                    shape = RoundedCornerShape(16.dp),
                    label = { Text(stringResource(id = R.string.titoloEdit), color = if(isDarkTheme) White else Color.Black) },
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
                )
                Text(
                    text = "${titolo.length} / $maxCharsTitolo",
                    color = if (isDarkTheme) Color.White else Color.Black,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 8.dp)
                )

                // Campo di input della descrizione
                OutlinedTextField(
                    value = descrizione,
                    maxLines = 10,
                    onValueChange = {
                        if (it.length <= maxCharsDescrizione) {
                        descrizione = it
                    } },
                    shape = RoundedCornerShape(16.dp),
                    label = { Text((stringResource(id = R.string.descrizioneEdit)),color = if(isDarkTheme) White else Color.Black,modifier = Modifier.background(Color.Transparent) ) },
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
                )
                Text(
                    text = "${descrizione.length} / $maxCharsDescrizione",
                    color = if (isDarkTheme) Color.White else Color.Black,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 8.dp)
                )

                // Campo di input della data di scadenza
                OutlinedTextField(
                    value = dataScadenzaStr,
                    onValueChange = {dataScadenzaStr = it},
                    shape = RoundedCornerShape(16.dp),
                    readOnly = true,
                    label = { Text(stringResource(id = R.string.inserisciData), color = if(isDarkTheme) White else Color.Black) },
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
                    trailingIcon = {
                        IconButton(
                            onClick = { datePickerDialog.show() },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_calendario_evento),
                                contentDescription = "scegli data di scadenza progetto",
                                modifier = Modifier.size(20.dp),
                                tint = if (isDarkTheme) White else Color.Black)
                        }
                    },
                    textStyle = TextStyle(fontSize = 18.sp, color = if(isDarkTheme) White else Color.Black),
                    placeholder = { Text("dd/MM/yyyy") }
                )

                // Campo di input della priorità
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = priorita.nomeTradotto(),
                        onValueChange = {},
                        readOnly = true,
                        label = {
                            Text(
                                stringResource(id = R.string.priorità),
                                color = if (isDarkTheme) White else Color.Black
                            )
                        },
                        shape = RoundedCornerShape(16.dp),
                        trailingIcon = {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown",
                                modifier = Modifier.clickable { expanded = true })
                        },
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
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(if (isDarkTheme) Color.DarkGray else Grey35)
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
                                        Text(p.nomeTradotto() , color = if (isDarkTheme) White else Color.Black)

                                    }
                                },
                                onClick = {
                                    priorita = p
                                    expanded = false
                                },
                                modifier = Modifier.background(if (isDarkTheme)Color.DarkGray else Grey35)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Pulsante per selezionare un file
                Button(
                    onClick = { permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isDarkTheme) Grey70 else Grey50
                    )
                ) {
                    Text(stringResource(id = R.string.selFile), color = if (isDarkTheme) White else Color.Black)
                }

                if(caricamentoFile){
                    CircularProgressIndicator(color = Red70, modifier = Modifier.padding(top = 8.dp))
                }else  if (selectedFileUri != null) {
                    Text("File Selezionato: ${selectedFileUri!!.lastPathSegment}", color = if (isDarkTheme) White else Color.Black)
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Pulsante per delegare
                Button(
                    onClick = {
                        navController.navigate("task_selezionata/${todoItem.id.toString()}/${todoItem.progetto}")
                    },
                    colors = ButtonDefaults.buttonColors(if (isDarkTheme) Grey70 else Grey50)
                ) {
                    Text(stringResource(id = R.string.delega), color = if (isDarkTheme) White else Color.Black)
                }
            }
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors( containerColor = Red70,
                    contentColor = Color.White),
                onClick = {
                    val updatedTodo = todoItem.copy(
                        titolo = titolo,
                        descrizione = descrizione,
                        dataScadenza = sdf.parse(dataScadenzaStr)!!,
                        priorita = priorita,
                    )
                    coroutineScope.launch {
                        viewModel.uploadFileAndSaveTodo(
                            id = updatedTodo.id ?: "",
                            titolo = updatedTodo.titolo,
                            descrizione = updatedTodo.descrizione,
                            dataScad = updatedTodo.dataScadenza,
                            priorita = priorita,
                            sezione = 1,
                            dataScadenzaProgetto = progetto.dataScadenza,
                            context
                        )

                        for (p1 in updatedTodo.utenti) {
                            if (p1 != userProfile?.id) {
                                val contenuto = "${userProfile?.nome ?: " "} ${userProfile?.cognome ?: " "} ha modificato la vostra task: ${updatedTodo.titolo} del progetto: $progettoNome"
                                viewModelNotifiche.creaNotifica(userProfile?.id ?: " ", p1, "Modifica_Task", contenuto, updatedTodo.progetto)
                            }
                        }
                    }
                    onSave(updatedTodo)
                },
            ) {

                Text(
                    text = stringResource(id = R.string.salvaEdit),
                    style = TextStyle(color = Color.White )
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(id = R.string.annullaEdit), color = if (isDarkTheme) White else Color.Black)
            }
        }
    )
}

/**
 * Composable per la dialog di aggiunta di una nuova attività.
 *
 * @param onDismiss Funzione da chiamare quando il dialogo viene chiuso.
 * @param onSave Funzione da chiamare quando l'attività viene salvata.
 * @param viewModel ViewModel per la gestione delle attività.
 */
@Composable
fun AddTodoDialog(
    onDismiss: () -> Unit,
    onSave: (LeMieAttivita) -> Unit,
    viewModel: LeMieAttivitaViewModel
) {
    var titolo by remember { mutableStateOf("") }
    var descrizione by remember { mutableStateOf("") }
    var dataScadenza by remember { mutableStateOf(Date()) }
    var priorita by remember { mutableStateOf(Priorita.BASSA) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val isDarkTheme = ThemePreferences.getTheme(context)

    val calendar = Calendar.getInstance()
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val dataScadenzaStr = sdf.format(dataScadenza)
    var expanded by remember { mutableStateOf(false) }
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

    val maxCharsTitolo = 50
    val maxCharsDescrizione = 1000

    // Dialog di aggiunta di un'attività
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = if (isDarkTheme) Color.Black else Grey35,
        textContentColor = if (isDarkTheme) White else Color.Black,
        title = { Text(stringResource(id = R.string.aggiungiTodo), color = if (isDarkTheme) White else Color.Black) },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = titolo,
                    maxLines = 2,
                    onValueChange = {
                        if (it.length <= maxCharsTitolo) {
                            titolo = it
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    label = { Text(stringResource(id = R.string.titoloEdit), color = if (isDarkTheme) White else Color.Black) },
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
                    textStyle = TextStyle(fontSize = 18.sp, color = if (isDarkTheme) White else Color.Black),
                    placeholder = { Text(stringResource(id = R.string.titoloEdit)) }
                )
                Text(
                    text = "${titolo.length} / $maxCharsTitolo",
                    color = if (isDarkTheme) Color.White else Color.Black,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 8.dp)
                )
                OutlinedTextField(
                    value = descrizione,
                    maxLines = 10,
                    onValueChange = {
                        if (it.length <= maxCharsDescrizione) {
                            descrizione = it
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    label = { Text(stringResource(id = R.string.descrizioneEdit), color = if (isDarkTheme) White else Color.Black) },
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
                    textStyle = TextStyle(fontSize = 18.sp, color = if (isDarkTheme) White else Color.Black),
                    placeholder = { Text(stringResource(id = R.string.inserisciDescrizione)) }
                )
                Text(
                    text = "${descrizione.length} / $maxCharsDescrizione",
                    color = if (isDarkTheme) Color.White else Color.Black,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 8.dp)
                )
                OutlinedTextField(
                    value = dataScadenzaStr,
                    onValueChange = {},
                    shape = RoundedCornerShape(16.dp),
                    readOnly = true,
                    label = { Text(stringResource(id = R.string.inserisciData), color = if (isDarkTheme) White else Color.Black) },
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
                    trailingIcon = {
                        IconButton(
                            onClick = { datePickerDialog.show() },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_calendario_evento),
                                contentDescription = "scegli data di scadenza progetto",
                                modifier = Modifier.size(20.dp),
                                tint = if (isDarkTheme) White else Color.Black
                            )
                        }
                    },
                    textStyle = TextStyle(fontSize = 18.sp, color = if (isDarkTheme) White else Color.Black),
                    placeholder = { Text("dd/MM/yyyy") }
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = priorita.nomeTradotto(),
                        onValueChange = {},
                        readOnly = true,
                        label = {
                            Text(
                                stringResource(id = R.string.priorità),
                                color = if (isDarkTheme) White else Color.Black
                            )
                        },
                        shape = RoundedCornerShape(16.dp),
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
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Grey35)
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
                                        Text(p.nomeTradotto(), color = if (isDarkTheme) White else Color.Black)

                                    }
                                },
                                onClick = {
                                    priorita = p
                                    expanded = false
                                },
                                modifier = Modifier.background(if (isDarkTheme) Grey70 else Grey35)
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(Red70),
                onClick = {
                    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ITALY)
                    val date = try {
                        simpleDateFormat.parse(dataScadenzaStr)
                    } catch (e: Exception) {
                        null
                    }

                    if (date != null) {
                        coroutineScope.launch {
                            onSave(
                                LeMieAttivita(
                                    titolo = titolo,
                                    descrizione = descrizione,
                                    dataScadenza = date,
                                    priorita = priorita
                                )
                            )
                        }
                    } else {
                        Toast.makeText( context, context.getString(R.string.datiErrati), Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text(stringResource(id = R.string.aggiungiTodo), color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(id = R.string.annullaEdit), color = if (isDarkTheme) White else Color.Black)
            }
        }
    )

    val erroreAggiungiTask by viewModel.erroreAggiungiAttivita.observeAsState()

    LaunchedEffect(erroreAggiungiTask) {
        erroreAggiungiTask?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            viewModel.resetErroreAggiungiTask()
        }
    }
}

/**
 * Composable per il dialogo di completamento di un'attività.
 *
 * @param sezione La sezione a cui appartiene l'attività.
 * @param onDismiss Funzione da chiamare quando il dialogo viene chiuso.
 * @param onSave Funzione da chiamare quando l'attività viene completata.
 * @param item L'attività da completare.
 */
@Composable
fun CompleteDialog(
    sezione: Int,
    onDismiss: () -> Unit,
    onSave: (LeMieAttivita) -> Unit,
    item: LeMieAttivita
) {
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    if (sezione == 1 || sezione == 2) {
        AlertDialog(
            containerColor = if(isDarkTheme) Color.Black else Grey35,
            textContentColor = if (isDarkTheme) White else Color.Black,
            onDismissRequest = onDismiss,
            title = {
                Text(text = stringResource(id = R.string.conferma),color = if (isDarkTheme) White else Color.Black)
            },

            text = {
                Text(text = stringResource(id = R.string.completaTodo),color = if (isDarkTheme) Grey35 else Grey70)
            },
            confirmButton = {
                Button(
                    onClick = {

                        onSave(item)

                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Red70) // Colore di sfondo del pulsante di conferma
                ) {
                    Text(text = stringResource(id = R.string.conferma), color = Color.White) // Testo bianco sul pulsante rosso
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(stringResource(id = R.string.annullaEdit), color = if (isDarkTheme) White else Color.Black)
                }
            }
        )
    }else{
        AlertDialog(
            containerColor = if(isDarkTheme) Color.Black else Grey35,
            textContentColor = if (isDarkTheme) White else Color.Black,
            onDismissRequest = onDismiss,
            title = {
                Text(text = stringResource(id = R.string.conferma),color = if (isDarkTheme) White else Color.Black)
            },

            text = {
                Text(text =  stringResource(id = R.string.noncompletaTodo),color = if (isDarkTheme) Grey35 else Grey70)
            },
            confirmButton = {
                Button(
                    onClick = {
                        onSave(item)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Red70)
                ) {
                    Text(text =  stringResource(id = R.string.conferma), color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(stringResource(id = R.string.annullaEdit), color = if (isDarkTheme) White else Color.Black)
                }
            }
        )
    }
}

/**
 * Composable che consente di visualizzare tutte le informazioni di un'attività.
 *
 * @param item L'attività da visualizzare.
 * @param onDismiss Funzione da chiamare quando il dialogo viene chiuso.
 * @param viewModelUtente ViewModel per la gestione del profilo utente.
 */
@Composable
fun ExpandedDialog(
    item: LeMieAttivita,
    onDismiss: () -> Unit,
    viewModelUtente: ViewModelUtente
) {
    var listaUtenti by remember { mutableStateOf("") }
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    LaunchedEffect(item.utenti) {
        listaUtenti = ""
        val size = item.utenti.size
        var contatore = 0
        for (u in item.utenti) {
            viewModelUtente.ottieniUtente(u) { userProfile ->
                contatore++
                if (userProfile != null) {
                    listaUtenti += if (contatore == size) {
                        "${userProfile.nome} ${userProfile.cognome}"
                    } else {
                        "${userProfile.nome} ${userProfile.cognome}\n"
                    }
                }
            }
        }
    }

    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = if (isDarkTheme) Color.Black else Grey35,
        textContentColor = if (isDarkTheme) White else Color.Black,
        title = {
            Text(
                text = stringResource(id = R.string.DettagliAttività),
                color = if (isDarkTheme) White else Color.Black,
            )
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (isDarkTheme) Color.Black else Grey35)
                    .padding(16.dp)
            ) {
                item {
                    Text(
                        text = stringResource(id = R.string.Titoloduepunti),
                        color = if (isDarkTheme) White else Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = item.titolo,
                        color = if (isDarkTheme) White else Color.Black,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                item {
                    Text(
                        text = stringResource(id = R.string.Descrizioneduepunti),
                        color = if (isDarkTheme) White else Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = item.descrizione,
                        color = if (isDarkTheme) White else Color.Black,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                item {
                    Text(
                        text = stringResource(id = R.string.dataDiCreazioneduepunti),
                        color = if (isDarkTheme) White else Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(item.dataCreazione),
                        color = if (isDarkTheme) White else Color.Black,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                item {
                    Text(
                        text = stringResource(id = R.string.dataDiScadenzaduepunti),
                        color = if (isDarkTheme) White else Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(item.dataScadenza),
                        color = if (isDarkTheme) White else Color.Black,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                item {
                    Text(
                        text = stringResource(id = R.string.Prioritaduepunti),
                        color = if (isDarkTheme) White else Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = item.priorita.nomeTradotto(),
                        color = if (isDarkTheme) White else Color.Black,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                item {
                    Text(
                        text = stringResource(id = R.string.listaPartecipantiDuePunti),
                        color = if (isDarkTheme) White else Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = listaUtenti,
                        color = if (isDarkTheme) White else Color.Black,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                item {
                    Text(
                        text = stringResource(id = R.string.fileAllegatoDuePunti),
                        color = if (isDarkTheme) White else Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    if (item.fileUri != null) {
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                    data = Uri.parse(item.fileUri)
                                }
                                context.startActivity(intent)
                            },
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "File Icon",
                                tint = Color.White,
                            )
                            Text(
                                text = stringResource(id = R.string.bottoneFile),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    } else {
                        Text(
                            text = stringResource(id = R.string.nessunFile),
                            color = if (isDarkTheme) White else Color.Black
                        )
                    }
                }
            }
        },
        confirmButton = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(Grey70),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(text = stringResource(id = R.string.capito), color = Color.White)
                }
            }
        }
    )
}

/**
 * Composable per la card di riepilogo delle attività.
 *
 * @param progress La progressione delle attività completate.
 * @param todoCompletate Numero di attività completate.
 * @param todoNonCompletate Numero di attività non completate.
 */
@Composable
fun ProgressiProgettoCard(progress: Float, todoCompletate: Int, todoNonCompletate: Int) {
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    val context = LocalContext.current

    OutlinedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = if(isDarkTheme) Color.Black else Grey35,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(vertical = 8.dp),


        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tipocheride),
                    contentDescription = "studente che ride",
                    modifier = Modifier
                        .size(200.dp) // da modificare!!!
                        .offset(x = (-16).dp) // da modificare!!!
                )
            }



            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = context.getString(R.string.completed_tasks_count, todoCompletate,todoNonCompletate),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelLarge,
                    color = if (isDarkTheme) White else Color.Black
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.size(64.dp),
                        color = Red70,
                        trackColor = Grey50,
                    )
                    Text(
                        text = "${(progress * 100).toInt()}%",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = if (isDarkTheme) White else Color.Black,
                    )
                }

            }
        }
    }
}

/**
 * Composable per il dialog di abbandono del progetto.
 *
 * @param onDismissRequest Funzione da chiamare quando il dialogo viene chiuso.
 * @param viewModelProgetto ViewModel per la gestione del progetto.
 * @param progettoId ID del progetto da abbandonare.
 */
@Composable
fun AbbandonaProgettoDialog(
    onDismissRequest: () -> Unit,
    viewModelProgetto: ViewModelProgetto,
    progettoId: String,
    isLoading: Boolean
) {
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    val userId = viewModelProgetto.utenteCorrenteId.value


    AlertDialog(
        text = {
            Text(
                text = stringResource(id = R.string.abbandonaProg),
                color = if (isDarkTheme) White else Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center,
            )
        },
        containerColor = if (isDarkTheme) Color.Black else Grey35,
        textContentColor = if (isDarkTheme) White else Color.Black,
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            Button(
                onClick = {
                    if (!isLoading) {
                        viewModelProgetto.abbandonaProgetto(userId, progettoId)
                    }
                },
                colors = ButtonDefaults.buttonColors(Red70),
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Grey50,
                        trackColor = Red70,
                        strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap
                    )
                } else {
                    Text(text = stringResource(id = R.string.Abbandona))
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(
                    text = stringResource(id = R.string.annullaEdit),
                    color = if (isDarkTheme) White else Color.Black,
                )
            }
        }
    )
}

