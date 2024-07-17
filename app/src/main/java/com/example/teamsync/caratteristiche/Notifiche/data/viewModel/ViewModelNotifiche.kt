package com.example.teamsync.caratteristiche.Notifiche.data.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teamsync.caratteristiche.Notifiche.data.model.Notifiche
import com.example.teamsync.caratteristiche.Notifiche.data.repository.RepositoryNotifiche
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ViewModelNotifiche : ViewModel() {

    private val repositoryNotifiche = RepositoryNotifiche()


    private val viewModelU = ViewModelUtente()
    var notificheList = mutableStateOf<List<Notifiche>>(emptyList())
        private set
    var isLoading = mutableStateOf(true)
        private set

    // AGGIUNTA per tenere traccia delle notifiche non lette
    val notificheNonLette = MutableLiveData<Boolean>().apply { value = false }


    init {
        fetchNotifiche()
    }


    fun fetchNotifiche() {
        var tentativi = 0
        val max_tentativi = 8

        viewModelScope.launch {
            isLoading.value = true

            try {
                // Attendiamo finché non abbiamo un userId valido
                var userId = viewModelU.userProfile?.id
                while (userId.isNullOrEmpty()) {
                    delay(500)
                    userId = viewModelU.userProfile?.id
                }

                // Se siamo qui, abbiamo trovato un userId valido
                var notifiche = repositoryNotifiche.getNotifiche()
                while (notifiche.isEmpty() && tentativi < max_tentativi) {
                    delay(400)
                    notifiche = repositoryNotifiche.getNotifiche()
                    tentativi++
                }

                // Filtriamo le notifiche per il destinatario corrente
                notificheList.value = notifiche.filter {
                    it.destinatario == userId
                }

                Log.d("ViewModelNotifiche", "Notifiche caricate: ${notificheList.value}")
                aggiornaNotificheNonLette()

                println("Notifiche caricate: ${notificheList.value}")

            } catch (e: Exception) {
                e.printStackTrace()
                // Gestire l'errore, ad esempio mostrare un messaggio all'utente
            } finally {
                isLoading.value = false
            }
        }
    }

    private fun aggiornaNotificheNonLette(){
        val nonLette = notificheList.value.any { !it.aperto }
        notificheList.value.forEach { notifica ->
            Log.d("ViewModelNotifiche", "Notifica ID: ${notifica.id}, Aperto: ${notifica.aperto}")
        }
        Log.d("ViewModelNotifiche", "aggiornaNotificheNonLette: $nonLette")
        notificheNonLette.value = nonLette
    }


    fun cambiaStatoNotifica(notificaId: String) {

        viewModelScope.launch {
            try {
                // Apri la notifica su Firebase
                repositoryNotifiche.apriNotifica(notificaId)

                // Aggiorna la lista locale delle notifiche
                notificheList.value = notificheList.value.map {
                    if (it.id == notificaId) {
                        val updatedNotifica = it.copy(aperto = true)
                        updatedNotifica
                    } else {
                        it
                    }
                }

                Log.d("ViewModelNotifiche", "Dopo cambiaStatoNotifica: ${notificheList.value}")
                // Aggiorna lo stato delle notifiche non lette
                aggiornaNotificheNonLette()


                println("Stato della notifica cambiato con successo")
            } catch (e: Exception) {
                println("Errore durante il cambio di stato della notifica: $e")
            }
        }
    }

    fun creaNotificaViewModel(mittenteId: String, destinatarioId: String, tipo: String, contenuto: String, progetto: String) {
        viewModelScope.launch {
            try {
                repositoryNotifiche.creaNotifica(mittenteId, destinatarioId, tipo, contenuto, progetto)
            } catch (e: Exception) {
                println("Eccezione durante la creazione della notifica nel ViewModel: $e")

            }
        }
    }


    fun cambiaStato_Accettato_Notifica(notificaId: String) {
        viewModelScope.launch {
            try {
                // Apri la notifica su Firebase (assicurati che questa funzione aggiorni Firebase)
                repositoryNotifiche.apriNotifica(notificaId)

                // Aggiorna la lista locale delle notifiche
                notificheList.value = notificheList.value.map {
                    if (it.id == notificaId) {
                        val updatedNotifica = it.copy(accettato = "true", aperto = true)

                        // Aggiorna la notifica su Firebase
                        repositoryNotifiche.updateNotifica(updatedNotifica)

                        updatedNotifica
                    } else {
                        it
                    }
                }

                println("Stato della notifica cambiato con successo")
            } catch (e: Exception) {
                println("Errore durante il cambio di stato della notifica: $e")
            }
        }
    }

    fun eliminaNotifica(notificaId: String) {
        viewModelScope.launch {
            try {
                repositoryNotifiche.deleteNotifica(notificaId)

                // Rimuovi la notifica dalla lista locale
                notificheList.value = notificheList.value.filter { it.id != notificaId }


                println("Notifica eliminata con successo")
            } catch (e: Exception) {
                println("Errore durante l'eliminazione della notifica: $e")
            }
        }
    }

    fun getNotificaIdByContent(contenuto: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            try {
                val notificaId = repositoryNotifiche.getNotificaIdByContent(contenuto)
                onResult(notificaId)
            } catch (e: Exception) {
                println("Errore durante il recupero della notifica: $e")
                onResult(null)
            }
        }
    }
}



