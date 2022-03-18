package com.mod_int.carwash.ui.pickup_manager.pickup_list_recycler_view

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PickupList (
    val datePicupListWasher : String,
    val namePickupManager : String,
    val washTypeOrderListWasher : String,
    val carNumberOrderListWasher : String,
    val brandOrderListWasher : String,
    val styleNameOrderListWasher : String,
    val carKindsOrderListWasher : String,
    val carColorOrderListWasher : String,
    val carSizeOrderListWasher : String,
    val ownerAddressOrderListWasher : String,
    ): Parcelable