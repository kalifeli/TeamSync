package com.example.teamsync.caratteristiche.iTuoiProgetti.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.autentificazione.data.repository.RepositoryUtente
import com.example.teamsync.caratteristiche.autentificazione.data.viewModel.ViewModelUtente
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.util.theme.Grey50
import com.example.teamsync.util.theme.Red70
import com.example.teamsync.util.theme.White

/**
 * Composable che mostra una sezione del profilo utente con nome e immagine.
 * Include una scheda elevata (ElevatedCard) che, se cliccata, naviga alla schermata del profilo utente.
 *
 * @param navController Controller di navigazione per gestire la navigazione all'interno dell'app.
 * @param viewModelUtente ViewModel che gestisce i dati del profilo utente.
 * @param isDarkTheme Indica se l'app è in modalità tema scuro.
 */
@Composable
fun SezioneProfiloUtente(
    navController: NavController,
    viewModelUtente: ViewModelUtente,
    isDarkTheme: Boolean
){
    val userProfile by viewModelUtente.userProfilo.observeAsState()
    val isLoading by viewModelUtente.isLoading.observeAsState(false)

    ElevatedCard(
        onClick = {
            navController.navigate(Schermate.Profilo.route)
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, if (isDarkTheme) White else White, shape = RoundedCornerShape(16.dp))
            .height(150.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if(isDarkTheme) Color.Black else Red70
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if(isLoading || userProfile == null){
                Column(
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.CenterHorizontally),
                        color = Grey50,
                        trackColor = Red70,
                        strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap
                    )
                    Text(
                        text = stringResource(id = R.string.preparazioneAccount),
                        color = if (isDarkTheme) Color.White else Color.Black,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            } else {
                Column(
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Text(
                        text = stringResource(id = R.string.ciao),
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
                    )
                    Text(
                        text = "${userProfile!!.nome}!",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
                    )
                }

                ImmagineProfiloUtente(
                    imageUrl = userProfile!!.immagine,
                    defaultImage = R.drawable.logo_rotondo,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(90.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.White, CircleShape)
                )
            }
        }
    }
}

/**
 * Composable che visualizza l'immagine del profilo utente.
 * Se non è presente un'immagine, viene mostrata un'immagine di default.
 *
 * @param imageUrl URL dell'immagine del profilo utente.
 * @param defaultImage Risorsa dell'immagine di default da visualizzare se l'immagine del profilo non è disponibile.
 * @param modifier Modificatore per configurare l'aspetto e il comportamento di questo composable.
 */
@Composable
fun ImmagineProfiloUtente(imageUrl: String?, defaultImage: Int, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val painter = if (imageUrl.isNullOrEmpty()) {
        painterResource(id = defaultImage)
    } else {
        rememberAsyncImagePainter(
            ImageRequest.Builder(context)
                .data(imageUrl)
                .apply {
                    placeholder(defaultImage)
                    crossfade(true)
                }
                .build()
        )
    }

    Image(
        painter = painter,
        contentDescription = "Immagine Profilo",
        modifier = modifier
            .size(64.dp)
            .background(Color.White, CircleShape)
        ,
        contentScale = ContentScale.Crop
    )
}

/**
 * Anteprima della funzione [ImmagineProfiloUtente] con valori predefiniti.
 */
@Preview(showSystemUi = true)
@Composable
fun PreviewImmagineProfiloUtente(){
    //ImmagineProfiloUtente()
}

/**
 * Anteprima della funzione [SezioneProfiloUtente] con valori predefiniti.
 */
@Preview(showSystemUi = true)
@Composable
fun PreviewSezioneProfiloUtente(){
    SezioneProfiloUtente(navController = rememberNavController(), viewModelUtente = ViewModelUtente(
        RepositoryUtente(LocalContext.current), LocalContext.current
    ), isDarkTheme = true)
}
