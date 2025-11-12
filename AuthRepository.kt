package com.example.snowtimerapp.data.repo

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth,
    private val store: FirebaseFirestore
) {
    private val usernames = store.collection("usernames")
    private val users = store.collection("users")

    suspend fun isUsernameAvailable(raw: String): Boolean {
        val handle = raw.trim().lowercase()
        if (handle.isBlank()) return false
        return !usernames.document(handle).get().await().exists()
    }

    suspend fun createEmailAccount(email: String, password: String) =
        auth.createUserWithEmailAndPassword(email, password).await().user!!

    suspend fun reserveUsernameAndCreateProfile(
        uid: String,
        username: String,
        nickname: String,
        email: String
    ) {
        val handle = username.trim().lowercase()
        store.runTransaction { tx ->
            val hRef = usernames.document(handle)
            val uRef = users.document(uid)
            if (tx.get(hRef).exists()) error("USERNAME_TAKEN")
            tx.set(hRef, mapOf("uid" to uid))
            tx.set(
                uRef,
                mapOf(
                    "email" to email,
                    "username" to handle,
                    "nickname" to nickname,
                    "createdAt" to FieldValue.serverTimestamp()
                ),
                SetOptions.merge()
            )
        }.await()
    }
}