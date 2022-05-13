package com.mod_int.carwash.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PriceItem (
    var wmCompanyName : String = "",
    var insideWashingCarXS: String = "",
    var insideWashingCarS: String = "",
    var insideWashingCarM: String = "",
    var insideWashingCarL: String = "",
    var outsideWashingCarXS: String = "",
    var outsideWashingCarS: String = "",
    var outsideWashingCarM: String = "",
    var outsideWashingCarL: String = "",
    var inOutsideWashingCarXS: String = "",
    var inOutsideWashingCarS: String = "",
    var inOutsideWashingCarM: String = "",
    var inOutsideWashingCarL: String = "",
    var insideWashingTime: String = "",
    var outsideWashingTime: String = "",
    var inOutsideWashingTime: String = "",
    var pickupDeliveryCost: String = "",
    var addCost : String = "",
    ) : Parcelable