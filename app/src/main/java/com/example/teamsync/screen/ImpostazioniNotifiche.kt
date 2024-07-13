package com.example.teamsync.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.R.string.Notifiche
import com.example.teamsync.R.string.Unutentecompletaunatask
import com.example.teamsync.R.string.avvisamiquando
import com.example.teamsync.R.string.unutenteentranelprogetto
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.ui.theme.Red70
import com.example.teamsync.util.ThemePreferences

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
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFE5E5E5))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (isDarkTheme) Color.Black else Color(0xFFE5E5E5))
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 20.dp),
                color = if(isDarkTheme) Color.White else Color.Black
            )
            Box(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp, end = 10.dp)
                    .size(30.dp)
                    .background(if (isSelected) Red70 else Color.Transparent, CircleShape)
                    .clickable (onClick = { onSelectionChange(!isSelected) })
                    .border(2.dp,if (isDarkTheme) Color.White else Color.Black, CircleShape),
                contentAlignment = Alignment.Center


            ) {
                if (isSelected) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "Selected Icon",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun NotificheImp(navController: NavHostController) {
    val context = LocalContext.current
    val preferences = context.getSharedPreferences("preferenze_notifiche", Context.MODE_PRIVATE)
    val editor = preferences.edit()
    var isEntraProgettoSelected by remember { mutableStateOf(preferences.getBoolean("utente_entra_progetto", true)) }
    var isCompletaTaskSelected by remember { mutableStateOf(preferences.getBoolean("utente_completa_task", true)) }
    var isModificaTaskSelected by remember { mutableStateOf(preferences.getBoolean("utente_modifica_mia_task", true)) }
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    Box(
        modifier = Modifier.fillMaxSize()
            .background(if(isDarkTheme) Color.DarkGray else Color.White )
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
                            .background(
                                Color.White,
                                RoundedCornerShape(20.dp)
                            )
                            .clickable { navController.navigate(Schermate.Impostazioni.route) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "close_impostazioni",
                        )
                    }

                    Row(
                        modifier = Modifier.weight(8f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = Notifiche) ,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = if(isDarkTheme) Color.White else Color.Black
                        )
                    }

                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {}
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.08f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(8f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = stringResource(id = avvisamiquando) ,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = if(isDarkTheme) Color.White else Color.Black
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp,
                                bottomEnd = 12.dp,
                                bottomStart = 20.dp
                            )
                        )
                ) {
                    SingolaScelta(
                        label = stringResource(id = unutenteentranelprogetto) ,
                        isSelected = isEntraProgettoSelected,
                        onSelectionChange = { isSelected ->
                            isEntraProgettoSelected = isSelected
                            editor.putBoolean("utente_entra_progetto", isSelected).apply()
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    SingolaScelta(
                        label = stringResource(id = Unutentecompletaunatask),
                        isSelected = isCompletaTaskSelected,
                        onSelectionChange = { isSelected ->
                            isCompletaTaskSelected = isSelected
                            editor.putBoolean("utente_completa_task", isSelected).apply()
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    SingolaScelta(
                        label = stringResource(id = Unutentecompletaunatask),

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

@Composable
@Preview
fun PreviewSettingsScreen() {
    NotificheImp(navController = rememberNavController())
}
