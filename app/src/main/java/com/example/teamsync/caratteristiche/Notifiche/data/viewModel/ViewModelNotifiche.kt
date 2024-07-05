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
        viewModelScope.launch {
            var userId = viewModelU.userProfile?.id
            while (userId.isNullOrEmpty()) {
                isLoading.value = true
                delay(500) // Attendiamo mezzo secondo tra ogni tentativo
                userId = viewModelU.userProfile?.id
            }

            // Se siamo qui, abbiamo trovato un userId valido
            isLoading.value = true
            try {
                val notifiche = repositoryNotifiche.getNotifiche()
                println("Notifiche ricevute: $notifiche")
                notificheList.value = notifiche.filter {
                    it.destinatario == userId
                }
                println("Notifiche caricate: ${notificheList.value}")
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading.value = false
            }
        }
    }





    fun cambiaStatoNotifica(notificaId: String) {
        viewModelScope.launch {
            try {
                repositoryNotifiche.apriNotifica(notificaId)
                // Aggiorna la lista delle notifiche localmente dopo aver cambiato lo stato
                notificheList.value = notificheList.value.map {
                    if (it.id == notificaId) it.copy(aperto = true) else it
                }
                println("Stato della notifica cambiato con successo")
            } catch (e: Exception) {
                println("Errore durante il cambio di stato della notifica: $e")
            }
        }
    }
}

