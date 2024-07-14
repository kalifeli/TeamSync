package com.example.teamsync.screen
import android.content.Context
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.teamsync.R.string.datadiscadenza
import com.example.teamsync.R.string.mostralidalladashboard
import com.example.teamsync.R.string.ordinedicreazione
import com.example.teamsync.R.string.ordinevisualizzazioneprogetti
import com.example.teamsync.R.string.priorità
import com.example.teamsync.R.string.progetti
import com.example.teamsync.R.string.visualizzaprogetticompletati
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.util.ThemePreferences

@Composable
fun ImpoProgetti(navController: NavHostController ) {
    val context = LocalContext.current
    val preferences = context.getSharedPreferences("preferenze_progetti", Context.MODE_PRIVATE)
    var visualizza_completati by remember {
        mutableStateOf(
            preferences.getBoolean(
                "preferenza_progetti_completati",
                false
            )
        )
    }
    val editor = preferences.edit()
    var ordine_progetti by remember {
        mutableStateOf(
            preferences.getString(
                "ordine_progetti",
                "cronologico"
            )
        )
    }
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDarkTheme) Color.DarkGray else Color.Transparent)
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
                            ) // Imposta il rettangolo di sfondo a nero
                            .clickable { navController.navigate(Schermate.Impostazioni.route) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "close_impostazioni",
                        )
                    }


                    // Centra il testo all'interno della Row
                    Row(
                        modifier = Modifier.weight(8f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = progetti),
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isDarkTheme) Color.White else Color.DarkGray
                        )
                    }

                    // Row vuota per bilanciare il layout
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {}
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.08f)
                        .padding(start = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = stringResource(id = ordinevisualizzazioneprogetti),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isDarkTheme) Color.White else Color.Black
                    )


                }

                Spacer(modifier = Modifier.height(7.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp,
                                bottomEnd = 20.dp,
                                bottomStart = 20.dp
                            )
                        )
                        .background(if (isDarkTheme) Color.Black else Color(0xFFE5E5E5))

                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(1f)  // 70% of the available width
                            .padding(5.dp)
                            .background(if (isDarkTheme) Color.Black else Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start

                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)  // 70% of the available width
                                .padding(5.dp)
                                .background(if (isDarkTheme) Color.Black else Color.Transparent),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        )
                        {
                            Text(
                                text = stringResource(id = priorità),
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 10.dp),
                                color = if (isDarkTheme) Color.White else Color.Black
                            )
                        }
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {}



                        RadioButton(
                            selected = ordine_progetti == "priorità",
                            onClick = {
                                ordine_progetti = "priorità"
                                editor.putString("ordine_progetti", "priorità").apply()

                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = if (isDarkTheme) Color.White else Color.Black,
                                unselectedColor = if (isDarkTheme) Color.White else Color.Black,
                            )
                        )
                        Row(
                            modifier = Modifier.weight(0.1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {}
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
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
                        .background(if (isDarkTheme) Color.Black else Color(0xFFE5E5E5))
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .background(if (isDarkTheme) Color.Black else Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(5.dp)
                                .background(if (isDarkTheme) Color.Black else Color.Transparent),
                        ) {

                            Text(
                                text = stringResource(id = datadiscadenza),
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 10.dp),
                                color = if (isDarkTheme) Color.White else Color.Black
                            )
                        }
                        Row(
                            modifier = Modifier.weight(3f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {}


                        RadioButton(

                            selected = ordine_progetti == "scadenza",
                            onClick = {
                                ordine_progetti = "scadenza"
                                editor.putString("ordine_progetti", "scadenza").apply()

                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = if (isDarkTheme) Color.White else Color.Black,
                                unselectedColor = if (isDarkTheme) Color.White else Color.Black,
                            )
                        )
                        Row(
                            modifier = Modifier.weight(0.1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {}
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))

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
                        .background(if (isDarkTheme) Color.Black else Color(0xFFE5E5E5))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .background(if (isDarkTheme) Color.Black else Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(5.dp)
                                .background(if (isDarkTheme) Color.Black else Color.Transparent),
                        ) {

                            Text(
                                text = stringResource(id = ordinedicreazione),
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 10.dp),
                                color = if (isDarkTheme) Color.White else Color.Black
                            )
                        }
                        Row(
                            modifier = Modifier.weight(3f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {}


                        RadioButton(

                            selected = ordine_progetti == "cronologico",
                            onClick = {
                                ordine_progetti = "cronologico"
                                editor.putString("ordine_progetti", "cronologico").apply()

                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = if (isDarkTheme) Color.White else Color.Black,
                                unselectedColor = if (isDarkTheme) Color.White else Color.Black,
                            )
                        )
                        Row(
                            modifier = Modifier.weight(0.1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {}
                    }
                }
                Spacer(modifier = Modifier.height(25.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.08f)
                        .padding(start = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = stringResource(id = visualizzaprogetticompletati),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isDarkTheme) Color.White else Color.Black
                    )


                }

                Spacer(modifier = Modifier.height(15.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp,
                                bottomEnd = 20.dp,
                                bottomStart = 20.dp
                            )
                        )
                        .background(if (isDarkTheme) Color.Black else Color(0xFFE5E5E5))

                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(1f)  // 70% of the available width
                            .padding(5.dp)
                            .background(if (isDarkTheme) Color.Black else Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start

                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)  // 70% of the available width
                                .padding(5.dp)
                                .background(if (isDarkTheme) Color.Black else Color.Transparent),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        )
                        {
                            Text(
                                text = stringResource(id = mostralidalladashboard),
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 10.dp),
                                color = if (isDarkTheme) Color.White else Color.Black
                            )
                        }
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {}



                        RadioButton(
                            selected = visualizza_completati,
                            onClick = {
                                visualizza_completati = !visualizza_completati
                                editor.putBoolean("preferenza_progetti_completati", visualizza_completati)
                                    .apply()

                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = if (isDarkTheme) Color.White else Color.Black,
                                unselectedColor = if (isDarkTheme) Color.White else Color.Black,
                            )
                        )
                        Row(
                            modifier = Modifier.weight(0.1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {}
                    }
                }


            }
        }
    }
}

