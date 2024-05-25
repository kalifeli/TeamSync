import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.teamsync.model.UserProfile
import com.example.teamsync.model.UserProfileRepository



class UserProfileViewModel(user_id: String): ViewModel() {
    // Variabili di stato per i dati del profilo utente
    val nome: MutableState<String> = mutableStateOf("")
    val cognome: MutableState<String> = mutableStateOf("")
    val username: MutableState<String> = mutableStateOf("")
    val dataDiNascita: MutableState<String> = mutableStateOf("")
    val matricola: MutableState<String> = mutableStateOf("")
    val email: MutableState<String> = mutableStateOf("")
    val id: MutableState<String> = mutableStateOf("")

    // Metodo per ottenere gli utenti dalla Repository
    fun getUsersFromRepository(): List<UserProfile> {
        return repository.getUsers()
    }
    // Repository per ottenere i dati degli utenti
    private val repository = UserProfileRepository();

    init {
        // Carica i dati del primo utente dalla repository e imposta i valori iniziali dei MutableState
        val user = repository.getUserById(user_id)
        user?.let {
            nome.value = user.nome
            cognome.value = user.cognome
            username.value = user.username
            dataDiNascita.value = user.dataDiNascita.toString() // Assicurati che il formato della data sia quello desiderato
            matricola.value = user.matricola
            email.value = user.email
            id.value= user.id
        }
    }


    // Metodo per aggiornare i dati del profilo utente
    fun updateUserProfile(profileId: String, newNome: String, newCognome: String, newUsername: String, newMatricola: String, newEmail: String) {
        // Trova il profilo utente con l'ID specificato
        val userProfile = repository.getUserById(profileId)

        // Se il profilo utente esiste, aggiorna i suoi valori con i nuovi dati
        userProfile?.apply {
            nome = newNome
            cognome = newCognome
            username = newUsername
            matricola = newMatricola
            email = newEmail
        }
    }

}
