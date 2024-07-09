package com.example.teamsync.screen

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import com.example.teamsync.caratteristiche.login.data.model.SessoUtente
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.ui.theme.Grey20
import com.example.teamsync.ui.theme.Red70
import com.example.teamsync.ui.theme.White
import com.example.teamsync.util.ThemePreferences
import java.util.Date

@Composable
fun Registrazione(navController: NavHostController, viewModelUtente: ViewModelUtente) {
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    val background: Painter = painterResource(id = registrazione)
    val backgroundB: Painter = painterResource(id = background_black)

    var matricola by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var nome by remember {
        mutableStateOf("")
    }
    var cognome by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var confermaPassword by remember {
        mutableStateOf("")
    }
    var dataNascita by remember {
        mutableStateOf(Date())
    }
    var sesso by remember {
        mutableStateOf(SessoUtente.UOMO)
    }
    var passwordVisibile by remember {
        mutableStateOf(false)
    }
    var confermaPasswordVisibile by remember {
        mutableStateOf(false)
    }
    val erroreRegistrazione = viewModelUtente.erroreRegistrazione.value
    val registrazioneRiuscita = viewModelUtente.registrazioneRiuscita.value
    val context = LocalContext.current



    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
       if(isDarkTheme)
           Image(
               painter = backgroundB,
               contentDescription = "Background Image",
               modifier = Modifier
                   .fillMaxSize(),
               contentScale = ContentScale.FillBounds // Scala l'immagine per riempire lo spazio

           )
       else
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

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if(isDarkTheme)
                Image(
                    painter = painterResource(id = logo_white),
                    contentDescription = "Icona Applicazione",
                    modifier = Modifier
                        .size(70.dp)


                )
                else
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = "Icona Applicazione",
                        modifier = Modifier
                            .size(70.dp)

                    )

            }


            Spacer(modifier = Modifier.height(40.dp))
            Text(text = stringResource(id = R.string.iscriviti), fontSize = 35.sp, fontWeight = FontWeight.Bold, color = if (isDarkTheme) Color.White else  Color.Black)
            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                ) {
                    OutlinedTextField(
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Grey20,
                            focusedContainerColor = White
                        ),
                        value = matricola,
                        onValueChange = {matricola = it},
                        label = { Text(stringResource(id = R.string.matricola)) },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_badge),
                                contentDescription = "icona matricola registrazione",
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier .fillMaxWidth(),
                        minLines = 1,
                        maxLines = 1
                    )
                    OutlinedTextField(
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Grey20,
                            focusedContainerColor = White
                        ),
                        value = email,
                        onValueChange = {email = it},
                        label = { Text("Email") },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.icona_mail),
                                contentDescription = "icona mail",
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier .fillMaxWidth(),
                        minLines = 1,
                        maxLines = 1
                    )


                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween

                    ) {
                        OutlinedTextField(
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Grey20,
                                focusedContainerColor = White
                            ),
                            value = nome,
                            onValueChange = {nome = it},
                            label = { Text(stringResource(id = R.string.nome)) },
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .padding(end = 5.dp),
                            minLines = 1,
                            maxLines = 1
                        )
                        OutlinedTextField(
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Grey20,
                                focusedContainerColor = White
                            ),
                            value = cognome,
                            onValueChange = {cognome = it},
                            label = { Text(stringResource(id = R.string.cognome)) },
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .padding(start = 5.dp),
                            minLines = 1,
                            maxLines = 1
                        )
                    }

                    OutlinedTextField(
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Grey20,
                            focusedContainerColor = White
                        ),
                        value = password,
                        onValueChange = {password = it},
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
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 1,
                        maxLines = 1
                    )

                    OutlinedTextField(
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Grey20,
                            focusedContainerColor = White
                        ),
                        value = confermaPassword,
                        onValueChange = {confermaPassword = it},
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
                            IconButton(onClick = { confermaPasswordVisibile = !confermaPasswordVisibile }) {
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
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 1,
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = {
                            viewModelUtente.signUp(matricola,nome, cognome,email,dataNascita,sesso,password,confermaPassword)
                                  },
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.20f),
                        colors = if(isDarkTheme) ButtonDefaults.buttonColors(
                            containerColor = Color.White, // Cambia il colore di sfondo del pulsante
                            contentColor = Color.DarkGray // Cambia il colore del testo all'interno del pulsante
                        ) else ButtonDefaults.buttonColors(Red70)
                    ) {  Text(text = stringResource(id = R.string.next)) }


                }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 40.dp),
                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                        ) {
                            Text(
                                text = stringResource(id = R.string.accountgia),
                                textAlign = TextAlign.Center,
                                color = if(isDarkTheme) White else Color.Black
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clickable { navController.navigate(Schermate.Login.route) }
                        ) {
                            Text(
                                text = "Login",
                                textAlign = TextAlign.Center,
                                color = if(isDarkTheme) White else Red70
                            )
                            HorizontalDivider(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .width(50.dp)
                                    .height(1.dp), // Adjust thickness as needed
                                color = if(isDarkTheme) White else Red70
                            )
                        }
                    }
                }
            }
        }

    }
    LaunchedEffect(erroreRegistrazione) {
        if(erroreRegistrazione != null ){
            Toast.makeText(context, erroreRegistrazione, Toast.LENGTH_LONG).show()
            viewModelUtente.resetErroreRegistrazione()
        }
    }
    LaunchedEffect(registrazioneRiuscita) {
        if(registrazioneRiuscita){
            navController.navigate(Schermate.VerificaEmail.route)
            viewModelUtente.resetRegistrazioneRiuscita()
        }
    }

}




@Preview
@Composable
fun PreviewRegistrazione() {
    Registrazione(navController = (rememberNavController()), ViewModelUtente())
}
