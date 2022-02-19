package com.mod_int.carwash.history_recycler_adapter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HistoryInfo (
    val historyDate : String,
    val washTypeCategory : String,
    val carNumber : String,
    val brandHistory : String,
    val styleNameHistory : String,
    val carKindsHistory : String,
    val carColorHistory : String,
) : Parcelable