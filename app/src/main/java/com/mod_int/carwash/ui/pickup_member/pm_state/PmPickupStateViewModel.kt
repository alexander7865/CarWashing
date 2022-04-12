package com.mod_int.carwash.ui.pickup_member.pm_state

import android.app.Application
import androidx.databinding.ObservableField
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ui.washer_member.wm_state.WmOrderStateViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class PmPickupStateViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {
    val omCarInfo = ObservableField("")
    val pmDate = ObservableField("")
    val washingType = ObservableField("")
    val washingCost = ObservableField("")
    val pickupCost = ObservableField("")
    val washingAmount = ObservableField("")
    val omPhoneNr = ObservableField("010-1111-1111")
    val carLocation1 = ObservableField("")
    val washingRequest = ObservableField("")
    val stateMsg = ObservableField("")
    private val current: LocalDateTime = LocalDateTime.now()


    fun pickupStateInfo(){
        pmDate.set("${current.year}년 ${current.monthValue}월 ${current.dayOfMonth}일")
    }

    fun routeOrderList(){
        viewStateChanged(PmPickupStateViewState.RouteOrderList)
    }
}