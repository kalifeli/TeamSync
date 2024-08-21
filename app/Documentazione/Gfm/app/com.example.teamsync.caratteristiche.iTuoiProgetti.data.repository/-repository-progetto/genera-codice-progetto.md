//[app](../../../index.md)/[com.example.teamsync.caratteristiche.iTuoiProgetti.data.repository](../index.md)/[RepositoryProgetto](index.md)/[generaCodiceProgetto](genera-codice-progetto.md)

# generaCodiceProgetto

[androidJvm]\
fun [generaCodiceProgetto](genera-codice-progetto.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Funzione che genera un codice univoco per un progetto.

#### Return

Una stringa di 8 caratteri che rappresenta un codice univoco per il progetto.

La funzione utilizza la classe `UUID` per generare un UUID casuale e restituisce i primi 8 caratteri della rappresentazione in stringa dell'UUID. Questo codice può essere utilizzato per identificare in modo univoco un progetto.
