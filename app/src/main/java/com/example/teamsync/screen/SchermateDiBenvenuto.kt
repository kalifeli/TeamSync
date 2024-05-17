package com.example.teamsync.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.teamsync.ui.theme.Red70
import com.example.teamsync.ui.theme.White
import com.example.teamsync.util.PaginaDiBenvenuto

@Composable
fun SchermataDiBenvenuto(
    navController : NavHostController
){

}

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
                text = paginaDiBenvenuto.titolo,
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
                text = paginaDiBenvenuto.sottotitolo,
                color = Color.White,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center

            )

        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
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
                Text(text = "Inizia Ora")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Preview_PrimaSchermataBenvenuto(){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PaginaDiBenvenuto(paginaDiBenvenuto = PaginaDiBenvenuto.PrimaPagina)
    }
}
@Preview(showBackground = true)
@Composable
fun Preview_SecondaSchermataBenvenuto(){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PaginaDiBenvenuto(paginaDiBenvenuto = PaginaDiBenvenuto.SecondaPagina)
    }
}
@Preview(showBackground = true)
@Composable
fun Preview_TerzaSchermataBenvenuto(){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PaginaDiBenvenuto(paginaDiBenvenuto = PaginaDiBenvenuto.TerzaPagina)
    }
}
@Preview(showBackground = true)
@Composable
fun Preview_QuartaSchermataBenvenuto(){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PaginaDiBenvenuto(paginaDiBenvenuto = PaginaDiBenvenuto.QuartaPagina)
    }
}
@Preview(showBackground = true)
@Composable
fun Preview_QuintaSchermataBenvenuto(){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PaginaDiBenvenuto(paginaDiBenvenuto = PaginaDiBenvenuto.QuintaPagina)
    }
}
@Preview(showBackground = true)
@Composable
fun Preview_SestaSchermataBenvenuto(){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PaginaDiBenvenuto(paginaDiBenvenuto = PaginaDiBenvenuto.SestaPagina)
    }
}





