package com.example.teamsync.caratteristiche.autentificazione.data.viewModel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.teamsync.caratteristiche.autentificazione.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.autentificazione.data.model.SessoUtente
import com.example.teamsync.caratteristiche.autentificazione.data.repository.RepositoryUtente
import com.google.firebase.auth.FirebaseUser
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
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
class ViewModelUtenteTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repositoryUtente: RepositoryUtente
    private lateinit var viewModelUtente: ViewModelUtente

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repositoryUtente = mockk(relaxed = true)
        viewModelUtente = ViewModelUtente(repositoryUtente)
    }

    /*
    @Test
    fun `login avvenuto con successo`() = runTest {
        // Configurazione delle variabili per il test
        val email = "felizianiale34@gmail.com"
        val password = "pratofiorito"
        val utente = mockk<FirebaseUser>(relaxed = true)

        // Mock delle dipendenze
        coEvery { repositoryUtente.login(email, password) } returns utente
        coEvery { repositoryUtente.isEmailVerified() } returns true
        coEvery { repositoryUtente.isFirstLogin(any()) } returns true

        // Mock della funzione di validazione dell'email
        mockkObject(ViewModelUtente.EmailValidator)
        every { ViewModelUtente.EmailValidator.isValidEmail(email) } returns true

        // Esecuzione del metodo di login
        viewModelUtente.login(email, password)
        advanceUntilIdle()

        // Verifica dei risultati
        assert(viewModelUtente.loginRiuscito.value) { "Ci si aspettava loginRiuscito a true ma era ${viewModelUtente.loginRiuscito.value}" }
        assert(viewModelUtente.primoAccesso.value) { "Ci si aspettava primoAccesso a true ma era ${viewModelUtente.primoAccesso.value}" }
        assert(viewModelUtente.utenteCorrente != null) { "Ci si aspettava che utenteCorrente non fosse null ma era null" }
        assert(viewModelUtente.erroreLogin.value == null) { "Ci si aspettava erroreLogin a null ma era ${viewModelUtente.erroreLogin.value}" }

        // Verifica che l'utente corrente sia effettivamente quello restituito dal login
        assert(viewModelUtente.utenteCorrente === utente) { "Ci si aspettava che utenteCorrente fosse la stessa istanza del mock" }
    }

     */



    @Test
    fun `registrazione avvenuta con successo`() = runTest {
        // Configurazione delle variabili per la registrazione
        val matricola = "S1104302"
        val nome = "Mario"
        val cognome = "Rossi"
        val email = "mario.rossi@esempio.com"
        val dataDiNascita = SimpleDateFormat("dd/MM/yyyy").parse("06/05/2002")
        val sesso = SessoUtente.UOMO
        val password = "password123"
        val confermaPassword = "password123"

        val utente = mockk<FirebaseUser>(relaxed = true)

        // Mock delle dipendenze
        coEvery { repositoryUtente.signUp(
            matricola = matricola,
            nome = nome,
            cognome = cognome,
            email = email,
            dataNascita = dataDiNascita ?: Date(),
            sesso = sesso,
            password = password,
        ) } returns utente

        // Mock della funzione di validazione dell'email
        mockkObject(ViewModelUtente.EmailValidator)
        every { ViewModelUtente.EmailValidator.isValidEmail(email) } returns true

        // Esecuzione del metodo di registrazione
        viewModelUtente.signUp(
            matricola = matricola,
            nome = nome,
            cognome = cognome,
            email = email,
            dataNascita = dataDiNascita ?: Date(),
            sesso = sesso,
            password = password,
            confermaPassword = confermaPassword
        )
        advanceUntilIdle()

        // Verifica dei risultati
        assert(viewModelUtente.erroreRegistrazione.value == null) { "Ci si aspettava erroreRegistrazione a null ma era ${viewModelUtente.erroreRegistrazione.value}" }
        assert(viewModelUtente.registrazioneRiuscita.value) { "Ci si aspettava registrazioneRiuscita a true ma era ${viewModelUtente.registrazioneRiuscita.value}" }
    }

    @Test
    fun `registrazione fallita per data di nascita incorretta`() = runTest {
        // Configurazione delle variabili per la registrazione
        val matricola = "S1104302"
        val nome = "Mario"
        val cognome = "Rossi"
        val email = "mario.rossi@esempio.com"
        val dataDiNascita = SimpleDateFormat("dd/MM/yyyy").parse("25/08/2024")
        val sesso = SessoUtente.UOMO
        val password = "password123"
        val confermaPassword = "password123"

        val utente = mockk<FirebaseUser>(relaxed = true)

        // Mock delle dipendenze
        coEvery { repositoryUtente.signUp(
            matricola = matricola,
            nome = nome,
            cognome = cognome,
            email = email,
            dataNascita = dataDiNascita ?: Date(),
            sesso = sesso,
            password = password,
        ) } returns utente

        // Mock della funzione di validazione dell'email
        mockkObject(ViewModelUtente.EmailValidator)
        every { ViewModelUtente.EmailValidator.isValidEmail(email) } returns true

        // Esecuzione del metodo di registrazione
        viewModelUtente.signUp(
            matricola = matricola,
            nome = nome,
            cognome = cognome,
            email = email,
            dataNascita = dataDiNascita ?: Date(),
            sesso = sesso,
            password = password,
            confermaPassword = confermaPassword
        )
        advanceUntilIdle()

        // Verifica dei risultati
        assert(viewModelUtente.erroreRegistrazione.value == "Per favore, inserisci una data di nascita valida.") { "Ci si aspettava erroreRegistrazione a 'Per favore, inserisci una data di nascita valida.' ma era ${viewModelUtente.erroreRegistrazione.value}" }
        assert(!viewModelUtente.registrazioneRiuscita.value) { "Ci si aspettava registrazioneRiuscita a false ma era ${viewModelUtente.registrazioneRiuscita.value}" }
    }

    @Test
    fun `resetPassword con successo`() = runTest {
        // Definizione delle variabili necessarie per il reset
        val email = "mario.rossi@esempio.com"

        // Mock delle dipendenze
        coEvery { repositoryUtente.resetPassword(email) } just Runs
        mockkObject(ViewModelUtente.EmailValidator)
        every { ViewModelUtente.EmailValidator.isValidEmail(email) } returns true

        // Esecuzione del metodo di reset della password
        viewModelUtente.resetPassword(email)
        advanceUntilIdle()

        // Verifica dei risultati
        assert(viewModelUtente.resetPasswordRiuscito.value) { "Ci si aspettava resetPasswordRiuscito a true ma era ${viewModelUtente.resetPasswordRiuscito.value}" }
        assert(viewModelUtente.erroreResetPassword.value == null) { "Ci si aspettava erroreResetPassword a null ma era ${viewModelUtente.erroreResetPassword.value}" }
    }

    @Test
    fun `resetPassword con email invalida dovrebbe fallire`() = runTest {
        // Definizione delle variabili necessarie per il reset
        val email = "mariorossi@"

        // Mock delle dipendenze
        mockkObject(ViewModelUtente.EmailValidator)
        every { ViewModelUtente.EmailValidator.isValidEmail(email) } returns false

        // Esecuzione del metodo di reset della password
        viewModelUtente.resetPassword(email)
        advanceUntilIdle()

        // Verifica dei risultati
        assert(!viewModelUtente.resetPasswordRiuscito.value) { "Ci si aspettava resetPasswordRiuscito a false ma era ${viewModelUtente.resetPasswordRiuscito.value}" }
        assert(viewModelUtente.erroreResetPassword.value == "L'indirizzo email inserito non è valido.") { "Ci si aspettava erroreResetPassword a 'L'indirizzo email inserito non è valido.' ma era ${viewModelUtente.erroreResetPassword.value}" }
    }

    /*
    @Test
    fun `updateUserProfile con successo`() = runTest {
        // Definizione del profilo utente fittizio
        val profiloUtente = ProfiloUtente(
            id = "123",
            nome = "Mario",
            cognome = "Rossi",
            matricola = "s1104302",
            dataDiNascita = SimpleDateFormat("dd/MM/yyyy").parse("06/05/2002") ?: Date(),
            sesso = SessoUtente.UOMO,
            email = "mario.rossi@example.com"
        )

        // Mock delle dipendenze
        coEvery { repositoryUtente.updateUserProfile(profiloUtente) } just Runs
        coEvery { repositoryUtente.getUserProfile(profiloUtente.id) } returns profiloUtente

        // Esecuzione del metodo del ViewModelUtente per aggiornare il profilo
        viewModelUtente.updateUserProfile(profiloUtente)
        advanceUntilIdle()

        // Verifica dei risultati confrontando i singoli campi
        val userProfile = viewModelUtente.userProfilo.value

        // Log per debugging
        println("Profilo Utente restituito: $userProfile")
        println("Expected ID: ${profiloUtente.id}, Actual ID: ${userProfile?.id}")
        println("Expected Nome: ${profiloUtente.nome}, Actual Nome: ${userProfile?.nome}")
        println("Expected Cognome: ${profiloUtente.cognome}, Actual Cognome: ${userProfile?.cognome}")

        // Asserzioni
        assert(userProfile != null) { "Ci si aspettava che userProfilo non fosse null" }
        assert(userProfile?.id == profiloUtente.id) { "Ci si aspettava id = ${profiloUtente.id} ma era ${userProfile?.id}" }
        assert(userProfile?.nome == profiloUtente.nome) { "Ci si aspettava nome = ${profiloUtente.nome} ma era ${userProfile?.nome}" }
        assert(userProfile?.cognome == profiloUtente.cognome) { "Ci si aspettava cognome = ${profiloUtente.cognome} ma era ${userProfile?.cognome}" }
        assert(userProfile?.matricola == profiloUtente.matricola) { "Ci si aspettava matricola = ${profiloUtente.matricola} ma era ${userProfile?.matricola}" }
        assert(userProfile?.dataDiNascita == profiloUtente.dataDiNascita) { "Ci si aspettava dataDiNascita = ${profiloUtente.dataDiNascita} ma era ${userProfile?.dataDiNascita}" }
        assert(userProfile?.sesso == profiloUtente.sesso) { "Ci si aspettava sesso = ${profiloUtente.sesso} ma era ${userProfile?.sesso}" }
        assert(userProfile?.email == profiloUtente.email) { "Ci si aspettava email = ${profiloUtente.email} ma era ${userProfile?.email}" }

        assert(viewModelUtente.erroreAggiornaProfilo.value == null) {
            "Ci si aspettava erroreAggiornaProfilo a essere null ma era ${viewModelUtente.erroreAggiornaProfilo.value}"
        }
        assert(viewModelUtente.aggiornaProfiloRiuscito.value) {
            "Ci si aspettava aggiornaProfiloRiuscito a essere true ma era ${viewModelUtente.aggiornaProfiloRiuscito.value}"
        }
    }



    @Test
    fun `updateUserProfile fallita per data di nascita incorretta`() = runTest {
        val profiloUtente = ProfiloUtente(
            id = "123",
            nome = "Mario",
            cognome = "Rossi",
            matricola = "s1104302",
            dataDiNascita = SimpleDateFormat("dd/MM/yyyy").parse("06/05/2025") ?: Date(),
            sesso = SessoUtente.UOMO,
        )

        viewModelUtente.updateUserProfile(profiloUtente)
        advanceUntilIdle()

        assert(!viewModelUtente.aggiornaProfiloRiuscito.value)
        assert(viewModelUtente.erroreAggiornaProfilo.value == "Per favore, inserisci una data di nascita corretta")
    }

     */



}