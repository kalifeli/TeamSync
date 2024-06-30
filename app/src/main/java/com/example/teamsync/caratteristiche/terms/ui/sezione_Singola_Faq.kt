
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.teamsync.caratteristiche.terms.data.model.terms
import com.example.teamsync.caratteristiche.terms.data.repository.RepositoryTerms
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.ui.theme.ColorePrioritaAlta
import com.example.teamsync.ui.theme.TeamSyncTheme
import com.example.teamsync.util.ThemePreferences
import kotlinx.coroutines.launch
import java.util.Date


@Composable
fun Termini(navController: NavHostController) {
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    // Se il tema è scuro, applichiamo il tema scuro tramite TeamSyncTheme
    if (isDarkTheme) {
        TeamSyncTheme(darkTheme = true) {
            // Contenuto della schermata delle impostazioni
            TermsOfServiceScreenBlack(nav = navController )
        }
    } else {
        // Altrimenti, applichiamo il tema predefinito
        TermsOfServiceScreenWhite(nav = navController)
    }
}

@Composable
fun punti(singolo: terms)
{
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        if(isDarkTheme) {
            Text(
                text = singolo.titolo,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            val paragrafi = singolo.descrizione.split("\\n")
            paragrafi.forEachIndexed { index, paragrafo ->


                Text(
                    text = paragrafo,
                    fontSize = 16.sp,
                    softWrap = true,
                    modifier = Modifier.padding(bottom = 5.dp),
                    color = Color.White

                )
                if (index < paragrafi.size - 1) {
                    Spacer(modifier = Modifier.height(8.dp))
                }


            }
        }
        else
        {
            Text(
            text = singolo.titolo,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,

            modifier = Modifier.padding(vertical = 8.dp)
        )
            val paragrafi = singolo.descrizione.split("\\n")
            paragrafi.forEachIndexed { index, paragrafo ->


                Text(
                    text = paragrafo,
                    fontSize = 16.sp,
                    softWrap = true,
                    modifier = Modifier.padding(bottom = 5.dp),


                )
                if (index < paragrafi.size - 1) {
                    Spacer(modifier = Modifier.height(8.dp))
                }


            }

        }
    }
}


@Composable
fun TermsOfServiceScreenWhite(nav: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val repository = RepositoryTerms()
    val listaTermini = remember { mutableStateListOf<terms>() }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var ultima_modifica by remember { mutableStateOf<Date?>(null) }

    LaunchedEffect(Unit) {
        try {
            val terms = repository.getAllTerms()
            if (terms.isEmpty()) {
                error = "Nessun termine trovato."
            } else {
                listaTermini.addAll(terms)
                ultima_modifica = repository.get_last_update()
            }
        } catch (e: Exception) {
            error = "Errore di connessione: ${e.message}"
        } finally {
            loading = false
        }
    }

    Column(modifier = Modifier.background(Color.White)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .fillMaxHeight(0.08f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .background(Color.Black, RoundedCornerShape(20.dp))
                    .clickable { nav.navigate(Schermate.Impostazioni.route) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back to impostazioni",
                    tint = Color.White
                )
            }

            Row(
                modifier = Modifier.weight(8f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Termini & Condizioni",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {}
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Scaffold { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(paddingValues)
                ) {
                    if (loading) {
                        Text("Caricamento in corso...", fontSize = 16.sp, color = Color.Gray)
                    } else if (error != null) {
                        Text("Errore: $error", fontSize = 16.sp, color = Color.Red)
                    } else {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Text(
                                text = "Ultima modifica: " + ultima_modifica.toString(),
                                fontSize = 14.sp,
                                color = Color.Gray,
                            )

                            LazyColumn(state = listState) {
                                items(listaTermini) { term ->
                                    punti(term)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))


                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    listState.animateScrollToItem(0)
                                }
                            },
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 16.dp),
                            colors = ButtonDefaults.buttonColors( ColorePrioritaAlta)
                        ) {
                            Text("Torna su")

                        }
                    }

                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun TermsOfServiceScreenBlack(nav: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val repository = RepositoryTerms()
    val listaTermini = remember { mutableStateListOf<terms>() }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var ultima_modifica by remember { mutableStateOf<Date?>(null) }

    LaunchedEffect(Unit) {
        try {
            val terms = repository.getAllTerms()
            if (terms.isEmpty()) {
                error = "Nessun termine trovato."
            } else {
                listaTermini.addAll(terms)
                ultima_modifica = repository.get_last_update()
            }
        } catch (e: Exception) {
            error = "Errore di connessione: ${e.message}"
        } finally {
            loading = false
        }
    }

    Column(modifier = Modifier.background(Color.Black)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .fillMaxHeight(0.08f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .background(Color.White, RoundedCornerShape(20.dp))
                    .clickable { nav.navigate(Schermate.Impostazioni.route) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back to impostazioni",
                    tint = Color.Black
                )
            }

            Row(
                modifier = Modifier.weight(8f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Termini & Condizioni",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {}
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Scaffold { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                        .padding(paddingValues)
                ) {
                    if (loading) {
                        Text("Caricamento in corso...", fontSize = 16.sp, color = Color.Gray)
                    } else if (error != null) {
                        Text("Errore: $error", fontSize = 16.sp, color = Color.Red)
                    } else {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Text(
                                text = "Ultima modifica: " + ultima_modifica.toString(),
                                fontSize = 14.sp,
                                color = Color.Gray,
                            )

                            LazyColumn(state = listState) {
                                items(listaTermini) { term ->
                                    punti(term)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))


                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    listState.animateScrollToItem(0)
                                }
                            },
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 16.dp),
                            colors = ButtonDefaults.buttonColors( ColorePrioritaAlta)
                        ) {
                            Text(color = Color.White, text = "Torna su")

                        }
                    }

                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
