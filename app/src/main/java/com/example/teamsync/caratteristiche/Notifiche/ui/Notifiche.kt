package com.example.teamsync.caratteristiche.Profilo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.Notifiche.data.model.Notifiche
import com.example.teamsync.caratteristiche.Notifiche.data.viewModel.ViewModelNotifiche
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente
import com.example.teamsync.navigation.Schermate

@Composable
fun NotificationScreen(
    viewModel: ViewModelUtente,
    navController: NavHostController,
    notificheModel: ViewModelNotifiche = viewModel()
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Non lette", "Tutte")
    val userProfile = viewModel.userProfile
    val notificheList by remember { notificheModel.notificheList }

    LaunchedEffect(Unit) {
        notificheModel.fetchNotifiche()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
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
                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .background(
                            Color.White,
                            RoundedCornerShape(20.dp)
                        ) // Imposta il rettangolo di sfondo a nero
                        .clickable { navController.navigate(Schermate.Profilo.route) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "close_impostazioni",
                        tint = Color.DarkGray // Assicurati che l'icona sia visibile impostando il colore a bianco
                    )
                }
                Row(
                    modifier = Modifier.weight(8f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Notifiche",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {}

                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        // Aggiorna l'elenco delle notifiche
                        notificheModel.fetchNotifiche()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh Notifications"
                        )
                    }
                }


            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tabs
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = {
                        selectedTab = index
                    },
                    text = { Text(text = title) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp)) // Spazio tra le tabs e l'indicatore di caricamento

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        )
        {
            if (notificheModel.isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.size(40.dp))

            }
        }




        Spacer(modifier = Modifier.height(16.dp))



        Column(modifier = Modifier.fillMaxWidth()) {
            notificheList.filter { notifica ->
                when (selectedTab) {
                    0 -> !notifica.aperto // Non lette
                    1 -> true // Tutte
                    else -> true // Fallback
                }
            }.forEach { notifica ->
                if (userProfile != null) {
                    NotificationItem(
                        iconColor = if (notifica.aperto) Color.Gray else if (notifica.Tipo == "Urgente") Color.Red else Color.Blue,
                        notifica = notifica,
                        navController = navController,
                        vmNotifiche = notificheModel
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
    }



@Composable
fun NotificationItem(iconColor: Color, notifica: Notifiche, navController: NavHostController, vmNotifiche : ViewModelNotifiche) {
    val apertura = remember { mutableStateOf(false) }

    // Esegui l'azione di apertura notifica quando apertura cambia a true
    LaunchedEffect(apertura.value) {
        if (apertura.value) {
            vmNotifiche.cambiaStatoNotifica(notifica.id)
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp))
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        apertura.value = true
                        when (notifica.Tipo) {
                            "Richiesta_Amicizia" -> navController.navigate("utente/${notifica.mittente}/false/notifica")
                            "Accetta_Amicizia" -> navController.navigate(Schermate.Profilo.route)
                        }
                    }
                )
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(iconColor, CircleShape)
                .padding(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon), // Sostituisci con l'icona della notifica
                contentDescription = "Notification Icon",
                tint = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = notifica.Contenuto,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
