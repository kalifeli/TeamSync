package com.example.teamsync.caratteristiche.autentificazione.data.model

import android.content.Context
import androidx.annotation.StringRes
import com.example.teamsync.R
import java.util.Date

/**
 * Classe dati che rappresenta il profilo di un utente.
 *
 * @property id L'ID univoco dell'utente.
 * @property nome Il nome dell'utente.
 * @property cognome Il cognome dell'utente.
 * @property matricola La matricola dell'utente (ad esempio, per studenti universitari).
 * @property dataDiNascita La data di nascita dell'utente.
 * @property sesso Il sesso dell'utente. Può essere UOMO, DONNA, o ALTRO.
 * @property email L'indirizzo email dell'utente.
 * @property primoAccesso Indica se è il primo accesso dell'utente all'applicazione.
 * @property immagine L'URL dell'immagine del profilo dell'utente. Può essere null.
 * @property amici Una lista di ID degli amici dell'utente.
 */
data class ProfiloUtente(
    var id: String = "",
    var nome: String = "",
    var cognome: String = "",
    var matricola: String = "",
    var dataDiNascita: Date = Date(),
    var sesso: SessoUtente = SessoUtente.UOMO,
    var email: String = "",
    var primoAccesso: Boolean = true,
    val immagine: String? = null,
    var amici: List<String> = emptyList()
)

/**
 * Enumerazione che rappresenta il sesso dell'utente.
 * Ogni valore ha associata una risorsa di stringa per la traduzione.
 *
 * @property resId L'ID della risorsa stringa associata per la traduzione del valore dell'enumerazione.
 */
enum class SessoUtente(@StringRes val resId: Int){
    UOMO(R.string.sesso_uomo),
    DONNA(R.string.sesso_donna),
    ALTRO(R.string.sesso_altro);

    fun getSessoTradotto(context: Context, sesso: SessoUtente): String {
        return context.getString(sesso.resId)
    }
}

