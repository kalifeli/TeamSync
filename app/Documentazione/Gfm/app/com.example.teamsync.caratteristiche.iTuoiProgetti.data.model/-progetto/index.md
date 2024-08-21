//[app](../../../index.md)/[com.example.teamsync.caratteristiche.iTuoiProgetti.data.model](../index.md)/[Progetto](index.md)

# Progetto

[androidJvm]\
data class [Progetto](index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val nome: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val descrizione: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = &quot;&quot;, val dataCreazione: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html) = Date(), val dataScadenza: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html) = Date(), val dataConsegna: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html) = dataScadenza, val priorita: [Priorita](../../com.example.teamsync.util/-priorita/index.md) = Priorita.NESSUNA, val partecipanti: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = emptyList(), val voto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;Non Valutato&quot;, val completato: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val codice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;)

Rappresenta un progetto in un sistema di gestione progetti.

## Constructors

| | |
|---|---|
| [Progetto](-progetto.md) | [androidJvm]<br>constructor(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, nome: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, descrizione: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = &quot;&quot;, dataCreazione: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html) = Date(), dataScadenza: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html) = Date(), dataConsegna: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html) = dataScadenza, priorita: [Priorita](../../com.example.teamsync.util/-priorita/index.md) = Priorita.NESSUNA, partecipanti: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = emptyList(), voto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;Non Valutato&quot;, completato: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, codice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;) |

## Properties

| Name | Summary |
|---|---|
| [codice](codice.md) | [androidJvm]<br>val [codice](codice.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Un codice univoco associato al progetto, utilizzato per identificare il progetto in modo univoco tra i partecipanti. |
| [completato](completato.md) | [androidJvm]<br>val [completato](completato.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>Indica se il progetto è stato completato. Il valore predefinito è `false`. |
| [dataConsegna](data-consegna.md) | [androidJvm]<br>val [dataConsegna](data-consegna.md): [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)<br>La data di consegna del progetto. |
| [dataCreazione](data-creazione.md) | [androidJvm]<br>val [dataCreazione](data-creazione.md): [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)<br>La data di creazione del progetto. Impostata automaticamente alla data corrente se non specificata. |
| [dataScadenza](data-scadenza.md) | [androidJvm]<br>val [dataScadenza](data-scadenza.md): [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)<br>La data di scadenza del progetto. Impostata automaticamente alla data di scadenza del progetto se non specificata. |
| [descrizione](descrizione.md) | [androidJvm]<br>val [descrizione](descrizione.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>Una descrizione opzionale del progetto. |
| [id](id.md) | [androidJvm]<br>val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>L'ID univoco del progetto, che corrisponde all'ID del documento in Firestore. |
| [nome](nome.md) | [androidJvm]<br>val [nome](nome.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Il nome del progetto. |
| [partecipanti](partecipanti.md) | [androidJvm]<br>val [partecipanti](partecipanti.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>Una lista di ID degli utenti partecipanti al progetto. |
| [priorita](priorita.md) | [androidJvm]<br>val [priorita](priorita.md): [Priorita](../../com.example.teamsync.util/-priorita/index.md)<br>La priorità del progetto, che può essere `NESSUNA`, `BASSA`, `MEDIA`, `ALTA`, ecc. |
| [voto](voto.md) | [androidJvm]<br>val [voto](voto.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Il voto assegnato al progetto, che può essere aggiornato al termine del progetto. Il valore predefinito è &quot;Non Valutato&quot;. |
