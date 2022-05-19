package com.mod_int.carwash.ui.owner_member.om_state

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.fragment.app.viewModels
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ext.ioScope
import com.mod_int.carwash.model.OrderOmInfo
import com.mod_int.carwash.ui.dialog.CustomDialogOrderViewModel
import com.mod_int.carwash.ui.dialog.CustomDialogOrderViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class OmOrderStateViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {
    var omDate = ObservableField("")
    var washingType = ObservableField("")
    var washingPrice = ObservableField("")
    var pickupDeliveryPrice = ObservableField("")
    var totalPrice = ObservableField("")
    var orderStateMsg = ObservableField("")
    var washerName = ObservableField("")
    var washerBank = ObservableField("")
    var washerBankAccount = ObservableField("")
    var washerPhoneNr = ObservableField("")
    var washingState1 = ObservableField("")
    var washingState2 = ObservableField("")
    var washingState3 = ObservableField("")
    var washingState4 = ObservableField("")
    private val current: LocalDateTime = LocalDateTime.now()

    fun orderStateInfo(){
        omDate.set("${current.year}년 ${current.monthValue}월 ${current.dayOfMonth}일")


        //오더현황에서 오너가 입력한 정보가 모두 넘어와야합니다. 어떻게 해야 맞는지요?
        ioScope {
            val email = firebaseRepository.getFirebaseAuth().currentUser!!.email
            firebaseRepository.getFirebaseFireStore().collection("OwnerMember")
                .document("$email")
                .get()
                .addOnSuccessListener{
                    val ordType = it.get("orderType")
                    washingType.set("$ordType")
                    Log.d("오더현황", "orderStateInfo: $ordType")
                }

        }
    }

    fun routeHistory() {
        viewStateChanged(OmOrderStateViewState.RouteHistory)
    }

    fun washerMemberPhoneNr(){
        viewStateChanged(OmOrderStateViewState.WasherMemberPhoneNr)
    }

//    fun hashMapTest(){
//        ioScope {
//            val email = firebaseRepository.getFirebaseAuth().currentUser!!.email
//            firebaseRepository.getFirebaseFireStore().collection("OwnerMember")
//                .document("$email")
//                .get()
//                .addOnCompleteListener {
//                    if(it.isSuccessful){
//                        if(it.result.exists()){
//                            val getResult : ArrayList<HashMap<String,String>>? =
//                                it.result.get("OrderList") as ArrayList<HashMap<String, String>>?
//                            val toResultList = getResult?.map { it.toOrderOmInfo() }
//                            if (!toResultList.isNullOrEmpty()){
//                                Log.d("가지고온값", toResultList.toString())
//                                viewStateChanged(CustomDialogOrderViewState.OrderOmInfo)
//                            }else{
//
//                            }
//                        }else{
//
//                        }
//                    }else{
//
//                    }
//                }
//        }
//    }

}