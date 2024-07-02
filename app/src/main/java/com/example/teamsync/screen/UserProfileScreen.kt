package com.example.teamsync.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.login.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.ui.theme.TeamSyncTheme
import com.example.teamsync.util.ThemePreferences
import kotlinx.coroutines.tasks.await


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
    viewModel.getUserProfile()
    val userProfile = viewModel.userProfile

    var nome by remember { mutableStateOf(userProfile?.nome ?: "") }
    var cognome by remember { mutableStateOf(userProfile?.cognome ?: "") }
    var dataDiNascita by remember { mutableStateOf(userProfile?.dataDiNascita ?: "") }
    var matricola by remember { mutableStateOf(userProfile?.matricola ?: "") }
    var email by remember { mutableStateOf(userProfile?.email ?: "") }

    var loading1 by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
            imageUri?.let {
                viewModel.aggiorna_foto_profilo(it)
            }
        }

    // Stato per gestire il menu
    var showMenu by remember { mutableStateOf(false) }



    // LaunchedEffect per caricare il profilo utente
    LaunchedEffect(userProfile) {
        try {
            userProfile?.let {
                nome = it.nome ?: ""
                cognome = it.cognome ?: ""
                dataDiNascita = it.dataDiNascita ?: ""
                matricola = it.matricola ?: ""
                email = it.email ?: ""
            }
        } catch (e: Exception) {
            error = "Errore di connessione: ${e.message}"
        } finally {
            loading = false
        }
    }


    LaunchedEffect(imageUri) {
        imageUri?.let { uri ->
            try {
                loading1 = true
                val imageUrl = viewModel.aggiorna_foto_profilo(uri).await()

                val updatedProfile =
                    userProfile?.copy(immagine = imageUrl.toString()) ?: ProfiloUtente(
                        id = "", // Assicurati di gestire il caso in cui userProfile sia null
                        nome = nome,
                        cognome = cognome,
                        dataDiNascita = dataDiNascita,
                        matricola = matricola,
                        email = email,
                        immagine = imageUrl.toString()
                    )
                viewModel.updateUserProfile(updatedProfile)


            } catch (e: Exception) {
                // Gestisci eventuali errori durante il caricamento dell'immagine
                error = "Errore durante il caricamento dell'immagine: ${e.message}"
            } finally {
                // Aggiorna lo stato di caricamento o gestisci altri aspetti dell'UI
                loading1 = false
                loading = false
                navController.navigate(Schermate.ModificaProfilo.route)
            }
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


            Box(
                modifier = Modifier
                    .size(125.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {showMenu = true  }
                        )
                    },


            )

            {

                if (userProfile?.immagine != null) {

                        Image(
                            painter = rememberImagePainter(
                                userProfile.immagine,
                                builder = {
                                    // Gestisci l'indicatore di caricamento qui
                                    placeholder(R.drawable.white) // Placeholder di caricamento
                                    crossfade(true) // Effetto crossfade durante il caricamento
                                }
                            ),

                            contentDescription = "Immagine Profilo",
                            modifier = Modifier.fillMaxSize()
                                .clip(RoundedCornerShape(20.dp)),

                            contentScale = ContentScale.Crop,


                            )

                    }

                else {

                    Image(
                        painter = painterResource(id = R.drawable.user_icon),
                        contentDescription = "Icona Applicazione",
                        modifier = Modifier.fillMaxSize(),
                    )
                }


            }









        Spacer(modifier = Modifier.height(20.dp))

        if (loading) {
            Text("Caricamento in corso...", fontSize = 16.sp, color = Color.Gray)
        } else if (error != null) {
            Text("Errore: $error", fontSize = 16.sp, color = Color.Red)
        } else {
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
            Button(
                modifier = Modifier
                    .width(120.dp) // Imposta la larghezza al massimo disponibile
                    .height(50.dp)  // Imposta un'altezza fissa
                    .clickable { showMenu = true },
                onClick = {
                    val updatedProfile = ProfiloUtente(
                        id = userProfile?.id ?: "",
                        nome = nome,
                        cognome = cognome,
                        dataDiNascita = dataDiNascita,
                        matricola = matricola,
                        email = email,
                        immagine = userProfile?.immagine

                    )
                    viewModel.updateUserProfile(updatedProfile)
                    navController.navigate(Schermate.Impostazioni.route)
                },
                enabled = !loading1,
                border = BorderStroke(1.dp, Color.DarkGray),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE5E5E5), // Cambia il colore di sfondo del pulsante
                    contentColor = Color.DarkGray // Cambia il colore del testo all'interno del pulsante
                )
            ) {
                Text("Salva")
            }
            Spacer(modifier = Modifier.height(8.dp))
            if (loading1) {
                Text("Caricamento in corso...", fontSize = 16.sp, color = Color.Black)
            }
            Spacer(modifier = Modifier.height(20.dp))
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false } ,
                modifier = Modifier.align(Alignment.End),
                offset = DpOffset(x = 110.dp, y = 0.dp)
            ) {
                DropdownMenuItem(
                    text = { Text("Cambia Immagine") },
                    onClick = {
                        showMenu = false
                        launcher.launch("image/*")
                    }
                )
                DropdownMenuItem(
                    text = { Text("Rimuovi Immagine") },
                    onClick = {
                        showMenu = false
                        imageUri = null
                        viewModel.updateUserProfile(
                            userProfile?.copy(immagine = null) ?: ProfiloUtente(
                                id = userProfile?.id ?: "",
                                nome = nome,
                                cognome = cognome,
                                dataDiNascita = dataDiNascita,
                                matricola = matricola,
                                email = email,
                                immagine = null
                            )
                        )

                        navController.navigate(Schermate.ModificaProfilo.route)
                    }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

    }
}






@Composable
    fun UserProfileScreen_dark(viewModel: ViewModelUtente, navController: NavHostController) {
        viewModel.getUserProfile()
        val userProfile = viewModel.userProfile
        val darkTextColor = Color.White
        var nome by remember { mutableStateOf(userProfile?.nome ?: "") }
        var cognome by remember { mutableStateOf(userProfile?.cognome ?: "") }
        var dataDiNascita by remember { mutableStateOf(userProfile?.dataDiNascita ?: "") }
        var matricola by remember { mutableStateOf(userProfile?.matricola ?: "") }
        var email by remember { mutableStateOf(userProfile?.email ?: "") }



    var loading1 by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        uri: Uri? ->
            imageUri = uri
            imageUri?.let {
                viewModel.aggiorna_foto_profilo(it)
            }
        }

    // Stato per gestire il menu
    var showMenu by remember { mutableStateOf(false) }


    // LaunchedEffect per caricare il profilo utente
    LaunchedEffect(userProfile) {
        try {
            userProfile?.let {
                nome = it.nome ?: ""
                cognome = it.cognome ?: ""
                dataDiNascita = it.dataDiNascita ?: ""
                matricola = it.matricola ?: ""
                email = it.email ?: ""
            }
        } catch (e: Exception) {
            error = "Errore di connessione: ${e.message}"
        } finally {
            loading = false
        }
    }


    LaunchedEffect(imageUri) {
        imageUri?.let { uri ->
            try {
                loading1 = true
                val imageUrl = viewModel.aggiorna_foto_profilo(uri).await()

                val updatedProfile =
                    userProfile?.copy(immagine = imageUrl.toString()) ?: ProfiloUtente(
                        id = "", // Assicurati di gestire il caso in cui userProfile sia null
                        nome = nome,
                        cognome = cognome,
                        dataDiNascita = dataDiNascita,
                        matricola = matricola,
                        email = email,
                        immagine = imageUrl.toString()
                    )
                viewModel.updateUserProfile(updatedProfile)


            } catch (e: Exception) {
                // Gestisci eventuali errori durante il caricamento dell'immagine
                error = "Errore durante il caricamento dell'immagine: ${e.message}"
            } finally {
                // Aggiorna lo stato di caricamento o gestisci altri aspetti dell'UI
                loading1 = false
                loading = false
                navController.navigate(Schermate.ModificaProfilo.route)
            }
        }
    }

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
                        .fillMaxHeight(0.07f),
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
                Spacer(modifier = Modifier.height(8.dp))




                Box(
                    modifier = Modifier
                        .size(125.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {showMenu = true  }
                            )
                        },

                )

                {

                    if (userProfile?.immagine != null) {

                        Image(
                            painter = rememberImagePainter(
                                userProfile.immagine,
                                builder = {
                                    // Gestisci l'indicatore di caricamento qui
                                    placeholder(R.drawable.white) // Placeholder di caricamento
                                    crossfade(true) // Effetto crossfade durante il caricamento
                                }
                            ),
                            contentDescription = "Immagine Profilo",
                            modifier = Modifier.fillMaxSize()
                                .clip(RoundedCornerShape(20.dp)),

                            contentScale = ContentScale.Crop,

                            )

                    }

                    else {

                        Image(
                            painter = painterResource(id = R.drawable.user_icon),
                            contentDescription = "Icona Applicazione",
                            modifier = Modifier.fillMaxSize()
                        )
                    }


                }
                Spacer(modifier = Modifier.height(15.dp))

                Column(
                    modifier = Modifier
                        .border(2.dp, Color.White, RoundedCornerShape(16.dp))
                        .background(Color(0xFF333333), RoundedCornerShape(16.dp))
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally, // Centro gli elementi orizzontalmente
                    verticalArrangement = Arrangement.Top
                ) {






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
                Button(
                    modifier = Modifier
                        .width(120.dp) // Imposta la larghezza al massimo disponibile
                        .height(50.dp)  // Imposta un'altezza fissa
                        .clickable { showMenu = true },
                    onClick = {
                        val updatedProfile = ProfiloUtente(
                            id = userProfile?.id ?: "",
                            nome = nome,
                            cognome = cognome,
                            dataDiNascita = dataDiNascita,
                            matricola = matricola,
                            email = email,
                            immagine = userProfile?.immagine

                        )
                        viewModel.updateUserProfile(updatedProfile)
                        navController.navigate(Schermate.Impostazioni.route)
                    },
                    enabled = !loading1,
                    border = BorderStroke(1.dp, Color.DarkGray),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE5E5E5), // Cambia il colore di sfondo del pulsante
                        contentColor = Color.DarkGray // Cambia il colore del testo all'interno del pulsante
                    )
                ) {
                    Text("Salva")
                }
                Spacer(modifier = Modifier.height(8.dp))
                if (loading1) {
                    Text("Caricamento in corso...", fontSize = 16.sp, color = Color.White)
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false } ,
                    modifier = Modifier.align(Alignment.End),
                    offset = DpOffset(x = 110.dp, y = 100.dp)
                ) {
                    DropdownMenuItem(
                        text = { Text("Cambia Immagine") },
                        onClick = {
                            showMenu = false
                            launcher.launch("image/*")
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Rimuovi Immagine") },
                        onClick = {
                            showMenu = false
                            imageUri = null
                            viewModel.updateUserProfile(
                                userProfile?.copy(immagine = null) ?: ProfiloUtente(
                                    id = userProfile?.id ?: "",
                                    nome = nome,
                                    cognome = cognome,
                                    dataDiNascita = dataDiNascita,
                                    matricola = matricola,
                                    email = email,
                                    immagine = null
                                )
                            )

                            navController.navigate(Schermate.ModificaProfilo.route)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

            }
        }
    }



