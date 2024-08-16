package com.example.teamsync.caratteristiche.leMieAttivita.ui

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
import com.example.teamsync.util.theme.Grey35
import com.example.teamsync.util.theme.Red70
import com.example.teamsync.util.theme.White
import com.example.teamsync.util.ThemePreferences
import com.example.teamsync.R


/**
 * Composable per il dialog di condivisione del progetto.
 *
 * Questo Composable visualizza un dialog che consente all'utente di condividere il codice di un progetto.
 * Il codice viene recuperato dal ViewModel e visualizzato in un campo di testo non modificabile.
 *
 * @param onDismissRequest Funzione chiamata quando il dialog viene chiuso.
 * @param viewModelProgetto ViewModel utilizzato per gestire i dati del progetto e recuperare il codice del progetto.
 * @param contesto Contesto dell'applicazione, utilizzato per condividere il codice del progetto tramite Intent.
 * @param progettoId L'ID del progetto per il quale si desidera condividere il codice.
 */
@Composable
fun CondividiProgettoDialog(
    onDismissRequest: () -> Unit,
    viewModelProgetto: ViewModelProgetto,
    contesto: Context,
    progettoId: String
){
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    LaunchedEffect(progettoId) {
        viewModelProgetto.recuperaCodiceProgetto(progettoId)
    }

    val codiceProgetto = viewModelProgetto.codiceProgetto.observeAsState()

    AlertDialog(
        title = {
            Text(
                text = stringResource(id = R.string.condividiCodice),
                textAlign = TextAlign.Center,
                color = if(isDarkTheme)Color.White else Color.Black
            )
        },
        text = {
            codiceProgetto.value?.let {
                OutlinedTextField(
                    value = it,
                    onValueChange = {},
                    readOnly = true,
                    label = {
                        Text(
                            stringResource(id = R.string.codiceProgetto),
                            color = if(isDarkTheme)Color.White else Color.Black
                        )
                            },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Red70,
                        unfocusedBorderColor = if(isDarkTheme) White else Color.Black,
                        focusedContainerColor = if(isDarkTheme) Color.Black else White ,
                        unfocusedLabelColor = if(isDarkTheme) White else Color.Black,
                        focusedLabelColor = if(isDarkTheme) White else Color.Black,
                        focusedTextColor = if(isDarkTheme) Color.White else Color.Black,
                        unfocusedTextColor = if(isDarkTheme) Color.White else Color.Black,
                        unfocusedTrailingIconColor = if(isDarkTheme) Color.White else Color.Black,
                        unfocusedLeadingIconColor = if(isDarkTheme) Color.White else Color.Black,
                        focusedTrailingIconColor = if(isDarkTheme) Color.White else Color.Black,

                        )
                )
            }
        },
        containerColor = if (isDarkTheme) Color.Black else Grey35,
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = {
                    codiceProgetto.value?.let {
                        viewModelProgetto.condividiCodiceProgetto(contesto,it)
                    }
                },
                colors = ButtonDefaults.buttonColors( containerColor = Red70,
                contentColor = Color.White),
                enabled = codiceProgetto.value != null
            ) {
                Text(text = stringResource(id = R.string.Condividi), style = TextStyle(color =Color.White ))
            }

        },
        dismissButton ={
            TextButton(
                onClick = onDismissRequest
            ){
                Text(
                    text = stringResource(id = R.string.annullaEdit),
                    color = if(isDarkTheme)Color.White else Color.Black
                )
            }
        }
    )
}