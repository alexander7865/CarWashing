package com.mod_int.carwash.ui.washer_member.wm_home

import android.app.Application
import androidx.databinding.ObservableField
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class WmHomeViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {
    val nowDate = ObservableField("")
    val count = ObservableField("")
    val account = ObservableField("")
    val washerPoint = ObservableField("")
    val washerLocation = ObservableField("")
    private val current: LocalDateTime = LocalDateTime.now()

    fun wmHomeInfo(){
        nowDate.set("${current.year}년 ${current.monthValue}월 ${current.dayOfMonth}일")
        count.set("12건")
        account.set("360,000원")
        washerPoint.set("95점")
        washerLocation.set("서울시 강남구 논현동 222-22 엘타워 주차장")
    }

    private fun checkWmInfo(){

    }

    fun routePayment(){
        viewStateChanged(WmHomeViewState.RoutePayment)
    }

    fun routeWebViewSuggestWm1(){
        viewStateChanged(WmHomeViewState.RouteWebViewSuggestWm1)
    }
    fun routeWebViewSuggestWm2(){
        viewStateChanged(WmHomeViewState.RouteWebViewSuggestWm2)
    }
}