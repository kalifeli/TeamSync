package com.example.teamsync.caratteristiche.leMieAttivita.ui

import android.app.DatePickerDialog
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import com.example.teamsync.util.Priorita
import com.example.teamsync.util.NoInternetScreen
import com.example.teamsync.util.theme.Grey35
import com.example.teamsync.util.theme.Grey50
import com.example.teamsync.util.theme.Red70
import com.example.teamsync.util.theme.White
import com.example.teamsync.util.ThemePreferences
import com.example.teamsync.util.isInternetAvailable
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Composable per la schermata di modifica di un progetto.
 *
 * @param viewModelProgetto Il ViewModel associato al progetto da modificare.
 * @param navController Il controller di navigazione per gestire il ritorno alla schermata precedente.
 * @param progettoId L'ID del progetto che si desidera modificare.
 */
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
    var priorita by remember { mutableStateOf(Priorita.NESSUNA) }
    var voto by remember { mutableStateOf("Non valutato") }
    var dataConsegnaProgetto by remember { mutableStateOf(Date()) }
    var completato by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var erroreModificaProgetto by remember { mutableStateOf<String?>(null)}
    var mostraVoto by remember { mutableStateOf(false) }
    val contesto = LocalContext.current

    val formattatoreData = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val dataScadenzaSdf = formattatoreData.format(dataScadenzaProgetto)
    val dataConsegnaSfd = formattatoreData.format(dataConsegnaProgetto)
    val calendario = Calendar.getInstance()

    val isDarkTheme = ThemePreferences.getTheme(contesto)

    // Dialog per la selezione della data di scadenza del progetto
    val datePickerDialogScadenza = DatePickerDialog(
        contesto,
        if(isDarkTheme) R.style.CustomDatePickerDialogDark else R.style.CustomDatePickerDialog,
        { _, year, month, dayOfMonth ->
            calendario.set(year, month, dayOfMonth)
            dataScadenzaProgetto = calendario.time
        },
        calendario.get(Calendar.YEAR),
        calendario.get(Calendar.MONTH),
        calendario.get(Calendar.DAY_OF_MONTH)
    )

    // Dialogo per la selezione della data di consegna del progetto (se completato)
    val datePickerDialogConsegna = DatePickerDialog(
        contesto,
        if(isDarkTheme) R.style.CustomDatePickerDialogDark else R.style.CustomDatePickerDialog,
        { _, year, month, dayOfMonth ->
            calendario.set(year, month, dayOfMonth)
            dataConsegnaProgetto = calendario.time
        },
        calendario.get(Calendar.YEAR),
        calendario.get(Calendar.MONTH),
        calendario.get(Calendar.DAY_OF_MONTH)
    )
    // Limiti di caratteri per i campi descrizione e nome progetto
    val maxCharsDescrizione = 200
    val maxCharsNome = 20

    val isConnected = remember { mutableStateOf(isInternetAvailable(contesto)) }

    // Effetto per controllare periodicamente la connessione internet
    LaunchedEffect(Unit) {
        // Periodicamente avviene un controllo per verificare che ci sia la connessione ad internet
        while(true){
            isConnected.value = isInternetAvailable(contesto)
            delay(5000) // controllo ogni 5 secondi
        }
    }

    // Caricamento iniziale dei dati del progetto
    LaunchedEffect(progettoId) {
        try {
            val progetto = viewModelProgetto.getProgettoById(progettoId)
            nomeProgetto = progetto.nome
            descrizioneProgetto = progetto.descrizione ?: ""
            dataScadenzaProgetto = progetto.dataScadenza
            priorita = progetto.priorita
            completato = progetto.completato
            voto = progetto.voto
            dataConsegnaProgetto = progetto.dataConsegna
            erroreModificaProgetto = null
            isLoading = false
        }catch (e: Exception){
            erroreModificaProgetto = "Si è verificato un errore. Si prega di riprovare"
            isLoading = false
        }
    }

    // Contenuto della schermata
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.titolo),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = if(isDarkTheme) White else Color.Black                    )
                },
                navigationIcon = {
                    Button(
                        onClick = { navController.popBackStack() },
                        colors = ButtonDefaults.buttonColors(if(isDarkTheme) Color.DarkGray else Grey35)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "torna indietro",
                            tint =if(isDarkTheme) White else Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor =if(isDarkTheme) Color.Black else White,
                    titleContentColor = if(isDarkTheme) White else Color.Black,
                    actionIconContentColor = if(isDarkTheme) White else Color.Black,
                )
            )
        }
    ) { padding ->

        if(!isConnected.value){
            // Schermata per assenza di connessione
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
              NoInternetScreen(
                  isDarkTheme = isDarkTheme,
                  onRetry = {isConnected.value = isInternetAvailable(contesto) }
              )
            }
        } else if(isLoading){
            // Indicatore di caricamento durante il fetch dei dati del progetto
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
        }else {
            erroreModificaProgetto?.let {
                Toast.makeText(contesto, erroreModificaProgetto, Toast.LENGTH_LONG).show()
            }
            Box(modifier = Modifier.background(if (isDarkTheme) Color.Black else Color.White)) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                ) {
                    // Campo per il nome del progetto
                    item {
                        OutlinedTextField(
                            value = nomeProgetto,
                            onValueChange = { if (it.length <= maxCharsNome) {
                                nomeProgetto = it
                            } },
                            label = {
                                Text(
                                    stringResource(id = R.string.nome),
                                    color = if (isDarkTheme) White else Color.Black
                                )
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
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 10.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = "${nomeProgetto.length} / $maxCharsNome",
                                color = if (isDarkTheme) Color.White else Color.Black,
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                    }

                    // Campo per la descrizione del progetto
                    item {
                        OutlinedTextField(
                            value = descrizioneProgetto,
                            onValueChange = { if (it.length <= maxCharsDescrizione) {
                                descrizioneProgetto = it
                            } },
                            label = {
                                Text(
                                    stringResource(id = R.string.descrizioneEdit),
                                    color = if (isDarkTheme) White else Color.Black
                                )
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
                            shape = RoundedCornerShape(16.dp),
                            maxLines = 5,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 10.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = "${descrizioneProgetto.length} / $maxCharsDescrizione",
                                color = if (isDarkTheme) Color.White else Color.Black,
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                    }

                    // Campo per la data di scadenza del progetto
                    item {
                        OutlinedTextField(
                            value = dataScadenzaSdf,
                            onValueChange = { },
                            readOnly = true,
                            label = { Text(
                                stringResource(id = R.string.datadiscadenza),
                                color = if (isDarkTheme) White else Color.Black
                            ) },
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
                            shape = RoundedCornerShape(16.dp),
                            trailingIcon = {
                                IconButton(
                                    onClick = { datePickerDialogScadenza.show() },
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
                    }

                    // Campo per la priorità del progetto
                    item {
                        var expanded by remember { mutableStateOf(false) }
                        Box {
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
                                    Icon(Icons.Default.ArrowDropDown,
                                        contentDescription = "Dropdown",
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
                                    .padding(8.dp)
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
                                                Text(p.nomeTradotto(), color = if (isDarkTheme) White else Color.Black)

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
                    }

                    // Switch per il completamento del progetto
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(id = R.string.fineProgetto),
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.labelLarge,
                                color = if (isDarkTheme) White else Color.Black

                            )
                            Switch(
                                checked = completato,
                                onCheckedChange = {
                                    completato = it
                                    viewModelProgetto.aggiornaStatoProgetto(progettoId, it)
                                },
                                thumbContent = {
                                    if (completato) {
                                        Icon(
                                            imageVector = Icons.Filled.Check,
                                            contentDescription = "progetto completato",
                                            modifier = Modifier.size(SwitchDefaults.IconSize)
                                        )
                                    }
                                },
                                colors = SwitchDefaults.colors(
                                    uncheckedTrackColor = White,
                                    checkedTrackColor = Red70,
                                    checkedBorderColor = Red70
                                )
                            )
                        }
                    }

                    item { Spacer(modifier = Modifier.height(16.dp)) }

                    // Campo per la data di consegna (se il progetto è completato)
                    if (completato) {
                        item {
                            OutlinedTextField(
                                value = dataConsegnaSfd,
                                onValueChange = { },
                                readOnly = true,
                                label = { Text(
                                    stringResource(id = R.string.dataConsegna),
                                    color = if (isDarkTheme) White else Color.Black
                                ) },
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
                                    focusedTrailingIconColor = if (isDarkTheme) Color.White else Color.Black,                                ),
                                shape = RoundedCornerShape(16.dp),
                                trailingIcon = {
                                    IconButton(
                                        onClick = { datePickerDialogConsegna.show() },
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_progettoconsegnato),
                                            contentDescription = "scegli data di consegna progetto",
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }

                        // Campo per la selezione del voto
                        item {
                            Box {
                                OutlinedTextField(
                                    value = voto,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = {
                                        Text(
                                            stringResource(id = R.string.voto),
                                            color = if (isDarkTheme) White else Color.Black
                                        )
                                    },
                                    shape = RoundedCornerShape(16.dp),
                                    trailingIcon = {
                                        Icon(Icons.Default.ArrowDropDown,
                                            contentDescription = "Dropdown",
                                            modifier = Modifier.clickable { mostraVoto = true })
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
                                        focusedTrailingIconColor = if (isDarkTheme) Color.White else Color.Black,                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                DropdownMenu(
                                    expanded = mostraVoto,
                                    onDismissRequest = { mostraVoto = false },
                                    modifier = Modifier
                                        .background(if (isDarkTheme) Color.DarkGray else Grey35)
                                        .height(200.dp)
                                ) {
                                    val voti = listOf(
                                        stringResource(id = R.string.nv),
                                        "18",
                                        "19",
                                        "20",
                                        "21",
                                        "22",
                                        "23",
                                        "24",
                                        "25",
                                        "26",
                                        "27",
                                        "28",
                                        "29",
                                        "30"
                                    )

                                    voti.forEach { v ->
                                        DropdownMenuItem(
                                            text = {
                                                Text(v, color = if (isDarkTheme) White else Color.Black)

                                            },
                                            onClick = {
                                                voto = v
                                                mostraVoto = false
                                            },
                                            modifier = Modifier.background(if (isDarkTheme)Color.DarkGray else Grey35)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item { Spacer(modifier = Modifier.height(16.dp)) }

                    // Pulsante per salvare le modifiche
                    item {
                        Button(
                            onClick = {
                                viewModelProgetto.aggiornaProgetto(
                                    progettoId,
                                    nomeProgetto,
                                    descrizioneProgetto,
                                    dataScadenzaProgetto,
                                    priorita,
                                    voto,
                                    dataConsegnaProgetto
                                )
                                navController.popBackStack()
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .padding(16.dp),
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
    }
}

/**
 * Anteprima della schermata di modifica progetto.
 */
@Preview(showSystemUi = true)
@Composable
fun PreviewSchermataModificaProgetto(){
    //SchermataModificaProgetto()
}

