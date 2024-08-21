//[app](../../../index.md)/[com.example.teamsync.caratteristiche.impostazioni.data.repository](../index.md)/[RepositoryTerms](index.md)

# RepositoryTerms

[androidJvm]\
class [RepositoryTerms](index.md)

Repository per la gestione delle operazioni relative ai Termini e Condizioni (Terms) su Firebase Firestore.

## Constructors

| | |
|---|---|
| [RepositoryTerms](-repository-terms.md) | [androidJvm]<br>constructor() |

## Functions

| Name | Summary |
|---|---|
| [getAllTerms](get-all-terms.md) | [androidJvm]<br>suspend fun [getAllTerms](get-all-terms.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Terms](../../com.example.teamsync.caratteristiche.impostazioni.data.model/-terms/index.md)&gt;<br>Recupera tutti i termini e condizioni dalla collezione &quot;terms&policies&quot; di Firestore. |
| [getLastUpdate](get-last-update.md) | [androidJvm]<br>suspend fun [getLastUpdate](get-last-update.md)(): [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)?<br>Recupera la data dell'ultimo aggiornamento dei termini e condizioni. |
