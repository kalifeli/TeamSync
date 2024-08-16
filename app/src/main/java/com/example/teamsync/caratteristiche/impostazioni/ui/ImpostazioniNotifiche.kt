package com.example.teamsync.caratteristiche.impostazioni.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.R
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.util.theme.Red70
import com.example.teamsync.util.theme.White
import com.example.teamsync.util.ThemePreferences

/**
 * Schermata per la gestione delle impostazioni delle notifiche.
 *
 * @param navController Il controller di navigazione per gestire la navigazione tra le schermate.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificheImp(navController: NavHostController) {
    val context = LocalContext.current
    val preferences = context.getSharedPreferences("preferenze_notifiche", Context.MODE_PRIVATE)
    val editor = preferences.edit()

    // Variabili di stato per le impostazioni delle notifiche
    var isEntraProgettoSelected by remember { mutableStateOf(preferences.getBoolean("utente_entra_progetto", true)) }
    var isCompletaTaskSelected by remember { mutableStateOf(preferences.getBoolean("utente_completa_task", true)) }
    var isModificaTaskSelected by remember { mutableStateOf(preferences.getBoolean("utente_modifica_mia_task", true)) }

    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.Notifiche),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (isDarkTheme) White else Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Schermate.Impostazioni.route) }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = if (isDarkTheme) White else Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = if (isDarkTheme) Color.DarkGray else White
                )
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(if (isDarkTheme) Color.DarkGray else White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {

                    // Titolo delle impostazioni delle notifiche
                    Text(
                        text = stringResource(id = R.string.avvisamiquando),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = if (isDarkTheme) White else Color.Black,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )

                    // Lista delle opzioni di notifica
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(vertical = 16.dp)
                    ) {
                        item {
                            SingolaScelta(
                                label = stringResource(id = R.string.unutenteentranelprogetto),
                                isSelected = isEntraProgettoSelected,
                                onSelectionChange = { isSelected ->
                                    isEntraProgettoSelected = isSelected
                                    editor.putBoolean("utente_entra_progetto", isSelected).apply()
                                }
                            )
                        }
                        item {
                            SingolaScelta(
                                label = stringResource(id = R.string.Unutentecompletaunatask),
                                isSelected = isCompletaTaskSelected,
                                onSelectionChange = { isSelected ->
                                    isCompletaTaskSelected = isSelected
                                    editor.putBoolean("utente_completa_task", isSelected).apply()
                                }
                            )
                        }
                        item {
                            SingolaScelta(
                                label = stringResource(id = R.string.UnUtenteModificaUnaTask),
                                isSelected = isModificaTaskSelected,
                                onSelectionChange = { isSelected ->
                                    isModificaTaskSelected = isSelected
                                    editor.putBoolean("utente_modifica_mia_task", isSelected).apply()
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}


/**
 * Composable che rappresenta un'opzione singola nelle impostazioni delle notifiche.
 *
 * @param label Il testo descrittivo dell'opzione.
 * @param isSelected Indica se l'opzione è selezionata.
 * @param onSelectionChange Callback che gestisce il cambiamento di selezione dell'opzione.
 */
@Composable
fun SingolaScelta(
    label: String,
    isSelected: Boolean,
    onSelectionChange: (Boolean) -> Unit
) {
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(if (isDarkTheme) Color(0xFF303030) else Color(0xFFE5E5E5))
            .clickable { onSelectionChange(!isSelected) }
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Testo dell'opzione
            Text(
                text = label,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDarkTheme) White else Color.Black
            )
            // Icona di selezione
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) Red70 else Color.Transparent)
                    .border(2.dp, if (isDarkTheme) White else Color.Black, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "Selected Icon",
                        tint = White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

/**
 * Funzione di anteprima per la schermata delle impostazioni delle notifiche.
 */
@Composable
@Preview
fun PreviewSettingsScreen() {
    NotificheImp(navController = rememberNavController())
}