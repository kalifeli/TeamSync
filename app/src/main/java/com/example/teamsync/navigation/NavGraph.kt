package com.example.teamsync.navigation


import Termini
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.teamsync.caratteristiche.LeMieAttivita.data.viewModel.LeMieAttivitaViewModel
import com.example.teamsync.caratteristiche.LeMieAttivita.ui.LeMieAttivitaUI
import com.example.teamsync.caratteristiche.Notifiche.data.repository.RepositoryNotifiche
import com.example.teamsync.caratteristiche.Profilo.ProfiloSchermata
import com.example.teamsync.caratteristiche.Profilo.ProfiloUtenteCliccato
import com.example.teamsync.caratteristiche.Notifiche.ui.NotificationScreen
import com.example.teamsync.caratteristiche.faq.ui.Faq
import com.example.teamsync.caratteristiche.faq.ui.Supporto
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
import com.example.teamsync.caratteristiche.iTuoiProgetti.ui.ITuoiProgetti
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente
import com.example.teamsync.caratteristiche.login.ui.LoginScreen
import com.example.teamsync.caratteristiche.login.ui.PasswordDimenticata
import com.example.teamsync.caratteristiche.login.ui.VerificaEmail
import com.example.teamsync.screen.BottomNav
import com.example.teamsync.screen.Impostazioni
import com.example.teamsync.screen.Registrazione
import com.example.teamsync.screen.SchermataDiBenvenuto
import com.example.teamsync.screen.Tema
import com.example.teamsync.screen.UserProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(){
    val navController = rememberNavController()
    val viewmodel = ViewModelUtente()

    val viewModelUtente = ViewModelUtente()
    val viewModelProgetto = ViewModelProgetto()
    val viewModelLeMieAttivita = LeMieAttivitaViewModel()
    NavHost(navController = navController, startDestination = Schermate.Login.route) {

        composable(route = Schermate.Registrazione.route){ Registrazione(navController,viewModelUtente)}
        composable(route = Schermate.VerificaEmail.route){ VerificaEmail(navController)}
        composable(route = Schermate.Benvenuto.route){ SchermataDiBenvenuto(navController, viewModelUtente) }

        composable(route = Schermate.Login.route) { LoginScreen( navController, viewModelUtente, viewModelProgetto) }
        composable(route = Schermate.RecuperoPassword.route) { PasswordDimenticata(navController, viewModelUtente)}


        composable(route = Schermate.ItuoiProgetti.route){
            Scaffold (
                bottomBar = {
                    BottomNav(navController = navController)
                }
            ){innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)){
                    ITuoiProgetti(navController, viewModelProgetto, viewModelUtente)
                }
            }
        }
        composable(route = Schermate.Notifiche.route) {
            Scaffold (
                bottomBar = {
                    BottomNav(navController = navController)
                }
            ){innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)){
                    NotificationScreen(viewmodel, navController)
                }
            }

        }
        composable(route = Schermate.Profilo.route) {
            Scaffold (
                bottomBar = {
                    BottomNav(navController = navController)
                }
            ){innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)){
                    ProfiloSchermata(viewmodel, navController)
                }
            }
        }

        composable(route = Schermate.ModificaProfilo.route){ UserProfileScreen(viewModelUtente,navController)}
        composable(route = Schermate.LeMieAttivita.route) { LeMieAttivitaUI(navController, viewModelLeMieAttivita) }
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


            Faq(navController = navController, sezioneFaq = projectId ?: "")
        }

        composable(
            route = "utente/{id}/{amicizia}/{provenienza}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
                navArgument("amicizia") { type = NavType.StringType },
                navArgument("provenienza") { type = NavType.StringType },// Aggiungi qui l'argomento "amicizia"
            )
        ) { backStackEntry ->
            // Recupera i parametri "id" e "amicizia" dalla rotta
            val id_u = backStackEntry.arguments?.getString("id")
            val amici = backStackEntry.arguments?.getString("amicizia")
            val provenienza = backStackEntry.arguments?.getString("provenienza")


            if (id_u != null) {
                if (provenienza != null) {
                    ProfiloUtenteCliccato(viewModel = viewmodel, navController = navController, id = id_u, amicizia = amici.toString(), provenienza = provenienza, notificheRepo = RepositoryNotifiche())
                }
            }
        }

    }
}


  