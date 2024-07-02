package com.example.teamsync.caratteristiche.login.ui

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
import androidx.compose.material3.Divider
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
import com.example.teamsync.R.drawable.icon
import com.example.teamsync.R.drawable.user_icon
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.ui.theme.Grey20
import com.example.teamsync.ui.theme.Red70
import com.example.teamsync.ui.theme.White



@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModelUtente: ViewModelUtente
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibile by remember { mutableStateOf(false) }
    var erroreLogin = viewModelUtente.erroreLogin.value
    val erroreVerificaEmail = viewModelUtente.erroreVerificaEmail.value
    val loginRiuscito = viewModelUtente.loginRiuscito.value
    val primoAccesso = viewModelUtente.primoAccesso.value
    val context = LocalContext.current

    val background: Painter = painterResource(id = background)

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        // Immagine di sfondo
        Image(
            painter = background,
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
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
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = "Icona Applicazione",
                    modifier = Modifier
                        .size(70.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = user_icon),
                contentDescription = "Icona User Login",
                modifier = Modifier.size(150.dp) // Imposta la dimensione dell'immagine
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(text = "Accedi", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(25.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 40.dp),
                ) {
                    // campo Email Login
                    OutlinedTextField(
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Grey20,
                            focusedContainerColor = White
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
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Grey20,
                            focusedContainerColor = White
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

                    Box(
                        modifier = Modifier.clickable {
                            navController.navigate(Schermate.RecuperoPassword.route)
                        }
                    ) {
                        Text(
                            text = "Password dimenticata?",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End
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

                Column(

                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 40.dp),

                    ) {

                    Spacer(modifier = Modifier.height(30.dp))
                    Button(
                        onClick = {
                            viewModelUtente.login(email, password)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Red70)
                    ) {
                        Text(text = "Accedi")
                    }


                    Spacer(modifier = Modifier.height(60.dp))


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(modifier = Modifier) {
                            Text(
                                text = "Non hai un account? ",
                                textAlign = TextAlign.Center
                            )
                        }
                        Box(
                            modifier = Modifier.clickable {
                                navController.navigate(Schermate.Registrazione.route)
                            }
                        ) {
                            Text(
                                text = "Registrati",
                                textAlign = TextAlign.Center,
                                color = Red70
                            )
                            Divider(
                                color = Red70,
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .width(70.dp)
                                    .height(1.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))

            }
        }
    }
    LaunchedEffect(erroreLogin) {
        erroreLogin?.let { messaggio ->
            Toast.makeText(context, messaggio, Toast.LENGTH_SHORT).show()
            viewModelUtente.resetErroreLogin()
        }
    }
    LaunchedEffect(loginRiuscito) {
        if (loginRiuscito) {
            if (primoAccesso) {
                navController.navigate(Schermate.Benvenuto.route)
            } else {
                navController.navigate(Schermate.Progetti.route)
            }
            viewModelUtente.resetLoginRiuscito()
        }
    }
    LaunchedEffect(erroreVerificaEmail) {
        if(erroreVerificaEmail != null){
            Toast.makeText(context, erroreVerificaEmail, Toast.LENGTH_LONG).show()
            viewModelUtente.resetErroreVerificaEmail()
        }

    }
}




@Preview
@Composable
fun Login() {
    LoginScreen(navController = (rememberNavController()), ViewModelUtente())
}


