//[app](../../../index.md)/[com.example.teamsync.caratteristiche.notifiche.data.viewModel](../index.md)/[ViewModelNotifiche](index.md)

# ViewModelNotifiche

[androidJvm]\
class [ViewModelNotifiche](index.md)(repositoryNotifiche: [RepositoryNotifiche](../../com.example.teamsync.caratteristiche.notifiche.data.repository/-repository-notifiche/index.md), viewModelUtente: [ViewModelUtente](../../com.example.teamsync.caratteristiche.autentificazione.data.viewModel/-view-model-utente/index.md)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

ViewModel per gestire le operazioni relative alle notifiche.

## Constructors

| | |
|---|---|
| [ViewModelNotifiche](-view-model-notifiche.md) | [androidJvm]<br>constructor(repositoryNotifiche: [RepositoryNotifiche](../../com.example.teamsync.caratteristiche.notifiche.data.repository/-repository-notifiche/index.md), viewModelUtente: [ViewModelUtente](../../com.example.teamsync.caratteristiche.autentificazione.data.viewModel/-view-model-utente/index.md)) |

## Properties

| Name | Summary |
|---|---|
| [eliminazioneNotificheStato](eliminazione-notifiche-stato.md) | [androidJvm]<br>var [eliminazioneNotificheStato](eliminazione-notifiche-stato.md): [MutableState](https://developer.android.com/reference/kotlin/androidx/compose/runtime/MutableState.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [erroreEliminazioneNotifiche](errore-eliminazione-notifiche.md) | [androidJvm]<br>var [erroreEliminazioneNotifiche](errore-eliminazione-notifiche.md): [MutableState](https://developer.android.com/reference/kotlin/androidx/compose/runtime/MutableState.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?&gt; |
| [erroreLetturaNotifiche](errore-lettura-notifiche.md) | [androidJvm]<br>var [erroreLetturaNotifiche](errore-lettura-notifiche.md): [MutableState](https://developer.android.com/reference/kotlin/androidx/compose/runtime/MutableState.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?&gt; |
| [isLoading](is-loading.md) | [androidJvm]<br>var [isLoading](is-loading.md): [MutableState](https://developer.android.com/reference/kotlin/androidx/compose/runtime/MutableState.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [letturaNotificheStato](lettura-notifiche-stato.md) | [androidJvm]<br>var [letturaNotificheStato](lettura-notifiche-stato.md): [MutableState](https://developer.android.com/reference/kotlin/androidx/compose/runtime/MutableState.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [notificheList](notifiche-list.md) | [androidJvm]<br>var [notificheList](notifiche-list.md): [MutableState](https://developer.android.com/reference/kotlin/androidx/compose/runtime/MutableState.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Notifiche](../../com.example.teamsync.caratteristiche.notifiche.data.model/-notifiche/index.md)&gt;&gt; |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](index.md#383812252%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [addCloseable](index.md#383812252%2FFunctions%2F-912451524)(closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html))<br>fun [addCloseable](index.md#1722490497%2FFunctions%2F-912451524)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)) |
| [cambiastatoAccettatoNotifica](cambiastato-accettato-notifica.md) | [androidJvm]<br>fun [cambiastatoAccettatoNotifica](cambiastato-accettato-notifica.md)(notificaId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Cambia lo stato di una notifica specifica a &quot;accettato&quot;. |
| [cambiaStatoNotifica](cambia-stato-notifica.md) | [androidJvm]<br>fun [cambiaStatoNotifica](cambia-stato-notifica.md)(notificaId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Cambia lo stato di una notifica specifica. |
| [controllaRichiestaAmicizia](controlla-richiesta-amicizia.md) | [androidJvm]<br>fun [controllaRichiestaAmicizia](controlla-richiesta-amicizia.md)(userId1: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), userId2: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), onResult: ([Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html))<br>Controlla se esiste una richiesta di amicizia tra due utenti specifici. |
| [creaNotifica](crea-notifica.md) | [androidJvm]<br>fun [creaNotifica](crea-notifica.md)(mittenteId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), destinatarioId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), tipo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), contenuto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), progetto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Crea una nuova notifica. |
| [eliminaNotifica](elimina-notifica.md) | [androidJvm]<br>fun [eliminaNotifica](elimina-notifica.md)(notificaId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Elimina una notifica specifica. |
| [eliminaNotificheAmicizia](elimina-notifiche-amicizia.md) | [androidJvm]<br>fun [eliminaNotificheAmicizia](elimina-notifiche-amicizia.md)(userId1: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), userId2: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Elimina tutte le notifiche di amicizia tra due utenti specificati. |
| [eliminaNotificheUtente](elimina-notifiche-utente.md) | [androidJvm]<br>fun [eliminaNotificheUtente](elimina-notifiche-utente.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Elimina tutte le notifiche di un utente specifico. |
| [fetchNotifiche](fetch-notifiche.md) | [androidJvm]<br>fun [fetchNotifiche](fetch-notifiche.md)()<br>Recupera le notifiche per l'utente corrente. |
| [getCloseable](index.md#1102255800%2FFunctions%2F-912451524) | [androidJvm]<br>fun &lt;[T](index.md#1102255800%2FFunctions%2F-912451524) : [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)&gt; [getCloseable](index.md#1102255800%2FFunctions%2F-912451524)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [T](index.md#1102255800%2FFunctions%2F-912451524)? |
| [getNotificaIdByContent](get-notifica-id-by-content.md) | [androidJvm]<br>fun [getNotificaIdByContent](get-notifica-id-by-content.md)(contenuto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), onResult: ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html))<br>Recupera l'ID di una notifica in base al contenuto specificato. |
| [resetEliminazioneNotificheStato](reset-eliminazione-notifiche-stato.md) | [androidJvm]<br>fun [resetEliminazioneNotificheStato](reset-eliminazione-notifiche-stato.md)()<br>Resetta lo stato di eliminazione delle notifiche. |
| [resetErroreEliminazioneNotifiche](reset-errore-eliminazione-notifiche.md) | [androidJvm]<br>fun [resetErroreEliminazioneNotifiche](reset-errore-eliminazione-notifiche.md)()<br>Resetta l'errore di eliminazione delle notifiche. |
| [resetErroreLetturaNotifiche](reset-errore-lettura-notifiche.md) | [androidJvm]<br>fun [resetErroreLetturaNotifiche](reset-errore-lettura-notifiche.md)()<br>Resetta l'errore di lettura delle notifiche. |
| [resetLetturaNotificheStato](reset-lettura-notifiche-stato.md) | [androidJvm]<br>fun [resetLetturaNotificheStato](reset-lettura-notifiche-stato.md)()<br>Resetta lo stato di lettura delle notifiche. |
