package com.mod_int.carwash.ui.washer_member.wm_home

import android.app.Application
import android.os.Parcelable
import androidx.databinding.ObservableField
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ext.ioScope
import com.mod_int.carwash.ext.wmOrderCount
import com.mod_int.carwash.ui.owner_member.om_state.WmBankInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class WmHomeViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {
    val nowDate = ObservableField("")
    val count = ObservableField("")
    val companyName = ObservableField("")
    val washerPoint = ObservableField("")
    val washerLocation = ObservableField("")
    private val current: LocalDateTime = LocalDateTime.now()

    fun wmHomeInfo() {
        ioScope {
            firebaseRepository.getFirebaseAuth().currentUser?.email.let { email ->
                firebaseRepository.getFirebaseFireStore().collection("WasherMember")
                    .document(email.toString())
                    .get()
                    .addOnSuccessListener {
                        nowDate.set("${current.year}년 ${current.monthValue}월 ${current.dayOfMonth}일")
                        count.set("${it.get("wmCount")}")
                        //주문건 금액이 쌓여야함
                        companyName.set("${it.get("wmCompanyName")}")
                        washerPoint.set("${it.get("wmPoint")}")
                        washerLocation.set("${it.get("wmLocation")}")
                    }

            }
        }
    }

    fun routePriceRegistration() {
        viewStateChanged(WmHomeViewState.RoutePriceRegistration)
    }

    fun routeWebViewSuggestWm1() {
        viewStateChanged(WmHomeViewState.RouteWebViewSuggestWm1)
    }

    fun routeWebViewSuggestWm2() {
        viewStateChanged(WmHomeViewState.RouteWebViewSuggestWm2)
    }
}

//@Parcelize
//data class WmInfo(
//    var nowDate: String = "",
//    var wmCount: String = "",
//    var wmAccount: String = "",
//    var wmPoint: String = "",
//    var wmLocation: String = "",
//) : Parcelable