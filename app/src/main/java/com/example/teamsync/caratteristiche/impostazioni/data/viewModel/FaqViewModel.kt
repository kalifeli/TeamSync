package com.example.teamsync.caratteristiche.impostazioni.data.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teamsync.caratteristiche.impostazioni.data.model.Faq
import com.example.teamsync.caratteristiche.impostazioni.data.repository.RepositoryFaq
import kotlinx.coroutines.launch

/**
 * ViewModel per la gestione della logica della schermata FAQ.
 *
 * @param repository Il repository per il recupero delle FAQ.
 */
class FaqViewModel(private val repository: RepositoryFaq) : ViewModel() {

    private val _listaFaq = MutableLiveData<List<Faq>>(emptyList())
    val listaFaq: LiveData<List<Faq>> = _listaFaq

    private val _loading = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    /**
     * Metodo per caricare le FAQ dalla repository.
     */
    fun caricaFaq() {
        viewModelScope.launch {
            try {
                val faq = repository.getAllFaq()
                if (faq.isEmpty()) {
                    _error.value = "Nessuna Faq trovata."
                } else {
                    _listaFaq.value = faq
                }
            } catch (e: Exception) {
                _error.value = "Errore di connessione: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}
