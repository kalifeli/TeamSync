package com.example.teamsync.caratteristiche.autentificazione.data.viewModel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
}