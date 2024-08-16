package com.example.teamsync.caratteristiche.impostazioni.ui


import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.teamsync.R
import com.example.teamsync.R.string.datadiscadenza
import com.example.teamsync.R.string.inbaseallapriorità
import com.example.teamsync.R.string.ordinedicreazione
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.util.theme.White
import com.example.teamsync.util.ThemePreferences

/**
 * Schermata per la gestione delle impostazioni dei task.
 *
 * @param navController Il controller di navigazione per gestire la navigazione tra le schermate.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImpoTask(navController: NavHostController) {
    val context = LocalContext.current
    val preferences = context.getSharedPreferences("preferenze_task", Context.MODE_PRIVATE)
    val editor = preferences.edit()
    var ordineTask by remember { mutableStateOf(preferences.getString("ordine_task", "creazione")) }
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Task",
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
                    Text(
                        text = stringResource(id = R.string.ordineTask),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = if (isDarkTheme) White else Color.Black,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(vertical = 16.dp)
                    ) {
                        item {
                            TaskOption(
                                label = stringResource(id = inbaseallapriorità),
                                isSelected = ordineTask == "priorità",
                                onSelectionChange = {
                                    ordineTask = "priorità"
                                    editor.putString("ordine_task", ordineTask).apply()
                                }
                            )
                        }
                        item {
                            TaskOption(
                                label = stringResource(id = ordinedicreazione),
                                isSelected = ordineTask == "creazione",
                                onSelectionChange = {
                                    ordineTask = "creazione"
                                    editor.putString("ordine_task", ordineTask).apply()
                                }
                            )
                        }
                        item {
                            TaskOption(
                                label = stringResource(id = datadiscadenza),
                                isSelected = ordineTask == "data_di_scadenza",
                                onSelectionChange = {
                                    ordineTask = "data_di_scadenza"
                                    editor.putString("ordine_task", ordineTask).apply()
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
 * Composable che rappresenta una singola opzione di ordinamento dei task.
 *
 * @param label Il testo dell'etichetta dell'opzione.
 * @param isSelected Indica se l'opzione è selezionata.
 * @param onSelectionChange Callback chiamato quando l'opzione viene selezionata.
 */
@Composable
fun TaskOption(
    label: String,
    isSelected: Boolean,
    onSelectionChange: () -> Unit
) {
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(if (isDarkTheme) Color(0xFF303030) else Color(0xFFE5E5E5))
            .clickable { onSelectionChange() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = if (isDarkTheme) White else Color.Black
        )
        RadioButton(
            selected = isSelected,
            onClick = null,
            colors = RadioButtonDefaults.colors(
                selectedColor = if (isDarkTheme) White else Color.Black,
                unselectedColor = if (isDarkTheme) White else Color.Black
            )
        )
    }
}
