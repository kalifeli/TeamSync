package com.example.teamsync.caratteristiche.impostazioni.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.teamsync.util.theme.White
import com.example.teamsync.util.ThemePreferences

/**
 * Composable per la schermata di selezione del tema.
 *
 * @param navController Controller per la navigazione tra le schermate.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tema(navController: NavHostController) {
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    val context = LocalContext.current
    var selectedOption by remember { mutableIntStateOf(if (isDarkTheme) R.string.scuro else R.string.chiaro) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.tema),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (isDarkTheme) White else Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Schermate.Impostazioni.route) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(
                                    RoundedCornerShape(16.dp)
                                )
                        ) {
                            OpzioniTema(
                                etichetta = stringResource(id = R.string.chiaro),
                                isSelected = selectedOption == R.string.chiaro,
                                onSelectionChange = {
                                    selectedOption = R.string.chiaro
                                    ThemePreferences.saveTheme(context, false)
                                    navController.navigate(Schermate.Tema.route)
                                },
                                isDarkTheme = isDarkTheme
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            OpzioniTema(
                                etichetta = stringResource(id = R.string.scuro),
                                isSelected = selectedOption == R.string.scuro,
                                onSelectionChange = {
                                    selectedOption = R.string.scuro
                                    ThemePreferences.saveTheme(context, true)
                                    navController.navigate(Schermate.Tema.route)
                                },
                                isDarkTheme = isDarkTheme
                            )
                        }
                    }
                }
            }
        }
    )
}

/**
 * Composable per l'opzione di selezione del tema.
 *
 * @param etichetta Etichetta dell'opzione di selezione.
 * @param isSelected Indica se l'opzione è selezionata.
 * @param onSelectionChange Funzione callback da eseguire quando l'opzione è selezionata.
 * @param isDarkTheme Indica se il tema corrente è scuro.
 */
@Composable
fun OpzioniTema(
    etichetta: String,
    isSelected: Boolean,
    onSelectionChange: () -> Unit,
    isDarkTheme: Boolean
) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(if (isDarkTheme) Color(0xFF303030) else Color(0xFFE5E5E5))
            .clickable {
                try {
                    onSelectionChange() // Cambio del tema
                } catch (e: Exception) {
                    Toast.makeText(context, "Errore nel salvataggio del tema", Toast.LENGTH_SHORT).show()
                }
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = etichetta,
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

/**
 * Funzione di anteprima per la schermata di selezione del tema.
 */
@Preview
@Composable
fun PreviewTema() {
    Tema(navController = rememberNavController())
}
