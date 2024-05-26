package com.example.teamsync.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.R
import com.example.teamsync.navigation.Schermate

data class SettingItem(val icon: Int, val label: String, val rotta: Schermate)

val settingsList1 = listOf(
    SettingItem(R.drawable.person_icon, "Modifica Profilo",Schermate.ModificaProfilo ),
    SettingItem(R.drawable.notifiche, "Notifiche",Schermate.Progetti),
    SettingItem(R.drawable.lingua, "Lingua",Schermate.Progetti),
    SettingItem(R.drawable.tema, "Tema",Schermate.Progetti),
    SettingItem(R.drawable.task, "Task", Schermate.Progetti),
    SettingItem(R.drawable.to_do_list, "To-Do List",Schermate.Progetti),

)

val settingsList2 = listOf(
    SettingItem(R.drawable.help, "Help & Support",Schermate.Progetti),
    SettingItem(R.drawable.terms_and_condiction, "Terms and Policies",Schermate.Progetti)
)

val settingsList3 = listOf(
    SettingItem(R.drawable.logout, "Logout",Schermate.Login)
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Impostazioni(navController: NavHostController) {
    val background: Painter = painterResource(id = R.drawable.bianco)

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

        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.08f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    FloatingActionButton(
                        onClick = { navController.navigate(Schermate.Progetti.route) },
                        modifier = Modifier
                            .size(35.dp)
                            .background(Color.White),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "close_impostazioni",
                            )
                    }

                    // Centra il testo all'interno della Row
                    Row(
                        modifier = Modifier.weight(8f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Impostazioni",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Row vuota per bilanciare il layout
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {}

                }
            }

            Row(
                modifier = Modifier.clickable { navController.navigate(Schermate.ModificaProfilo.route) }
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(top = 20.dp)
                    .padding(bottom = 5.dp),
            ) {
                Text(
                    text = "Account",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,

                    )
            }



            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp,  bottomEnd = 12.dp, bottomStart = 20.dp),)
            ) {
                items(settingsList1) { setting ->
                    SettingItemRow(setting = setting, navController = navController)
                }
            }

            Row(
                modifier = Modifier.clickable { navController.navigate(Schermate.ModificaProfilo.route) }
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(top = 50.dp)
                    .padding(bottom = 5.dp),
            ) {
                Text(
                    text = "Supporto",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,

                    )
            }



            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp,  bottomEnd = 12.dp, bottomStart = 20.dp),)
            ) {
                items(settingsList2) { setting ->
                    SettingItemRow(setting = setting, navController = navController)
                }
            }
            Row(
                modifier = Modifier.clickable { navController.navigate(Schermate.ModificaProfilo.route) }
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(top = 50.dp)
                    .padding(bottom = 5.dp),
            ) {
                Text(
                    text = "Azioni",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,

                    )
            }



            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp,  bottomEnd = 12.dp, bottomStart = 20.dp),)
            ) {
                items(settingsList3) { setting ->
                    SettingItemRow(setting = setting, navController = navController)
                }
            }


        }

            }

        }





@Composable
fun SettingItemRow(setting: SettingItem,navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE5E5E5)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {

        Row(
            modifier = Modifier.weight(0.45f),
            verticalAlignment = Alignment.CenterVertically
        ) {}
        Image(
            painter = painterResource(id = setting.icon),
            contentDescription = "Icona",
            modifier = Modifier
                .size(40.dp) // Imposta le dimensioni iniziali dell'immagine
                .padding(
                    top = 10.dp,
                    bottom = 8.dp
                )

        )
        Row(
            modifier = Modifier.weight(1.5f),
            verticalAlignment = Alignment.CenterVertically
        ) {}

        // Centra il testo all'interno della Row
        Row(
            modifier = Modifier.clickable { navController.navigate(setting.rotta.route) }
                .weight(12f),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = setting.label,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    top = 10.dp,
                    bottom = 8.dp
                )

            )
        }

    }

}

@Preview
@Composable
fun PreviewImpostazioni() {
    Impostazioni(navController = rememberNavController())
}
