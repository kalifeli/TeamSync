package com.example.teamsync.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.teamsync.R.drawable.registrazione
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.ui.theme.Grey20
import com.example.teamsync.ui.theme.Red70
import com.example.teamsync.ui.theme.White

@Composable
fun Registrazione(navController: NavHostController) {
    val background: Painter = painterResource(id = registrazione)

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


            Spacer(modifier = Modifier.height(40.dp))
            Text(text = "Iscriviti", fontSize = 35.sp, fontWeight = FontWeight.Bold)
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
                        value = "",
                        onValueChange = {},
                        label = { Text("Email") },
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier .fillMaxWidth()
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
                            value = "",
                            onValueChange = {},
                            label = { Text("Nome") },
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .padding(end = 5.dp)
                        )
                        OutlinedTextField(
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Grey20,
                                focusedContainerColor = White
                            ),
                            value = "",
                            onValueChange = {},
                            label = { Text("Cognome") },
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .padding(start = 5.dp)
                        )
                    }




                    OutlinedTextField(
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Grey20,
                            focusedContainerColor = White
                        ),
                        value = "",
                        onValueChange = {},
                        label = { Text("Password") },
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier.fillMaxWidth(),
                        // Personalizza il colore del contenitore
                    )

                    OutlinedTextField(
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Grey20,
                            focusedContainerColor = White
                        ),
                        value = "",
                        onValueChange = {},
                        label = { Text("Conferma Password") },
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Button(onClick = { navController.navigate(Schermate.Benvenuto.route) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.20f),
                        colors = ButtonDefaults.buttonColors(Color(0xFFC1092A))
                    ) {  Text(text = "Avanti") }


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
                    Spacer(modifier = Modifier.height(100.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                        ) {
                            Text(
                                text = "Hai già un account?  ",
                                textAlign = TextAlign.Center,
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clickable { navController.navigate(Schermate.Login.route) }
                        ) {
                            Text(
                                text = "Accedi",
                                textAlign = TextAlign.Center,
                                color = Red70,
                            )
                            Divider(
                                color = Red70,
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .width(50.dp)
                                    .height(1.dp) // Adjust thickness as needed
                            )
                        }
                    }



                    /*
                    Row (modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    )
                    {

                        Divider(
                            color = Red70,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp) // Adjust thickness as needed
                        )

                    }

                     */



                }
            }
        }

    }
}




@Preview
@Composable
fun PreviewAccesso() {
    Registrazione(navController = (rememberNavController()))
}
