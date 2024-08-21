//[app](../../../index.md)/[com.example.teamsync.caratteristiche.impostazioni.data.repository](../index.md)/[RepositoryTerms](index.md)/[getAllTerms](get-all-terms.md)

# getAllTerms

[androidJvm]\
suspend fun [getAllTerms](get-all-terms.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Terms](../../com.example.teamsync.caratteristiche.impostazioni.data.model/-terms/index.md)&gt;

Recupera tutti i termini e condizioni dalla collezione &quot;terms&policies&quot; di Firestore.

#### Return

Una lista di oggetti [Terms](../../com.example.teamsync.caratteristiche.impostazioni.data.model/-terms/index.md) contenente tutti i termini e condizioni disponibili.     Se si verifica un errore durante il recupero, viene restituita una lista vuota.

#### Throws

| | |
|---|---|
| [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html) | se si verifica un errore durante il recupero dei Terms. |
