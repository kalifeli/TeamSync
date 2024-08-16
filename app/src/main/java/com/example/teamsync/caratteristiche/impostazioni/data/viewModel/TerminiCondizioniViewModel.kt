package com.example.teamsync.caratteristiche.impostazioni.data.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teamsync.caratteristiche.impostazioni.data.model.Terms
import com.example.teamsync.caratteristiche.impostazioni.data.repository.RepositoryTerms
import kotlinx.coroutines.launch
import java.util.Date


/**
 * ViewModel per la gestione della logica e dello stato della schermata dei Termini e Condizioni.
 *
 * Questa classe è responsabile di recuperare i dati relativi ai Termini e Condizioni da un repository e di esporli alla UI.
 * Utilizza [LiveData] per fornire dati osservabili alla UI in modo da aggiornare automaticamente la visualizzazione quando i dati cambiano.
 *
 * @property repository Il repository utilizzato per ottenere i dati relativi ai Termini e Condizioni.
 */
class TerminiCondizioniViewModel(private val repository: RepositoryTerms) : ViewModel(){

    // Lista dei termini caricati dal repository.
    private val _listaTermini = MutableLiveData<List<Terms>>(emptyList())
    val listaTermini: LiveData<List<Terms>> = _listaTermini

    // Data dell'ultima modifica dei termini.
    private val _ultimaModifica = MutableLiveData<Date?>(null)
    val ultimaModifica: LiveData<Date?> = _ultimaModifica

    // Stato di caricamento per indicare se i termini sono ancora in fase di caricamento.
    private val _loading = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading

    // Stato dell'errore per indicare eventuali problemi durante il caricamento dei dati.
    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    /**
     * Inizializza il ViewModel caricando i termini.
     * Questo metodo viene chiamato immediatamente alla creazione del ViewModel.
     */
    init {
        caricaTermini()
    }


    /**
     * Carica i termini dal repository e aggiorna gli stati di [_listaTermini], [_ultimaModifica], [_loading], e [_error].
     *
     * Questo metodo esegue una chiamata asincrona per ottenere i dati, e gestisce eventuali errori che possono verificarsi durante il processo di recupero dei dati.
     */
    private fun caricaTermini() {
        viewModelScope.launch {
            try {
                val terms = repository.getAllTerms()
                if (terms.isEmpty()) {
                    _error.value = "Nessun termine trovato."
                } else {
                    _listaTermini.value = terms
                    _ultimaModifica.value = repository.getLastUpdate()
                }
            } catch (e: Exception) {
                _error.value = "Errore di connessione: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}