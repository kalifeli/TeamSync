package com.example.teamsync.screen

import com.example.teamsync.R
import com.example.teamsync.R.drawable.start
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import com.example.teamsync.R.drawable.icon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController


val lobsterFontFamily = FontFamily(Font(R.font.lobster)) // Assicurati di sostituire "lobster" con il nome effettivo del tuo file font
val customTextStyle = TextStyle(
    fontFamily = lobsterFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 60.sp,
    color = Color.White,

    )
@Composable
fun Start(navController: NavHostController) {
    val background: Painter = painterResource(id = start)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Immagine di sfondo
        Image(
            painter = background,
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize()
                .clickable {  navController.navigate("Login") },
            contentScale = ContentScale.FillBounds // Scala l'immagine per riempire lo spazio

        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "TeamSynch", style= customTextStyle
                )
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = "Icona Applicazione",
                    modifier = Modifier
                        .size(70.dp)
                )

            }

        }




    }
    /*LaunchedEffect(Unit) {
        navController.navigate("Login")
    }*/
}
