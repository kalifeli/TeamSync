package com.example.teamsync.caratteristiche.autentificazione.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.R
import com.example.teamsync.R.drawable.background_black
import com.example.teamsync.R.drawable.icon
import com.example.teamsync.R.drawable.logo_white
import com.example.teamsync.R.drawable.registrazione
import com.example.teamsync.caratteristiche.autentificazione.data.model.SessoUtente
import com.example.teamsync.caratteristiche.autentificazione.data.repository.RepositoryUtente
import com.example.teamsync.caratteristiche.autentificazione.data.viewModel.ViewModelUtente
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.util.NoInternetScreen
import com.example.teamsync.util.theme.Grey20
import com.example.teamsync.util.theme.Grey50
import com.example.teamsync.util.theme.Red70
import com.example.teamsync.util.theme.White
import com.example.teamsync.util.ThemePreferences
import com.example.teamsync.util.isInternetAvailable
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Schermata di registrazione per nuovi utenti.
 *
 * @param navController Controller per la navigazione tra le schermate.
 * @param viewModelUtente ViewModel per la gestione delle operazioni di autenticazione utente.
 */
@Composable
fun Registrazione(navController: NavHostController, viewModelUtente: ViewModelUtente) {
    // Variabili per il tema e per lo sfondo
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    val background: Painter = painterResource(id = registrazione)
    val backgroundDark: Painter = painterResource(id = background_black)

    // Variabili di stato per i campi di input
    var matricola by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    var cognome by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confermaPassword by remember { mutableStateOf("") }
    var dataDiNascita by remember { mutableStateOf(Date()) }
    var sesso by remember { mutableStateOf(SessoUtente.UOMO) }
    var passwordVisibile by remember { mutableStateOf(false) }
    var confermaPasswordVisibile by remember { mutableStateOf(false) }
    val erroreRegistrazione = viewModelUtente.erroreRegistrazione.value
    val registrazioneRiuscita = viewModelUtente.registrazioneRiuscita.value
    val isLoading by viewModelUtente.isLoading.observeAsState()
    val context = LocalContext.current

    // Variabili per il DatePicker e il formato della data
    val calendar = Calendar.getInstance()
    val datePickerDialog = android.app.DatePickerDialog(
        context,
        if(isDarkTheme) R.style.CustomDatePickerDialogDark else R.style.CustomDatePickerDialog,
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
    var expanded by remember { mutableStateOf(false) }

    val isConnected = remember { mutableStateOf(isInternetAvailable(context)) }

    // Layout principale che occupa l'intero schermo
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = if (isDarkTheme) backgroundDark else background,
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds // Scala l'immagine per riempire lo spazio
        )

        // Colonna centrale per contenere i componenti UI
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Riga superiore con il logo dell'applicazione
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = if(isDarkTheme) painterResource(id = logo_white) else painterResource(id = icon),
                    contentDescription = "Icona Applicazione",
                    modifier = Modifier
                        .size(70.dp)
                )
            }

            // Controllo della connessione internet
            if(!isConnected.value){
                Spacer(modifier = Modifier.height(25.dp))
                NoInternetScreen(
                    isDarkTheme = isDarkTheme,
                    onRetry = {isConnected.value = isInternetAvailable(context) }
                )
            }else {

                // Titolo della schermata di registrazione
                Text(
                    text = stringResource(id = R.string.iscriviti),
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isDarkTheme) Color.White else Color.Black,
                    modifier = Modifier.padding(16.dp)
                )

                // Colonna contenente i campi di input
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally)
                {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    ) {
                        // Campo di input per la matricola
                        OutlinedTextField(
                            value = matricola,
                            onValueChange = { matricola = it },
                            label = { Text(stringResource(id = R.string.matricola)) },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_badge),
                                    contentDescription = "icona matricola registrazione",
                                    modifier = Modifier.size(20.dp),
                                    tint = Color.Black
                                )
                            },
                            shape = RoundedCornerShape(25.dp),
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 1,
                            maxLines = 1,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedLeadingIconColor = Color.Black,
                                focusedTrailingIconColor = Color.Black,
                                unfocusedLeadingIconColor = Color.Black,
                                unfocusedTrailingIconColor = Color.Black,
                                focusedBorderColor = if (isDarkTheme) Color.Black else Red70,
                                unfocusedBorderColor = if (isDarkTheme) Color.Gray else Color.Black,
                                focusedTextColor = Color.Black,
                                focusedContainerColor = White,
                                focusedLabelColor = if (isDarkTheme) Color.White else Red70,
                                unfocusedTextColor = Color.Black,
                                unfocusedContainerColor = Grey20,
                                unfocusedLabelColor = Color.Black,
                                cursorColor = if (isDarkTheme) Color.Black else Red70,
                            ),
                        )
                        // Campo di input per l'email
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.icona_mail),
                                    contentDescription = "icona mail",
                                    modifier = Modifier.size(20.dp),
                                    tint = Color.Black
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            shape = RoundedCornerShape(25.dp),
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 1,
                            maxLines = 1,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedLeadingIconColor = Color.Black,
                                focusedTrailingIconColor = Color.Black,
                                unfocusedLeadingIconColor = Color.Black,
                                unfocusedTrailingIconColor = Color.Black,
                                focusedBorderColor = if (isDarkTheme) Color.Black else Red70,
                                unfocusedBorderColor = if (isDarkTheme) Color.Gray else Color.Black,
                                focusedTextColor = Color.Black,
                                focusedContainerColor = White,
                                focusedLabelColor = if (isDarkTheme) Color.White else Red70,
                                unfocusedTextColor = Color.Black,
                                unfocusedContainerColor = Grey20,
                                unfocusedLabelColor = Color.Black,
                                cursorColor = if (isDarkTheme) Color.Black else Red70,
                            ),
                        )

                        // Riga contenente i campi di input per nome e cognome
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween

                        ) {
                            OutlinedTextField(
                                value = nome,
                                onValueChange = { nome = it },
                                label = { Text(stringResource(id = R.string.nome)) },
                                shape = RoundedCornerShape(25.dp),
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .padding(end = 5.dp),
                                minLines = 1,
                                maxLines = 1,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedLeadingIconColor = Color.Black,
                                    focusedTrailingIconColor = Color.Black,
                                    unfocusedLeadingIconColor = Color.Black,
                                    unfocusedTrailingIconColor = Color.Black,
                                    focusedBorderColor = if (isDarkTheme) Color.Black else Red70,
                                    unfocusedBorderColor = if (isDarkTheme) Color.Gray else Color.Black,
                                    focusedTextColor = Color.Black,
                                    focusedContainerColor = White,
                                    focusedLabelColor = if (isDarkTheme) Color.White else Red70,
                                    unfocusedTextColor = Color.Black,
                                    unfocusedContainerColor = Grey20,
                                    unfocusedLabelColor = Color.Black,
                                    cursorColor = if (isDarkTheme) Color.Black else Red70,
                                ),
                            )

                            OutlinedTextField(
                                value = cognome,
                                onValueChange = { cognome = it },
                                label = { Text(stringResource(id = R.string.cognome)) },
                                shape = RoundedCornerShape(25.dp),
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                                    .padding(start = 5.dp),
                                minLines = 1,
                                maxLines = 1,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedLeadingIconColor = Color.Black,
                                    focusedTrailingIconColor = Color.Black,
                                    unfocusedLeadingIconColor = Color.Black,
                                    unfocusedTrailingIconColor = Color.Black,
                                    focusedBorderColor = if (isDarkTheme) Color.Black else Red70,
                                    unfocusedBorderColor = if (isDarkTheme) Color.Gray else Color.Black,
                                    focusedTextColor = Color.Black,
                                    focusedContainerColor = White,
                                    focusedLabelColor = if (isDarkTheme) Color.White else Red70,
                                    unfocusedTextColor = Color.Black,
                                    unfocusedContainerColor = Grey20,
                                    unfocusedLabelColor = Color.Black,
                                    cursorColor = if (isDarkTheme) Color.Black else Red70,
                                ),
                            )
                        }

                        // Riga contenente i campi di input per data di nascita e sesso
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)

                        ) {
                            OutlinedTextField(
                                value = dataNascitaSdf,
                                onValueChange = {},
                                modifier = Modifier.weight(0.5f),
                                readOnly = true,
                                shape = RoundedCornerShape(25.dp),
                                label = { Text(stringResource(id = R.string.dataDiNascita)) },
                                trailingIcon = {
                                    IconButton(
                                        onClick = { datePickerDialog.show() },
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_calendario_evento),
                                            contentDescription = "scegli data di scadenza progetto",
                                            modifier = Modifier.size(20.dp),
                                            tint = Color.Black
                                        )
                                    }
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = if (isDarkTheme) Color.Black else Red70,
                                    unfocusedBorderColor = if (isDarkTheme) Color.Gray else Color.Black,
                                    focusedTextColor = Color.Black,
                                    focusedContainerColor = White,
                                    focusedLabelColor = if (isDarkTheme) Color.White else Red70,
                                    unfocusedTextColor = Color.Black,
                                    unfocusedContainerColor = Grey20,
                                    unfocusedLabelColor = if (isDarkTheme) Color.White else Color.Black,
                                    cursorColor = if (isDarkTheme) Color.Black else Red70,
                                ),
                            )
                            Box(modifier = Modifier.weight(0.5f)) {
                                OutlinedTextField(
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = if (isDarkTheme) Color.Black else Red70,
                                        unfocusedBorderColor = if (isDarkTheme) Color.Gray else Color.Black,
                                        focusedTextColor = Color.Black,
                                        focusedContainerColor = White,
                                        focusedLabelColor = if (isDarkTheme) Color.White else Red70,
                                        unfocusedTextColor = Color.Black,
                                        unfocusedContainerColor = Grey20,
                                        unfocusedLabelColor = if (isDarkTheme) Color.White else Color.Black,
                                        cursorColor = if (isDarkTheme) Color.Black else Red70,
                                    ),
                                    modifier = Modifier.fillMaxWidth(),
                                    value = sesso.getSessoTradotto(context, sesso),
                                    onValueChange = {},
                                    label = { Text(text = stringResource(id = R.string.sesso)) },
                                    readOnly = true,
                                    shape = RoundedCornerShape(25.dp),
                                    trailingIcon = {
                                        Icon(
                                            Icons.Default.ArrowDropDown,
                                            contentDescription = "Dropdown",
                                            modifier = Modifier.clickable { expanded = true },
                                            tint = Color.Black
                                        )
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
                                            text = { Text(s.getSessoTradotto(context, s), color = Color.Black) },
                                            onClick = {
                                                sesso = s
                                                expanded = false
                                            },
                                            modifier = Modifier.background(Grey20)
                                        )
                                    }
                                }
                            }
                        }


                        // Campo di input per la password
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password") },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.icona_password),
                                    contentDescription = "icona password registrazione",
                                    modifier = Modifier.size(20.dp)
                                )
                            },

                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Password
                            ),

                            visualTransformation = if (passwordVisibile) VisualTransformation.None else PasswordVisualTransformation(),

                            // visualizza o non visualizzare password
                            trailingIcon = {
                                IconButton(onClick = { passwordVisibile = !passwordVisibile }) {
                                    val icona: Painter = if (passwordVisibile) {
                                        painterResource(id = R.drawable.icona_password_visibile)
                                    } else {
                                        painterResource(id = R.drawable.icona_password_nonvisibile)
                                    }

                                    Icon(
                                        painter = icona,
                                        contentDescription = "icona visualizzazione conferma password",
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            },
                            shape = RoundedCornerShape(25.dp),
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 1,
                            maxLines = 1,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedLeadingIconColor = Color.Black,
                                focusedTrailingIconColor = Color.Black,
                                unfocusedLeadingIconColor = Color.Black,
                                unfocusedTrailingIconColor = Color.Black,
                                focusedBorderColor = if (isDarkTheme) Color.Black else Red70,
                                unfocusedBorderColor = if (isDarkTheme) Color.Gray else Color.Black,
                                focusedTextColor = Color.Black,
                                focusedContainerColor = White,
                                focusedLabelColor = if (isDarkTheme) Color.White else Red70,
                                unfocusedTextColor = Color.Black,
                                unfocusedContainerColor = Grey20,
                                unfocusedLabelColor = Color.Black,
                                cursorColor = if (isDarkTheme) Color.Black else Red70,
                            ),
                        )

                        // Campo di input per la conferma della password
                        OutlinedTextField(
                            value = confermaPassword,
                            onValueChange = { confermaPassword = it },
                            label = { Text(stringResource(id = R.string.confermaPassword)) },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.icona_password),
                                    contentDescription = "icona password login",
                                    modifier = Modifier.size(20.dp)
                                )
                            },

                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Password
                            ),

                            visualTransformation = if (confermaPasswordVisibile) VisualTransformation.None else PasswordVisualTransformation(),

                            // visualizza o non visualizzare password di conferma
                            trailingIcon = {
                                IconButton(onClick = {
                                    confermaPasswordVisibile = !confermaPasswordVisibile
                                }) {
                                    val icona: Painter = if (confermaPasswordVisibile) {
                                        painterResource(id = R.drawable.icona_password_visibile)
                                    } else {
                                        painterResource(id = R.drawable.icona_password_nonvisibile)
                                    }

                                    Icon(
                                        painter = icona,
                                        contentDescription = "icona visualizzazione conferma password",
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            },
                            shape = RoundedCornerShape(25.dp),
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 1,
                            maxLines = 1,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedLeadingIconColor = Color.Black,
                                focusedTrailingIconColor = Color.Black,
                                unfocusedLeadingIconColor = Color.Black,
                                unfocusedTrailingIconColor = Color.Black,
                                focusedBorderColor = if (isDarkTheme) Color.Black else Red70,
                                unfocusedBorderColor = if (isDarkTheme) Color.Gray else Color.Black,
                                focusedTextColor = Color.Black,
                                focusedContainerColor = White,
                                focusedLabelColor = if (isDarkTheme) Color.White else Red70,
                                unfocusedTextColor = Color.Black,
                                unfocusedContainerColor = Grey20,
                                unfocusedLabelColor = Color.Black,
                                cursorColor = if (isDarkTheme) Color.Black else Red70,
                            ),
                        )

                        // Pulsante per procedere con la registrazione
                        Button(
                            onClick = {
                                viewModelUtente.signUp(
                                    matricola,
                                    nome,
                                    cognome,
                                    email,
                                    dataDiNascita,
                                    sesso,
                                    password,
                                    confermaPassword
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .align(Alignment.CenterHorizontally),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isDarkTheme) Color.White else Red70,
                                contentColor = if (isDarkTheme) Color.DarkGray else White
                            )
                        ) { Text(text = stringResource(id = R.string.next)) }

                        // Indicatore di caricamento
                        if (isLoading == true) {
                            Spacer(modifier = Modifier.height(16.dp))
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .align(Alignment.CenterHorizontally),
                                color = Grey50,
                                trackColor = Red70,
                                strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap
                            )
                        }
                    }

                    // Sezione inferiore con il collegamento per il login
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(id = R.string.accountgia),
                                    textAlign = TextAlign.Center,
                                    color = if (isDarkTheme) White else Color.Black,
                                    modifier = Modifier.weight(1f)
                                )
                                Box(
                                    modifier = Modifier.clickable { navController.navigate(Schermate.Login.route) }
                                ) {
                                    Text(
                                        text = "Login",
                                        textAlign = TextAlign.Center,
                                        color = if (isDarkTheme) White else Red70,
                                        modifier = Modifier.width(IntrinsicSize.Min) // Mantiene la larghezza minima
                                    )
                                    HorizontalDivider(
                                        modifier = Modifier
                                            .align(Alignment.BottomCenter)
                                            .width(40.dp)
                                            .height(1.dp),
                                        color = if (isDarkTheme) White else Red70
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Effetto per il controllo periodico della connessione internet
    LaunchedEffect(Unit) {
        // Periodicamente avviene un controllo per verificare che ci sia la connessione ad internet
        while(true){
            isConnected.value = isInternetAvailable(context)
            delay(5000) // controllo ogni 5 secondi
        }
    }

    // Effetto per la gestione degli errori di registrazione
    LaunchedEffect(erroreRegistrazione, isLoading) {
        if(erroreRegistrazione != null  && !isLoading!!){
            Toast.makeText(context, erroreRegistrazione, Toast.LENGTH_LONG).show()
            viewModelUtente.resetErroreRegistrazione()
        }
    }

    // Effetto per la gestione della registrazione riuscita
    LaunchedEffect(registrazioneRiuscita,isLoading) {
        if(registrazioneRiuscita && !isLoading!!){
            navController.navigate(Schermate.VerificaEmail.route)
            viewModelUtente.resetRegistrazioneRiuscita()
        }
    }

}

/**
 * Anteprima della composable Registrazione.
 */
@Preview
@Composable
fun PreviewRegistrazione() {
    Registrazione(navController = (rememberNavController()), ViewModelUtente(RepositoryUtente()))
}