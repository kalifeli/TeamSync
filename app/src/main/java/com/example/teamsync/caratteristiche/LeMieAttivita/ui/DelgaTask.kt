package com.example.teamsync.caratteristiche.LeMieAttivita.ui


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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.LeMieAttivita.data.model.LeMieAttivita
import com.example.teamsync.caratteristiche.LeMieAttivita.data.viewModel.LeMieAttivitaViewModel
import com.example.teamsync.caratteristiche.Notifiche.data.viewModel.ViewModelNotifiche
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
import com.example.teamsync.caratteristiche.login.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente
import com.example.teamsync.ui.theme.Red70
import com.example.teamsync.ui.theme.White
import com.example.teamsync.ui.theme.WhiteFacebook
import com.example.teamsync.util.ThemePreferences
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun Lista_Utenti_assegna_Task(viewModel: ViewModelUtente, navController: NavHostController, viewModel_att: LeMieAttivitaViewModel, id_task : String, view_model_prog: ViewModelProgetto, id_prog : String, view_model_notifiche : ViewModelNotifiche) {
    var task by remember { mutableStateOf<LeMieAttivita?>(null) }
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    LaunchedEffect(id_task) {
        task = viewModel_att.getTodoById(id_task)
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
                        ) // Imposta il rettangolo di sfondo a nero
                        .clickable { navController.navigate("progetto/${id_prog}") },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "close_impostazioni",
                        tint = Color.DarkGray // Assicurati che l'icona sia visibile impostando il colore a bianco
                    )
                }
                // Centra il testo all'interno della Row
                Row(
                    modifier = Modifier.weight(8f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Aggiungi Persone",
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
                    viewModel_att,
                    id_task,
                    view_model_prog,
                    id_prog,
                    view_model_notifiche
                )
            }
        }
    }

}


@Composable
fun ProfiloHeader(viewModel: ViewModelUtente, navController: NavHostController, task : LeMieAttivita) {
    viewModel.getUserProfile()
    val userProfile = viewModel.userProfile
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    var nome by remember { mutableStateOf(userProfile?.nome ?: "") }
    var amici by remember { mutableStateOf(userProfile?.amici ?: "") }
    var id_task by remember { mutableStateOf(task.id) }
    var id_prog by remember { mutableStateOf(task.progetto ) }

    userProfile?.let {
        nome = it.nome
        amici = it.amici
    }

    ElevatedCard(
        onClick = {
            navController.navigate("task_selezionata/${id_task}/${id_prog}")
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, if(isDarkTheme) White else White,shape = RoundedCornerShape(16.dp)),
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
                        text = "Dettagli Task",
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
                text = "Nome: ${task.titolo}",
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Priorità: ${task.priorita}",
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Data scadenza: ${SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(task.dataScadenza)}",
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Descrizione: ${task.descrizione}",
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
            )
        }
    }

}



@Composable
fun ListaColleghi(
    viewModel: ViewModelUtente,
    navController: NavHostController,
    viewModel_att: LeMieAttivitaViewModel,
    id_task : String,
    viewModel_Prog : ViewModelProgetto,
    id_progetto : String,
    view_model_notifiche: ViewModelNotifiche
) {
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    val userProfile = viewModel.userProfile
    var amici by remember { mutableStateOf<List<String>>(emptyList()) }
    var task by remember { mutableStateOf<LeMieAttivita?>(null) }
    val cache =
        remember { mutableStateMapOf<String, ProfiloUtente?>() } // Utilizzo di mutableStateMapOf per la cache
    var isLoading by remember { mutableStateOf(true) }
    var visualizza_amici by remember { mutableStateOf(true) }



    LaunchedEffect(Unit) {
        task = viewModel_att.getTodoById(id_task = id_task)
        if (userProfile != null) {
            amici = viewModel_Prog.getLista_Partecipanti(id_progetto, userProfile.id)
        }
    }
    // Utilizzo di LaunchedEffect per gestire il cambio di stato di amici
    LaunchedEffect(userProfile) {
        viewModel_Prog.getLista_Partecipanti(id_progetto) { partecipanti -> amici = partecipanti }
        println(
            "partecipanti" + " " + amici
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
            .border(1.dp, if(isDarkTheme) White else White,shape = RoundedCornerShape(16.dp)),
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
                text = "Lista Partecipanti->",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDarkTheme) Color.White else Color.Black
            )
        }
        Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp)){


            if (visualizza_amici) {

                LazyColumn {
                    item {
                        if (!isLoading) {

                            if (task != null && userProfile != null && task!!.utenti.contains(
                                    userProfile.id
                                )
                            ) {
                                CollegaItem(
                                    userProfile,
                                    color = Red70,
                                    navController = navController,
                                    userProfile,
                                    true,
                                    viewModel_att,
                                    id_task,
                                    id_progetto,
                                    view_model_notifiche
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
                    items(amici) { amico ->

                        var user_amico by remember { mutableStateOf<ProfiloUtente?>(null) }


                        if (cache.contains(amico)) {
                            user_amico = cache[amico]
                        } else {
                            viewModel.ottieni_utente(amico) { profile ->
                                user_amico = profile
                                cache[amico] = profile // Aggiungi il profilo alla cache
                                Log.d("Singolo Collega", "Dati: $user_amico")
                            }


                        }


                        var partecipa = task?.utenti?.contains(amico) ?: false

                        if (!isLoading) {
                            user_amico?.let { collega ->

                                CollegaItem(
                                    collega,
                                    color = Red70,
                                    navController,
                                    userProfile,
                                    partecipa,
                                    viewModel_att,
                                    id_task,
                                    id_progetto,
                                    view_model_notifiche


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







@Composable
fun CollegaItem(utente : ProfiloUtente, color: Color, navController: NavHostController, user_loggato: ProfiloUtente?, partecipa : Boolean, viewModel_att: LeMieAttivitaViewModel, id_task : String, id_prog: String, view_model_notifiche: ViewModelNotifiche) {

    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    var amicizia = false
    if (utente.amici.contains(user_loggato?.id) && ( user_loggato!!.id != utente.id) ) {
        amicizia = true
    }


    ElevatedCard(
        onClick = {

                navController.navigate("utente/${utente.id}/${amicizia}/task/${id_task}/${id_prog}/progetto")
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

            if (!partecipa && user_loggato!!.id != utente.id ) {
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
                                viewModel_att.aggiungi_persona(id_task, utente.id)
                                Log.d("IconClick", "Icona cliccata")
                                val contenuto = (user_loggato?.nome + " " + (user_loggato?.cognome
                                    ?: "") + " " + "ti ha assegnato una task")
                                view_model_notifiche.creaNotificaViewModel(
                                    utente.id,
                                    utente.id ?: "",
                                    "Assegnazione_Task",
                                    contenuto,
                                    id_prog

                                )
                                navController.navigate("task_selezionata/${id_task}/${id_prog}")


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
                                viewModel_att.rimuovi_persona(id_task, utente.id)


                                Log.d("IconClick", "Icona cliccata")

                                val contenuto = (user_loggato?.nome + " " + (user_loggato?.cognome
                                    ?: "") + " " + "ti ha assegnato una task")


                                view_model_notifiche.getNotificaIdByContent(contenuto) { id ->
                                    id?.let { view_model_notifiche.eliminaNotifica(it) }
                                }


                                navController.navigate("task_selezionata/${id_task}/${id_prog}")


                            }
                    )
                }

            }


        }

    }
}








