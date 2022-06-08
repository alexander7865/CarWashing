package com.mod_int.carwash.model

import android.os.Parcelable
import android.widget.Button
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderList (
    val email : String,
    val ordDate: String,
    val ordState : String,
    val ordType : String,
    val ordCarNum : String,
    val ordCarBrand : String,
    val ordCarModel : String,
    val ordCarCategory : String,
    val ordCarCol : String,
    val ordCarSize : String,
    val ordLocation : String,
    val ordTime : String,
    ): Parcelable