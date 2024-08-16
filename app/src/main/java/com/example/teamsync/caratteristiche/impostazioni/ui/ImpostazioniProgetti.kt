package com.example.teamsync.caratteristiche.impostazioni.ui
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.util.ThemePreferences

/**
 * Schermata per la gestione delle impostazioni dei progetti.
 *
 * @param navController Il controller di navigazione per gestire la navigazione tra le schermate.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImpoProgetti(navController: NavHostController) {
    // Recupera il contesto corrente e le preferenze per i progetti
    val context = LocalContext.current
    val preferences = context.getSharedPreferences("preferenze_progetti", Context.MODE_PRIVATE)
    val editor = preferences.edit()

    // Stato per la visualizzazione dei progetti completati
    var visualizzaCompletati by remember {
        mutableStateOf(preferences.getBoolean("preferenza_progetti_completati", false))
    }

    // Stato per l'ordine dei progetti
    var ordineProgetti by remember {
        mutableStateOf(preferences.getString("ordine_progetti", "cronologico"))
    }

    // Verifica se il tema attuale è scuro
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    // Scaffold per la struttura della schermata con una TopAppBar centrata
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.progetti),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (isDarkTheme) Color.White else Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Schermate.Impostazioni.route) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = if (isDarkTheme) Color.White else Color.Black
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Sezione per l'ordine di visualizzazione dei progetti
                    SettingsSection(
                        title = stringResource(id = R.string.ordinevisualizzazioneprogetti),
                        content = {
                            SettingItem(
                                label = stringResource(id = R.string.priorità),
                                isSelected = ordineProgetti == "priorità",
                                onSelected = {
                                    ordineProgetti = "priorità"
                                    editor.putString("ordine_progetti", "priorità").apply()
                                },
                                isDarkTheme = isDarkTheme
                            )
                            SettingItem(
                                label = stringResource(id = R.string.datadiscadenza),
                                isSelected = ordineProgetti == "scadenza",
                                onSelected = {
                                    ordineProgetti = "scadenza"
                                    editor.putString("ordine_progetti", "scadenza").apply()
                                },
                                isDarkTheme = isDarkTheme
                            )
                            SettingItem(
                                label = stringResource(id = R.string.ordinedicreazione),
                                isSelected = ordineProgetti == "cronologico",
                                onSelected = {
                                    ordineProgetti = "cronologico"
                                    editor.putString("ordine_progetti", "cronologico").apply()
                                },
                                isDarkTheme = isDarkTheme
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    // Sezione per la visualizzazione dei progetti completati
                    SettingsSection(
                        title = stringResource(id = R.string.visualizzaprogetticompletati),
                        content = {
                            SettingItem(
                                label = stringResource(id = R.string.mostralidalladashboard),
                                isSelected = visualizzaCompletati,
                                onSelected = {
                                    visualizzaCompletati = !visualizzaCompletati
                                    editor.putBoolean("preferenza_progetti_completati", visualizzaCompletati).apply()
                                },
                                isDarkTheme = isDarkTheme
                            )
                        }
                    )
                }
            }
        }
    )
}


/**
 * Composable per una sezione di impostazioni con un titolo e una lista di opzioni.
 *
 * @param title Il titolo della sezione.
 * @param content Il contenuto della sezione, che verrà fornito come un Composable.
 */
@Composable
fun SettingsSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = if (ThemePreferences.getTheme(LocalContext.current)) Color.White else Color.Black
            )
        }
        Spacer(modifier = Modifier.height(7.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(if (ThemePreferences.getTheme(LocalContext.current)) Color.Black else Color(0xFFE5E5E5))
        ) {
            content()
        }
    }
}

/**
 * Composable che rappresenta un singolo elemento di impostazione con un'etichetta e un RadioButton.
 *
 * @param label Il testo dell'etichetta dell'opzione.
 * @param isSelected Indica se l'opzione è selezionata.
 * @param onSelected Callback che viene chiamato quando l'opzione viene selezionata.
 * @param isDarkTheme Indica se il tema corrente è scuro.
 */
@Composable
fun SettingItem(
    label: String,
    isSelected: Boolean,
    onSelected: () -> Unit,
    isDarkTheme: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .background(if (isDarkTheme) Color.Black else Color.Transparent),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 10.dp),
            color = if (isDarkTheme) Color.White else Color.Black
        )
        RadioButton(
            selected = isSelected,
            onClick = onSelected,
            colors = RadioButtonDefaults.colors(
                selectedColor = if (isDarkTheme) Color.White else Color.Black,
                unselectedColor = if (isDarkTheme) Color.White else Color.Black
            )
        )
    }
}

