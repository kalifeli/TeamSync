package com.example.teamsync.caratteristiche.notifiche.data.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.autentificazione.data.viewModel.ViewModelUtente
import com.example.teamsync.caratteristiche.notifiche.data.model.Notifiche
import com.example.teamsync.caratteristiche.notifiche.data.repository.RepositoryNotifiche
import kotlinx.coroutines.launch

/**
 * ViewModel per gestire le operazioni relative alle notifiche.
 */
class ViewModelNotifiche(
    private val repositoryNotifiche : RepositoryNotifiche,
    private val viewModelUtente : ViewModelUtente,
    private val context: Context
) : ViewModel() {

    var notificheList = mutableStateOf<List<Notifiche>>(emptyList())
        private set
    var isLoading = mutableStateOf(true)
        private set
    var eliminazioneNotificheStato = mutableStateOf(false)
        private set
    var erroreEliminazioneNotifiche = mutableStateOf<String?>(null)
        private set
    var letturaNotificheStato = mutableStateOf(false)
        private set
    var erroreLetturaNotifiche = mutableStateOf<String?>(null)
        private set



    init {
        //Il fetchNotifiche è chiamato all'interno di questa funzione solo quando l'ID utente è disponibile.
        osservaProfiloUtente()
    }

    /**
     * Osserva i cambiamenti nel profilo utente e avvia il caricamento delle notifiche quando l'ID utente è disponibile.
     */
    private fun osservaProfiloUtente() {
        viewModelUtente.userProfilo.observeForever { profiloUtente ->
            if (profiloUtente != null) {
                fetchNotifiche()
            }
        }
    }

    /**
     * Recupera le notifiche per l'utente corrente.
     */
    fun fetchNotifiche() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val userId = viewModelUtente.userProfilo.value?.id

                if (!userId.isNullOrEmpty()) {
                    val notifiche = repositoryNotifiche.getNotificheUtente(userId)
                    notificheList.value = notifiche
                    erroreLetturaNotifiche.value = null
                } else {
                    erroreLetturaNotifiche.value = context.getString(R.string.errore_id_utente)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                erroreLetturaNotifiche.value = context.getString(R.string.errore_lettura_notifiche)
            } finally {
                isLoading.value = false
            }
        }
    }

    /**
     * Cambia lo stato di una notifica specifica.
     *
     * @param notificaId L'ID della notifica da cambiare.
     */
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

                erroreLetturaNotifiche.value = null
                letturaNotificheStato.value = true
            } catch (e: Exception) {
                erroreLetturaNotifiche.value = context.getString(R.string.errore_stato_notifica)
                letturaNotificheStato.value = false
            }
        }
    }

    /**
     * Resetta l'errore di lettura delle notifiche.
     */
    fun resetErroreLetturaNotifiche(){
        erroreLetturaNotifiche.value = null
    }

    /**
     * Resetta lo stato di lettura delle notifiche.
     */
    fun resetLetturaNotificheStato(){
        letturaNotificheStato.value = false
    }

    /**
     * Crea una nuova notifica.
     *
     * @param mittenteId L'ID dell'utente che invia la notifica.
     * @param destinatarioId L'ID dell'utente che riceve la notifica.
     * @param tipo Il tipo di notifica.
     * @param contenuto Il contenuto della notifica.
     * @param progetto L'ID del progetto a cui si riferisce la notifica.
     */
    fun creaNotifica(mittenteId: String, destinatarioId: String, tipo: String, contenuto: String, progetto: String) {
        viewModelScope.launch {
            try {
                repositoryNotifiche.creaNotifica(mittenteId, destinatarioId, tipo, contenuto, progetto)
            } catch (e: Exception) {
                Log.e("ViewModelNotifiche", "Eccezione durante la creazione della notifica nel ViewModel", e) // Sostituito println con Log.e
            }
        }
    }

    /**
     * Cambia lo stato di una notifica specifica a "accettato".
     *
     * @param notificaId L'ID della notifica da cambiare.
     */
    fun cambiastatoAccettatoNotifica(notificaId: String) {
        viewModelScope.launch {
            try {
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

                Log.d("ViewModelNotifiche", "Stato della notifica cambiato con successo")
            } catch (e: Exception) {
                Log.e("ViewModelNotifiche", "Errore durante il cambio di stato della notifica", e)
            }
        }
    }

    /**
     * Elimina una notifica specifica.
     *
     * @param notificaId L'ID della notifica da eliminare.
     */
    fun eliminaNotifica(notificaId: String) {
        viewModelScope.launch {
            try {
                repositoryNotifiche.deleteNotifica(notificaId)
                // Rimuovi la notifica dalla lista locale
                notificheList.value = notificheList.value.filter { it.id != notificaId }
                Log.d("ViewModelNotifiche", "Notifica eliminata con successo")
            } catch (e: Exception) {
                Log.e("ViewModelNotifiche", "Errore durante l'eliminazione della notifica", e)
            }
        }
    }

    /**
     * Elimina tutte le notifiche di amicizia tra due utenti specificati.
     *
     * Questa funzione avvia una coroutine per eseguire l'eliminazione delle notifiche di amicizia tra due utenti
     * identificati da `userId1` e `userId2`. Viene utilizzato il repository delle notifiche per effettuare
     * l'operazione di eliminazione. In caso di errore durante l'operazione, l'errore viene loggato.
     *
     * @param userId1 L'ID del primo utente coinvolto nell'amicizia.
     * @param userId2 L'ID del secondo utente coinvolto nell'amicizia.
     *
     * @throws Exception Se si verifica un errore durante l'eliminazione delle notifiche di amicizia, viene loggato.
     */
    fun eliminaNotificheAmicizia(userId1: String, userId2: String) {
        viewModelScope.launch {
            try {
                repositoryNotifiche.deleteAmiciziaNotifiche(userId1, userId2)
            } catch (e: Exception) {
                Log.e("ViewModelNotifiche", "Errore durante l'eliminazione delle notifiche di amicizia", e) // Aggiunto log dell'errore
            }
        }
    }

    /**
     * Controlla se esiste una richiesta di amicizia tra due utenti specifici.
     *
     * @param userId1 L'ID del primo utente.
     * @param userId2 L'ID del secondo utente.
     * @param onResult Funzione di callback che verrà chiamata con `true` se esiste una richiesta di amicizia, `false` altrimenti.
     */
    fun controllaRichiestaAmicizia(userId1: String, userId2: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                // Recupera la notifica di richiesta di amicizia tra userId1 e userId2
                val notifica = repositoryNotifiche.getNotificaRichiestaAmicizia(userId1, userId2)
                // Chiama la funzione di callback con `true` se esiste una richiesta di amicizia, `false` altrimenti
                onResult(notifica != null)
            } catch (e: Exception) {
                // Gestisce l'eccezione. In caso di errore, logga l'errore e chiama la funzione di callback con `false`
                Log.e("ViewModelNotifiche", "Errore durante il controllo della richiesta di amicizia", e)
                onResult(false)
            }
        }
    }

    /**
     * Elimina tutte le notifiche di un utente specifico.
     *
     * @param userId L'ID dell'utente di cui si vogliono eliminare tutte le notifiche.
     */
    fun eliminaNotificheUtente(userId: String) {
        // Avvia una coroutine nel ViewModelScope per eseguire operazioni asincrone
        viewModelScope.launch {
            try {
                // Chiede al repository di eliminare tutte le notifiche per l'utente specificato
                repositoryNotifiche.deleteAllNotifiche(userId)

                eliminazioneNotificheStato.value = true
                erroreEliminazioneNotifiche.value = null

            } catch (e: Exception) {
                Log.e("ViewModelNotifiche", "Errore durante l'eliminazione delle notifiche per l'utente $userId", e)
                eliminazioneNotificheStato.value = false
                erroreEliminazioneNotifiche.value = context.getString(R.string.errore_eliminazione_notifiche)
            }
        }
    }


    /**
     * Resetta l'errore di eliminazione delle notifiche.
     */
    fun resetErroreEliminazioneNotifiche(){
        erroreEliminazioneNotifiche.value = null
    }

    /**
     * Resetta lo stato di eliminazione delle notifiche.
     */
    fun resetEliminazioneNotificheStato(){
        eliminazioneNotificheStato.value = false
    }

    /**
     * Recupera l'ID di una notifica in base al contenuto specificato.
     *
     * @param contenuto Il contenuto della notifica da cercare.
     * @param onResult Funzione di callback per restituire l'ID della notifica.
     */
    fun getNotificaIdByContent(contenuto: String, onResult: (String?) -> Unit) {
        // Avvia una coroutine nel ViewModelScope per eseguire operazioni asincrone
        viewModelScope.launch {
            try {
                // Chiede al repository di recuperare l'ID della notifica in base al contenuto
                val notificaId = repositoryNotifiche.getNotificaIdByContent(contenuto)
                // Restituisce l'ID della notifica tramite il callback onResult
                onResult(notificaId)
            } catch (e: Exception) {
                // Gestisce l'eccezione in caso di errore durante il recupero dell'ID della notifica
                Log.e("ViewModelNotifiche", "Errore durante il recupero della notifica per il contenuto: $contenuto", e)
                // Restituisce null tramite il callback onResult in caso di errore
                onResult(null)
            }
        }
    }
}



