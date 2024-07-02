package com.example.teamsync.util

import androidx.annotation.DrawableRes
import com.example.teamsync.R

sealed class PaginaDiBenvenuto (
    @DrawableRes
    val immagine: Int,
    val titolo: String,
    val sottotitolo: String,
    @DrawableRes
    val sfondo: Int
){
    // ogni object rappresenta le proprietà di una pagina nella nostra presentazione
    object PrimaPagina : PaginaDiBenvenuto (
        immagine = R.drawable.im_pagina_di_benvenuto1,
        titolo = "Benvenuto in TeamSync",
        sottotitolo = "Il modo più semplice e veloce per organizzare i tuoi progetti universitari.",
        sfondo = R.drawable.sfondo_pagina_di_benvenuto1
    )
    object SecondaPagina : PaginaDiBenvenuto (
        immagine = R.drawable.im_pagina_di_benvenuto2,
        titolo = "Tieni traccia di tutti i tuoi progetti",
        sottotitolo = "",
        sfondo = R.drawable.sfondo_pagina_di_benvenuto2
    )

    object TerzaPagina : PaginaDiBenvenuto (
        immagine = R.drawable.im_pagina_di_benvenuto3,
        titolo = "Gestisci i task",
        sottotitolo = "Crea,assegna e tieni traccia dei tuoi compiti efficacemente, tutto da un'unica schermata intuitiva.",
        sfondo = R.drawable.sfondo_pagina_di_benvenuto3
    )

    object QuartaPagina : PaginaDiBenvenuto (
        immagine = R.drawable.im_pagina_di_benvenuto4,
        titolo = "Resta sempre aggiornato",
        sottotitolo = "Attiva le notifiche di gruppo per non perdere mai un aggiornamento importante o una scadenza imminente nei tuoi progetti di gruppo.",
        sfondo = R.drawable.sfondo_pagina_di_benvenuto2
    )
    object QuintaPagina : PaginaDiBenvenuto (
        immagine = R.drawable.im_pagina_di_benvenuto5,
        titolo = "Aggiungi studenti ai tuoi amici",
        sottotitolo = "Costruisci connessioni con nuovi compagni di corso.",
        sfondo = R.drawable.sfondo_pagina_di_benvenuto1
    )
    object SestaPagina : PaginaDiBenvenuto (
        immagine = R.drawable.im_pagina_di_benvenuto6,
        titolo = "Inizia ora!",
        sottotitolo = "Sei solo a un passo dall'essere più organizzato e connesso che mai. Entra ora e trasforma il modo in cui lavori sui tuoi progetti!",
        sfondo = R.drawable.sfondo_pagina_di_benvenuto6
    )
}