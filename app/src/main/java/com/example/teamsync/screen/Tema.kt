package com.example.teamsync.screen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.R
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.ui.theme.TeamSyncTheme
import com.example.teamsync.util.ThemePreferences

@Composable
fun Tema(navController: NavHostController) {
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    // Se il tema è scuro, applichiamo il tema scuro tramite TeamSyncTheme
    if (isDarkTheme) {
        TeamSyncTheme(darkTheme = true) {
            // Contenuto della schermata delle impostazioni
            TemaContent_dark(navController = navController)
        }
    } else {
        // Altrimenti, applichiamo il tema predefinito
        TemaContent(navController = navController)
    }
}
@Composable
fun TemaContent(navController: NavHostController, ) {
    val background: Painter = painterResource(id = R.drawable.bianco)


    val context = LocalContext.current
    var selectedOption by remember {
        mutableStateOf(if (ThemePreferences.getTheme(context)) "Scuro" else "Chiaro")
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        // Immagine di sfondo
        Image(
            painter = background,
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds // Scala l'immagine per riempire lo spazio
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
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
                    Box(
                        modifier = Modifier
                            .size(35.dp)
                            .background(Color.White,RoundedCornerShape(20.dp)) // Imposta il rettangolo di sfondo a nero
                            .clickable { navController.navigate(Schermate.Impostazioni.route) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "close_impostazioni",
                        )
                    }



                    // Centra il testo all'interno della Row
                    Row(
                        modifier = Modifier.weight(8f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Tema",
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

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomEnd = 12.dp, bottomStart = 20.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(1f)  // 70% of the available width
                            .background(Color(0xFFE5E5E5))
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {

                        Text(
                            text = "Chiaro",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 20.dp)
                        )
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {}


                        RadioButton(
                            selected = selectedOption == "Chiaro",
                            onClick = {
                                selectedOption = "Chiaro"
                                ThemePreferences.saveTheme(context, selectedOption == "Scuro")
                                navController.navigate(Schermate.Tema.route)
                            },
                        )
                        Row(
                            modifier = Modifier.weight(0.1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {}
                    }


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFE5E5E5))
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {

                        Text(
                            text = "Scuro",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 20.dp)
                        )
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {}
                        RadioButton(
                            selected = selectedOption == "Scuro",
                            onClick = {
                                selectedOption = "Scuro"
                                ThemePreferences.saveTheme(context, selectedOption == "Scuro")
                                navController.navigate(Schermate.Tema.route)
                            }
                        )
                        Row(
                            modifier = Modifier.weight(0.1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {}
                    }




                }
            }
        }
    }
}
@Composable
fun TemaContent_dark(navController: NavHostController) {
    val background: Painter = painterResource(id = R.drawable.bianco)
    val context = LocalContext.current
    var selectedOption by remember {
        mutableStateOf(if (ThemePreferences.getTheme(context)) "Scuro" else "Chiaro")
    }

    Box(
        modifier = Modifier.fillMaxSize()
        .background(color = Color.DarkGray),
    ) {
        // Immagine di sfondo


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
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
                    Box(
                        modifier = Modifier
                            .size(35.dp)
                            .background(Color.Black,RoundedCornerShape(20.dp)) // Imposta il rettangolo di sfondo a nero
                            .clickable { navController.navigate(Schermate.Impostazioni.route) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "close_impostazioni",
                            tint = Color.White // Assicurati che l'icona sia visibile impostando il colore a bianco
                        )
                    }

                    // Centra il testo all'interno della Row
                    Row(
                        modifier = Modifier.weight(8f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Tema",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White // Cambia il colore del testo per il tema scuro
                        )
                    }

                    // Row vuota per bilanciare il layout
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {}
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomEnd = 12.dp, bottomStart = 20.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(1f)  // 70% of the available width
                            .background(Color(0xFF333333)) // Colore di sfondo per il tema scuro
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Chiaro",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 20.dp),
                            color = Color.White // Cambia il colore del testo per il tema scuro
                        )
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {}
                        RadioButton(
                            selected = selectedOption == "Chiaro",
                            onClick = {
                                selectedOption = "Chiaro"
                                ThemePreferences.saveTheme(context, selectedOption == "Scuro")
                                navController.navigate(Schermate.Tema.route)
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.White, // Cambia il colore selezionato per il tema scuro
                                unselectedColor = Color.White, // Cambia il colore non selezionato per il tema scuro
                            )
                        )
                        Row(
                            modifier = Modifier.weight(0.1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {}
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF333333)) // Colore di sfondo per il tema scuro
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Scuro",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 20.dp),
                            color = Color.White // Cambia il colore del testo per il tema scuro
                        )
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {}
                        RadioButton(
                            selected = selectedOption == "Scuro",
                            onClick = {
                                selectedOption = "Scuro"
                                ThemePreferences.saveTheme(context, selectedOption == "Scuro")
                                navController.navigate(Schermate.Tema.route)

                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.White, // Cambia il colore selezionato per il tema scuro
                                unselectedColor = Color.White, // Cambia il colore non selezionato per il tema scuro
                            )
                        )
                        Row(
                            modifier = Modifier.weight(0.1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {}
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewTema() {
    TemaContent(navController = rememberNavController())
}
