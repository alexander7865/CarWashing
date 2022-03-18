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
//로그인시 맴버 type 별로 로그인이 안되어 아래와 같이 구현해봤는데 로그인이 되어도 "로그인이 실패하였습니다" 메세지가 나오네요
//이부분은 어떻게 해야할까요? 일단 토스트메세지가 안나오도록 막아 놨습니다.
fun FirebaseRepository.getUserInfo(
    callback: (user: RegisterActivity.User?) -> Unit) {
    getFirebaseAuth().currentUser?.email?.let {email ->
        val types = listOf("ownerMember","headWasher","pickupWasher")
        for(i in types) {
            getFirebaseFireStore().collection(i).document(email).get()
                .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(it.result.toObject(RegisterActivity.User::class.java))
                } else {
                    callback(null)
                }
            }
        }
//        getFirebaseFireStore().collection(email).document("UserInfo").get()
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    callback(it.result.toObject(RegisterActivity.User::class.java))
//                } else {
//                    callback(null)
//                }
//            }
    } ?: callback(null)
}