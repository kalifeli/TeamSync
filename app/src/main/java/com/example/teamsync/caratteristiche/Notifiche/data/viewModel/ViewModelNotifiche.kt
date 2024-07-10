package com.example.teamsync.caratteristiche.Notifiche.data.viewModel

import androidx.compose.runtime.mutableStateOf
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

                println("Notifiche caricate: ${notificheList.value}")

            } catch (e: Exception) {
                e.printStackTrace()
                // Gestire l'errore, ad esempio mostrare un messaggio all'utente
            } finally {
                isLoading.value = false
            }
        }
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

                println("Stato della notifica cambiato con successo")
            } catch (e: Exception) {
                println("Errore durante il cambio di stato della notifica: $e")
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

}


