//[app](../../../index.md)/[com.example.teamsync.caratteristiche.notifiche.data.viewModel](../index.md)/[ViewModelNotifiche](index.md)/[controllaRichiestaAmicizia](controlla-richiesta-amicizia.md)

# controllaRichiestaAmicizia

[androidJvm]\
fun [controllaRichiestaAmicizia](controlla-richiesta-amicizia.md)(userId1: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), userId2: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), onResult: ([Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html))

Controlla se esiste una richiesta di amicizia tra due utenti specifici.

#### Parameters

androidJvm

| | |
|---|---|
| userId1 | L'ID del primo utente. |
| userId2 | L'ID del secondo utente. |
| onResult | Funzione di callback che verrà chiamata con `true` se esiste una richiesta di amicizia, `false` altrimenti. |
