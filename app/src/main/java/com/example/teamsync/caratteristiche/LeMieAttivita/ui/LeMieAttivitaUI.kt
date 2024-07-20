package com.example.teamsync.caratteristiche.LeMieAttivita.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.Manifest
import android.content.Context
import android.os.Build
import com.google.accompanist.permissions.*
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
import androidx.compose.material3.ButtonColors
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
import androidx.compose.runtime.collectAsState
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.LeMieAttivita.data.model.LeMieAttivita
import com.example.teamsync.caratteristiche.LeMieAttivita.data.viewModel.LeMieAttivitaViewModel
import com.example.teamsync.caratteristiche.Notifiche.data.viewModel.ViewModelNotifiche
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente
import com.example.teamsync.data.models.Priorità
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.ui.theme.Green50
import com.example.teamsync.ui.theme.Grey20
import com.example.teamsync.ui.theme.Grey35
import com.example.teamsync.ui.theme.Grey50
import com.example.teamsync.ui.theme.Grey70
import com.example.teamsync.ui.theme.Red70
import com.example.teamsync.ui.theme.White
import com.example.teamsync.util.ThemePreferences
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("StateFlowValueCalledInComposition")
@ExperimentalMaterial3Api
@Composable
fun LeMieAttivitaUI(navController: NavHostController, viewModel: LeMieAttivitaViewModel, viewModelUtente: ViewModelUtente, viewmodelprogetto: ViewModelProgetto,  id_prog: String, viewModelNotifiche: ViewModelNotifiche) {
    viewModel.getTodoByProject(id_prog)
    viewModel.getTodoCompletateByProject(id_prog)
    viewModelUtente.getUserProfile()
    val utente = viewModelUtente.userProfile
    val coroutineScope = rememberCoroutineScope()
    var addTodoDialog by remember { mutableStateOf(false) }
    val currentTodoItem = remember { mutableStateOf<LeMieAttivita?>(null) }
    var dialogComplete by remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false) }
    val isClicked = remember { mutableStateOf(true) }
    val isClicked1 = remember { mutableStateOf(false) }
    val isClicked2 = remember { mutableStateOf(false) }
    var sezione by remember { mutableIntStateOf(1) }
    val caricanome = remember { mutableStateOf(true) }
    var carica by remember { mutableStateOf(false) }
    var isCompletedSection by remember { mutableStateOf(false) }
    val progetto_nome = remember { mutableStateOf("") }
    var isLoadingNonCompletate by remember { mutableStateOf(false) }
    var isLoadingCompletate by remember { mutableStateOf(true) }
    var isLoadingNonCompletateUtente by remember { mutableStateOf(true) }
    val partecipanti = remember { mutableStateOf<List<String>>(emptyList()) }
    val progettoCompletato = remember { mutableStateOf(false) }
    val progressione by viewModel.progressione.collectAsState()
    val attivitaCompletate by viewModel.attivitaCompletate.collectAsState()
    val attivitaTotali by viewModel.attivitaTotali.collectAsState()
    var expended by remember { mutableStateOf(false) }
    var mostraDialogAbbandono by remember { mutableStateOf(false) }
    var mostraDialogCodiceProgetto by remember { mutableStateOf(false) }
    val contesto = LocalContext.current
    val preferences = contesto.getSharedPreferences("preferenze_task", Context.MODE_PRIVATE)
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)


    val ordine by remember { mutableStateOf(preferences.getString("ordine_task", "creazione")) }

    LaunchedEffect(Unit) {
        progetto_nome.value = viewmodelprogetto.getnome_progetto(id_prog)
        partecipanti.value = viewmodelprogetto.getLista_Partecipanti(id_prog)
        progettoCompletato.value = viewmodelprogetto.get_progetto_by_id(id_prog).completato
    }

    LaunchedEffect(viewmodelprogetto.cambia_lista_partecipanti.value) {
        if (viewmodelprogetto.cambia_lista_partecipanti.value) {
            viewModel.getAllTodo_BY_Project(id_prog)
            val progettoId = viewModel.leMieAttivita.firstOrNull()?.progetto

            if (progettoId != null) {
                partecipanti.value = viewmodelprogetto.getLista_Partecipanti(progettoId)
                // Log della lista dei partecipanti
                Log.d("PartecipantiLog", "Partecipanti: $partecipanti")
            } else {
                Log.d("PartecipantiLog", "Nessuna attività trovata")
            }
        }
    }

    val caricaNomeState by viewmodelprogetto.carica_nome.collectAsState()

    LaunchedEffect(caricaNomeState) {
        caricanome.value = caricaNomeState
    }


    val comparatore = Comparator<LeMieAttivita> { attivita1, attivita2 ->
        val priorita1 = attivita1.priorita
        val priorita2 = attivita2.priorita


        when {
            priorita1 == Priorità.ALTA && priorita2 != Priorità.ALTA -> -1 // ALTA prima di qualsiasi altra
            priorita1 != Priorità.ALTA && priorita2 == Priorità.ALTA -> 1  // ALTA prima di qualsiasi altra
            priorita1 == Priorità.MEDIA && priorita2 == Priorità.BASSA -> -1 // MEDIA prima di BASSA
            priorita1 == Priorità.BASSA && priorita2 == Priorità.MEDIA -> 1  // MEDIA prima di BASSA
            else -> 0 // Rimane invariato
        }
    }

    LaunchedEffect(isClicked1.value) {
        if (isClicked1.value) {
            carica = true
            isLoadingCompletate = true
            isLoadingNonCompletate = true
            isLoadingNonCompletateUtente = true
            viewModel.getTodoCompletateByProject(id_prog)
            carica = false
            isLoadingCompletate = false
        }

    }

    LaunchedEffect(isClicked.value) {
        if (isClicked.value) {
            carica = true
            isLoadingNonCompletate = true
            isLoadingCompletate = true
            isLoadingNonCompletateUtente = true
            viewModel.getTodoByProject(id_prog)
            carica = false
            isLoadingNonCompletate = false
        }
    }

    LaunchedEffect(isClicked2.value) {
        if (isClicked2.value) {
            carica = true
            isLoadingNonCompletate = true
            isLoadingCompletate = true
            isLoadingNonCompletateUtente = true
            utente?.let {
                viewModel.getTodoUtente(id_prog, it.id)
            }
            carica = false
            isLoadingNonCompletateUtente = false

        }
    }

    if (addTodoDialog) {
        AddTodoDialog(
            onDismiss = { addTodoDialog = false },
            onSave = { newTodo ->
                coroutineScope.launch {
                    utente?.let {
                        viewModel.addTodo(
                            newTodo.titolo,
                            newTodo.descrizione,
                            newTodo.dataScadenza,
                            newTodo.priorita,
                            newTodo.completato,
                            it.id,
                            id_prog
                        )
                    }
                    addTodoDialog = false
                }
            }
        )
    }


    if (openDialog.value && currentTodoItem.value != null) {

        EditTodoDialog(
            todoItem = currentTodoItem.value!!,
            onDismiss = { openDialog.value = false },
            onSave = { updatedItem ->
                val fileUri = viewModel.uploadResult.value

                viewModel.updateTodo(
                    id = updatedItem.id ?: "",
                    titolo = updatedItem.titolo,
                    descrizione = updatedItem.descrizione,
                    dataScad = updatedItem.dataScadenza,
                    priorita = updatedItem.priorita,
                    sezione,
                    id_prog,
                    updatedItem.utenti,
                    fileUri = fileUri
                )
                openDialog.value = false
            },
            navController,
            progettoNome = progetto_nome.value,
            viewModelNotifiche = viewModelNotifiche


        )
    }

    if (dialogComplete && currentTodoItem.value != null) {
        CompleteDialog(
            sezione,
            onDismiss = { dialogComplete = false },
            onSave = { completeTodo ->
                coroutineScope.launch {
                    // Chiama la funzione completa nel viewModel

                    viewModel.completeTodo(
                        id = completeTodo.id ?: "",
                        completeTodo.completato,
                        sezione,
                        id_prog
                    )
                    for (p in partecipanti.value) {
                        if (p != viewModelUtente.userProfile?.id) {
                            val contenuto = (viewModelUtente.userProfile?.nome
                                ?: " ") + " " + (viewModelUtente.userProfile?.cognome
                                ?: " ") + " ha completato una task del progetto: " + progetto_nome.value
                            viewModelNotifiche.creaNotificaViewModel(
                                viewModelUtente.userProfile?.id ?: " ",
                                p,
                                "Completamento_Task",
                                contenuto,
                                id_prog
                            )
                        }
                    }
                }
                dialogComplete = false
            },
            item = currentTodoItem.value!!
        )
    }

    LaunchedEffect(viewModel.erroreAggiungiTask.value) {
        if (viewModel.erroreAggiungiTask.value != null) {
            Toast.makeText(contesto, viewModel.erroreAggiungiTask.value, Toast.LENGTH_LONG).show()
            viewModel.erroreAggiungiTask.value
        }
    }

    LaunchedEffect(viewModel.erroreEditTask.value) {
        if (viewModel.erroreEditTask.value != null) {
            Toast.makeText(contesto, viewModel.erroreEditTask.value, Toast.LENGTH_LONG).show()
            viewModel.resetErroreAggiungiTask()
        }

    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    if (caricanome.value) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(8.dp),
                            color = Grey50,
                            trackColor = Red70,
                            strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap
                        )
                    }
                    Text(
                        text = progetto_nome.value,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = if(isDarkTheme)Color.White else Color.Black
                    )
                },
                navigationIcon = {
                    if (!(caricanome.value)) {
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
                    if (!(caricanome.value)) {
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
                                        navController.navigate("progetto_da_accettare/${id_prog}/progetto/progetto")
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
                                        navController.navigate("modificaProgetto/${id_prog}")
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
        }
    ) { padding ->
        if (!(caricanome.value)) {
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


                    Text(
                        textAlign = TextAlign.Center,
                        text = stringResource(id = R.string.project_tasks_description),
                        style = MaterialTheme.typography.labelLarge,
                        color = if (isDarkTheme)Color.White else Grey70,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        color = Red70,
                        thickness = 2.dp
                    )

                    Card(progressione, attivitaCompletate, attivitaTotali)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Start)
                    ) {
                        Button(
                            onClick = {
                                viewModel.getTodoCompletateByProject(id_prog)
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
                                if (isClicked1.value) Red70 else if(isDarkTheme) Color.Black else Grey35
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.bottoneCompletate),
                                fontSize = 12.sp,
                                color = if (isDarkTheme)Color.White else Color.Black
                            )
                        }
                        Button(
                            onClick = {
                                viewModel.getTodoByProject(id_prog)
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
                                if (isClicked.value) Red70 else if(isDarkTheme) Color.Black else Grey35
                            ),

                            ) {
                            Text(
                                text = stringResource(id = R.string.bottoneNonCompletate),
                                fontSize = 12.sp,
                                color = if(isDarkTheme) White else Color.Black
                            )
                        }
                        Button(
                            onClick = {
                                utente?.let {
                                    viewModel.getTodoUtente(id_prog, it.id)
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
                                if (isClicked2.value) Red70 else if(isDarkTheme) Color.Black else Grey35
                            ),

                            ) {
                            Text(
                                text = "Le Mie",
                                fontSize = 12.sp,
                                color = if (isDarkTheme)Color.White else Color.Black
                                )
                        }
                    }
                    if (carica) {
                        CircularProgressIndicator(color =  if(isDarkTheme)Color.White else Color.Black)
                    }

                    if (!isLoadingNonCompletate) {
                        when (ordine) {
                            "data_di_scadenza" -> {
                                LazyColumn {
                                    items(viewModel.leMieAttivitaNonCompletate) { attivita ->
                                        TodoItem(
                                            item = attivita,
                                            onDelete = { id ->
                                                viewModel.deleteTodo(id, sezione, id_prog)
                                            },
                                            onEdit = { item ->
                                                currentTodoItem.value = item
                                                openDialog.value = true
                                            },
                                            onComplete = { item ->
                                                currentTodoItem.value = item
                                                dialogComplete = true
                                            },
                                            viewModelUtente
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
                                                viewModel.deleteTodo(id, sezione, id_prog)
                                            },
                                            onEdit = { item ->
                                                currentTodoItem.value = item
                                                openDialog.value = true
                                            },
                                            onComplete = { item ->
                                                currentTodoItem.value = item
                                                dialogComplete = true
                                            },
                                            viewModelUtente
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
                                                viewModel.deleteTodo(id, sezione, id_prog)
                                            },
                                            onEdit = { item ->
                                                currentTodoItem.value = item
                                                openDialog.value = true
                                            },
                                            onComplete = { item ->
                                                currentTodoItem.value = item
                                                dialogComplete = true
                                            },
                                            viewModelUtente
                                        )
                                    }
                                }
                            }

                        }

                    }
                    if (!isLoadingNonCompletateUtente) {
                        when (ordine) {
                            "data_di_scadenza" -> {
                                LazyColumn {
                                    items(viewModel.leMieAttivitaPerUtente) { attivita ->
                                        TodoItem(
                                            item = attivita,
                                            onDelete = { id ->
                                                viewModel.deleteTodo(id, sezione, id_prog)
                                            },
                                            onEdit = { item ->
                                                currentTodoItem.value = item
                                                openDialog.value = true
                                            },
                                            onComplete = { item ->
                                                currentTodoItem.value = item
                                                dialogComplete = true
                                            },
                                            viewModelUtente
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
                                                viewModel.deleteTodo(id, sezione, id_prog)
                                            },
                                            onEdit = { item ->
                                                currentTodoItem.value = item
                                                openDialog.value = true
                                            },
                                            onComplete = { item ->
                                                currentTodoItem.value = item
                                                dialogComplete = true
                                            },
                                            viewModelUtente
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
                                                viewModel.deleteTodo(id, sezione, id_prog)
                                            },
                                            onEdit = { item ->
                                                currentTodoItem.value = item
                                                openDialog.value = true
                                            },
                                            onComplete = { item ->
                                                currentTodoItem.value = item
                                                dialogComplete = true
                                            },
                                            viewModelUtente
                                        )
                                    }
                                }
                            }
                        }
                    }



                    if (!isLoadingCompletate) {

                        when (ordine) {
                            "data_di_scadenza" -> {
                                LazyColumn {
                                    items(viewModel.leMieAttivitaCompletate) { attivita ->
                                        TodoItem(
                                            item = attivita,
                                            onDelete = { id ->
                                                viewModel.deleteTodo(id, sezione, id_prog)
                                            },
                                            onEdit = { item ->
                                                currentTodoItem.value = item
                                                openDialog.value = true
                                            },
                                            onComplete = { item ->
                                                currentTodoItem.value = item
                                                dialogComplete = true
                                            },
                                            viewModelUtente
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
                                                viewModel.deleteTodo(id, sezione, id_prog)
                                            },
                                            onEdit = { item ->
                                                currentTodoItem.value = item
                                                openDialog.value = true
                                            },
                                            onComplete = { item ->
                                                currentTodoItem.value = item
                                                dialogComplete = true
                                            },
                                            viewModelUtente
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
                                                viewModel.deleteTodo(id, sezione, id_prog)
                                            },
                                            onEdit = { item ->
                                                currentTodoItem.value = item
                                                openDialog.value = true
                                            },
                                            onComplete = { item ->
                                                currentTodoItem.value = item
                                                dialogComplete = true
                                            },
                                            viewModelUtente
                                        )
                                    }

                                }
                            }

                        }

                    }

                }
                if (sezione == 1) {
                    FloatingActionButton(
                        containerColor = Red70,
                        shape = FloatingActionButtonDefaults.shape,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
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
        }

        if (mostraDialogAbbandono) {
            AbbandonaProgettoDialog(
                onDismissRequest = { mostraDialogAbbandono = false },
                viewModelProgetto = viewmodelprogetto,
                navController = navController,
                progettoId = id_prog
            )
        }

        if (mostraDialogCodiceProgetto) {
            CondividiProgettoDialog(
                onDismissRequest = { mostraDialogCodiceProgetto = false },
                viewModelProgetto = viewmodelprogetto,
                contesto = contesto,
                progettoId = id_prog
            )
        }

    }
}

@Composable
fun TodoItem
            (
                item: LeMieAttivita,
                onDelete: (String) -> Unit,
                onEdit: (LeMieAttivita) -> Unit,
                onComplete: (LeMieAttivita) -> Unit,
                viewModelUtente: ViewModelUtente
            )
    {
        var dialogDelete by remember { mutableStateOf(false) }
        var dialogExpanded by remember { mutableStateOf(false) }
        var lista_utenti by remember { mutableStateOf("") }
        val modifica = remember {mutableStateOf(false) }

        val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)


        LaunchedEffect(item.utenti) {
            lista_utenti = ""
            val size = item.utenti.size
            var contatore = 0
            for (u in item.utenti) {
                viewModelUtente.ottieni_utente(u) { userProfile ->
                    contatore++
                    if (userProfile != null) {
                        if (contatore == size){
                        lista_utenti += "${userProfile.nome} ${userProfile.cognome}"
                        }else{lista_utenti+= "${userProfile.nome} ${userProfile.cognome}\n"}


                    }
                }
            }

            if(viewModelUtente.userProfile?.let { item.utenti.contains(it.id) } == true)
                modifica.value = true
        }


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
                color = if (isDarkTheme) Grey20 else Color.Black,
                textAlign = TextAlign.Center
            )
            Text(
                text = lista_utenti,
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
                        textContentColor = if (isDarkTheme)White else Color.Black,
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
                            Button(
                                onClick = { dialogDelete = false },
                                colors = ButtonDefaults.buttonColors(Grey50),
                            ) {
                                Text(stringResource(id = R.string.annullaEdit))
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

        if (dialogExpanded) {
            ExpandedDialog(item = item, onDismiss = { dialogExpanded = false }, viewModelUtente)
        }
}



@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun EditTodoDialog(
    todoItem: LeMieAttivita,
    onDismiss: () -> Unit,
    onSave: (LeMieAttivita) -> Unit,
    navController: NavHostController,
    viewModel: LeMieAttivitaViewModel = LeMieAttivitaViewModel(),
    viewModelUtente: ViewModelUtente = ViewModelUtente(),
    viewModelNotifiche: ViewModelNotifiche,
    progettoNome: String
) {
    var titolo by remember { mutableStateOf(todoItem.titolo) }
    var descrizione by remember { mutableStateOf(todoItem.descrizione) }
    var dataScadenza by remember { mutableStateOf(Date()) }
    var priorita by remember { mutableStateOf(todoItem.priorita) }
    val context = LocalContext.current
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    val uploadResult by viewModel.uploadResult.observeAsState()
    val coroutineScope = rememberCoroutineScope()
    var fileContent by remember { mutableStateOf<String?>(null) }


    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedFileUri = it
            viewModel.setFileUri(it)
            coroutineScope.launch {
                fileContent = viewModel.readFileContent(context, it)
            }
        }
    }


    var expanded by remember { mutableStateOf(false) }


    val calendar = Calendar.getInstance()
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    var dataScadenzaStr = sdf.format(dataScadenza)
    val datePickerDialog = DatePickerDialog(
        context,
        R.style.CustomDatePickerDialog,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            dataScadenza = calendar.time
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)


    LaunchedEffect(uploadResult) {

    }
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            launcher.launch("*/*")
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(id = R.string.modificaAttività), color = if (isDarkTheme)White else Color.Black) },
        containerColor = if(isDarkTheme) Color.Black else Grey35,
        textContentColor = if (isDarkTheme)White else Color.Black,
        text = {
            Column {
                OutlinedTextField(
                    value = titolo,
                    onValueChange = { titolo = it },
                    shape = RoundedCornerShape(16.dp),
                    label = { Text(stringResource(id = R.string.titoloEdit), color = if(isDarkTheme)White else Color.Black) },
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
                OutlinedTextField(
                    value = descrizione,
                    onValueChange = { descrizione = it },
                    shape = RoundedCornerShape(16.dp),
                    label = { Text((stringResource(id = R.string.descrizioneEdit)),color = if(isDarkTheme)White else Color.Black,modifier = Modifier.background(Color.Transparent) ) },
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
                OutlinedTextField(
                    value = dataScadenzaStr,
                    onValueChange = {dataScadenzaStr = it},
                    shape = RoundedCornerShape(16.dp),
                    readOnly = true,
                    label = { Text(stringResource(id = R.string.inserisciData), color = if(isDarkTheme)White else Color.Black) },
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
                                tint = if (isDarkTheme)White else Color.Black)
                        }
                    },
                    textStyle = TextStyle(fontSize = 18.sp, color = if(isDarkTheme) White else Color.Black),
                    placeholder = { Text("dd/MM/yyyy") }
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = priorita.name,
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
                        Priorità.entries.forEach { p ->
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
                                        Text(p.name , color = if (isDarkTheme)White else Color.Black)

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

                Button(onClick = { permissionLauncher.launch(Manifest.permission.READ_MEDIA_VIDEO) }, colors = ButtonDefaults.buttonColors(
                    containerColor = if (isDarkTheme) Grey70 else Grey50
                )) {
                    Text(stringResource(id = R.string.selFile), color = if (isDarkTheme) White else Color.Black)
                }




                if (selectedFileUri != null) {
                    Text("File Selezionato: ${selectedFileUri!!.lastPathSegment}", color = if (isDarkTheme) White else Color.Black)
                }
                
                Spacer(modifier = Modifier.width(8.dp))
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
                colors = ButtonDefaults.buttonColors(Red70),
                onClick = {
                    val updatedTodo = todoItem.copy(
                        titolo = titolo,
                        descrizione = descrizione,
                        dataScadenza = SimpleDateFormat("dd/MM/yyyy").parse(dataScadenzaStr),
                        priorita = priorita,
                    )
                    coroutineScope.launch {
                        viewModel.uploadFileAndSaveTodo(
                            id = updatedTodo.id ?: "",
                            titolo = updatedTodo.titolo,
                            descrizione = updatedTodo.descrizione,
                            dataScad = updatedTodo.dataScadenza,
                            priorita = priorita,
                            sezione = 1
                        )

                        for (p1 in updatedTodo.utenti) {
                            if (p1 != viewModelUtente.userProfile?.id) {
                                val contenuto = "${viewModelUtente.userProfile?.nome ?: " "} ${viewModelUtente.userProfile?.cognome ?: " "} ha modificato la vostra task: ${updatedTodo.titolo} del progetto: $progettoNome"
                                viewModelNotifiche.creaNotificaViewModel(viewModelUtente.userProfile?.id ?: " ", p1, "Modifica_Task", contenuto, updatedTodo.progetto)
                            }
                        }
                    }
                    onSave(updatedTodo)
                    onDismiss()
                },
            ) {
                Text(stringResource(id = R.string.salvaEdit))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(Grey70)
            ) {
                Text(stringResource(id = R.string.annullaEdit), color = if(isDarkTheme) White else Color.Black)
            }
        }
    )
    LaunchedEffect(viewModel.erroreEditTask.value) {
        if(viewModel.erroreEditTask.value != null){
            Toast.makeText(context, viewModel.erroreEditTask.value, Toast.LENGTH_LONG).show()
            viewModel.resetErroreAggiungiTask()
        }

    }
}


@Composable
fun AddTodoDialog(
    onDismiss: () -> Unit,
    onSave: (LeMieAttivita) -> Unit
) {
    var titolo by remember { mutableStateOf("") }
    var descrizione by remember { mutableStateOf("") }
    var dataScadenza by remember { mutableStateOf(Date()) }
    var priorita by remember { mutableStateOf(Priorità.BASSA) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val calendar = Calendar.getInstance()
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val dataScadenzaStr = sdf.format(dataScadenza)

    var expanded by remember { mutableStateOf(false) }

    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    val datePickerDialog = DatePickerDialog(
        context,
        R.style.CustomDatePickerDialog,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            dataScadenza = calendar.time
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = if(isDarkTheme) Color.Black else Grey35,
        textContentColor = if (isDarkTheme)White else Color.Black,
        title = { Text(stringResource(id = R.string.aggiungiTodo), color = if (isDarkTheme)White else Color.Black) },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = titolo,
                    onValueChange = { titolo = it },
                    shape = RoundedCornerShape(16.dp),
                    label = { Text(stringResource(id = R.string.titoloEdit), color = if(isDarkTheme)White else Color.Black) },
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
                    textStyle = TextStyle(fontSize = 18.sp, color = if(isDarkTheme)White else Color.Black),
                    placeholder = { Text(stringResource(id = R.string.titoloEdit)) }
                )
                OutlinedTextField(
                    value = descrizione,
                    onValueChange = { descrizione = it },
                    shape = RoundedCornerShape(16.dp),
                    label = { Text(stringResource(id = R.string.descrizioneEdit), color = if(isDarkTheme)White else Color.Black) },
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
                    textStyle = TextStyle(fontSize = 18.sp,  color = if(isDarkTheme)White else Color.Black),
                    placeholder = { Text(stringResource(id = R.string.inserisciDescrizione)) }
                )
                OutlinedTextField(
                    value = dataScadenzaStr,
                    onValueChange = {},
                    shape = RoundedCornerShape(16.dp),
                    readOnly = true,
                    label = { Text(stringResource(id = R.string.inserisciData),  color = if(isDarkTheme)White else Color.Black) },
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
                                tint = if (isDarkTheme)White else Color.Black
                            )
                        }
                    },
                    textStyle = TextStyle(fontSize = 18.sp,  color = if(isDarkTheme)White else Color.Black),
                    placeholder = { Text("dd/MM/yyyy") }
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = priorita.name,
                        onValueChange = {},
                        readOnly = true,
                        label = {
                            Text(
                                stringResource(id = R.string.priorità),
                                color = if(isDarkTheme)White else Color.Black
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
                        modifier = Modifier.background(Grey35)
                    ) {
                        Priorità.entries.forEach { p ->
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
                                        Text(p.name, color = if (isDarkTheme)White else Color.Black)

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
                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ITALY)
                    val date = try {
                        sdf.parse(dataScadenzaStr)
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
                            onDismiss()
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

}

@Composable
fun CompleteDialog(
    sezione: Int,
    onDismiss: () -> Unit,
    onSave: (LeMieAttivita) -> Unit,
    item: LeMieAttivita
) {
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    if (sezione == 1) {
        AlertDialog(
            containerColor = if(isDarkTheme) Color.Black else Grey35,
            textContentColor = if (isDarkTheme)White else Color.Black,
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
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red) // Colore di sfondo del pulsante di conferma
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
            textContentColor = if (isDarkTheme)White else Color.Black,
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
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
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

@Composable
fun ExpandedDialog(
    item: LeMieAttivita,
    onDismiss: () -> Unit,
    viewModelUtente: ViewModelUtente
) {
    var lista_utenti by remember { mutableStateOf("") }
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    LaunchedEffect(item.utenti) {
        lista_utenti = ""
        val size = item.utenti.size
        var contatore = 0
        for (u in item.utenti) {
            viewModelUtente.ottieni_utente(u) { userProfile ->
                contatore++
                if (userProfile != null) {
                    if (contatore == size) {
                        lista_utenti += "${userProfile.nome} ${userProfile.cognome}"
                    } else {
                        lista_utenti += "${userProfile.nome} ${userProfile.cognome}\n"
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
                color = if (isDarkTheme) White else Color.Black
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
                        text = item.priorita.toString(),
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
                        text = lista_utenti,
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


@Composable
fun Card(progress: Float, todoCompletate: Int, todoNonCompletate: Int) {
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

@Composable
fun AbbandonaProgettoDialog(
    onDismissRequest : () -> Unit,
    viewModelProgetto: ViewModelProgetto,
    navController: NavController,
    progettoId: String
){
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
        containerColor = if(isDarkTheme) Color.Black else Grey35,
        textContentColor = if (isDarkTheme)White else Color.Black,
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = {
                    onDismissRequest()
                    viewModelProgetto.abbandonaProgetto(userId,progettoId)
                    navController.navigate(Schermate.ItuoiProgetti.route)
                          },
                colors = ButtonDefaults.buttonColors(Red70),
            ) {
                Text(
                    text = stringResource(id = R.string.Abbandona)
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ){
                Text(
                    text = stringResource(id = R.string.annullaEdit),
                    color = if (isDarkTheme) White else Color.Black,
                )
            }
        }
    )

}

