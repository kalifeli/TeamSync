package com.example.teamsync

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.teamsync.ui.theme.TeamSyncTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TeamSyncTheme {
                println("ciao")
                println("porco dio")
                println("alex frocio")
            }
        }
    }
}

@Composable
fun ciaoooooooooooooo() {
    var text = "Benvenuto in TeamSync";
}
