package com.mod_int.carwash.model

import android.os.Parcelable
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HistoryInfo(
    var email: String,
    var phoneNumber: String,
    var type: String,
//    val carBrand: String,
//    val carModel: String,
//    val carKinds: String,
//    val carColor: String,
) : Parcelable
