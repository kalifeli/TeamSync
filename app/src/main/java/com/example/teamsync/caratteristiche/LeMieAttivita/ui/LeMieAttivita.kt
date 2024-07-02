package com.example.teamsync.caratteristiche.LeMieAttivita.ui


import android.widget.Toast;
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.LeMieAttivita.data.model.LeMieAttivita
import com.example.teamsync.caratteristiche.LeMieAttivita.data.repository.ToDoRepository
import com.example.teamsync.caratteristiche.LeMieAttivita.data.viewModel.LeMieAttivitaViewModel
import com.example.teamsync.data.models.Priorità
import com.example.teamsync.ui.theme.Grey20
import com.example.teamsync.ui.theme.Grey50
import com.example.teamsync.ui.theme.Grey70
import com.example.teamsync.ui.theme.Red70
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@ExperimentalMaterial3Api
@Composable
fun LeMieAttivita(navController: NavHostController, viewModel: LeMieAttivitaViewModel) {
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    var titolo by remember { mutableStateOf("") }
    var descrizione by remember { mutableStateOf("") }
    var dataScad by remember { mutableStateOf("") }
    var priorita by remember { mutableStateOf(Priorità.NESSUNA) }

    val coroutineScope = rememberCoroutineScope()

    val openDialog = remember { mutableStateOf(false) }
    val currentTodoItem = remember { mutableStateOf<LeMieAttivita?>(null) }

    if (openDialog.value && currentTodoItem.value != null) {
        EditTodoDialog(
            todoItem = currentTodoItem.value!!,
            onDismiss = { openDialog.value = false },
            onSave = { updatedItem ->
                viewModel.updateTodo(
                    id = updatedItem.id ?: "",
                    titolo = updatedItem.titolo,
                    descrizione = updatedItem.descrizione,
                    dataScad = updatedItem.dataScadenza,
                    priorità = updatedItem.priorita
                )
                openDialog.value = false
            }
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
                .padding(top = 60.dp),
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
                text = "Qui troverai le Task che sono assegnate per questo progetto",
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

            LazyColumn {
                items(viewModel.leMieAttività) { attività ->
                    TodoItem(item = attività,
                        onDelete = {
                            id -> viewModel.deleteTodo(id)
                        } ,
                        onEdit = { item ->
                            currentTodoItem.value = item
                            openDialog.value = true  // Mostra il dialogo di modifica
                        })

                }
            }
        }

        FloatingActionButton(
            containerColor = Red70,
            shape = FloatingActionButtonDefaults.shape,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp),
            onClick = { isSheetOpen = true }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "add Todo",
                tint = Color.White
            )
        }

        if (isSheetOpen) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { isSheetOpen = false }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = titolo,
                            onValueChange = { titolo = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Titolo") },
                            textStyle = TextStyle(fontSize = 18.sp, color = Color.Black)
                        )

                        OutlinedTextField(
                            value = descrizione,
                            onValueChange = { descrizione = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Descrizione") },
                            textStyle = TextStyle(fontSize = 18.sp, color = Color.Black)
                        )

                        OutlinedTextField(
                            value = dataScad,
                            onValueChange = { dataScad = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Data di Scadenza (dd-mm-yyyy)") },
                            textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )

                        Text("Priorità", style = TextStyle(fontSize = 18.sp, color = Color.Black))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Priorità.values().forEach { p ->
                                RadioButton(
                                    selected = (priorita == p),
                                    onClick = { priorita = p },
                                    colors = RadioButtonDefaults.colors(selectedColor = p.colore)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(p.name, color = p.colore)
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = {
                                    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.ITALY)
                                    val date = try {
                                        sdf.parse(dataScad)
                                    } catch (e: Exception) {
                                        null
                                    }

                                    if (date != null) {
                                        coroutineScope.launch {
                                            viewModel.addTodo(
                                                titolo,
                                                descrizione,
                                                date,
                                                priorita
                                            )
                                            isSheetOpen = false
                                        }
                                    } else {
                                        // Handle invalid date format
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFFF0000), // Background color
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Aggiungi Attività", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun TodoItem(item: LeMieAttivita, onDelete: (String) -> Unit, onEdit:(LeMieAttivita) -> Unit) {

    Row(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(Grey50)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = item.titolo,
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            )
            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = item.descrizione,
                color = Grey70
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(top = 4.dp),
                text = SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).format(item.dataScadenza),
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(top = 4.dp),

                text = "Priorità: ${item.priorita.toString()}",
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(onClick = {
                onDelete(item.id ?: "")
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    contentDescription = "Delete",
                    tint = Color.Red
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewLeMieAttivita() {
    LeMieAttivita(navController = rememberNavController(), viewModel = LeMieAttivitaViewModel(ToDoRepository()))
}

@Composable
fun EditTodoDialog(
    todoItem: LeMieAttivita,
    onDismiss: () -> Unit,
    onSave: (LeMieAttivita) -> Unit
) {
    var titolo by remember { mutableStateOf(todoItem.titolo) }
    var descrizione by remember { mutableStateOf(todoItem.descrizione) }
    var dataScadenza by remember { mutableStateOf(SimpleDateFormat("dd/MM/yyyy").format(todoItem.dataScadenza)) }
    var priorità by remember { mutableStateOf(todoItem.priorita) }
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.background(Grey70),
        title = { Text("Modifica Attività") },
        text = {
            Column {
                TextField(
                    value = titolo,
                    onValueChange = { titolo = it },
                    label = { Text("Titolo") }
                )
                TextField(
                    value = descrizione,
                    onValueChange = { descrizione = it },
                    label = { Text("Descrizione") }
                )
                TextField(
                    value = dataScadenza,
                    onValueChange = { dataScadenza = it },
                    label = { Text("Data Scadenza") }
                )
                // Gestisci la priorità se necessario
                // puoi usare un RadioButton o un DropdownMenu per gestire la priorità
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ITALY)
                    val date = sdf.parse(dataScadenza)

                    if (date != null) {
                        onSave(
                            todoItem.copy(
                                titolo = titolo,
                                descrizione = descrizione,
                                dataScadenza = date,
                                priorita = priorità
                            )
                        )
                    } else {
                        Toast.makeText( context, "i dati inseriti non sono validi", Toast.LENGTH_SHORT).show();
                    }
                }
            ) {
                Text("Salvaaaaaa")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Annulla")
            }
        }
    )
}
