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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente
import com.example.teamsync.data.models.Priorità
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.ui.theme.Grey20
import com.example.teamsync.ui.theme.Grey50
import com.example.teamsync.ui.theme.Red70
import com.example.teamsync.ui.theme.White
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ITuoiProgetti(
    navController: NavController,
    viewModelProgetto: ViewModelProgetto,
    viewModelUtente: ViewModelUtente
){
    var expended by remember {
        mutableStateOf(false)
    }
    var mostraCreaProgettoDialog by remember {
        mutableStateOf(false)
    }
    val aggiungiProgettoRiuscito = viewModelProgetto.aggiungiProgettoRiuscito.value
    val progetti by viewModelProgetto.progetti

    val utenteCorrenteId by viewModelProgetto.utenteCorrenteId
    val context = LocalContext.current
    val loading = viewModelProgetto.isLoading.value

    LaunchedEffect(Unit) {
        viewModelProgetto.utenteCorrenteId.value?.let {
            viewModelProgetto.caricaProgettiUtente(it, false)
        }
        Log.d("View", "utente corrente : ${viewModelProgetto.utenteCorrenteId.value}")
    }

    Scaffold(
       topBar = {
           CenterAlignedTopAppBar(
               title = {
                   Text(
                       text = "Home",
                       style = MaterialTheme.typography.headlineSmall,
                       fontWeight = FontWeight.Bold,
                       textAlign = TextAlign.Center,
                       color = Color.Black
                   )
               },

               actions = {
                   Box{
                       IconButton(onClick = {expended = true }) {
                           Icon(Icons.Default.MoreVert, contentDescription = "Menu a tendina")
                       }
                       DropdownMenu(
                           expanded = expended,
                           onDismissRequest = { expended = false },
                           modifier = Modifier.background(Grey20)
                       ) {
                           DropdownMenuItem(
                               text = { Text(text = "Sincronizza") },
                               onClick = {
                                   expended = false
                                   utenteCorrenteId?.let {
                                       viewModelProgetto.caricaProgettiUtente(it, true)
                                   }
                               },
                               leadingIcon = {
                                   Icon(Icons.Default.Refresh, contentDescription = "icona sincronizzazione", tint = Color.Black)
                               },
                               modifier = Modifier.background(Grey20)
                           )

                           DropdownMenuItem(
                               text = { Text(text = "Impostazioni") },
                               onClick = {
                                   expended = false
                                   navController.navigate(Schermate.Impostazioni.route)
                                         },
                               leadingIcon = {
                                   Icon(Icons.Default.Settings, contentDescription = "icona Impostazioni", tint = Color.Black)
                               },
                               modifier = Modifier.background(Grey20)
                           )
                           DropdownMenuItem(
                               text = { Text(text = "Esci")},
                               onClick = {
                                   expended = false
                                   viewModelProgetto.logout()
                                   navController.navigate(Schermate.Login.route)
                               },
                               leadingIcon = {
                                   Icon(
                                       painter = painterResource(id = R.drawable.ic_logout),
                                       contentDescription = "icona Logout",
                                       tint = Color.Black,
                                       modifier = Modifier.size(20.dp),
                                   )
                               },
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
       },
       floatingActionButton = {
           FloatingActionButton(
               onClick = {
                   mostraCreaProgettoDialog = true
               },
               containerColor = Red70,
               contentColor = White,
           ) {
               Icon(Icons.Default.Add, contentDescription = "Aggiungi progetto")
           }
       },
       content = {padding ->

           Column(
               modifier = Modifier
                   .fillMaxSize()
                   .padding(padding)
                   .padding(horizontal = 16.dp)
           ) {
               Row(
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(8.dp),
                   verticalAlignment = Alignment.Top,
                   horizontalArrangement = Arrangement.Center
               ){
                   if (loading) {
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


               SezioneProfiloUtente(navController, viewModelUtente)

               Spacer(modifier = Modifier.height(16.dp))

               SezioneITUoiProgetti(navController = navController, progetti = progetti, viewModelProgetto = viewModelProgetto)

               Spacer(modifier = Modifier.height(16.dp))


               Row(
                   modifier = Modifier
                       .fillMaxSize(),
                   horizontalArrangement = Arrangement.spacedBy(32.dp)
               ) {
                   SezioneProgressiProgetti(progress = 0.55f, completedProjects = 3)

                   SezioneCalendario()
               }

           }
       }
   )
    if (mostraCreaProgettoDialog) {
        CreaProgettoDialog(
            onDismissRequest = { mostraCreaProgettoDialog = false },
            viewModelProgetto = viewModelProgetto
        )
    }


    LaunchedEffect(viewModelProgetto.erroreAggiungiProgetto.value) {
        if(viewModelProgetto.erroreAggiungiProgetto.value != null){
            Toast.makeText(context, viewModelProgetto.erroreAggiungiProgetto.value, Toast.LENGTH_LONG).show()
            viewModelProgetto.resetErroreAggiungiProgetto()
        }
    }

    LaunchedEffect(aggiungiProgettoRiuscito) {
        if(aggiungiProgettoRiuscito){
            Toast.makeText(context, "Progetto aggiunto con successo", Toast.LENGTH_LONG).show()
            utenteCorrenteId?.let {
                viewModelProgetto.caricaProgettiUtente(it, true)
            }
            viewModelProgetto.resetAggiugniProgettoRiuscito()
            mostraCreaProgettoDialog = false
        }
    }

}





@Composable
fun CreaProgettoDialog(
    onDismissRequest: () -> Unit,
    viewModelProgetto: ViewModelProgetto,
) {
    var nome by remember { mutableStateOf("") }
    var descrizione by remember { mutableStateOf("") }
    var dataScadenza by remember { mutableStateOf(Date()) }
    var priorita by remember { mutableStateOf(Priorità.NESSUNA) }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var mostraAggiungiProgetto by remember {
        mutableStateOf(false)
    }

    val datePickerDialog = DatePickerDialog(
        context,
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



    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                "Crea un nuovo progetto",
            )
                },
        containerColor = White,
        text = {
            Column {
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = {
                        Text(
                            "Nome",
                            color = Color.Black
                        ) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Red70
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = descrizione,
                    onValueChange = { descrizione = it },
                    label = {
                        Text("Descrizione",
                        color = Color.Black) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Red70
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = dataScadenzaStr,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(
                        "Data di Scadenza",
                        color = Color.Black
                    ) },
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
                        focusedBorderColor = Red70
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                var expanded by remember { mutableStateOf(false) }
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = priorita.name,
                        onValueChange = {},
                        readOnly = true,
                        label = {
                            Text(
                                "Priorità",
                                color = Color.Black
                            ) },
                        modifier = Modifier
                            .fillMaxWidth(),
                        trailingIcon = {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown",
                                modifier = Modifier.clickable { expanded = true })
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Red70
                        )
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Grey20)
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
                                                .border(0.5.dp, Color.Black, CircleShape)
                                                .background(p.colore)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(p.name)

                                    }
                                       },
                                onClick = {
                                    priorita = p
                                    expanded = false
                                },
                                modifier = Modifier.background(Grey20)
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
                        text = "Oppure unisciti a un progetto con ",
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "un codice",
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
        },
        confirmButton = {
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
                Text("Crea")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest,
            ) {
                Text(
                    "Annulla",
                    color = Color.Black)

            }
        }
    )
    if(mostraAggiungiProgetto){
        AggiungiProgettoDialog(
            onDismissRequest = { mostraAggiungiProgetto = false },
            viewModelProgetto = viewModelProgetto ,
        )
    }
    LaunchedEffect(viewModelProgetto.erroreAggiungiProgetto.value) {
        if(viewModelProgetto.erroreAggiungiProgetto.value != null){
            Toast.makeText(context, viewModelProgetto.erroreAggiungiProgetto.value, Toast.LENGTH_LONG).show()
            viewModelProgetto.resetErroreAggiungiProgetto()
        }

    }
}

@Composable
fun AggiungiProgettoDialog(
    onDismissRequest: () -> Unit,
    viewModelProgetto: ViewModelProgetto,
){
    var codiceProgetto by remember { mutableStateOf("") }
    val utenteId = viewModelProgetto.utenteCorrenteId.value
    AlertDialog(
        title = {
            Text(
                text = "Aggiungi un progetto",
            )
        },
        containerColor = White,
        text = {
            Column {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = "Per unirti a un progetto esistente, inserisci il codice univoco fornito dal creatore del progetto.",
                    textAlign = TextAlign.Center
                )
                OutlinedTextField(
                    value = codiceProgetto , 
                    onValueChange = {codiceProgetto = it},
                    label = {
                        Text(
                            "Codice progetto",
                            color = Color.Black
                        ) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Red70,
                        focusedBorderColor = Red70
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
                colors = ButtonDefaults.buttonColors(Red70),
                enabled = codiceProgetto.length >= 8
            ) {
                Text(text = "Unisciti")
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






@Preview(showSystemUi = true)
@Composable
fun PreviewITuoiProgetti(){
    ITuoiProgetti(navController = rememberNavController(), ViewModelProgetto(), ViewModelUtente())
}
@Preview(showSystemUi = true)
@Composable
fun PreviewAggiungiProgetto(){
    //AggiungiProgettoDialog()
}


