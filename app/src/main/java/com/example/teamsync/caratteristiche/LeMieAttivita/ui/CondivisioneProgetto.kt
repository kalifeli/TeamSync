package com.example.teamsync.caratteristiche.LeMieAttivita.ui

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
import androidx.compose.ui.text.style.TextAlign
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
import com.example.teamsync.ui.theme.Grey35
import com.example.teamsync.ui.theme.Red70
import com.example.teamsync.ui.theme.White
import com.example.teamsync.util.ThemePreferences

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
                text = "Condividi il codice del progetto",
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
                            "Codice Progetto",
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
                colors = ButtonDefaults.buttonColors(Red70),
                // il pulsante viene abilitato solo se il codice è stato caricato
                enabled = codiceProgetto.value != null
            ) {
                Text(text = "Condividi")
            }
        },
        dismissButton ={
            TextButton(
                onClick = onDismissRequest
            ){
                Text(
                    text = "Annulla",
                    color = if(isDarkTheme)Color.White else Color.Black
                )
            }
        }
    )
}