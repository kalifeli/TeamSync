package com.example.teamsync.caratteristiche.LeMieAttivita.ui

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
import com.example.teamsync.data.models.Priorità
import com.example.teamsync.ui.theme.Grey20
import com.example.teamsync.ui.theme.Grey50
import com.example.teamsync.ui.theme.Red70
import com.example.teamsync.ui.theme.White
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchermataModificaProgetto(
    viewModelProgetto: ViewModelProgetto,
    navController: NavController,
    progettoId: String,
) {
    //variabili per gestire i campi di imput della schermata
    var nomeProgetto by remember { mutableStateOf("") }
    var descrizioneProgetto by remember { mutableStateOf("") }
    var dataScadenzaProgetto by remember { mutableStateOf(Date()) }
    var priorita by remember { mutableStateOf(Priorità.NESSUNA) }
    var isLoading by remember { mutableStateOf(true) }
    var erroreModificaProgetto by remember { mutableStateOf<String?>(null)}
    val contesto = LocalContext.current

    // Caricamento iniziale dei dati del progetto
    LaunchedEffect(progettoId) {
        try {
            val progetto = viewModelProgetto.get_progetto_by_id(progettoId)
            nomeProgetto = progetto.nome
            descrizioneProgetto = progetto.descrizione ?: ""
            dataScadenzaProgetto = progetto.dataScadenza
            priorita = progetto.priorita
            erroreModificaProgetto = null
            isLoading = false
        }catch (e: Exception){
            erroreModificaProgetto = "Si è verificato un errore. Si prega di riprovare"
            isLoading = false
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Modifica Progetto",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    Button(
                        onClick = { navController.popBackStack() },
                        colors = ButtonDefaults.buttonColors(Color.Transparent)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "torna indietro",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black,
                )
            )
        }
    ) { padding ->

        if(isLoading){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(
                    modifier = Modifier.padding(8.dp),
                    color = Grey50,
                    trackColor = Red70,
                    strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap)
            }
        }else{
            erroreModificaProgetto?.let {
                Toast.makeText(contesto, erroreModificaProgetto, Toast.LENGTH_LONG).show()
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                OutlinedTextField(
                    value = nomeProgetto,
                    onValueChange = {nomeProgetto = it },
                    label = { Text(stringResource(id = R.string.nome), color = Color.Black) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Red70
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                OutlinedTextField(
                    value = descrizioneProgetto,
                    onValueChange = { descrizioneProgetto = it},
                    label = {
                        Text(
                            stringResource(id = R.string.descrizioneEdit),
                            color = Color.Black,
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Red70
                    ),
                    shape = RoundedCornerShape(16.dp),
                    maxLines = 15,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(200.dp)
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = { },
                    label = { Text("Data di scadenza", color = Color.Black) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Red70
                    ),
                    shape = RoundedCornerShape(16.dp),
                    trailingIcon = {
                        IconButton(
                            onClick = { /* TODO: datePickerDialog.show() */ },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_calendario_evento),
                                contentDescription = "scegli data di scadenza progetto",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                var expanded by remember { mutableStateOf(false) }
                Box{
                    OutlinedTextField(
                        value = priorita.name,
                        onValueChange = {},
                        readOnly = true,
                        label = {
                            Text(
                                "Priorità",
                                color = Color.Black
                            )
                        },
                        shape = RoundedCornerShape(16.dp),
                        trailingIcon = {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown",
                                modifier = Modifier.clickable { expanded = true })
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Red70
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
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

                Button(
                    onClick = {
                        viewModelProgetto.aggiornaProgetto(
                            progettoId,
                            nomeProgetto,
                            descrizioneProgetto,
                            dataScadenzaProgetto,
                            priorita
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    border = BorderStroke(1.dp, Color.DarkGray),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Red70,
                        contentColor = White
                    )
                ) {
                    Text(stringResource(id = R.string.salvaEdit))
                }
            }
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewSchermataModificaProgetto(){
    //SchermataModificaProgetto()
}

