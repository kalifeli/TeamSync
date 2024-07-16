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
import com.example.teamsync.caratteristiche.LeMieAttivita.ui.Lista_Utenti_assegna_Task
import com.example.teamsync.caratteristiche.LeMieAttivita.ui.SchermataModificaProgetto
import com.example.teamsync.caratteristiche.Notifiche.data.repository.RepositoryNotifiche
import com.example.teamsync.caratteristiche.Notifiche.data.viewModel.ViewModelNotifiche
import com.example.teamsync.caratteristiche.Notifiche.ui.NotificationScreen
import com.example.teamsync.caratteristiche.Profilo.ProfiloUtenteCliccato
import com.example.teamsync.caratteristiche.ProfiloAmici.ProfiloSchermata
import com.example.teamsync.caratteristiche.ProfiloAmici.Progetto
import com.example.teamsync.caratteristiche.faq.ui.Faq
import com.example.teamsync.caratteristiche.faq.ui.Supporto
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
import com.example.teamsync.caratteristiche.iTuoiProgetti.ui.ITuoiProgetti
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente
import com.example.teamsync.caratteristiche.login.ui.LoginScreen
import com.example.teamsync.caratteristiche.login.ui.PasswordDimenticata
import com.example.teamsync.caratteristiche.login.ui.VerificaEmail
import com.example.teamsync.screen.BottomNav
import com.example.teamsync.screen.ImpoProgetti
import com.example.teamsync.screen.ImpoTask
import com.example.teamsync.screen.Impostazioni
import com.example.teamsync.screen.NotificheImp
import com.example.teamsync.screen.Registrazione
import com.example.teamsync.screen.SchermataDiBenvenuto
import com.example.teamsync.screen.Tema
import com.example.teamsync.screen.UserProfileScreen

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun NavGraph(){
    val navController = rememberNavController()
    val viewModelUtente = ViewModelUtente()
    val viewModelProgetto = ViewModelProgetto()
    val viewModelLeMieAttivita = LeMieAttivitaViewModel()
    val viewModelNotifiche = ViewModelNotifiche()

    NavHost(navController = navController, startDestination = Schermate.Login.route) {

        composable(route = Schermate.Registrazione.route){ Registrazione(navController,viewModelUtente)}
        composable(route = Schermate.VerificaEmail.route){ VerificaEmail(navController)}
        composable(route = Schermate.Benvenuto.route){ SchermataDiBenvenuto(navController, viewModelUtente) }
        composable(route = Schermate.Login.route) { LoginScreen( navController, viewModelUtente, viewModelProgetto) }
        composable(route = Schermate.RecuperoPassword.route) { PasswordDimenticata(navController, viewModelUtente)}
        composable(route = Schermate.ModificaProfilo.route){ UserProfileScreen(viewModelUtente,navController)}
        composable(route= Schermate.Impostazioni.route){Impostazioni(navController)}
        composable(route = Schermate.Tema.route){ Tema(navController) }
        composable(route = Schermate.Terms.route) {Termini(navController)}
        composable(route = Schermate.Supporto.route) { Supporto(navController)}
        composable(route = Schermate.ImpNotifche.route) { NotificheImp(navController = navController)}
        composable(route = Schermate.Imptask.route) { ImpoTask(navController = navController)}
        composable(route = Schermate.ImpoProgetti.route) { ImpoProgetti(navController = navController)}

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
                    NotificationScreen(viewModelUtente, navController)
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
                    ProfiloSchermata(viewModelUtente, navController, viewModelProgetto,viewModelLeMieAttivita)
                }
            }
        }

        composable(
            route = "faq/{sezioneFaq}",
            arguments = listOf(navArgument("sezioneFaq") { type = NavType.StringType })
        ) { backStackEntry ->
            // Recupera il parametro projectId dalla rotta
            val projectId = backStackEntry.arguments?.getString("sezioneFaq")
            Faq(navController = navController, sezioneFaq = projectId ?: "")
        }


        composable(
            route = "utente/{id}/{amicizia}/{provenienza}/{id_task}/{id_progetto}/{sottoprovenienza}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
                navArgument("amicizia") { type = NavType.StringType },
                navArgument("provenienza") { type = NavType.StringType },
                navArgument("id_task") { type = NavType.StringType },
                navArgument("id_progetto") { type = NavType.StringType },
                navArgument("sottoprovenienza") { type = NavType.StringType }// Aggiungi qui l'argomento "amicizia"
            )
        ) { backStackEntry ->
            // Recupera i parametri "id" e "amicizia" dalla rotta
            val id_u = backStackEntry.arguments?.getString("id")
            val amici = backStackEntry.arguments?.getString("amicizia")
            val provenienza = backStackEntry.arguments?.getString("provenienza")
            val task = backStackEntry.arguments?.getString("id_task")
            val pro = backStackEntry.arguments?.getString("id_progetto")
            val sottoprovenienza = backStackEntry.arguments?.getString("sottoprovenienza")

            if (id_u != null) {
                if (provenienza != null) {
                    if (task != null) {
                        if (pro != null) {
                            if (sottoprovenienza != null) {
                                ProfiloUtenteCliccato(viewModel = viewModelUtente, navController = navController, id = id_u, amicizia = amici.toString(), provenienza = provenienza, notificheRepo = RepositoryNotifiche(), task = task, progetto = pro,viewModelProgetto, viewModelNotifiche, sottoprovenienza)
                            }
                        }
                    }
                }
            }
        }


        composable(
            route = "task_selezionata/{id_task}/{id_prog}",
            arguments = listOf(
                navArgument("id_task") { type = NavType.StringType },
                navArgument("id_prog") { type = NavType.StringType })
        ) { backStackEntry ->
            // Recupera il parametro projectId dalla rotta
            val task = backStackEntry.arguments?.getString("id_task")
            val prog = backStackEntry.arguments?.getString("id_prog")

            if (task != null) {
                if (prog != null) {
                    Lista_Utenti_assegna_Task(
                        viewModel = viewModelUtente,
                        navController = navController,
                        viewModel_att = viewModelLeMieAttivita ,
                        id_task = task,
                        view_model_prog =viewModelProgetto ,
                        id_prog = prog,
                        view_model_notifiche = viewModelNotifiche
                    )
                }
            }

        }


        composable(
            route = "progetto/{id_prog}",
            arguments = listOf(navArgument("id_prog") { type = NavType.StringType })
        ) { backStackEntry ->
            // Recupera il parametro projectId dalla rotta
            val projectId = backStackEntry.arguments?.getString("id_prog")

            if (projectId != null) {
                LeMieAttivitaUI(navController, viewModelLeMieAttivita,viewModelUtente,viewModelProgetto,projectId, viewModelNotifiche)
            }
        }

        composable(
            route = "progetto_da_accettare/{id_prog}/{provenienza}/{sottoprovenienza}",
            arguments = listOf(
                navArgument("id_prog") { type = NavType.StringType },
                navArgument("provenienza") { type = NavType.StringType },
                navArgument("sottoprovenienza") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val projectId = backStackEntry.arguments?.getString("id_prog")
            val provenienza = backStackEntry.arguments?.getString("provenienza")
            val sottoprovenienza = backStackEntry.arguments?.getString("sottoprovenienza")

            if (projectId != null) {
                if (provenienza != null) {
                    if (sottoprovenienza != null) {
                        Progetto(
                            viewModel = viewModelUtente,
                            navController = navController,
                            viewModel_att = viewModelLeMieAttivita,
                            view_model_prog = viewModelProgetto,
                            id_prog = projectId,
                            viewNotifiche = viewModelNotifiche,
                            provenienza = provenienza,
                            sottoprovenienza = sottoprovenienza,
                        )
                    }
                }
            }
        }

        composable(
            route = "modificaProgetto/{progettoId}" ,
            arguments = listOf(navArgument("progettoId") { type = NavType.StringType })
        ) { backStackEntry ->
            val progettoId = backStackEntry.arguments?.getString("progettoId") ?: return@composable
            SchermataModificaProgetto(
                viewModelProgetto = viewModelProgetto,
                navController = navController,
                progettoId = progettoId,
            )
        }


    }
}



