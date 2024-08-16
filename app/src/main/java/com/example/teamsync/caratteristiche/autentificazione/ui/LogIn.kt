package com.example.teamsync.caratteristiche.autentificazione.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.teamsync.R.drawable.background
import com.example.teamsync.R.drawable.background_black
import com.example.teamsync.R.drawable.icon
import com.example.teamsync.R.drawable.logo_white
import com.example.teamsync.R.drawable.user_icon
import com.example.teamsync.caratteristiche.leMieAttivita.data.repository.ToDoRepository
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.repository.RepositoryProgetto
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
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

/**
 * Schermata di login dell'applicazione.
 *
 * @param navController Controller per la navigazione tra schermate.
 * @param viewModelUtente ViewModel per la gestione dei dati dell'utente.
 * @param viewModelProgetto ViewModel per la gestione dei dati dei progetti.
 */
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModelUtente: ViewModelUtente,
    viewModelProgetto: ViewModelProgetto
) {

    // Variabili di stato per email, password e visibilità della password
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibile by remember { mutableStateOf(false) }

    // Variabili di stato per il controllo degli errori e dello stato di login
    val erroreLogin = viewModelUtente.erroreLogin.value
    val erroreVerificaEmail = viewModelUtente.erroreVerificaEmail.value
    val loginRiuscito = viewModelUtente.loginRiuscito.value
    val primoAccesso = viewModelUtente.primoAccesso.value

    // Variabili di stato per il contesto e il tema
    val context = LocalContext.current
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    val background: Painter = painterResource(id = background)
    val backgroundB: Painter = painterResource(id = background_black)

    // Variabile di stato per il caricamento e la connessione
    var isLoading by remember { mutableStateOf(false) }
    val isConnected = remember { mutableStateOf(isInternetAvailable(context)) }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        // Immagine di sfondo basata sul tema
        if (isDarkTheme) {
            Image(
                painter = backgroundB,
                contentDescription = "Background Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        } else {
            Image(
                painter = background,
                contentDescription = "Background Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Sezione per l'icona dell'applicazione
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (isDarkTheme) {
                    Image(
                        painter = painterResource(id = logo_white),
                        contentDescription = "Icona Applicazione",
                        modifier = Modifier.size(70.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = "Icona Applicazione",
                        modifier = Modifier.size(70.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Schermata per la gestione dello stato della connessione
            if (!isConnected.value) {
                Spacer(modifier = Modifier.height(10.dp))
                NoInternetScreen(
                    isDarkTheme = isDarkTheme,
                    onRetry = { isConnected.value = isInternetAvailable(context) }
                )
            } else {
                Image(
                    painter = painterResource(id = user_icon),
                    contentDescription = "Icona User Login",
                    modifier = Modifier.size(150.dp) // Imposta la dimensione dell'immagine
                )
                Spacer(modifier = Modifier.height(40.dp))

                Column(modifier = Modifier.verticalScroll(rememberScrollState())

                    .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                Text(
                    text = stringResource(id = R.string.accedi),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isDarkTheme) White else Color.Black
                )

                Spacer(modifier = Modifier.height(25.dp))

                    // Form per l'inserimento di email e password
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 30.dp),
                    ) {
                        // campo Email Login
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = if (isDarkTheme) Color.Black else Red70,
                                focusedTextColor = Color.Black,
                                focusedContainerColor = White,
                                focusedLabelColor = if (isDarkTheme) Color.White else Red70,
                                unfocusedBorderColor = if (isDarkTheme) Color.Gray else Color.Black,
                                unfocusedTextColor = Color.Black,
                                unfocusedContainerColor = Grey20,
                                unfocusedLabelColor = Color.Black,
                                cursorColor = if (isDarkTheme) Color.Black else Red70,

                                ),
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.icona_mail),
                                    contentDescription = "icona mail",
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            shape = RoundedCornerShape(25.dp),
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        // campo Password Login
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = if (isDarkTheme) Color.Black else Red70,
                                focusedTextColor = Color.Black,
                                focusedContainerColor = White,
                                focusedLabelColor = if (isDarkTheme) Color.White else Red70,
                                unfocusedBorderColor = if (isDarkTheme) Color.Gray else Color.Black,
                                unfocusedTextColor = Color.Black,
                                unfocusedContainerColor = Grey20,
                                unfocusedLabelColor = Color.Black,
                                cursorColor = if (isDarkTheme) Color.Black else Red70,

                                ),
                            value = password,
                            onValueChange = { password = it },
                            label = {
                                Text("Password")
                            },
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
                                        contentDescription = "icona visualizzazione password",
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            },
                            shape = RoundedCornerShape(25.dp),
                            maxLines = 1
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Link per il recupero della password
                        Box(
                            modifier = Modifier
                                .clickable(
                                    indication = null, // Rimuove l'ombra di selezione
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    navController.navigate(Schermate.RecuperoPassword.route)
                                }
                        ) {
                            Text(
                                text = stringResource(id = R.string.passwordDimenticata),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.End,
                                color = if (isDarkTheme) Color.White else Color.Black
                            )
                        }
                    }

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    // Bottone di login
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(horizontal = 40.dp),
                        ) {

                        Spacer(modifier = Modifier.height(30.dp))

                        Button(
                            onClick = {
                                isLoading = true
                                viewModelUtente.login(email, password)
                            },
                            enabled = !isLoading,
                            modifier = Modifier.fillMaxWidth(),

                            colors = if (isDarkTheme) ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.DarkGray
                            ) else ButtonDefaults.buttonColors(containerColor = Red70, contentColor = Color.White)
                        ) {
                            Text(text = stringResource(id = R.string.accedi))
                        }

                        // Indicatore di caricamento
                        if (isLoading) {
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

                        Spacer(modifier = Modifier.height(40.dp))

                        // Link per la registrazione
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(modifier = Modifier) {
                                Text(
                                    text = stringResource(id = R.string.nonHaiAccount),
                                    textAlign = TextAlign.Center,
                                    color = if (isDarkTheme) Color.White else Color.Black
                                )
                            }
                            Box(
                                modifier = Modifier.clickable {
                                    navController.navigate(Schermate.Registrazione.route)
                                }
                            ) {
                                Text(
                                    text = stringResource(id = R.string.registrati),
                                    textAlign = TextAlign.Center,
                                    color = if (isDarkTheme) Color.White else Red70
                                )
                                HorizontalDivider(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .width(70.dp)
                                        .height(1.dp),
                                    color = if (isDarkTheme) White else Red70
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }

                }
                }
            }
        }
    }

    // Effetto lanciato al montaggio del composable per controllare periodicamente la connessione
    LaunchedEffect(Unit) {
        while(true){
            isConnected.value = isInternetAvailable(context)
            delay(5000)
        }
    }

    // Effetto lanciato al verificarsi di un errore di login
    LaunchedEffect(erroreLogin) {
        erroreLogin?.let { messaggio ->
            Toast.makeText(context, messaggio, Toast.LENGTH_SHORT).show()
            isLoading = false
            viewModelUtente.resetErroreLogin()
        }
    }

    // Effetto lanciato al successo del login
    LaunchedEffect(loginRiuscito) {
        if (loginRiuscito) {
            Log.d("LoginScreen", "primoAccesso in LaunchedEffect: $primoAccesso")
            if (primoAccesso) {
                navController.navigate(Schermate.Benvenuto.route)
            } else {
                isLoading = true
                viewModelProgetto.aggiornaUtenteCorrente()
                viewModelProgetto.utenteCorrenteId.value?.let {
                    viewModelProgetto.caricaProgettiUtente(it, false)
                }
                navController.navigate(Schermate.ItuoiProgetti.route) {
                    popUpTo(Schermate.Login.route) { inclusive = true }
                }
            }
            viewModelUtente.resetLoginRiuscito()
            isLoading = false
        }
    }

    // Effetto lanciato al verificarsi di un errore di verifica email
    LaunchedEffect(erroreVerificaEmail) {
        if (erroreVerificaEmail != null) {
            Toast.makeText(context, erroreVerificaEmail, Toast.LENGTH_LONG).show()
            viewModelUtente.resetErroreVerificaEmail()
        }
    }
}

/**
 * Anteprima della schermata di login.
 */

@Preview
@Composable
fun Login() {
    LoginScreen(navController = (rememberNavController()), ViewModelUtente(RepositoryUtente()), ViewModelProgetto(
        RepositoryProgetto(), ToDoRepository(), ViewModelUtente(RepositoryUtente())
    ))
}
