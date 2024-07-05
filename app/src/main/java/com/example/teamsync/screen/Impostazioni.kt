package com.example.teamsync.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.ui.theme.TeamSyncTheme
import com.example.teamsync.util.ThemePreferences

data class SettingItem(val icon: Int, val label: String, val rotta: Schermate)

val settingsList1 = listOf(
    SettingItem(R.drawable.person_icon, R.string.modificaProfilo.toString(),Schermate.ModificaProfilo ),
    SettingItem(R.drawable.notifiche, R.string.Notifiche.toString(),Schermate.Progetti),
    SettingItem(R.drawable.lingua, R.string.Lingua.toString(),Schermate.Progetti),
    SettingItem(R.drawable.tema, R.string.tema.toString(),Schermate.Tema),
    SettingItem(R.drawable.task, R.string.task.toString(), Schermate.Progetti),
    SettingItem(R.drawable.to_do_list, R.string.todolist.toString(),Schermate.Progetti),

)

val settingsList2 = listOf(
    SettingItem(R.drawable.help, R.string.help.toString(),Schermate.Supporto),
    SettingItem(R.drawable.terms_and_condiction, R.string.terms.toString(),Schermate.Terms)
)

val settingsList3 = listOf(
    SettingItem(R.drawable.logout, R.string.logout.toString() ,Schermate.Login)
)



@Composable
fun Impostazioni(navController: NavHostController) {
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    // Se il tema è scuro, applichiamo il tema scuro tramite TeamSyncTheme
    if (isDarkTheme) {
        TeamSyncTheme(darkTheme = true) {
            // Contenuto della schermata delle impostazioni
            ImpostazioniContent_dark(navController = navController)
        }
    } else {
        // Altrimenti, applichiamo il tema predefinito
        ImpostazioniContent(navController = navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImpostazioniContent(navController: NavHostController) {
    val background: Painter = painterResource(id = R.drawable.bianco)
    Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            // Immagine di sfondo
            Image(
                painter = background,
                contentDescription = "Background Image",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillBounds // Scala l'immagine per riempire lo spazio

            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
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
                                    Color.Black,
                                    RoundedCornerShape(20.dp)
                                ) // Imposta il rettangolo di sfondo a nero
                                .clickable { navController.navigate(Schermate.Progetti.route) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "close_impostazioni",
                                tint = Color.White // Assicurati che l'icona sia visibile impostando il colore a bianco
                            )
                        }

                        // Centra il testo all'interno della Row
                        Row(
                            modifier = Modifier.weight(8f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text =  stringResource(id = R.string.impostazioni),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Row vuota per bilanciare il layout
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {}

                    }
                }

                Row(
                    modifier = Modifier
                        .clickable { navController.navigate(Schermate.ModificaProfilo.route) }
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .padding(top = 20.dp)
                        .padding(bottom = 10.dp),
                ) {
                    Text(
                        text = "Account",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,

                        )
                }



                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp,
                                bottomEnd = 12.dp,
                                bottomStart = 20.dp
                            ),
                        )
                ) {
                    items(settingsList1) { setting ->
                        SettingItemRow(setting = setting, navController = navController)
                    }
                }

                Row(
                    modifier = Modifier
                        .clickable { navController.navigate(Schermate.ModificaProfilo.route) }
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .padding(top = 50.dp)
                        .padding(bottom = 10.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.supporto),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,

                        )
                }



                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp,
                                bottomEnd = 12.dp,
                                bottomStart = 20.dp
                            ),
                        )
                ) {
                    items(settingsList2) { setting ->
                        SettingItemRow(setting = setting, navController = navController)
                    }
                }
                Row(
                    modifier = Modifier
                        .clickable { navController.navigate(Schermate.ModificaProfilo.route) }
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .padding(top = 50.dp)
                        .padding(bottom = 10.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.azioni),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,

                        )
                }



                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp,
                                bottomEnd = 12.dp,
                                bottomStart = 20.dp
                            ),
                        )
                ) {
                    items(settingsList3) { setting ->
                        SettingItemRow(setting = setting, navController = navController)
                    }
                }


            }

        }

    }





@Composable
fun SettingItemRow(setting: SettingItem,navController: NavHostController) {

    val viewmodel = ViewModelUtente()


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (setting.label == R.string.logout.toString()) Color(0xFFC1092A) else Color(0xFFE5E5E5)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {

        Row(
            modifier = Modifier.weight(0.45f),
            verticalAlignment = Alignment.CenterVertically
        ) {}
        Image(
            painter = painterResource(id = setting.icon),
            contentDescription = "Icona",
            modifier = Modifier
                .size(40.dp) // Imposta le dimensioni iniziali dell'immagine
                .padding(
                    top = 10.dp,
                    bottom = 8.dp
                )

        )
        Row(
            modifier = Modifier.weight(1.5f),
            verticalAlignment = Alignment.CenterVertically
        ) {}

        if (setting.label == R.string.logout.toString()) {



                // Centra il testo all'interno della Row
                Row(
                    modifier = Modifier
                        .clickable {
                            viewmodel.signOut()
                            navController.navigate(setting.rotta.route)
                        }
                        .weight(12f),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = stringResource(id = setting.label.toInt()),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            top = 10.dp,
                            bottom = 8.dp
                        )

                    )
                }



        } else {
            // Centra il testo all'interno della Row
            Row(
                modifier = Modifier
                    .clickable { navController.navigate(setting.rotta.route) }
                    .weight(12f),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = stringResource(id = setting.label.toInt()),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        top = 10.dp,
                        bottom = 8.dp
                    )

                )
            }

        }

    }
}
@Composable
fun ImpostazioniContent_dark(navController: NavHostController) {
    val background: Painter = painterResource(id = R.drawable.bianco)
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    val darkBackgroundColor = Color.Black
    val darkTextColor = Color.White

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)
    ) {
        // Immagine di sfondo


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
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
                            .clickable { navController.navigate(Schermate.Progetti.route) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "close_impostazioni",
                            tint = Color.DarkGray // Assicurati che l'icona sia visibile impostando il colore a bianco
                        )
                    }

                    // Centra il testo all'interno della Row
                    Row(
                        modifier = Modifier.weight(8f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.impostazioni),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = darkTextColor // Cambia il colore del testo
                        )
                    }

                    // Row vuota per bilanciare il layout
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {}

                }
            }

            Row(
                modifier = Modifier
                    .clickable { navController.navigate(Schermate.ModificaProfilo.route) }
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(top = 20.dp)
                    .padding(bottom = 10.dp),
            ) {
                Text(
                    text = "Account",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkTextColor // Cambia il colore del testo
                )
            }


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 20.dp,
                            bottomEnd = 12.dp,
                            bottomStart = 20.dp
                        ),
                    )
            ) {
                items(settingsList1) { setting ->
                    SettingItemRow_dark(setting = setting, navController = navController)
                }
            }

            // Supporto
            Row(
                modifier = Modifier
                    .clickable { navController.navigate(Schermate.ModificaProfilo.route) }
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(top = 50.dp)
                    .padding(bottom = 10.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.supporto),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkTextColor // Cambia il colore del testo per il tema scuro
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 20.dp,
                            bottomEnd = 12.dp,
                            bottomStart = 20.dp
                        ),
                    )
            ) {
                items(settingsList2) { setting ->
                    SettingItemRow_dark(setting = setting, navController = navController)
                }
            }

// Azioni
            Row(
                modifier = Modifier
                    .clickable { navController.navigate(Schermate.ModificaProfilo.route) }
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(top = 50.dp)
                    .padding(bottom = 10.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.azioni),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkTextColor // Cambia il colore del testo per il tema scuro
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 20.dp,
                            bottomEnd = 12.dp,
                            bottomStart = 20.dp
                        ),
                    )
            ) {
                items(settingsList3) { setting ->
                    SettingItemRow_dark(setting = setting, navController = navController)
                }
            }


        }

    }
}

@Composable
fun SettingItemRow_dark(setting: SettingItem, navController: NavHostController) {
    val darkTextColor = Color.White
    val viewmodel = ViewModelUtente ()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (setting.label == R.string.logout.toString()) Color(0xFFC1092A) else Color(0xFF333333)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {

        Row(
            modifier = Modifier.weight(0.45f),
            verticalAlignment = Alignment.CenterVertically
        ) {}
        Image(
            painter = painterResource(id = setting.icon),
            contentDescription = "Icona",
            modifier = Modifier
                .size(40.dp) // Imposta le dimensioni iniziali dell'immagine
                .padding(
                    top = 10.dp,
                    bottom = 8.dp
                ),
            colorFilter = ColorFilter.tint(Color.White)

        )
        Row(
            modifier = Modifier.weight(1.5f),
            verticalAlignment = Alignment.CenterVertically
        ) {}

        // Centra il testo all'interno della Row
        if(setting.label == R.string.logout.toString())
        {
            Row(
                modifier = Modifier
                    .clickable {
                        viewmodel.signOut()
                        navController.navigate(setting.rotta.route)
                    }
                    .weight(12f),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = stringResource(id = setting.label.toInt()),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        top = 10.dp,
                        bottom = 8.dp
                    ),
                    color = darkTextColor // Cambia il colore del testo
                )
            }


        }
        else
        { Row(
            modifier = Modifier
                .clickable { navController.navigate(setting.rotta.route) }
                .weight(12f),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = stringResource(id = setting.label.toInt()),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    top = 10.dp,
                    bottom = 8.dp
                ),
                color = darkTextColor // Cambia il colore del testo
            )
        }

        }


    }
}


@Preview
@Composable
fun PreviewImpostazioni() {
    ImpostazioniContent_dark(navController = rememberNavController())
}
