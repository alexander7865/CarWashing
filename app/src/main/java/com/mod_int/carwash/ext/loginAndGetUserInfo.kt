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
    email : String,
    password: String,
    type: String,
    callback: (isSuccess: Boolean) -> Unit
) {
    registerUser(email, password) { isRegister ->
        if (isRegister) {
            createUserDB(email, type) { isCreateUserDB ->
                if (isCreateUserDB) {
                    createTypeDB(email, type, callback)
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
    email: String,
    password: String,
    callback: (isSuccess: Boolean) -> Unit
) {
    getFirebaseAuth().createUserWithEmailAndPassword(email, password).addOnCompleteListener {
        callback(it.isSuccessful)
    }
}


fun FirebaseRepository.createUserDB(
    email: String,
    type: String,
    callback: (isSuccess: Boolean) -> Unit
) {
    val user = User(
        email, "010xxxxxxxx", type
    )
    //회원가입 생성
    getFirebaseFireStore().collection(email).document("User").set(user)
        .addOnCompleteListener {
            callback(it.isSuccessful)
        }
}

fun FirebaseRepository.createTypeDB(
    email: String,
    type: String,
    callback: (isSuccess: Boolean) -> Unit
) {
    val user = User(
        email, "010xxxxxxxx", type
    )

    when (type) {
        "ownerMember" -> {
            Log.d("회원타입", type)
            getFirebaseFireStore().collection("OwnerMember").document(email).set(user)
                .addOnCompleteListener {
                    callback(it.isSuccessful)
                }
        }

        // 다른형태로 보여주고 싶어 이렇게 만들었습니다. 가지고 오는 방법은 컬렉션의 모든 데이터를 가지고 오는 형식으로 했습니다
        "washerMember" -> {
            getFirebaseFireStore().collection("WasherMember").document(email).set(user)
                .addOnCompleteListener {
                    callback(it.isSuccessful)
                }

            // HashMap 형태로 데이터를 보여주기위한 방법 최초 데이터만 사용이 가능함
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
            getFirebaseFireStore().collection("PickupMember").document(email).set(user)
                .addOnCompleteListener {
                    callback(it.isSuccessful)
                }
        }
        else -> callback(false)
    }
}