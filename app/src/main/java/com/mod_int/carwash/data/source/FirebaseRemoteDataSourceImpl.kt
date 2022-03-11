package com.mod_int.carwash.data.source

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
) :
    FirebaseRemoteDataSource {

    override suspend fun login(id: String, password: String): Task<AuthResult> =
        withContext(Dispatchers.IO) {
            return@withContext firebaseAuth.signInWithEmailAndPassword(id, password)
        }

    override suspend fun logout(): Boolean =
        withContext(Dispatchers.IO) {
            return@withContext try {
                firebaseAuth.signOut()
                firebaseAuth.currentUser == null
            } catch (e: Exception) {
                false
            }
        }

    override suspend fun register(id: String, password: String): Task<AuthResult> =
        withContext(Dispatchers.IO) {
            return@withContext firebaseAuth.createUserWithEmailAndPassword(
                id,
                password
            )
        }

    override suspend fun delete(): Task<Void>? = withContext(Dispatchers.IO) {
        return@withContext firebaseAuth.currentUser?.delete()
    }

    override suspend fun resetPass(resetPassToId: String): Task<Void> =
        withContext(Dispatchers.IO) {
            return@withContext firebaseAuth.sendPasswordResetEmail(resetPassToId)
        }

    override fun getFirebaseAuth(): FirebaseAuth =
        firebaseAuth

    override fun getFirebaseFireStore(): FirebaseFirestore =
        fireStore

}