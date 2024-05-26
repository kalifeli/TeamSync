package com.example.teamsync.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.R
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.ui.theme.Grey20
import com.example.teamsync.ui.theme.Red70
import com.example.teamsync.ui.theme.White

data class SettingItem(val icon: ImageVector, val label: String)

val settingsList = listOf(
    SettingItem( Icons.Default.Settings, "Modifica Profilo"),
    SettingItem(Icons.Default.Settings, "Notifiche"),
    SettingItem(Icons.Default.Settings, "Lingua"),
    SettingItem(Icons.Default.Settings, "DashBoard"),
    SettingItem(Icons.Default.Settings, "Task"),
    SettingItem(Icons.Default.Settings, "To-Do List"),
    SettingItem(Icons.Default.Settings, "Help & Support"),
    SettingItem(Icons.Default.Settings, "Report a problem"),
    SettingItem(Icons.Default.Settings, "Log out")
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

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.08f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                FloatingActionButton(
                    onClick = { navController.navigate(Schermate.Impostazioni.route) },
                    modifier = Modifier
                        .padding(5.dp)
                        .size(35.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "add project",
                        tint = Color.Red,


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
                        .fillMaxHeight(0.05f)
                        .background(color = Color.LightGray),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {

                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "ModificaProfilo",
                        tint = Color.Black,
                    )


                    // Centra il testo all'interno della Row
                    Row(
                        modifier = Modifier.clickable { navController.navigate(Schermate.ModificaProfilo.route) }
                            .weight(8f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Impostazioni",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,

                            )
                    }

                }

                }
            }
        }
    }



@Composable
fun SettingItemRow(setting: SettingItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { /* Handle item click */ }
    ) {
        Icon(imageVector = setting.icon, contentDescription = null)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = setting.label)
    }
}

@Preview
@Composable
fun PreviewImpostazioni() {
    Impostazioni(navController = rememberNavController())
}
