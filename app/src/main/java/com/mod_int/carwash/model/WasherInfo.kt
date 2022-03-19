package com.mod_int.carwash.model

import android.os.Parcelable
import com.mod_int.carwash.ui.register.RegisterActivity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WasherInfo(
    var id : String = "",
    var name: String = "",
    var count: String = "",
    var point: String = "",
    var deliPrice: String = "",
    var policyPrice: String = "",
    var location: String = "",
    var washingType: String = "",
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
        id = getValue("id"),
        name = getValue("name"),
        count = getValue("count"),
        point = getValue("point"),
        deliPrice = getValue("deliPrice"),
        policyPrice = getValue("policyPrice"),
        location = getValue("location"),
        washingType = getValue("washingType"),
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

