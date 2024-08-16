package com.example.teamsync.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.teamsync.R

/**
 * Visualizza una schermata che informa l'utente della mancanza di connessione Internet.
 *
 * Questa funzione composable viene utilizzata per visualizzare una schermata di avviso quando
 * non è disponibile una connessione Internet. Mostra un'immagine, un messaggio di testo e un
 * pulsante per riprovare a stabilire la connessione.
 *
 * @param isDarkTheme Booleano che indica se il tema corrente è scuro. Utilizzato per scegliere i colori appropriati.
 * @param onRetry Callback che viene eseguito quando l'utente preme il pulsante "Riprova".
 *                Questo permette di tentare nuovamente la connessione.
 */
@Composable
fun NoInternetScreen(
    isDarkTheme: Boolean,
    onRetry: () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDarkTheme) Color.Black else Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.im_no_internet),
                contentDescription = "No Internet Connection",
                modifier = Modifier.size(250.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Oops!",
                color = if (isDarkTheme) Color.White else Color.Black,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.NessunaConnessioneH1),
                color = if (isDarkTheme) Color.White else Color.Black,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.NessunaConnessioneH2),
                color = if (isDarkTheme) Color.White else Color.Gray,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isDarkTheme) Color.White else Color.Black,
                    contentColor = if (isDarkTheme) Color.Black else Color.White
                ),
                modifier = Modifier.padding(horizontal = 32.dp)
            ) {
                Text(stringResource(id = R.string.Riprova))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoInternetScreenPreview() {
    NoInternetScreen(isDarkTheme = false, onRetry = {})
}

@Preview(showBackground = true)
@Composable
fun NoInternetScreenDarkPreview() {
    NoInternetScreen(isDarkTheme = true, onRetry = {})
}
