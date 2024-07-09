package com.example.teamsync.caratteristiche.LeMieAttivita.ui

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.asFlow
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.LeMieAttivita.data.model.LeMieAttivita
import com.example.teamsync.caratteristiche.LeMieAttivita.data.repository.ToDoRepository
import com.example.teamsync.caratteristiche.LeMieAttivita.data.viewModel.LeMieAttivitaViewModel
import com.example.teamsync.data.models.Priorità
import com.example.teamsync.ui.theme.Green50
import com.example.teamsync.ui.theme.Grey20
import com.example.teamsync.ui.theme.Grey35
import com.example.teamsync.ui.theme.Grey50
import com.example.teamsync.ui.theme.Grey70
import com.example.teamsync.ui.theme.Red70
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@ExperimentalMaterial3Api
@Composable
fun LeMieAttivitaUI(navController: NavHostController, viewModel: LeMieAttivitaViewModel) {

    val coroutineScope = rememberCoroutineScope()
    var addTodoDialog by remember { mutableStateOf(false) }
    val currentTodoItem = remember { mutableStateOf<LeMieAttivita?>(null) }
    var dialogComplete by remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false) }
    val isClicked = remember { mutableStateOf(true) }
    val isClicked1 = remember { mutableStateOf(false) }
    var sezione by remember { mutableIntStateOf(1) }
    val progressione by viewModel.progressione.collectAsState() // Osserva il valore del flusso
    val todoCompletate by viewModel.taskCompletate.collectAsState()
    val todoNonCompletate by viewModel.taskNonCompletate.collectAsState()


    if (addTodoDialog) {
        AddTodoDialog(
            onDismiss = { addTodoDialog = false },
            onSave = { newTodo ->
                coroutineScope.launch {
                    viewModel.addTodo(
                        newTodo.titolo,
                        newTodo.descrizione,
                        newTodo.dataScadenza,
                        newTodo.priorita,
                        newTodo.completato
                    )
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
                var sezioneTodo = sezione
                val fileUri = viewModel.uploadResult.value
                viewModel.updateTodo(
                    id = updatedItem.id ?: "",
                    titolo = updatedItem.titolo,
                    descrizione = updatedItem.descrizione,
                    dataScad = updatedItem.dataScadenza,
                    fileUri =fileUri,
                    priorita = updatedItem.priorita,
                    sezione = sezioneTodo
                )
                openDialog.value = false
            }
        )
    }

    if (dialogComplete && currentTodoItem.value != null) {
        CompleteDialog(
            sezione,
            onDismiss = { dialogComplete = false },
            onSave = { completeTodo ->
                coroutineScope.launch {
                    // Chiama la funzione completa nel viewModel
                    viewModel.completeTodo(id = completeTodo.id ?: "", completeTodo.completato, sezione)
                }
                dialogComplete = false // Chiudi il dialogo dopo aver salvato
            },
            item = currentTodoItem.value!! // Passa l'elemento su cui agire
        )
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
            Text(
                text = "To Do List",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                color = Color.Black,
                fontFamily = FontFamily.Monospace
            )

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




            Row {
                Card(progressione,todoCompletate,todoNonCompletate)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
            ) {


                Button(
                    onClick = {
                        viewModel.getAllTodoCompletate()
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
                        viewModel.getAllTodo()
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
            //bottone non completate sezione = 1 else 0


            LazyColumn {
                items(viewModel.leMieAttivita) { attivita ->
                    TodoItem(
                        item = attivita,
                        onDelete = { id ->
                            viewModel.deleteTodo(id, sezione)
                        },
                        onEdit = { item ->
                            currentTodoItem.value = item
                            openDialog.value = true
                        },
                        onComplete = { item ->
                            currentTodoItem.value = item
                            dialogComplete = true
                        },
                        sezione
                    )
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
fun TodoItem(
    item: LeMieAttivita,
    onDelete: (String) -> Unit,
    onEdit: (LeMieAttivita) -> Unit,
    onComplete: (LeMieAttivita) -> Unit,
    sezione: Int
) {

    var dialogDelete by remember { mutableStateOf(false) }


    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(Grey35)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically) // Aligns the column center vertically
        ) {
                if (!item.completato)
                {
                    IconButton(onClick = { onComplete(item) }) {
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
                modifier = Modifier.padding(end = 10.dp),
                text = item.titolo,
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            )
            Text(
                modifier = Modifier
                    .padding(end = 10.dp),
                text = item.descrizione,
                color = Grey70
            )
            Text(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .padding(top = 4.dp, bottom = 6.dp),
                text = SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).format(item.dataScadenza),
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )

            Canvas(modifier = Modifier
                .size(16.dp)) {
                drawCircle(
                    color = item.priorita.colore,
                    radius = size.minDimension / 2
                )
                
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(onClick = {dialogDelete = true}
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    contentDescription = "Delete",
                    tint = Color.Red
                )
            }
            if (dialogDelete){
                AlertDialog(
                    onDismissRequest = { dialogDelete = false },
                    confirmButton = {
                        Button(onClick = {
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
                            onClick = {dialogDelete = false},
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
}

@Composable
fun EditTodoDialog(
    todoItem: LeMieAttivita,
    onDismiss: () -> Unit,
    onSave: (LeMieAttivita) -> Unit,
    viewModel: LeMieAttivitaViewModel = LeMieAttivitaViewModel()
) {
    var titolo by remember { mutableStateOf(todoItem.titolo) }
    var descrizione by remember { mutableStateOf(todoItem.descrizione) }
    var dataScadenza by remember { mutableStateOf(SimpleDateFormat("dd/MM/yyyy").format(todoItem.dataScadenza)) }
    var priorita by remember { mutableStateOf(todoItem.priorita) }
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedFileUri = it
            viewModel.setFileUri(it)  // Imposta il file URI nel ViewModel
        }
    }


    // Observe the upload result
    val uploadResult by viewModel.uploadResult.observeAsState()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Modifica Attività") },
        containerColor = Grey35,
        textContentColor = Grey50,
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                TextField(
                    value = titolo,
                    onValueChange = { titolo = it },
                    label = { Text(stringResource(id = R.string.titoloEdit), color = Color.Black) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Grey35,
                        unfocusedContainerColor = Grey50,
                    ),
                )
                TextField(
                    value = descrizione,
                    onValueChange = { descrizione = it },
                    label = { Text((stringResource(id = R.string.descrizioneEdit)), color = Color.Black) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Grey35,
                        unfocusedContainerColor = Grey50,
                    ),
                )
                TextField(
                    value = dataScadenza,
                    onValueChange = { dataScadenza = it },
                    label = { Text(stringResource(id = R.string.inserisciData), color = Color.Black) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Grey35,
                        unfocusedContainerColor = Grey50,
                    ),
                )
                // Aggiungi la selezione della priorità
                // ...

                // Aggiungi il pulsante per selezionare un file
                Button(onClick = { launcher.launch("*/*") }) {
                    Text("Seleziona File")
                }

                if (selectedFileUri != null) {
                    Text("File Selezionato: ${selectedFileUri?.lastPathSegment}")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val updatedTodo = todoItem.copy(
                        titolo = titolo,
                        descrizione = descrizione,
                        dataScadenza = SimpleDateFormat("dd/MM/yyyy").parse(dataScadenza),
                        priorita = priorita
                    )
                    viewModel.uploadFileAndSaveTodo(
                        id = updatedTodo.id ?: "",
                        titolo = updatedTodo.titolo,
                        descrizione = updatedTodo.descrizione,
                        dataScad = updatedTodo.dataScadenza,
                        priorita = priorita,
                        sezione = 1
                    )  // Esegui l'upload del file e salva il todo
                    onSave(updatedTodo)
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(Red70)
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

    LaunchedEffect(uploadResult) {
        // Gestisci il risultato dell'upload se necessario
    }
}





@Composable
fun AddTodoDialog(
    onDismiss: () -> Unit,
    onSave: (LeMieAttivita) -> Unit
) {
    var titolo by remember { mutableStateOf("") }
    var descrizione by remember { mutableStateOf("") }
    var dataScadenza by remember { mutableStateOf("") }
    var priorita by remember { mutableStateOf(Priorità.BASSA) }
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

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
                TextField(
                    value = titolo,
                    onValueChange = { titolo = it },
                    label = { Text(stringResource(id = R.string.titoloEdit), color = Color.Black) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Grey35,
                        unfocusedContainerColor = Grey50,
                    ),
                    textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                    placeholder = { Text(stringResource(id = R.string.titoloEdit)) }
                )
                TextField(
                    value = descrizione,
                    onValueChange = { descrizione = it },
                    label = { Text(stringResource(id = R.string.descrizioneEdit), color = Color.Black) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Grey35,
                        unfocusedContainerColor = Grey50,
                    ),
                    textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                    placeholder = { Text(stringResource(id = R.string.inserisciDescrizione)) }
                )
                TextField(
                    value = dataScadenza,
                    onValueChange = { dataScadenza = it },
                    label = { Text(stringResource(id = R.string.inserisciData), color = Color.Black) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Grey35,
                        unfocusedContainerColor = Grey50,
                    ),
                    textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = { Text("dd/MM/yyyy") }
                )

                // Componente DropdownMenu per la priorità
                Box(modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        value = priorita.name,
                        onValueChange = {},
                        label = { Text(stringResource(id = R.string.selPriorita), color = Color.Black) },
                        colors = TextFieldDefaults.colors(
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
                        modifier = Modifier.fillMaxWidth()
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

                // Aggiungi qui il bottone "Aggiungi File"
                Button(
                    onClick = { /* Logica per aggiungere file */ },
                    colors = ButtonDefaults.buttonColors(Grey50),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Aggiungi File", color = Color.Black)
                }
            }
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(Red70),
                onClick = {
                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ITALY)
                    val date = try {
                        sdf.parse(dataScadenza)
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
                        Toast.makeText(context, context.getString(R.string.datiErrati), Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text(stringResource(id = R.string.aggiungiTodo), color = Color.White)
            }
        },
        dismissButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(Grey70)) {
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
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Red70) // Colore di sfondo del pulsante di conferma
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
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red) // Colore di sfondo del pulsante di conferma
                ) {
                    Text(text =  stringResource(id = R.string.conferma), color = Color.White) // Testo bianco sul pulsante rosso
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
                    ) // Testo nero sul pulsante grigio chiaro
                }
            }
        )
    }
}

@Composable
fun Card(progress: Float, todoCompletate: Int, todoNonCompletate: Int) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 8.dp)
            .padding(bottom = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Grey35 // Sostituisci Grey35 con il colore appropriato
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Prima colonna (vuota)
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
                        .size(200.dp) // Imposta la dimensione desiderata
                        .offset(x = (-16).dp) // Sposta l'immagine fuori dai bordi della colonna
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
                    fontWeight = FontWeight.Bold,
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
                        progress = progress,
                        modifier = Modifier.size(64.dp),
                        color = Red70,
                        trackColor = Grey50
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewLeMieAttivita() {
    LeMieAttivitaUI(navController = rememberNavController(), viewModel = LeMieAttivitaViewModel())
}
