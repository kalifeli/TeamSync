//[app](../../index.md)/[com.example.teamsync.caratteristiche.autentificazione.data.model](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [ProfiloUtente](-profilo-utente/index.md) | [androidJvm]<br>data class [ProfiloUtente](-profilo-utente/index.md)(var id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, var nome: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, var cognome: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, var matricola: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, var dataDiNascita: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html) = Date(), var sesso: [SessoUtente](-sesso-utente/index.md) = SessoUtente.UOMO, var email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, var primoAccesso: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, val immagine: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, var amici: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = emptyList())<br>Classe dati che rappresenta il profilo di un utente. |
| [SessoUtente](-sesso-utente/index.md) | [androidJvm]<br>enum [SessoUtente](-sesso-utente/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[SessoUtente](-sesso-utente/index.md)&gt; <br>Enumerazione che rappresenta il sesso dell'utente. Ogni valore ha associata una risorsa di stringa per la traduzione. |
