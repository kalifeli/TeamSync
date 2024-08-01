package com.example.teamsync.caratteristiche.login.data.viewModel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.teamsync.caratteristiche.login.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.login.data.model.SessoUtente
import com.example.teamsync.caratteristiche.login.data.repository.RepositoryUtente
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
    fun `login avvenuto con successo`() = runTest {
        // Configurazione delle variabili per il test
        val email = "felizianiale34@gmail.com"
        val password = "pratofiorito"
        val utente = mockk<FirebaseUser>(relaxed = true)

        // Mock delle dipendenze: configurazione dei metodi della repository che verranno chiamati dal 'ViewModelUtente'
        coEvery { repositoryUtente.login(email, password) } returns utente
        coEvery { repositoryUtente.isEmailVerified() } returns true // quando viene chiamato questo metodo verrà restituito il valore true simulando che l'email è verificata
        coEvery { repositoryUtente.isFirstLogin(any()) } returns true // simulazione del primo accesso dell'utente

        // Mock della funzione di validazione dell'email
        mockkObject(ViewModelUtente.EmailValidator)
        every { ViewModelUtente.EmailValidator.isValidEmail(email) } returns true

        // Eseguiamo il metodo di login
        viewModelUtente.login(email, password)

        // Permette di far avanzare il dispatcher di test fino a quando tutte le coroutine sono completate, garantendo che il test attenda la fine dell'esecuzione delle operazioni asincrone
        advanceUntilIdle()

        // Logging per la diagnostica
        println("loginRiuscito: ${viewModelUtente.loginRiuscito.value}")
        println("primoAccesso: ${viewModelUtente.primoAccesso.value}")
        println("utenteCorrente: ${viewModelUtente.utenteCorrente}")
        println("erroreLogin: ${viewModelUtente.erroreLogin.value}")

        // Verifica dei risultati
        assert(viewModelUtente.loginRiuscito.value) { "Expected loginRiuscito to be true but was ${viewModelUtente.loginRiuscito.value}" }
        assert(viewModelUtente.primoAccesso.value) { "Expected primoAccesso to be true but was ${viewModelUtente.primoAccesso.value}" }
        assert(viewModelUtente.utenteCorrente == utente) { "Expected utenteCorrente to be $utente but was ${viewModelUtente.utenteCorrente}" }
        assert(viewModelUtente.erroreLogin.value == null) { "Expected erroreLogin to be null but was ${viewModelUtente.erroreLogin.value}" }
    }

    fun `login fallito a causa di creden zuali incorrette` (){

    }

    @Test
    fun `registrazione avvenuta con successo`() = runTest {
        // configurazione delle variabili per la registrazione
        val matricola = "s1104302"
        val nome = "Mario"
        val cognome = "Rossi"
        val email = "mario.rossi@esempio.com"
        val dataDiNascita = SimpleDateFormat("dd/MM/yyyy").parse("06/05/2002")
        val sesso = SessoUtente.UOMO
        val password = "password123"
        val confermaPassword = "password123"

        val utente = mockk<FirebaseUser>(relaxed = true)

        //Mock delle dipendenze
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

        //Esecuzione del metodo di registrazione

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
        advanceUntilIdle() // BARRIERA???

        //Verifica dei risultati
        assert(viewModelUtente.erroreRegistrazione.value == null)
        assert(viewModelUtente.registrazioneRiuscita.value)

    }

    @Test
    fun `registrazione fallita per data di nascita incorretta`() = runTest {
        // configurazione delle variabili per la registrazione
        val matricola = "s1104302"
        val nome = "Mario"
        val cognome = "Rossi"
        val email = "mario.rossi@esempio.com"
        val dataDiNascita = SimpleDateFormat("dd/MM/yyyy").parse("25/08/2024")
        val sesso = SessoUtente.UOMO
        val password = "password123"
        val confermaPassword = "password123"

        val utente = mockk<FirebaseUser>(relaxed = true)

        //Mock delle dipendenze
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

        //Esecuzione del metodo di registrazione

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
        advanceUntilIdle() // BARRIERA???

        //Verifica dei risultati
        assert(viewModelUtente.erroreRegistrazione.value == "Per favore, inserisci una data di nascita valida.")
        assert(!viewModelUtente.registrazioneRiuscita.value)

    }

    @Test
    fun `resetPassword con successo` () = runTest {

        // Definizio delle variabili necessarie per il reset
        val email = "mario.rossi@esempio.com"

        //Mock delle dipendenze
        coEvery { repositoryUtente.resetPassword(email)} just Runs
        mockkObject(ViewModelUtente.EmailValidator)
        every {ViewModelUtente.EmailValidator.isValidEmail(email)} returns true

        viewModelUtente.resetPassword(email)
        advanceUntilIdle()

        assert(viewModelUtente.resetPasswordRiuscito.value)
        assert(viewModelUtente.erroreResetPassword.value == null)

    }

    @Test
    fun `updateUserProfile con successo` () = runTest{
        //definizione del profilo utente fittizio
        val profiloUtente = ProfiloUtente(
            id = "123",
            nome = "Mario",
            cognome = "Rossi",
            matricola = "s1104302",
            dataDiNascita = SimpleDateFormat("dd/MM/yyyy").parse("06/05/2002") ?: Date(),
            sesso = SessoUtente.UOMO,
        )

        //Mock delle dipendenze
        coEvery { repositoryUtente.updateUserProfile(profiloUtente) } just Runs
        coEvery { repositoryUtente.getUserProfile(profiloUtente.id) } returns profiloUtente

        //Eseguiamo il metodo del viewModelUtente per aggiornare il profilo
        viewModelUtente.updateUserProfile(profiloUtente)
        advanceUntilIdle()

        assert(viewModelUtente.userProfilo.value == profiloUtente)
    }

    @Test
    fun `updateUserProfile falita per data di nascita incorretta` () = runTest{
        //definizione del profilo utente fittizio
        val profiloUtente = ProfiloUtente(
            id = "123",
            nome = "Mario",
            cognome = "Rossi",
            matricola = "s1104302",
            dataDiNascita = SimpleDateFormat("dd/MM/yyyy").parse("06/05/2025") ?: Date(),
            sesso = SessoUtente.UOMO,
        )

        //Mock delle dipendenze
        coEvery { repositoryUtente.updateUserProfile(profiloUtente) } just Runs
        coEvery { repositoryUtente.getUserProfile(profiloUtente.id) } returns profiloUtente

        //Eseguiamo il metodo del viewModelUtente per aggiornare il profilo
        viewModelUtente.updateUserProfile(profiloUtente)
        advanceUntilIdle()

        assert(viewModelUtente.userProfilo.value == profiloUtente)
    }

    @Test
    fun `sendEmailVerificaition con successo` () = runTest {

    }


}