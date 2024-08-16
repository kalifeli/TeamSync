package com.example.teamsync.caratteristiche.notifiche.data.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.teamsync.caratteristiche.notifiche.data.repository.RepositoryNotifiche
import com.example.teamsync.caratteristiche.autentificazione.data.repository.RepositoryUtente
import com.example.teamsync.caratteristiche.autentificazione.data.viewModel.ViewModelUtente
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

import org.junit.Before
import org.junit.Rule
import org.junit.Test
/*
@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelNotificheTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repositoryNotifiche: RepositoryNotifiche
    private lateinit var repositoryUtente: RepositoryUtente
    private lateinit var viewModelUtente: ViewModelUtente

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repositoryNotifiche = mockk(relaxed = true)
        repositoryUtente = mockk(relaxed = true)
        viewModelUtente = ViewModelUtente(repositoryUtente)
    }


//    @Test
//    fun `fetchNotifiche recupera correttamente le notifiche di un utente`() = runTest {
//        val userId = "utenteId"
//
//    }
}

 */