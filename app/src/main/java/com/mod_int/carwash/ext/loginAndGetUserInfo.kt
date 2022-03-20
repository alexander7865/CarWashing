package com.mod_int.carwash.ext

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.model.User
import com.mod_int.carwash.model.WasherInfo

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
        getFirebaseFireStore().collection(email).document("User").get()
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
    val user = User(
        id, "010xxxxxxxx", type
    )
    when (type) {
        "ownerMember" -> {
            getFirebaseFireStore().collection("OwnerMember").document(id).set(user)
                .addOnCompleteListener {
                    callback(it.isSuccessful)
                }
        }

        // SetOptions.merge() 덮어씌기 방지
        "washerMember" -> {
            //방법 1 본 방법으로 구현했을시 워셔맴버가 정보입력을 받았을경우 많이 꼬여버렸습니다.
//            getFirebaseFireStore().collection("WasherMember").document("info")
//                .set(emptyMap<String, WasherInfo>(),SetOptions.merge())
//                .addOnCompleteListener {
//                    if (it.isSuccessful) {
//                        getFirebaseFireStore().collection("WasherMember").document("info").update(
//                            "list", FieldValue.arrayUnion(WasherInfo().copy(id = id))
//                        ).addOnCompleteListener {
//                            callback(it.isSuccessful)
//                        }
//                    } else {
//                        callback(false)
//                    }
//                }

            // 방법 2 맵형태로가 아닌 일반 형태로 가지고 오는 형식으로도 만들었습니다.
            getFirebaseFireStore().collection("WasherMember").document(id)
                .set(user)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        getFirebaseFireStore().collection("WasherMember").document(id).set(user)
                        .addOnCompleteListener {
                            callback(it.isSuccessful)
                        }
                    } else {
                        callback(false)
                    }
                }
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