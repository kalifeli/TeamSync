package com.example.teamsync.caratteristiche.autentificazione.ui

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.autentificazione.data.viewModel.ViewModelUtente
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.util.ThemePreferences
import com.example.teamsync.util.theme.Grey20
import com.example.teamsync.util.theme.Grey70
import com.example.teamsync.util.theme.Red70
import com.example.teamsync.util.theme.White

/**
 * Schermata per il recupero della password.
 *
 * @param navController Controller per la navigazione tra le schermate.
 * @param viewModelUtente ViewModel per la gestione delle operazioni di autenticazione utente.
 */
@Composable
fun PasswordDimenticata(
    navController: NavHostController,
    viewModelUtente: ViewModelUtente
){
    var emailRecuperoPassword by remember { mutableStateOf("") }

    val context = LocalContext.current
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    // Layout principale che occupa l'intero schermo
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

        // Colonna centrale per contenere i componenti UI
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
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = White
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            // Titolo della schermata
            Text(
                text = stringResource(id = R.string.passwordDimenticata),
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp),
                color = if(isDarkTheme) White else Color.Black
            )

            // Descrizione della schermata
            Text(
                text = stringResource(id = R.string.nonPreoccuparti),
                textAlign = TextAlign.Center,

                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),

                color = if(isDarkTheme) White else Grey70,

            )

            // Campo di input per l'email
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
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLeadingIconColor = Color.Black,
                    focusedTrailingIconColor = Color.Black,
                    unfocusedLeadingIconColor = Color.Black,
                    unfocusedTrailingIconColor = Color.Black,
                    focusedBorderColor = if(isDarkTheme) Color.Black else Red70,
                    unfocusedBorderColor = if (isDarkTheme) Color.Gray else Color.Black,
                    focusedTextColor =  Color.Black,
                    focusedContainerColor = White,
                    focusedLabelColor = if(isDarkTheme) Color.White else Red70,
                    unfocusedTextColor =  Color.Black,
                    unfocusedContainerColor = Grey20,
                    unfocusedLabelColor =   Color.Black,
                    cursorColor = if(isDarkTheme) Color.Black else Red70,
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(25.dp),
                maxLines = 1
            )

            // Pulsante per procedere con il recupero della password
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
                Text(text = stringResource(id = R.string.procedi))
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    // Effetto lanciato quando viene rilevato un errore nel reset della password
    LaunchedEffect(viewModelUtente.erroreResetPassword.value) {
        if(viewModelUtente.erroreResetPassword.value != null){
            Toast.makeText(context, viewModelUtente.erroreResetPassword.value, Toast.LENGTH_LONG).show()
        }
    }

    // Effetto lanciato quando il reset della password è riuscito
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
