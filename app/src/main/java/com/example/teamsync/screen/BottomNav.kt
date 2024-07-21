package com.example.teamsync.screen

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.Notifiche.data.viewModel.ViewModelNotifiche
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.ui.theme.Red70
import com.example.teamsync.ui.theme.White
import com.example.teamsync.util.ThemePreferences

@Composable
fun BottomNav(
    navController: NavHostController,
    notificheViewModel: ViewModelNotifiche,

) {
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    NavigationBar(
        containerColor = if(isDarkTheme) Color.Black else White,
        tonalElevation = 16.dp,
        modifier = Modifier
            .height(60.dp)
    ) {

        val rottaCorrente = navController.currentBackStackEntryAsState().value?.destination?.route
        val notificheNonLette by notificheViewModel.notificheNonLette.observeAsState(false)

        NavigationBarItem(
            selected = rottaCorrente == Schermate.ItuoiProgetti.route,
            onClick = {
                if(rottaCorrente != Schermate.ItuoiProgetti.route){
                    navController.navigate(Schermate.ItuoiProgetti.route){
                        launchSingleTop = true
                        restoreState = true
                    }
                }
                      },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "icona i tuoi progetti",
                )
                   },
            label = {
                Text(text = "Home")
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent,
                selectedTextColor = Red70,
                unselectedTextColor = if(isDarkTheme) White else Color.Black,
                unselectedIconColor = if(isDarkTheme) White else Color.Black,
                selectedIconColor = Red70
            ),
        )
        NavigationBarItem(
            selected = rottaCorrente == Schermate.Notifiche.route,
            onClick = {
                if(rottaCorrente != Schermate.Notifiche.route){
                    navController.navigate(Schermate.Notifiche.route){
                        launchSingleTop = true
                        restoreState = true
                    }
                }
                      },
            icon = {
                Icon(
                    painter = painterResource(
                        if (notificheNonLette) R.drawable.ic_notifichenonaperte else R.drawable.ic_notifiche
                    ),
                    contentDescription = "icona notifiche"
                )
                   },
            label = {
                Text(text = stringResource(id = R.string.notifiche))
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent,
                selectedTextColor = Red70,
                unselectedTextColor = if(isDarkTheme) White else Color.Black,
                unselectedIconColor = if(isDarkTheme) White else Color.Black,
                selectedIconColor = Red70
            ),
        )
        NavigationBarItem(
            selected = rottaCorrente == Schermate.Profilo.route,
            onClick = {
                if(rottaCorrente != Schermate.Profilo.route){
                    navController.navigate(Schermate.Profilo.route){
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "icona profilo"
                )
                   },
            label = {
                Text(text = stringResource(id = R.string.profilo))
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent,
                selectedTextColor = Red70,
                unselectedTextColor = if(isDarkTheme) White else Color.Black,
                unselectedIconColor = if(isDarkTheme) White else Color.Black,
                selectedIconColor = Red70
            ),
        )
    }
}
