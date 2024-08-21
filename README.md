
# TeamSync

TeamSync è un'applicazione Android progettata per facilitare la collaborazione tra i membri di un team, gestendo progetti, attività e notifiche in modo efficace. Questa documentazione fornisce una panoramica del progetto, come generare l'applicazione, e una guida per l'utente.

## Sommario
- [Introduzione](#introduzione)
- [Requisiti di Sistema](#requisiti-di-sistema)
- [Installazione e Generazione dell'App](#installazione-e-generazione-dellapp)
- [Struttura del Progetto](#struttura-del-progetto)
- [Testing](#testing)
- [Guida per l'Utente](#guida-per-lutente)
- [Conclusioni](#conclusioni)

## Introduzione

TeamSync è stato sviluppato per gestire la comunicazione e l'organizzazione all'interno di un team di progetto. L'app permette agli utenti di creare progetti, assegnare attività, inviare notifiche e mantenere il flusso di lavoro sotto controllo. L'obiettivo era di sviluppare un'app semplice, ma funzionale, che potesse essere estesa e manutenuta facilmente nel tempo.

### Evoluzione del Progetto

All'inizio, il codice presentava una struttura rudimentale, con l'obiettivo principale di far funzionare l'applicazione rapidamente. Tuttavia, con l'avanzare del progetto, ci siamo concentrati sulla pulizia del codice, la leggibilità e la modularità, rivedendo la maggior parte dei file e migliorando la qualità del codice. Questi miglioramenti rendono ora l'applicazione più solida e facile da mantenere.

## Requisiti di Sistema

- **JDK** 11 o superiore
- **Android Studio** versione 4.2 o superiore
- **Gradle** 7.0 o superiore
- Connessione internet per gestire le dipendenze del progetto

## Installazione e Generazione dell'App

1. **Clona il Repository:**
   ```bash
   git clone https://github.com/tuo-utente/TeamSync.git
   cd TeamSync
   ```

2. **Apri il Progetto in Android Studio:**
   - Apri Android Studio.
   - Seleziona "Open an existing Android Studio project" e scegli la cartella `TeamSync`.

3. **Scarica le Dipendenze:**
   - Android Studio dovrebbe automaticamente scaricare tutte le dipendenze del progetto tramite Gradle.

4. **Genera l'APK:**
   - Vai su **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
   - Una volta completato, troverai il file APK nella directory `app/build/outputs/apk`.

5. **Esegui i Test (opzionale):**
   - Se desideri eseguire i test unitari, puoi farlo tramite Android Studio o con il comando:
     ```bash
     ./gradlew test
     ```

## Struttura del Progetto

Il progetto è organizzato nelle seguenti principali sezioni:

- **caratteristiche/notifiche**: Gestione delle notifiche, incluse le funzionalità di visualizzazione e aggiornamento delle notifiche.
- **caratteristiche/iTuoiProgetti**: Gestione dei progetti dell'utente, incluse la creazione e la modifica dei progetti.
- **caratteristiche/leMieAttivita**: Gestione delle attività dell'utente, incluse la creazione, modifica e completamento delle attività.
- **caratteristiche/autentificazione**: Gestione dell'autenticazione dell'utente, incluse la registrazione e il login.

## Testing

Abbiamo utilizzato diversi strumenti e librerie per garantire la qualità del codice:

- **JUnit**: per i test unitari.
- **MockK**: per il mocking dei repository e dei componenti non testati direttamente.
- **Kotlin Coroutines Test**: per gestire le coroutine nei test.

### Scelte di Testing

Per ridurre il tempo e la complessità, ci siamo concentrati principalmente sul testing dei ViewModel, che rappresentano la logica centrale dell'applicazione. Abbiamo utilizzato MockK per simulare il comportamento dei repository, scegliendo quali valori restituire dalle funzioni, senza testare direttamente i repository stessi.

### Difficoltà negli Android Test

I test Android sono stati la parte più complessa del progetto, con numerosi errori dovuti a meccanismi di sicurezza nel codice che impedivano agli utenti non autenticati di accedere alle funzionalità dell'app. Questo ha complicato i test dei componenti UI, poiché l'applicazione mostrava continuamente un'icona di caricamento senza permettere l'interazione con i pulsanti o i menu.

## Guida per l'Utente

### Navigazione Principale

1. **Login e Registrazione**: All'avvio dell'app, gli utenti devono registrarsi o autenticarsi per accedere alle funzionalità principali.
2. **Progetti**: Gli utenti possono creare, modificare e visualizzare i progetti ai quali sono assegnati.
3. **Attività**: Gli utenti possono gestire le loro attività all'interno dei progetti, segnando come completate o modificandole.
4. **Notifiche**: L'applicazione invia notifiche per informare gli utenti di aggiornamenti o nuove assegnazioni.

### Funzionalità Chiave

- **Creazione Progetti**: Gli utenti possono creare nuovi progetti, assegnare una priorità e definire una data di scadenza.
- **Gestione Attività**: Ogni progetto può avere diverse attività associate, che possono essere gestite direttamente dall'interfaccia utente.
- **Notifiche in Tempo Reale**: Le notifiche permettono agli utenti di essere sempre aggiornati sugli sviluppi dei progetti.

## Conclusioni

TeamSync rappresenta un valido strumento per la gestione del lavoro in team, grazie alla sua capacità di gestire progetti, attività e notifiche. Nonostante le sfide tecniche, come la gestione dei test, l'applicazione è ora stabile e pronta per essere utilizzata e ampliata in futuro.

