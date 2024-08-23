package com.example.teamsync.caratteristiche.impostazioni.ui

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.teamsync.caratteristiche.autentificazione.data.repository.RepositoryUtente
import com.example.teamsync.caratteristiche.autentificazione.data.viewModel.ViewModelUtente
import com.example.teamsync.navigation.Schermate
import com.example.teamsync.util.NoInternetScreen
import com.example.teamsync.util.theme.Grey20
import com.example.teamsync.util.theme.Grey35
import com.example.teamsync.util.theme.Red70
import com.example.teamsync.util.theme.White
import com.example.teamsync.util.ThemePreferences
import com.example.teamsync.util.isInternetAvailable
import kotlinx.coroutines.delay

/**
 * Data class che rappresenta un singolo elemento di impostazione.
 *
 * @param icon L'icona dell'impostazione.
 * @param label L'etichetta dell'impostazione.
 * @param rotta La rotta di navigazione associata all'impostazione.
 */
data class SettingItem(
    val icon: Int,
    val label: Int,
    val rotta: Schermate
)

/**
 * Lista di elementi di impostazione per la sezione Account.
 */
val settingsList1 = listOf(
    SettingItem(R.drawable.ic_modificaaccount, R.string.modificaProfilo, Schermate.ModificaProfilo),
    SettingItem(R.drawable.ic_notifiche, R.string.Notifiche, Schermate.ImpNotifche),
    SettingItem(R.drawable.ic_tema, R.string.tema, Schermate.Tema),
    SettingItem(R.drawable.ic_attivita, R.string.task, Schermate.Imptask),
    SettingItem(R.drawable.ic_progetto, R.string.progetti, Schermate.ImpoProgetti),
)

/**
 * Lista di elementi di impostazione per la sezione Supporto.
 */
val settingsList2 = listOf(
    SettingItem(R.drawable.ic_help, R.string.help, Schermate.Supporto),
    SettingItem(R.drawable.ic_info, R.string.terms, Schermate.Terms)
)

/**
 * Lista di elementi di impostazione per la sezione Azioni, inclusa l'eliminazione dell'account.
 */
val settingsList3 = listOf(
    SettingItem(R.drawable.ic_delete, R.string.eliminaAccount, Schermate.Login)
)

/**
 * Schermata delle impostazioni che visualizza diverse opzioni di configurazione per l'utente.
 *
 * @param navController Il controller di navigazione per gestire la navigazione tra le schermate.
 * @param viewModelUtente Il ViewModel che gestisce lo stato e la logica dell'utente.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Impostazioni(
    navController: NavHostController,
    viewModelUtente: ViewModelUtente
) {
    val context = LocalContext.current
    val isDarkTheme = ThemePreferences.getTheme(context)
    val isConnected = remember { mutableStateOf(isInternetAvailable(context)) }

    LaunchedEffect(Unit) {
        // Periodically check for internet connection
        while (true) {
            isConnected.value = isInternetAvailable(context)
            delay(5000) // check every 5 seconds
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.impostazioni),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = if (isDarkTheme) Color.White else Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Schermate.ItuoiProgetti.route) }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = if (isDarkTheme) Color.White else Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = if (isDarkTheme) Color.DarkGray else White
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(if (isDarkTheme) Color.Black else Color.White)
        ) {
            if (!isConnected.value) {
                NoInternetScreen(
                    isDarkTheme = isDarkTheme,
                    onRetry = { isConnected.value = isInternetAvailable(context) }
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        SectionHeader(text = "Account", isDarkTheme = isDarkTheme)
                        SettingsCard(settingsList = settingsList1, navController = navController, viewModelUtente = viewModelUtente, isDarkTheme = isDarkTheme)
                    }
                    item {
                        SectionHeader(text = stringResource(id = R.string.supporto), isDarkTheme = isDarkTheme)
                        SettingsCard(settingsList = settingsList2, navController = navController, viewModelUtente = viewModelUtente, isDarkTheme = isDarkTheme)
                    }
                    item {
                        SectionHeader(text = stringResource(id = R.string.azioni), isDarkTheme = isDarkTheme)
                        SettingsCard(settingsList = settingsList3, navController = navController, viewModelUtente = viewModelUtente, isDarkTheme = isDarkTheme, isDeleteAccount = true)
                    }
                }
            }
        }
    }
}

/**
 * Composable per visualizzare l'intestazione di una sezione di impostazioni.
 *
 * @param text Il testo da visualizzare come intestazione della sezione.
 * @param isDarkTheme Flag che indica se il tema scuro è abilitato.
 */
@Composable
fun SectionHeader(text: String, isDarkTheme: Boolean) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        color = if (isDarkTheme) White else Color.Black,
        modifier = Modifier.padding(start = 8.dp, top = 16.dp)
    )
}

/**
 * Composable per visualizzare una scheda contenente una lista di elementi di impostazione.
 *
 * @param settingsList Lista di elementi di impostazione da visualizzare.
 * @param navController Il controller di navigazione per gestire la navigazione tra le schermate.
 * @param viewModelUtente Il ViewModel che gestisce lo stato e la logica dell'utente.
 * @param isDarkTheme Flag che indica se il tema scuro è abilitato.
 * @param isDeleteAccount Flag che indica se la scheda contiene l'opzione per eliminare l'account.
 */
@Composable
fun SettingsCard(settingsList: List<SettingItem>, navController: NavHostController, viewModelUtente: ViewModelUtente, isDarkTheme: Boolean, isDeleteAccount: Boolean = false) {
    Card(
        colors = CardDefaults.cardColors(containerColor = if (isDeleteAccount) Red70 else if (isDarkTheme) Color.Black else Color(0xFFE5E5E5)),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(0.5.dp, if(isDarkTheme) White else Color.Unspecified)
    ) {
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            settingsList.forEach { setting ->
                SettingItemRow(setting = setting, navController = navController, viewModelUtente = viewModelUtente, isDarkTheme = isDarkTheme, isDeleteAccount = isDeleteAccount)
                if (setting != settingsList.last()) {
                    HorizontalDivider(color = if (isDarkTheme) Color.White else Grey20)
                }
            }
        }
    }
}

/**
 * Composable per visualizzare una riga contenente un singolo elemento di impostazione.
 *
 * @param setting L'elemento di impostazione da visualizzare.
 * @param navController Il controller di navigazione per gestire la navigazione tra le schermate.
 * @param viewModelUtente Il ViewModel che gestisce lo stato e la logica dell'utente.
 * @param isDarkTheme Flag che indica se il tema scuro è abilitato.
 * @param isDeleteAccount Flag che indica se l'elemento di impostazione riguarda l'eliminazione dell'account.
 */
@Composable
fun SettingItemRow(setting: SettingItem, navController: NavHostController, viewModelUtente: ViewModelUtente, isDarkTheme: Boolean, isDeleteAccount: Boolean) {
    val userProfile by viewModelUtente.userProfilo.observeAsState()
    val erroreEliminazioneUtente by viewModelUtente.erroreEliminazioneUtente.observeAsState()
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (setting.label == R.string.eliminaAccount) {
                    showDialog = true
                } else {
                    navController.navigate(setting.rotta.route)
                }
            }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = setting.icon),
            contentDescription = null,
            tint = if (isDeleteAccount || isDarkTheme) Color.White else Color.Black,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = stringResource(id = setting.label),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = if (isDeleteAccount || isDarkTheme) Color.White else Color.Black,
            modifier = Modifier.padding(start = 8.dp)
        )
        if (setting.label == R.string.eliminaAccount && showDialog) {
            userProfile?.let {
                DeleteAccountDialog(
                    onSuccess = {
                        if (erroreEliminazioneUtente.isNullOrEmpty()) {
                            navController.navigate(setting.rotta.route)
                            viewModelUtente.eliminaAccount(userProfile!!.id)
                        } else {
                            Toast.makeText(context, erroreEliminazioneUtente, Toast.LENGTH_LONG).show()
                        }
                    },
                    onDismissRequest = { showDialog = false },
                    isDarkTheme = isDarkTheme
                )
            }
        }
    }
}

/**
 * Composable per visualizzare un dialogo di conferma per l'eliminazione dell'account.
 *
 * @param onDismissRequest Funzione chiamata quando il dialogo viene chiuso senza conferma.
 * @param onSuccess Funzione chiamata quando l'utente conferma l'eliminazione dell'account.
 * @param isDarkTheme Flag che indica se il tema scuro è abilitato.
 */

@Composable
fun DeleteAccountDialog(
    onDismissRequest: () -> Unit,
    onSuccess: () -> Unit,
    isDarkTheme: Boolean
) {
    AlertDialog(
        title = {
            Text(
                text = stringResource(id = R.string.eliminaAccount),
                textAlign = TextAlign.Center,
                color = if (isDarkTheme) Color.White else Color.Black
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.eliminaAccountDescrizione),
                color = if (isDarkTheme) Color.White else Color.Black,
            )
        },
        containerColor = if (isDarkTheme) Color.Black else Grey35,
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = { onSuccess() },
                colors = ButtonDefaults.buttonColors(Red70),
            ) {
                Text(text = stringResource(id = R.string.conferma))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(
                    text = stringResource(id = R.string.annullaEdit),
                    color = if (isDarkTheme) Color.White else Color.Black
                )
            }
        }
    )
}

/**
 * Funzione di anteprima per la schermata delle Impostazioni.
 */
@Preview
@Composable
fun PreviewImpostazioni() {
    Impostazioni(navController = rememberNavController(), viewModelUtente = ViewModelUtente(
        RepositoryUtente()
    ))
}

