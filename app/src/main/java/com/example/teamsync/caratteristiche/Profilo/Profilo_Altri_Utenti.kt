package com.example.teamsync.caratteristiche.Profilo
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.LeMieAttivita.data.viewModel.LeMieAttivitaViewModel
import com.example.teamsync.caratteristiche.Notifiche.data.repository.RepositoryNotifiche
import com.example.teamsync.caratteristiche.Notifiche.data.viewModel.ViewModelNotifiche
import com.example.teamsync.caratteristiche.ProfiloAmici.StatBox
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.model.Progetto
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
import com.example.teamsync.caratteristiche.login.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.ui.theme.Red70
import com.example.teamsync.util.ThemePreferences


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfiloUtenteCliccato(viewModel: ViewModelUtente, navController: NavHostController, id: String, amicizia: String, provenienza: String, notificheRepo : RepositoryNotifiche, task: String, progetto : String, viewModelprogetto: ViewModelProgetto, viewModelNotifiche: ViewModelNotifiche, sottoprovenienza : String ) {


    var viewModelTodo = LeMieAttivitaViewModel()
    var userProfile by remember { mutableStateOf<ProfiloUtente?>(null) }

    var user_amico by remember { mutableStateOf<ProfiloUtente?>(null) }
    var nome by remember { mutableStateOf("") }
    var cognome by remember { mutableStateOf("") }
    var matricola by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var avvia_notifica by remember { mutableStateOf(false) }
    var accetta_amicizia by remember { mutableStateOf(false) }
    var load = remember { mutableStateOf(true) }
    var numeroTaskCompletati by remember { mutableStateOf("") }
    var numeroProgettiCompletati by remember { mutableStateOf("") }
    var showMenu by remember { mutableStateOf(false) }
    var abilitaBottone by remember { mutableStateOf(false) }
    var disabilitaclick by remember { mutableStateOf(true) }
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    LaunchedEffect(Unit) {
        viewModel.getUserProfile()
    }
    LaunchedEffect(viewModel.userProfile) {
        userProfile = viewModel.userProfile
    }
    LaunchedEffect(userProfile?.id) {
        disabilitaclick = false
        userProfile?.id?.let { userId ->
            viewModelprogetto.caricaProgettiUtente(id, true)
        }
        Log.d("Progetti", "${viewModelprogetto.progetti}")
    }
    LaunchedEffect(viewModelprogetto.isLoading.value) {
        load.value = true
        if (!viewModelprogetto.isLoading.value) {
            abilitaBottone = true
            val progettiCompletati = viewModelprogetto.progetti.value.count { it.completato }
            numeroProgettiCompletati = progettiCompletati.toString()


            var taskCompletati = 0
            var completatiCounter = 0 // Contatore per le callback completate
            val totaleProgetti = viewModelprogetto.progetti.value.size

            if (totaleProgetti == 0) {
                numeroTaskCompletati = "0"
                load.value = false
            } else {
                viewModelprogetto.progetti.value.forEach { progetto ->
                    viewModelTodo.getTodoCompletateByProject2(progetto.id.toString()) { attivitàCompletate ->
                        if (attivitàCompletate.isNotEmpty()) {

                            for (att in attivitàCompletate) {
                                if (att.utenti.contains(id)) {

                                    taskCompletati += 1

                                }

                            }
                        }

                        completatiCounter++
                        if (completatiCounter == totaleProgetti) {
                            numeroTaskCompletati = taskCompletati.toString()
                            load.value = false
                        }
                    }


                }
            }


        }

    }

    LaunchedEffect(id) {
        viewModel.ottieni_utente(id) { profile ->
            user_amico = profile
            nome = profile?.nome ?: ""
            cognome = profile?.cognome ?: ""
            matricola = profile?.matricola ?: ""
            email = profile?.email ?: ""
        }
    }

    LaunchedEffect(avvia_notifica) {
        if (avvia_notifica) {
            viewModel.ottieni_utente(id) { profile ->
                val contenuto = (userProfile?.nome ?: "") + " " + (userProfile?.cognome
                    ?: "") + " " + "ti ha inviato una richiesta di amicizia"

                if (userProfile != null) {
                    viewModelNotifiche.creaNotificaViewModel(
                        userProfile!!.id,
                        profile?.id ?: "",
                        "Richiesta_Amicizia",
                        contenuto,
                        ""


                    )
                }
            }
        }
    }
    LaunchedEffect(accetta_amicizia) {

        if (accetta_amicizia) {
            viewModel.ottieni_utente(id) { profile ->
                val contenuto = (userProfile?.nome ?: "") + " " + (userProfile?.cognome
                    ?: "") + " " + "ha accettato la tua richiesta di amicizia"

                if (userProfile != null) {
                    viewModelNotifiche.creaNotificaViewModel(
                        userProfile!!.id,
                        profile?.id ?: "",
                        "Accetta_Amicizia",
                        contenuto,
                        "",

                        )
                }
            }
        }
    }

    if (showMenu) {
        userProfile?.let {
            AddTodoDialog(
                onDismiss = { showMenu = false },
                id = id,
                id_persona_autenticata = it.id
            )
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Profilo Utenti",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = if (isDarkTheme) Color.White else Color.Black
                    )
                },
                navigationIcon = {
                    if (!disabilitaclick) {
                        Box(
                            modifier = Modifier
                                .size(35.dp)
                                .background(
                                    Color.Black,
                                    RoundedCornerShape(20.dp)
                                )
                                .clickable {
                                    when (provenienza) {
                                        "task" -> {
                                            navController.navigate("task_selezionata/${task}/${progetto}")
                                        }
                                        "progetto" -> {
                                            navController.navigate("progetto_da_accettare/${progetto}/progetto/${sottoprovenienza}")
                                        }
                                        "notifiche" -> {
                                            if (sottoprovenienza == "progetto") {
                                                navController.navigate("progetto_da_accettare/${progetto}/${provenienza}/${sottoprovenienza}")
                                            }
                                            if( sottoprovenienza == "Richiesta_Amicizia")
                                            {
                                                navController.navigate(Schermate.Notifiche.route)
                                            }
                                        }
                                        else -> {
                                            navController.navigate(Schermate.Profilo.route)
                                        }
                                    }

                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(

                                imageVector = Icons.Default.Close,
                                contentDescription = "close_impostazioni",
                                tint = Color.White
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .size(35.dp)
                                .background(
                                    Color.Black,
                                    RoundedCornerShape(20.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color.Black)
                        }

                    }
                },
                actions = {
                },

                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black,
                )
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = if (isDarkTheme) Color.DarkGray else Color.Transparent)

        )
        {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(start = 16.dp, end = 16.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (isDarkTheme) Color.Black else (Red70),
                            RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    if (user_amico?.immagine != null) {

                        Image(
                            painter = // Gestisci l'indicatore di caricamento qui
                            rememberAsyncImagePainter(
                                ImageRequest.Builder // Placeholder di caricamento
                                // Effetto crossfade durante il caricamento
                                    (LocalContext.current).data(user_amico!!.immagine)
                                    .apply(block = fun ImageRequest.Builder.() {
                                        // Gestisci l'indicatore di caricamento qui
                                        placeholder(R.drawable.user_icon) // Placeholder di caricamento
                                        crossfade(true) // Effetto crossfade durante il caricamento
                                    }).build()
                            ),

                            contentDescription = "Immagine Profilo",
                            modifier = Modifier

                                .size(64.dp)
                                .background(Color.White, CircleShape)
                                .padding(16.dp),

                            contentScale = ContentScale.Crop,


                            )

                    } else {

                        Image(
                            painter = painterResource(id = R.drawable.user_icon),
                            contentDescription = "Icona Applicazione",
                            modifier = Modifier
                                .size(64.dp)
                                .background(Color.White, CircleShape)
                                .padding(16.dp),
                        )
                    }

                    Text(
                        text = nome + " " + cognome,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = email,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = matricola,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier.padding(top = 8.dp)
                    )


                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        if (load.value) {
                            Column(
                                modifier = Modifier
                                    .background(Color.White, RoundedCornerShape(8.dp))
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                CircularProgressIndicator(color = Color.Black)
                            }
                        } else {
                            StatBox(number = numeroTaskCompletati, label = "Task Completate")
                            StatBox(
                                number = numeroProgettiCompletati,
                                label = "Progetti Completati"
                            )

                        }

                    }
                    Spacer(modifier = Modifier.height(20.dp))


                }

                Spacer(modifier = Modifier.height(40.dp))
                if (id != (userProfile?.id ?: "")) {
                    if (amicizia == "false") {
                        when (provenienza) {
                            "profilo" -> {
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(70.dp)
                                        .padding(horizontal = 16.dp),
                                    onClick =
                                    {
                                        avvia_notifica = true
                                        navController.navigate(Schermate.Profilo.route)
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (isDarkTheme) Color.Black else Red70, // Cambia il colore di sfondo del pulsante
                                        contentColor = Color.White
                                    )
                                )
                                {
                                    Text(
                                        text = "Richiedi Amicizia",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(vertical = 12.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(5.dp))
                            }

                            "task" -> {
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(70.dp)
                                        .padding(horizontal = 16.dp),
                                    onClick =
                                    {
                                        avvia_notifica = true
                                        navController.navigate("task_selezionata/${task}/${progetto}")
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (isDarkTheme) Color.Black else Red70,  // Cambia il colore di sfondo del pulsante
                                        contentColor = Color.White
                                    )
                                )
                                {
                                    Text(
                                        text = "Richiedi Amicizia",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(vertical = 12.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(5.dp))
                            }


                            "notifiche" -> {
                                when (sottoprovenienza) {
                                    "Richiesta_Amicizia" -> {

                                        Button(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(70.dp)
                                                .padding(horizontal = 16.dp),
                                            onClick =
                                            {
                                                userProfile?.let { profile ->
                                                    viewModel.fai_amicizia(profile.id, id) {
                                                        viewModel.getUserProfile()
                                                        accetta_amicizia = true
                                                        navController.navigate(Schermate.Profilo.route)
                                                    }
                                                }
                                            },
                                            shape = RoundedCornerShape(8.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = if (isDarkTheme) Color.Black else Red70,  // Cambia il colore di sfondo del pulsante
                                                contentColor = Color.White
                                            )
                                        )
                                        {
                                            Text(
                                                text = "Accetta amicizia",
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(vertical = 12.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(5.dp))

                                    }

                                    "progetto" -> {
                                        Button(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(70.dp)
                                                .padding(horizontal = 16.dp),
                                            onClick =
                                            {
                                                avvia_notifica = true
                                                navController.navigate("progetto_da_accettare/${progetto}/${provenienza}/${sottoprovenienza}")
                                            },
                                            shape = RoundedCornerShape(8.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = if (isDarkTheme) Color.Black else Red70,  // Cambia il colore di sfondo del pulsante
                                                contentColor = Color.White
                                            )
                                        )
                                        {
                                            Text(
                                                text = "Richiedi Amicizia",
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(vertical = 12.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(5.dp))

                                    }
                                }
                            }

                            "progetto" -> {
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(70.dp)
                                        .padding(horizontal = 16.dp),
                                    onClick =
                                    {
                                        avvia_notifica = true
                                        navController.navigate("progetto_da_accettare/${progetto}/progetto/${sottoprovenienza}")
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (isDarkTheme) Color.Black else Red70,  // Cambia il colore di sfondo del pulsante
                                        contentColor = Color.White
                                    )
                                )
                                {
                                    Text(
                                        text = "Richiedi Amicizia",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(vertical = 12.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(5.dp))


                            }


                        }
                    }

                    if (amicizia == "true") {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(70.dp)
                                .padding(horizontal = 16.dp),
                            onClick = {

                                userProfile?.let { profile ->
                                    viewModel.finisci_amicizia(profile.id, id)
                                    {
                                        viewModel.getUserProfile()
                                        if (provenienza == "profilo")
                                            navController.navigate(Schermate.Profilo.route)
                                        if (provenienza == "progetto")
                                            navController.navigate("progetto_da_accettare/${progetto}/progetto/${sottoprovenienza}")
                                        if (provenienza == "task")
                                            navController.navigate("task_selezionata/${task}/${progetto}")
                                        if (provenienza == "notifiche") {
                                            if (sottoprovenienza == "progetto")
                                                navController.navigate("progetto_da_accettare/${progetto}/${provenienza}/${sottoprovenienza}")
                                            if (sottoprovenienza == "Richiesta_Amicizia")
                                                navController.navigate(Schermate.Notifiche.route)


                                        }


                                    }
                                }
                            },

                            shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(
                                containerColor = if (isDarkTheme) Color.Black else Red70,  // Cambia il colore di sfondo del pulsante
                                contentColor = Color.White
                            )
                        )
                        {
                            Text(
                                text = "Rimuovi Amicizia",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 12.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        when (provenienza) {
                            "profilo" -> {

                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(70.dp)
                                        .padding(horizontal = 16.dp),

                                    onClick = {
                                        showMenu = true // Mostra l'alert dialog
                                    },


                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (abilitaBottone) Red70 else Color.Gray, // Cambia il colore di sfondo del pulsante
                                        contentColor = Color.White
                                    ),
                                    enabled = abilitaBottone
                                )
                                {
                                    Text(
                                        text = "Aggiungi ad un progetto",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(vertical = 12.dp)
                                    )
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddTodoDialog(
    onDismiss: () -> Unit,
    id: String,
    id_persona_autenticata: String
) {
    val viewModelProgetto = remember { ViewModelProgetto() }
    val viewModel = remember { ViewModelUtente() }
    val viewModelNotifiche = remember { ViewModelNotifiche() }

    var isLoading by remember { mutableStateOf(true) }
    var progetti by remember { mutableStateOf<List<Progetto>>(emptyList()) }

    LaunchedEffect(Unit) {
        viewModelProgetto.caricaProgettiUtente_callback(id_persona_autenticata, true) { progettiCaricati ->
            progetti = progettiCaricati
            isLoading = false
        }
    }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Seleziona un progetto") },
        text = {
            Column {
                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    progetti.forEach { progetto ->
                        Text(
                            text = progetto.nome,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.ottieni_utente(id) { profile ->
                                        val contenuto = (viewModel.userProfile?.nome
                                            ?: "") + " " + (viewModel.userProfile?.cognome
                                            ?: "") + " ti ha invitato in un progetto"
                                        if (viewModel.userProfile != null) {
                                            (
                                                    viewModelNotifiche.creaNotificaViewModel(
                                                viewModel.userProfile!!.id,
                                                profile?.id ?: "",
                                                "Richiesta_Progetto",
                                                contenuto,
                                                progetto.id.toString()
                                                    )
                                            )
                                        }
                                    }
                                    onDismiss()
                                }
                                .padding(8.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Chiudi")
            }
        }
    )
}












@Composable
fun StatBoxUtente(number: String, label: String) {
    Column(
        modifier = Modifier
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = number, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(text = label, fontSize = 12.sp)
    }
}







