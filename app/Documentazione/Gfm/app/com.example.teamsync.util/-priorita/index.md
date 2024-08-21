//[app](../../../index.md)/[com.example.teamsync.util](../index.md)/[Priorita](index.md)

# Priorita

[androidJvm]\
enum [Priorita](index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[Priorita](index.md)&gt; 

Enum class che rappresenta i diversi livelli di priorità per le attività o i task. Ogni livello di priorità è associato a un colore specifico che può essere utilizzato per visualizzare graficamente l'importanza di un task.

## Entries

| | |
|---|---|
| [ALTA](-a-l-t-a/index.md) | [androidJvm]<br>[ALTA](-a-l-t-a/index.md)<br>Priorità alta, rappresentata dal colore associato a un livello di urgenza elevato. |
| [MEDIA](-m-e-d-i-a/index.md) | [androidJvm]<br>[MEDIA](-m-e-d-i-a/index.md)<br>Priorità media, rappresentata dal colore associato a un livello di urgenza moderato. |
| [BASSA](-b-a-s-s-a/index.md) | [androidJvm]<br>[BASSA](-b-a-s-s-a/index.md)<br>Priorità bassa, rappresentata dal colore associato a un livello di urgenza basso. |
| [NESSUNA](-n-e-s-s-u-n-a/index.md) | [androidJvm]<br>[NESSUNA](-n-e-s-s-u-n-a/index.md)<br>Nessuna priorità, rappresentata dal colore associato ad un'attività o progetto senza urgenza specifica. |

## Properties

| Name | Summary |
|---|---|
| [colore](colore.md) | [androidJvm]<br>val [colore](colore.md): [Color](https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/Color.html)<br>Il colore associato a ciascun livello di priorità. |
| [entries](entries.md) | [androidJvm]<br>val [entries](entries.md): [EnumEntries](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.enums/-enum-entries/index.html)&lt;[Priorita](index.md)&gt;<br>Returns a representation of an immutable list of all enum entries, in the order they're declared. |
| [name](-n-e-s-s-u-n-a/index.md#-372974862%2FProperties%2F-912451524) | [androidJvm]<br>val [name](-n-e-s-s-u-n-a/index.md#-372974862%2FProperties%2F-912451524): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [nomeResId](nome-res-id.md) | [androidJvm]<br>val [nomeResId](nome-res-id.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [ordinal](-n-e-s-s-u-n-a/index.md#-739389684%2FProperties%2F-912451524) | [androidJvm]<br>val [ordinal](-n-e-s-s-u-n-a/index.md#-739389684%2FProperties%2F-912451524): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

## Functions

| Name | Summary |
|---|---|
| [nomeTradotto](nome-tradotto.md) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [nomeTradotto](nome-tradotto.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Funzione per ottenere il nome tradotto della priorità |
| [valueOf](value-of.md) | [androidJvm]<br>fun [valueOf](value-of.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Priorita](index.md)<br>Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.md) | [androidJvm]<br>fun [values](values.md)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Priorita](index.md)&gt;<br>Returns an array containing the constants of this enum type, in the order they're declared. |
