package com.example.teamsync.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.login.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.ui.theme.TeamSyncTheme
import com.example.teamsync.util.ThemePreferences


@Composable
fun UserProfileScreen(viewModel: ViewModelUtente, navController: NavHostController) {
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    // Se il tema è scuro, applichiamo il tema scuro tramite TeamSyncTheme
    if (isDarkTheme) {
        TeamSyncTheme(darkTheme = true) {
            UserProfileScreen_dark(viewModel, navController)
        }
    } else {
        // Altrimenti, applichiamo il tema predefinito
        UserProfileScreen_white(viewModel, navController)
    }
}
@Composable
fun UserProfileScreen_white(viewModel: ViewModelUtente, navController: NavHostController) {
    val userProfile = viewModel.userProfile

    var nome by remember { mutableStateOf(userProfile?.nome ?: "") }
    var cognome by remember { mutableStateOf(userProfile?.cognome ?: "") }
    var dataDiNascita by remember { mutableStateOf(userProfile?.dataDiNascita ?: "") }
    var matricola by remember { mutableStateOf(userProfile?.matricola ?: "") }
    var email by remember { mutableStateOf(userProfile?.email ?: "") }

    LaunchedEffect(userProfile) {
        userProfile?.let {
            nome = it.nome
            cognome = it.cognome
            dataDiNascita = it.dataDiNascita
            matricola = it.matricola
            email = it.email
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
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
                    text = "Modifica Profilo",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray // Cambia il colore del testo
                )
            }

            // Row vuota per bilanciare il layout
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {}

        }

        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.user_icon),
            contentDescription = "Icona Applicazione",
            modifier = Modifier.size(70.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") }
        )
        OutlinedTextField(
            value = cognome,
            onValueChange = { cognome = it },
            label = { Text("Cognome") }
        )
        OutlinedTextField(
            value = matricola,
            onValueChange = { matricola = it },
            label = { Text("Matricola") }
        )
        OutlinedTextField(
            value = dataDiNascita,
            onValueChange = { dataDiNascita = it },
            label = { Text("Data di Nascita") }
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val updatedProfile = ProfiloUtente(
                id = userProfile?.id ?: "",
                nome = nome,
                cognome = cognome,
                dataDiNascita = dataDiNascita,
                matricola = matricola,
                email = email
            )
            viewModel.updateUserProfile(updatedProfile)
            navController.navigate(Schermate.Impostazioni.route)
        },border = BorderStroke(1.dp, Color.DarkGray),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE5E5E5), // Cambia il colore di sfondo del pulsante
                contentColor = Color.DarkGray // Cambia il colore del testo all'interno del pulsante
            )
            ) {
            Text("Salva")
        }
    }
}
@Composable
fun UserProfileScreen_dark(viewModel: ViewModelUtente, navController: NavHostController) {
    val userProfile = viewModel.userProfile
    val darkTextColor = Color.White
    var nome by remember { mutableStateOf(userProfile?.nome ?: "") }
    var cognome by remember { mutableStateOf(userProfile?.cognome ?: "") }
    var dataDiNascita by remember { mutableStateOf(userProfile?.dataDiNascita ?: "") }
    var matricola by remember { mutableStateOf(userProfile?.matricola ?: "") }
    var email by remember { mutableStateOf(userProfile?.email ?: "") }

    LaunchedEffect(userProfile) {
        userProfile?.let {
            nome = it.nome
            cognome = it.cognome
            dataDiNascita = it.dataDiNascita
            matricola = it.matricola
            email = it.email
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.DarkGray),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        )



        {


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
                            text = "Modifica Profilo",
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
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .border(2.dp, Color.White, RoundedCornerShape(16.dp))
                    .background(Color(0xFF333333), RoundedCornerShape(16.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally, // Centro gli elementi orizzontalmente
                verticalArrangement = Arrangement.Top
            ) {
            Image(
                painter = painterResource(id = R.drawable.user_icon),
                contentDescription = "Icona Applicazione",
                modifier = Modifier.size(70.dp)
            )
            Spacer(modifier = Modifier.height(25.dp))




                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },

                    label = { Text("Nome", color = darkTextColor) }
                )
                OutlinedTextField(
                    value = cognome,
                    onValueChange = { cognome = it },
                    label = { Text("Cognome", color = darkTextColor) }
                )
                OutlinedTextField(
                    value = matricola,
                    onValueChange = { matricola = it },
                    label = { Text("Matricola", color = darkTextColor) }
                )
                OutlinedTextField(
                    value = dataDiNascita,
                    onValueChange = { dataDiNascita = it },
                    label = { Text("Data di Nascita", color = darkTextColor) }
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email", color = darkTextColor) }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val updatedProfile = ProfiloUtente(
                    id = userProfile?.id ?: "",
                    nome = nome,
                    cognome = cognome,
                    dataDiNascita = dataDiNascita,
                    matricola = matricola,
                    email = email
                )

                viewModel.updateUserProfile(updatedProfile)
                navController.navigate(Schermate.Impostazioni.route)
            },
                border = BorderStroke(1.dp, darkTextColor),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF333333), // Cambia il colore di sfondo del pulsante
                    contentColor = darkTextColor // Cambia il colore del testo all'interno del pulsante
                )
            ) {
                Text("Salva",color = darkTextColor)
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfileScreenPreview() {
    val viewModel = ViewModelUtente()
    UserProfileScreen(viewModel = viewModel, navController = rememberNavController())
}
