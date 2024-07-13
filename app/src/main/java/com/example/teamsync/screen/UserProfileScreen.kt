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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.login.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.login.data.model.SessoUtente
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.ui.theme.Grey20
import com.example.teamsync.ui.theme.Red70
import com.example.teamsync.ui.theme.TeamSyncTheme
import com.example.teamsync.ui.theme.White
import com.example.teamsync.util.ThemePreferences
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen_white(viewModel: ViewModelUtente, navController: NavHostController) {
    viewModel.getUserProfile()
    val userProfile = viewModel.userProfile

    var nome by remember { mutableStateOf(userProfile?.nome ?: "") }
    var cognome by remember { mutableStateOf(userProfile?.cognome ?: "") }
    var dataDiNascita by remember { mutableStateOf(userProfile?.dataDiNascita ?: Date()) }
    var sesso by remember { mutableStateOf(userProfile?.sesso ?: SessoUtente.UOMO) }
    var matricola by remember { mutableStateOf(userProfile?.matricola ?: "") }
    var email by remember { mutableStateOf(userProfile?.email ?: "") }
    var amici by remember { mutableStateOf(userProfile?.amici ?: emptyList()) }
    var loading1 by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    val primoaccesso by remember { mutableStateOf(userProfile?.primoAccesso ?: false) }
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
    val calendar = Calendar.getInstance()
    val context = LocalContext.current
    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            dataDiNascita = calendar.time
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val dataNascitaSdf = sdf.format(dataDiNascita)


    // LaunchedEffect per caricare il profilo utente
    LaunchedEffect(userProfile) {
        try {
            userProfile?.let {
                nome = it.nome
                cognome = it.cognome
                dataDiNascita = it.dataDiNascita
                sesso = it.sesso
                matricola = it.matricola
                email = it.email
                amici = it.amici
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
                        id = "",
                        nome = nome,
                        cognome = cognome,
                        dataDiNascita = dataDiNascita,
                        matricola = matricola,
                        email = email,
                        immagine = imageUrl.toString(),
                        amici = amici,
                        primoAccesso = primoaccesso
                    )
                viewModel.updateUserProfile(updatedProfile)


            } catch (e: Exception) {
                // Gestisce eventuali errori durante il caricamento dell'immagine
                error = "Errore durante il caricamento dell'immagine: ${e.message}"
            } finally {
                // Aggiorna lo stato di caricamento e gestisce altri aspetti dell'UI
                loading1 = false
                loading = false
                navController.navigate(Schermate.ModificaProfilo.route)
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.modificaProfiloScreen),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                        },
                navigationIcon = {
                    IconButton(
                        onClick = {navController.popBackStack()}
                    ) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "icona per tornare indietro", tint = Color.Black)
                    }
                }
            )
        }
    ){ padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(125.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { showMenu = true }
                        )
                    },
            ) {
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
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
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

            if (loading) {
                Text(stringResource(id = R.string.caricamento), style = MaterialTheme.typography.labelMedium, color = Color.Gray)
            } else if (error != null) {
                Text("Errore: $error", style = MaterialTheme.typography.labelMedium, color = Color.Red)
            } else {
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text(stringResource(id = R.string.nome), color = Color.Black) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Red70
                    ),
                    shape = RoundedCornerShape(16.dp),
                )
                OutlinedTextField(
                    value = cognome,
                    onValueChange = { cognome = it },
                    label = { Text(stringResource(id = R.string.cognome), color = Color.Black) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Red70
                    ),
                    shape = RoundedCornerShape(16.dp),
                )
                OutlinedTextField(
                    value = matricola,
                    onValueChange = { matricola = it },
                    label = { Text(stringResource(id = R.string.matricola1), color = Color.Black) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Red70
                    ),
                    shape = RoundedCornerShape(16.dp),
                )
                OutlinedTextField(
                    value = dataNascitaSdf,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(id = R.string.dataDiNascita), color = Color.Black) },
                    trailingIcon = {
                        IconButton(
                            onClick = {datePickerDialog.show()},
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_calendario_evento),
                                contentDescription = "scegli data di scadenza progetto",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Red70
                    ),
                    shape = RoundedCornerShape(16.dp),
                )
                var expanded by remember { mutableStateOf(false) }
                Box{
                    OutlinedTextField(
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Grey20,
                            focusedContainerColor = White
                        ),
                        value = sesso.name,
                        onValueChange = {},
                        label = { Text(text = stringResource(id = R.string.sesso)) },
                        readOnly = true,
                        shape = RoundedCornerShape(15.dp),
                        trailingIcon = {
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown",
                                modifier = Modifier.clickable { expanded = true })
                        },
                        minLines = 1,
                        maxLines = 1,
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Grey20)
                    ) {
                        SessoUtente.entries.forEach { s ->
                            DropdownMenuItem(
                                text = { Text(s.name) },
                                onClick = {
                                    sesso = s
                                    expanded = false
                                },
                                modifier = Modifier.background(Grey20)
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email", color = Color.Black) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Red70
                    ),
                    shape = RoundedCornerShape(16.dp)
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                        .clickable { showMenu = true },
                    onClick = {
                        val updatedProfile = ProfiloUtente(
                            id = userProfile?.id ?: "",
                            nome = nome,
                            cognome = cognome,
                            dataDiNascita = dataDiNascita,
                            sesso = sesso,
                            matricola = matricola,
                            email = email,
                            immagine = userProfile?.immagine,
                            primoAccesso = primoaccesso,
                            amici = amici
                        )
                        viewModel.updateUserProfile(updatedProfile)
                        navController.navigate(Schermate.Impostazioni.route)
                    },
                    enabled = !loading1,
                    border = BorderStroke(1.dp, Color.DarkGray),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Red70,
                        contentColor = White
                    )
                ) {
                    Text(stringResource(id = R.string.salvaEdit))
                }

                if (loading1) {
                    Text(
                        stringResource(id = R.string.caricamento),
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false } ,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .background(Grey20),
                    offset = DpOffset(x = 120.dp, y = 0.dp),
                ) {
                    DropdownMenuItem(
                        text = { Text(stringResource(id = R.string.caricaImmagine)) },
                        onClick = {
                            showMenu = false
                            launcher.launch("image/*")
                        },
                        modifier = Modifier.background(Grey20)
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(id = R.string.rimuoviImmagine)) },
                        onClick = {
                            showMenu = false
                            imageUri = null
                            viewModel.updateUserProfile(
                                userProfile?.copy(immagine = null) ?: ProfiloUtente(
                                    id = userProfile?.id ?: "",
                                    nome = nome,
                                    cognome = cognome,
                                    dataDiNascita = dataDiNascita,
                                    sesso = sesso,
                                    matricola = matricola,
                                    email = email,
                                    immagine = null
                                )
                            )
                            navController.navigate(Schermate.ModificaProfilo.route)
                        },
                        modifier = Modifier.background(Grey20)
                    )
                }
            }
        }
    }
}






@Composable
    fun UserProfileScreen_dark(viewModel: ViewModelUtente, navController: NavHostController) {
        viewModel.getUserProfile() // ????????
    val userProfile = viewModel.userProfile
    val darkTextColor = Color.White
    var nome by remember { mutableStateOf(userProfile?.nome ?: "") }
    var cognome by remember { mutableStateOf(userProfile?.cognome ?: "") }
    var dataDiNascita by remember { mutableStateOf(userProfile?.dataDiNascita ?: Date()) }
    var matricola by remember { mutableStateOf(userProfile?.matricola ?: "") }
    var email by remember { mutableStateOf(userProfile?.email ?: "") }

    var amici by remember { mutableStateOf(userProfile?.amici ?: emptyList()) }
    var primoaccesso by remember { mutableStateOf(userProfile?.primoAccesso ?: false) }
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
                nome = it.nome
                cognome = it.cognome
                dataDiNascita = it.dataDiNascita
                matricola = it.matricola
                email = it.email
                amici = it.amici
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
                        id = "",
                        nome = nome,
                        cognome = cognome,
                        dataDiNascita = dataDiNascita,
                        matricola = matricola,
                        email = email,
                        immagine = imageUrl.toString(),
                        primoAccesso = primoaccesso,
                        amici = amici

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
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.DarkGray),
        ) {
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
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
                                onTap = { showMenu = true }
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
                            modifier = Modifier
                                .fillMaxSize()
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

                        label = { Text(stringResource(id = R.string.nome), color = darkTextColor) }
                    )
                    OutlinedTextField(
                        value = cognome,
                        onValueChange = { cognome = it },
                        label = { Text((stringResource(id = R.string.cognome)), color = darkTextColor) }
                    )
                    OutlinedTextField(
                        value = matricola,
                        onValueChange = { matricola = it },
                        label = { Text((stringResource(id = R.string.matricola1)), color = darkTextColor) }
                    )
                    OutlinedTextField(
                        value = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(dataDiNascita),
                        onValueChange = {},
                        label = { Text(stringResource(id = R.string.dataDiNascita), color = darkTextColor) }
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
                            immagine = userProfile?.immagine,
                            amici = amici,
                            primoAccesso = primoaccesso

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
                    Text(stringResource(id = R.string.salvaEdit))
                }
                Spacer(modifier = Modifier.height(8.dp))
                if (loading1) {
                    Text(stringResource(id = R.string.caricamento), fontSize = 16.sp, color = Color.White)
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false } ,
                    modifier = Modifier.align(Alignment.End),
                    offset = DpOffset(x = 110.dp, y = 100.dp)
                ) {
                    DropdownMenuItem(
                        text = { Text(stringResource(id = R.string.caricaImmagine)) },
                        onClick = {
                            showMenu = false
                            launcher.launch("image/*")
                        }
                    )

                    DropdownMenuItem(
                        text = { Text(stringResource(id = R.string.rimuoviImmagine)) },
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



