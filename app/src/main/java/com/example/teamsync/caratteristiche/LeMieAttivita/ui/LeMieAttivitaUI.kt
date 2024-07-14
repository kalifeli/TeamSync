package com.example.teamsync.caratteristiche.LeMieAttivita.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.MenuDefaults
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
import com.example.teamsync.caratteristiche.Notifiche.data.repository.RepositoryNotifiche
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
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@SuppressLint("StateFlowValueCalledInComposition")
@ExperimentalMaterial3Api
@Composable
fun LeMieAttivitaUI(navController: NavHostController, viewModel: LeMieAttivitaViewModel, viewModelUtente: ViewModelUtente, viewmodelprogetto: ViewModelProgetto,  id_prog: String) {
    viewModel.getTodoByProject(id_prog)
    viewModel.getTodoCompletateByProject(id_prog)
    viewModelUtente.getUserProfile()
    val repoNotifiche = RepositoryNotifiche()
    val utente = viewModelUtente.userProfile
    val coroutineScope = rememberCoroutineScope()
    var addTodoDialog by remember { mutableStateOf(false) }
    val currentTodoItem = remember { mutableStateOf<LeMieAttivita?>(null) }
    var dialogComplete by remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false) }
    val isClicked = remember { mutableStateOf(true) }
    val isClicked1 = remember { mutableStateOf(false) }
    var sezione by remember { mutableIntStateOf(1) }
    val caricanome = remember { mutableStateOf(false) }
    var carica by remember { mutableStateOf(false) }
    var isCompletedSection by remember { mutableStateOf(false) }
    val progetto_nome  = remember { mutableStateOf("") }
    var isLoadingNonCompletate by remember { mutableStateOf(false) }
    var isLoadingCompletate by remember { mutableStateOf(true) }
    var partecipanti = remember { mutableStateOf<List<String>>(emptyList()) }
    val progressione by viewModel.progressione.collectAsState()
    val todoCompletate by viewModel.taskCompletate.collectAsState()
    val todoNonCompletate by viewModel.taskNonCompletate.collectAsState()
    var expended by remember { mutableStateOf(false) }
    var mostraDialogAbbandono by remember { mutableStateOf(false) }
    var mostraDialogCodiceProgetto by remember { mutableStateOf(false) }
    val contesto = LocalContext.current
    val preferences = contesto.getSharedPreferences("preferenze_task", Context.MODE_PRIVATE)
    var ordine by remember { mutableStateOf(preferences.getString("ordine_task", "cronologico" )) }


    LaunchedEffect(Unit) {
        progetto_nome.value = viewmodelprogetto.getnome_progetto(id_prog)
        partecipanti.value = viewmodelprogetto.getLista_Partecipanti(id_prog)
    }

    LaunchedEffect(viewmodelprogetto.cambia_lista_partecipanti.value) {
        if(viewmodelprogetto.cambia_lista_partecipanti.value) {
            viewModel.getAllTodo_BY_Project(id_prog)
            var progettoId = viewModel.leMieAttivita.firstOrNull()?.progetto

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

        // Confronto basato sull'ordine dell'enumerazione Priorità
        when {
            priorita1 == Priorità.ALTA && priorita2 != Priorità.ALTA -> -1 // ALTA prima di qualsiasi altra
            priorita1 != Priorità.ALTA && priorita2 == Priorità.ALTA -> 1  // ALTA prima di qualsiasi altra
            priorita1 == Priorità.MEDIA && priorita2 == Priorità.BASSA -> -1 // MEDIA prima di BASSA
            priorita1 == Priorità.BASSA && priorita2 == Priorità.MEDIA -> 1  // MEDIA prima di BASSA
            else -> 0 // Rimane invariato
        }
    }







    LaunchedEffect(isClicked1.value) {
        if(isClicked1.value)
        {
            carica = true
            isLoadingCompletate = true
            isLoadingNonCompletate = true
            viewModel.getTodoCompletateByProject(id_prog)
            carica = false
            isLoadingCompletate = false
        }

    }

    LaunchedEffect(isClicked.value) {
        if(isClicked.value)
        {
            carica = true
            isLoadingNonCompletate = true
            isLoadingCompletate = true
            viewModel.getTodoByProject(id_prog)
            carica = false
            isLoadingNonCompletate = false
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
            progettoNome = progetto_nome.value


        )
    }

    if (dialogComplete && currentTodoItem.value != null) {
        CompleteDialog(
            sezione,
            onDismiss = { dialogComplete = false },
            onSave = { completeTodo ->
                coroutineScope.launch {
                    // Chiama la funzione completa nel viewModel

                    viewModel.completeTodo(id = completeTodo.id ?: "", completeTodo.completato, sezione,id_prog)
                    for(p in partecipanti.value)
                    {
                        if (p != viewModelUtente.userProfile?.id )
                        {
                            var contenuto = (viewModelUtente.userProfile?.nome ?: " ") + " " + (viewModelUtente.userProfile?.cognome
                                ?: " ") + " ha completato una task del progetto: " + progetto_nome.value
                            repoNotifiche.creaNotifica(viewModelUtente.userProfile?.id ?: " ",p,"Completamento_Task", contenuto, id_prog)
                        }
                    }
                }
                dialogComplete = false
            },
            item = currentTodoItem.value!!
        )
    }

        LaunchedEffect(viewModel.erroreAggiungiTask.value) {
            if(viewModel.erroreAggiungiTask.value != null){
                Toast.makeText(contesto, viewModel.erroreAggiungiTask.value, Toast.LENGTH_LONG).show()
                viewModel.erroreAggiungiTask.value
            }
        }

        LaunchedEffect(viewModel.erroreEditTask.value) {
            if(viewModel.erroreEditTask.value != null){
                Toast.makeText(contesto, viewModel.erroreEditTask.value, Toast.LENGTH_LONG).show()
                viewModel.resetErroreAggiungiTask()
            }

        }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = progetto_nome.value,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Schermate.ItuoiProgetti.route) }) { // non è meglio popBackStack() ???
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "torna indietro", tint = Color.Black)
                    }
                },
                actions = {
                    Box{
                        IconButton(onClick = {expended = true}) {
                            Icon(Icons.Default.MoreVert, contentDescription = "menu a tendina", tint = Color.Black)
                        }
                        DropdownMenu(
                            expanded = expended ,
                            onDismissRequest = { expended = false },
                            modifier = Modifier.background(Grey20)
                        ) {
                            DropdownMenuItem(
                                text = { Text(text = "Condividi") },
                                onClick = {
                                    expended = false
                                    mostraDialogCodiceProgetto = true
                                },
                                leadingIcon = { Icon(Icons.Default.Share, contentDescription = "condividi progetto")},
                                modifier = Modifier.background(Grey20)
                            )
                            DropdownMenuItem(
                                text = { Text(text = "Info Progetto") },
                                onClick = {
                                    expended = false
                                    navController.navigate("progetto_da_accettare/${id_prog}")
                                },
                                leadingIcon = { Icon(Icons.Default.Info, contentDescription = "informazioni progetto")},
                                modifier = Modifier.background(Grey20)
                            )
                            DropdownMenuItem(
                                text = { Text(text = "Modifica Progetto") },
                                onClick = {
                                    expended = false
                                    navController.navigate("modificaProgetto/${id_prog}")
                                },
                                leadingIcon = { Icon(Icons.Default.Create, contentDescription = "modifica informazioni progetto")},
                                modifier = Modifier.background(Grey20)
                            )
                            DropdownMenuItem(
                                text = { Text(text = "Abbandona", color = Red70) },
                                onClick = {
                                    expended = false
                                    mostraDialogAbbandono = true
                                },
                                leadingIcon = { Icon(painter = painterResource(id = R.drawable.ic_logout), contentDescription = "informazioni progetto", modifier = Modifier.size(20.dp), tint = Red70)},
                                modifier = Modifier.background(Grey20)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black,
                )
            )
        }
    ){ padding ->
        Box(
            modifier = Modifier
                .background(White)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(caricanome.value)
                {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(8.dp),
                        color = Grey50,
                        trackColor = Red70,
                        strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap
                    )
                }

                Text(
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.project_tasks_description),
                    style = MaterialTheme.typography.labelLarge,
                    color = Grey70,
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

                Card(progressione,todoCompletate,todoNonCompletate)

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
                            sezione = 0
                        },
                        modifier = Modifier
                            .padding(start = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            if (isClicked1.value) Red70 else Grey35
                        )
                    ) {
                        Text(text = stringResource(id = R.string.bottoneCompletate))
                    }
                    Button(
                        onClick = {
                            viewModel.getTodoByProject(id_prog)
                            isCompletedSection = false
                            isClicked.value = true
                            isClicked1.value = false
                            sezione = 1
                        },
                        modifier = Modifier
                            .padding(start = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            if (isClicked.value) Red70 else Grey35
                        ),

                        ) {
                        Text(text = stringResource(id = R.string.bottoneNonCompletate))
                    }
                }
                if(carica) {
                    CircularProgressIndicator(color = Color.Black)
                }

                if (!isLoadingNonCompletate)
                {
                    when(ordine) {
                        "data_di_scadenza"-> {
                            LazyColumn{
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


                            LazyColumn{
                                items(viewModel.leMieAttivitaNonCompletate.sortedWith(comparatore)) { attivita ->
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
                            LazyColumn{
                                items(viewModel.leMieAttivitaNonCompletate.sortedByDescending{ it.dataCreazione}) { attivita ->
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

                    when(ordine) {
                        "data_di_scadenza"-> {
                            LazyColumn{
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


                            LazyColumn{
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
                            LazyColumn{
                                items(viewModel.leMieAttivitaCompletate.sortedByDescending{ it.dataCreazione}) { attivita ->
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

    if(mostraDialogAbbandono){
        AbbandonaProgettoDialog(
            onDismissRequest = { mostraDialogAbbandono = false },
            viewModelProgetto = viewmodelprogetto,
            navController = navController,
            progettoId = id_prog
        )
    }

    if(mostraDialogCodiceProgetto){
        CondividiProgettoDialog(
            onDismissRequest = { mostraDialogCodiceProgetto = false },
            viewModelProgetto = viewmodelprogetto,
            contesto = contesto,
            progettoId = id_prog
        )
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
        var modifica = remember {mutableStateOf(false) }

        LaunchedEffect(item.utenti) {
            lista_utenti = ""
            var size = item.utenti.size
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
        .background(Grey35)
        .clickable {
            dialogExpanded = true
        }
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            if (!item.completato)
            {
                IconButton(onClick = {
                    onComplete(item)

                })
                {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Completata",
                        tint = Green50,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }else {
                IconButton(onClick = { onComplete(item) }) {
                    Icon(imageVector = Icons.Default.Clear,
                        contentDescription = "Non Completata",
                        tint = Red70,
                        modifier = Modifier.size(15.dp))
                }
            }
        }
        Column(modifier = Modifier
            .weight(1f)
            .padding(vertical = 8.dp)) {
            Text(
                maxLines = 1,
                modifier = Modifier.padding(end = 10.dp),
                text = item.titolo,
                textAlign = TextAlign.Left,
                fontSize = 16.sp
            )
            Text(
                maxLines = 1,
                modifier = Modifier
                    .padding(end = 10.dp),
                text = item.descrizione,
                color = Grey70
            )
            Text(
                modifier = Modifier
                    .padding(end = 10.dp),
                text = SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).format(item.dataScadenza),
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = lista_utenti,
                color = Grey70,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .padding(bottom = 3.dp),
            )
            if (item.fileUri != null) {
                Text(text = "File allegato clicca per visualizzare")
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
                IconButton(onClick = { dialogDelete = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_delete_24),
                        contentDescription = "Delete",
                        tint = Color.Red
                    )
                }
                if (dialogDelete) {
                    AlertDialog(
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
                        title = { Text(stringResource(id = R.string.eliminaTodoTitle)) },
                        text = { Text(stringResource(id = R.string.eliminaTodoDescrizione)) },
                        containerColor = Grey35,
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



@Composable
fun EditTodoDialog(
    todoItem: LeMieAttivita,
    onDismiss: () -> Unit,
    onSave: (LeMieAttivita) -> Unit,
    navController: NavHostController,
    viewModel: LeMieAttivitaViewModel = LeMieAttivitaViewModel(),
    viewModelUtente: ViewModelUtente = ViewModelUtente(),
    repoNotifiche: RepositoryNotifiche = RepositoryNotifiche(),
    progettoNome: String
) {
    var titolo by remember { mutableStateOf(todoItem.titolo) }
    var descrizione by remember { mutableStateOf(todoItem.descrizione) }
    var dataScadenza by remember { mutableStateOf(Date()) }
    val priorita by remember { mutableStateOf(todoItem.priorita) }
    val context = LocalContext.current
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    val uploadResult by viewModel.uploadResult.observeAsState()
    val coroutineScope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedFileUri = it
            viewModel.setFileUri(it)
        }
    }

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

    LaunchedEffect(uploadResult) {

    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Modifica Attività") },
        containerColor = Grey35,
        textContentColor = Grey50,
        text = {
            Column {
                OutlinedTextField(
                    value = titolo,
                    onValueChange = { titolo = it },
                    shape = RoundedCornerShape(16.dp),
                    label = { Text(stringResource(id = R.string.titoloEdit), color = Color.Black) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Grey35,
                        unfocusedContainerColor = Grey50,
                    ),
                )
                OutlinedTextField(
                    value = descrizione,
                    onValueChange = { descrizione = it },
                    shape = RoundedCornerShape(16.dp),
                    label = { Text((stringResource(id = R.string.descrizioneEdit)), color = Color.Black,modifier = Modifier.background(Color.Transparent) ) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Grey35,
                        unfocusedContainerColor = Grey50,
                    ),
                )
                OutlinedTextField(
                    value = dataScadenzaStr,
                    onValueChange = {dataScadenzaStr = it},
                    shape = RoundedCornerShape(16.dp),
                    readOnly = true,
                    label = { Text(stringResource(id = R.string.inserisciData), color = Color.Black) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Grey35,
                        unfocusedContainerColor = Grey50,
                    ),
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
                    textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                    placeholder = { Text("dd/MM/yyyy") }
                )
                // Aggiungi il pulsante per selezionare un file
                Button(onClick = { launcher.launch("*/*") }) {
                    Text("Seleziona File")
                }

                if (selectedFileUri != null) {
                    Text("File Selezionato: ${selectedFileUri!!.lastPathSegment}")
                }

                // Gestisci la priorità se necessario
                // puoi usare un RadioButton o un DropdownMenu per gestire la priorità
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        navController.navigate("task_selezionata/${todoItem.id.toString()}/${todoItem.progetto.toString()}")
                    },
                    colors = ButtonDefaults.buttonColors(Grey70)
                ) {
                    Text("Delega Task")
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
                        priorita = priorita
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
                                repoNotifiche.creaNotifica(viewModelUtente.userProfile?.id ?: " ", p1, "Modifica_Task", contenuto, updatedTodo.progetto)
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
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(Grey70)
            ) {
                Text(stringResource(id = R.string.annullaEdit))
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
        containerColor = Grey35,
        textContentColor = Grey50,
        title = { Text(stringResource(id = R.string.aggiungiTodo)) },
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
                    label = { Text(stringResource(id = R.string.titoloEdit), color = Color.Black) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Grey35,
                        unfocusedContainerColor = Grey50,
                    ),
                    textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                    placeholder = { Text(stringResource(id = R.string.titoloEdit)) }
                )
                OutlinedTextField(
                    value = descrizione,
                    onValueChange = { descrizione = it },
                    shape = RoundedCornerShape(16.dp),
                    label = { Text(stringResource(id = R.string.descrizioneEdit), color = Color.Black) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Grey35,
                        unfocusedContainerColor = Grey50,
                    ),
                    textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                    placeholder = { Text(stringResource(id = R.string.inserisciDescrizione)) }
                )
                OutlinedTextField(
                    value = dataScadenzaStr,
                    onValueChange = {},
                    shape = RoundedCornerShape(16.dp),
                    readOnly = true,
                    label = { Text(stringResource(id = R.string.inserisciData), color = Color.Black) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Grey35,
                        unfocusedContainerColor = Grey50,
                    ),
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
                    textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                    placeholder = { Text("dd/MM/yyyy") }
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = priorita.name,
                        onValueChange = {},
                        shape = RoundedCornerShape(16.dp),
                        label = { Text(stringResource(id = R.string.selPriorita), color = Color.Black) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Grey35,
                            unfocusedContainerColor = Grey50,
                        ),
                        textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { expanded = true }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.width(150.dp)
                    ) {
                        Priorità.entries.forEach { p ->
                            DropdownMenuItem(
                                text = { Text(p.name, color = p.colore) },
                                colors = MenuDefaults.itemColors(Grey50),
                                onClick = {
                                    priorita = p
                                    expanded = false
                                }
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
            Button(onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(Grey70)) {
                Text(stringResource(id = R.string.annullaEdit))
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
    if (sezione == 1) {
        AlertDialog(
            containerColor = Grey35,
            onDismissRequest = onDismiss,
            title = {
                Text(text = stringResource(id = R.string.conferma))
            },

            text = {
                Text(text = stringResource(id = R.string.completaTodo))
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
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(Grey70)
                    // Colore di sfondo del pulsante di annullamento
                ) {
                    Text(
                        text = stringResource(id = R.string.annullaEdit),
                        color = Color.White
                    )
                }
            }
        )
    }else{
        AlertDialog(
            containerColor = Grey35,
            onDismissRequest = onDismiss,
            title = {
                Text(text = stringResource(id = R.string.conferma))
            },

            text = {
                Text(text =  stringResource(id = R.string.noncompletaTodo))
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
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(Grey70)
                ) {
                    Text(
                        text = stringResource(id = R.string.annullaEdit),
                        color = Color.White
                    )
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

    LaunchedEffect(item.utenti) {
        lista_utenti = ""
        var size = item.utenti.size
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
    }

    val context = LocalContext.current


    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Grey35,
        title = { Text(text = "Dettagli Attività", color = Color.Black) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Grey35)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Titolo:",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = item.titolo,
                    color = Color.Black,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Descrizione:",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = item.descrizione,
                    color = Color.Black,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "Data di Creazione:",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(item.dataCreazione),
                    color = Color.Black,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "Data di Scadenza:",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(item.dataScadenza),
                    color = Color.Black,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Priorità:",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = item.priorita.toString(),
                    color = Color.Black,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Lista Partecipanti:",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = lista_utenti,
                    color = Color.Black,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "File Allegato:",
                    color = Color.Black,
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
                            text = "Clicca qui per visualizzare il file",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }else Text(text = "Nessun File allegato....")


            }
        },

        confirmButton = {
            Box(modifier = Modifier.fillMaxWidth())
            {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(Grey70),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(text = "Capito", color = Color.White)
                }
            }
        }
    )
}

@Composable
fun Card(progress: Float, todoCompletate: Int, todoNonCompletate: Int) {
    OutlinedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Grey20
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
                    text = "Hai Completato $todoCompletate/$todoNonCompletate Attività",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Black
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
                        color = Color.Black
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
    val userId = viewModelProgetto.utenteCorrenteId.value
    AlertDialog(
        text = {
            Text(
                text = "Sei sicuro di abbandonare il progetto? Una volta confermato non potrai tornare più indietro!",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center,
            )
        },
        containerColor = White,
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
                    text = "Abbandona"
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ){
                Text(
                    text = "Annulla",
                    color = Color.Black
                )
            }
        }
    )

}

