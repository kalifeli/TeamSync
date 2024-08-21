//[app](../../../index.md)/[com.example.teamsync.caratteristiche.autentificazione.data.model](../index.md)/[SessoUtente](index.md)

# SessoUtente

[androidJvm]\
enum [SessoUtente](index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[SessoUtente](index.md)&gt; 

Enumerazione che rappresenta il sesso dell'utente. Ogni valore ha associata una risorsa di stringa per la traduzione.

## Entries

| | |
|---|---|
| [UOMO](-u-o-m-o/index.md) | [androidJvm]<br>[UOMO](-u-o-m-o/index.md) |
| [DONNA](-d-o-n-n-a/index.md) | [androidJvm]<br>[DONNA](-d-o-n-n-a/index.md) |
| [ALTRO](-a-l-t-r-o/index.md) | [androidJvm]<br>[ALTRO](-a-l-t-r-o/index.md) |

## Properties

| Name | Summary |
|---|---|
| [entries](entries.md) | [androidJvm]<br>val [entries](entries.md): [EnumEntries](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.enums/-enum-entries/index.html)&lt;[SessoUtente](index.md)&gt;<br>Returns a representation of an immutable list of all enum entries, in the order they're declared. |
| [name](../../com.example.teamsync.util/-priorita/-n-e-s-s-u-n-a/index.md#-372974862%2FProperties%2F-912451524) | [androidJvm]<br>val [name](../../com.example.teamsync.util/-priorita/-n-e-s-s-u-n-a/index.md#-372974862%2FProperties%2F-912451524): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [ordinal](../../com.example.teamsync.util/-priorita/-n-e-s-s-u-n-a/index.md#-739389684%2FProperties%2F-912451524) | [androidJvm]<br>val [ordinal](../../com.example.teamsync.util/-priorita/-n-e-s-s-u-n-a/index.md#-739389684%2FProperties%2F-912451524): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [resId](res-id.md) | [androidJvm]<br>val [resId](res-id.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>L'ID della risorsa stringa associata per la traduzione del valore dell'enumerazione. |

## Functions

| Name | Summary |
|---|---|
| [getSessoTradotto](get-sesso-tradotto.md) | [androidJvm]<br>fun [getSessoTradotto](get-sesso-tradotto.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), sesso: [SessoUtente](index.md)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [valueOf](value-of.md) | [androidJvm]<br>fun [valueOf](value-of.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [SessoUtente](index.md)<br>Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.md) | [androidJvm]<br>fun [values](values.md)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[SessoUtente](index.md)&gt;<br>Returns an array containing the constants of this enum type, in the order they're declared. |
