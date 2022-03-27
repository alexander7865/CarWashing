package com.mod_int.carwash.model

import android.os.Parcelable
import com.mod_int.carwash.base.ViewState
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HistoryInfo(
    var date: String ="",
    var washType: String ="",
    var carInfo: String =""
) : Parcelable




