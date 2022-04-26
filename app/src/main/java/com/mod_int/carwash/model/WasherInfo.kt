package com.mod_int.carwash.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WasherInfo(
    var companyLocation : String = "",
    var companyName: String = "",
    var count: String = "",
    var point: String = "",
    var deliPrice: String = "",
    var policyPrice: String = "",
    var washingType1: String = "",
    var washingType2: String = "",
    var washingType3: String = "",
    var inWashingCountryOfCar: String = "",
    var outWashingCountryOfCar: String = "",
    var inOutWashingCountryOfCar: String = "",
    var inWashingCarSize: String = "",
    var outWashingCarSize: String = "",
    var inOutWashingCarSize: String = "",
    var inWashingCost: String = "",
    var outWashingCost: String = "",
    var inOutWashingCost: String = "",
    var inWashingTime: String = "",
    var outWashingTime: String = "",
    var inOutWashingTime: String = "",
    var introduceText: String = "",
    var expandable: String = "false"
) : Parcelable


fun HashMap<String, String>.toWasherInfo(): WasherInfo =
    WasherInfo(
        companyLocation = getValue("companyLocation"),
        companyName = getValue("companyName"),
        count = getValue("count"),
        point = getValue("point"),
        deliPrice = getValue("deliPrice"),
        policyPrice = getValue("policyPrice"),
        washingType1 = getValue("washingType1"),
        washingType2 = getValue("washingType2"),
        washingType3 = getValue("washingType3"),
        inWashingCountryOfCar = getValue("inWashingCountryOfCar"),
        outWashingCountryOfCar = getValue("outWashingCountryOfCar"),
        inOutWashingCountryOfCar = getValue("inOutWashingCountryOfCar"),
        inWashingCarSize = getValue("inWashingCarSize"),
        outWashingCarSize = getValue("outWashingCarSize"),
        inOutWashingCarSize = getValue("inOutWashingCarSize"),
        inWashingCost = getValue("inWashingCost"),
        outWashingCost = getValue("outWashingCost"),
        inOutWashingCost = getValue("inOutWashingCost"),
        inWashingTime = getValue("inWashingTime"),
        outWashingTime = getValue("outWashingTime"),
        inOutWashingTime = getValue("inOutWashingTime"),
        introduceText = getValue("introduceText"),
        expandable = getValue("expandable")
    )
