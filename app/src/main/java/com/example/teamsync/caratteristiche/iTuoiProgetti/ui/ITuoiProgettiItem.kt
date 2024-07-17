package com.example.teamsync.caratteristiche.iTuoiProgetti.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.model.Progetto
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
import com.example.teamsync.ui.theme.Grey35
import com.example.teamsync.ui.theme.Red70
import com.example.teamsync.ui.theme.White
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun ITuoiProgettiItem(
    progetto: Progetto,
    navController: NavController,
    viewModelProgetto: ViewModelProgetto,
    attivitaNonCompletate: Int,
    isDarkTheme: Boolean
){
    val dataScadenza = remember { progetto.dataScadenza }
    val formattatoreData = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    val dataScadenzaSdf = formattatoreData.format(dataScadenza)
    val dataConsegna = remember {progetto.dataConsegna}
    val dataConsegnaSdf = formattatoreData.format(dataConsegna)

    ElevatedCard(
        onClick = {
                navController.navigate("progetto/${progetto.id}")
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),
        modifier = Modifier
            .size(width = 220.dp, height = 140.dp)
            .border(1.dp, if(isDarkTheme) White else White,shape = RoundedCornerShape(16.dp)),
        colors = CardDefaults.outlinedCardColors(
            containerColor = if(isDarkTheme) Color.Black else White
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                if(progetto.completato){
                    Icon(painter = painterResource(id = R.drawable.ic_progettocompletato), contentDescription = "progetto completato", tint = if(isDarkTheme) White else Color.Black)
                }

                Text(
                    text = progetto.nome,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = if(isDarkTheme) White else Color.Black
                )
                if(!progetto.completato) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(progetto.priorita.colore)
                    )
                }
            }
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = if(isDarkTheme) White else Grey35
            )

            if(progetto.completato) {
                Row(
                    modifier = Modifier.align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_votoprogetto),
                        contentDescription = "voto progetto",
                        modifier = Modifier.size(16.dp),
                        tint = if(isDarkTheme) White else Color.Black
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = progetto.voto,
                        textAlign = TextAlign.End,
                        fontSize = 12.sp,
                        color = if(isDarkTheme) White else Color.Black
                    )
                }

            } else {
                Row(
                    modifier = Modifier.align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_task_da_completare),
                        contentDescription = "attività da completare",
                        modifier = Modifier.size(16.dp),
                        tint = if(isDarkTheme) White else Color.Black
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$attivitaNonCompletate attività",
                        textAlign = TextAlign.End,
                        fontSize = 12.sp,
                        color = if(isDarkTheme) White else Color.Black
                    )
                }
            }

            if(progetto.completato){
                Row(
                    modifier = Modifier.align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_progettoconsegnato),
                        contentDescription = "data consegna progetto",
                        modifier = Modifier
                            .size(16.dp),
                        tint = if (viewModelProgetto.progettoScaduto(progetto)) Red70 else if (isDarkTheme) White else Color.Black
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = dataConsegnaSdf,
                        textAlign = TextAlign.End,
                        fontSize = 12.sp,
                        color = if(isDarkTheme) White else Color.Black
                    )
                }

            }else {
                Row(
                    modifier = Modifier.align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val progettoScaduto = viewModelProgetto.progettoScaduto(progetto)
                    val iconaCalendario =
                        if (!progettoScaduto) R.drawable.ic_calendario_evento else R.drawable.ic_progettoscaduto
                    Icon(
                        painter = painterResource(iconaCalendario),
                        contentDescription = "data scadenza",
                        modifier = Modifier.size(16.dp),
                        tint = if (viewModelProgetto.progettoScaduto(progetto)) Red70 else if (isDarkTheme) White else Color.Black

                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = dataScadenzaSdf,
                        textAlign = TextAlign.End,
                        fontSize = 12.sp,
                        color = if (viewModelProgetto.progettoScaduto(progetto)) Red70 else if (isDarkTheme) White else Color.Black
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewITuoiProgettiItem(){
    ITuoiProgettiItem( navController = rememberNavController(),progetto = Progetto(nome = "TeamSync"), viewModelProgetto = ViewModelProgetto(), attivitaNonCompletate = 3 , isDarkTheme = false)
}
