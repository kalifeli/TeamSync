package com.example.teamsync.navigation


import Termini
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.teamsync.caratteristiche.LeMieAttivita.data.viewModel.LeMieAttivitaViewModel
import com.example.teamsync.caratteristiche.LeMieAttivita.ui.LeMieAttivitaUI
import com.example.teamsync.caratteristiche.faq.ui.Faq
import com.example.teamsync.caratteristiche.faq.ui.Supporto
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
import com.example.teamsync.caratteristiche.iTuoiProgetti.ui.ITuoiProgetti
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente
import com.example.teamsync.caratteristiche.login.ui.LoginScreen
import com.example.teamsync.caratteristiche.login.ui.PasswordDimenticata
import com.example.teamsync.caratteristiche.login.ui.VerificaEmail
import com.example.teamsync.screen.Impostazioni
import com.example.teamsync.screen.Registrazione
import com.example.teamsync.screen.SchermataDiBenvenuto
import com.example.teamsync.screen.Start
import com.example.teamsync.screen.Tema
import com.example.teamsync.screen.UserProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(){
    val navController = rememberNavController()
    val viewModelUtente = ViewModelUtente()
    val viewModelProgetto = ViewModelProgetto()
    val viewModelLeMieAttivita = LeMieAttivitaViewModel()
    NavHost(navController = navController, startDestination = Schermate.Login.route) {

        composable(route = Schermate.Registrazione.route){ Registrazione(navController,viewModelUtente)}
        composable(route = Schermate.VerificaEmail.route){ VerificaEmail(navController)}
        composable(route = Schermate.Benvenuto.route){ SchermataDiBenvenuto(navController, viewModelUtente) }

        composable(route = Schermate.Login.route) { LoginScreen( navController, viewModelUtente, viewModelProgetto) }
        composable(route = Schermate.RecuperoPassword.route) { PasswordDimenticata(navController, viewModelUtente)}


        composable(route = Schermate.ItuoiProgetti.route){ ITuoiProgetti(navController, viewModelProgetto, viewModelUtente) }

        composable(route = Schermate.LeMieAttivita.route) { LeMieAttivitaUI(navController, viewModelLeMieAttivita) }
        composable(route = Schermate.Inizio.route) { Start(navController) }


        composable(route = Schermate.ModificaProfilo.route){ UserProfileScreen(viewModelUtente,navController)}
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


  