//[app](../../../index.md)/[com.example.teamsync.caratteristiche.impostazioni.data.viewModel](../index.md)/[FaqViewModel](index.md)

# FaqViewModel

class [FaqViewModel](index.md)(repository: [RepositoryFaq](../../com.example.teamsync.caratteristiche.impostazioni.data.repository/-repository-faq/index.md)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

ViewModel per la gestione della logica della schermata FAQ.

#### Parameters

androidJvm

| | |
|---|---|
| repository | Il repository per il recupero delle FAQ. |

## Constructors

| | |
|---|---|
| [FaqViewModel](-faq-view-model.md) | [androidJvm]<br>constructor(repository: [RepositoryFaq](../../com.example.teamsync.caratteristiche.impostazioni.data.repository/-repository-faq/index.md)) |

## Properties

| Name | Summary |
|---|---|
| [error](error.md) | [androidJvm]<br>val [error](error.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?&gt; |
| [listaFaq](lista-faq.md) | [androidJvm]<br>val [listaFaq](lista-faq.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Faq](../../com.example.teamsync.caratteristiche.impostazioni.data.model/-faq/index.md)&gt;&gt; |
| [loading](loading.md) | [androidJvm]<br>val [loading](loading.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](../../com.example.teamsync.caratteristiche.notifiche.data.viewModel/-view-model-notifiche/index.md#383812252%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [addCloseable](../../com.example.teamsync.caratteristiche.notifiche.data.viewModel/-view-model-notifiche/index.md#383812252%2FFunctions%2F-912451524)(closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html))<br>fun [addCloseable](../../com.example.teamsync.caratteristiche.notifiche.data.viewModel/-view-model-notifiche/index.md#1722490497%2FFunctions%2F-912451524)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)) |
| [caricaFaq](carica-faq.md) | [androidJvm]<br>fun [caricaFaq](carica-faq.md)()<br>Metodo per caricare le FAQ dalla repository. |
| [getCloseable](../../com.example.teamsync.caratteristiche.notifiche.data.viewModel/-view-model-notifiche/index.md#1102255800%2FFunctions%2F-912451524) | [androidJvm]<br>fun &lt;[T](../../com.example.teamsync.caratteristiche.notifiche.data.viewModel/-view-model-notifiche/index.md#1102255800%2FFunctions%2F-912451524) : [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)&gt; [getCloseable](../../com.example.teamsync.caratteristiche.notifiche.data.viewModel/-view-model-notifiche/index.md#1102255800%2FFunctions%2F-912451524)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [T](../../com.example.teamsync.caratteristiche.notifiche.data.viewModel/-view-model-notifiche/index.md#1102255800%2FFunctions%2F-912451524)? |
