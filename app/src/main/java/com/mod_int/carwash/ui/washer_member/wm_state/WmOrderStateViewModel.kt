package com.mod_int.carwash.ui.washer_member.wm_state

import android.app.Application
import androidx.databinding.ObservableField
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class WmOrderStateViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {
    val omCarInfo = ObservableField("")
    val wmDate = ObservableField("")
    val washingType = ObservableField("")
    val washingCost = ObservableField("")
    val pickupCost = ObservableField("")
    val washingAmount = ObservableField("")
    val pmPhoneNr = ObservableField("010-1111-1111")
    val carLocation = ObservableField("")
    val washingRequest = ObservableField("")
    val stateMsg = ObservableField("")
    private val current: LocalDateTime = LocalDateTime.now()

    fun orderStateInfo(){
        wmDate.set("${current.year}년 ${current.monthValue}월 ${current.dayOfMonth}일")

    }



    fun routeOrderList(){
        viewStateChanged(WmOrderStateViewState.RouteOrderList)
    }

    // HasMap 형태로 데이터를 보여주기위한 방법 최초 데이터만 사용이 가능함
//            getFirebaseFireStore().collection("WasherMember").document("User")
//                .set(emptyMap<String, WasherInfo>(), SetOptions.merge())
//                .addOnCompleteListener {
//                    if (it.isSuccessful) {
//                        getFirebaseFireStore().collection("WasherMember").document("User").update(
//                            "list", FieldValue.arrayUnion(WasherInfo().copy(id = id))
//                        ).addOnCompleteListener {
//                            callback(it.isSuccessful)
//                        }
//                    } else {
//                        callback(false)
//                    }
//                }

    // HasMap 형태를 가지고 오는 패턴
//            firebaseRepository.getFirebaseFireStore().collection("WasherMember")
//                .document("User")
//                .get()
//                .addOnCompleteListener {
//                    if (it.isSuccessful) {
//                        if (it.result.exists()) {
//                            val getResult: ArrayList<HashMap<String, String>>? =
//                                it.result.get("wmInfo") as ArrayList<HashMap<String, String>>?
//                            val toResultList = getResult?.map { it.toWasherInfo() }
//                            if (!toResultList.isNullOrEmpty()) {
//                                viewStateChanged(
//                                    OmFindWasherViewState.GetWasherMember(toResultList)
//                                )
//                            } else {
//                            }
//                        } else {
//                        }
//                    }
//                }
}