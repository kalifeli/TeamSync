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
        titolo = R.string.benvenuto.toString(),
        sottotitolo = R.string.benvenutosottotitolo.toString(),
        sfondo = R.drawable.sfondo_pagina_di_benvenuto1
    )
    object SecondaPagina : PaginaDiBenvenuto (
        immagine = R.drawable.im_pagina_di_benvenuto2,
        titolo = R.string.benvenutotitolo2.toString(),
        sottotitolo = R.string.benvenutosottotitolo2.toString(),
        sfondo = R.drawable.sfondo_pagina_di_benvenuto2
    )

    object TerzaPagina : PaginaDiBenvenuto (
        immagine = R.drawable.im_pagina_di_benvenuto3,
        titolo = R.string.benvenutotitolo3.toString(),
        sottotitolo = R.string.benvenutosottotitolo3.toString(),
        sfondo = R.drawable.sfondo_pagina_di_benvenuto3
    )

    object QuartaPagina : PaginaDiBenvenuto (
        immagine = R.drawable.im_pagina_di_benvenuto4,
        titolo = R.string.benvenutotitolo4.toString(),
        sottotitolo = R.string.benvenutosottotitolo4.toString(),
        sfondo = R.drawable.sfondo_pagina_di_benvenuto2
    )
    object QuintaPagina : PaginaDiBenvenuto (
        immagine = R.drawable.im_pagina_di_benvenuto5,
        titolo = R.string.benvenutotitolo5.toString(),
        sottotitolo = R.string.benvenutosottotitolo5.toString(),
        sfondo = R.drawable.sfondo_pagina_di_benvenuto1
    )
    object SestaPagina : PaginaDiBenvenuto (
        immagine = R.drawable.im_pagina_di_benvenuto6,
        titolo = R.string.benvenutotitolo6.toString(),
        sottotitolo = R.string.benvenutosottotitolo6.toString(),
        sfondo = R.drawable.sfondo_pagina_di_benvenuto6
    )
}