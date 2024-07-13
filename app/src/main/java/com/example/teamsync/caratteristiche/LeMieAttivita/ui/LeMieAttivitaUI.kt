package com.example.teamsync.caratteristiche.LeMieAttivita.ui

import android.app.DatePickerDialog
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.google.rpc.Help.Link
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@ExperimentalMaterial3Api
@Composable
fun LeMieAttivitaUI(navController: NavHostController, viewModel: LeMieAttivitaViewModel, viewModelUtente: ViewModelUtente, viewmodelprogetto: ViewModelProgetto,  id_prog: String) {
    val id_progetto_x = id_prog
    viewModel.getTodoByProject(id_prog)
    viewModel.getTodoCompletateByProject(id_prog)
    viewModelUtente.getUserProfile()
    var repoNotifiche = RepositoryNotifiche()
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
    var progetto_nome  = remember { mutableStateOf("") }
    var isLoadingNonCompletate by remember { mutableStateOf(false) }
    var isLoadingCompletate by remember { mutableStateOf(true) }
    var partecipanti = remember { mutableStateOf<List<String>>(emptyList()) }
    val progressione by viewModel.progressione.collectAsState()
    val todoCompletate by viewModel.taskCompletate.collectAsState()
    val todoNonCompletate by viewModel.taskNonCompletate.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        progetto_nome.value = viewmodelprogetto.getnome_progetto(id_progetto_x)
        partecipanti.value = viewmodelprogetto.getLista_Partecipanti(id_progetto_x)
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

    LaunchedEffect(viewmodelprogetto.carica_nome_progetto.value) {
        if(viewmodelprogetto.carica_nome_progetto.value)
            caricanome.value = true
        else
            caricanome.value = false
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
                dialogComplete = false // Chiudi il dialogo dopo aver salvato
            },
            item = currentTodoItem.value!!
            // Passa l'elemento su cui agire
        )

    }

    LaunchedEffect(viewModel.erroreAggiungiTask.value) {
        if(viewModel.erroreAggiungiTask.value != null){
            Toast.makeText(context, viewModel.erroreAggiungiTask.value, Toast.LENGTH_LONG).show()
            viewModel.erroreAggiungiTask.value
        }
    }

    LaunchedEffect(viewModel.erroreEditTask.value) {
        if(viewModel.erroreEditTask.value != null){
            Toast.makeText(context, viewModel.erroreEditTask.value, Toast.LENGTH_LONG).show()
            viewModel.resetErroreAggiungiTask()
        }

    }


    Box(
        modifier = Modifier
            .background(Grey20)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.08f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Box(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(35.dp)
                        .background(
                            Color.Black,
                            RoundedCornerShape(20.dp)
                        ) // Imposta il rettangolo di sfondo a nero
                        .clickable { navController.navigate(Schermate.ItuoiProgetti.route) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "close_impostazioni",
                        tint = Color.White // Assicurati che l'icona sia visibile impostando il colore a bianco
                    )
                }

                // Centra il testo all'interno della Row
                Row(
                    modifier = Modifier.weight(8f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "To Do List",
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        color = Color.Black,
                        fontFamily = FontFamily.Monospace
                    )
                }

                // Row vuota per bilanciare il layout
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {}

            }

            if(caricanome.value)
            {
                CircularProgressIndicator(color = Color.Black)
            }
            else
            {
                Text(
                    text = progetto_nome.value,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(top = 5.dp)

                )
            }


            Text(
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.project_tasks_description),
                color = Grey70,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .padding(horizontal = 20.dp)
            )

            Image(
                painterResource(id = R.drawable.linea),
                contentDescription = "Linea",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(30.dp),
                alignment = Alignment.Center
            )
            Spacer(modifier = Modifier.height(20.dp))

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





            if (!isLoadingCompletate) {
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Grey35)
                .clickable {
                    dialogExpanded = true
                }
        ) {
            Column(
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
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
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    maxLines = 1,
                    modifier = Modifier.padding(end = 10.dp),
                    text = item.titolo,
                    fontSize = 16.sp
                )
                Text(
                    maxLines = 1,
                    modifier = Modifier.padding(end = 10.dp),
                    text = item.descrizione,
                    color = Grey70
                )
                Text(
                    modifier = Modifier.padding(end = 10.dp),
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
                IconButton(onClick = { onEdit(item) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_edit_24),
                        contentDescription = "Edit",
                        tint = Grey70
                    )
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
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = 8.dp)
            .padding(bottom = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Grey35
        )
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
                    contentDescription = "tipo che ride",
                    modifier = Modifier
                        .size(200.dp)
                        .offset(x = (-16).dp)
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
                    text = "Hai Completato $todoCompletate/$todoNonCompletate ToDo",
                    textAlign = TextAlign.Center,
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
