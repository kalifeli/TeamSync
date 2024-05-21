package com.example.teamsync.screen

import androidx.compose.foundation.layout.*

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teamsync.R.drawable.user_icon
import com.example.teamsync.R.drawable.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import com.example.teamsync.R.drawable.icon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material3.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.ui.theme.Grey20
import com.example.teamsync.ui.theme.Red70
import com.example.teamsync.ui.theme.White


@Composable
fun LoginScreen(navController: NavHostController) {
    val background: Painter = painterResource(id = background)

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
                    OutlinedTextField(
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Grey20,
                            focusedContainerColor = White
                        ),
                        value = "",
                        onValueChange = {},
                        label = { Text("Email") },
                        shape = RoundedCornerShape(25.dp),
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Grey20,
                            focusedContainerColor = White
                        ),
                        value = "",
                        onValueChange = {},
                        label = { Text("Password") },
                        shape = RoundedCornerShape(25.dp),
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Password dimenticata?",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.End

                    )
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
                        onClick = { navController.navigate(Schermate.Benvenuto.route) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(Color(0xFFC1092A))
                    )

                    { Text(text = "Accedi") }

                    Spacer(modifier = Modifier.height(60.dp))

                    Row (modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    )
                    {
                        Text(
                            text = "Non hai un account? ",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(end = 1.dp)
                        )

                        Text(
                            text = "Registrati",
                            color = Red70,
                            modifier = Modifier
                                .clickable { navController.navigate(Schermate.Registrazione.route) },
                            minLines = 1


                        )
                    }
                }






                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }

    }


  

@Preview
@Composable
fun Loginn() {
    LoginScreen(navController = (rememberNavController()))
}


