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
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente
import com.example.teamsync.ui.theme.Red70

@Composable
fun SezioneProfiloUtente(
    navController: NavController,
    viewModelUtente: ViewModelUtente,
){
    val userProfile = viewModelUtente.userProfile
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Red70
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Text(
                    text = "Ciao,",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Normal,
                )
                Text(
                    text = "Alessandro" + "!",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Normal,
                )

            }

                // Usa la funzione per mostrare l'immagine del profilo utente
                ImmagineProfiloUtente(
                    imageUrl = userProfile?.immagine,
                    defaultImage = R.drawable.logo_rotondo,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(90.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                )



        }
    }
}

/*
@Composable
fun ImmagineProfiloUtente(imageResource: Int, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = imageResource),
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}
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
            .padding(7.dp),
        contentScale = ContentScale.Crop
    )
}

@Preview(showSystemUi = true)
@Composable
fun PreviewImmagineProfiloUtente(){
    //ImmagineProfiloUtente()

}

@Preview(showSystemUi = true)
@Composable
fun PreviewSezioneProfiloUtente(){
    SezioneProfiloUtente(navController = rememberNavController(), viewModelUtente = ViewModelUtente())
}
