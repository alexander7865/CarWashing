package com.mod_int.carwash.model

import android.os.Parcelable
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HistoryInfo(
    val historyDate: String,
    val washTypeCategory: String,
    val carNumber: String,
    val brandHistory: String,
    val styleNameHistory: String,
    val carKindsHistory: String,
    val carColorHistory: String,
) : Parcelable

fun HashMap<String, String>.toHisInfo(): HistoryInfo =
    HistoryInfo(
        historyDate = getValue("historyDate"),
        washTypeCategory = getValue("washTypeCategory"),
        carNumber = getValue("carNumber"),
        brandHistory = getValue("brandHistory"),
        styleNameHistory = getValue("styleNameHistory"),
        carKindsHistory = getValue("carKindsHistory"),
        carColorHistory = getValue("carColorHistory")

    )