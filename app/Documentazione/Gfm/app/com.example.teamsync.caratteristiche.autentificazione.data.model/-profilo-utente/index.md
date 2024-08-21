//[app](../../../index.md)/[com.example.teamsync.caratteristiche.autentificazione.data.model](../index.md)/[ProfiloUtente](index.md)

# ProfiloUtente

[androidJvm]\
data class [ProfiloUtente](index.md)(var id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, var nome: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, var cognome: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, var matricola: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, var dataDiNascita: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html) = Date(), var sesso: [SessoUtente](../-sesso-utente/index.md) = SessoUtente.UOMO, var email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, var primoAccesso: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, val immagine: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, var amici: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = emptyList())

Classe dati che rappresenta il profilo di un utente.

## Constructors

| | |
|---|---|
| [ProfiloUtente](-profilo-utente.md) | [androidJvm]<br>constructor(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, nome: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, cognome: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, matricola: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, dataDiNascita: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html) = Date(), sesso: [SessoUtente](../-sesso-utente/index.md) = SessoUtente.UOMO, email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, primoAccesso: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, immagine: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, amici: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = emptyList()) |

## Properties

| Name | Summary |
|---|---|
| [amici](amici.md) | [androidJvm]<br>var [amici](amici.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>Una lista di ID degli amici dell'utente. |
| [cognome](cognome.md) | [androidJvm]<br>var [cognome](cognome.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Il cognome dell'utente. |
| [dataDiNascita](data-di-nascita.md) | [androidJvm]<br>var [dataDiNascita](data-di-nascita.md): [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)<br>La data di nascita dell'utente. |
| [email](email.md) | [androidJvm]<br>var [email](email.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>L'indirizzo email dell'utente. |
| [id](id.md) | [androidJvm]<br>var [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>L'ID univoco dell'utente. |
| [immagine](immagine.md) | [androidJvm]<br>val [immagine](immagine.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null<br>L'URL dell'immagine del profilo dell'utente. Può essere null. |
| [matricola](matricola.md) | [androidJvm]<br>var [matricola](matricola.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>La matricola dell'utente (ad esempio, per studenti universitari). |
| [nome](nome.md) | [androidJvm]<br>var [nome](nome.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Il nome dell'utente. |
| [primoAccesso](primo-accesso.md) | [androidJvm]<br>var [primoAccesso](primo-accesso.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Indica se è il primo accesso dell'utente all'applicazione. |
| [sesso](sesso.md) | [androidJvm]<br>var [sesso](sesso.md): [SessoUtente](../-sesso-utente/index.md)<br>Il sesso dell'utente. Può essere UOMO, DONNA, o ALTRO. |
