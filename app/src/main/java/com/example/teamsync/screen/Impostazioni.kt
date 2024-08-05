package com.example.teamsync.screen

import android.widget.Toast
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.login.data.repository.RepositoryUtente
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.ui.theme.Grey35
import com.example.teamsync.ui.theme.Red70
import com.example.teamsync.ui.theme.TeamSyncTheme
import com.example.teamsync.ui.theme.White
import com.example.teamsync.util.ThemePreferences

data class SettingItem(val icon: Int, val label: String, val rotta: Schermate)

val settingsList1 = listOf(
    SettingItem(R.drawable.ic_modificaaccount, R.string.modificaProfilo.toString(),Schermate.ModificaProfilo ),
    SettingItem(R.drawable.ic_notifiche, R.string.Notifiche.toString(),Schermate.ImpNotifche),
    SettingItem(R.drawable.ic_tema, R.string.tema.toString(),Schermate.Tema),
    SettingItem(R.drawable.ic_attivita, R.string.task.toString(), Schermate.Imptask),
    SettingItem(R.drawable.ic_progetto, R.string.progetti.toString(),Schermate.ImpoProgetti),

)

val settingsList2 = listOf(
    SettingItem(R.drawable.ic_help, R.string.help.toString(),Schermate.Supporto),
    SettingItem(R.drawable.ic_info, R.string.terms.toString(),Schermate.Terms)
)

val settingsList3 = listOf(
    SettingItem(R.drawable.ic_delete, R.string.eliminaAccount.toString(),Schermate.Login)
)


@Composable
fun Impostazioni(
    navController: NavHostController,
    viewModelUtente: ViewModelUtente
) {
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    // Se il tema è scuro, applichiamo il tema scuro tramite TeamSyncTheme
    if (isDarkTheme) {
        TeamSyncTheme(darkTheme = true) {
            // Contenuto della schermata delle impostazioni
            ImpostazioniContent_dark(navController = navController)
        }
    } else {
        // Altrimenti, applichiamo il tema predefinito
        ImpostazioniContent(navController = navController, viewModelUtente, isDarkTheme)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImpostazioniContent(
    navController: NavHostController,
    viewModelUtente: ViewModelUtente,
    isDarkTheme: Boolean
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.impostazioni),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {navController.navigate(Schermate.ItuoiProgetti.route)}
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "torna indietro"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = if(isDarkTheme) Color.DarkGray else White,
                    titleContentColor = if(isDarkTheme) White else Color.Black,
                    actionIconContentColor = if(isDarkTheme) White else Color.Black,
                    navigationIconContentColor = if(isDarkTheme) White else Color.Black
                )
            )
        }
        

    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(White),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .padding(top = 20.dp)
                        .padding(bottom = 10.dp),
                ) {
                    Text(
                        text = "Account",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
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
                        SettingItemRow(setting = setting, navController = navController, viewModelUtente, isDarkTheme)
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .padding(top = 50.dp)
                        .padding(bottom = 10.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.supporto),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
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
                        SettingItemRow(setting = setting, navController = navController, viewModelUtente, isDarkTheme)
                    }
                }
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .padding(top = 50.dp)
                        .padding(bottom = 10.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.azioni),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
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
                        SettingItemRow(setting = setting, navController = navController, viewModelUtente, isDarkTheme)
                    }
                }
            }
        }
    }
}

@Composable
fun SettingItemRow(setting: SettingItem,navController: NavHostController, viewModelUtente: ViewModelUtente, isDarkTheme: Boolean) {

    val userProfile by viewModelUtente.userProfilo.observeAsState()
    val erroreEliminazioneUtente by viewModelUtente.erroreEliminazioneUtente.observeAsState()
    var showDialog by remember {
        mutableStateOf(false)
    }
    val contesto = LocalContext.current


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (setting.label == R.string.eliminaAccount.toString()) Red70 else Color(0xFFE5E5E5)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            modifier = Modifier.weight(0.45f),
            verticalAlignment = Alignment.CenterVertically
        ) {}
        Icon(
            painter = painterResource(id = setting.icon),
            contentDescription = "Icona",
            modifier = Modifier
                .size(40.dp)
                .padding(
                    top = 10.dp,
                    bottom = 8.dp
                ),
            tint = Color.Black
        )
        Row(
            modifier = Modifier.weight(1.5f),
            verticalAlignment = Alignment.CenterVertically
        ) {}

        if (setting.label == R.string.eliminaAccount.toString()) {
                Row(
                    modifier = Modifier
                        .clickable {
                            showDialog = true
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
                        color = Color.White

                    )
                }

            if (showDialog){
                userProfile?.let {
                    DeleteAccountDialog(
                        onSuccess = {
                            if(erroreEliminazioneUtente.isNullOrEmpty()){
                                navController.navigate(setting.rotta.route)
                                viewModelUtente.eliminaAccount(userProfile!!.id)
                            }else{
                                Toast.makeText(contesto, erroreEliminazioneUtente, Toast.LENGTH_LONG).show()
                            }
                                    },
                        onDismissRequest = {
                            showDialog = false
                                           },
                        isDarkTheme = isDarkTheme
                    )
                }
            }
        } else {
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
                    ),
                    color = Color.Black



                )
            }

        }

    }
}
@Composable
fun ImpostazioniContent_dark(navController: NavHostController) {
    painterResource(id = R.drawable.bianco)
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
                            .clickable { navController.navigate(Schermate.ItuoiProgetti.route) },
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
    val viewmodel = ViewModelUtente(RepositoryUtente())
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (setting.label == R.string.eliminaAccount.toString()) Color(0xFFC1092A) else Color.Black) ,
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
        if(setting.label == R.string.eliminaAccount.toString())
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

@Composable
fun DeleteAccountDialog(
    onDismissRequest : () -> Unit,
    onSuccess : () -> Unit,
    isDarkTheme: Boolean
){
    AlertDialog(
        title = {
            Text(
                text = "Elimina Account",
                textAlign = TextAlign.Center,
                color = if(isDarkTheme)Color.White else Color.Black
            )
        },
        text = {
            Text(
                text = "Sei sicuro di eliminare il tuo account? Cliccando conferma non potrai tornare più indietro e perderai tutti i tuoi dati.",
                color = if(isDarkTheme)Color.White else Color.Black,
            )
        },
        containerColor = if (isDarkTheme) Color.Black else Grey35,
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = {
                    onSuccess()
                },
                colors = ButtonDefaults.buttonColors(Red70),
            ) {
                Text(text = "Conferma")
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


@Preview
@Composable
fun PreviewImpostazioni() {
    ImpostazioniContent_dark(navController = rememberNavController())
}
