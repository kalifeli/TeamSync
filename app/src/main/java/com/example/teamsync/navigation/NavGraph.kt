package com.example.teamsync.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.screen.Progetti
import com.example.teamsync.screen.SchermataDiBenvenuto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Schermate.Iscrizione.route) {
        composable(route = Schermate.Iscrizione.route){

        }
        composable(route = Schermate.Accesso.route){

        }
        composable(route = Schermate.Benvenuto.route){
            SchermataDiBenvenuto(navController = navController)
        }
        composable(route = Schermate.Progetti.route){
            Progetti()
        }

    }
}