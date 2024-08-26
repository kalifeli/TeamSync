package com.example.teamsync.navigation


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.teamsync.caratteristiche.leMieAttivita.data.repository.ToDoRepository
import com.example.teamsync.caratteristiche.leMieAttivita.data.viewModel.LeMieAttivitaViewModel
import com.example.teamsync.caratteristiche.leMieAttivita.ui.LeMieAttivitaUI
import com.example.teamsync.caratteristiche.leMieAttivita.ui.DelegaTask
import com.example.teamsync.caratteristiche.leMieAttivita.ui.SchermataModificaProgetto
import com.example.teamsync.caratteristiche.notifiche.data.repository.RepositoryNotifiche
import com.example.teamsync.caratteristiche.notifiche.data.viewModel.ViewModelNotifiche
import com.example.teamsync.caratteristiche.notifiche.ui.NotificationScreen
import com.example.teamsync.caratteristiche.profilo.ProfiloUtenteCliccato
import com.example.teamsync.caratteristiche.profilo.ProfiloSchermata
import com.example.teamsync.caratteristiche.iTuoiProgetti.ui.Progetto
import com.example.teamsync.caratteristiche.impostazioni.ui.Faq
import com.example.teamsync.caratteristiche.impostazioni.ui.Supporto
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.repository.RepositoryProgetto
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
import com.example.teamsync.caratteristiche.iTuoiProgetti.ui.ITuoiProgetti
import com.example.teamsync.caratteristiche.autentificazione.data.repository.RepositoryUtente
import com.example.teamsync.caratteristiche.autentificazione.data.viewModel.ViewModelUtente
import com.example.teamsync.caratteristiche.autentificazione.ui.LoginScreen
import com.example.teamsync.caratteristiche.autentificazione.ui.PasswordDimenticata
import com.example.teamsync.caratteristiche.autentificazione.ui.VerificaEmail
import com.example.teamsync.caratteristiche.impostazioni.ui.TerminiCondizioniScreen
import com.example.teamsync.caratteristiche.impostazioni.ui.ImpoProgetti
import com.example.teamsync.caratteristiche.impostazioni.ui.ImpoTask
import com.example.teamsync.caratteristiche.impostazioni.ui.Impostazioni
import com.example.teamsync.caratteristiche.impostazioni.ui.NotificheImp
import com.example.teamsync.caratteristiche.autentificazione.ui.Registrazione
import com.example.teamsync.caratteristiche.benvenuto.ui.SchermataDiBenvenuto
import com.example.teamsync.caratteristiche.impostazioni.data.repository.RepositoryFaq
import com.example.teamsync.caratteristiche.impostazioni.data.repository.RepositoryTerms
import com.example.teamsync.caratteristiche.impostazioni.data.viewModel.FaqViewModel
import com.example.teamsync.caratteristiche.impostazioni.data.viewModel.TerminiCondizioniViewModel
import com.example.teamsync.caratteristiche.impostazioni.ui.Tema
import com.example.teamsync.caratteristiche.impostazioni.ui.UserProfileScreen

/**
 * Composable che definisce la navigazione per l'applicazione.
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(){
    val contesto = LocalContext.current
    val navController = rememberNavController()
    val viewModelUtente = ViewModelUtente(RepositoryUtente(contesto), contesto)
    val viewModelProgetto = ViewModelProgetto(RepositoryProgetto(), ToDoRepository(), ViewModelUtente(RepositoryUtente(contesto), contesto), contesto)
    val viewModelLeMieAttivita = LeMieAttivitaViewModel(ToDoRepository(), RepositoryUtente(contesto))
    val viewModelNotifiche = ViewModelNotifiche(RepositoryNotifiche(), viewModelUtente, contesto)
    val terminiCondizioniViewModel = TerminiCondizioniViewModel(RepositoryTerms())
    val faqViewModel = FaqViewModel(RepositoryFaq())

    NavHost(navController = navController, startDestination = Schermate.Login.route) {

        composable(route = Schermate.Registrazione.route){ Registrazione(navController,viewModelUtente) }
        composable(route = Schermate.VerificaEmail.route){ VerificaEmail(navController)}
        composable(route = Schermate.Benvenuto.route){ SchermataDiBenvenuto(navController, viewModelUtente) }
        composable(route = Schermate.Login.route) { LoginScreen( navController, viewModelUtente, viewModelProgetto) }
        composable(route = Schermate.RecuperoPassword.route) { PasswordDimenticata(navController, viewModelUtente)}
        composable(route = Schermate.ModificaProfilo.route){ UserProfileScreen(viewModelUtente,navController) }
        composable(route= Schermate.Impostazioni.route){ Impostazioni(navController, viewModelUtente) }
        composable(route = Schermate.Tema.route){ Tema(navController) }
        composable(route = Schermate.Terms.route) { TerminiCondizioniScreen(navController, terminiCondizioniViewModel ) }
        composable(route = Schermate.Supporto.route) { Supporto(navController) }
        composable(route = Schermate.ImpNotifche.route) { NotificheImp(navController = navController) }
        composable(route = Schermate.Imptask.route) { ImpoTask(navController = navController) }
        composable(route = Schermate.ImpoProgetti.route) { ImpoProgetti(navController = navController) }

        composable(route = Schermate.ItuoiProgetti.route){
            Scaffold (
                bottomBar = {
                    BottomNav(navController = navController, viewModelNotifiche)
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
                    BottomNav(navController = navController, viewModelNotifiche)
                }
            ){innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)){
                    NotificationScreen(viewModelUtente, navController, viewModelNotifiche)
                }
            }
        }
        composable(route = Schermate.Profilo.route) {
            Scaffold (
                bottomBar = {
                    BottomNav(navController = navController, viewModelNotifiche)
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
            Faq(navController = navController, sezioneFaq = projectId ?: "", viewModel = faqViewModel)
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
            val idU = backStackEntry.arguments?.getString("id")
            val amici = backStackEntry.arguments?.getString("amicizia")
            val provenienza = backStackEntry.arguments?.getString("provenienza")
            val task = backStackEntry.arguments?.getString("id_task")
            val pro = backStackEntry.arguments?.getString("id_progetto")
            val sottoprovenienza = backStackEntry.arguments?.getString("sottoprovenienza")

            if (idU != null) {
                if (provenienza != null) {
                    if (task != null) {
                        if (pro != null) {
                            if (sottoprovenienza != null) {
                                ProfiloUtenteCliccato(
                                    viewModelUtente = viewModelUtente,
                                    leMieAttivitaViewModel = viewModelLeMieAttivita,
                                    viewModelprogetto = viewModelProgetto,
                                    viewModelNotifiche = viewModelNotifiche,
                                    navController = navController,
                                    id = idU,
                                    amicizia = amici.toString(),
                                    provenienza = provenienza,
                                    task = task,
                                    progetto = pro,
                                    sottoprovenienza = sottoprovenienza
                                )
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
                    DelegaTask(
                        viewModel = viewModelUtente,
                        navController = navController,
                        leMieAttivitaViewModel = viewModelLeMieAttivita ,
                        idTask = task,
                        viewModelProgetto =viewModelProgetto ,
                        idProg = prog,
                        viewModelNotifiche = viewModelNotifiche
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
                            viewModelUtente = viewModelUtente,
                            navController = navController,
                            viewModelProgetto = viewModelProgetto,
                            idProg = projectId,
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



