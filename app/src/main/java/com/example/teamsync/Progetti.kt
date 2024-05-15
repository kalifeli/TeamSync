@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.teamsync

import android.transition.CircularPropagation
import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teamsync.ui.theme.Grey
import com.example.teamsync.ui.theme.Grey_Forte
import com.example.teamsync.ui.theme.Main
import com.example.teamsync.ui.theme.SottotitoliGrey
import com.example.teamsync.ui.theme.White


@ExperimentalMaterial3Api
@Composable
fun Progetti() {


    Box(
        modifier = Modifier
            .background(Grey)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .offset(y = -40.dp)
                .align(Alignment.Center)
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "I Tuoi Progetti",
                fontSize = 24.sp,
                color = Color.Black,
                fontFamily = FontFamily.Monospace
            )
            Text(
                text = "Scegli il progetto che vuoi aprire",
                color = SottotitoliGrey,
                modifier = Modifier.padding(top = 10.dp)
            )
            Box(
                modifier = Modifier
                    .size(300.dp)

                    .offset(y = -130.dp)// Riduci il margine inferiore per spostare l'immagine più in alto
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
                    .offset(y = -150.dp) // Riduci il margine inferiore per spostare l'immagine più in alto
            ) {
                Image(
                    painterResource(id = R.drawable.pana),
                    contentDescription = "Immagini di Progetti Vuota",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Text(
                text = "Oops!!! Sembra che non ci siano progetti,\n inizia ora creandone uno",
                color = SottotitoliGrey,
                modifier = Modifier.offset(y = -140.dp)
            )

            val sheetState = rememberModalBottomSheetState()
            var isSheetOpen by rememberSaveable {
                mutableStateOf(false)
            }

            IconButton(
                onClick = { isSheetOpen = true }, modifier = Modifier
                    .offset(x = 120.dp, y = 40.dp)
            )
            {
                Icon(
                    Icons.Default.AddCircle,
                    contentDescription = null,
                    tint = Main,
                    modifier = Modifier.size(40.dp)
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
                                    .size(70.dp)
                            )
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