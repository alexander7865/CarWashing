package com.mod_int.carwash.ext


import android.util.Log
import com.google.firebase.firestore.ktx.toObject
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.model.User


//fun FirebaseRepository.getUserCheckInfo(
//    callback: (document: OmInfo?) -> Unit
//) {
//    getFirebaseAuth().currentUser!!.email.let {email ->
//        getFirebaseFireStore().collection("OwnerMember")
//            .document(email.toString())
//            .get()
//            .addOnSuccessListener { document ->
//                if (document.exists()) {
//                    callback(document.toObject(OmInfo::class.java))
//                } else {
//                    callback(null)
//                }
//            }
//
//    }
//}
//
//
//
//
//data class OmInfo(
//    val date : String,
//    val carNumber : String,
//    val carBrand : String,
//    val carModel : String,
//    val carKinds : String,
//    val carSize : String,
//    val carColor : String,
//    val myLocation : String
//)