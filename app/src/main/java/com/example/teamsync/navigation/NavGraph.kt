package com.example.teamsync.navigation


import Termini
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.teamsync.caratteristiche.faq.ui.Faq
import com.example.teamsync.caratteristiche.faq.ui.Supporto
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente
import com.example.teamsync.caratteristiche.login.ui.LoginScreen
import com.example.teamsync.screen.Impostazioni
import com.example.teamsync.screen.Progetti
import com.example.teamsync.screen.Registrazione
import com.example.teamsync.screen.SchermataDiBenvenuto
import com.example.teamsync.screen.Start
import com.example.teamsync.screen.Tema
import com.example.teamsync.screen.UserProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(){
    val navController = rememberNavController()
    val viewmodel = ViewModelUtente()
    NavHost(navController = navController, startDestination = Schermate.Login.route) {

        composable(route = Schermate.Registrazione.route){ Registrazione(navController,viewmodel)}
        composable(route = Schermate.Benvenuto.route){ SchermataDiBenvenuto(navController) }

        composable(route = Schermate.Login.route) { LoginScreen( navController, viewmodel) }

        composable(route = Schermate.Progetti.route){ Progetti(navController) }

        composable(route = Schermate.Inizio.route) { Start(navController) }


        composable(route = Schermate.ModificaProfilo.route){ UserProfileScreen(viewmodel,navController)}
        composable(route= Schermate.Impostazioni.route){Impostazioni(navController)}
        composable(route = Schermate.Tema.route){ Tema(navController)}
        composable(route = Schermate.Terms.route) {Termini(navController)}
        composable(route = Schermate.Supporto.route) { Supporto(navController)}

        // Composable per la nuova schermata DettaglioProgetto
        composable(
            route = "dettaglio_progetto/{sezioneFaq}",
            arguments = listOf(navArgument("sezioneFaq") { type = NavType.StringType })
        ) { backStackEntry ->
            // Recupera il parametro projectId dalla rotta
            val projectId = backStackEntry.arguments?.getString("sezioneFaq")

            // Chiamata alla schermata DettaglioProgetto con il parametro
            Faq(navController = navController, sezioneFaq = projectId ?: "")
        }
    }
}


  