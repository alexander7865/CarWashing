package com.mod_int.carwash.model

import android.os.Parcelable
import com.mod_int.carwash.base.ViewState
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HistoryInfo(
    var email: String ="",
    var phoneNumber: String ="",
    var type: String =""
) : Parcelable




