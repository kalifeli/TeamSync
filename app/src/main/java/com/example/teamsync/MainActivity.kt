package com.example.teamsync

import UserProfileViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.teamsync.navigation.NavGraph
import com.example.teamsync.screen.UserProfileScreen
import com.example.teamsync.ui.theme.TeamSyncTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private val auth: FirebaseAuth by lazy { Firebase.auth }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TeamSyncTheme {
                NavGraph()

            }
        }
    }
    
}

@Composable
fun AppContent(auth: FirebaseAuth){
    // ricorda se dobbiamo mostrare lo splash screen o no
    var showSplashScreen by remember { mutableStateOf(true) }

    LaunchedEffect(showSplashScreen) {
        delay(2000)
        // modifico la variabile che tiene traccia della visualizzaziona schermo e la metto a false: lo splash screen è stato già visualizzato
        showSplashScreen = false
    }
}

