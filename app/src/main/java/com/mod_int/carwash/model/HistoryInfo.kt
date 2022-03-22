package com.mod_int.carwash.model

import android.os.Parcelable
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HistoryInfo(
    val email: String,
    val phoneNumber: String,
    val type: String,
//    val carBrand: String,
//    val carModel: String,
//    val carKinds: String,
//    val carColor: String,
) : Parcelable
