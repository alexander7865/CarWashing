package com.mod_int.carwash.data.source.remote

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

interface FirebaseRemoteDataSource {

    suspend fun login(
        email: String,
        password: String
    ): Task<AuthResult>

    suspend fun logout(): Boolean

    suspend fun register(
        email: String,
        password: String
    ): Task<AuthResult>

    suspend fun delete(): Task<Void>?

    suspend fun resetPass(
        resetPassToEmail: String
    ): Task<Void>

    fun getFirebaseAuth(): FirebaseAuth

    fun getFirebaseFireStore(): FirebaseFirestore

}