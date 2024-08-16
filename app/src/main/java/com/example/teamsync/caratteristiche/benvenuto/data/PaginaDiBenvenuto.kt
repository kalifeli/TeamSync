package com.example.teamsync.caratteristiche.benvenuto.data

import androidx.annotation.DrawableRes
import com.example.teamsync.R

/**
 * Rappresenta una pagina della schermata di benvenuto.
 * Ogni pagina contiene un'immagine, un titolo, un sottotitolo e uno sfondo.
 *
 * @property immagine L'ID della risorsa drawable per l'immagine principale della pagina.
 * @property titolo La stringa rappresentante il titolo della pagina.
 * @property sottotitolo La stringa rappresentante il sottotitolo della pagina.
 * @property sfondo L'ID della risorsa drawable per lo sfondo della pagina.
 */
sealed class PaginaDiBenvenuto (
    @DrawableRes
    val immagine: Int,
    val titolo: String,
    val sottotitolo: String,
    @DrawableRes
    val sfondo: Int
){
    /**
     * La prima pagina della schermata di benvenuto.
     */
    object PrimaPagina : PaginaDiBenvenuto(
        immagine = R.drawable.im_pagina_di_benvenuto1,
        titolo = R.string.benvenuto.toString(),
        sottotitolo = R.string.benvenutosottotitolo.toString(),
        sfondo = R.drawable.sfondo_pagina_di_benvenuto1
    )

    /**
     * La seconda pagina della schermata di benvenuto.
     */
    object SecondaPagina : PaginaDiBenvenuto(
        immagine = R.drawable.im_pagina_di_benvenuto2,
        titolo = R.string.benvenutotitolo2.toString(),
        sottotitolo = R.string.benvenutosottotitolo2.toString(),
        sfondo = R.drawable.sfondo_pagina_di_benvenuto2
    )

    /**
     * La terza pagina della schermata di benvenuto.
     */
    object TerzaPagina : PaginaDiBenvenuto(
        immagine = R.drawable.im_pagina_di_benvenuto3,
        titolo = R.string.benvenutotitolo3.toString(),
        sottotitolo = R.string.benvenutosottotitolo3.toString(),
        sfondo = R.drawable.sfondo_pagina_di_benvenuto3
    )

    /**
     * La quarta pagina della schermata di benvenuto.
     */
    object QuartaPagina : PaginaDiBenvenuto(
        immagine = R.drawable.im_pagina_di_benvenuto4,
        titolo = R.string.benvenutotitolo4.toString(),
        sottotitolo = R.string.benvenutosottotitolo4.toString(),
        sfondo = R.drawable.sfondo_pagina_di_benvenuto2
    )

    /**
     * La quinta pagina della schermata di benvenuto.
     */
    object QuintaPagina : PaginaDiBenvenuto(
        immagine = R.drawable.im_pagina_di_benvenuto5,
        titolo = R.string.benvenutotitolo5.toString(),
        sottotitolo = R.string.benvenutosottotitolo5.toString(),
        sfondo = R.drawable.sfondo_pagina_di_benvenuto1
    )

    /**
     * La sesta pagina della schermata di benvenuto.
     */
    object SestaPagina : PaginaDiBenvenuto(
        immagine = R.drawable.im_pagina_di_benvenuto6,
        titolo = R.string.benvenutotitolo6.toString(),
        sottotitolo = R.string.benvenutosottotitolo6.toString(),
        sfondo = R.drawable.sfondo_pagina_di_benvenuto6
    )
}