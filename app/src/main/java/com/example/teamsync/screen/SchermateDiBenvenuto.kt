package com.example.teamsync.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.teamsync.R
import com.example.teamsync.util.PaginaDiBenvenuto

@Composable
fun PaginaDiBenvenuto (paginaDiBenvenuto: PaginaDiBenvenuto){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.sfondo_pagina_di_benvenuto1),
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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp),
                painter = painterResource(id = paginaDiBenvenuto.immagine) ,
                contentDescription = "Immagine della pagina di benvenuto",
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 30.dp),
                text = paginaDiBenvenuto.titolo,
                color = Color.White,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .padding(top = 20.dp),
                text = paginaDiBenvenuto.sottotitolo,
                color = Color.White,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center

            )

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
        com.example.teamsync.screen.PaginaDiBenvenuto(paginaDiBenvenuto = PaginaDiBenvenuto.PrimaPagina)
    }

}




