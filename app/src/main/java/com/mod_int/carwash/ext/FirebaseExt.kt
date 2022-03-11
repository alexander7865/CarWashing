package com.mod_int.carwash.ext

import com.mod_int.carwash.RegisterActivity
import com.mod_int.carwash.data.repo.FirebaseRepository

fun FirebaseRepository.loginAndGetUserInfo(
    email: String,
    password: String,
    callback: (user: RegisterActivity.User?) -> Unit
) {
    loginUser(email, password) { isLogin ->
        if (isLogin) {
            getUserInfo { callback(it) }
        } else {
            callback(null)
        }
    }
}

fun FirebaseRepository.loginUser(
    email: String,
    password: String,
    callback: (isSuccess: Boolean) -> Unit
) {
    getFirebaseAuth().signInWithEmailAndPassword(email, password)
        .addOnCompleteListener {
            callback(it.isSuccessful)
        }
}

fun FirebaseRepository.getUserInfo(
    callback: (user: RegisterActivity.User?) -> Unit
) {
    getFirebaseAuth().currentUser?.email?.let { email ->
        getFirebaseFireStore().collection(email).document("UserInfo").get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(it.result.toObject(RegisterActivity.User::class.java))
                } else {
                    callback(null)
                }
            }
    } ?: callback(null)
}