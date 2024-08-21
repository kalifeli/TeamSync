//[app](../../../index.md)/[com.example.teamsync.caratteristiche.notifiche.data.model](../index.md)/[Notifiche](index.md)

# Notifiche

[androidJvm]\
data class [Notifiche](index.md)(var mittente: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, var destinatario: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, var tipo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, var aperto: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, var contenuto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, var id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, var progettoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, var accettato: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val data: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html) = Date())

Data class che rappresenta una notifica nell'applicazione.

## Constructors

| | |
|---|---|
| [Notifiche](-notifiche.md) | [androidJvm]<br>constructor(mittente: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, destinatario: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, tipo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, aperto: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, contenuto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, progettoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, accettato: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, data: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html) = Date()) |

## Properties

| Name | Summary |
|---|---|
| [accettato](accettato.md) | [androidJvm]<br>var [accettato](accettato.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Stato dell'accettazione della notifica, se è stata accettata o meno. |
| [aperto](aperto.md) | [androidJvm]<br>var [aperto](aperto.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Stato della notifica, se è stata aperta o meno. |
| [contenuto](contenuto.md) | [androidJvm]<br>var [contenuto](contenuto.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Il contenuto della notifica. |
| [data](data.md) | [androidJvm]<br>val [data](data.md): [Date](https://developer.android.com/reference/kotlin/java/util/Date.html)<br>La data e l'ora in cui la notifica è stata creata. |
| [destinatario](destinatario.md) | [androidJvm]<br>var [destinatario](destinatario.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>L'ID dell'utente che ha ricevuto la notifica. |
| [id](id.md) | [androidJvm]<br>var [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>L'ID univoco della notifica. |
| [mittente](mittente.md) | [androidJvm]<br>var [mittente](mittente.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>L'ID dell'utente che ha inviato la notifica. |
| [progettoId](progetto-id.md) | [androidJvm]<br>var [progettoId](progetto-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>L'ID del progetto associato alla notifica. |
| [tipo](tipo.md) | [androidJvm]<br>var [tipo](tipo.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Il tipo di notifica (ad esempio, richiesta di amicizia, richiesta di progetto). |
