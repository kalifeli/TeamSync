package com.example.teamsync.caratteristiche.faq.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.R
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.util.ThemePreferences

// Modello di dati per ogni rettangolo
data class MenuItemData(val icon: ImageVector, val text: String)

// Lista di dati per i rettangoli
val items = listOf(
    MenuItemData(Icons.Default.Info, R.string.introduzione.toString()),
    MenuItemData(Icons.Default.Create, R.string.funzionalità.toString()),
    MenuItemData(Icons.Default.Lock, R.string.sicurezza.toString()),
    MenuItemData(Icons.Default.AccountCircle, R.string.privacy.toString()),
    MenuItemData(Icons.Default.Build, R.string.avanzate.toString()),
    MenuItemData(Icons.Default.List, R.string.altro.toString())
)

@Composable
fun Supporto(navController: NavHostController) {
    val background: Painter = painterResource(id = R.drawable.background_grey)
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    val textColor = if (isDarkTheme) Color.White else Color.Black
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        if (!isDarkTheme)
        {
            Image(
                painter = background,
                contentDescription = "Background Image",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillBounds // Scala l'immagine per riempire lo spazio

            )
        }


        Column( modifier =  Modifier.background(if (isDarkTheme) Color.DarkGray else  Color.Transparent),) {

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.08f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier.weight(0.25f))

                    if(isDarkTheme)
                    {
                        Box(
                            modifier = Modifier
                                .size(35.dp)
                                .clickable { navController.navigate(Schermate.Impostazioni.route) }
                                .background(
                                    Color.White,
                                    RoundedCornerShape(20.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "back",
                                tint = Color.DarkGray // Assicurati che l'icona sia visibile impostando il colore a bianco
                            )
                        }
                    }
                    else
                    {Box(
                        modifier = Modifier
                            .size(35.dp)
                            .clickable { navController.navigate(Schermate.Impostazioni.route) }
                            .background(
                                Color.Black,
                                RoundedCornerShape(20.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back",
                            tint = Color.White // Assicurati che l'icona sia visibile impostando il colore a bianco
                        )
                    }

                    }


                    // Centra il testo all'interno della Row
                    Row(
                        modifier = Modifier.weight(8f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            color = textColor,
                            text = stringResource(id = R.string.supporto),
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
            }


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





@Composable
fun MenuItem(icon: ImageVector, text: String, navController: NavHostController) {
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    val textColor = if (isDarkTheme) Color.White else Color.Black
    Surface(
        color = if (isDarkTheme) Color.Black else Color.White, // Colore di sfondo grigino
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
        )
         {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Icon",
                    tint = if (isDarkTheme) Color.White else Color.DarkGray,

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

@Preview
@Composable
fun PreviewJetpackComposeScreen() {
    Supporto(navController = rememberNavController())
}
