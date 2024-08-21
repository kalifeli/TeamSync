//[app](../../../index.md)/[com.example.teamsync.caratteristiche.impostazioni.data.repository](../index.md)/[RepositoryFaq](index.md)/[getAllFaq](get-all-faq.md)

# getAllFaq

[androidJvm]\
suspend fun [getAllFaq](get-all-faq.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Faq](../../com.example.teamsync.caratteristiche.impostazioni.data.model/-faq/index.md)&gt;

Recupera tutte le FAQ dalla collezione &quot;Faq&quot; di Firestore.

#### Return

Una lista di oggetti [Faq](../../com.example.teamsync.caratteristiche.impostazioni.data.model/-faq/index.md) contenente tutte le FAQ disponibili.     Se si verifica un errore durante il recupero, viene restituita una lista vuota.

#### Throws

| | |
|---|---|
| [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html) | se si verifica un errore durante il recupero delle FAQ. |
