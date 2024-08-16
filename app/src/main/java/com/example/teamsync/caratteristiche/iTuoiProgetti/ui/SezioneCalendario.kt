package com.example.teamsync.caratteristiche.iTuoiProgetti.ui

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.teamsync.R
import com.example.teamsync.util.theme.Grey35
import com.example.teamsync.util.theme.White
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Composable che visualizza una sezione calendario.
 * L'utente può visualizzare il calendario attraverso un DatePickerDialog senza la possibilità di scegliere una data.
 *
 * @param isDarkTheme Booleano che indica se l'app è in modalità tema scuro.
 */
@Composable
fun SezioneCalendario(isDarkTheme: Boolean){
    // Data corrente formattata
    val currentDate = remember { Date() }
    val dateFormatter = remember { SimpleDateFormat("d MMMM", Locale.getDefault()) }
    val formattedDate = dateFormatter.format(currentDate)

    // Contesto corrente e calendario
    val contesto = LocalContext.current
    val calendario = Calendar.getInstance()

    // Dialog per selezionare la data
    val visualizzaCalendario = DatePickerDialog(
        contesto,
        if(isDarkTheme) R.style.CustomDatePickerDialogDark else R.style.CustomDatePickerDialog,
        { _, year, month, dayOfMonth ->
            calendario.set(year, month, dayOfMonth)
        },
        calendario.get(Calendar.YEAR),
        calendario.get(Calendar.MONTH),
        calendario.get(Calendar.DAY_OF_MONTH)
    )

    // Card elevata che visualizza il calendario e apre il DatePickerDialog al click
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),
        onClick = {
            visualizzaCalendario.show()
        },
        modifier = Modifier
            .border(1.dp, if (isDarkTheme) White else White, shape = RoundedCornerShape(16.dp))
            .size(width = 200.dp, height = 130.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = if(isDarkTheme) Color.Black else White
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        // Contenuto della card
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Titolo della card
            Text(
                text = stringResource(id = R.string.Calendario),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = if(isDarkTheme) White else Color.Black
            )

            Spacer(modifier = Modifier.size(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .background(
                        if (isDarkTheme) White else Grey35,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                // Icona del calendario
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendario_evento),
                    contentDescription = "Icona Calendario",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.size(4.dp))
                // Data corrente formattata
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}

/**
 * Anteprima del composable SezioneCalendario con tema scuro.
 */
@Preview(showSystemUi = false)
@Composable
fun PreviewSezioneCalendario(){
    SezioneCalendario(isDarkTheme = true)
}
