package com.mod_int.carwash.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WasherInfo(
    var wmLocation : String = "",
    var wmCompanyName : String = "",
    var wmCount: String = "",
    var wmPoint: String = "",
    var wmCheck1: String = "",
    var wmCheck2: String = "",
    var wmCheck3: String = "",
    var washerIntroduce: String = "",
    var expandable: String = "false"
) : Parcelable


//fun HashMap<String, String>.toWasherInfo(): WasherInfo =
//    WasherInfo(
//        wmLocation = getValue("companyLocation"),
//        wmCompanyName = getValue("companyName"),
//        wmCount = getValue("count"),
//        wmPoint = getValue("point"),
//        deliveryCost = getValue("deliPrice"),
//        polishCost = getValue("policyPrice"),
//        wmCheck1 = getValue("washingType1"),
//        wmCheck2 = getValue("washingType2"),
//        wmCheck3 = getValue("washingType3"),
//        inWashingCountryOfCar = getValue("inWashingCountryOfCar"),
//        outWashingCountryOfCar = getValue("outWashingCountryOfCar"),
//        inOutWashingCountryOfCar = getValue("inOutWashingCountryOfCar"),
//        inWashingCarSize = getValue("inWashingCarSize"),
//        outWashingCarSize = getValue("outWashingCarSize"),
//        inOutWashingCarSize = getValue("inOutWashingCarSize"),
//        inWashingCost = getValue("inWashingCost"),
//        outWashingCost = getValue("outWashingCost"),
//        inOutWashingCost = getValue("inOutWashingCost"),
//        insideWashingTime = getValue("inWashingTime"),
//        outsideWashingTime = getValue("outWashingTime"),
//        inOutsideWashingTime = getValue("inOutWashingTime"),
//        washerIntroduce = getValue("introduceText"),
//        expandable = getValue("expandable")
//    )
