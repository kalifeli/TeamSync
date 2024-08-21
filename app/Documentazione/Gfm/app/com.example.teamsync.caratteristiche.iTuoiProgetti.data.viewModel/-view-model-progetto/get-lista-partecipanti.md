//[app](../../../index.md)/[com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel](../index.md)/[ViewModelProgetto](index.md)/[getListaPartecipanti](get-lista-partecipanti.md)

# getListaPartecipanti

[androidJvm]\
suspend fun [getListaPartecipanti](get-lista-partecipanti.md)(idProgetto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;

Recupera la lista dei partecipanti a un progetto.

#### Return

La lista dei partecipanti.

#### Parameters

androidJvm

| | |
|---|---|
| idProgetto | L'ID del progetto. |

[androidJvm]\
suspend fun [getListaPartecipanti](get-lista-partecipanti.md)(idProgetto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), callback: ([List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html))

Recupera la lista dei partecipanti a un progetto con callback.

#### Parameters

androidJvm

| | |
|---|---|
| idProgetto | L'ID del progetto. |
| callback | La funzione di callback da eseguire. |

[androidJvm]\
suspend fun [getListaPartecipanti](get-lista-partecipanti.md)(idProgetto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), utenteCorrenteId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;

Recupera la lista dei partecipanti a un progetto filtrata dall'utente corrente.

#### Return

La lista dei partecipanti filtrata.

#### Parameters

androidJvm

| | |
|---|---|
| idProgetto | L'ID del progetto. |
| utenteCorrenteId | L'ID dell'utente corrente. |
