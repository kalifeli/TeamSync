

package com.example.teamsync.caratteristiche.ProfiloAmici
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.LeMieAttivita.data.viewModel.LeMieAttivitaViewModel
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
import com.example.teamsync.caratteristiche.iTuoiProgetti.ui.ImmagineProfiloUtente
import com.example.teamsync.caratteristiche.login.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.ui.theme.Grey50
import com.example.teamsync.ui.theme.Red70
import com.example.teamsync.ui.theme.White
import com.example.teamsync.util.ThemePreferences


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfiloSchermata(
    viewModel: ViewModelUtente,
    navController: NavHostController,
    viewModelProgetto: ViewModelProgetto,
    viewmodelTodo : LeMieAttivitaViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Profilo",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = if(isDarkTheme) Color.White else Color.Black
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = if(isDarkTheme) Color.DarkGray else White,
                        titleContentColor =  if(isDarkTheme) Color.White else  Color.Black,
                        actionIconContentColor =  if(isDarkTheme) Color.White else Color.Black,
                    )
                )
            }
        ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = if (isDarkTheme) Color.DarkGray else White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {
                ProfiloHeader(viewModel, navController, viewModelProgetto, viewmodelTodo)
                Spacer(modifier = Modifier.height(16.dp))
                RicercaAggiungiColleghi { query ->
                    searchQuery = query
                }
                Spacer(modifier = Modifier.height(16.dp))
                ListaColleghi(viewModel, navController, searchQuery, isDarkTheme)
            }
        }
    }
}



@Composable
fun ProfiloHeader(
    viewModel: ViewModelUtente,
    navController: NavHostController,
    viewModelProgetto: ViewModelProgetto,
    viewModelTodo: LeMieAttivitaViewModel
) {
    val isLoading by viewModelProgetto.isLoading.observeAsState()
    val userProfile by viewModel.userProfilo.observeAsState()
    val progetti by viewModelProgetto.progetti1.observeAsState()
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    var numeroTaskCompletati by remember { mutableStateOf("") }
    var numeroProgettiCompletati by remember { mutableStateOf("") }
    val load = remember { mutableStateOf(true) }

    //Carica il profilo utente all'avvio
    LaunchedEffect(Unit) {
        viewModel.getUserProfile()
    }

    // Carica i progetti utente quando cambia l'ID dell'utente
    LaunchedEffect(userProfile?.id) {
        userProfile?.id?.let { userId ->
            viewModelProgetto.caricaProgettiUtente(userId, false)
        }
    }


    LaunchedEffect(isLoading) {
        if (isLoading == false && load.value) {
            userProfile?.id?.let { userId ->
                progetti?.let { listaProgetti ->
                    val progettiCompletati = listaProgetti.count { it.completato }
                    numeroProgettiCompletati = progettiCompletati.toString()

                    var taskCompletati = 0
                    var completatiCounter = 0 // Contatore per le callback completate
                    val totaleProgetti = listaProgetti.size

                    if(totaleProgetti == 0){
                        numeroTaskCompletati = "0"
                        load.value = false
                    }else{
                        listaProgetti.forEach { progetto ->
                            viewModelTodo.getTodoCompletateByProject2(progetto.id.toString()) { attivitàCompletate ->
                                if (attivitàCompletate.isNotEmpty()) {
                                    for (att in attivitàCompletate) {
                                        if (att.utenti.contains(userId)) {
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
        }
    }

    val nome by remember { mutableStateOf(userProfile?.nome ?: "") }
    val cognome by remember { mutableStateOf(userProfile?.cognome ?: "") }

    ElevatedCard(
        onClick = {
            navController.navigate(Schermate.Profilo.route)
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .border(1.dp, if (isDarkTheme) White else White, shape = RoundedCornerShape(16.dp)),
        colors = CardDefaults.elevatedCardColors(
            containerColor =  if(isDarkTheme) Color.Black else Red70
        ),
        shape = RoundedCornerShape(16.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ImmagineProfiloUtente(
                imageUrl = userProfile?.immagine,
                defaultImage = R.drawable.logo_rotondo,
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.White, CircleShape)
            )

            Text(
                text = "$nome $cognome ",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
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
                            .background(
                                if (isDarkTheme) Color.DarkGray else Color.White,
                                RoundedCornerShape(8.dp)
                            )
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        CircularProgressIndicator(color = if(isDarkTheme) Color.White else Color.Black)
                    }
                } else {
                    StatBox(number = numeroTaskCompletati, label = "Task Completate")
                    StatBox(number = numeroProgettiCompletati, label = "Progetti Completati")

                }
            }
        }
    }
}

@Composable
fun StatBox(number: String, label: String) {
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    Column(
        modifier = Modifier
            .background(
                if (isDarkTheme) Color.DarkGray else Color.White,
                RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = number, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = if(isDarkTheme) Color.White else Color.Black)
        Text(text = label, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = if(isDarkTheme) Color.White else Color.Black)
    }
}


@Composable

fun RicercaAggiungiColleghi(onSearch: (String) -> Unit) {
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    var searchQuery by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp)
            .background(
                color = if (isDarkTheme) Color.Black else Color.White,
                shape = RoundedCornerShape(50.dp)
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { newValue ->
                searchQuery = newValue
            },
            placeholder = { Text("Trova colleghi", color = if(isDarkTheme) Color.White else Color.Black ) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            maxLines = 1,
            leadingIcon = {

                IconButton(onClick = {
                    onSearch(searchQuery)  // Passaggio della query di ricerca corrente
                }) {
                    Icon(Icons.Default.Search, contentDescription = null, tint = if(isDarkTheme) White else Color.Black)
                }

            },
            shape = RoundedCornerShape(50.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = if(isDarkTheme) Color.Black else  Color.White,
                focusedContainerColor = White
            )
        )
    }
}


@Composable
fun ListaColleghi(
    viewModel: ViewModelUtente,
    navController: NavHostController,
    searchQuery: String,
    isDarkTheme: Boolean
) {
    val userProfile by viewModel.userProfilo.observeAsState()

    var amici by remember { mutableStateOf(userProfile?.amici ?: emptyList()) }

    val cache = remember { mutableStateMapOf<String, ProfiloUtente?>() } // Utilizzo di mutableStateMapOf per la cache

    var lista_ricerca by remember { mutableStateOf<List<String>>(emptyList()) }
    var mostra_ricerca by remember { mutableStateOf(false) }
    var visualizza_amici by remember { mutableStateOf(true) }

    val searchIsLoading by viewModel.isLoading.observeAsState()

    userProfile?.let {
        amici = it.amici
    }

    // Utilizzo di LaunchedEffect per gestire il cambio di stato di amici
    LaunchedEffect(userProfile) {
        amici = userProfile?.amici ?: emptyList()
    }

    if(searchIsLoading == true){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                color = Grey50,
                trackColor = Red70,
                strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap
            )
        }
    }

    if(visualizza_amici) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),

        ) {
            Text(
                text = "I Tuoi Amici (${userProfile?.amici?.size})",
                style = MaterialTheme.typography.labelLarge,
                color = if(isDarkTheme) White else Color.Black
            )
        }

        if(userProfile?.amici?.size == 0){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding()
                    .background(if(isDarkTheme) Color.Black else White),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.im_amici),
                    contentDescription = "immagine nessun amico",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(16.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "La tua lista amici è vuota. Cerca colleghi per iniziare a connetterti.",
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelLarge,
                    color = if (isDarkTheme) White else Color.Black
                )
            }
        }
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

                user_amico?.let { collega ->
                    CollegaItem(
                        collega,
                        navController,
                        userProfile
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotBlank()) {
            viewModel.filterAmici(searchQuery) { risultati ->
                visualizza_amici = false
                mostra_ricerca = true
                lista_ricerca = risultati
            }
        } else if (searchQuery == ""){
            visualizza_amici = true
            mostra_ricerca = false
        }
        else{
            visualizza_amici = true
            mostra_ricerca = false

        }
    }
    if (mostra_ricerca) {
        LazyColumn {
            items(lista_ricerca) { amico ->
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

                user_amico?.let { collega ->
                    CollegaItem(
                        collega,
                        navController,
                        userProfile
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun CollegaItem(
    utente: ProfiloUtente,
    navController: NavHostController,
    user_loggato: ProfiloUtente?
) {
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    LaunchedEffect(utente.id) {
        println("l'utente è cambiato " + utente.id)
        Log.d("cambio id", "Dati: $utente.id")
    }

    var amicizia = false
    if(utente.amici.contains(user_loggato?.id)) {
        amicizia = true
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isDarkTheme) Color.Black else Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        navController.navigate("utente/${utente.id}/${amicizia}/profilo/0/0/profilo")
                    }
                )
            }
    ) {
        ImmagineProfiloUtente(
            imageUrl = utente.immagine,
            defaultImage = R.drawable.logo_rotondo,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.dp, Color.White, CircleShape),
        )


        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = utente.nome + " " + utente.cognome, fontWeight = FontWeight.Bold, fontSize = 16.sp,  color = if(isDarkTheme) Color.White else Color.Black)
            Text(text = utente.matricola, fontSize = 14.sp, color = if(isDarkTheme) Color.White else Color.Black)
        }
        if (!amicizia) {
            Spacer(modifier = Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Aggiungi",
                    tint =  if(isDarkTheme) Color.White else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

    }
}





