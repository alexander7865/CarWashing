package com.mod_int.carwash.find_recycler_adapter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WasherInfo (
    val name : String,
    val count : Int,
    val point : Int,
    val deliPrice : Int,
    val policyPrice : Int,
    val far : Int,
    val time : Int,
    val playTime : String,
    var expandable : Boolean = false
) : Parcelable
