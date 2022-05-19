package com.mod_int.carwash.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderOmInfo(
    var orderDate : String = "",
    var orderReservationTime : String = "",
    var orderType : String = "",
    var orderAmount : String = "",
    var orderMassage : String = "",
) : Parcelable

//fun HashMap<String, String>.toOrderOmInfo(): OrderOmInfo =
//    OrderOmInfo(
//        orderDate = getValue("orderDate"),
//        orderReservationTime = getValue("orderReservationTime"),
//        orderType = getValue("orderType"),
//        orderAmount = getValue("orderAmount"),
//        orderMassage = getValue("orderMassage"),
//)