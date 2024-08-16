package com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.teamsync.caratteristiche.autentificazione.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.leMieAttivita.data.repository.ToDoRepository
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.model.Progetto
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.repository.RepositoryProgetto
import com.example.teamsync.caratteristiche.autentificazione.data.repository.RepositoryUtente
import com.example.teamsync.caratteristiche.autentificazione.data.viewModel.ViewModelUtente
import com.example.teamsync.util.Priorita
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
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
import java.util.Date

@ExperimentalCoroutinesApi
class ViewModelProgettoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repositoryUtente: RepositoryUtente
    private lateinit var repositoryProgetto: RepositoryProgetto
    private lateinit var repositoryLeMieAttivita: ToDoRepository
    private lateinit var viewModelProgetto: ViewModelProgetto
    private lateinit var viewModelUtente: ViewModelUtente


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repositoryUtente = mockk(relaxed = true)
        repositoryProgetto = mockk(relaxed = true)
        repositoryLeMieAttivita = mockk(relaxed = true)

        // Mock del ViewModelUtente e il suo profilo utente
        viewModelUtente = mockk(relaxed = true)
        val mockUserProfile = mockk<ProfiloUtente>(relaxed = true) {
            every { id } returns "user1"
        }
        every { viewModelUtente.userProfilo } returns MutableLiveData(mockUserProfile)

        viewModelProgetto = ViewModelProgetto(repositoryProgetto, repositoryLeMieAttivita, viewModelUtente)
    }


    @Test
    fun `creaProgetto avvenuta con successo`() = runTest {
        // Configurazione delle variabili per il test
        val nome = "Progetto di Test"
        val descrizione = "Descrizione del Progetto di Test"
        val dataScadenza = SimpleDateFormat("dd/MM/yyyy").parse("25/09/2024")
        val priorita = Priorita.ALTA
        val codiceProgetto = "ABC12345"

        // Mock delle dipendenze
        coEvery { repositoryProgetto.generaCodiceProgetto() } returns codiceProgetto
        coEvery { repositoryProgetto.creaProgetto(any()) } returns "progettoId"

        // Esecuzione del metodo per la creazione di un progetto
        viewModelProgetto.creaProgetto(nome, descrizione, dataScadenza!!, priorita)
        advanceUntilIdle()

        // Verifica dei risultati
        assertNull("Errore: ${viewModelProgetto.erroreAggiungiProgetto.value}", viewModelProgetto.erroreAggiungiProgetto.value)
        assertTrue("Aggiunta progetto non riuscita", viewModelProgetto.aggiungiProgettoRiuscito.value)
    }


    @Test
    fun `creaProgetto fallita per dataScadenza non valida`() = runTest {
        // Configurazione delle variabili per il test
        val nome = "Progetto di Test"
        val descrizione = "Descrizione del Progetto di Test"
        val dataScadenza = SimpleDateFormat("dd/MM/yyyy").parse("31/07/2024")
        val priorita = Priorita.ALTA
        val codiceProgetto = "ABC12345"

        // Mock delle dipendenze
        coEvery { repositoryProgetto.generaCodiceProgetto() } returns codiceProgetto
        coEvery { repositoryProgetto.creaProgetto(any()) } returns "progettoId"

        // Esecuzione del metodo per la creazione di un progetto
        viewModelProgetto.creaProgetto(nome, descrizione, dataScadenza!!, priorita)
        advanceUntilIdle()

        // Verifica dei risultati
        assert(viewModelProgetto.erroreAggiungiProgetto.value == "La data di scadenza non può essere precedente alla data di creazione del progetto.")
        assert(!viewModelProgetto.aggiungiProgettoRiuscito.value)
    }

    @Test
    fun `aggiornaProgetto aggiorna correttamente un progetto esistente`() = runTest {
        // Configurazione delle variabili per il test
        val progettoId = "Progetto1Id"
        val nome = "Progetto1 aggiornato"
        val descrizione = "descrizione Progetto1 aggiornata"
        val dataScadenza = SimpleDateFormat("dd/MM/yyyy").parse("15/08/2024")
        val priorita = Priorita.ALTA
        val voto = 30
        val dataConsegna = Date()

        val progettoOriginale = Progetto(
            id = progettoId,
            nome =  "Progetto1",
            descrizione = "descrizione Progetto1",
            dataScadenza = dataScadenza!!,
            priorita = priorita,
            codice = "ABC12345",
            partecipanti = listOf("user1")
        )

        // Mock delle dipendenze
        coEvery { repositoryProgetto.getProgettoById(progettoId) } returns progettoOriginale
        coEvery { repositoryProgetto.aggiornaProgetto(any()) } returns Unit

        // Esecuzione del metodo per aggiornare il progetto
        viewModelProgetto.aggiornaProgetto(progettoId, nome, descrizione, dataScadenza, priorita, voto.toString(), dataConsegna)
        advanceUntilIdle()

        // Verifica che il metodo per aggiornare il progetto nella repository sia stato chiamato
        coVerify { repositoryProgetto.aggiornaProgetto(any()) }
    }

    @Test
    fun `aggiornaStatoProgetto aggiorna correttamente lo stato di un progetto`() = runTest {
        // Configurazione delle variabili per il test
        val progettoId = "Progetto1Id"
        val dataScadenza = SimpleDateFormat("dd/MM/yyyy").parse("15/08/2024")
        val priorita = Priorita.ALTA

        val progettoOriginale = Progetto(
            id = progettoId,
            nome =  "Progetto1",
            descrizione = "descrizione Progetto1",
            dataScadenza = dataScadenza!!,
            priorita = priorita,
            codice = "ABC12345",
            partecipanti = listOf("user1"),
            completato = false
        )

        // Mock delle dipendenze necessarie
        coEvery { repositoryProgetto.getProgettoById(progettoId) } returns progettoOriginale
        coEvery { repositoryProgetto.aggiornaProgetto(any()) } returns Unit

        // Esecuzione del metodo per aggiornare lo stato del progetto
        viewModelProgetto.aggiornaStatoProgetto(progettoId, true)
        advanceUntilIdle()

        // Verifica che il metodo della repository sia stato effettivamente chiamato
        coVerify { repositoryProgetto.aggiornaProgetto(any()) }
    }

    @Test
    fun `aggiungiPartecipanteConCodice aggiunge correttamente un partecipante ad un progetto `() = runTest {
        // Configurazione delle variabili per il test
        val userId = "utenteId"
        val progettoId = "progettoId"
        val dataScadenza = SimpleDateFormat("dd/MM/yyyy").parse("15/08/2024")
        val codice = "ABC12345"
        val progettiUtente = listOf<Progetto>()

        val progetto = Progetto(
            id = progettoId,
            nome = "progettoTest",
            descrizione = "descizione progettoTest",
            dataScadenza = dataScadenza!!,
            priorita = Priorita.ALTA,
            dataConsegna = Date(),
            partecipanti = listOf("userId1", "userId2"),
            codice = codice
        )

        // Mock delle dipendenze
        coEvery { repositoryProgetto.getProgettoIdByCodice(codice = codice) } returns progettoId
        coEvery { repositoryProgetto.getProgettiUtente(userId) } returns progettiUtente
        coEvery { repositoryProgetto.aggiungiPartecipante(progettoId, userId) } returns Unit

        // Esecuzione del metodo per aggiungere il partecipante ad un progetto tramite codice
        viewModelProgetto.aggiungiPartecipanteConCodice(userId, codice)
        advanceUntilIdle()

        // Verifica del comportamento
        coVerify { repositoryProgetto.aggiungiPartecipante(any(), any()) }
        assert(viewModelProgetto.aggiungiProgettoRiuscito.value)
        assert(viewModelProgetto.erroreAggiungiProgetto.value == null)
    }

    @Test
    fun `aggiungiPartecipanteConCodice non aggiunge un partecipante ad un progetto a causa del codice errato`() = runTest {
        // Configurazione delle variabili per il test
        val userId = "utenteId"
        val codiceErrato = "ABC12346"
        val progettiUtente = listOf<Progetto>()

        // Mock delle dipendenze
        coEvery { repositoryProgetto.getProgettoIdByCodice(codiceErrato) } returns null
        coEvery { repositoryProgetto.getProgettiUtente(userId) } returns progettiUtente

        // Esecuzione del metodo per aggiungere il partecipante ad un progetto tramite codice
        viewModelProgetto.aggiungiPartecipanteConCodice(userId, codiceErrato)
        advanceUntilIdle()

        // Verifica che il metodo repositoryProgetto.aggiungiPartecipante non sia stato chiamato
        coVerify(exactly = 0) { repositoryProgetto.aggiungiPartecipante(any(), any()) }

        // Verifica del comportamento
        assert(!viewModelProgetto.aggiungiProgettoRiuscito.value)
        assert(viewModelProgetto.erroreAggiungiProgetto.value == "Il codice inserito non è valido. Riprovare o contattare il creatore del progetto")
    }

    @Test
    fun `caricaProgettiUtente carica correttamente i progetti dell'utente`() = runTest {
        // Configurazione delle variabili per il test
        val userId = "user1"
        val progetti = listOf(
            Progetto(
                id = "progettoId",
                nome = "Progetto di Test",
                descrizione = "Descrizione del Progetto di Test",
                dataScadenza = Date(),
                priorita = Priorita.ALTA,
                codice = "ABC12345",
                partecipanti = listOf("user1", "user3")
            ),
            Progetto(
                id = "progettoId",
                nome = "Progetto di Test",
                descrizione = "Descrizione del Progetto di Test",
                dataScadenza = Date(),
                priorita = Priorita.ALTA,
                codice = "ABC12345",
                partecipanti = listOf("user1", "user2")
            )
        )

        // Mock delle dipendenze
        coEvery { repositoryProgetto.getProgettiUtente(userId) } returns progetti
        coEvery { repositoryLeMieAttivita.countNonCompletedTodoByProject(any()) } returns 0

        // Esecuzione del metodo per caricare i progetti dell'utente
        viewModelProgetto.caricaProgettiUtente(userId, true)
        advanceUntilIdle()

        // Verifica dei risultati
        assert(viewModelProgetto.progetti.value == progetti)
    }
}