@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.teamsync.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teamsync.R
import com.example.teamsync.ui.theme.Grey20
import com.example.teamsync.ui.theme.Red70
import com.example.teamsync.ui.theme.Grey70
import com.example.teamsync.ui.theme.White



@ExperimentalMaterial3Api
@Composable
fun Progetti() {

    Box(
        modifier = Modifier
            .background(Grey20)
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(top = 50.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "I Tuoi Progetti",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                color = Color.Black,
                fontFamily = FontFamily.Monospace
            )
            Text(
                text = "Scegli il progetto che vuoi aprire",
                color = Grey70,
                modifier = Modifier.padding(top = 10.dp)
            )
            Box(
                modifier = Modifier
                    .size(300.dp)

                    .offset(y = (-130).dp)// Riduci il margine inferiore per spostare l'immagine più in alto
            ) {
                Image(
                    painterResource(id = R.drawable.linea),
                    contentDescription = "Linea Rossa",
                    modifier = Modifier.fillMaxSize()
                )
            }
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .offset(y = (-150).dp) // Riduci il margine inferiore per spostare l'immagine più in alto
            ) {
                Image(
                    painterResource(id = R.drawable.no_project_illustration),
                    contentDescription = "Immagini di Progetti Vuota",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Text(
                textAlign = TextAlign.Center,
                text = "Oops!!! Sembra che non ci siano progetti,inizia ora creandone uno.",
                color = Grey70,
                modifier = Modifier
                    .padding(16.dp)
                    .padding(horizontal = 20.dp)
                    .offset(y = (-140).dp)
            )

            val sheetState = rememberModalBottomSheetState()
            var isSheetOpen by rememberSaveable {
                mutableStateOf(false)
            }
            var filledText by remember{
                mutableStateOf("")
            }

            FloatingActionButton(
                containerColor = Red70,
                shape = FloatingActionButtonDefaults.shape,
                onClick = { /*TODO*/ }
            ) {
               Icon(
                   imageVector = Icons.Default.Add,
                   contentDescription = "add project",
                   tint = Color.White
               )
            }
            if (isSheetOpen) {
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = { isSheetOpen = false }
                )
                {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(White)
                            .padding(35.dp)
                    )
                    {
                        Column(modifier = Modifier.align(Alignment.TopCenter)) {
                            Image(
                                painterResource(id = R.drawable.aggiungiicona),
                                contentDescription = "Aggiungi icona",
                                modifier = Modifier
                                    .clickable { /*TODO*/ }
                                    .size(120.dp)
                                    .padding(16.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                            TextField(value = filledText,
                                onValueChange = {filledText = it},
                                label = {
                                    Text(text = "Nome Progetto")
                                },
                                modifier = Modifier
                                    .padding(16.dp)
                                    .align(Alignment.CenterHorizontally)
                               )
                            Button(onClick = { /*TODO*/ },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(Color(193, 9, 42)),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(text = "Crea Progetto")
                            }
                            
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewProgetti(){
    Progetti()
}