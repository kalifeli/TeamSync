//[app](../../../index.md)/[com.example.teamsync.caratteristiche.autentificazione.data.repository](../index.md)/[RepositoryUtente](index.md)/[rimuoviAmiciNonEsistenti](rimuovi-amici-non-esistenti.md)

# rimuoviAmiciNonEsistenti

[androidJvm]\
suspend fun [rimuoviAmiciNonEsistenti](rimuovi-amici-non-esistenti.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amici: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;

Verifica l'esistenza dei profili associati alla lista di amici e rimuove quelli che non esistono più nel database.

Questa funzione scorre l'elenco degli ID amici forniti, controlla l'esistenza di ciascun profilo utente nel database e rimuove gli amici che non esistono più. La funzione aggiorna la lista degli amici validi nel database dell'utente e restituisce la lista aggiornata.

#### Return

Una lista aggiornata di ID amici che esistono ancora nel database.

#### Parameters

androidJvm

| | |
|---|---|
| userId | L'ID dell'utente proprietario della lista di amici. |
| amici | Una lista di ID amici da verificare. |

#### Throws

| | |
|---|---|
| [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html) | se si verifica un errore durante il recupero o la rimozione dei profili. |
