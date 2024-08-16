package com.example.teamsync.caratteristiche.benvenuto.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.benvenuto.data.PaginaDiBenvenuto
import com.example.teamsync.caratteristiche.autentificazione.data.repository.RepositoryUtente
import com.example.teamsync.caratteristiche.autentificazione.data.viewModel.ViewModelUtente
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.util.theme.Grey50
import com.example.teamsync.util.theme.Red70
import com.example.teamsync.util.theme.White

/**
 * Schermata di benvenuto che presenta una serie di pagine scorrevoli che introducono l'applicazione.
 *
 * @param navController Controller per la navigazione tra le schermate.
 * @param viewModelUtente ViewModel per la gestione delle operazioni di autenticazione utente.
 */
@Composable
fun SchermataDiBenvenuto(
    navController : NavController,
    viewModelUtente: ViewModelUtente
){
    val pages = listOf(
        PaginaDiBenvenuto.PrimaPagina,
        PaginaDiBenvenuto.SecondaPagina,
        PaginaDiBenvenuto.TerzaPagina,
        PaginaDiBenvenuto.QuartaPagina,
        PaginaDiBenvenuto.QuintaPagina,
        PaginaDiBenvenuto.SestaPagina
    )
    val pagerState = rememberPagerState(0,0F) {
        6
    }

    Box (modifier = Modifier.fillMaxSize()){

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Top
        ) {posizione ->
            PaginaDiBenvenuto(paginaDiBenvenuto = pages[posizione])
        }
        Row (
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ){
            repeat(pages.size){iterazione ->
                val colore = if (pagerState.currentPage == iterazione) Red70 else Grey50
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(colore)
                        .size(12.dp)
                )

            }
        }

        Bottone_IniziaOra(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = {
                viewModelUtente.updateFirstLogin()
                navController.navigate(Schermate.ItuoiProgetti.route) {
                    popUpTo(Schermate.Benvenuto.route) { inclusive = true }
                }

            }
        )

    }

}

/**
 * Composable che rappresenta una singola pagina nella schermata di benvenuto.
 *
 * @param paginaDiBenvenuto Oggetto che rappresenta il contenuto della pagina di benvenuto, inclusi immagine, titolo e sottotitolo.
 */
@Composable
fun PaginaDiBenvenuto (paginaDiBenvenuto: PaginaDiBenvenuto){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Image(
            painter = painterResource(id = paginaDiBenvenuto.sfondo),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize(),
            contentDescription = "sfondo"
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = paginaDiBenvenuto.immagine),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentDescription = "Immagine della pagina di benvenuto",
                contentScale = ContentScale.Fit
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                text = stringResource(id = paginaDiBenvenuto.titolo.toInt()),
                color = Color.White,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .padding(vertical = 10.dp),
                text = stringResource(id = paginaDiBenvenuto.sottotitolo.toInt()),
                color = Color.White,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )

        }
    }
}

/**
 * Composable che rappresenta il bottone "Inizia Ora" che appare nell'ultima pagina della schermata di benvenuto.
 *
 * @param pagerState Stato del pager che gestisce le pagine della schermata di benvenuto.
 * @param modifier Modificatore per personalizzare il comportamento dall'esterno.
 * @param onClick Funzione di callback eseguita al click del bottone.
 */
@Composable
fun Bottone_IniziaOra(
    pagerState: PagerState,
    modifier: Modifier, // personalizzare il comportamento dall'esterno
    onClick: () -> Unit // funzione di callback al click del bottone
){
    Row(
        modifier = modifier
            .padding(horizontal = 40.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            // il bottone sarà visibile solo al raggiungimento dell'ultima schermata.
            // Nota: il conteggio delle pagine inizia dallo 0
            visible = pagerState.currentPage == 5,
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Red70,
                    contentColor = White
                ),
                onClick = onClick,
            ) {
                Text(text = stringResource(id = R.string.benvenutotitolo6))
            }
        }
    }
}
@Preview
@Composable
fun PreviewSchermataDiBenvenuto() {
    val navController = rememberNavController()
    SchermataDiBenvenuto(navController, viewModelUtente = ViewModelUtente(RepositoryUtente()))
}

@Preview
@Composable
fun Preview_PrimaSchermataBenvenuto(){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PaginaDiBenvenuto(paginaDiBenvenuto = PaginaDiBenvenuto.PrimaPagina)
    }
}

@Preview
@Composable
fun Preview_SecondaSchermataBenvenuto(){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PaginaDiBenvenuto(paginaDiBenvenuto = PaginaDiBenvenuto.SecondaPagina)
    }
}

@Preview
@Composable
fun Preview_TerzaSchermataBenvenuto(){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PaginaDiBenvenuto(paginaDiBenvenuto = PaginaDiBenvenuto.TerzaPagina)
    }
}

@Preview
@Composable
fun Preview_QuartaSchermataBenvenuto(){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PaginaDiBenvenuto(paginaDiBenvenuto = PaginaDiBenvenuto.QuartaPagina)
    }
}

@Preview
@Composable
fun Preview_QuintaSchermataBenvenuto(){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PaginaDiBenvenuto(paginaDiBenvenuto = PaginaDiBenvenuto.QuintaPagina)
    }
}

@Preview
@Composable
fun Preview_SestaSchermataBenvenuto(){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PaginaDiBenvenuto(paginaDiBenvenuto = PaginaDiBenvenuto.SestaPagina)
    }
}





