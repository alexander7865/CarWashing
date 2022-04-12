package com.mod_int.carwash.ui.pickup_member.pm_home

import android.app.Application
import androidx.databinding.ObservableField
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ui.washer_member.wm_home.WmHomeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class PmHomeViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {
    val pmDate = ObservableField("")
    val pmCount = ObservableField("")
    val pmAccount = ObservableField("")
    val pmWasherPoint = ObservableField("")
    val pmWasherLocation = ObservableField("")
    private val current: LocalDateTime = LocalDateTime.now()

    fun pmHomeInfo(){
        pmDate.set("${current.year}년 ${current.monthValue}월 ${current.dayOfMonth}일")
        pmCount.set("12건")
        pmAccount.set("72,000원")
        pmWasherPoint.set("95점")
        pmWasherLocation.set("서울시 강남구 논현동 222-22 엘타워 주차장")
    }

    private fun checkWmInfo(){

    }

    fun routeSettlement(){
        viewStateChanged(PmHomeViewState.RouteSettlement)
    }

    fun routeWebViewSuggestPm1(){
        viewStateChanged(PmHomeViewState.RouteWebViewSuggestPm1)
    }
    fun routeWebViewSuggestPm2(){
        viewStateChanged(PmHomeViewState.RouteWebViewSuggestPm2)
    }
}