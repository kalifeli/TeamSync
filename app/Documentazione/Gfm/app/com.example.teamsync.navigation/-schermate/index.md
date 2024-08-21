//[app](../../../index.md)/[com.example.teamsync.navigation](../index.md)/[Schermate](index.md)

# Schermate

sealed class [Schermate](index.md)

Sealed class che rappresenta le diverse schermate (o route) dell'applicazione.

Ogni oggetto all'interno della sealed class `Schermate` rappresenta una schermata specifica con una route univoca utilizzata nel sistema di navigazione dell'applicazione. Questo permette di definire in modo centralizzato tutte le schermate e le relative rotte, facilitando la gestione della navigazione all'interno dell'app.

#### Inheritors

| |
|---|
| [Benvenuto](-benvenuto/index.md) |
| [ItuoiProgetti](-ituoi-progetti/index.md) |
| [Login](-login/index.md) |
| [RecuperoPassword](-recupero-password/index.md) |
| [Registrazione](-registrazione/index.md) |
| [VerificaEmail](-verifica-email/index.md) |
| [Profilo](-profilo/index.md) |
| [Notifiche](-notifiche/index.md) |
| [ModificaProfilo](-modifica-profilo/index.md) |
| [Impostazioni](-impostazioni/index.md) |
| [Tema](-tema/index.md) |
| [Terms](-terms/index.md) |
| [Supporto](-supporto/index.md) |
| [ImpNotifche](-imp-notifche/index.md) |
| [Imptask](-imptask/index.md) |
| [ImpoProgetti](-impo-progetti/index.md) |

## Types

| Name | Summary |
|---|---|
| [Benvenuto](-benvenuto/index.md) | [androidJvm]<br>object [Benvenuto](-benvenuto/index.md) : [Schermate](index.md)<br>Schermata di benvenuto. Route: &quot;schermata_benvenuto&quot; |
| [ImpNotifche](-imp-notifche/index.md) | [androidJvm]<br>object [ImpNotifche](-imp-notifche/index.md) : [Schermate](index.md)<br>Schermata per la gestione delle notifiche. Route: &quot;Impostazioni_Notifiche&quot; |
| [ImpoProgetti](-impo-progetti/index.md) | [androidJvm]<br>object [ImpoProgetti](-impo-progetti/index.md) : [Schermate](index.md)<br>Schermata per la gestione dei progetti. Route: &quot;Impostazioni_Progetti&quot; |
| [Impostazioni](-impostazioni/index.md) | [androidJvm]<br>object [Impostazioni](-impostazioni/index.md) : [Schermate](index.md)<br>Schermata delle impostazioni. Route: &quot;Impostazioni&quot; |
| [Imptask](-imptask/index.md) | [androidJvm]<br>object [Imptask](-imptask/index.md) : [Schermate](index.md)<br>Schermata per la gestione dei task. Route: &quot;Impostazioni_Task&quot; |
| [ItuoiProgetti](-ituoi-progetti/index.md) | [androidJvm]<br>object [ItuoiProgetti](-ituoi-progetti/index.md) : [Schermate](index.md)<br>Schermata &quot;I tuoi Progetti&quot;. Route: &quot;schermata_progetti&quot; |
| [Login](-login/index.md) | [androidJvm]<br>object [Login](-login/index.md) : [Schermate](index.md)<br>Schermata di Login. Route: &quot;Login&quot; |
| [ModificaProfilo](-modifica-profilo/index.md) | [androidJvm]<br>object [ModificaProfilo](-modifica-profilo/index.md) : [Schermate](index.md)<br>Schermata per la modifica del profilo utente. Route: &quot;ModificaProfilo&quot; |
| [Notifiche](-notifiche/index.md) | [androidJvm]<br>object [Notifiche](-notifiche/index.md) : [Schermate](index.md)<br>Schermata delle notifiche. Route: &quot;Notifiche&quot; |
| [Profilo](-profilo/index.md) | [androidJvm]<br>object [Profilo](-profilo/index.md) : [Schermate](index.md)<br>Schermata del profilo utente. Route: &quot;Profilo&quot; |
| [RecuperoPassword](-recupero-password/index.md) | [androidJvm]<br>object [RecuperoPassword](-recupero-password/index.md) : [Schermate](index.md)<br>Schermata per il recupero della password. Route: &quot;schermata_RecuperoPassword&quot; |
| [Registrazione](-registrazione/index.md) | [androidJvm]<br>object [Registrazione](-registrazione/index.md) : [Schermate](index.md)<br>Schermata di registrazione. Route: &quot;Registrazione&quot; |
| [Supporto](-supporto/index.md) | [androidJvm]<br>object [Supporto](-supporto/index.md) : [Schermate](index.md)<br>Schermata di supporto. Route: &quot;supporto&quot; |
| [Tema](-tema/index.md) | [androidJvm]<br>object [Tema](-tema/index.md) : [Schermate](index.md)<br>Schermata per la gestione del tema. Route: &quot;tema&quot; |
| [Terms](-terms/index.md) | [androidJvm]<br>object [Terms](-terms/index.md) : [Schermate](index.md)<br>Schermata dei termini e condizioni. Route: &quot;terms&quot; |
| [VerificaEmail](-verifica-email/index.md) | [androidJvm]<br>object [VerificaEmail](-verifica-email/index.md) : [Schermate](index.md)<br>Schermata per la verifica dell'email. Route: &quot;Verifica_Email&quot; |

## Properties

| Name | Summary |
|---|---|
| [route](route.md) | [androidJvm]<br>val [route](route.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>La stringa che rappresenta la route associata alla schermata. |
