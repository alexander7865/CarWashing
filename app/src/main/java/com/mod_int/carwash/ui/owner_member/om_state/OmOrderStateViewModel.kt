package com.mod_int.carwash.ui.owner_member.om_state

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ui.owner_member.om_join.OmJoinViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class OmOrderStateViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {
    val date = ObservableField("")
    val washingType = MutableLiveData("")
    val washingPrice = MutableLiveData("")
    val pickupDeliveryPrice = MutableLiveData("")
    val totalPrice = MutableLiveData("")
    val orderStateMsg = MutableLiveData("")
    val washerName = MutableLiveData("")
    val washerBank = MutableLiveData("")
    val washerBankAccount = MutableLiveData("")
    val washerPhoneNr = ObservableField("")
    val washingState1 = MutableLiveData("")
    val washingState2 = MutableLiveData("")
    val washingState3 = MutableLiveData("")
    val washingState4 = MutableLiveData("")
    private val current: LocalDateTime = LocalDateTime.now()

    fun orderStateInfo(){
        date.set("${current.year}년 ${current.monthValue}월 ${current.dayOfMonth}일")

    }

    fun routeHistory() {
        viewStateChanged(OmOrderStateViewState.RouteHistory)
    }

    fun washerMemberPhoneNr(){
        viewStateChanged(OmOrderStateViewState.WasherMemberPhoneNr)
    }
}