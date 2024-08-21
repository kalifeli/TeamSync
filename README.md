# Sommario {#sommario .list-paragraph .TOC-Heading}

[1 Introduzione [3](#introduzione)](#introduzione)

[1.1 Origine e Obiettivi di TeamSync
[3](#origine-e-obiettivi-di-teamsync)](#origine-e-obiettivi-di-teamsync)

[2 Funzionalità principali
[3](#funzionalità-principali)](#funzionalità-principali)

[2.1 Glossario [4](#glossario)](#glossario)

[3 Kotlin App [6](#kotlin-app)](#kotlin-app)

[3.1 Requisiti [6](#requisiti)](#requisiti)

[3.1.1 Requisiti Funzionali
[7](#requisiti-funzionali)](#requisiti-funzionali)

[3.1.2 Requisiti Non Funzionali
[14](#requisiti-non-funzionali)](#requisiti-non-funzionali)

[3.2 Casi d'Uso [15](#casi-duso)](#casi-duso)

[3.2.1 Attori [15](#attori)](#attori)

[3.2.2 Indice dei casi d'uso
[16](#indice-dei-casi-duso)](#indice-dei-casi-duso)

[3.2.3 Gestione dell'Autenticazione e Registrazione Utente
[19](#gestione-dellautenticazione-e-registrazione-utente)](#gestione-dellautenticazione-e-registrazione-utente)

[3.2.4 Gestione dei Progetti
[22](#gestione-dei-progetti)](#gestione-dei-progetti)

[3.2.5 Gestione delle Attività
[25](#gestione-delle-attività)](#gestione-delle-attività)

[3.2.6 Gestione del Profilo Utente
[29](#gestione-del-profilo-utente)](#gestione-del-profilo-utente)

[3.2.7 Gestione degli Amici
[32](#gestione-degli-amici)](#gestione-degli-amici)

[3.2.8 Gestione delle Notifiche
[33](#gestione-delle-notifiche)](#gestione-delle-notifiche)

[3.2.9 Gestione delle Impostazioni
[34](#gestione-delle-impostazioni)](#gestione-delle-impostazioni)

[3.3 Architettura dell'applicazione
[34](#architettura-dellapplicazione)](#architettura-dellapplicazione)

[3.3.1 Componenti del Pattern MVVM:
[34](#componenti-del-pattern-mvvm)](#componenti-del-pattern-mvvm)

[3.3.2 Struttura del Progetto
[36](#struttura-del-progetto)](#struttura-del-progetto)

[3.4 Data Storage [37](#data-storage)](#data-storage)

[3.4.1 Database (Firebase) [37](#database-firebase)](#database-firebase)

[3.4.2 Shared Preferences
[48](#shared-preferences)](#shared-preferences)

[3.5 MockUp UI [49](#mockup-ui)](#mockup-ui)

[3.5.1 Progettazione preliminare
[49](#progettazione-preliminare)](#progettazione-preliminare)

[3.5.2 Realizzazione effettiva
[54](#realizzazione-effettiva)](#realizzazione-effettiva)

[3.6 Approfondimenti sul codice
[65](#approfondimenti-sul-codice)](#approfondimenti-sul-codice)

[3.6.1 Creazione e Gestione degli Utenti
[66](#creazione-e-gestione-degli-utenti)](#creazione-e-gestione-degli-utenti)

[3.6.2 Creazione e Gestione dei Progetti
[67](#creazione-e-gestione-dei-progetti)](#creazione-e-gestione-dei-progetti)

[3.6.3 Creazione e Gestione delle Attività
[70](#creazione-e-gestione-delle-attività)](#creazione-e-gestione-delle-attività)

[3.6.4 Creazione e Gestione delle Notifiche
[73](#creazione-e-gestione-delle-notifiche)](#creazione-e-gestione-delle-notifiche)

[3.7 Testing [75](#testing)](#testing)

[3.7.1 Unit Test [75](#unit-test)](#unit-test)

[3.7.2 Android Test [79](#android-test)](#android-test)

[3.8 Pulizia del codice [80](#pulizia-del-codice)](#pulizia-del-codice)

###  {#section .list-paragraph}

# Introduzione

Nel contesto moderno dell\'istruzione superiore, il lavoro di squadra è
diventato una componente essenziale per lo sviluppo delle competenze
degli studenti. Con il crescere delle esigenze di collaborazione, è
fondamentale avere strumenti che facilitino la gestione di progetti, la
comunicazione tra i membri del team e la condivisione delle
risorse. **TeamSync** è un\'applicazione concepita per rispondere a
queste esigenze, offrendo un ambiente integrato per la gestione dei
progetti di gruppo. Sviluppata con un focus particolare sugli studenti
dell\'Università Politecnica delle Marche, TeamSync mira a semplificare
e potenziare la collaborazione accademica attraverso funzionalità
avanzate di gestione e coordinamento dei progetti.

## Origine e Obiettivi di TeamSync

TeamSync nasce dalla necessità di fornire agli studenti universitari uno
strumento che li aiuti a coordinare i loro sforzi nei progetti di
gruppo. L'idea è emersa durante la preparazione di alcuni esami
universitari che richiedevano un lavoro di gruppo. Da queste piccole
esperienze è emersa la necessità di una piattaforma centralizzata per la
gestione delle attività accademiche collaborative.

Gli obiettivi principali di TeamSync sono:

-   **Facilitare la gestione dei progetti**: permettendo agli studenti
    di creare, assegnare e monitorare le attività all\'interno dei loro
    gruppi di lavoro.

-   **Migliorare la comunicazione**: offrendo strumenti integrati per la
    condivisione di documenti, la pianificazione delle scadenze e
    l\'interazione tra i membri del team.

-   **Ottimizzare l\'organizzazione del lavoro**: grazie a funzionalità
    come la gestione delle priorità, la pianificazione delle attività e
    il monitoraggio dei progressi.

# Funzionalità principali

**Creazione e Gestione dei Progetti**\
Con TeamSync, gli utenti possono creare nuovi progetti in modo semplice
e intuitivo. Durante la creazione di un progetto, è possibile definire
un nome, una descrizione, una data di scadenza e una priorità. Questi
parametri aiutano a organizzare i progetti in base alla loro importanza
e alla scadenza temporale. Una volta creato, il progetto può essere
visualizzato in una schermata dedicata che mostra tutti i dettagli
relativi al progetto, comprese le attività assegnate e lo stato di
avanzamento.

**Gestione delle Attività**\
All\'interno di ciascun progetto, gli utenti possono creare e assegnare
attività specifiche ai membri del team. Ogni attività può includere una
descrizione dettagliata, una data di scadenza e una priorità. Gli utenti
possono aggiornare lo stato delle attività il che permette a tutti i
membri del team di rimanere informati sui progressi in tempo reale. Le
attività possono essere visualizzate in modo chiaro e organizzato,
facilitando la gestione del carico di lavoro individuale e collettivo.

**Sincronizzazione in Tempo Reale**\
Una delle caratteristiche chiave di TeamSync è la capacità di
sincronizzare i dati in tempo reale. Quando un utente aggiorna un
progetto o un\'attività, queste modifiche vengono immediatamente
sincronizzate con tutti gli altri membri del team. Questo garantisce che
tutti i membri siano sempre allineati sugli obiettivi del progetto e
sulle scadenze imminenti, riducendo il rischio di malintesi o di lavoro
duplicato.

**Gestione del Profilo Utente**\
TeamSync consente agli utenti di gestire il proprio profilo,
personalizzando le informazioni e le preferenze. L\'app include
funzionalità di autenticazione sicura, che permette agli utenti di
accedere in modo protetto e personalizzato. Le informazioni del profilo
utente sono utilizzate per assegnare correttamente le attività e i ruoli
all\'interno dei progetti.

**Visualizzazione dei Progressi**\
Un altro elemento centrale di TeamSync è la possibilità di monitorare i
progressi del team attraverso visualizzazioni grafiche e statistiche.
Gli utenti possono vedere quante attività sono state completate, quante
sono in corso e quante devono ancora essere iniziate. Questo fornisce
una chiara panoramica dello stato attuale del progetto e aiuta a
identificare eventuali ritardi o problemi che richiedono attenzione.

**Tema Scuro e Personalizzazione dell\'Interfaccia**\
Per migliorare l\'esperienza dell\'utente, TeamSync supporta la modalità
tema scuro, offrendo un\'interfaccia visivamente piacevole e meno
affaticante per gli occhi, specialmente in condizioni di scarsa
illuminazione. Gli utenti possono scegliere tra diverse opzioni di
personalizzazione dell\'interfaccia per adattare l\'app alle loro
preferenze personali.

## Glossario

  -----------------------------------------------------------------------
  **Termine**             **Descrizione**         **Sinonimo**
  ----------------------- ----------------------- -----------------------
  Progetto                Un insieme di attività  Compito; Iniziativa;
                          o compiti organizzati   
                          con lo scopo di         
                          raggiungere un          
                          obiettivi specifico     
                          entro un determinato    
                          periodo di tempo        

  Attività                Una singola unità di    Compito; Task; To-Do
                          lavoro all\'interno di  
                          un progetto. Le         
                          attività possono essere 
                          assegnate a membri      
                          specifici del team e    
                          includono dettagli come 
                          descrizione, data di    
                          scadenza e priorità.    

  Priorità                Un livello di           Importanza; Urgenza;
                          importanza assegnato a  Precedenza
                          un progetto o a         
                          un\'attività, che       
                          determina l\'ordine in  
                          cui dovrebbero essere   
                          completati.             

  Data di Scadenza        La data entro la quale  Scadenza; Termine; Data
                          un\'attività o un       Limite;
                          progetto devono essere  
                          completati.             

  Sincronizzazione in     Il processo di          Aggiornamento in Tempo
  tempo reale             aggiornamento           Reale; Sincronizzazione
                          automatico dei dati su  Immediata
                          tutti i dispositivi dei 
                          membri del team non     
                          appena vengono          
                          effettuate modifiche,   
                          assicurando che tutti   
                          abbiano accesso alle    
                          informazioni più        
                          recenti.                

  Profilo Utente          Un insieme di           Account Utente;
                          informazioni personali  Identità Utente
                          e di preferenze salvate 
                          per ogni utente,        
                          utilizzate per          
                          personalizzare          
                          l\'esperienza           
                          all\'interno            
                          dell\'applicazione.     

  Autentificazione Sicura Un processo di verifica Login Sicuro; Accesso
                          che assicura che solo   Protetto
                          gli utenti autorizzati  
                          possano accedere al     
                          proprio account e ai    
                          dati all\'interno       
                          dell\'app.              

  Collaborazione          Lavoro congiunto di più Lavoro di Gruppo;
                          persone su un progetto  Cooperazione
                          o un\'attività,         
                          facilitato da strumenti 
                          che permettono la       
                          condivisione di         
                          informazioni e          
                          comunicazione in tempo  
                          reale.                  

  Visualizzazione dei     Rappresentazioni        Monitoraggio dei
  Progressi               grafiche o statistiche  Progressi;
                          che mostrano lo stato   Visualizzazione dello
                          di avanzamento di un    Stato
                          progetto o delle sue    
                          attività, facilitando   
                          il monitoraggio delle   
                          prestazioni e           
                          l\'individuazione di    
                          eventuali problemi.     

  Tema Scuro              Un\'opzione di          Modalità Notte; Dark
                          visualizzazione che     Mode
                          cambia i colori         
                          dell\'interfaccia       
                          utente a toni più       
                          scuri, riducendo        
                          l\'affaticamento degli  
                          occhi e migliorando     
                          l\'usabilità in         
                          ambienti con poca luce. 

  Interfaccia Utente (UI) L\'insieme degli        UI; Interfaccia
                          elementi visivi e       Grafica; Layout Utente
                          interattivi             
                          dell\'applicazione con  
                          cui l\'utente           
                          interagisce, come       
                          pulsanti, menu e        
                          schermate.              
  -----------------------------------------------------------------------

# Kotlin App

Questa sezione descrive in dettaglio la fase di progettazione e sviluppo
dell\'applicazione TeamSync utilizzando il linguaggio Kotlin. Si inizia
con un\'analisi completa dei requisiti e dei casi d\'uso, che delineano
le funzionalità essenziali e le interazioni principali degli utenti con
l\'app. Successivamente, viene presentata una mappa dell\'architettura,
che illustra la struttura complessiva dell\'applicazione, seguita dalla
progettazione del sistema di data storage, essenziale per la gestione e
la persistenza dei dati. I mockup dell\'interfaccia utente forniscono
una visualizzazione delle schermate principali, mentre l\'approccio
adottato nello sviluppo e le problematiche affrontate vengono discussi
in modo critico. La sezione conclude con una descrizione dettagliata dei
componenti principali dell\'applicazione e dei test eseguiti per
garantirne la qualità e la robustezza.

## Requisiti

In questa sezione vengono analizzati i requisiti fondamentali
dell\'applicazione TeamSync, suddivisi in requisiti funzionali e non
funzionali. Per una rappresentazione chiara e gerarchica, è stato
adottato il modello *Two Level Requirement Hierarchy*, che consente di
organizzare i requisiti in maniera strutturata, mettendo in evidenza le
funzionalità principali dell\'applicazione e le loro sotto-componenti.
Questo approccio facilita la gestione e la comprensione dei requisiti,
assicurando una maggiore coerenza e chiarezza durante le fasi di
sviluppo e implementazione.

### Requisiti Funzionali

Questa sezione elenca i requisiti funzionali dell\'applicazione
TeamSync, descrivendo in dettaglio le funzionalità che l\'app deve
implementare per rispondere alle necessità degli utenti. Utilizzando il
modello gerarchico a due livelli, ogni requisito viene suddiviso in
funzionalità di alto livello e dettagli operativi, permettendo una
visione più completa e strutturata delle capacità richieste. Questo
approccio garantisce una migliore pianificazione e realizzazione delle
funzionalità durante lo sviluppo dell\'applicazione.

**RF1: Autentificazione e Registrazione Utente**

Questa funzionalità permette agli utenti di registrarsi
all\'applicazione e autenticarsi per accedere alle varie funzionalità.
Include la gestione delle credenziali utente per garantire un accesso
sicuro.

-   **RF1.1: Autentificazione Utente**\
    Consente agli utenti di effettuare il login utilizzando email e
    password, garantendo che solo gli utenti autorizzati possano
    accedere alle funzionalità dell\'applicazione.

-   **RF1.2: Recupero della password in caso di smarrimento**\
    Fornisce un meccanismo per recuperare la password in caso di
    smarrimento. Gli utenti possono richiedere il reset della password
    tramite email, assicurando che possano sempre accedere al proprio
    account.

-   **RF1.2.1: Verifica dell\'Account**\
    Una funzionalità di sicurezza che richiede agli utenti di verificare
    il proprio account tramite email, assicurando che l\'account
    appartenga a una persona reale.

**RF2: Visualizzazione Tutorial**

Questa funzionalità permette agli utenti di accedere a un tutorial che
fornisce una guida sulle funzionalità dell'applicazione, facilitando
l\'on-boarding di nuovi utenti.

**RF3: Gestione dei Progetti**

Questa funzione permette agli utenti di creare, modificare, visualizzare
e gestire i progetti all\'interno dell\'applicazione. Include
funzionalità per accedere ai progetti esistenti e visualizzare le
informazioni principali relative ai progetti.

-   **RF3.1: Creazione di Progetti**\
    Consente agli utenti di creare nuovi progetti specificando dettagli
    come il nome del progetto, la descrizione, la data di scadenza e
    altri parametri rilevanti, impostando le basi per la gestione del
    progetto.

-   **RF3.2: Modifica dei Progetti**\
    Permette agli utenti di modificare i dettagli di un progetto
    esistente, come il nome, la descrizione, la data di scadenza, e
    altri elementi, consentendo l\'aggiornamento delle informazioni del
    progetto in corso.

-   **RF3.3: Visualizzazione dei Progetti**\
    Fornisce una vista centralizzata dove gli utenti possono vedere
    tutti i progetti a cui stanno lavorando, con dettagli sullo stato di
    avanzamento, scadenze e membri del team coinvolti, facilitando il
    monitoraggio dei progetti.

-   **RF3.4: Accesso ad un Progetto Esistente**\
    Consente agli utenti di accedere a un progetto esistente, tramite
    invito diretto o codice invito, per visualizzare o collaborare sul
    progetto in corso.

    -   **RF3.4.1: Accettazione di inviti a progetti dal centro
        notifiche**\
        Gestisce la ricezione e l\'accettazione di inviti a progetti
        direttamente dal centro notifiche dell\'applicazione,
        facilitando l\'accesso rapido ai progetti condivisi.

    -   **RF3.4.2: Accesso diretto tramite codice univoco
        condivisibile**\
        Permette agli utenti di accedere ai progetti esistenti
        utilizzando un codice univoco condivisibile, rendendo più facile
        la collaborazione tra membri del team.

**RF4: Gestione delle Attività**

Questa funzione consente la creazione, visualizzazione, modifica e
gestione delle attività all\'interno di un progetto. Include la
possibilità di delegare, completare ed eliminare attività, garantendo
una gestione efficiente delle responsabilità.

-   **RF4.1: Creazione di un'attività**\
    Gli utenti possono creare nuove attività all\'interno di un
    progetto, specificando dettagli come il titolo, la descrizione, la
    scadenza e la priorità, aggiungendo nuovi compiti al progetto.

-   **RF4.2: Visualizzazione dettagli attività**\
    Fornisce una vista dettagliata delle attività esistenti, inclusi i
    progressi, la descrizione, le scadenze e i membri assegnati,
    permettendo una gestione chiara delle attività.

-   **RF4.3: Modifica di un'attività**\
    Consente agli utenti di aggiornare o modificare i dettagli di
    un'attività esistente, come lo stato, la descrizione, la data di
    scadenza o l'aggiunta di un file.

    -   **RF4.3.1: Delega di un'attività**\
        Permette agli utenti di delegare un'attività a un altro membro
        del team, assicurando che le responsabilità siano chiaramente
        definite e che i compiti vengano eseguiti da chi è più adatto.

    -   **RF4.3.2: Completamento di un'attività**\
        Gli utenti possono segnare un\'attività come completata,
        aggiornando lo stato dell\'attività e notificando il team dei
        progressi.

    -   **RF4.3.3: Eliminazione di un'attività**\
        Fornisce la possibilità di eliminare un'attività dal progetto,
        rimuovendola definitivamente dalla lista delle attività in
        corso, mantenendo l\'organizzazione del progetto.

    -   **RF4.3.4: Aggiunta di un File**\
        Permette agli utenti di aggiungere un file a un'attività,
        fornendo documentazione o risorse necessarie per completare il
        compito.

**RF5: Gestione del Profilo Utente**

Questa sezione si concentra sulle funzionalità che consentono agli
utenti di gestire e personalizzare il proprio profilo all\'interno
dell\'app.

-   **RF5.1 Autenticazione Sicura**\
    Questa funzionalità garantisce che gli utenti possano accedere al
    proprio profilo in modo sicuro tramite un sistema di autenticazione
    che utilizza email e password.

-   **RF5.2 Personalizzazione del Profilo**\
    Gli utenti possono personalizzare le informazioni del proprio
    profilo, tra cui immagine del profilo e informazioni personali.

    -   **RF5.2.1 Caricamento e aggiornamento dell\'immagine del
        profilo**\
        Consente agli utenti di caricare una nuova immagine del profilo
        o aggiornare quella esistente.

    -   **RF5.2.2 Modifica del nome, cognome e altre informazioni
        personali**\
        Questa funzionalità permette agli utenti di modificare il nome,
        cognome e altre informazioni personali registrate nel profilo.

-   **RF5.3 Ricerca e Aggiunta Studenti**\
    Gli utenti possono cercare altri studenti all\'interno dell\'app
    utilizzando diversi parametri di ricerca e inviare richieste di
    amicizia.

    -   **RF5.3.1 Ricerca tramite nome, email o matricola**\
        Consente agli utenti di trovare altri studenti utilizzando il
        loro nome, email o numero di matricola.

    -   **RF5.3.2 Invio di richieste di amicizia**\
        Una volta trovato lo studente desiderato, gli utenti possono
        inviare richieste di amicizia per aggiungerlo ai loro contatti.

-   **RF5.4 Eliminazione Profilo**\
    Questa funzionalità permette agli utenti di eliminare
    definitivamente il proprio profilo dall\'app, rimuovendo tutti i
    dati associati.

**RF6: Gestione delle Notifiche**

Questa sezione riguarda le funzionalità relative alla gestione delle
notifiche, che mantengono gli utenti aggiornati sugli eventi e le
attività all\'interno dell\'app.

-   **RF6.1: Visualizzazione delle Notifiche**\
    Gli utenti possono visualizzare tutte le notifiche ricevute, inclusi
    inviti ai progetti, aggiornamenti di attività e altro.

-   **RF6.2: Cancellazione delle Notifiche**\
    Gli utenti possono eliminare singole notifiche o tutte le notifiche
    ricevute, mantenendo la loro dashboard pulita e organizzata.

-   **RF6.3: Lettura delle Notifiche**\
    Questa funzionalità permette agli utenti di marcare le notifiche
    come lette o non lette, gestendo facilmente quali notifiche
    richiedono attenzione.

-   **RF6.4: Configurazione delle Notifiche**\
    Gli utenti possono configurare le impostazioni delle notifiche per
    decidere quali tipi di notifiche ricevere e come essere avvisati.

    -   **RF6.4.1: Configurazione del tipo di notifica**\
        Consente agli utenti di specificare quali tipi di notifiche
        vogliono ricevere in-app.

**RF7: Gestione delle Impostazioni**

Questa sezione permette agli utenti di personalizzare vari aspetti
dell\'app secondo le proprie preferenze.

-   **RF7.1: Modifica delle preferenze di notifica**\
    Gli utenti possono personalizzare le preferenze di notifica,
    decidendo quali notifiche ricevere (ad esempio relative ai progetti,
    alle richieste di amicizia o sugli aggiornamenti e sul completamento
    delle attività).

-   **RF7.2: Modifica delle preferenze di tema (modalità
    scura/chiara)**\
    Gli utenti possono scegliere tra tema chiaro o scuro, adattando
    l\'interfaccia dell\'app alle proprie preferenze di visualizzazione.

-   **RF7.3: Modifica delle preferenze delle Attività**\
    Permette agli utenti di personalizzare le preferenze relative alla
    gestione delle attività, come la modalità di visualizzazione.

-   **RF7.4: Modifica delle preferenze dei progetti**\
    Consente agli utenti di impostare preferenze specifiche per la
    gestione dei progetti, inclusi i criteri di ordinamento o di
    visualizzazione.

**RF8: Gestione degli Amici**

Questa sezione riguarda la gestione delle relazioni sociali all\'interno
dell\'app, permettendo agli utenti di connettersi e collaborare con
altri utenti.

-   **RF8.1: Ricerca e aggiunta amici**\
    Gli utenti possono cercare altri utenti all\'interno dell\'app e
    inviare richieste di amicizia per connettersi.

-   **RF8.2: Gestione delle richieste di amicizia**\
    Questa funzionalità permette agli utenti di gestire le richieste di
    amicizia ricevute, accettando o rifiutando le richieste.

    -   **RF8.2.1: Accettazione delle richieste di amicizia**\
        Consente agli utenti di accettare le richieste di amicizia
        ricevute, aggiungendo nuovi amici al loro elenco di contatti.

    -   **RF8.2.2: Rifiuto delle richieste di amicizia**\
        Permette agli utenti di rifiutare le richieste di amicizia non
        desiderate.

-   **RF8.3: Invito degli amici ai progetti**\
    Gli utenti possono invitare i propri amici a collaborare su
    specifici progetti all\'interno dell\'app.

-   **RF8.4: Rimozione degli amici**\
    Consente agli utenti di rimuovere amici dalla loro lista di
    contatti, interrompendo la connessione all\'interno dell\'app.

#### Diagramma dei requisiti funzionali

![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image1.emf){width="6.3in"
height="8.933333333333334in"}

![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image2.emf){width="6.3in"
height="8.915277777777778in"}

### Requisiti Non Funzionali

**RNF1. UI:**

L\'interfaccia utente deve essere semplice e intuitiva, in modo da
garantire una user experience ottimale. Gli utenti devono poter navigare
e utilizzare l\'applicazione senza difficoltà, anche se sono alle prime
armi con la tecnologia.

**RNF2. Sviluppo:**

L\'applicazione deve essere sviluppata utilizzando il linguaggio Kotlin,
sfruttando le sue capacità di garantire codice più sicuro e conciso
rispetto ad altri linguaggi come Java.

**RNF3. Architettura:**

Il pattern architetturale utilizzato deve essere MVVM
(Model-View-ViewModel) per separare chiaramente la logica di business
dalla logica di presentazione. Questo facilita la manutenzione del
codice e migliora la scalabilità del progetto.

**RNF4. Persistenza Dati Online:**

Le funzionalità di community devono essere gestite tramite un server
Firebase. Questo garantisce la sincronizzazione in tempo reale dei dati
tra gli utenti, permettendo loro di interagire e collaborare senza
ritardi.

#### Diagramma dei requisiti non funzionali

![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image3.emf){width="2.1532491251093613in"
height="2.2240102799650043in"}

## Casi d'Uso

La sezione dei casi d\'uso descrive in dettaglio come gli utenti
interagiscono con l\'applicazione \"TeamSync\" per raggiungere specifici
obiettivi. Ogni caso d\'uso rappresenta una funzione o una serie di
funzioni che un utente può eseguire all\'interno dell\'app, fornendo una
chiara visione di come l\'applicazione soddisfa le esigenze degli
utenti. Questi casi d\'uso sono essenziali per comprendere il flusso di
lavoro e per garantire che tutte le funzionalità siano implementate
secondo le aspettative degli utenti. La loro definizione e analisi
consentono di identificare le interazioni chiave tra l\'utente e il
sistema, delineando i requisiti funzionali dell\'applicazione.

### Attori

![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image4.emf){width="3.3278915135608047in"
height="2.2958869203849517in"}

Nel progetto **TeamSync**, abbiamo deciso di rappresentare
l\'interazione dell\'applicazione con il mondo esterno tramite due
attori principali: **Utente** e **Database/Server**. Questa scelta è
stata fatta per semplificare e chiarire l\'architettura del sistema,
concentrando l\'attenzione sugli elementi essenziali dell\'interazione
tra l\'utente e il sistema.

**Utente** rappresenta l\'interfaccia attraverso la quale l\'individuo
interagisce con l\'applicazione. Questo attore esegue tutte le
operazioni visibili nel sistema, come la registrazione,
l\'autenticazione, la gestione dei progetti e delle attività, la
gestione del profilo e la visualizzazione delle notifiche.

**Database/Server** comprende tutte le funzioni backend necessarie per
supportare le operazioni dell\'applicazione. Questo attore unificato
include il sistema di autenticazione, che gestisce la sicurezza
dell\'accesso, il sistema di gestione dei dati, che memorizza e recupera
tutte le informazioni rilevanti per l\'applicazione, e il sistema di
notifiche, che invia aggiornamenti e messaggi importanti agli utenti.

L\'aggregazione di queste funzionalità nel singolo
attore **Database/Server** consente di evitare un\'eccessiva
frammentazione e complessità nel diagramma dei casi d\'uso, rendendo più
chiara l\'interazione tra il front-end e il back-end. Questa
rappresentazione semplificata facilita anche la comprensione del flusso
di informazioni e delle dipendenze tra le diverse componenti del
sistema, mantenendo un alto livello di coerenza e chiarezza nel design.

Tuttavia, per garantire la chiarezza e la leggibilità del diagramma,
l\'attore **Database/Server** non viene collegato esplicitamente a tutti
i casi d\'uso. Questa scelta è stata fatta per evitare di appesantire il
diagramma con un eccessivo numero di collegamenti, che potrebbero
renderlo complesso e difficilmente interpretabile.

I collegamenti tra **Database/Server** e i casi d\'uso sono riportati
solo nei casi in cui il ruolo del server è particolarmente significativo
e non evidente, oppure in casi specifici dove l\'interazione con il
database differisce dalla norma. Questo approccio permette di mantenere
il diagramma pulito e focalizzato, facilitando la comprensione delle
relazioni principali e delle responsabilità dei vari attori senza
sacrificare la completezza dell\'informazione.

### Indice dei casi d'uso

In questa sezione, per ciascun caso d\'uso generale individuato nel
diagramma, verranno descritti in dettaglio [i principali]{.underline}
casi d\'uso specifici (sottolineati nell'indice). Ogni descrizione
fornirà una panoramica delle funzionalità chiave, delineando le
precondizioni, le post-condizioni e la sequenza di eventi principali o
alternative che caratterizzano ogni scenario. Questo approccio
permetterà di comprendere meglio le interazioni tra gli utenti e il
sistema, evidenziando i flussi operativi fondamentali per il corretto
funzionamento dell\'applicazione.

**Gestione dell\'Autenticazione e Registrazione Utente**

-   **CU1***: [Autentificazione Utente]{.underline} - Consente
    all\'utente di autenticarsi nell\'applicazione utilizzando
    credenziali come email e password.*

-   **CU2***[: Registrazione Utente]{.underline} - Permette a un nuovo
    utente di creare un account nell\'applicazione.*

-   **CU3***: [Recupero della Password]{.underline} - Fornisce un
    meccanismo per recuperare la password in caso di smarrimento.*

-   **CU4***: [Verifica dell\'Account]{.underline} - Richiede
    all\'utente di verificare il proprio account tramite email o codice
    di verifica.*

**Gestione dei Progetti**

-   **CU5***: [Creazione di un Progetto]{.underline} - Permette
    all\'utente di creare un nuovo progetto specificando dettagli come
    nome, descrizione e data di scadenza.*

-   **CU6***: [Modifica di un Progetto]{.underline} - Consente
    all\'utente di modificare i dettagli di un progetto esistente.*

-   **CU7***[: Visualizzazione di un Progetto]{.underline} - Fornisce
    una vista centralizzata per visualizzare i dettagli dei progetti a
    cui l\'utente partecipa.*

-   **CU8***: [Accesso ad un Progetto Esistente]{.underline} - Consente
    all\'utente di accedere a un progetto esistente tramite invito o
    codice condivisibile.*

-   **CU9***: Accettazione di un Invito ad un Progetto - Gestisce la
    ricezione e l\'accettazione di inviti a partecipare a progetti.*

-   **CU10***: Accesso Diretto tramite Codice Univoco - Permette
    l\'accesso a un progetto tramite un codice univoco.*

-   **CU11***: Uscire da un Progetto - Consente all\'utente di uscire da
    un progetto in cui è coinvolto​.*

**Gestione delle Attività**

-   **CU12***: [Creazione di un\'Attività]{.underline} - Consente
    all\'utente di creare una nuova attività all\'interno di un
    progetto.*

-   **CU13***: [Visualizzazione dei Dettagli di
    un\'Attività]{.underline} - Permette all\'utente di visualizzare i
    dettagli di un\'attività, inclusi i progressi e i membri assegnati.*

-   **CU14***: [Modifica di un\'Attività]{.underline} - Consente
    all\'utente di aggiornare o modificare i dettagli di un\'attività
    esistente.*

-   **CU15***: [Delega di un\'Attività]{.underline} - Permette
    all\'utente di delegare un\'attività a un altro membro del team.*

-   **CU16***: Completamento di un\'Attività - Consente all\'utente di
    segnare un\'attività come completata.*

-   **CU17***:* Eliminazione di un\'Attività *- Permette all\'utente di
    eliminare un\'attività dal progetto.*

-   **CU18***: [Aggiunta di un File ad un\'Attività]{.underline} -
    Consente all\'utente di aggiungere file a un\'attività per una
    gestione più completa​.*

-   **CU19***: Visualizzazione di un File -- Consente all'utente di
    visualizzare il file caricato in un'attività.*

**Gestione del Profilo Utente**

-   **CU20**[*:* Personalizzazione del Profilo]{.underline} *- Permette
    all\'utente di personalizzare il proprio profilo, ad esempio
    modificando il nome o l\'immagine.*

-   **CU21***[: Caricamento e Aggiornamento dell\'Immagine del
    Profilo]{.underline} - Consente all\'utente di caricare o aggiornare
    l\'immagine del profilo.*

-   **CU22***: [Ricerca e Aggiunta Studenti]{.underline} - Permette
    all\'utente di cercare altri studenti e aggiungerli come amici o
    collaboratori.*

-   **CU23***: Invio di Richieste di Amicizia - Consente all\'utente di
    inviare richieste di amicizia ad altri utenti.*

-   **CU24***[: Invio di Richieste di Partecipazione a
    Progetti]{.underline} - Permette all\'utente di inviare richieste
    per partecipare a progetti.*

-   **CU25***: [Eliminazione del Profilo]{.underline} - Consente
    all\'utente di eliminare il proprio profilo e tutti i dati
    associati​.*

**Gestione degli Amici**

-   **CU26***: Ricerca e Aggiunta Amici - Questo caso d\'uso consente
    all\'utente di cercare altri utenti nel sistema e inviare loro
    richieste di amicizia.*

-   **CU27***: [Visualizzazione Profilo Collega (include]{.underline})
    -- Consente di visualizzare le informazione di un collega e di
    osservare il numero di attività e progetti completati.*

-   **CU28***: Invio di Inviti a Partecipazione a Progetti - Consente
    all\'utente di invitare i propri amici a partecipare a progetti
    all\'interno dell\'applicazione, facilitando la collaborazione.*

-   **CU29***: [Rimozione degli Amici]{.underline} - Questo caso d\'uso
    permette all\'utente di rimuovere amici dalla propria lista di
    contatti.*

**Gestione delle Notifiche**

-   **CU30***: Visualizzazione della Notifica - Questo caso d\'uso
    consente all\'utente di visualizzare le notifiche ricevute. Le
    notifiche possono riguardare aggiornamenti relativi a progetti,
    attività o altre interazioni all\'interno dell\'app.*

-   **CU31***: Cancellazione delle Notifiche - Permette all\'utente di
    eliminare una o più notifiche, rimuovendole dalla lista delle
    notifiche visibili.*

-   **CU32***: Lettura delle Notifiche - Consente all\'utente di marcare
    una notifica come letta, cambiando il suo stato e rimuovendola
    dall\'elenco delle notifiche non lette.*

-   **CU33***: Configurazione delle Notifiche - Questo caso d\'uso
    permette all\'utente di personalizzare come e quando ricevere
    notifiche, ad esempio scegliendo il tipo di notifica o impostando
    preferenze generali.*

    -   *CU33.1: Configurazione del Tipo di Notifica (Include) - Questo
        caso d\'uso è incluso all\'interno della configurazione delle
        notifiche e permette all\'utente di scegliere quali tipi di
        notifiche desidera ricevere, come notifiche push, email, ecc.*

**Gestione delle Impostazioni**

-   **CU34***: Modifica Preferenze di Notifica - Questo caso d\'uso
    consente all\'utente di configurare le preferenze relative alle
    notifiche, decidendo quali notifiche ricevere.*

-   **CU35***: Modifica delle Preferenze di Tema (Modalità
    scura/chiara) - Permette all\'utente di scegliere e cambiare il tema
    dell\'applicazione tra modalità chiara e scura, migliorando
    l\'esperienza visiva in base alle condizioni di luce e alle
    preferenze personali.*

-   **CU36***: Modifica delle Preferenze delle Attività - Consente
    all\'utente di personalizzare le preferenze per la gestione delle
    attività, come la visualizzazione delle attività completate, le
    priorità predefinite, ecc.*

-   **CU37***: Modifica delle Preferenze dei Progetti - Questo caso
    d\'uso permette all\'utente di configurare le impostazioni
    predefinite per i progetti, come la visibilità, le impostazioni di
    privacy, e altre opzioni specifiche del progetto.*

### Gestione dell'Autenticazione e Registrazione Utente

![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image5.emf){width="5.67217738407699in"
height="3.379718941382327in"}

**CU1: Autentificazione Utente**

-   **Descrizione:** Questo caso d\'uso consente all\'utente di
    autenticarsi all\'interno del sistema. L\'autenticazione richiede
    l\'inserimento di credenziali valide (come email e password) per
    accedere alle funzionalità dell\'applicazione.

-   **Pre-condizioni:** L\'utente deve avere un account esistente con
    credenziali valide.

-   **Post-condizioni:** L\'utente è autenticato nel sistema e può
    accedere alle funzionalità dell\'app.

-   **Sequenza di eventi principali:**

    1.  L\'utente inserisce l\'email e la password nella schermata di
        login.

    2.  Il sistema invia le credenziali al Database/Server per la
        verifica.

    3.  Se le credenziali sono corrette, l\'utente viene autenticato e
        può accedere all\'applicazione.

    4.  Il sistema visualizza la schermata principale dell\'app.

-   **Sequenza degli eventi alternativi:**

    -   **Al punto 3:** Se le credenziali non sono corrette, il sistema
        notifica all\'utente che le informazioni inserite non sono
        valide. L\'utente può tentare nuovamente di inserire le
        credenziali oppure selezionare l\'opzione per il recupero della
        password.

**CU2: Registrazione Utente**

-   **Descrizione:** Questo caso d\'uso permette a un nuovo utente di
    registrarsi nel sistema creando un nuovo account. L\'utente deve
    fornire informazioni personali come matricola,nome, cognome , email,
    sesso, data di nascita e scegliere una password.

-   **Pre-condizioni:** Nessuna.

-   **Post-condizioni:** Un nuovo account utente è creato nel sistema e
    l\'utente è autenticato.

-   **Sequenza di eventi principali:**

    1.  L\'utente seleziona l\'opzione per la registrazione nella
        schermata principale.

    2.  L\'utente inserisce le informazioni richieste.

    3.  Il sistema invia i dati al Database/Server per la creazione del
        nuovo account.

    4.  Se i dati sono validi e l\'email non è già utilizzata, il
        sistema crea il nuovo account.

    5.  Il sistema invia una richiesta di verifica dell\'account
        (incluso) all\'email fornita dall\'utente.

    6.  L\'utente riceve un\'email di verifica e conferma l\'indirizzo
        email.

    7.  Il sistema attiva l\'account.

-   **Sequenza degli eventi alternativi:**

    -   **Al punto 4:** Se l\'email inserita è già in uso, il sistema
        notifica all\'utente che deve utilizzare un\'email diversa.

**CU3: Recupero della Password**

-   **Descrizione:** Questo caso d\'uso consente agli utenti di
    recuperare la password nel caso in cui l\'abbiano dimenticata.
    L\'utente può richiedere il reset della password tramite email.

-   **Pre-condizioni:** L\'utente deve avere un account registrato nel
    sistema.

-   **Post-condizioni:** L\'utente riceve un\'email con le istruzioni
    per reimpostare la password.

-   **Sequenza di eventi principali:**

    1.  L\'utente seleziona l\'opzione \"Recupera Password\" nella
        schermata di login.

    2.  Il sistema chiede all\'utente di inserire l\'email associata
        all\'account.

    3.  Il sistema invia una richiesta al Database/Server per verificare
        l\'esistenza dell\'email.

    4.  Se l\'email esiste, il sistema invia un\'email all\'utente con
        le istruzioni per il reset della password.

    5.  L\'utente segue le istruzioni nell\'email per reimpostare la
        password.

    6.  Il sistema aggiorna la password nel Database/Server e conferma
        all\'utente che la password è stata cambiata.

-   **Sequenza degli eventi alternativi:**

    -   **Al punto 4:** Se l\'email inserita non è valida, il sistema
        notifica all\'utente di controllare il campo inserito e
        riprovare.

**CU4: Verifica dell\'Account (Include)**

-   **Descrizione:** Questo caso d\'uso è incluso durante la
    registrazione di un nuovo utente per verificare l\'identità
    dell\'utente tramite un\'email di conferma.

-   **Pre-condizioni:** L\'utente deve aver fornito un\'email valida
    durante la registrazione.

-   **Post-condizioni:** L\'account dell\'utente è verificato e
    attivato.

-   **Sequenza di eventi principali:**

    1.  Il sistema invia un\'email di verifica all\'utente con un link o
        un codice di conferma.

    2.  L\'utente clicca sul link di verifica o inserisce il codice di
        conferma nell\'applicazione.

    3.  Il sistema verifica il codice o il link e conferma
        l\'attivazione dell\'account.

    4.  L\'utente riceve una conferma dell\'avvenuta verifica e può
        proseguire con l\'uso dell\'app.

### Gestione dei Progetti

![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image6.emf){width="5.59922353455818in"
height="4.5479429133858265in"}

**CU5: Creazione di un Progetto**

-   **Descrizione:** Questo caso d\'uso consente all\'utente di creare
    un nuovo progetto all\'interno dell\'applicazione. Durante la
    creazione, l\'utente può specificare dettagli importanti come il
    nome del progetto, una descrizione, la data di scadenza e la
    priorità.

-   **Pre-condizioni:** L\'utente deve essere autenticato all\'interno
    del sistema.

-   **Post-condizioni:** Un nuovo progetto è creato e memorizzato nel
    sistema, visibile nella dashboard dei progetti dell\'utente.

-   **Sequenza di eventi principali:**

    1.  L\'utente seleziona l\'opzione per creare un nuovo progetto.

    2.  Il sistema visualizza un modulo per l\'inserimento dei dettagli
        del progetto.

    3.  L\'utente compila il modulo con il nome, la descrizione e la
        data di scadenza e priorità del progetto.

    4.  Il sistema verifica i dati inseriti e crea il progetto.

    5.  Il nuovo progetto viene visualizzato nella dashboard dei
        progetti dell\'utente.

-   **Sequenza degli eventi alternativi:**

    1.  Se l\'utente non inserisce tutti i dettagli richiesti (es. nome
        del progetto) o inserisce informazioni non valide (es. data di
        scadenza), il sistema visualizza un messaggio di errore e
        richiede di completare in maniera corretta le informazioni.

    2.  L\'utente può annullare la creazione del progetto in qualsiasi
        momento, e il sistema ritorna alla dashboard senza creare un
        nuovo progetto.

**CU6: Modifica di un Progetto**

-   **Descrizione:** Questo caso d\'uso permette all\'utente di
    modificare i dettagli di un progetto esistente. Le modifiche possono
    includere il nome del progetto, la descrizione, la data di scadenza
    la priorità e contrassegnare il progetto come completato.
    Quest'ultima modifica comporta poi la compilazione di altri due
    campi (data di consegna e voto).

-   **Pre-condizioni:** L\'utente deve essere autenticato e avere
    accesso al progetto che desidera modificare.

-   **Post-condizioni:** I dettagli del progetto vengono aggiornati nel
    sistema e le modifiche sono riflesse immediatamente nella
    visualizzazione del progetto.

-   **Sequenza di eventi principali:**

    1.  L\'utente seleziona un progetto dalla sua dashboard.

    2.  Il sistema visualizza i dettagli attuali del progetto.

    3.  L\'utente seleziona l\'opzione per modificare i dettagli del
        progetto.

    4.  L\'utente aggiorna i dettagli desiderati e conferma le
        modifiche.

    5.  Il sistema salva le modifiche e aggiorna la visualizzazione del
        progetto.

-   **Sequenza degli eventi alternativi:**

    1.  Se l\'utente inserisce dati non validi (es. una data di scadenza
        nel passato), il sistema visualizza un messaggio di errore e
        richiede la correzione.

    2.  L\'utente può annullare la modifica in qualsiasi momento, e il
        sistema ripristina i dettagli originali del progetto.

**CU7: Visualizzazione di un Progetto**

-   **Descrizione:** Questo caso d\'uso fornisce all\'utente una vista
    centralizzata per visualizzare i dettagli dei progetti a cui
    partecipa. Include informazioni come il nome del progetto, la
    descrizione, la data di scadenza e lo stato di avanzamento.

-   **Pre-condizioni:** L\'utente deve essere autenticato e avere
    accesso a uno o più progetti.

-   **Post-condizioni:** L\'utente può visualizzare i dettagli del
    progetto senza apportare modifiche.

-   **Sequenza di eventi principali:**

    1.  L\'utente accede alla dashboard dei progetti.

    2.  Il sistema visualizza l\'elenco dei progetti a cui l\'utente
        partecipa.

    3.  L\'utente seleziona un progetto per visualizzarne i dettagli.

    4.  Il sistema visualizza una pagina dedicata con tutte le
        informazioni relative al progetto selezionato.

-   **Sequenza degli eventi alternativi:**

    1.  Se l\'utente non ha accesso a progetti, il sistema mostra un
        messaggio informativo e suggerisce di creare o unirsi a un
        progetto.

**CU8: Accesso ad un Progetto Esistente**

-   **Descrizione:** Questo caso d\'uso consente all\'utente di accedere
    a un progetto esistente tramite un invito o un codice condivisibile.
    Questo è utile per i progetti collaborativi in cui nuovi membri
    devono essere aggiunti.

-   **Pre-condizioni:** L\'utente deve essere autenticato e deve aver
    ricevuto un invito o un codice per accedere al progetto.

-   **Post-condizioni:** L\'utente ottiene l\'accesso al progetto e può
    visualizzare e collaborare sui dettagli del progetto.

-   **Sequenza di eventi principali:**

    1.  L\'utente riceve un invito o un codice per un progetto
        esistente.

    2.  L\'utente inserisce il codice o accetta l\'invito
        nell\'applicazione.

    3.  Il sistema verifica il codice o l\'invito.

    4.  Se la verifica ha successo, il progetto viene aggiunto alla
        dashboard dell\'utente.

-   **Sequenza degli eventi alternativi:**

    1.  Se il codice inserito non è valido o l\'invito è scaduto, il
        sistema visualizza un messaggio di errore e richiede di
        verificare i dettagli.

### Gestione delle Attività

![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image7.emf){width="6.3in"
height="4.2391961942257215in"}

**CU12: Creazione di un\'Attività**

-   **Descrizione:** Consente all\'utente di creare una nuova attività
    all\'interno di un progetto. Durante la creazione, l\'utente può
    specificare i dettagli essenziali come il titolo, la descrizione, la
    data di scadenza e la priorità dell\'attività.

-   **Pre-condizioni:** L\'utente deve essere autenticato e avere
    accesso a un progetto in cui creare l\'attività.

-   **Post-condizioni:** Una nuova attività viene creata e associata al
    progetto selezionato.

-   **Sequenza di eventi principali:**

    1.  L\'utente seleziona l\'opzione per creare una nuova attività.

    2.  Il sistema presenta un modulo per l\'inserimento dei dettagli
        dell\'attività.

    3.  L\'utente compila i dettagli e conferma la creazione.

    4.  Il sistema salva l\'attività e la rende visibile nel progetto
        associato.

-   **Sequenza degli eventi alternativi:**

    1.  Se l\'utente non compila i campi obbligatori, il sistema segnala
        l\'errore e richiede la correzione prima di procedere.

**CU13: Visualizzazione dei Dettagli di un\'Attività**

-   **Descrizione:** Permette all\'utente di visualizzare i dettagli
    completi di un\'attività, inclusi le informazioni dell'attività, i
    membri assegnati, la data di scadenza, la priorità ed eventuali file
    allegati.

-   **Pre-condizioni:** L\'utente deve essere autenticato e avere
    accesso all\'attività che intende visualizzare.

-   **Post-condizioni:** L\'utente visualizza le informazioni
    dettagliate dell\'attività senza modificarle.

-   **Sequenza di eventi principali:**

    1.  L\'utente seleziona un\'attività dalla lista.

    2.  Il sistema visualizza una pagina con tutti i dettagli relativi
        all\'attività selezionata.

    3.  L\'utente può consultare tutte le informazioni senza apportare
        modifiche.

**CU14: Modifica di un\'Attività**

-   **Descrizione:** Consente all\'utente di aggiornare o modificare i
    dettagli di un\'attività esistente, come il titolo, la descrizione,
    la data di scadenza, la priorità, i partecipanti all'attività e la
    possibilità di caricare un file.

-   **Pre-condizioni:** L\'utente deve essere autenticato e avere i
    permessi necessari per modificare l\'attività (partecipa
    all'attività).

-   **Post-condizioni:** I dettagli dell\'attività vengono aggiornati
    nel sistema.

-   **Sequenza di eventi principali:**

    1.  L\'utente seleziona un\'attività per modificarla.

    2.  Il sistema visualizza il modulo di modifica con i dettagli
        attuali dell\'attività.

    3.  L\'utente aggiorna i dettagli desiderati e conferma le
        modifiche.

    4.  Il sistema salva le modifiche e aggiorna l\'attività nel
        progetto.

-   **Sequenza degli eventi alternativi:**

    1.  Se l\'utente annulla la modifica, il sistema ripristina i
        dettagli originali e torna alla visualizzazione dell\'attività.

    2.  Se l'utente, durante la modifica, immette informazioni non
        valide, il sistema visualizzerà un messaggio di errore che
        informa l'utente di correggere il contenuto per poter completare
        la modifica.

**CU15: Delega di un\'Attività**

-   **Descrizione:** Permette all\'utente di delegare un\'attività a un
    altro membro del team, specificando chi sarà responsabile per il
    completamento dell\'attività.

-   **Pre-condizioni:** L\'utente deve essere autenticato e avere il
    permesso di modificare l\'attività.

-   **Post-condizioni:** L\'attività viene assegnata al membro del team
    selezionato, e il sistema aggiorna le informazioni dell\'attività.

-   **Sequenza di eventi principali:**

    1.  L\'utente seleziona un\'attività e sceglie l\'opzione per
        delegarla.

    2.  Il sistema mostra un elenco dei membri del team.

    3.  L\'utente seleziona il membro a cui delegare l\'attività e
        conferma.

    4.  Il sistema aggiorna l\'attività con il nuovo assegnatario.

-   **Sequenza degli eventi alternativi:**

    1.  Se l\'utente annulla l\'operazione di delega, l\'attività rimane
        assegnata all\'utente originale.

**CU18: Aggiunta di un File ad un\'Attività**

-   **Descrizione:** Consente all\'utente di aggiungere [un
    file]{.underline} a un\'attività per supportare la gestione del
    progetto. I file possono includere documenti, immagini, o qualsiasi
    altro tipo di allegato pertinente.

-   **Pre-condizioni:** L\'utente deve essere autenticato e avere
    accesso all\'attività a cui vuole aggiungere un file.

-   **Post-condizioni:** Il file viene aggiunto all\'attività e reso
    disponibile per la consultazione da parte di tutti i membri del
    progetto.

-   **Sequenza di eventi principali:**

    1.  L\'utente seleziona l\'opzione per aggiungere un file a
        un\'attività.

    2.  Il sistema apre un\'interfaccia di caricamento file.

    3.  L\'utente seleziona il file dal proprio dispositivo e conferma
        il caricamento.

    4.  Il sistema salva il file e lo associa all\'attività.

**CU16: Completamento di un\'Attività**

-   **Descrizione:** Consente all\'utente di segnare un\'attività come
    completata, aggiornando lo stato dell\'attività e notificando il
    team dei progressi.

-   **Pre-condizioni:** L\'utente deve essere autenticato e avere il
    permesso di modificare lo stato dell\'attività.

-   **Post-condizioni:** L\'attività viene contrassegnata come
    completata nel sistema.

-   **Sequenza di eventi principali:**

    1.  L\'utente seleziona un\'attività e sceglie l\'opzione per
        segnarla come completata.

    2.  Il sistema chiede una conferma dell\'azione.

    3.  L\'utente conferma il completamento dell\'attività.

    4.  Il sistema aggiorna lo stato dell\'attività a \"completata\" e
        notifica i membri del progetto.

-   **Sequenza degli eventi alternativi:**

    1.  Se l\'utente annulla l\'azione, lo stato dell\'attività rimane
        invariato.

**CU17: Eliminazione di un\'Attività**

-   **Descrizione:** Permette all\'utente di eliminare un\'attività dal
    progetto, rimuovendola definitivamente dal sistema.

-   **Pre-condizioni:** L\'utente deve essere autenticato e avere il
    permesso di eliminare l\'attività.

-   **Post-condizioni:** L\'attività viene eliminata dal progetto e non
    è più visibile o accessibile.

-   **Sequenza di eventi principali:**

    1.  L\'utente seleziona un\'attività e sceglie l\'opzione per
        eliminarla.

    2.  Il sistema chiede una conferma dell\'eliminazione.

    3.  L\'utente conferma l\'eliminazione.

    4.  Il sistema rimuove l\'attività dal progetto e aggiorna la
        visualizzazione.

-   **Sequenza degli eventi alternativi:**

    1.  Se l\'utente annulla l\'operazione di eliminazione, l\'attività
        rimane nel sistema.

### Gestione del Profilo Utente

![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image8.emf){width="5.1552821522309715in"
height="3.7518241469816274in"}

**CU20: Personalizzazione del profilo**

-   **Descrizione:** Consente all\'utente di personalizzare il proprio
    profilo modificando informazioni personali come il nome, la foto del
    profilo, e altre preferenze.

-   **Pre-condizioni:** L\'utente deve essere autenticato e avere
    accesso alla sezione del profilo.

-   **Post-condizioni:** Le informazioni aggiornate vengono salvate e
    visualizzate nel profilo dell\'utente.

-   **Sequenza di eventi principali:**

    1.  L\'utente accede alla sezione di personalizzazione del profilo.

    2.  Il sistema presenta un modulo con i dettagli attuali del
        profilo.

    3.  L\'utente modifica le informazioni desiderate e conferma le
        modifiche.

    4.  Il sistema salva le modifiche e aggiorna il profilo.

-   **Sequenza degli eventi alternativi:**

    1.  Se l\'utente annulla l\'operazione, il sistema ripristina i dati
        originali senza apportare modifiche.

**CU21: Caricamento e aggiornamento dell\'immagine del profilo**

-   **Descrizione:** Consente all\'utente di caricare o aggiornare
    l\'immagine del profilo.

-   **Pre-condizioni:** L\'utente deve essere autenticato e avere
    accesso alla sezione di personalizzazione del profilo.

-   **Post-condizioni:** La nuova immagine del profilo viene caricata e
    visualizzata nel profilo dell\'utente.

-   **Sequenza di eventi principali:**

    1.  L\'utente seleziona l\'opzione per caricare o aggiornare
        l\'immagine del profilo.

    2.  Il sistema apre un\'interfaccia di caricamento file.

    3.  L\'utente seleziona l\'immagine desiderata e conferma
        l\'operazione.

    4.  Il sistema salva l\'immagine e aggiorna il profilo dell\'utente.

-   **Sequenza degli eventi alternativi:**

    1.  Se l\'utente annulla l\'operazione di caricamento, il sistema
        mantiene l\'immagine del profilo precedente.

**CU22: Ricerca e aggiunta studenti**

-   **Descrizione:** Permette all\'utente di cercare altri studenti
    all\'interno dell\'applicazione e inviare loro una richiesta di
    amicizia.

-   **Pre-condizioni:** L\'utente deve essere autenticato.

-   **Post-condizioni:** Viene inviata una richiesta di amicizia agli
    studenti selezionati.

-   **Sequenza di eventi principali:**

    1.  L\'utente accede alla funzione di ricerca studenti.

    2.  Il sistema visualizza un modulo di ricerca dove l\'utente
        inserisce i criteri (nome, cognome, email, matricola).

    3.  L\'utente seleziona gli studenti dai risultati della ricerca e
        invia le richieste di amicizia.

    4.  Il sistema invia le richieste e notifica l\'utente del successo
        dell\'operazione.

-   **Sequenza degli eventi alternativi:**

    1.  Se la ricerca non produce risultati, il sistema informa
        l\'utente che non sono stati trovati studenti corrispondenti ai
        criteri inseriti.

**CU24: Invio di richieste di partecipazione a progetti**

-   **Descrizione:** Consente all\'utente di inviare richieste di
    partecipazione a progetti ad altri studenti o amici.

-   **Pre-condizioni:** L\'utente deve essere autenticato e avere
    accesso a un progetto in cui desidera invitare altri utenti.

-   **Post-condizioni:** Viene inviata una richiesta di partecipazione
    al progetto agli studenti selezionati.

-   **Sequenza di eventi principali:**

    1.  L\'utente seleziona l\'opzione per invitare amici o studenti a
        un progetto.

    2.  Il sistema visualizza un elenco di amici o studenti
        selezionabili.

    3.  L\'utente seleziona i destinatari dell\'invito e conferma
        l\'invio.

    4.  Il sistema invia le richieste di partecipazione e notifica
        l\'utente dell\'esito.

-   **Sequenza degli eventi alternativi:**

    1.  Se l\'utente annulla l\'operazione di invito, nessuna richiesta
        viene inviata.

**CU25: Eliminazione del profilo**

-   **Descrizione:** Consente all\'utente di eliminare il proprio
    profilo e tutte le informazioni associate, rimuovendolo
    definitivamente dal sistema.

-   **Pre-condizioni:** L\'utente deve essere autenticato e confermare
    l\'intenzione di eliminare il proprio profilo.

-   **Post-condizioni:** Il profilo dell\'utente viene eliminato e tutte
    le informazioni associate vengono rimosse dal sistema.

-   **Sequenza di eventi principali:**

    1.  L\'utente seleziona l\'opzione per eliminare il proprio profilo.

    2.  Il sistema chiede una conferma per procedere con
        l\'eliminazione.

    3.  L\'utente conferma l\'eliminazione del profilo.

    4.  Il sistema elimina il profilo e tutte le informazioni associate.

-   **Sequenza degli eventi alternativi:**

    1.  Se l\'utente annulla l\'operazione, il profilo rimane attivo e
        nessuna informazione viene eliminata.

### Gestione degli Amici

![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image9.emf){width="5.4947615923009625in"
height="3.512952755905512in"}.

**CU27: Visualizzazione profilo Collega**

-   **Descrizione:** Questo caso d\'uso consente all\'utente di
    visualizzare il profilo di un collega o amico all\'interno
    dell\'applicazione, inclusi i dettagli delle attività e progetti
    completati.

-   **Pre-condizioni:** L\'utente deve essere autenticato e avere il
    collega o amico nella propria lista di amici.

-   **Post-condizioni:** L\'utente visualizza i dettagli del profilo del
    collega selezionato.

-   **Sequenza di eventi principali:**

    1.  L\'utente accede alla sezione \"Profilo\".

    2.  L\'utente seleziona un collega dall\'elenco per visualizzarne il
        profilo.

    3.  Il sistema mostra i dettagli del profilo del collega, inclusi
        nome, ruolo, e altre informazioni rilevanti.

**CU29: Rimozione degli amici**

-   **Descrizione:** Questo caso d\'uso consente all\'utente di
    rimuovere un amico dalla propria lista di amici all\'interno
    dell\'applicazione.

-   **Pre-condizioni:** L\'utente deve essere autenticato e avere almeno
    un amico aggiunto nella propria lista.

-   **Post-condizioni:** L\'amico selezionato viene rimosso dalla lista
    di amici dell\'utente e non sarà più visibile nelle interazioni
    future, come l\'invio di inviti ai progetti.

-   **Sequenza di eventi principali:**

    1.  L\'utente accede alla sezione \"Profilo\".

    2.  Il sistema visualizza l\'elenco degli amici attuali
        dell\'utente.

    3.  L\'utente seleziona l\'amico che desidera rimuovere.

    4.  Il sistema rimuove l\'amico dalla lista e aggiorna l\'elenco
        visualizzato.

### Gestione delle Notifiche

![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image10.emf){width="5.686304680664917in"
height="3.8676356080489938in"}

### Gestione delle Impostazioni

![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image11.emf){width="5.594176509186352in"
height="4.855972222222222in"}

## Architettura dell'applicazione

Nella progettazione dell\'architettura dell\'applicazione **TeamSync**,
è stato adottato il pattern
architetturale **MVVM** (Model-View-ViewModel). Questa scelta è stata
effettuata per garantire la modularità, la testabilità e la
manutenibilità del codice, aspetti fondamentali nello sviluppo di
applicazioni Android moderne. L\'uso delle **Android Jetpack
Libraries** ha ulteriormente facilitato l\'implementazione di questo
pattern, offrendo componenti predefiniti per ciascuno dei livelli MVVM e
rendendo lo sviluppo più efficiente.

### Componenti del Pattern MVVM:

-   **Model:** Il Model rappresenta la fonte dei dati
    nell\'applicazione, gestendo la logica di business e l\'interazione
    con le fonti di dati, nel nostro caso un servizio remoto
    ( **Firebase**) che andremo ad approfondire nella prossima sezione.
    La separazione del Model dalla View garantisce che la gestione dei
    dati rimanga indipendente dalla UI, migliorando la modularità del
    sistema.

-   **View:** La View è l\'interfaccia utente che visualizza i dati
    forniti dal ViewModel. Essa è completamente disaccoppiata dalla
    logica di business, il che permette di modificare o aggiornare
    l\'interfaccia senza impattare sul funzionamento dell\'applicazione.
    Questa separazione contribuisce a migliorare la testabilità e la
    flessibilità della UI.

-   **ViewModel:** Il ViewModel funge da intermediario tra il Model e la
    View. È responsabile della logica di presentazione e
    dell\'aggiornamento della View in risposta ai cambiamenti nei dati.
    Il ViewModel utilizza componenti come **LiveData**  per gestire in
    modo reattivo le modifiche nei dati, mantenendo anche lo stato
    dell\'interfaccia utente durante i cambiamenti di configurazione.

-   **Repository:** Il Repository è un componente chiave
    nell\'architettura MVVM che funge da mediatore tra il ViewModel e le
    varie fonti di dati. Grazie al Repository, la logica di accesso ai
    dati è completamente astratta e disaccoppiata dal resto
    dell\'applicazione, il che facilita l\'adozione di nuove fonti di
    dati o la modifica delle esistenti senza impattare la logica di
    business o la presentazione. Questo approccio rende il codice più
    flessibile e riutilizzabile, migliorando anche la testabilità, in
    quanto il Repository può essere facilmente sostituito con mock o
    finti durante i test unitari, garantendo così una maggiore
    affidabilità del sistema.

### Struttura del Progetto

La struttura del progetto, come visualizzata in questa vista espansa,
riflette chiaramente l\'adozione del pattern architetturale **MVVM
(Model-View-ViewModel)**. Ogni funzionalità dell\'applicazione è
suddivisa in package specifici che corrispondono ai diversi livelli
dell\'architettura MVVM.

![Immagine che contiene testo, schermata, menu, design Descrizione
generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image12.png){width="4.8930905511811025in"
height="6.332108486439195in"}

Ogni package all\'interno del progetto è strutturato con una
suddivisione chiara e metodica, volta a riflettere l\'architettura MVVM.
In particolare, ogni package è organizzato in due principali
sottodirectory:

1.  **Data**: Questo sottopackage è ulteriormente suddiviso in tre
    componenti essenziali:

    -   *Model*: Contiene le classi che rappresentano i modelli di dati
        utilizzati nell\'applicazione. Questi modelli riflettono la
        struttura delle entità con cui l\'app interagisce e che vengono
        memorizzate, come ad esempio utenti, progetti, attività e
        notifiche.

    -   *ViewModel*: Questa componente gestisce la logica di
        presentazione, fungendo da intermediario tra il Model e la View.
        È qui che risiede la logica dell\'applicazione, come il recupero
        e la manipolazione dei dati, che viene poi esposta alla UI in
        modo reattivo, permettendo un\'interazione fluida e aggiornata.

    -   *Repository*: Il repository funge da intermediario tra i dati e
        la logica di business. Qui vengono gestite le fonti di dati,
        garantendo che il ViewModel abbia accesso ai dati in modo
        astratto e centralizzato, facilitando così la manutenibilità e
        l\'espansione del codice.

2.  **UI**: Questo sottopackage è dedicato alla gestione delle
    interfacce utente (View) dell\'applicazione. Qui si trovano le varie
    schermate e le composizioni UI che definiscono l\'aspetto e
    l\'interazione visiva dell\'app. Ogni View è strettamente connessa
    al corrispondente ViewModel, assicurando che i dati e la logica
    siano presentati all\'utente finale in modo coerente e intuitivo.

## Data Storage

### Database (Firebase)

Per la gestione del database della nostra applicazione TeamSync, abbiamo
scelto di utilizzare Firebase, un servizio completo offerto da Google.
**Firebase** mette a disposizione dei servizi che abbiamo adottato per
la gestione sia dell\'autenticazione degli utenti che per
l\'archiviazione e l\'organizzazione dei dati applicativi.

Firebase **Authentication** ci permette di implementare un sistema di
autenticazione sicuro ed efficiente, facilitando la registrazione e il
login degli utenti tramite diversi metodi, come email e password, Google
Sign-In, e altre piattaforme di autenticazione. Tuttavia, nel contesto
di TeamSync, abbiamo scelto di limitare l\'autenticazione esclusivamente
all\'uso di email e password. Questa decisione è stata presa per
simulare la registrazione e l\'accesso con email universitarie,
allineandosi al nostro obiettivo di collegare, in futuro, i servizi già
esistenti dell\'Università Politecnica delle Marche (Univpm) con
l\'applicazione e con Firebase.

Firebase **Firestore**, il database non relazionale scelto per la
gestione dei dati, offre una flessibilità e una scalabilità ideali per
un\'app mobile moderna come TeamSync. La struttura dei dati in Firestore
è organizzata in raccolte (collections) e documenti, permettendo di
archiviare le informazioni in modo semplice e intuitivo, ma con la
possibilità di espandere la complessità quando necessario. 

Il database di TeamSync è suddiviso in sei **raccolte
principali** (Collections), ciascuna delle quali gestisce un aspetto
specifico dell\'applicazione.

![Immagine che contiene testo, schermata, software, numero Descrizione
generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image13.png){width="6.3in"
height="3.5in"}

**Utenti**:

La classe ProfiloUtente rappresenta il profilo di un utente all\'interno
del sistema. Essa contiene diverse proprietà per gestire le informazioni
personali e sociali dell\'utente, inclusi i suoi amici e le informazioni
di contatto.

![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image14.png){width="4.819675196850394in"
height="2.600498687664042in"}

![Immagine che contiene testo, documento, schermata Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image15.png){width="6.3in"
height="3.125in"}

-   **id**: Una stringa che rappresenta l\'ID univoco dell\'utente nel
    database.

-   **nome**: Il nome dell\'utente.

-   **cognome**: Il cognome dell\'utente.

-   **matricola**: La matricola dell\'utente, utilizzata principalmente
    per studenti universitari.

-   **dataDiNascita**: La data di nascita dell\'utente, memorizzata come
    un oggetto Date.

-   **sesso**: Un\'istanza dell\'enum SessoUtente che può
    essere UOMO, DONNA o ALTRO, con un metodo per ottenere la traduzione
    del sesso in base alla lingua.

-   **email**: L\'indirizzo email dell\'utente.

-   **primoAccesso**: Un booleano che indica se è il primo accesso
    dell\'utente all\'applicazione.

-   **immagine**: Un URL opzionale che punta all\'immagine del profilo
    dell\'utente.

-   **amici**: Una lista di stringhe che contiene gli ID degli amici
    dell\'utente.

L\'enumerazione SessoUtente rappresenta i vari sessi disponibili per
l\'utente. Ogni valore ha associato un ID di risorsa stringa per la
traduzione. Essa consente di ottenere una versione tradotta del sesso in
base al contesto della lingua corrente.

![Immagine che contiene testo, schermata Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image16.png){width="6.3in"
height="1.9069444444444446in"}

Questo model viene utilizzato per rappresentare e gestire tutte le
informazioni relative agli utenti all\'interno del database Firestore,
come si può vedere dall\'immagine.

In questo caso, il database è organizzato in modo tale che ogni
documento nella raccolta \"utenti\" rappresenta un utente e contiene
tutte queste informazioni per facilitare la gestione e la
visualizzazione del profilo all\'interno dell\'applicazione.

**Notifiche**:

![Immagine che contiene testo, schermata, software, numero Descrizione
generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image17.png){width="4.791211723534558in"
height="2.5814545056867892in"}

![Immagine che contiene testo, schermata, Carattere, documento
Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image18.png){width="6.3in"
height="3.967361111111111in"}

Il **model \"Notifiche\"** rappresenta una data class in Kotlin
utilizzata per definire la struttura di una notifica all\'interno
dell\'applicazione TeamSync. Questo model viene utilizzato per
memorizzare e gestire le informazioni relative alle notifiche inviate e
ricevute dagli utenti. Ogni istanza della classe \"Notifiche\"
rappresenta una singola notifica e contiene i seguenti campi:

-   **mittente**: Rappresenta l\'ID dell\'utente che ha inviato la
    notifica.

-   **destinatario**: Indica l\'ID dell\'utente che ha ricevuto la
    notifica.

-   **tipo**: Specifica il tipo di notifica, come ad esempio una
    richiesta di amicizia o una richiesta di partecipazione a un
    progetto.

-   **aperto**: Un valore booleano che indica se la notifica è stata
    aperta dall\'utente destinatario. Di default è impostato su false.

-   **contenuto**: Contiene il testo o i dettagli della notifica.

-   **id**: Un identificatore univoco per la notifica.

-   **progettoId**: L\'ID del progetto associato alla notifica, nel caso
    la notifica riguardi un progetto specifico.

-   **accettato**: Indica lo stato dell\'accettazione della notifica, se
    è stata accettata, rifiutata o è ancora in attesa di risposta.

-   **data**: La data e l\'ora in cui la notifica è stata creata.

Una precisazione che merita attenzione è la seguente:

-   Il campo **accettato** è stato progettato come una stringa che viene
    inizialmente impostata a una stringa vuota (\"\"). Questa scelta è
    stata fatta per gestire in modo flessibile le notifiche di richiesta
    di amicizia. Mentre la maggior parte delle notifiche potrebbe non
    necessitare di questo campo, nel caso specifico delle notifiche di
    richiesta di amicizia, il campo **accettato** viene utilizzato per
    registrare la risposta dell\'utente. In tali casi, la stringa viene
    aggiornata a \"true\" se la richiesta di amicizia viene accettata,
    oppure a \"false\" se viene rifiutata. Questo approccio permette di
    mantenere la struttura del model semplice e adattabile alle diverse
    esigenze di gestione delle notifiche all\'interno
    dell\'applicazione.

**Todo**:

![Immagine che contiene testo, schermata, software Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image19.png){width="4.76207239720035in"
height="2.6001454505686787in"}

La raccolta Todo contiene i compiti e le attività che gli utenti devono
svolgere.

Questo modello rappresenta la struttura di una singola attività
all\'interno di un progetto nell\'applicazione TeamSync.

![Immagine che contiene testo, schermata, Carattere, documento
Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image20.png){width="6.3in"
height="4.227777777777778in"}

Ecco una descrizione dettagliata dei campi presenti nella data class:

-   **id**: Questo è l\'ID univoco dell\'attività, generato
    automaticamente da Firestore. Serve per identificare in modo univoco
    ogni attività all\'interno del database.

-   **titolo**: Indica il titolo dell\'attività. Questo campo fornisce
    una breve descrizione del compito da svolgere.

-   **descrizione**: Contiene una descrizione dettagliata
    dell\'attività, permettendo di specificare cosa deve essere fatto e
    quali sono i dettagli rilevanti.

-   **dataScadenza**: La data entro la quale l\'attività deve essere
    completata. Questo campo è importante per la gestione delle scadenze
    e la pianificazione del lavoro.

-   **priorita**: Rappresenta la priorità dell\'attività, che può essere
    una tra **NESSUNA**, **BASSA**, **MEDIA** o **ALTA**. Questo campo
    aiuta a determinare l\'importanza relativa delle attività nel
    contesto del progetto.

-   **completato**: Un booleano che indica se l\'attività è stata
    completata. Questo campo è fondamentale per monitorare lo stato di
    avanzamento delle attività.

-   **fileUri**: Un URI opzionale che punta a un file allegato
    all\'attività. Questo campo è utile se l\'attività richiede la
    gestione di documenti o altri file.

-   **progetto**: L\'ID del progetto a cui l\'attività appartiene.
    Questo campo collega l\'attività a un progetto specifico
    all\'interno dell\'applicazione.

-   **dataCreazione**: La data e l\'ora in cui l\'attività è stata
    creata. Questo campo è utile per tracciare quando è stata inserita
    l\'attività nel sistema.

-   **utenti**: Una lista di ID degli utenti assegnati all\'attività.
    Questo campo permette di gestire quali membri del team sono
    responsabili dell\'attività.

**Progetti**:

![Immagine che contiene testo, schermata, software Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image21.png){width="4.830207786526684in"
height="2.6199890638670165in"}

In questa raccolta vengono gestiti tutti i progetti creati dagli utenti.

![Immagine che contiene testo, documento, Carattere, schermata
Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image22.png){width="6.3in"
height="3.227777777777778in"}

Questo modello rappresenta la struttura di un progetto in un sistema di
gestione progetti, come quello utilizzato nell\'applicazione TeamSync.

Ecco una descrizione dettagliata dei campi presenti nella data class:

-   **id**: L\'ID univoco del progetto, che corrisponde all\'ID del
    documento in Firestore. Serve per identificare in modo univoco ogni
    progetto all\'interno del database.

-   **nome**: Il nome del progetto. Questo campo contiene il titolo del
    progetto, che dovrebbe essere breve e descrittivo.

-   **descrizione**: Una descrizione opzionale del progetto. Questo
    campo consente di fornire dettagli aggiuntivi che spiegano gli
    obiettivi e il contenuto del progetto.

-   **dataCreazione**: La data di creazione del progetto. Questo valore
    viene impostato automaticamente alla data corrente.

-   **dataScadenza**: La data di scadenza del progetto. Questo campo
    rappresenta la data entro la quale il progetto dovrebbe essere
    completato.

-   **dataConsegna**: La data di consegna del progetto. Può coincidere
    con la data di scadenza o essere specificata separatamente per
    indicare il termine effettivo di consegna.

-   **priorita**: La priorità del progetto, che può essere una
    tra **NESSUNA**, **BASSA**, **MEDIA**, **ALTA**. Questo campo aiuta
    a classificare i progetti in base alla loro importanza o urgenza.

-   **partecipanti**: Una lista di ID degli utenti partecipanti al
    progetto. Questo campo elenca tutti i membri del team che sono
    coinvolti nel progetto.

-   **voto**: Il voto assegnato al progetto, che può essere aggiornato
    al termine del progetto. Il valore predefinito è \"Non Valutato\",
    ma può essere cambiato a seguito di una valutazione del progetto.

-   **completato**: Indica se il progetto è stato completato. Il valore
    predefinito è false, e viene aggiornato a *true* quando il progetto
    è concluso.

-   **codice**: Un codice univoco associato al progetto, utilizzato per
    identificare il progetto in modo univoco tra i partecipanti. Questo
    campo è utile per facilitare l\'accesso rapido e sicuro al progetto.

**Terms & Policies**:

![Immagine che contiene testo, schermata, software Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image23.png){width="4.800563210848644in"
height="2.6384044181977253in"}

Questa raccolta garantisce che tutte le versioni aggiornate delle
politiche siano accessibili agli utenti.

![Immagine che contiene testo, schermata, algebra Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image24.png){width="6.3in"
height="1.6201388888888888in"}

Questo modello rappresenta la struttura dei termini e condizioni o delle
policy dell\'applicazione all\'interno del sistema di gestione di
TeamSync. Di seguito è riportata una descrizione dettagliata dei campi
presenti nella data class:

-   **titolo**: Il titolo del documento o della sezione relativa ai
    termini e condizioni. Questo campo è utilizzato per identificare
    chiaramente il documento o la parte specifica delle policy a cui si
    fa riferimento, ad esempio \"1. Introduzione\".

-   **descrizione**: La descrizione o il contenuto dettagliato dei
    termini e condizioni. Questo campo contiene il testo effettivo che
    spiega i termini e le condizioni o le politiche che gli utenti
    devono accettare per utilizzare l\'applicazione. Può includere
    regole, linee guida, e informazioni legali che regolano l\'uso
    dell\'app.

-   **data**: La data di pubblicazione o aggiornamento dei termini e
    condizioni. Questo campo è facoltativo e può essere null se la data
    non è disponibile. Serve per tenere traccia di quando i termini e le
    condizioni sono stati pubblicati o aggiornati, offrendo un
    riferimento temporale agli utenti.

**Faq**:

La classe Faq rappresenta una domanda frequente (FAQ) all\'interno
dell\'applicazione. Questo model viene utilizzato per organizzare e
gestire le domande e risposte che gli utenti pongono più spesso,
facilitando l\'accesso alle informazioni necessarie per utilizzare
correttamente l\'applicazione.

![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image25.png){width="4.791404199475066in"
height="2.629676290463692in"}

![Immagine che contiene testo, schermata, algebra Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image26.png){width="6.3in"
height="1.5909722222222222in"}

-   **domanda**: Una stringa che contiene la domanda frequentemente
    posta dagli utenti. Questa proprietà rappresenta l\'interrogativo
    principale che gli utenti potrebbero avere.

-   **risposta**: Una stringa che fornisce la risposta alla domanda
    specificata. Questa proprietà contiene la spiegazione o la guida che
    risponde alla domanda.

-   **sezione**: Una stringa che identifica la sezione o categoria a cui
    appartiene la FAQ. Questo campo è utile per organizzare le FAQ in
    gruppi tematici, rendendo più facile per gli utenti trovare le
    risposte alle loro domande all\'interno dell\'applicazione.

Ogni documento all\'interno della raccolta \"Faq\" rappresenta una
singola FAQ, con le sue rispettive proprietà domanda, risposta,
e sezione. Questo modello è ideale per gestire le informazioni di
supporto agli utenti, fornendo risposte rapide e organizzate a domande
comuni direttamente all\'interno dell\'applicazione.

#### Regole del Database Firebase Firestore

Durante lo sviluppo della nostra applicazione, abbiamo incontrato una
problematica legata alla sicurezza dell\'accesso ai dati memorizzati in
Firebase Firestore.

Inizialmente, le **regole di sicurezza** predefinite permettevano un
accesso troppo permissivo ai dati, potenzialmente esponendo informazioni
sensibili ad accessi non autorizzati. Per risolvere questo problema,
abbiamo dovuto modificare le regole di sicurezza del database Firestore,
implementando controlli più rigidi per garantire che solo gli utenti
autenticati possano eseguire operazioni sui dati, e solo su quelli che
sono loro assegnati.

![Immagine che contiene testo, schermata, software, Pagina Web
Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image27.png){width="6.3in"
height="3.2708333333333335in"}

1.  **Regole generali per l\'accesso solo agli utenti autenticati:**

    -   La regola generale specificata nella prima parte del file
        (*match /{document=\*\*}*) è stata configurata per consentire
        l\'accesso in lettura e scrittura solo agli utenti autenticati.
        Questa regola funge da base, garantendo che qualsiasi operazione
        di lettura o scrittura su Firestore sia consentita solo se
        l\'utente ha effettuato l\'accesso (*request.auth != null*).

2.  **Regole specifiche per la raccolta \"users\":**

    -   Per la raccolta \"users\", abbiamo implementato una regola che
        permette l\'accesso ai dati utente solo se l\'utente autenticato
        sta accedendo ai propri dati. Questa regola verifica che l\'ID
        dell\'utente nella richiesta (*request.auth.uid*) corrisponda
        all\'ID utente (*userId*) del documento che si sta tentando di
        accedere. Ciò garantisce che un utente non possa accedere o
        modificare i dati di un altro utente.

3.  **Regole specifiche per la raccolta \"Todo\":**

    -   Analogamente, per la raccolta \"Todo\", è stata aggiunta una
        regola che permette l\'accesso solo agli utenti autenticati.
        Questa regola assicura che solo gli utenti autenticati possano
        visualizzare o modificare le attività che hanno creato,
        proteggendo così i dati personali e garantendo che le operazioni
        siano eseguite solo dai proprietari legittimi.

Le regole attualmente implementate nel database sono ancora piuttosto
primitive e, in futuro, verranno aggiornate per soddisfare un numero
maggiore di casi d\'uso, garantendo un livello di sicurezza e
affidabilità ancora più elevato per gli utenti.

### Shared Preferences

Nel progetto TeamSync, le **SharedPreferences** sono state utilizzate
principalmente per la gestione delle impostazioni personalizzabili
dall\'utente, come la selezione del tema (scuro o chiaro) e le
preferenze relative alle notifiche, alle attività e ai progetti.

**ThemePreferences**

Il file ThemePreferences è un oggetto che consente di salvare e
recuperare la preferenza dell\'utente riguardo al tema
dell\'applicazione (scuro o chiaro). Questo viene gestito
tramite **SharedPreferences**, uno strumento che permette di memorizzare
coppie chiave-valore in modo persistente all\'interno dell\'app.

-   **Salvataggio del tema**: Quando l\'utente seleziona un tema, questa
    scelta viene salvata con una chiave specifica (THEME_KEY). In questo
    modo, l\'applicazione può ricordare e ripristinare il tema preferito
    dell\'utente anche dopo la chiusura e la riapertura dell\'app.

-   **Recupero del tema**: All\'avvio dell\'app, l\'impostazione del
    tema viene recuperata per garantire che l\'utente trovi
    l\'applicazione nel tema selezionato in precedenza. Se nessuna
    preferenza è stata ancora salvata, il tema predefinito è quello
    chiaro.

**Notifiche**

Le preferenze per le notifiche sono gestite all\'interno
di **SharedPreferences** sotto il nome \"preferenze_notifiche\". Queste
preferenze permettono all\'utente di abilitare o disabilitare specifiche
notifiche, come:

-   **Entra Progetto**: Se abilitata, l\'utente riceve una notifica
    quando un collega entra in un progetto in comune.

-   **Completa Task**: Notifica quando un\'attività assegnata viene
    completata.

-   **Modifica Task**: Notifica quando un\'attività assegnata
    all\'utente viene modificata.

**Progetti**

Per i progetti, **SharedPreferences** sono utilizzate per memorizzare
preferenze relative alla visualizzazione e all\'ordinamento dei
progetti:

-   **Visualizzazione dei progetti completati**: L\'utente può scegliere
    di visualizzare o meno i progetti già completati.

-   **Ordine dei progetti**: I progetti possono essere ordinati in base
    a diverse modalità, come l\'ordine cronologico, preferenza che viene
    salvata e ripristinata tramite **SharedPreferences**.

**Attività**

Le preferenze per le attività sono simili a quelle per i progetti e
includono:

-   **Ordine delle attività**: Le attività possono essere ordinate
    secondo diversi criteri, come la data di creazione, e questa scelta
    è memorizzata nelle **SharedPreferences**.

L\'uso di **SharedPreferences** in TeamSync garantisce che le preferenze
personali dell\'utente siano sempre mantenute e ripristinate
correttamente, migliorando l\'esperienza utente e assicurando una
personalizzazione consistente dell\'interfaccia e delle notifiche
dell\'app.

## MockUp UI

Nella fase di progettazione dell\'applicazione TeamSync, abbiamo
riservato particolare attenzione allo sviluppo dell\'interfaccia utente
(UI), consapevoli dell\'importanza che questa riveste nell\'offrire
un\'esperienza utente efficace e intuitiva. Prima di procedere con
l\'implementazione diretta della UI su Android Studio, abbiamo
utilizzato il software professionale **Figma** per studiare, progettare
e comporre i vari elementi dell\'interfaccia.

Figma ci ha permesso di sperimentare diverse soluzioni grafiche, di
definire con precisione il layout e di ottimizzare l\'organizzazione
degli elementi visivi, garantendo così un design coerente e piacevole.
Questo passaggio preliminare è stato cruciale per evitare modifiche
costose e dispendiose in termini di tempo durante la fase di sviluppo.

L\'implementazione effettiva dell\'interfaccia UI moderna è stata resa
possibile grazie all\'utilizzo di **Jetpack Compose**, il framework di
Google per la creazione di interfacce utente native in Android. Durante
lo sviluppo dell\'applicazione, abbiamo approfondito e sviscerato le
funzionalità di Jetpack Compose, sfruttando le sue caratteristiche utili
e preziose per tradurre fedelmente il mockup grafico in componenti
funzionali all\'interno di Android Studio. Questo approccio ci ha
permesso di mantenere fede al design originale.

La sezione che segue illustrerà i mockup creati in Figma, confrontandoli
con le implementazioni finali in Android Studio, evidenziando come il
design iniziale e l\'uso di Jetpack Compose abbiano guidato lo sviluppo
dell\'interfaccia utente dell\'applicazione.

### Progettazione preliminare

Il mockup della UI realizzato con **Figma** rappresenta una visione
completa e dettagliata dell\'interfaccia utente che avevamo in mente per
l\'applicazione TeamSync. Durante la fase di progettazione, abbiamo
creato un design che coprisse ogni aspetto dell\'esperienza utente,
includendo non solo le funzionalità essenziali, ma anche elementi
avanzati pensati per un\'implementazione futura. Tuttavia, alcune di
queste funzionalità avanzate non sono state ancora implementate nella
versione attuale dell\'applicazione, principalmente perché non erano
state concordate precedentemente e perché sono state concepite per un
futuro sviluppo dell\'app, che continuerà a evolversi anche dopo la
conclusione di questo esame universitario.

Uno degli aspetti più interessanti è stato il ripensamento della UI
della sezione Progetti. Inizialmente, il design creato in Figma era
molto dettagliato e articolato, ma durante la fase di sviluppo abbiamo
deciso di semplificare ulteriormente le interazioni utente. Questa
scelta è stata fatta per rendere l\'esperienza utente ancora più fluida
e intuitiva, minimizzando i passaggi necessari per completare le azioni
principali. Nonostante queste modifiche, le schermate progettate in
Figma sono state riportate fedelmente nel progetto finale, mantenendo il
design originale come guida durante lo sviluppo.

La decisione di includere le schermate progettate in Figma nella
documentazione non è stata solo un modo per documentare il lavoro
svolto, ma anche per sottolineare l\'importanza del design nelle
applicazioni moderne.

Nel corso della preparazione di questo progetto, non ci siamo limitati
ad apprendere i concetti basilari. Abbiamo voluto esplorare oltre i
confini del corso, ampliando le nostre competenze e approfondendo
tecnologie avanzate come Jetpack Compose. Questo approccio ci ha
permesso di creare un\'applicazione non solo utile e funzionale, ma
anche moderna e all\'avanguardia.

#### Accesso e Registrazione

![Immagine che contiene design, schermata, Elementi grafici, rosso
Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image28.png){width="1.573642825896763in"
height="3.4971227034120735in"} ![Immagine che contiene testo, schermata,
design Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image29.png){width="1.5748031496062993in"
height="3.499701443569554in"} ![Immagine che contiene testo, schermata,
design, logo Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image30.png){width="1.5748031496062993in"
height="3.499701443569554in"}

#### Schermate di Benvenuto

![Immagine che contiene testo, poster, design, grafica Descrizione
generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image31.png){width="1.5748031496062993in"
height="3.499701443569554in"} ![Immagine che contiene testo, schermata,
poster, grafica Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image32.png){width="1.5748031496062993in"
height="3.499701443569554in"} ![Immagine che contiene testo, poster,
grafica, vestiti Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image33.png){width="1.5748031496062993in"
height="3.499701443569554in"}

![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image34.png){width="1.5748031496062993in"
height="3.499701443569554in"}
![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image35.png){width="1.5748031496062993in"
height="3.499701443569554in"}
![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image36.png){width="1.5748031496062993in"
height="3.499701443569554in"}

#### Home e Le Mie Attività

![Immagine che contiene testo, schermata, design Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image37.png){width="1.5748031496062993in"
height="3.499701443569554in"} ![Immagine che contiene testo, schermata,
Cellulare, design Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image38.png){width="1.5748031496062993in"
height="3.499701443569554in"} ![Immagine che contiene testo, schermata,
Carattere Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image39.png){width="1.5748031496062993in"
height="3.499701443569554in"}

![Immagine che contiene testo, schermata, Carattere, ricevuta
Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image40.png){width="1.5748031496062993in"
height="3.499701443569554in"}

#### Profilo Utente e Centro Notifiche

![Immagine che contiene testo, schermata, Cellulare, design Descrizione
generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image41.png){width="1.5748031496062993in"
height="3.499701443569554in"} ![Immagine che contiene testo, schermata
Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image42.png){width="1.5748031496062993in"
height="3.499701443569554in"}

#### Impostazioni

![Immagine che contiene testo, schermata, Carattere, numero Descrizione
generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image43.png){width="1.5748031496062993in"
height="3.499701443569554in"}

### Realizzazione effettiva 

In questa sottosezione, presentiamo l\'effettiva realizzazione della UI
dell\'applicazione TeamSync, mostrando tutte le schermate sia in
modalità chiara che scura. 

#### Accesso

![Immagine che contiene testo, schermata, design Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image44.png){width="1.5748031496062993in"
height="3.411992563429571in"} ![Immagine che contiene testo, schermata,
Cellulare, design Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image45.png){width="1.5748031496062993in"
height="3.411992563429571in"}

#### Registrazione

![Immagine che contiene testo, schermata, Cellulare, Carattere
Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image46.png){width="1.5748031496062993in"
height="3.411992563429571in"}
![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image47.png){width="1.5748031496062993in"
height="3.411992563429571in"}

#### Recupero Password

![Immagine che contiene testo, schermata, design Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image48.png){width="1.5748031496062993in"
height="3.411992563429571in"} ![Immagine che contiene testo, schermata,
Cellulare, design Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image49.png){width="1.5748031496062993in"
height="3.411992563429571in"}

#### Navigation Bar

![Immagine che contiene testo, schermata, design, modello Descrizione
generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image50.png){width="2.4564545056867892in"
height="0.3937007874015748in"} ![Immagine che contiene testo, schermata,
multimediale, Cellulare Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image51.png){width="2.3983180227471568in"
height="0.3937007874015748in"}

#### Home 

![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image52.png){width="1.5748031496062993in"
height="3.411992563429571in"}
![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image53.png){width="1.5748031496062993in"
height="3.411992563429571in"}

#### Crea nuovo progetto

![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image54.png){width="1.5748031496062993in"
height="3.411992563429571in"} ![Immagine che contiene testo, schermata,
Cellulare, multimediale Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image55.png){width="1.5748031496062993in"
height="3.411992563429571in"}

#### Aggiunta di un progetto

![Immagine che contiene testo, schermata, Cellulare Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image56.png){width="1.5748031496062993in"
height="3.411992563429571in"} ![Immagine che contiene testo, schermata,
Cellulare, Dispositivo mobile Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image57.png){width="1.5748031496062993in"
height="3.411992563429571in"}

#### Le Mie Attività

![Immagine che contiene testo, schermata, Sito Web, Pagina Web
Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image58.png){width="1.5748031496062993in"
height="3.411992563429571in"} ![Immagine che contiene testo, schermata,
Cellulare, Dispositivo mobile Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image59.png){width="1.5748031496062993in"
height="3.411992563429571in"}

#### Crea nuova attività

![Immagine che contiene testo, schermata, Cellulare, multimediale
Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image60.png){width="1.5748031496062993in"
height="3.411992563429571in"} ![Immagine che contiene testo, schermata,
Cellulare, design Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image61.png){width="1.5748031496062993in"
height="3.411992563429571in"}

#### Condividi progetto

![Immagine che contiene testo, schermata, multimediale, software
Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image62.png){width="1.5748031496062993in"
height="3.411992563429571in"} ![Immagine che contiene testo, schermata,
Cellulare, Dispositivo mobile Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image63.png){width="1.5748031496062993in"
height="3.411992563429571in"}

#### Informazioni Progetto

![Immagine che contiene testo, schermata, Cellulare, Dispositivo mobile
Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image64.png){width="1.5748031496062993in"
height="3.411992563429571in"}
![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image65.png){width="1.5748031496062993in"
height="3.411992563429571in"}

#### Modifica progetto

![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image66.png){width="1.5748031496062993in"
height="3.411992563429571in"}
![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image67.png){width="1.5748031496062993in"
height="3.411992563429571in"}

#### Centro Notifiche

![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image68.png){width="1.5748031496062993in"
height="3.411992563429571in"} ![Immagine che contiene testo, schermata,
software Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image69.png){width="1.5748031496062993in"
height="3.411992563429571in"}

#### Profilo Utente 

![Immagine che contiene testo, elettronica, schermata, multimediale
Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image70.png){width="1.5748031496062993in"
height="3.411992563429571in"} ![Immagine che contiene testo, schermata,
Cellulare, multimediale Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image71.png){width="1.5748031496062993in"
height="3.411992563429571in"}

#### Profilo Collega

![Immagine che contiene testo, schermata, mela, Sistema operativo
Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image72.png){width="1.5748031496062993in"
height="3.411992563429571in"} ![Immagine che contiene testo, schermata,
Cellulare, multimediale Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image73.png){width="1.5748031496062993in"
height="3.411992563429571in"}

#### Impostazioni

![Immagine che contiene testo, schermata, multimediale Descrizione
generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image74.png){width="1.5748031496062993in"
height="3.411992563429571in"} ![Immagine che contiene testo, schermata,
Carattere Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image75.png){width="1.5748031496062993in"
height="3.411992563429571in"}

#### Modifica Profilo

![Immagine che contiene testo, elettronica, schermata, Cellulare
Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image76.png){width="1.5748031496062993in"
height="3.411992563429571in"} ![Immagine che contiene testo, schermata,
Cellulare, multimediale Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image77.png){width="1.5748031496062993in"
height="3.411992563429571in"}

#### Impostazioni Notifiche

![Immagine che contiene testo, schermata, multimediale, gadget
Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image78.png){width="1.5748031496062993in"
height="3.411992563429571in"} ![Immagine che contiene testo, schermata,
multimediale, Cellulare Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image79.png){width="1.5748031496062993in"
height="3.411992563429571in"}

#### Impostazioni Tema

![Immagine che contiene testo, schermata, multimediale, gadget
Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image80.png){width="1.5748031496062993in"
height="3.411992563429571in"} ![Immagine che contiene testo, schermata,
multimediale Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image81.png){width="1.5748031496062993in"
height="3.411992563429571in"}

#### Impostazioni Attività

![Immagine che contiene testo, schermata, multimediale, gadget
Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image82.png){width="1.5748031496062993in"
height="3.411992563429571in"}
![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image83.png){width="1.5748031496062993in"
height="3.411992563429571in"}

#### Impostazioni Progetti

![Immagine che contiene testo, elettronica, schermata, multimediale
Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image84.png){width="1.5748031496062993in"
height="3.411992563429571in"} ![Immagine che contiene testo, schermata,
Cellulare, multimediale Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image85.png){width="1.5748031496062993in"
height="3.411992563429571in"}

#### Aiuto e Supporto

![Immagine che contiene testo, schermata, multimediale, Sistema
operativo Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image86.png){width="1.5748031496062993in"
height="3.411992563429571in"} ![Immagine che contiene testo, schermata,
Cellulare, multimediale Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image87.png){width="1.5748031496062993in"
height="3.411992563429571in"}

#### Termini e Condizioni

![Immagine che contiene testo, schermata, Carattere, numero Descrizione
generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image88.png){width="1.5748031496062993in"
height="3.411992563429571in"} ![Immagine che contiene testo, schermata,
Carattere Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image89.png){width="1.5748031496062993in"
height="3.411992563429571in"}

#### Nessuna connessione

![Immagine che contiene testo, schermata, Cellulare, multimediale
Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image90.png){width="1.5748031496062993in"
height="3.411992563429571in"} ![Immagine che contiene testo, Cellulare,
schermata, multimediale Descrizione generata
automaticamente](vertopal_4c6415411bb247c783c72c46fa58464b/media/image91.png){width="1.5748031496062993in"
height="3.411992563429571in"}

## Approfondimenti sul codice

Nella sezione \"Approfondimenti sul Codice\" esamineremo in dettaglio le
componenti principali del codice di TeamSync, focalizzandoci sulle parti
più rilevanti e complesse dell\'implementazione. L\'obiettivo è offrire
una comprensione approfondita delle scelte progettuali, delle strutture
adottate, e delle logiche di sviluppo che hanno guidato la realizzazione
dell\'applicazione. Attraverso questa analisi, metteremo in evidenza le
soluzioni tecniche adottate per affrontare le sfide incontrate durante
lo sviluppo, dimostrando come queste contribuiscano alla solidità e
scalabilità dell\'app.

Inoltre, per garantire una documentazione completa e facilmente
accessibile, è stata generata la documentazione del codice Kotlin
utilizzando Dokka. Tutti i dettagli del codice sono ampiamente descritti
e possono essere consultati al seguente link: \[link della
documentazione\].

Per evitare di appesantire ulteriormente la relazione, abbiamo deciso di
concentrarci sull\'approfondimento del codice relativo alle parti più
importanti dell\'applicazione. In particolare, abbiamo scelto di
dettagliare i ViewModel, le funzionalità chiave e la gestione dello
stato, tralasciando la descrizione della repository, alcune componenti
della UI e i file di utilità. Questa scelta ci ha permesso di mantenere
un equilibrio tra la completezza della documentazione e la sua
fruibilità, focalizzandoci sugli aspetti che riteniamo più critici per
la comprensione del funzionamento e dell\'architettura complessiva
dell\'applicazione.

### Creazione e Gestione degli Utenti

Il **ViewModelUtente** è una classe di tipo *ViewModel* progettata per
gestire tutte le operazioni relative all\'utente all\'interno
dell\'applicazione TeamSync, come la gestione del login, della
registrazione e del profilo utente. Questa classe rappresenta un punto
centrale nella gestione dello stato dell\'utente, mantenendo i dati
relativi all\'utente attuale e comunicando con il repository
(*RepositoryUtente*) per eseguire operazioni di rete e interagire con il
database.

**Gestione dello Stato dell\'Utente**

Il *ViewModelUtente* utilizza variabili di stato
come *utenteCorrente*, *loginRiuscito*, *registrazioneRiuscita* e *primoAccesso* per
tracciare l\'utente attuale e gestire lo stato delle operazioni comuni
come il login, la registrazione e il reset della password. Queste
variabili di stato sono esposte come proprietà immutabili per evitare
modifiche non autorizzate da altre parti dell\'applicazione.

Il *ViewModelUtente* include metodi come *login()* e *signUp()* per
gestire il processo di autenticazione degli utenti. Questi metodi
eseguono una validazione iniziale dei dati inseriti dall\'utente, come
l\'indirizzo email e la password, per assicurarsi che siano conformi ai
requisiti dell\'applicazione. Se la validazione ha esito positivo, viene
avviata una richiesta al repository per eseguire l\'operazione di login
o registrazione. In caso di errori durante queste operazioni,
il ViewModelUtente aggiorna le variabili di stato corrispondenti,
come *erroreLogin* o *erroreRegistrazione*, per fornire feedback
immediato all\'utente.

**Gestione del Profilo Utente**

Il ViewModelUtente è responsabile anche della gestione del profilo
utente, che include il caricamento e l\'aggiornamento delle informazioni
dell\'utente. Attraverso metodi
come *getUserProfile()* e *updateUserProfile()*,
il ViewModelUtente comunica con il repository per recuperare o
aggiornare i dati del profilo. In caso di operazioni di aggiornamento,
viene effettuata una validazione dei campi del profilo prima di inviare
i dati aggiornati al repository.

**Gestione delle Amicizie**

Il ViewModelUtente offre anche funzionalità per la gestione delle
relazioni tra gli utenti, come l\'aggiunta o la rimozione di amici,
attraverso metodi come *faiAmicizia()* e *finisciAmicizia()*. Queste
operazioni coinvolgono la modifica delle liste di amici degli utenti e
richiedono il coinvolgimento del repository per l\'aggiornamento dei
dati nel database.

**Caricamento delle Immagini e Reset della Password**

La classe gestisce anche operazioni specifiche come il caricamento delle
immagini del profilo (*aggiornaFotoProfilo()*) utilizzando un gestore
delle immagini *(ImageUploader*), e il reset della password
(resetPassword()), fornendo un\'interfaccia unificata per l\'utente per
gestire tutti gli aspetti relativi al proprio account.

**Validazione e Gestione degli Errori**

Una delle caratteristiche principali del ViewModelUtente è l\'attenzione
alla validazione dei dati e alla gestione degli errori. Metodi
come *validateRegistrationField()* e *validaCampiProfilo()* garantiscono
che i dati inseriti dall\'utente siano conformi alle regole stabilite
prima di inviare richieste al repository. In caso di errori,
il ViewModelUtente gestisce e registra gli errori in modo appropriato,
fornendo messaggi di errore chiari e specifici che possono essere
visualizzati all\'utente.

In sintesi, il ViewModelUtente centralizza la logica di gestione
dell\'utente, mantenendo lo stato dell\'utente, gestendo le operazioni
di autenticazione e registrazione, e interfacciandosi con il repository
per garantire che tutte le operazioni vengano eseguite correttamente e
in sicurezza.

### Creazione e Gestione dei Progetti

#### ViewModelProgetto

Il **ViewModelProgetto** è responsabile di una vasta gamma di operazioni
legate alla gestione dei progetti all\'interno dell\'applicazione.
Questo include la creazione di nuovi progetti, l\'aggiunta di
partecipanti, la visualizzazione delle informazioni dei progetti,
l\'aggiornamento del loro stato, e l\'abbandono dei progetti. Di
seguito, vengono riportate le principali funzioni offerte dal ViewModel.

**Creazione di un Nuovo Progetto**

Il processo di creazione di un nuovo progetto è gestito dalla
funzione creaProgetto(). Questa funzione consente all\'utente di
definire i dettagli del progetto, come il nome, la descrizione, la data
di scadenza e la priorità. Inoltre, durante la creazione, viene generato
un codice univoco per il progetto, che può essere utilizzato per
aggiungere altri partecipanti in un secondo momento. Una volta creato,
il progetto viene salvato nel repository, e le informazioni sono
immediatamente disponibili nella UI per l\'utente.

**Aggiunta di un Partecipante**

Dopo aver creato un progetto, è possibile invitare ulteriori
partecipanti utilizzando un codice univoco associato al progetto. Questa
operazione viene gestita dal metodo aggiungiPartecipanteConCodice().
L\'utente inserisce il codice fornito dal creatore del progetto, e il
sistema verifica che l\'utente non sia già un membro e che il codice sia
valido. Se la verifica ha esito positivo, l\'utente viene aggiunto al
progetto, e la lista dei partecipanti viene aggiornata nel repository e
riflessa nella UI.

Un partecipante può essere aggiunto al progetto anche tramite invito
diretto. In questo caso, l\'utente può visitare il profilo di un collega
amico, cliccare sul pulsante \"Aggiungi a un Progetto\" e selezionare il
progetto desiderato. Questa operazione è gestita da una funzione
differente, aggiungiPartecipanteAlProgetto().

L'utente destinatario potrà accettare o rifiutare l'invito diretto al
progetto nel centro notifiche.

**Visualizzazione e Caricamento dei Progetti**

l ViewModelProgetto gestisce il caricamento e la gestione dei progetti
associati sia all\'utente corrente sia a un collega, utilizzando le
funzioni caricaProgettiUtente() e caricaProgettiCollega(). Queste
funzioni recuperano i progetti dal repository e li memorizzano in
variabili LiveData distinte, consentendo alla UI di reagire
automaticamente ai cambiamenti e aggiornando dinamicamente le
informazioni visualizzate. Inoltre, il ViewModel carica anche le
attività correlate a ciascun progetto dell'utente autenticato, offrendo
un quadro completo delle attività in corso direttamente nella schermata
Home. Questo permette all\'utente di visualizzare un\'anteprima di ogni
progetto, inclusi il numero di attività incomplete e la data di scadenza
del progetto.

La funzione caricaProgettiCollega() viene richiamata all\'interno della
schermata del profilo di un collega, permettendo così il caricamento
delle informazioni sui progetti del collega visualizzato. Il caricamento
delle informazioni del collega viene gestito tramite
diversi LaunchedEffect per garantire che la UI si aggiorni correttamente
quando i dati del collega cambiano.

**Aggiornamento dei Dettagli del Progetto**

Il ViewModel consente anche la modifica delle informazioni di un
progetto esistente tramite il metodo aggiornaProgetto(). Questo metodo
permette all\'utente di aggiornare dettagli come il nome, la
descrizione, la data di scadenza, la priorità e il voto del progetto.
Dopo l\'aggiornamento, le informazioni vengono sincronizzate nel
repository e riflettute nella UI, garantendo che tutte le modifiche
siano immediatamente visibili e accessibili.

**Aggiornamento dello Stato di Completamento del Progetto**

Per gestire l\'avanzamento del progetto, il ViewModel offre la
funzione aggiornaStatoProgetto(), che consente di segnare un progetto
come completato. Questa funzione aggiorna lo stato del progetto nel
repository e assicura che i cambiamenti siano riflessi nell\'interfaccia
utente, permettendo all\'utente di visualizzare chiaramente quali
progetti sono attivi e quali sono stati completati.

**Abbandono di un Progetto**

Infine, il ViewModelProgetto include una funzione per l\'abbandono di un
progetto tramite il metodo abbandonaProgetto(). Questo metodo permette
all\'utente di rimuoversi da un progetto in cui non vuole più
partecipare. Se l\'operazione va a buon fine, il progetto viene rimosso
dalla lista dell\'utente e tutte le notifiche correlate vengono
eliminate, mantenendo la UI aggiornata e priva di elementi non più
rilevanti.

#### UI

**Creazione di un Progetto**

Quando un utente desidera creare un nuovo progetto, viene mostrata una
schermata di inserimento dei dettagli del progetto, come il nome, la
descrizione, la data di scadenza e la priorità. La
funzione creaProgetto() del ViewModelProgetto viene chiamata quando
l\'utente conferma la creazione.

-   **LaunchedEffect**: Dopo che l\'utente ha inserito i dettagli e ha
    confermato, un LaunchedEffect viene utilizzato per osservare lo
    stato di aggiungiProgettoRiuscito. Se la creazione ha successo, la
    UI reagisce chiudendo la schermata di creazione e mostrando un
    messaggio di conferma.

**Aggiunta di un Partecipante tramite Codice**

Per aggiungere un partecipante a un progetto esistente utilizzando un
codice univoco, la UI presenta una schermata in cui l\'utente può
inserire il codice del progetto. La
funzione aggiungiPartecipanteConCodice() viene chiamata per verificare e
aggiungere il partecipante.

LaunchedEffect: Un LaunchedEffect viene utilizzato per monitorare lo
stato di aggiungiProgettoRiuscito. Se il codice è valido e l\'utente è
aggiunto con successo, la UI aggiorna la lista dei partecipanti e mostra
un messaggio di conferma.

**Visualizzazione dei Progetti**

Nella schermata principale dell\'applicazione, viene mostrata una lista
dei progetti a cui l\'utente partecipa. La
funzione caricaProgettiUtente() viene chiamata all\'inizio per
recuperare e visualizzare questi progetti, garantendo che l\'utente
abbia immediatamente accesso alle informazioni più aggiornate sui
progetti in corso.

Per gestire correttamente il caricamento dei progetti, viene utilizzato
un LaunchedEffect, che richiama la funzione caricaProgettiUtente() non
appena il ViewModelProgetto viene inizializzato. Questo approccio
assicura che i progetti siano caricati e visualizzati immediatamente
quando l\'utente apre l\'applicazione, offrendo un\'esperienza utente
fluida e reattiva.

La gestione del profilo del collega è altrettanto cruciale e avviene
attraverso una serie di LaunchedEffect che assicurano che i dati del
collega siano sempre aggiornati e accurati:

*Caricamento del Profilo del Collega***:** Un LaunchedEffect viene
utilizzato per chiamare la
funzione ottieniCollega(id) nel ViewModelUtente ogni volta che cambia
l\'ID del collega. Questo effetto garantisce che il profilo del collega
sia caricato correttamente non appena l\'ID del collega è disponibile.

*Aggiornamento dei Dati del Collega***:** Un altro LaunchedEffect viene
impiegato per aggiornare i dati del profilo del collega nella UI non
appena questi vengono caricati. Qui, vengono aggiornati campi come il
nome, il cognome, la matricola, e l\'email del collega. Inoltre, la
funzione caricaProgettiCollega(id, true) viene chiamata per caricare i
progetti a cui il collega sta partecipando. Questo effetto include anche
un controllo per verificare se esiste già una richiesta di amicizia tra
l\'utente e il collega visualizzato, aggiornando lo stato della
richiesta in tempo reale.

*Aggiornamento dei Progetti del Collega***:** Infine, un
ulteriore LaunchedEffect viene utilizzato per ricaricare i progetti del
collega ogni volta che cambia l\'ID del profilo del collega. Questo
assicura che i progetti visualizzati nella UI siano sempre sincronizzati
con i dati effettivi del collega.

**Ulteriori precisazioni**

Oltre ai LaunchedEffect, la UI utilizza altri strumenti di gestione
dello stato come remember e State per mantenere il controllo sugli stati
dell\'interfaccia utente in relazione alle funzioni del ViewModel. Ogni
volta che una funzione del ViewModel cambia lo stato dell\'applicazione,
la UI reagisce automaticamente grazie al meccanismo di composizione
reattiva di Jetpack Compose.

### Creazione e Gestione delle Attività

#### LeMieAttivitaViewModel

Il LeMieAttivitaViewModel è un componente centrale nella gestione delle
attività (tasks) all\'interno dell\'applicazione. Questo ViewModel si
occupa di tutte le operazioni relative alle attività, dalla creazione
alla modifica, dall\'eliminazione al completamento, mantenendo
sincronizzati i dati tra l\'interfaccia utente e il backend. Di seguito
è riportata una descrizione dettagliata delle sue principali
funzionalità.

**Gestione delle Attività**

*Recupero delle Attività per Progetto*: Il ViewModel include diverse
funzioni per recuperare le attività associate a un progetto specifico.
Le
funzioni getTodoUtente(), getNonCompletedTodoByProject(), getTodoCompletateByProject() e getAllTodoByProject() permettono
di ottenere, rispettivamente, le attività per un utente, le attività non
completate, le attività completate, e tutte le attività di un progetto.
Queste funzioni utilizzano il repository per accedere ai dati e popolano
le variabili di
stato leMieAttivita, leMieAttivitaNonCompletate, leMieAttivitaCompletate,
e leMieAttivitaPerUtente, garantendo che la UI sia sempre aggiornata.

*Creazione di un\'Attività*: La funzione addTodo() consente di creare
una nuova attività all\'interno di un progetto. Prima di procedere con
la creazione, viene effettuata una validazione dei campi di input (come
il titolo e la data di scadenza) per assicurarsi che siano conformi alle
regole definite. Se la validazione è superata, l\'attività viene
aggiunta al progetto e la lista delle attività viene aggiornata di
conseguenza.

*Modifica di un\'Attività*: La funzione uploadFileAndSaveTodo() gestisce
la modifica di un\'attività esistente. Questo metodo consente di
aggiornare i dettagli di un\'attività, come il titolo, la descrizione,
la data di scadenza, la priorità e il file allegato. Anche in questo
caso, viene effettuata una validazione dell\'input prima di procedere
con l\'aggiornamento. Se l\'attività è associata a un file, il file
viene caricato su Firebase Storage e il suo URL viene memorizzato
nell\'attività.

*Eliminazione di un\'Attività*: La funzione deleteTodo() consente di
eliminare un\'attività specifica. Una volta eliminata l\'attività, la
lista delle attività viene aggiornata per riflettere i cambiamenti.

*Completamento di un\'Attività*: La funzione completeTodo() permette di
segnare un\'attività come completata o di annullare la sua
completazione. Anche in questo caso, la lista delle attività viene
aggiornata per riflettere lo stato corrente delle attività.

*Aggiunta e Rimozione di Persone da un\'Attività*: Le
funzioni aggiungiPersona() e rimuoviPersona()gestiscono rispettivamente
l\'aggiunta e la rimozione di utenti da un\'attività. Queste funzioni
sono particolarmente utili in un contesto di collaborazione, dove più
utenti possono essere coinvolti in una singola attività.

**Gestione dei File Allegati**

Il ViewModel include la capacità di gestire file allegati alle attività.
La funzione uploadFile() gestisce il caricamento di file su Firebase
Storage, restituendo l\'URL del file caricato. Questo URL viene poi
utilizzato per associare il file all\'attività pertinente.

In questo caso, la versione richiesta è TIRAMISU, che corrisponde ad
Android 13 (API level 33). 

**Gestione della Progressione delle Attività**

Il ViewModel calcola e aggiorna automaticamente la progressione delle
attività completate all\'interno di un progetto. Le
funzioni updateProgress(), updateTaskCompletate(),
e updateTaskTotali() sono responsabili di monitorare il numero di
attività totali e completate, e di calcolare la percentuale di
completamento del progetto. Questi dati vengono esposti alla UI tramite
variabili LiveData, permettendo all\'interfaccia utente di visualizzare
in tempo reale l\'avanzamento del progetto.

**Gestione degli Errori e Caricamento**

Il ViewModel gestisce lo stato di caricamento e gli errori che possono
verificarsi durante le operazioni. Variabili
come \_isLoading, \_erroreAggiungiAttivita,
e \_erroreEditAttivita tengono traccia dello stato attuale e segnalano
eventuali problemi all\'interfaccia utente, consentendo di fornire
feedback tempestivi agli utenti.

**Validazione dell\'Input**

Funzioni come validateInput() e isDateBeforeToday() sono utilizzate per
assicurare che i dati forniti dall\'utente rispettino le regole
dell\'applicazione. Queste funzioni evitano l\'inserimento di dati non
validi, migliorando l\'affidabilità complessiva del sistema.

#### UI

**Inizializzazione e Caricamento dei Dati**

*Caricamento del Progetto e delle Attività*: Utilizzando
il LaunchedEffect associato all\'ID del progetto (idProg), la UI avvia
il caricamento dei dettagli del progetto e delle attività correlate non
appena viene montata la composizione. Questo assicura che tutte le
informazioni rilevanti siano disponibili e visualizzate immediatamente
all\'utente. La UI recupera sia le attività completate che quelle non
completate del progetto corrente.

*Verifica della Connessione Internet:* Un altro LaunchedEffect è
utilizzato per monitorare continuamente lo stato della connessione
Internet, verificando ogni 5 secondi. In caso di disconnessione, viene
visualizzata una schermata apposita che invita l\'utente a riprovare.

*Aggiornamento della Lista dei Partecipanti*: Quando la lista dei
partecipanti a un progetto cambia, un LaunchedEffect specifico si attiva
per aggiornare la UI. Questo effetto utilizza una chiamata al ViewModel
per ottenere i dati aggiornati.

**Interazione con l\'Utente**

*Selezione delle Attività*: L\'interfaccia permette all\'utente di
filtrare le attività visualizzate in base al loro stato: completate, non
completate, o solo le attività assegnate a se stesso. A seconda della
sezione selezionata (individuata tramite il valore di sezione),
il LaunchedEffect corrispondente viene attivato per caricare le attività
appropriate.

*Aggiunta di una Nuova Attività*: Un pulsante di azione flottante
consente all\'utente di aggiungere nuove attività al progetto. Quando
viene cliccato, si apre una dialog (AddTodoDialog) che permette di
inserire tutti i dettagli della nuova attività. Se il progetto è
completato, il pulsante non è disponibile.

*Modifica e Completamento delle Attività*: Le attività visualizzate
possono essere modificate o completate. Cliccando su un\'attività,
l\'utente può accedere a un dialogo di modifica (EditTodoDialog) o
segnare l\'attività come completata tramite il CompleteDialog. Questi
dialoghi utilizzano LaunchedEffect per gestire errori e successi,
garantendo un feedback immediato all\'utente.

*Ordinamento delle Attività*: Le attività possono essere ordinate per
data di scadenza, priorità, o data di creazione, grazie a un comparatore
che organizza la lista visualizzata nella LazyColumn.

**Gestione degli Errori e Feedback all\'Utente**

*Errori nell\'Aggiunta o Modifica delle Attività*: Quando si verifica un
errore durante l\'aggiunta o la modifica di un\'attività, viene mostrato
un Toast con il messaggio di errore. Questo è gestito tramite LiveData.

*Dialoghi di Conferma*: La UI include anche dialoghi di conferma per
operazioni critiche, come l\'abbandono del progetto
(AbbandonaProgettoDialog). Questi dialoghi offrono un\'ulteriore
verifica prima che l\'utente proceda con azioni irreversibili.

**Schermata di Riepilogo delle Attività**

*Card dei Progressi del Progetto*: Una card visualizza un riepilogo
delle attività completate e non completate, insieme alla percentuale di
completamento del progetto. Questa card utilizza
un CircularProgressIndicator per rappresentare graficamente la
progressione.

*Visualizzazione Dettagliata delle Attività*: Ogni attività può essere
espansa per visualizzare maggiori dettagli, inclusi i partecipanti e i
file allegati, tramite un dialogo (ExpandedDialog).

**Gestione della Condivisione e Abbandono del Progetto**

*Condivisione del Progetto*: L\'utente può condividere il progetto con
altri membri tramite un dialogo che mostra un codice di progetto
univoco.

*Abbandono del Progetto*: Se l\'utente decide di abbandonare il
progetto, un dialogo di conferma viene mostrato per evitare azioni
accidentali. Il ViewModel gestisce l\'operazione di abbandono e aggiorna
la UI di conseguenza.

**Gestione del Tema**

*Tema Scuro/Chiaro*: L\'interfaccia supporta sia il tema scuro che
chiaro, con colori adattati dinamicamente per assicurare una buona
leggibilità e un\'esperienza utente uniforme indipendentemente dal tema
scelto.

### Creazione e Gestione delle Notifiche

#### ViewModelNotifiche

Il ViewModelNotifiche gestisce tutte le operazioni relative alle
notifiche degli utenti. Questo viewModel fa da intermediario tra il
repository delle notifiche (\`RepositoryNotifiche\`) e l'interfaccia
utente (\`Notifiche\`) assicurandosi che i dati siano sempre
sincronizzati.

**Caricamento delle Notifiche**

Il ViewModelNotifiche carica le notifiche per l\'utente corrente tramite
la funzione fetchNotifiche. Questa funzione viene chiamata
automaticamente quando l\'ID utente diventa disponibile, garantendo che
le notifiche siano sempre aggiornate. Durante il caricamento, viene
attivato lo stato isLoading, che può essere utilizzato dalla UI per
mostrare un indicatore di caricamento.

**Gestione dello stato delle Notifiche**

Il ViewModel consente di marcare le notifiche come lette tramite la
funzione cambiaStatoNotifica. Questo metodo aggiorna lo stato delle
notifiche sia nel repository remoto sia nella lista locale gestita dal
ViewModel, riflettendo immediatamente il cambiamento nell\'interfaccia
utente.

**Creazione di Notifiche**

La funzione creaNotifica permette di creare nuove notifiche nel sistema.
Questa funzione è utilizzata, ad esempio, per inviare notifiche ai
membri di un progetto quando un\'attività viene completata o modificata.
La creazione delle notifiche avviene in background, evitando blocchi nel
thread principale dell\'applicazione.

**Eliminazione delle Notifiche**

Il ViewModel supporta l\'eliminazione di notifiche specifiche o di tutte
le notifiche di un utente tramite le
funzioni eliminaNotifica ed eliminaNotificheUtente. Queste operazioni
aggiornano il repository remoto e rimuovono le notifiche dalla lista
locale, assicurando che la UI rifletta sempre lo stato più aggiornato.

**Gestione delle richieste di amicizia**

Il ViewModel include funzioni specifiche per gestire le richieste di
amicizia, come controllaRichiestaAmicizia, che verifica se esiste una
richiesta di amicizia tra due utenti, e cambiastatoAccettatoNotifica,
che aggiorna lo stato di una richiesta di amicizia accettata.

**Gestione degli Errori**

Il ViewModelNotifiche gestisce accuratamente gli errori che possono
verificarsi durante le operazioni di rete, come il caricamento o
l\'eliminazione delle notifiche. Gli errori vengono salvati in variabili
di stato (erroreLetturaNotifiche, erroreEliminazioneNotifiche), che
possono essere osservate dalla UI per fornire feedback all\'utente, come
la visualizzazione di messaggi di errore.

#### UI

La schermata delle notifiche è progettata per gestire e visualizzare le
notifiche dell\'utente all\'interno dell\'applicazione. È realizzata
con **Jetpack Compose** e supporta un\'interfaccia utente moderna e
reattiva, ottimizzata sia per il tema chiaro che per il tema scuro.

La schermata è composta da diverse sezioni principali:

1.  **TopAppBar**: *CenterAlignedTopAppBar*, un\'app bar centrata con il
    titolo \"Notifiche\", stilizzato per essere prominente. Include un
    pulsante di refresh che consente agli utenti di aggiornare
    manualmente l\'elenco delle notifiche. Il colore della barra si
    adatta al tema corrente dell\'applicazione (chiaro o scuro).

2.  **TabRow**: Viene utilizzato un TabRow per gestire due categorie di
    notifiche: \"Non Lette\" e \"Lette\". Gli utenti possono navigare
    tra queste categorie per vedere le notifiche non ancora aperte o
    tutte le notifiche. Un indicatore colorato sotto la tab attiva
    fornisce un feedback visivo immediato per indicare quale categoria
    di notifiche è attualmente selezionata.

3.  **Contenuto Principale**

    -   **Loader di Caricamento**: Quando le notifiche sono in fase di
        caricamento, viene visualizzato un indicatore circolare che
        informa l\'utente del progresso.

    -   **Gestione Connessione Internet**: La UI verifica continuamente
        la disponibilità della connessione internet. Se la connessione è
        assente, viene mostrata una schermata che invita l\'utente a
        riprovare.

    -   **Schermata Vuota**: Se non ci sono notifiche da visualizzare,
        viene mostrata una schermata vuota con un messaggio informativo
        e un\'immagine rappresentativa.

    -   **LazyColumn**: Le notifiche vengono elencate in una LazyColumn,
        che consente di gestire grandi quantità di notifiche in modo
        efficiente.

4.  **NotificationItem**: Ogni notifica viene visualizzata utilizzando
    un composable NotificationItem, che mostra dettagli come il
    contenuto della notifica, l\'icona associata e lo stato (aperta o
    non aperta).Gli utenti possono cliccare su una notifica per aprirla
    e gestire direttamente richieste come amicizie o inviti a progetti.
    Ogni NotificationItem è progettato per reagire ai clic e aggiornare
    lo stato della notifica.

5.  **Gestione Azioni**: La schermata offre anche azioni rapide per
    gestire le notifiche, come contrassegnare tutte come lette o
    eliminare tutte le notifiche lette.

## Testing

Nella fase di testing del progetto, abbiamo dedicato particolare
attenzione sia agli Unit Test che agli Android Test, al fine di
garantire la qualità e l\'affidabilità dell\'applicazione.

### Unit Test

Gli Unit Test sono stati implementati per verificare il corretto
funzionamento delle singole unità di codice, come funzioni e classi,
isolandole dal contesto esterno. Questo ci ha permesso di identificare
rapidamente eventuali bug o comportamenti inattesi nelle singole
componenti logiche dell\'applicazione. Durante questa fase, abbiamo
incontrato alcune difficoltà legate alla necessità di simulare
dipendenze esterne e al mantenimento dell\'indipendenza dei test, ma
queste sfide sono state superate con l\'utilizzo di framework di
mocking. In particolare abbiamo utilizzato le seguenti dipendenze:

-   **JUnit 4**:Abbiamo utilizzato JUnit 4 come framework di base per
    scrivere e organizzare gli Unit Test. JUnit fornisce le annotazioni
    essenziali come @Test, @Before, @After, che ci hanno permesso di
    definire e gestire i nostri test in modo strutturato.

    -   *testImplementation*(*libs*.*junit*)

-   **Mockito**: Per simulare le dipendenze esterne e isolare le unità
    di codice sotto test, abbiamo fatto largo uso di Mockito. Questo
    strumento ci ha consentito di creare mock degli oggetti e di
    definire i loro comportamenti, permettendo di testare le unità di
    codice senza dover dipendere da implementazioni concrete.

    -   testImplementation(libs.mockito.core.v3112)

    -   testImplementation(libs.mockito.kotlin)

-   **Kotlin Coroutines Test**: Per gestire e testare le coroutine, che
    sono una parte centrale del nostro progetto, abbiamo utilizzato la
    libreria kotlinx-coroutines-test. Questa libreria ci ha permesso di
    controllare l\'esecuzione delle coroutine durante i test, garantendo
    che le operazioni asincrone fossero testate in modo deterministico.

    -   testImplementation(libs.kotlinx.coroutines.test)

```{=html}
<!-- -->
```
-   **AndroidX Core Testing**: Per facilitare il testing delle
    funzionalità specifiche di Android, abbiamo integrato la
    libreria androidx.core.testing, che fornisce strumenti utili per
    testare componenti Android come LiveData e ViewModels.

    -   testImplementation(libs.androidx.core.testing)

-   **MockK**: Insieme a Mockito, abbiamo utilizzato **MockK** come
    alternativa moderna e flessibile per il mocking, particolarmente
    efficace con Kotlin. Questa libreria ci ha permesso di creare mock
    in modo più intuitivo e di simulare comportamenti complessi come
    quelli delle funzioni di estensione.

Durante il nostro lavoro di testing, **MockK** ha giocato un ruolo
cruciale nel facilitare il processo di mock delle dipendenze
all\'interno dei nostri Unit Test, specialmente quando ci siamo trovati
di fronte a classi e funzioni complesse.

MockK è stato scelto come principale strumento per il mocking per
diverse ragioni:

-   Compatibilità con Kotlin;

-   Una delle difficoltà incontrate durante il testing riguardava il
    mocking delle funzioni di estensione. Con MockK, siamo riusciti a
    mockare queste funzioni con facilità, permettendoci di isolare
    meglio le unità di codice sotto test;

-   Gestione efficace delle coroutine;

Abbiamo deciso di concentrare i nostri test sui **ViewModel** poiché
rappresentano la **parte centrale** del nostro progetto, dove risiede la
maggior parte della logica applicativa. Dato il tempo limitato e la
complessità del nostro codice, abbiamo preferito evitare di mockare
manualmente ogni singola repository. Invece, abbiamo
utilizzato **MockK** per simulare il comportamento delle repository,
scegliendo i valori restituiti dalle loro funzioni. Questo approccio ci
ha permesso di risparmiare tempo, mantenendo comunque un elevato livello
di copertura e garantendo la qualità del codice senza aggiungere un
ulteriore livello di complessità.

#### ViewModelUtenteTest

Il file contiene quattro test che verificano le principali funzionalità
del ViewModelUtente:

1.  **Test di Registrazione Riuscita**:

> Questo test verifica che la registrazione di un utente con dati
> corretti avvenga con successo.
>
> Viene creato un mock di RepositoryUtente che simula la registrazione
> di un utente ritornando un oggetto FirebaseUser. Inoltre, viene
> mockata la validazione dell\'email per garantire che l\'email sia
> considerata valida.
>
> **Verifica**: Si verifica che la proprietà erroreRegistrazione del
> ViewModel sia nulla e che registrazioneRiuscita sia true.

2.  **Test di Registrazione Fallita per Data di Nascita Invalida**:

> Questo test verifica che la registrazione fallisca quando l\'utente
> inserisce una data di nascita non valida (futura).
>
> La data di nascita viene impostata su una data futura e il
> comportamento del repository viene mockato come nel test precedente.
> Viene anche mockata la validazione dell\'email.
>
> **Verifica**: Si controlla che erroreRegistrazione contenga il
> messaggio di errore corretto e che registrazioneRiuscita sia false.

3.  **Test di Reset della Password con Successo**:

> Questo test verifica che la funzione di reset della password funzioni
> correttamente quando l\'email è valida.
>
> Viene mockata la funzione resetPassword del repository per eseguire
> senza errori. Viene anche mockata la validazione dell\'email.
>
> **Verifica**: Si controlla che resetPasswordRiuscito sia true e
> che erroreResetPassword sia nulla.

4.  **Test di Reset della Password con Email Invalida**:

> Questo test verifica che il reset della password fallisca quando
> l\'email fornita non è valida.
>
> L\'email viene impostata su un valore non valido e viene mockata la
> validazione per restituire false.
>
> **Verifica**: Si controlla che resetPasswordRiuscito sia false e
> che erroreResetPassword contenga il messaggio di errore corretto.

#### ViewModelProgettoTest

**1. creaProgetto avvenuta con successo:**

> Verifica che un progetto venga creato correttamente quando vengono
> forniti dati validi.
>
> Viene simulata la generazione di un codice progetto e la creazione del
> progetto tramite RepositoryProgetto.
>
> **Verifica**: Il test verifica che non ci siano errori
> (erroreAggiungiProgetto) e che il progetto sia stato aggiunto con
> successo (aggiungiProgettoRiuscito).

3.  **creaProgetto fallita per dataScadenza non valida**:

> Verifica che la creazione di un progetto fallisca se la data di
> scadenza è precedente alla data corrente.
>
> Viene simulata una data di scadenza non valida e il comportamento del
> repository come nel test precedente.
>
> **Verifica**: Il test si assicura che venga restituito un messaggio di
> errore appropriato e che il progetto non venga aggiunto.

1.  **aggiornaProgetto aggiorna correttamente un progetto esistente:**

> Verifica che un progetto esistente venga aggiornato correttamente.
>
> Simula il recupero di un progetto esistente e l\'aggiornamento dello
> stesso tramite RepositoryProgetto.
>
> **Verifica**: Il test verifica che il metodo per aggiornare il
> progetto nel repository sia stato chiamato correttamente.

2.  **aggiornaStatoProgetto aggiorna correttamente lo stato di un
    progetto:**

> Verifica che lo stato di un progetto venga aggiornato correttamente
> (da incompleto a completato).
>
> Simula il recupero di un progetto e l\'aggiornamento del suo stato.
>
> **Verifica**: Il test verifica che il metodo di aggiornamento del
> progetto nel repository sia stato effettivamente chiamato.

3.  **aggiungiPartecipanteConCodice aggiunge correttamente un
    partecipante ad un progetto:**

> Verifica che un partecipante venga correttamente aggiunto a un
> progetto utilizzando un codice di progetto valido.
>
> Viene simulato il recupero dell\'ID del progetto tramite codice e
> l\'aggiunta del partecipante.
>
> **Verifica**: Il test verifica che il metodo di aggiunta del
> partecipante sia stato chiamato e che non vi siano errori.

4.  **aggiungiPartecipanteConCodice non aggiunge un partecipante ad un
    progetto a causa del codice errato:**

> Verifica che l\'aggiunta di un partecipante fallisca se viene fornito
> un codice di progetto non valido.
>
> Simula un codice di progetto errato e verifica che il metodo di
> aggiunta del partecipante non venga chiamato.
>
> **Verifica**: Il test si assicura che venga restituito un messaggio di
> errore e che l\'aggiunta del partecipante non sia riuscita.

5.  **caricaProgettiUtente carica correttamente i progetti
    dell\'utente:**

> Verifica che i progetti associati a un utente vengano caricati
> correttamente.
>
> Simula il caricamento dei progetti utente dal repository e il
> conteggio delle attività incomplete associate ai progetti.
>
> **Verifica**: Il test verifica che i progetti caricati corrispondano a
> quelli attesi.

#### LeMieAttivitaViewModel

Il codice riportato contiene una serie di test unitari per
il LeMieAttivitaViewModel, che è il ViewModel responsabile della
gestione delle attività all\'interno dell\'applicazione.
Utilizzando MockK e la libreria Coroutine Test, questi test verificano
che le funzioni principali del ViewModel, come l\'aggiunta, la
cancellazione, il completamento e il recupero delle attività, funzionino
correttamente e gestiscano correttamente i casi d\'errore.

1.  **addTodo aggiunge una nuova attivita con successo**:

> Verificare che una nuova attività venga aggiunta correttamente quando
> vengono forniti dati validi.
>
> Viene simulata l\'aggiunta di una nuova attività tramite
> il ToDoRepository.
>
> Il test verifica che il metodo addTodo del repository venga chiamato
> con i parametri corretti.

2.  **addTodo non aggiunge una nuova attivita a causa del titolo
    vuoto**:

> Verificare che l\'aggiunta di una nuova attività fallisca se il titolo
> è vuoto.
>
> Viene simulata una chiamata con un titolo vuoto, e il test verifica
> che venga restituito un messaggio d\'errore appropriato.
>
> **Verifica:** Si assicura che il metodo addTodo del repository non
> venga chiamato.

3.  **deleteTodo elimina correttamente un\'attivita**:

> Verifica che un\'attività venga eliminata correttamente.
>
> Viene simulata l\'eliminazione di un\'attività tramite
> il ToDoRepository.
>
> **Verifica:** Il test verifica che il metodo deleteTodo del repository
> venga chiamato con il giusto ID dell\'attività.

4.  **completeTodo annulla correttamente il completamento di
    un\'attivita**:

> Verifica che lo stato di completamento di un\'attività possa essere
> annullato correttamente.
>
> Viene simulata la modifica dello stato di completamento di
> un\'attività a false.
>
> **Verifica:** Il test verifica che il metodo completeTodo del
> repository venga chiamato con i parametri corretti.

5.  **completeTodo completa correttamente un\'attivita**:

> Verifica che un\'attività possa essere marcata come completata
> correttamente.
>
> Simile al test precedente, ma questa volta lo stato di completamento è
> impostato a true.
>
> **Verifica:** Si verifica che il repository gestisca correttamente la
> richiesta.

6.  **getTodoById recupera correttamente un\'attivita dato il suo ID**:

> Verifica che un\'attività venga recuperata correttamente dal
> repository tramite il suo ID.
>
> Viene simulato il recupero di un\'attività specifica dal repository.
>
> **Verifica:** Il test verifica che il risultato sia l\'attività
> prevista.

7.  **getTodoById non recupera correttamente un\'attivita**:

> Verifica che la funzione restituisca null se un\'attività con l\'ID
> specificato non viene trovata.
>
> Viene simulata una situazione in cui il repository non trova
> l\'attività richiesta.
>
> **Verifica:** Il test verifica che il metodo del ViewModel
> restituisca null in questo caso.

#### Difficoltà incontrate

Durante l\'implementazione dei test, abbiamo incontrato vari problemi,
come errori nella creazione di mock per classi complesse o difficoltà
nel simulare comportamenti asincroni correttamente. In particolare, ci
sono stati problemi nel mockare funzioni che ritornavano valori
differenti a seconda delle condizioni. Grazie a MockK, siamo riusciti a
risolvere questi problemi, utilizzando funzionalità avanzate
come ***every { \... } returns*** per definire comportamenti diversi in
base alle necessità dei test.

#### Android Test

Durante lo sviluppo e l\'esecuzione degli Android Test, abbiamo
incontrato numerosi problemi che hanno portato al fallimento di molti
test, evidenziando le difficoltà nel testare efficacemente
l\'interfaccia utente della nostra applicazione.

Come accennato, il nostro codice include meccanismi di sicurezza che
impediscono agli utenti non autenticati di accedere alle funzionalità
principali dell\'applicazione. Questo ha creato un problema
significativo durante i test, poiché senza un\'appropriata simulazione
dell\'autenticazione, l\'interfaccia rimaneva bloccata su schermate di
caricamento. Abbiamo tentato di mockare il ViewModelUtente e
il RepositoryUtente per simulare un utente autenticato, ma i test hanno
continuato a fallire perché l\'interfaccia non si comportava come
previsto, spesso mostrando un\'icona di caricamento infinita.

Un altro problema ricorrente è stato il fallimento dei test a causa dei
timeout. Abbiamo utilizzato waitUntil per attendere che determinate
condizioni dell\'interfaccia fossero soddisfatte (ad esempio, la
scomparsa di un messaggio di caricamento), ma i test spesso superavano
il limite di tempo senza raggiungere lo stato desiderato. Anche con un
timeout esteso di 10 secondi, molti test non riuscivano a completarsi in
tempo, indicando possibili problemi di performance o di gestione degli
stati nel mock degli oggetti. Ecco riportato un esempio di errore (uno
degli ultimi incontrati dopo diverse modifiche):

java.lang.RuntimeException: Failed to instantiate test runner class
androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner

at
androidx.test.ext.junit.runners.AndroidJUnit4.throwInitializationError(AndroidJUnit4.java:129)

at
androidx.test.ext.junit.runners.AndroidJUnit4.loadRunner(AndroidJUnit4.java:121)

at
androidx.test.ext.junit.runners.AndroidJUnit4.loadRunner(AndroidJUnit4.java:82)

at
androidx.test.ext.junit.runners.AndroidJUnit4.\<init\>(AndroidJUnit4.java:56)

\... 14 trimmed

Caused by: java.lang.reflect.InvocationTargetException

at java.lang.reflect.Constructor.newInstance0(Native Method)

at java.lang.reflect.Constructor.newInstance(Constructor.java:343)

at
androidx.test.ext.junit.runners.AndroidJUnit4.loadRunner(AndroidJUnit4.java:112)

\... 17 more

Caused by: org.junit.runners.model.InvalidTestClassError: Invalid test
class
\'com.example.teamsync.caratteristiche.iTuoiProgetti.ui.ITuoiProgettiTest\':

1\. Method testBottoniITuoiProgetti() should be void

at org.junit.runners.ParentRunner.validate(ParentRunner.java:525)

at org.junit.runners.ParentRunner.\<init\>(ParentRunner.java:92)

at
org.junit.runners.BlockJUnit4ClassRunner.\<init\>(BlockJUnit4ClassRunner.java:74)

at
androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner.\<init\>(AndroidJUnit4ClassRunner.java:43)

at
androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner.\<init\>(AndroidJUnit4ClassRunner.java:48)

\... 20 more

## Pulizia del codice

Il codice iniziale adottava un approccio piuttosto basilare, con una
struttura funzionale ma non ottimizzata. Nelle prime fasi di sviluppo,
l\'obiettivo principale era ottenere rapidamente una versione
funzionante del prodotto, trascurando aspetti come la pulizia, la
leggibilità e la modularità del codice.

Con l\'aumentare della complessità del progetto, è diventato evidente
che era necessario migliorare la qualità del codice. Questo ci ha spinto
a rivedere la maggior parte dei file, impegnandoci a rendere il codice
più pulito, leggibile e modulare.

Grazie a questi sforzi, il codice è ora più robusto e sostenibile nel
lungo termine, facilitando l\'implementazione di nuove funzionalità e la
gestione delle modifiche. Avremmo potuto migliorare ulteriormente la
modularità di alcune schermate, rendendo il codice ancora più
manutenibile e leggibile, ma la mancanza di tempo e gli obiettivi di
questo corso ci hanno portato a posticipare ulteriori perfezionamenti.

4 **Flutter App**

La versione in flutter dell'applicazione presenta meno funzionalità
rispetto alla versione in Kotlin. In particolare, abbiamo implementato:

-   Possibilità di registrarsi e loggarsi

-   Possibilità di cambiare la password

-   Possibilità di creare un nuovo progetto

-   Possibilità di entrare in un progetto esistente tramite codice

-   Possibilità di creare e modificare task

-   Possibilità di eliminare o segnare come completate i tasks

-   Possibilità di modificare i dati di un progetto

-   Possibilità di abbandonare un progetto

-   Possibilità di segnare un progetto come completato

4.1 Mockup Flutter App

Abbiamo cercato di mantenere lo stesso design per entrambe le
applicazioni:

![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image92.png){width="2.4103827646544183in"
height="4.781251093613299in"}
![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image93.png){width="2.3629647856517937in"
height="4.781251093613299in"}

![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image94.png){width="2.8622583114610674in"
height="5.810962379702537in"}
![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image95.png){width="2.924265091863517in"
height="5.819674103237095in"}

![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image96.png){width="3.03125in"
height="6.302084426946632in"}
![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image97.png){width="3.1458333333333335in"
height="6.302084426946632in"}

![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image98.png){width="2.9823326771653544in"
height="5.820360892388451in"}
![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image99.png){width="2.8674453193350833in"
height="5.841093613298337in"}
![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image100.png){width="2.7026859142607176in"
height="5.291668853893263in"}
![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image101.png){width="2.692557961504812in"
height="5.272629046369204in"}

4.2 Architettura App

Come l'app sviluppata in Kotlin, l'applicazione in Flutter è stata
sviluppata utilizzando l'architettura MVVM, però in questo caso le View
sono costituite da widget.

4.3 Database

Entrambe le applicazioni sono collegate allo stesso database,
implementato tramite Firebase. Ogni volta che viene creato o modificato
un account, un progetto o un task, il sistema genera un documento
uniforme nel database. Questo approccio garantisce che tutte le azioni
effettuate in una delle due applicazioni siano immediatamente visibili
anche nell\'altra, mantenendo così i dati sincronizzati e coerenti tra
le due applicazioni.

4.4 Documentazione del codice Flutter

La cartella lib , una delle directory principali in un progetto Flutter,
è strutturata così:

![](vertopal_4c6415411bb247c783c72c46fa58464b/media/image102.png){width="4.822916666666667in"
height="6.302084426946632in"}

Per facilità di consultazione riporteremo il codice dei file .dart più
importanti e rilevanti:

repository_progetto.dart

view_model_progetto.dart

home_page_progetti.dart

ToDoRepository.dart

LeMieAttivitaViewModel.dart

LeMieAttivitaUi.dart

Repository_utente.dart

View_model_utente.dart

Login.dart

Registrazione.dart
