package com.mod_int.carwash.ext

import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ui.register.RegisterActivity

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

// 이하 내용들 3.19 바뀐내용 적용
fun FirebaseRepository.getUserInfo(
    callback: (user: RegisterActivity.User?) -> Unit
) {
    getFirebaseAuth().currentUser?.email?.let { email ->
        getFirebaseFireStore().collection(email).document("User").get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(it.result.toObject(RegisterActivity.User::class.java))
                } else {
                    callback(null)
                }
            }
    } ?: callback(null)
}

fun FirebaseRepository.checkRegister(
    id: String,
    password: String,
    type: String,
    callback: (isSuccess: Boolean) -> Unit
) {
    registerUser(id, password) { isRegister ->
        if (isRegister) {
            createUserDB(id, type) { isCreateUserDB ->
                if (isCreateUserDB) {
                    createTypeDB(id, type, callback)
                } else {
                    callback(false)
                }
            }
        } else {
            callback(false)
        }
    }
}

fun FirebaseRepository.registerUser(
    id: String,
    password: String,
    callback: (isSuccess: Boolean) -> Unit
) {
    getFirebaseAuth().createUserWithEmailAndPassword(id, password).addOnCompleteListener {
        callback(it.isSuccessful)
    }
}


fun FirebaseRepository.createUserDB(
    id: String,
    type: String,
    callback: (isSuccess: Boolean) -> Unit
) {
    val user = RegisterActivity.User(
        id, "010xxxxxxxx", type
    )

    getFirebaseFireStore().collection(id).document("User").set(user)
        .addOnCompleteListener {
            callback(it.isSuccessful)
        }
}

fun FirebaseRepository.createTypeDB(
    id: String,
    type: String,
    callback: (isSuccess: Boolean) -> Unit
) {
    val user = RegisterActivity.User(
        id, "010xxxxxxxx", type
    )
    when (type) {
        "ownerMember" -> {
            getFirebaseFireStore().collection("OwnerMember").document(id).set(user)
                .addOnCompleteListener {
                    callback(it.isSuccessful)
                }
        }

        "headWasher" -> {
            getFirebaseFireStore().collection("HeadWasher").document(id).set(user)
                .addOnCompleteListener {
                    callback(it.isSuccessful)
                }
        }

        "pickupWasher" -> {
            getFirebaseFireStore().collection("PickupWasher").document(id).set(user)
                .addOnCompleteListener {
                    callback(it.isSuccessful)
                }
        }
        else -> callback(false)
    }
}