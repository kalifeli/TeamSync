package com.example.teamsync.caratteristiche.leMieAttivita.data.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.teamsync.caratteristiche.autentificazione.data.repository.RepositoryUtente
import com.example.teamsync.caratteristiche.leMieAttivita.data.model.LeMieAttivita
import com.example.teamsync.caratteristiche.leMieAttivita.data.repository.ToDoRepository
import com.example.teamsync.util.Priorita
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat

@OptIn(ExperimentalCoroutinesApi::class)
class LeMieAttivitaViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repositoryLeMieAttivita : ToDoRepository
    private lateinit var repositoryUtente: RepositoryUtente
    private lateinit var leMieAttivitaViewModel: LeMieAttivitaViewModel


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repositoryLeMieAttivita = mockk(relaxed = true)
        repositoryUtente = mockk(relaxed = true)
        leMieAttivitaViewModel = LeMieAttivitaViewModel(repositoryLeMieAttivita, repositoryUtente)
    }

    @Test
    fun `addTodo aggiunge una nuova attivita con successo`() = runTest {
        //configurazione delle variabili per il test
        val titolo = "aggiungi attività con successo"
        val descrizione = "descrizione aggiungi attività con successo"
        val dataScadenza = SimpleDateFormat("dd/MM/yyyy").parse("10/10/2024")!!
        val priorita = Priorita.ALTA
        val proprietarioID = "proprietarioID"
        val progettoID = "progettoID"
        val dataScadenzaProgetto = SimpleDateFormat("dd/MM/yyyy").parse("20/10/2024")!!
        val sezione = 1

        // mock delle dipendenze
        coEvery { repositoryLeMieAttivita.addTodo(titolo, descrizione, dataScadenza, priorita, proprietarioID, progettoID) } returns Unit

        // Chiamata del metodo del viewModel
        leMieAttivitaViewModel.addTodo(titolo, descrizione, dataScadenza, priorita, proprietarioID, progettoID, sezione, dataScadenzaProgetto)
        advanceUntilIdle()

        //Verifica del comportamento
        coVerify { repositoryLeMieAttivita.addTodo(titolo, descrizione, dataScadenza, priorita, proprietarioID, progettoID) }
    }

    @Test
    fun `addTodo non aggiunge una nuova attivita a causa del titolo vuoto`() = runTest {
        //configurazione delle variabili per il test
        val titolo = ""
        val descrizione = "descrizione aggiungi attività con successo"
        val dataScadenza = SimpleDateFormat("dd/MM/yyyy").parse("10/10/2024")!!
        val priorita = Priorita.ALTA
        val proprietarioID = "proprietarioID"
        val progettoID = "progettoID"
        val sezione = 1
        val dataScadenzaProgetto = SimpleDateFormat("dd/MM/yyyy").parse("20/10/2024")!!

        // mock delle dipendenze
        coEvery { repositoryLeMieAttivita.addTodo(titolo, descrizione, dataScadenza, priorita, proprietarioID, progettoID) } returns Unit

        // Chiamata del metodo del viewModel
        leMieAttivitaViewModel.addTodo(titolo, descrizione, dataScadenza, priorita, proprietarioID, progettoID, sezione, dataScadenzaProgetto)
        advanceUntilIdle()

        //Verifica del comportamento
        // Qui inutile testare la chiamata del metodo addTodo della repository perchè non viene chiamato
        assert(leMieAttivitaViewModel.erroreAggiungiAttivita.value == "AGGIUNGI RIFIUTATO!!!: Il titolo non può essere omesso.")
    }

    @Test
    fun `deleteTodo elimina correttamente un'attivita` () = runTest {
        //configurazione delle variabili per il test
        val taskId = "taskId"
        val sezione = 1
        val progettoId = "progettoId"

        // mock delle dipendenze
        coEvery { repositoryLeMieAttivita.deleteTodo(taskId) } returns Unit

        // Chiamata del metodo del viewModel
        leMieAttivitaViewModel.deleteTodo(taskId, sezione, progettoId)
        advanceUntilIdle()

        // Verifica del comportamento
        coVerify { repositoryLeMieAttivita.deleteTodo(taskId) }
    }

    @Test
    fun `completeTodo annulla correttamente il completamento di un'attivita`() = runTest {
        // Configurazione delle variabili per il test
        val idTask = "taskId"
        val completato = true
        val sezione = 1
        val progetto = "progettoId"

        // Mock delle dipendenze
        coEvery { repositoryLeMieAttivita.completeTodo(idTask, completato) } returns Unit

        // Esecuzione del metodo per completare un'attività
        leMieAttivitaViewModel.completeTodo(idTask, completato, sezione, progetto)
        advanceUntilIdle()

        // Verifica del comportamento
        coVerify { repositoryLeMieAttivita.completeTodo(idTask, completato) }
    }
    @Test
    fun `completeTodo completa correttamente un'attivita`() = runTest {
        // Configurazione delle variabili per il test
        val idTask = "taskId"
        val completato = false
        val sezione = 1
        val progetto = "progettoId"

        // Mock delle dipendenze
        coEvery { repositoryLeMieAttivita.completeTodo(idTask, completato) } returns Unit

        // Esecuzione del metodo per completare un'attività
        leMieAttivitaViewModel.completeTodo(idTask, completato, sezione, progetto)
        advanceUntilIdle()

        // Verifica del comportamento
        coVerify { repositoryLeMieAttivita.completeTodo(idTask, completato) }
    }

    @Test
    fun `getTodoById recupera correttamente un'attivita dato il suo ID`() = runTest {
        // Configurazione delle variabili per il test
        val taskId = "taskId"
        val laMiaAttivita = LeMieAttivita(
            id = taskId,
            titolo = "titolo task",
            descrizione = "desrizione task",
            dataScadenza = SimpleDateFormat("dd/MM/yyyy").parse("10/10/2024")!!,
            priorita = Priorita.ALTA,
            progetto = "progettoId",
            utenti = listOf(),
            completato = false
        )

        // Mock delle dipendenze
        coEvery { repositoryLeMieAttivita.getTodoById(taskId) } returns laMiaAttivita

        // Esecuzione del metodo per recuperare un'attività
        val risultato = leMieAttivitaViewModel.getTodoById(taskId)
        advanceUntilIdle()

        // Verifica del comportamento
        assert(risultato == laMiaAttivita)
    }

    @Test
    fun `getTodoById non recupera correttamente un'attivita`() = runTest {
        // Configurazione delle variabili per il test
        val taskId = "taskId"

        // Mock delle dipendenze
        coEvery { repositoryLeMieAttivita.getTodoById(taskId) } returns null

        // Esecuzione del metodo per recuperare un'attività
        val risultato = leMieAttivitaViewModel.getTodoById(taskId)
        advanceUntilIdle()

        // Verifica del comportamento
        assert(risultato == null)
    }

}