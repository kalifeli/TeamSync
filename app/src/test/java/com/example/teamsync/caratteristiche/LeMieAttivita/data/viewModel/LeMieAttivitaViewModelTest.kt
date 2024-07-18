package com.example.teamsync.caratteristiche.LeMieAttivita.data.viewModel

import org.junit.Rule
import org.junit.jupiter.api.Assertions.*
import org.junit.rules.TestRule

class LeMieAttivitaViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val repository: LeMieAttivitaViewModel = mock(LeMieAttivitaViewModel::class.java)
    private val viewModel = LeMieAttivitaViewModel(repository)

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getTodoByUtente() = runBlockingTest {
        val idProg = "testIdProg"
        val utenteId = "testUtenteId"
        val mockResult = listOf(TodoItem(1, "Mocked Todo"))

        // Imposta il comportamento del mock
        `when`(repository.getTodoByUtente(idProg, utenteId)).thenReturn(mockResult)

        // Chiama il metodo da testare
        viewModel.getTodoUtente(idProg, utenteId)

        // Verifica che il repository sia stato chiamato con i parametri corretti
        verify(repository).getTodoByUtente(idProg, utenteId)

        // Verifica che il risultato sia stato impostato correttamente
        assertEquals(mockResult, viewModel.leMieAttivitaPerUtente)
    }
}