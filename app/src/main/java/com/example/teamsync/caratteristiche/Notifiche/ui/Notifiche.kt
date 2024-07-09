package com.example.teamsync.caratteristiche.Notifiche.ui

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.Notifiche.data.model.Notifiche
import com.example.teamsync.caratteristiche.Notifiche.data.viewModel.ViewModelNotifiche
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.ui.theme.Red70
import com.example.teamsync.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    viewModel: ViewModelUtente,
    navController: NavHostController,
    notificheModel: ViewModelNotifiche = viewModel()
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Non lette", "Tutte")
    val userProfile = viewModel.userProfile
    val notificheList by remember { notificheModel.notificheList }

    LaunchedEffect(Unit) {
        notificheModel.fetchNotifiche()
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Notifiche",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                },
                actions = {
                    IconButton(onClick = {
                        // Aggiorna l'elenco delle notifiche
                        notificheModel.fetchNotifiche()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh Notifications",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black,
                )
            )
        },

    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            // Tabs
            TabRow(
                selectedTabIndex = selectedTab,
                indicator = @Composable { posizioneTab ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier
                            .tabIndicatorOffset(posizioneTab[selectedTab])
                            .height(4.dp),
                        color = Red70
                    )

                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = {
                            selectedTab = index
                        },
                        text = { Text(text = title) },
                        selectedContentColor = Red70,
                        unselectedContentColor = Color.Black
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

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(notificheList.filter { notifica ->
                    when (selectedTab) {
                        0 -> !notifica.aperto // Non lette
                        1 -> true // Tutte
                        else -> true // Fallback
                    }
                }) { notifica ->
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
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        onClick = {
            apertura.value = true
            when (notifica.Tipo) {
                "Richiesta_Amicizia" -> navController.navigate("utente/${notifica.mittente}/false/notifica")
                "Accetta_Amicizia" -> navController.navigate(Schermate.Profilo.route)
            }
        },
        colors = CardDefaults.outlinedCardColors(
            containerColor = White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
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
}
