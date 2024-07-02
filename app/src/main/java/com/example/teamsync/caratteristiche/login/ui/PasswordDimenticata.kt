package com.example.teamsync.caratteristiche.login.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.ui.theme.Grey20
import com.example.teamsync.ui.theme.Grey70
import com.example.teamsync.ui.theme.Red70
import com.example.teamsync.ui.theme.White
import com.example.teamsync.util.ThemePreferences

@Composable
fun PasswordDimenticata(
    navController: NavHostController,
    viewModelUtente: ViewModelUtente
){
    val context = LocalContext.current
    var emailRecuperoPassword by remember {
        mutableStateOf("")
    }
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)


    Box(modifier = Modifier.fillMaxSize()){
        if(isDarkTheme)
            Image(
                painter = painterResource(id = R.drawable.dimenticata_black),
                contentDescription = "sfondo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        else Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "sfondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = White
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Hai dimenticato la password?",
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp),
                color = if(isDarkTheme) White else Color.Black
            )

            Text(
                text = "Non Preoccuparti! Cliccando su \"Procedi\", verrà inviato un link per il recupero della password dimenticata all'indirizzo email associato al tuo account TeamSync.",
                textAlign = TextAlign.Center,

                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),

                color = if(isDarkTheme) White else Grey70,

            )


            OutlinedTextField(
                value = emailRecuperoPassword,
                onValueChange = {emailRecuperoPassword = it},
                label = {
                    Text(text = "Email")
                },
                leadingIcon = { Icon(
                    painter = painterResource(id = R.drawable.icona_mail ),
                    contentDescription = "icona mail",
                    modifier = Modifier.size(20.dp),
                )},
                colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Grey20,
                        focusedContainerColor = White
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(25.dp),
                maxLines = 1
            )

            Button(
                onClick = {
                    viewModelUtente.resetPassword(emailRecuperoPassword)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                colors = if(isDarkTheme) ButtonDefaults.buttonColors(
                    containerColor = Color.White, // Cambia il colore di sfondo del pulsante
                    contentColor = Color.DarkGray // Cambia il colore del testo all'interno del pulsante
                ) else ButtonDefaults.buttonColors(containerColor = Red70,contentColor = White)

            ) {
                Text(text = "Procedi")
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
    LaunchedEffect(viewModelUtente.erroreResetPassword.value) {
        if(viewModelUtente.erroreResetPassword.value != null){
            Toast.makeText(context, viewModelUtente.erroreResetPassword.value, Toast.LENGTH_LONG).show()
        }
    }
    LaunchedEffect(viewModelUtente.resetPasswordRiuscito.value) {
        if(viewModelUtente.resetPasswordRiuscito.value){
            navController.navigate(Schermate.Login.route)
        }

    }


}
@Preview(showBackground = true)
@Composable
fun PreviewPasswordDimenticata(){
    //PasswordDimenticata(navController = rememberNavController())
}
