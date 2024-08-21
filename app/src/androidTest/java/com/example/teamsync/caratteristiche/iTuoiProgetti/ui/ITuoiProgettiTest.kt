package com.example.teamsync.caratteristiche.iTuoiProgetti.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.teamsync.caratteristiche.autentificazione.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.autentificazione.data.viewModel.ViewModelUtente
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ITuoiProgettiTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModelUtente: ViewModelUtente

    @Before
    fun setup() {
        val mockRepositoryUtente = mockk<com.example.teamsync.caratteristiche.autentificazione.data.repository.RepositoryUtente>()

        // Mock the ViewModel behavior
        coEvery { mockRepositoryUtente.getUserProfile(any()) } answers {
            ProfiloUtente(
                id = "1",
                nome = "Mario",
                cognome = "Rossi",
                email = "mario.rossi@example.com"
            )
        }

        viewModelUtente = ViewModelUtente(repositoryUtente = mockRepositoryUtente)
    }

    @Test
    fun testBottoniITuoiProgetti() = runBlocking {
        composeTestRule.setContent {
            ITuoiProgetti(
                navController = rememberNavController(),
                viewModelProgetto = ViewModelProgetto(
                    repositoryProgetto = mockk(),
                    repositoryLeMieAttivita = mockk(),
                    viewModelUtente = viewModelUtente
                ),
                viewModelUtente = viewModelUtente
            )
        }

        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithText("Stiamo preparando il tuo account per l'utilizzo...").fetchSemanticsNodes().isEmpty()
        }

        composeTestRule.onNodeWithText("Home").assertExists()
        composeTestRule.onNodeWithContentDescription("Aggiungi progetto").performClick()
    }
}




