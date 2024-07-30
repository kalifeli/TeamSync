package com.example.teamsync.caratteristiche.ProfiloAmici
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.LeMieAttivita.data.model.LeMieAttivita
import com.example.teamsync.caratteristiche.LeMieAttivita.data.viewModel.LeMieAttivitaViewModel
import com.example.teamsync.caratteristiche.Notifiche.data.viewModel.ViewModelNotifiche
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.model.Progetto
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
import com.example.teamsync.caratteristiche.login.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.ui.theme.Red70
import com.example.teamsync.ui.theme.White
import com.example.teamsync.util.ThemePreferences
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Progetto(viewModel: ViewModelUtente, navController: NavHostController, viewModel_att: LeMieAttivitaViewModel, view_model_prog: ViewModelProgetto, id_prog : String, viewNotifiche: ViewModelNotifiche, provenienza : String, sottoprovenienza: String) {

    val userProfile by viewModel.userProfilo.observeAsState()
    val searchQuery by remember { mutableStateOf("") }
    var progetto_ by remember { mutableStateOf<Progetto?>(null) }
    var listap by remember { mutableStateOf<List<String>?>(emptyList()) }
    var nomeProgetto by remember { mutableStateOf("") }
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)


    LaunchedEffect(Unit) {
        //viewModel.getUserProfile()
        progetto_ = view_model_prog.get_progetto_by_id(id_prog)
        listap = view_model_prog.getLista_Partecipanti(id_prog)

        nomeProgetto = view_model_prog.getnome_progetto(id_prog)

    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Info Progetto",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = if(isDarkTheme) Color.White else Color.Black

                    )
                },
                navigationIcon = {
                    IconButton(onClick = {

                        when (provenienza) {
                            "notifiche" -> navController.navigate(Schermate.Notifiche.route)
                            "progetto" -> {
                                when (sottoprovenienza) {
                                    "progetto"->navController.navigate("progetto/${id_prog}")
                                    "notifiche"->navController.navigate(Schermate.Notifiche.route)

                                }
                            }
                        }
                    }) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "icona indietro",
                            tint = if (isDarkTheme)Color.White else Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = if(isDarkTheme) Color.DarkGray else White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isDarkTheme) Color.DarkGray else White)
                .padding(padding)
                .padding(16.dp)
        ) {


            ProfiloProgetto(
                        viewModel,
                        view_model_prog,
                        navController,
                        id_prog,
                userProfile
                    )
            Spacer(modifier = Modifier.height(16.dp))



            listap?.let {
                ListaColleghi(
                    viewModel,
                    navController,
                    searchQuery,
                    viewModel_att,
                    view_model_prog,
                    id_prog,
                    it,
                    nomeProgetto,
                    viewNotifiche,
                    sottoprovenienza,
                    provenienza
                )
            }


                }
            }
        }



@Composable
fun ProfiloProgetto(viewModel: ViewModelUtente, viewModelProgetto: ViewModelProgetto, navController: NavHostController, progetto : String, userProfile: ProfiloUtente?) {
    viewModel.getUserProfile()
    var progetto_ by remember { mutableStateOf<Progetto?>(null) }
    LaunchedEffect(Unit){
        progetto_ = viewModelProgetto.get_progetto_by_id(progetto)
    }

    val formatoData = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val dataCreazione = formatoData.format(progetto_?.dataCreazione ?: Date())
    val dataScadenza = formatoData.format(progetto_?.dataScadenza ?: Date())
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(1.dp, if (isDarkTheme) White else White, shape = RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),
        shape = RoundedCornerShape(16.dp),
        colors = if(isDarkTheme)CardDefaults.cardColors(Color.Black) else CardDefaults.cardColors(Red70),

    ) {
        Text(
            text = "Informazioni",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        InfoText(informazione = "Nome", testo = progetto_?.nome)
        InfoText(informazione = "Descrizione", testo = progetto_?.descrizione)
        InfoText(informazione = "Priorità", testo = progetto_?.priorita?.name)
        InfoText(informazione = "Data Creazione", testo = dataCreazione)
        InfoText(informazione = "Data Scadenza", testo = dataScadenza)
    }
}
@Composable
fun InfoText(informazione: String, testo: String?){
    Text(
        text = "$informazione: $testo",
        style = MaterialTheme.typography.labelLarge,
        color = White,
        modifier = Modifier.padding(8.dp)
    )
}



@Composable
fun ListaColleghi(
    viewModel: ViewModelUtente,
    navController: NavHostController,
    searchQuery: String,
    viewModel_att: LeMieAttivitaViewModel,
    viewModel_Prog : ViewModelProgetto,
    id_progetto : String,
    listap : List<String>,
    nomeProgetto : String,
    viewNotifiche: ViewModelNotifiche,
    sottoprovenienza : String,
    provenienza: String
) {
    val userProfile by viewModel.userProfilo.observeAsState()
    var amici by remember { mutableStateOf<List<String>>(emptyList()) }
    val task by remember { mutableStateOf<LeMieAttivita?>(null) }
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    val cache = remember { mutableStateMapOf<String, ProfiloUtente?>() } // Utilizzo di mutableStateMapOf per la cache
    val visualizza_amici by remember { mutableStateOf(true) }
    val mostraPulsante = remember {
        mutableStateOf(false)
    }
    val caricamento_tasto =  remember{
        mutableStateOf(true)
    }


    LaunchedEffect(Unit) {
        if (userProfile != null) {
            amici = viewModel_Prog.getLista_Partecipanti(id_progetto, userProfile!!.id)
        }
        userProfile?.let {
            viewModel_Prog.getProgettiUtenteByIdUtente(it.id) { progetti, id ->
                mostraPulsante.value = !(viewModel_Prog.utentePartecipa(progetti,id_progetto))
                Log.d("utente", "valore: ${userProfile?.id}")
                Log.d("lista", "valore: $progetti")
                Log.d("progetto da controllare", "valore: $id_progetto")
                Log.d("mostra pulsante1", "Dati: ${!(viewModel_Prog.utentePartecipa(progettiUtente = progetti, id_progetto))}")
                Log.d("mostra pulsante2", "valore: $mostraPulsante")
                caricamento_tasto.value = false

            }
        }

    }
    // Utilizzo di LaunchedEffect per gestire il cambio di stato di amici
    LaunchedEffect(userProfile) {
        amici = viewModel_Prog.getLista_Partecipanti(id_progetto)
    }

    if(visualizza_amici)
    {
        OutlinedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .border(1.dp, if (isDarkTheme) White else White, shape = RoundedCornerShape(16.dp)),
            colors = if(isDarkTheme)CardDefaults.cardColors(containerColor = Color.Black) else  CardDefaults.cardColors(containerColor = White)
        ) {
            Text(
                text = "Partecipanti",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = if(isDarkTheme) Color.White else Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )
            LazyColumn {
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
                    // Verifica se l'amico è già nella lista degli utenti di task
                    val partecipa = task?.utenti?.contains(amico) ?: false

                    user_amico?.let { collega ->
                        CollegaItem(
                            collega,
                            color = Red70,
                            navController,
                            userProfile,
                            partecipa,
                            viewModel_att,
                            id_progetto,
                            sottoprovenienza,
                            provenienza
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

            }


            }
        if (caricamento_tasto.value) {
            CircularProgressIndicator(color = if (isDarkTheme)Color.White else Color.Black)
        }
        else {
            if (mostraPulsante.value) {

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(horizontal = 16.dp),
                    onClick =
                    {
                        viewModel_Prog.aggiungiPartecipanteAlProgetto(
                            id_progetto,
                            userProfile?.id ?: ""
                        )
                        for (p in listap) {
                            if (p != userProfile?.id) {
                                val contenuto =
                                    userProfile?.nome + " " + (userProfile?.cognome
                                        ?: "") + " " + "è entrato nel progetto " + nomeProgetto
                                userProfile?.id?.let {
                                    viewNotifiche.creaNotificaViewModel(
                                        it,
                                        p,
                                        "Entra_Progetto",
                                        contenuto,
                                        id_progetto
                                    )
                                }
                            }


                        }

                        mostraPulsante.value = false
                    },
                    shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(
                        containerColor = Red70, // Cambia il colore di sfondo del pulsante
                        contentColor = Color.White
                    )
                )
                {
                    Text(
                        text = "Entra nel progetto",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }
        }
        }
    }





@Composable
fun CollegaItem(utente : ProfiloUtente, color: Color, navController: NavHostController, user_loggato: ProfiloUtente?, partecipa : Boolean,
                viewModel_att: LeMieAttivitaViewModel,  id_prog: String, sottoprovenienza : String, provenienza: String) {

    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    LaunchedEffect(utente.id) {
        println("l'utente è cambiato " + utente.id)
        Log.d("cambio id", "Dati: $utente.id")
    }
    var amicizia = false
    if (utente.amici.contains(user_loggato?.id)) {
        amicizia = true

    }
    Log.d("Amici?", "Dati: $amicizia")
    ElevatedCard(
        onClick = {
            navController.navigate("utente/${utente.id}/${amicizia}/${provenienza}/1/${id_prog}/${sottoprovenienza}")},
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = if(isDarkTheme)CardDefaults.cardColors(Color.Gray) else CardDefaults.cardColors(White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        if (isDarkTheme) Color.White else color.copy(alpha = 0.2f),
                        CircleShape
                    )
                    .padding(8.dp),
                painter = painterResource(id = R.drawable.logo_white),
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "${utente.nome} ${utente.cognome}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = if(isDarkTheme) Color.White else Color.Black
                )
                Text(
                    text = utente.matricola,
                    fontSize = 14.sp,
                    color = if(isDarkTheme) Color.White else Color.Black
                )
            }
        }
    }
}





















