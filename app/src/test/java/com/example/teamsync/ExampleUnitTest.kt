import com.example.teamsync.caratteristiche.LeMieAttivita.data.viewModel.LeMieAttivitaViewModel
import com.example.teamsync.data.models.Priorità
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Calendar

class LeMieAttivitaViewModelTest {

    private lateinit var viewModel: LeMieAttivitaViewModel

    @Before
    fun setUp() {
        viewModel = LeMieAttivitaViewModel()
    }

    @Test
    fun testAddTodo() {
        val titolo = "Test Title"
        val descrizione = "Test Description"
        val dataScad = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 1) }.time
        val priorita = Priorità.ALTA
        val completato = false
        val proprietario = "testOwner"
        val progetto = "testProject"

        viewModel.addTodo(
            titolo,
            descrizione,
            dataScad,
            priorita,
            completato,
            proprietario,
            progetto
        )


        // Verifica il comportamento del view model
        // Esempio: verifica se il task è stato aggiunto correttamente

        val addedTasks = viewModel.leMieAttivitaNonCompletate.filter { it.titolo == titolo }
        assertEquals("Il numero di attività non completate dovrebbe essere 1", 1, addedTasks.size)
        assertEquals("La priorità del task aggiunto dovrebbe essere ALTA", Priorità.ALTA, addedTasks.first().priorita)
        // Aggiungi altre asserzioni secondo necessità
    }
}
