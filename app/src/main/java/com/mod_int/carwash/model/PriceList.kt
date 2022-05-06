package com.mod_int.carwash.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PriceList (
    var insideWashingKoreaCarXS: String = "",
    var insideWashingKoreaCarS: String = "",
    var insideWashingKoreaCarM: String = "",
    var insideWashingKoreaCarL: String = "",
    var outsideWashingKoreaCarXS: String = "",
    var outsideWashingKoreaCarS: String = "",
    var outsideWashingKoreaCarM: String = "",
    var outsideWashingKoreaCarL: String = "",
    var inOutsideWashingKoreaCarXS: String = "",
    var inOutsideWashingKoreaCarS: String = "",
    var inOutsideWashingKoreaCarM: String = "",
    var inOutsideWashingKoreaCarL: String = "",
    var insideWashingForeignCarXS: String = "",
    var insideWashingForeignCarS: String = "",
    var insideWashingForeignCarM: String = "",
    var insideWashingForeignCarL: String = "",
    var outsideWashingForeignCarXS: String = "",
    var outsideWashingForeignCarS: String = "",
    var outsideWashingForeignCarM: String = "",
    var outsideWashingForeignCarL: String = "",
    var inOutsideWashingForeignCarXS: String = "",
    var inOutsideWashingForeignCarS: String = "",
    var inOutsideWashingForeignCarM: String = "",
    var inOutsideWashingForeignCarL: String = "",
    var insideWashingTime: String = "",
    var outsideWashingTime: String = "",
    var inOutsideWashingTime: String = "",
    var email: String = "",
    ) : Parcelable