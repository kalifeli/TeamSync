package com.example.teamsync.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente
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
    val viewModelUtente = ViewModelUtente()

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
        composable(route = Schermate.Login.route) { LoginScreen(
            navController = navController,
            viewModelUtente = viewModelUtente
        )}
        composable(route = Schermate.Registrazione.route){ Registrazione(navController,viewModelUtente)}
        composable(route = Schermate.ModificaProfilo.route){ UserProfileScreen()}
        composable(route= Schermate.Impostazioni.route){Impostazioni(navController)}

    }
}


  