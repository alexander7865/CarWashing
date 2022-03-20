package com.mod_int.carwash.ext


import android.util.Log
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.model.User

fun FirebaseRepository.loginAndGetUserInfo(
    email: String,
    password: String,
    callback: (user: User?) -> Unit
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
    callback: (user: User?) -> Unit
) {
    getFirebaseAuth().currentUser?.email?.let { email ->
        getFirebaseFireStore().collection(email).document("UserInfo").get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(it.result.toObject(User::class.java))
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
    val user = User(
        id, "010xxxxxxxx", type
    )

    getFirebaseFireStore().collection(id).document("UserInfo").set(user)
        .addOnCompleteListener {
            callback(it.isSuccessful)
        }
}

fun FirebaseRepository.createTypeDB(
    id: String,
    type: String,
    callback: (isSuccess: Boolean) -> Unit
) {
    val user = User(
        id, "010xxxxxxxx", type
    )

    when (type) {
        "ownerMember" -> {
            Log.d("회원타입", type)
            getFirebaseFireStore().collection("OwnerMember").document(id).set(user)
                .addOnCompleteListener {
                    callback(it.isSuccessful)
                }
        }

        // 다른형태로 보여주고 싶어 이렇게 만들었습니다.
        "washerMember" -> {
            getFirebaseFireStore().collection("WasherMember").document(id).set(user)
                .addOnCompleteListener {
                    callback(it.isSuccessful)
                }

            // HasMap 형태로 데이터를 보여주기위한 방법 최초 데이터만 사용이 가능함
//            getFirebaseFireStore().collection("WasherMember").document("User")
//                .set(emptyMap<String, WasherInfo>(), SetOptions.merge())
//                .addOnCompleteListener {
//                    if (it.isSuccessful) {
//                        getFirebaseFireStore().collection("WasherMember").document("User").update(
//                            "list", FieldValue.arrayUnion(WasherInfo().copy(id = id))
//                        ).addOnCompleteListener {
//                            callback(it.isSuccessful)
//                        }
//                    } else {
//                        callback(false)
//                    }
//                }
        }

        "pickupMember" -> {
            getFirebaseFireStore().collection("PickupMember").document(id).set(user)
                .addOnCompleteListener {
                    callback(it.isSuccessful)
                }
        }
        else -> callback(false)
    }
}