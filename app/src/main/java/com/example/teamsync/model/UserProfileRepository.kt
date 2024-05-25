package com.example.teamsync.model
import java.util.*
import java.time.LocalDate

class UserProfileRepository {

            // Utente 1
            val user1 = UserProfile(
                id = "ID_1",
                nome = "Mario",
                cognome = "Rossi",
                username = "mario.rossi",
                dataDiNascita = Date(1995, 12, 23), // Utilizzare Calendar o altro per creare date
                matricola = "123456",
                email = "mario.rossi@example.com"
            )

            // Utente 2
            val user2 = UserProfile(
                id = "ID_2",
                nome = "Luigi",
                cognome = "Verdi",
                username = "luigi.verdi",
                dataDiNascita = Date(1990, 8, 25),
                matricola = "789012",
                email = "luigi.verdi@example.com"
            )

            // Utente 3
            val user3 = UserProfile(
                id = "ID_3",
                nome = "Giovanna",
                cognome = "Bianchi",
                username = "giovanna.bianchi",
                dataDiNascita = Date(1988, 3, 10),
                matricola = "345678",
                email = "giovanna.bianchi@example.com"
            )
    private val userList = listOf(user1, user2, user3)

    // Metodo per ottenere un utente tramite il suo ID
    fun getUserById(userId: String): UserProfile? {
        return userList.find { it.id == userId }
    }

    // Metodo per ottenere tutti gli utenti
    fun getUsers(): List<UserProfile> {
        return userList
    }
    }

