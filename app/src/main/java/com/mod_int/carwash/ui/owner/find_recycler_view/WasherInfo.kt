package com.mod_int.carwash.ui.owner.find_recycler_view

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WasherInfo (
    val name : String,
    val count : Int,
    val point : Int,
    val deliPrice : Int,
    val policyPrice : Int,
    val location : String,
    val washingType : String,
    val inWashingCountryOfCar : String,
    val outWashingCountryOfCar : String,
    val inOutWashingCountryOfCar : String,
    val inWashingCarSize : String,
    val outWashingCarSize : String,
    val inOutWashingCarSize : String,
    val inWashingCost : String,
    val outWashingCost : String,
    val inOutWashingCost : String,
    val inWashingTime : String,
    val outWashingTime : String,
    val inOutWashingTime : String,
    val introduceText : String,

//    val playTime : String,
    var expandable : Boolean = false
) : Parcelable
