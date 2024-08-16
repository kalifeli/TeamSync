package com.example.teamsync.caratteristiche.impostazioni.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.R
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.util.ThemePreferences

/**
 * Modello di dati che rappresenta un singolo elemento del menu di supporto.
 *
 * @param icon L'icona che rappresenta l'elemento del menu.
 * @param text Il testo associato all'elemento del menu.
 */
data class MenuItemData(
    val icon: ImageVector,
    val text: String
)

/**
 * Lista di elementi del menu di supporto.
 */
val items = listOf(
    MenuItemData(Icons.Default.Info, R.string.introduzione.toString()),
    MenuItemData(Icons.Default.Create, R.string.funzionalità.toString()),
    MenuItemData(Icons.Default.Lock, R.string.sicurezza.toString()),
    MenuItemData(Icons.Default.AccountCircle, R.string.privacy.toString()),
    MenuItemData(Icons.Default.Build, R.string.avanzate.toString()),
    MenuItemData(Icons.AutoMirrored.Filled.List, R.string.altro.toString())
)

/**
 * Schermata di supporto che visualizza le opzioni di supporto per l'utente.
 *
 * @param navController Il controller di navigazione per gestire la navigazione tra le schermate.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Supporto(navController: NavHostController) {
    val background: Painter = painterResource(id = R.drawable.background_grey)
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    val textColor = if (isDarkTheme) Color.White else Color.Black

    // Scaffold per la struttura della schermata con una TopAppBar centrata
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.supporto),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Schermate.Impostazioni.route) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back",
                            tint = textColor
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
                // Immagine di sfondo per il tema chiaro
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
                    // Colonna per la visualizzazione degli elementi del menu
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.Top
                    ) {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            items(items.size / 2) { index ->
                                val menuItem = items[index]
                                MenuItem(icon = menuItem.icon, text = stringResource(id = menuItem.text.toInt()), navController = navController)
                            }
                        }

                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            items(items.size / 2) { index ->
                                val menuItem = items[index + items.size / 2]
                                MenuItem(icon = menuItem.icon, text = stringResource(id = menuItem.text.toInt()), navController = navController)
                            }
                        }
                    }
                }
            }
        }
    )
}

/**
 * Composable per visualizzare un singolo elemento del menu di supporto.
 *
 * @param icon L'icona da visualizzare accanto all'elemento del menu.
 * @param text Il testo da visualizzare per l'elemento del menu.
 * @param navController Il controller di navigazione per gestire la navigazione tra le schermate.
 */
@Composable
fun MenuItem(icon: ImageVector, text: String, navController: NavHostController) {
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    val textColor = if (isDarkTheme) Color.White else Color.Black
    Surface(
        color = if (isDarkTheme) Color.Black else Color.White, // Colore di sfondo
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(8.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { navController.navigate("faq/$text") }
                    )
                }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Icon",
                    tint = if (isDarkTheme) Color.White else Color.DarkGray
                )
                Text(
                    textAlign = TextAlign.Center,
                    color = textColor,
                    text = text,
                    modifier = Modifier.padding(start = 8.dp) // Spazio tra l'icona e il testo
                )
            }
        }
    }
}

/**
 * Anteprima della schermata di supporto.
 */
@Preview
@Composable
fun PreviewJetpackComposeScreen() {
    Supporto(navController = rememberNavController())
}
