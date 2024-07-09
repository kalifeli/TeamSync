

package com.example.teamsync.caratteristiche.Profilo
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.login.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente


@Composable
fun ProfiloSchermata(viewModel: ViewModelUtente, navController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }


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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(8f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text =  "Profilo",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            // Row vuota per bilanciare il layout
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {}

        }
        ProfiloHeader(viewModel, navController)
        Spacer(modifier = Modifier.height(16.dp))
        RicercaAggiungiColleghi { query ->
            // Esegui l'azione di ricerca qui
            searchQuery = query

        }
        Spacer(modifier = Modifier.height(16.dp))
        ListaColleghi(viewModel, navController, searchQuery)
    }
}



@Composable
fun ProfiloHeader(viewModel: ViewModelUtente, navController: NavHostController) {
        viewModel.getUserProfile()
        val userProfile = viewModel.userProfile

        var nome by remember { mutableStateOf(userProfile?.nome ?: "") }
        var amici by remember { mutableStateOf(userProfile?.amici ?: "") }



        userProfile?.let {
                    nome = it.nome
                    amici = it.amici
        }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFEF5350), RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (userProfile?.immagine != null) {

            Image(
                painter = // Gestisci l'indicatore di caricamento qui
                rememberAsyncImagePainter(ImageRequest.Builder // Placeholder di caricamento
                // Effetto crossfade durante il caricamento
                    (LocalContext.current).data(userProfile.immagine).apply(block = fun ImageRequest.Builder.() {
                    // Gestisci l'indicatore di caricamento qui
                    placeholder(R.drawable.white) // Placeholder di caricamento
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

        }

        else {

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
            StatBox(number = "13", label = "Task Completate")
            StatBox(number = "2", label = "Progetti Completati")
        }
    }
}



@Composable
fun StatBox(number: String, label: String) {
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun RicercaAggiungiColleghi(onSearch: (String) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp)
            .background(color = Color.White, shape = RoundedCornerShape(50.dp)),
        contentAlignment = Alignment.CenterStart
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { newValue ->
                searchQuery = newValue
            },
            placeholder = { Text("Ricerca Colleghi/Aggiungi Colleghi") },
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
            shape = RoundedCornerShape(50.dp)
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
        amici = it.amici
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
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {

                        navController.navigate("utente/${utente.id}/${amicizia}/profilo")

                    }
                )
            }
    ) {

            Icon(
                painter = painterResource(id = R.drawable.logo_teamsync),
                contentDescription = null,

                modifier = Modifier
                    .size(32.dp)
                    .background(color.copy(alpha = 0.2f), CircleShape)
                    .padding(8.dp)
            )


        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = utente.nome + " " + utente.cognome, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = utente.matricola, fontSize = 14.sp)
        }
        if (!amicizia) {
            Spacer(modifier = Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Aggiungi",
                    tint = Color.Gray,
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




