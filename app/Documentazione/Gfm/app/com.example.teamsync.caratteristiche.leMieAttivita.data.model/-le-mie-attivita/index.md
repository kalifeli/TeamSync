//[app](../../../index.md)/[com.example.teamsync.caratteristiche.leMieAttivita.data.model](../index.md)/[LeMieAttivita](index.md)

# LeMieAttivita

[androidJvm]\
data class [LeMieAttivita](index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val titolo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val descrizione: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val dataScadenza: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html) = Date(), val priorita: [Priorita](../../com.example.teamsync.util/-priorita/index.md) = Priorita.NESSUNA, val completato: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val fileUri: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val progetto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val dataCreazione: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html) = Date(), val utenti: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = emptyList())

Rappresenta una singola attività all'interno del progetto.

## Constructors

| | |
|---|---|
| [LeMieAttivita](-le-mie-attivita.md) | [androidJvm]<br>constructor(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, titolo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, descrizione: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, dataScadenza: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html) = Date(), priorita: [Priorita](../../com.example.teamsync.util/-priorita/index.md) = Priorita.NESSUNA, completato: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, fileUri: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, progetto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, dataCreazione: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html) = Date(), utenti: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = emptyList()) |

## Properties

| Name | Summary |
|---|---|
| [completato](completato.md) | [androidJvm]<br>val [completato](completato.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>Indica se l'attività è stata completata. |
| [dataCreazione](data-creazione.md) | [androidJvm]<br>val [dataCreazione](data-creazione.md): [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)<br>La data di creazione dell'attività. |
| [dataScadenza](data-scadenza.md) | [androidJvm]<br>val [dataScadenza](data-scadenza.md): [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)<br>La data di scadenza dell'attività. |
| [descrizione](descrizione.md) | [androidJvm]<br>val [descrizione](descrizione.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>La descrizione dettagliata dell'attività. |
| [fileUri](file-uri.md) | [androidJvm]<br>val [fileUri](file-uri.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>L'URI del file allegato all'attività (se presente). |
| [id](id.md) | [androidJvm]<br>val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>L'ID univoco dell'attività (generato automaticamente da Firestore). |
| [priorita](priorita.md) | [androidJvm]<br>val [priorita](priorita.md): [Priorita](../../com.example.teamsync.util/-priorita/index.md)<br>La priorità dell'attività (può essere NESSUNA, BASSA, MEDIA, ALTA). |
| [progetto](progetto.md) | [androidJvm]<br>val [progetto](progetto.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>L'ID del progetto a cui appartiene l'attività. |
| [titolo](titolo.md) | [androidJvm]<br>val [titolo](titolo.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Il titolo dell'attività. |
| [utenti](utenti.md) | [androidJvm]<br>val [utenti](utenti.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>La lista degli ID degli utenti assegnati all'attività. |
