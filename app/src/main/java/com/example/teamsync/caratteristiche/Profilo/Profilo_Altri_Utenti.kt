package com.example.teamsync.caratteristiche.Profilo
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.Notifiche.data.repository.RepositoryNotifiche
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel.ViewModelProgetto
import com.example.teamsync.caratteristiche.login.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.login.data.viewModel.ViewModelUtente
import com.example.teamsync.navigation.Schermate


@Composable
fun ProfiloUtenteCliccato(viewModel: ViewModelUtente, navController: NavHostController, id: String, amicizia: String, provenienza: String, notificheRepo : RepositoryNotifiche, task: String, pro : String, viewModelprogetto: ViewModelProgetto ) {

    Log.d("Id quando sono dentro", "Dati: $id")
    viewModel.getUserProfile()
    val userProfile = viewModel.userProfile

    var user_amico by remember { mutableStateOf<ProfiloUtente?>(null) }
    var nome by remember { mutableStateOf("") }
    var cognome by remember { mutableStateOf("") }
    var matricola by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var avvia_notifica by remember { mutableStateOf(false) }
    var accetta_amicizia by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }



    LaunchedEffect(id) {
        viewModel.ottieni_utente(id) { profile ->
            user_amico = profile
            nome = profile?.nome ?: ""
            cognome = profile?.cognome ?: ""
            matricola = profile?.matricola ?: ""
            email = profile?.email ?: ""
        }
    }

    LaunchedEffect(avvia_notifica) {
        if(avvia_notifica)
        { viewModel.ottieni_utente(id) { profile ->
            val contenuto = (userProfile?.nome ?: "") + " " + (userProfile?.cognome ?: "") + " " + "ti ha inviato una richiesta di amicizia"

            if (userProfile != null) {
                notificheRepo.creaNotifica(
                    userProfile.id,
                    profile?.id ?: "",
                    "Richiesta_Amicizia",
                    contenuto,
                    ""


                )
            }
        }
        }
    }
    LaunchedEffect(accetta_amicizia) {

        if(accetta_amicizia)
        { viewModel.ottieni_utente(id) { profile ->
            val contenuto = (userProfile?.nome ?: "") + " " + (userProfile?.cognome ?: "") + " " + "ha accettato la tua richiesta di amicizia"

            if (userProfile != null) {
                notificheRepo.creaNotifica(
                    userProfile.id,
                    profile?.id ?: "",
                    "Accetta_Amicizia",
                    contenuto,
                    "",

                    )
            }
        }
        }
    }

    Spacer(modifier = Modifier.height(8.dp))





    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFEF5350), RoundedCornerShape(16.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
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

                    Box(
                        modifier = Modifier
                            .size(35.dp)
                            .background(
                                Color.Black,
                                RoundedCornerShape(20.dp)
                            ) // Imposta il rettangolo di sfondo a nero
                            .clickable {
                                if (provenienza == "task")
                                    navController.navigate("task_selezionata/${task}/${pro}}")
                                else
                                    navController.navigate(Schermate.Profilo.route)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "close_impostazioni",
                            tint = Color.White // Assicurati che l'icona sia visibile impostando il colore a bianco
                        )
                    }

                    // Centra il testo all'interno della Row
                    Row(
                        modifier = Modifier.weight(9f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                    }



                }
            }

            if (user_amico?.immagine != null) {

                Image(
                    painter = // Gestisci l'indicatore di caricamento qui
                    rememberAsyncImagePainter(ImageRequest.Builder // Placeholder di caricamento
                    // Effetto crossfade durante il caricamento
                        (LocalContext.current).data(user_amico!!.immagine).apply(block = fun ImageRequest.Builder.() {
                        // Gestisci l'indicatore di caricamento qui
                        placeholder(R.drawable.user_icon) // Placeholder di caricamento
                        crossfade(true) // Effetto crossfade durante il caricamento
                    }).build()
                    ),

                    contentDescription = "Immagine Profilo",
                    modifier = Modifier

                        .size(64.dp)
                        .background(Color.White, CircleShape)
                        .padding(16.dp),

                    contentScale = ContentScale.Crop,


                    )

            }

            else {

                Image(
                    painter = painterResource(id = R.drawable.user_icon),
                    contentDescription = "Icona Applicazione",
                    modifier = Modifier
                        .size(64.dp)
                        .background(Color.White, CircleShape)
                        .padding(16.dp),
                )
            }

            Text(
                text = nome + " " +  cognome,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = email,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = matricola,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(top = 8.dp)
            )


            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                StatBoxUtente(number = "13", label = "Task Completate")
                StatBoxUtente(number = "2", label = "Progetti Completati")
            }
            Spacer(modifier = Modifier.height(20.dp))



        }

        Spacer(modifier = Modifier.height(40.dp))
        if(amicizia == "false" )
        {
            if(provenienza == "profilo")
            {
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(horizontal = 16.dp),
                    onClick =
                    {
                        avvia_notifica = true

                        navController.navigate(Schermate.Profilo.route)
                    },
                    shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue, // Cambia il colore di sfondo del pulsante
                        contentColor = Color.White
                    ))
                {
                    Text(
                        text = "Richiedi Amicizia",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }
            if(provenienza == "notifica")
            {
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(horizontal = 16.dp),
                    onClick =
                    {
                        userProfile?.let { profile ->
                            viewModel.fai_amicizia(profile.id, id) {
                                viewModel.getUserProfile()
                                accetta_amicizia = true
                                navController.navigate(Schermate.Profilo.route)
                            }
                        }
                    },
                    shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue, // Cambia il colore di sfondo del pulsante
                        contentColor = Color.White
                    ))
                {
                    Text(
                        text = "Accetta amicizia",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }

        }
        if(amicizia == "true")
        {
            Button(modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(horizontal = 16.dp),

                onClick = {
                    userProfile?.let { profile ->
                        viewModel.finisci_amicizia(profile.id, id) {
                            viewModel.getUserProfile()
                            navController.navigate(Schermate.Profilo.route)
                        }
                    }
                },

                shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red, // Cambia il colore di sfondo del pulsante
                    contentColor = Color.White
                ))
            {
                Text(
                    text = "Rimuovi Amicizia",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }
            Button(modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(horizontal = 16.dp),

                onClick = {
                    expanded = true // Mostra il menu a discesa
                },

                shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue, // Cambia il colore di sfondo del pulsante
                    contentColor = Color.White
                ))
            {
                Text(
                    text = "Aggiungi ad un progetto",
                    fontSize = 16.sp,

                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }
            DropdownMenu(

                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(Color.White)
            ) {

                viewModelprogetto.caricaProgettiUtente(viewModel.userProfile?.id.toString(),true)
                val progetti = viewModelprogetto.progetti
                progetti.value.forEach { progetto ->
                    DropdownMenuItem(

                        text = {  Text(progetto.nome, color = Color.Black)  },
                        onClick = {
                            viewModel.ottieni_utente(id) { profile ->
                                val contenuto = (userProfile?.nome ?: "") + " " + (userProfile?.cognome ?: "") + " " + "ti ha invitato in un progetto"
                                if (userProfile != null) {
                                    notificheRepo.creaNotifica(
                                        userProfile.id,
                                        profile?.id ?: "",
                                        "Richiesta_Progetto",
                                        contenuto,
                                        progetto.id.toString()


                                    )
                                }
                            }
                            expanded = false

                        },

                    )
                    }
            }
        }
        }

    }







@Composable
fun StatBoxUtente(number: String, label: String) {
    Column(
        modifier = Modifier
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = number, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(text = label, fontSize = 12.sp)
    }
}







