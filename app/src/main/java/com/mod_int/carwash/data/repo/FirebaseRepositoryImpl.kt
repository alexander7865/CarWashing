package com.mod_int.carwash.data.repo


import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mod_int.carwash.data.source.remote.FirebaseRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FirebaseRepositoryImpl @Inject constructor(private val firebaseRemoteDataSource: FirebaseRemoteDataSource) :
    FirebaseRepository {


    override suspend fun login(id: String, password: String): Task<AuthResult> =
        withContext(Dispatchers.IO) {
            return@withContext firebaseRemoteDataSource.login(id, password)
        }

    override suspend fun logout(): Boolean =
        withContext(Dispatchers.IO) {
            return@withContext firebaseRemoteDataSource.logout()
        }

    override suspend fun register(id: String, password: String): Task<AuthResult> =
        withContext(Dispatchers.IO) {
            return@withContext firebaseRemoteDataSource.register(id, password)
        }

    override suspend fun delete(): Task<Void>? = withContext(Dispatchers.IO) {
        return@withContext firebaseRemoteDataSource.delete()
    }

    override suspend fun resetPass(resetPassToId: String): Task<Void> =
        withContext(Dispatchers.IO) {
            return@withContext firebaseRemoteDataSource.resetPass(resetPassToId)
        }

    override fun getFirebaseAuth(): FirebaseAuth =
        firebaseRemoteDataSource.getFirebaseAuth()

    override fun getFirebaseFireStore(): FirebaseFirestore =
        firebaseRemoteDataSource.getFirebaseFireStore()

}