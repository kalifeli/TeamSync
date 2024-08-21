//[app](../../../index.md)/[com.example.teamsync.caratteristiche.impostazioni.data.viewModel](../index.md)/[TerminiCondizioniViewModel](index.md)

# TerminiCondizioniViewModel

[androidJvm]\
class [TerminiCondizioniViewModel](index.md)(repository: [RepositoryTerms](../../com.example.teamsync.caratteristiche.impostazioni.data.repository/-repository-terms/index.md)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

ViewModel per la gestione della logica e dello stato della schermata dei Termini e Condizioni.

Questa classe è responsabile di recuperare i dati relativi ai Termini e Condizioni da un repository e di esporli alla UI. Utilizza [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html) per fornire dati osservabili alla UI in modo da aggiornare automaticamente la visualizzazione quando i dati cambiano.

## Constructors

| | |
|---|---|
| [TerminiCondizioniViewModel](-termini-condizioni-view-model.md) | [androidJvm]<br>constructor(repository: [RepositoryTerms](../../com.example.teamsync.caratteristiche.impostazioni.data.repository/-repository-terms/index.md)) |

## Properties

| Name | Summary |
|---|---|
| [error](error.md) | [androidJvm]<br>val [error](error.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?&gt; |
| [listaTermini](lista-termini.md) | [androidJvm]<br>val [listaTermini](lista-termini.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Terms](../../com.example.teamsync.caratteristiche.impostazioni.data.model/-terms/index.md)&gt;&gt; |
| [loading](loading.md) | [androidJvm]<br>val [loading](loading.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [ultimaModifica](ultima-modifica.md) | [androidJvm]<br>val [ultimaModifica](ultima-modifica.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Date](https://developer.android.com/reference/kotlin/java/util/Date.html)?&gt; |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](../../com.example.teamsync.caratteristiche.notifiche.data.viewModel/-view-model-notifiche/index.md#383812252%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [addCloseable](../../com.example.teamsync.caratteristiche.notifiche.data.viewModel/-view-model-notifiche/index.md#383812252%2FFunctions%2F-912451524)(closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html))<br>fun [addCloseable](../../com.example.teamsync.caratteristiche.notifiche.data.viewModel/-view-model-notifiche/index.md#1722490497%2FFunctions%2F-912451524)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)) |
| [getCloseable](../../com.example.teamsync.caratteristiche.notifiche.data.viewModel/-view-model-notifiche/index.md#1102255800%2FFunctions%2F-912451524) | [androidJvm]<br>fun &lt;[T](../../com.example.teamsync.caratteristiche.notifiche.data.viewModel/-view-model-notifiche/index.md#1102255800%2FFunctions%2F-912451524) : [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)&gt; [getCloseable](../../com.example.teamsync.caratteristiche.notifiche.data.viewModel/-view-model-notifiche/index.md#1102255800%2FFunctions%2F-912451524)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [T](../../com.example.teamsync.caratteristiche.notifiche.data.viewModel/-view-model-notifiche/index.md#1102255800%2FFunctions%2F-912451524)? |
