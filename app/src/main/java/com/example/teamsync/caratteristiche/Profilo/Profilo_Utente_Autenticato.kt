

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
import com.example.teamsync.caratteristiche.login.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.ui.theme.Red70
import com.example.teamsync.ui.theme.White
import com.example.teamsync.util.ThemePreferences


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfiloSchermata(viewModel: ViewModelUtente, navController: NavHostController, viewModelProgetto: ViewModelProgetto, viewmodelTodo : LeMieAttivitaViewModel) {
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
                    actions = {
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = if(isDarkTheme) Color.DarkGray else Color.Transparent,
                        titleContentColor =  if(isDarkTheme) Color.White else  Color.Black,
                        actionIconContentColor =  if(isDarkTheme) Color.White else Color.Black,
                    )
                )
            }
        ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = if (isDarkTheme) Color.DarkGray else Color.Transparent)
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
                ListaColleghi(viewModel, navController, searchQuery)
            }
        }
    }



}



@Composable
fun ProfiloHeader(viewModel: ViewModelUtente, navController: NavHostController, viewModelProgetto: ViewModelProgetto, viewModelTodo: LeMieAttivitaViewModel) {

    var userProfile by remember { mutableStateOf<ProfiloUtente?>(null) }
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    var numeroTaskCompletati by remember { mutableStateOf("") }
    var numeroProgettiCompletati by remember { mutableStateOf("") }
    var load = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.getUserProfile()
    }


    LaunchedEffect(viewModel.userProfile) {
        userProfile = viewModel.userProfile
    }

    LaunchedEffect(userProfile?.id) {
        userProfile?.id?.let { userId ->
            viewModelProgetto.caricaProgettiUtente(userId, true)
        }
    }




    LaunchedEffect(viewModelProgetto.isLoading.value) {
        load.value = true
        if (!viewModelProgetto.isLoading.value) {
            userProfile?.id?.let { userId ->
                val progettiCompletati = viewModelProgetto.progetti.value.count { it.completato }
                numeroProgettiCompletati = progettiCompletati.toString()


                var taskCompletati = 0
                var completatiCounter = 0 // Contatore per le callback completate
                val totaleProgetti = viewModelProgetto.progetti.value.size

                viewModelProgetto.progetti.value.forEach { progetto ->
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






    userProfile = viewModel.userProfile

    var nome by remember { mutableStateOf(userProfile?.nome ?: "") }
    var amici by remember { mutableStateOf(userProfile?.amici ?: "") }



    userProfile?.let {
        nome = it.nome
        amici = it.amici
    }



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
            .border(1.dp, if(isDarkTheme) White else White,shape = RoundedCornerShape(16.dp)),
        colors = CardDefaults.elevatedCardColors(
            containerColor =  if(isDarkTheme) Color.Black else Red70
        ),
        shape = RoundedCornerShape(16.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (userProfile?.immagine != null) {

                Image(
                    painter = // Gestisci l'indicatore di caricamento qui
                    rememberAsyncImagePainter(
                        ImageRequest.Builder // Placeholder di caricamento
                        // Effetto crossfade durante il caricamento
                            (LocalContext.current).data(userProfile!!.immagine)
                            .apply(block = fun ImageRequest.Builder.() {
                                // Gestisci l'indicatore di caricamento qui
                                placeholder(R.drawable.white) // Placeholder di caricamento
                                crossfade(true) // Effetto crossfade durante il caricamento
                            }).build()
                    ),

                    contentDescription = "Immagine Profilo",
                    modifier = Modifier

                        .size(64.dp)
                        .background(Color.White, CircleShape)
                        .padding(10.dp),

                    contentScale = ContentScale.Crop,


                    )

            } else {

                Image(
                    painter = painterResource(id = R.drawable.user_icon),
                    contentDescription = "Icona Applicazione",
                    modifier = Modifier
                        .size(64.dp)
                        .background(Color.White, CircleShape)
                        .padding(10.dp),
                )
            }
            Text(
                text = nome,
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
                            .background(if(isDarkTheme) Color.DarkGray else Color.White, RoundedCornerShape(8.dp))
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
            .background( if(isDarkTheme) Color.DarkGray else Color.White,
                RoundedCornerShape(8.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = number, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = if(isDarkTheme) Color.White else Color.Black)
        Text(text = label, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = if(isDarkTheme) Color.White else Color.Black)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
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
                color =  if(isDarkTheme) Color.Black else  Color.White,
                shape = RoundedCornerShape(50.dp)),
        contentAlignment = Alignment.CenterStart
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { newValue ->
                searchQuery = newValue
            },
            placeholder = { Text("Ricerca Colleghi/Aggiungi Colleghi", color = if(isDarkTheme) Color.White else Color.Black ) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            maxLines = 1,
            leadingIcon = {

                IconButton(onClick = {
                    onSearch(searchQuery)  // Passaggio della query di ricerca corrente
                }) {
                    Icon(Icons.Default.Search, contentDescription = null)
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
    searchQuery: String
) {
    val userProfile = viewModel.userProfile
    var amici by remember { mutableStateOf(userProfile?.amici ?: emptyList()) }

    val cache = remember { mutableStateMapOf<String, ProfiloUtente?>() } // Utilizzo di mutableStateMapOf per la cache

    var lista_ricerca by remember { mutableStateOf<List<String>>(emptyList()) }

    var mostra_ricerca by remember { mutableStateOf(false) }


    var visualizza_amici by remember { mutableStateOf(true) }

    userProfile?.let {
        amici = it.amici ?: emptyList()
    }

    // Utilizzo di LaunchedEffect per gestire il cambio di stato di amici
    LaunchedEffect(userProfile) {
        amici = userProfile?.amici ?: emptyList()
    }

    if(visualizza_amici)
    {
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
                        color = getColore(amici.indexOf(amico) + 1),
                        navController,
                        userProfile,
                        amico
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
                        color = getColore(lista_ricerca.indexOf(amico) + 1),
                        navController,
                        userProfile,
                        amico
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun CollegaItem(utente : ProfiloUtente, color: Color, navController: NavHostController, user_loggato: ProfiloUtente?, y : String) {
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    LaunchedEffect(utente.id) {
        println("l'utente è cambiato " + utente.id)
        Log.d("cambio id", "Dati: $utente.id")
    }

    var amicizia = false
    if(utente.amici.contains(user_loggato?.id))
    {
        amicizia = true

    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(if(isDarkTheme) Color.Black else Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {

                        navController.navigate("utente/${utente.id}/${amicizia}/profilo/0/0/profilo")

                    }
                )
            }
    ) {

        Icon(
            painter = painterResource(id = R.drawable.logo_white ),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .background(
                    if(isDarkTheme) Color.White else color.copy(alpha = 0.2f),
                    CircleShape)
                .padding(8.dp)
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



@Composable
fun getColore(number: Int): Color {
    return when (number) {
        1 -> Color.Blue
        2 -> Color.Green
        3 -> Color.Red
        4 -> Color.Yellow
        5 -> Color.Magenta
        6 -> Color.Gray
        else -> Color.Black
    }
}




