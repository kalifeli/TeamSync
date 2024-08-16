package com.example.teamsync.caratteristiche.impostazioni.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.impostazioni.data.model.Faq
import com.example.teamsync.caratteristiche.impostazioni.data.repository.RepositoryFaq
import com.example.teamsync.caratteristiche.impostazioni.data.viewModel.FaqViewModel
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.util.ThemePreferences
import com.example.teamsync.util.theme.Grey20
import com.example.teamsync.util.theme.White


/**
 * Composable che rappresenta una singola FAQ, visualizzando la domanda e, se espansa, la risposta.
 *
 * @param faqItem L'oggetto [Faq] che contiene la domanda e la risposta da visualizzare.
 */
@Composable
fun SingolaFaq(faqItem: Faq) {
    var expanded by remember { mutableStateOf(false) }
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        // Box per visualizzare la domanda
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = if(isDarkTheme) Color.Black else White,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(0.5.dp, White, RoundedCornerShape(8.dp))
                .padding(16.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { expanded = !expanded })
                }
        ) {
            Text(
                text = faqItem.domanda,
                textAlign = TextAlign.Start,
                color = if (isDarkTheme) Color.White else Color.Black,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Box per visualizzare la risposta, se espansa
        if (expanded) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = if (isDarkTheme) Color.DarkGray else Grey20,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(0.5.dp, White, RoundedCornerShape(8.dp))
                    .padding(16.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = { expanded = !expanded })
                    }
            ) {
                Text(
                    text = faqItem.risposta,
                    textAlign = TextAlign.Start,
                    color = if (isDarkTheme) Color.White else Color.Black
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

/**
 * Funzione di utilità per recuperare la sezione delle FAQ in base al nome della sezione.
 *
 * @param sezione Il nome della sezione da tradurre.
 * @return Il nome della sezione tradotto.
 */
@Composable
fun recuperaSezioneFaq(sezione: String): String {
    return when (sezione) {
        "Introduction", "Introduzione" -> "Introduzione"
        "Features", "Funzionalità" -> "Funzionalità"
        "Sicurity", "Sicurezza" -> "Sicurezza"
        "Advanced", "Avanzate" -> "Avanzate"
        "Other", "Altro" -> "Altro"
        "Privacy" -> "Privacy"
        else -> "0"
    }
}

/**
 * Composable che rappresenta la schermata delle FAQ.
 *
 * @param navController Il controller di navigazione utilizzato per navigare tra le schermate.
 * @param sezioneFaq La sezione specifica delle FAQ da visualizzare.
 * @param viewModel Il [FaqViewModel] utilizzato per gestire lo stato della schermata.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Faq(
    navController: NavHostController,
    sezioneFaq: String,
    viewModel: FaqViewModel
) {
    val listaFaq by viewModel.listaFaq.observeAsState(emptyList())
    val loading by viewModel.loading.observeAsState(true)
    val error by viewModel.error.observeAsState(null)
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    val background: Painter = painterResource(id = R.drawable.background_grey)

    LaunchedEffect(Unit) {
        viewModel.caricaFaq()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = sezioneFaq,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (isDarkTheme) Color.White else Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Schermate.Supporto.route) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = if (isDarkTheme) Color.White else Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = if (isDarkTheme) Color.DarkGray else Color.White
                )
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(if (isDarkTheme) Color.DarkGray else Color.White)
            ) {
                if (!isDarkTheme) {
                    Image(
                        painter = background,
                        contentDescription = "Background Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .padding(bottom = 32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when {
                        loading -> {
                            Text(
                                stringResource(id = R.string.caricamento),
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }
                        error != null -> {
                            Text(
                                "Errore: $error",
                                fontSize = 16.sp,
                                color = Color.Red
                            )
                        }
                        else -> {
                            LazyColumn {
                                items(listaFaq) { term ->
                                    if (term.sezione == recuperaSezioneFaq(sezione = sezioneFaq)) {
                                        SingolaFaq(term)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

/**
 * Funzione di anteprima per la schermata FAQ.
 */
@Preview
@Composable
fun PreviewFaq() {
    Faq(navController = rememberNavController(), sezioneFaq = "Introduzione", FaqViewModel(
        RepositoryFaq()
    )
    )
}