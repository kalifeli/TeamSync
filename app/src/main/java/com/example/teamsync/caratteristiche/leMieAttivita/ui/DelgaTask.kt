package com.example.teamsync.caratteristiche.leMieAttivita.ui


import android.util.Log
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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.leMieAttivita.data.model.LeMieAttivita
import com.example.teamsync.caratteristiche.leMieAttivita.data.viewModel.LeMieAttivitaViewModel
import com.example.teamsync.caratteristiche.notifiche.data.viewModel.ViewModelNotifiche
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
import com.example.teamsync.caratteristiche.autentificazione.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.autentificazione.data.viewModel.ViewModelUtente
import com.example.teamsync.util.theme.Red70
import com.example.teamsync.util.theme.White
import com.example.teamsync.util.theme.WhiteFacebook
import com.example.teamsync.util.ThemePreferences
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Composable che rappresenta la schermata per delegare un'attività a un collega.
 *
 * @param viewModel Il ViewModel utilizzato per gestire le informazioni del profilo utente.
 * @param navController Il NavHostController utilizzato per navigare tra le schermate.
 * @param leMieAttivitaViewModel Il ViewModel utilizzato per gestire le attività.
 * @param idTask L'ID dell'attività da delegare.
 * @param viewModelProgetto Il ViewModel utilizzato per gestire i progetti e ottenere la lista dei partecipanti.
 * @param idProg L'ID del progetto associato all'attività.
 * @param viewModelNotifiche Il ViewModel utilizzato per gestire le notifiche.
 */
@Composable
fun DelegaTask(viewModel: ViewModelUtente, navController: NavHostController, leMieAttivitaViewModel: LeMieAttivitaViewModel, idTask : String, viewModelProgetto: ViewModelProgetto, idProg : String, viewModelNotifiche : ViewModelNotifiche) {
    var task by remember { mutableStateOf<LeMieAttivita?>(null) }
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    LaunchedEffect(idTask) {
        task = leMieAttivitaViewModel.getTodoById(idTask)
    }

    Box(modifier = Modifier.background(if (isDarkTheme) Color.DarkGray else Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)

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
                        .size(35.dp)
                        .background(
                            Color.White,
                            RoundedCornerShape(20.dp)
                        )
                        .clickable { navController.navigate("progetto/${idProg}") },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "close_impostazioni",
                        tint = Color.DarkGray
                    )
                }

                Row(
                    modifier = Modifier.weight(8f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.AggiungiPersone),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isDarkTheme) Color.White else Color.Black,
                    )
                }

                // Row vuota per bilanciare il layout
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {}

            }

            task?.let { ProfiloHeader(viewModel, navController, it) }
            Spacer(modifier = Modifier.height(16.dp))
            task?.let {
                ListaColleghi(
                    viewModel,
                    navController,
                    leMieAttivitaViewModel,
                    idTask,
                    viewModelProgetto,
                    idProg,
                    viewModelNotifiche
                )
            }
        }
    }

}

/**
 * Composable che visualizza l'header con i dettagli dell'attività selezionata.
 *
 * @param viewModel Il ViewModel utilizzato per gestire le informazioni del profilo utente.
 * @param navController Il NavHostController utilizzato per navigare tra le schermate.
 * @param task L'attività selezionata di cui mostrare i dettagli.
 */
@Composable
fun ProfiloHeader(viewModel: ViewModelUtente, navController: NavHostController, task : LeMieAttivita) {
    viewModel.getUserProfile()
    val userProfile by viewModel.userProfilo.observeAsState()
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    var nome by remember { mutableStateOf(userProfile?.nome ?: "") }
    var amici by remember { mutableStateOf(userProfile?.amici ?: "") }
    val idTask by remember { mutableStateOf(task.id) }
    val idProg by remember { mutableStateOf(task.progetto ) }
    val context = LocalContext.current
    val titolo = task.titolo
    val dataScadenza = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(task.dataScadenza)
    val priorita = task.priorita
    val descrizione = task.descrizione

    userProfile?.let {
        nome = it.nome
        amici = it.amici
    }

    ElevatedCard(
        onClick = {
            navController.navigate("task_selezionata/${idTask}/${idProg}")
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, White,shape = RoundedCornerShape(16.dp)),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if(isDarkTheme) Color.Black else Red70
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.08f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {}

                Row(
                    modifier = Modifier.weight(8f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.DettagliAttività),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color =Color.White,
                        modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
                    )
                }

                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {}

            }


            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = context.getString(R.string.nomeDelega, titolo),
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = context.getString(R.string.prioritaDelega, priorita),
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = context.getString(R.string.dataDiScadenzaDelega, dataScadenza),
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = context.getString(R.string.DescrizioneDelega, descrizione),
                fontSize = 16.sp,
                color = Color.White,
                maxLines = 3,
                modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
            )
        }
    }

}

/**
 * Composable che visualizza l'elenco dei colleghi a cui può essere delegata un'attività.
 *
 * @param viewModel Il ViewModel utilizzato per gestire le informazioni del profilo utente.
 * @param navController Il NavHostController utilizzato per navigare tra le schermate.
 * @param leMieAttivitaViewModel Il ViewModel utilizzato per gestire le attività.
 * @param idTask L'ID dell'attività da delegare.
 * @param viewModelProgetto Il ViewModel utilizzato per gestire i progetti e ottenere la lista dei partecipanti.
 * @param idProgetto L'ID del progetto associato all'attività.
 * @param viewModelNotifiche Il ViewModel utilizzato per gestire le notifiche.
 */
@Composable
fun ListaColleghi(
    viewModel: ViewModelUtente,
    navController: NavHostController,
    leMieAttivitaViewModel: LeMieAttivitaViewModel,
    idTask : String,
    viewModelProgetto : ViewModelProgetto,
    idProgetto : String,
    viewModelNotifiche: ViewModelNotifiche
) {
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    val userProfile by viewModel.userProfilo.observeAsState()
    var amici by remember { mutableStateOf<List<String>>(emptyList()) }
    var task by remember { mutableStateOf<LeMieAttivita?>(null) }
    val cache =
        remember { mutableStateMapOf<String, ProfiloUtente?>() } // Utilizzo di mutableStateMapOf per la cache
    var isLoading by remember { mutableStateOf(true) }
    val visualizzaAmici by remember { mutableStateOf(true) }



    LaunchedEffect(Unit) {
        task = leMieAttivitaViewModel.getTodoById(idTask = idTask)
        if (userProfile != null) {
            amici = viewModelProgetto.getListaPartecipanti(idProgetto, userProfile!!.id)
        }
    }
    // Utilizzo di LaunchedEffect per gestire il cambio di stato di amici
    LaunchedEffect(userProfile) {
        viewModelProgetto.getListaPartecipanti(idProgetto) { partecipanti -> amici = partecipanti }
        println(
            "partecipanti $amici"
        )
    }

    LaunchedEffect(amici) {
        isLoading = false
    }

    ElevatedCard(
        onClick = {
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .border(1.dp, White,shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (isDarkTheme) Color.Black else WhiteFacebook
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(start = 10.dp, top = 15.dp, bottom = 25.dp)
        ) {
            Text(
                text = stringResource(id = R.string.listaPartecipanti),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDarkTheme) Color.White else Color.Black
            )
        }
        Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp)){


            if (visualizzaAmici) {

                LazyColumn {
                    item {
                        if (!isLoading) {

                            if (task != null && userProfile != null && task!!.utenti.contains(
                                    userProfile!!.id
                                )
                            ) {
                                CollegaItem(
                                    userProfile!!,
                                    color = Red70,
                                    navController = navController,
                                    userProfile,
                                    true,
                                    leMieAttivitaViewModel,
                                    idTask,
                                    idProgetto,
                                    viewModelNotifiche
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
                    items(amici) { amico ->

                        var userAmico by remember { mutableStateOf<ProfiloUtente?>(null) }


                        if (cache.contains(amico)) {
                            userAmico = cache[amico]
                        } else {
                            viewModel.ottieniUtente(amico) { profile ->
                                userAmico = profile
                                cache[amico] = profile // Aggiungi il profilo alla cache
                                Log.d("Singolo Collega", "Dati: $userAmico")
                            }


                        }


                        val partecipa = task?.utenti?.contains(amico) ?: false

                        if (!isLoading) {
                            userAmico?.let { collega ->

                                CollegaItem(
                                    collega,
                                    color = Red70,
                                    navController,
                                    userProfile,
                                    partecipa,
                                    leMieAttivitaViewModel,
                                    idTask,
                                    idProgetto,
                                    viewModelNotifiche


                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }


                    }
                }
            }

        }
    }
}


/**
 * Composable che rappresenta un singolo collega nell'elenco dei partecipanti.
 *
 * @param utente Il profilo del collega.
 * @param color Il colore da utilizzare per lo sfondo e altri elementi visivi.
 * @param navController Il NavHostController utilizzato per navigare tra le schermate.
 * @param userLoggato Il profilo dell'utente loggato.
 * @param partecipa Indica se il collega partecipa già all'attività.
 * @param leMieAttivitaViewModel Il ViewModel utilizzato per gestire le attività.
 * @param idTask L'ID dell'attività da delegare.
 * @param idProg L'ID del progetto associato all'attività.
 * @param viewModelNotifiche Il ViewModel utilizzato per gestire le notifiche.
 */
@Composable
fun CollegaItem(utente : ProfiloUtente, color: Color, navController: NavHostController, userLoggato: ProfiloUtente?, partecipa : Boolean, leMieAttivitaViewModel: LeMieAttivitaViewModel, idTask : String, idProg: String, viewModelNotifiche: ViewModelNotifiche) {

    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    var amicizia = false
    if (utente.amici.contains(userLoggato?.id) && ( userLoggato!!.id != utente.id) ) {
        amicizia = true
    }


    ElevatedCard(
        onClick = {
            navController.navigate("utente/${utente.id}/${amicizia}/task/${idTask}/${idProg}/progetto")
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if(isDarkTheme) Color.Gray else Color.White
        ),
        shape = RoundedCornerShape(16.dp),

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .pointerInput(Unit) {

                }
        ) {

            Icon(
                painter = painterResource(id = R.drawable.logo_white),
                contentDescription = null,

                modifier = Modifier
                    .size(32.dp)
                    .background(
                        if (isDarkTheme) Color.White else color.copy(alpha = 0.05f),
                        CircleShape
                    )
                    .padding(8.dp)
            )


            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = utente.nome + " " + utente.cognome,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = if(isDarkTheme) Color.White else Color.Black
                )
                Text(text = utente.matricola, fontSize = 14.sp, color = if(isDarkTheme) Color.White else Color.Black)
            }

            if (!partecipa && userLoggato!!.id != utente.id ) {
                Spacer(modifier = Modifier.weight(1f))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Aggiungi",
                        tint = if(isDarkTheme) Color.White else Color.Gray,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                leMieAttivitaViewModel.aggiungiPersona(idTask, utente.id)
                                Log.d("IconClick", "Icona cliccata")
                                val contenuto = (userLoggato.nome + " " + (userLoggato.cognome) + " " + "ti ha assegnato una task")
                                viewModelNotifiche.creaNotifica(
                                    utente.id,
                                    utente.id,
                                    "Assegnazione_Task",
                                    contenuto,
                                    idProg

                                )
                                navController.navigate("task_selezionata/${idTask}/${idProg}")


                            }
                    )
                }
            }
            else
            {
                Spacer(modifier = Modifier.weight(1f))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "rimuovi delega",
                        tint = if(isDarkTheme) Color.White else Color.Gray,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                leMieAttivitaViewModel.rimuoviPersona(idTask, utente.id)


                                Log.d("IconClick", "Icona cliccata")

                                val contenuto = (userLoggato?.nome + " " + (userLoggato?.cognome
                                    ?: "") + " " + "ti ha assegnato una task")


                                viewModelNotifiche.getNotificaIdByContent(contenuto) { id ->
                                    id?.let { viewModelNotifiche.eliminaNotifica(it) }
                                }


                                navController.navigate("task_selezionata/${idTask}/${idProg}")


                            }
                    )
                }

            }


        }

    }
}