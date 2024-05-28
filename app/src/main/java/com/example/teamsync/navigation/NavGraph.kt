package com.example.teamsync.navigation

import UserProfileViewModel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.screen.Impostazioni
import com.example.teamsync.caratteristiche.login.ui.LoginScreen
import com.example.teamsync.screen.Progetti
import com.example.teamsync.screen.Registrazione
import com.example.teamsync.screen.SchermataDiBenvenuto
import com.example.teamsync.screen.Start
import com.example.teamsync.screen.UserProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(){
    val navController = rememberNavController()
    val viewmodel = UserProfileViewModel("ID_2")
    NavHost(navController = navController, startDestination = Schermate.Inizio.route) {
        composable(route = Schermate.Iscrizione.route){

        }
        composable(route = Schermate.Accesso.route){

        }
        composable(route = Schermate.Benvenuto.route){
            SchermataDiBenvenuto(navController)
        }
        composable(route = Schermate.Progetti.route){
            Progetti(navController)
        }

        composable(route = Schermate.Inizio.route) { Start(navController) }
        composable(route = Schermate.Login.route) { LoginScreen( navController) }
        composable(route = Schermate.Registrazione.route){ Registrazione(navController)}
        composable(route = Schermate.ModificaProfilo.route){ UserProfileScreen(viewmodel,navController)}
        composable(route= Schermate.Impostazioni.route){Impostazioni(navController)}

    }
}


  