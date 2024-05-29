package com.example.teamsync.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.teamsync.R
import com.example.teamsync.navigation.Schermate

//viewModel: UserProfileViewModel,navController: NavHostController
@Composable
fun UserProfileScreen() {
//    var nome by viewModel.nome
//    var cognome by viewModel.cognome
//    var username by viewModel.username
//    var dataDiNascita by viewModel.dataDiNascita
//    var matricola by viewModel.matricola
//    var email by viewModel.email
//    var id by viewModel.id
//
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Top,
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Text("Modifica Profilo")
//            Spacer(modifier = Modifier.height(16.dp))
//        }
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxHeight(0.2f),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.user_icon),
//                contentDescription = "Icona Applicazione",
//                modifier = Modifier
//                    .size(70.dp)
//
//
//            )
//        }
//        // Icona per caricare l'immagine del profilo
//        Column(
//            modifier = Modifier
//                .padding(horizontal = 40.dp),
//        ) {
//        OutlinedTextField(value = nome, onValueChange = { newValue ->
//            nome = newValue
//        },
//            label = { Text("Nome") })
//        OutlinedTextField(value = cognome, onValueChange = { newValue ->
//            cognome = newValue
//        },label = { Text("Cognome") })
//
//        // Campo per l'username
//
//        // Selettore per la data di nascita
//
//        OutlinedTextField(value = matricola, onValueChange = { newValue ->
//            matricola = newValue
//        }, label = { Text("Matricola") })
//
//        OutlinedTextField(value = username, onValueChange = { newValue ->
//            username = newValue
//        }, label = { Text("Username") })
//        // Campo per l'email
//        OutlinedTextField(value = dataDiNascita, onValueChange = { newValue ->
//            dataDiNascita = newValue
//        }, label = { Text("Data Di Nascita") })
//        OutlinedTextField(value = email, onValueChange = { newValue ->
//            email = newValue
//        }, label = { Text("Email") })
//        }
//
//        Column(
//            modifier = Modifier
//                .padding(horizontal = 40.dp)
//                .padding(vertical = 20.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Top,
//        ) {
//
//            Button(onClick = { /* TODO: Cambia Password */ }) {
//                Text("Cambia Password")
//            }
//        }
//        Column(
//            modifier = Modifier
//                .padding(horizontal = 40.dp)
//                .padding(vertical = 20.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Top,
//        ) {
//
//            Button(onClick = {
//                //viewModel.updateUserProfile(id,nome,cognome,username,matricola,email)
//                navController.navigate(Schermate.Impostazioni.route)
//            } )
//            {
//                Text("Salva")
//            }
//        }
//
//
//
//        }
    }
//
//
//@Preview(showBackground = true)
//@Composable
//fun Vai() {
//    val viewModel = UserProfileViewModel("ID_2") // Crea un'istanza del ViewModel
//    UserProfileScreen(viewModel = viewModel, navController = (rememberNavController()))
//
//}








