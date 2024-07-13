package com.example.teamsync.caratteristiche.faq.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.terms.data.model.Faq
import com.example.teamsync.caratteristiche.terms.data.repository.RepositoryFaq
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.util.ThemePreferences



@Composable
fun SingolaFaq(faqItem: Faq) {
    var expanded by remember { mutableStateOf(false) }
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
    )
    {


    if(!isDarkTheme)
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp)

                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { expanded = !expanded }
                    )
                }
        ) {
            Text(
                text = faqItem.domanda,
                textAlign = TextAlign.Start,

                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (expanded) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { expanded = !expanded }
                        )
                    }
            ) {
                Text(
                    text = faqItem.risposta,
                    textAlign = TextAlign.Start,

                )

            }
            Spacer(modifier = Modifier.height(10.dp))
        }

    }
    else
    {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.DarkGray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp)

                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { expanded = !expanded }
                        )
                    }
            ) {
                Text(
                    text = faqItem.domanda,
                    textAlign = TextAlign.Start,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (expanded) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = { expanded = !expanded }
                            )
                        }
                ) {
                    Text(
                        text = faqItem.risposta,
                        textAlign = TextAlign.Start,

                        )

                }
                Spacer(modifier = Modifier.height(10.dp))
            }

        }
    }

    }


@Composable
fun recupera_sezione_faq(sezione: String) : String {

    if (sezione == "Introduction" || sezione == "Introduzione")
        return "Introduzione"

    if (sezione == "Features" || sezione == "Funzionalità")
        return "Funzionalità"

    if (sezione == "Sicurity" || sezione == "Sicurezza")
        return "Sicurezza"

    if (sezione == "Advanced" || sezione == "Avanzate")
        return "Avanzate"

    if (sezione == "Other" || sezione == "Altro")
        return "Altro"

    if (sezione == "Privacy")
    return "Privacy"
    
    return "0"

}






@Composable
fun Faq(navController: NavHostController, sezioneFaq: String) {


    val repository = RepositoryFaq()
    val listaFaq = remember { mutableStateListOf<Faq>() }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    val isDarkTheme = ThemePreferences.getTheme(LocalContext.current)

    val background: Painter = painterResource(id = R.drawable.background_grey)



    LaunchedEffect(Unit) {
        try {
            val faq = repository.getAllFaq()
            if (faq.isEmpty()) {
                error = "Nessuna Faq trovata."
            } else {
                listaFaq.addAll(faq)

            }
        } catch (e: Exception) {
            error = "Errore di connessione: ${e.message}"
        } finally {
            loading = false
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        if(!isDarkTheme)
        {
            Image(
                painter = background,
                contentDescription = "Background Image",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillBounds // Scala l'immagine per riempire lo spazio

            )
        }

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .background(if (isDarkTheme) Color.Black else Color.Transparent),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.08f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.weight(0.25f))
                if(!isDarkTheme) {
                    Box(
                        modifier = Modifier
                            .size(35.dp)
                            .clickable { navController.navigate(Schermate.Supporto.route) }
                            .background(
                                Color.Black,
                                RoundedCornerShape(20.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back",
                            tint = Color.White // Assicurati che l'icona sia visibile impostando il colore a bianco
                        )
                    }
                }
                else
                {
                    Box(
                        modifier = Modifier
                            .size(35.dp)
                            .clickable { navController.navigate(Schermate.Supporto.route) }
                            .background(
                                Color.White,
                                RoundedCornerShape(20.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back",
                            tint = Color.Black // Assicurati che l'icona sia visibile impostando il colore a bianco
                        )
                    }
                }


                // Centra il testo all'interno della Row
                Row(
                    modifier = Modifier.weight(8f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if(isDarkTheme)
                    {
                        Text(
                            text = sezioneFaq,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    else
                    {
                        Text(
                            text = sezioneFaq,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                }

                // Row vuota per bilanciare il layout
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {}

            }
            if (loading) {
                Text(stringResource(id = R.string.caricamento), fontSize = 16.sp, color = Color.Gray)
            } else if (error != null) {
                Text("Errore: $error", fontSize = 16.sp, color = Color.Red)
            } else {
                Column(modifier = Modifier.fillMaxSize()) {
                    LazyColumn() {
                        items(listaFaq) { term ->
                            if (term.sezione == recupera_sezione_faq(sezione = sezioneFaq))
                                SingolaFaq(term)
                        }
                    }
                }
            }

        }




        }
    }







@Preview
@Composable
fun ProvaFaq() {
    Faq(navController = rememberNavController(), "ciao")
}
