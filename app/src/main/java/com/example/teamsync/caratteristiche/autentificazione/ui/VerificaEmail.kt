package com.example.teamsync.caratteristiche.autentificazione.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.R
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.util.ThemePreferences
import com.example.teamsync.util.theme.White

/**
 * Funzione Composable per visualizzare la schermata di verifica dell'email.
 *
 * @param navController Il NavController utilizzato per navigare tra le schermate.
 */
@Composable
fun VerificaEmail(
    navController: NavController
) {
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Immagine di sfondo
        Image(
            painter = if(isDarkTheme) painterResource(id = R.drawable.background_black) else painterResource(id = R.drawable.sfondo_pagina_di_benvenuto1),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize(),
            contentDescription = "sfondo"
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Immagine di verifica dell'email
            Image(
                painter = painterResource(id = R.drawable.im_mailbox),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentDescription = "Immagine della pagina di verifica email",
                contentScale = ContentScale.Fit
            )

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                // Testo di istruzione per controllare l'email
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    text = stringResource(id = R.string.controllaEmail),
                    color = Color.White,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                )

                // Testo di istruzione per verificare l'email
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    text = stringResource(id = R.string.verifica).trimIndent(),
                    color = Color.White,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                )

                // Pulsante per navigare alla schermata di login
                Button(
                    onClick = {
                        navController.navigate(Schermate.Login.route)
                    },
                    colors = ButtonDefaults.buttonColors(White)
                ) {
                    Text(
                        text = stringResource(id = R.string.hoCapito),
                        color = Color.Black
                    )

                }
            }
        }
    }
}

/**
 * Funzione di anteprima per il composable VerificaEmail.
 */
@Preview(showSystemUi = true)
@Composable
fun PrevireVerificaEmail(){
    VerificaEmail(navController = rememberNavController())
}

