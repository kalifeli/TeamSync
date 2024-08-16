package com.example.teamsync.util

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import java.util.UUID

class ImageUploader {

    /**
     * Carica un'immagine su Firebase Storage.
     *
     * @param uri L'URI dell'immagine da caricare.
     * @return Un [Task] che rappresenta il completamento del caricamento e fornisce l'URI dell'immagine caricata.
     */
    fun uploadImageToFirebaseStorage(uri: Uri): Task<Uri> {
        val storageRef = Firebase.storage.reference
        val imagesRef = storageRef.child("images/${UUID.randomUUID()}")

        return imagesRef.putFile(uri)
            .continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                imagesRef.downloadUrl
            }
    }
}