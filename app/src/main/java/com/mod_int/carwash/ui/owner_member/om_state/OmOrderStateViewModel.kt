package com.mod_int.carwash.ui.owner_member.om_state

import android.app.Application
import android.os.Parcelable
import android.util.Log
import androidx.databinding.ObservableField
import androidx.fragment.app.setFragmentResultListener
import com.google.firebase.firestore.DocumentSnapshot
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ext.ioScope
import com.mod_int.carwash.model.PriceItem
import com.mod_int.carwash.ui.dialog.order.CustomDialogOrderViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class OmOrderStateViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {

    var omDate = ObservableField("")
    var carInfo = ObservableField("")
    var washingType = ObservableField("")
    var washingAmount = ObservableField("")
    var pickupDeliveryPrice = ObservableField("")
    var totalPrice = ObservableField("")
    var wmAccountNameInfo = ObservableField("")
    var wmBankNameInfo = ObservableField("")
    var wmBankAccountNrInfo = ObservableField("")
    var wmPhoneNumberInfo = ObservableField("")
    var washingState1 = ObservableField("")
    var washingState2 = ObservableField("")
    var washingState3 = ObservableField("")
    var washingState4 = ObservableField("")
    var companyName = ObservableField("")

    fun orderStateInfo() {
        ioScope {
            val email = firebaseRepository.getFirebaseAuth().currentUser!!.email
            firebaseRepository.getFirebaseFireStore().collection("OwnerMember")
                .document("$email")
                .get()
                .addOnSuccessListener {
                    val amount = it.get("orderAmount")
                    val pickupDeliCost = it.get("pickupDeliCost")
                    val carInfo1 = it.get("carNumber")
                    val carInfo2 = it.get("carBrand")
                    val carInfo3 = it.get("carModel")
                    val carInfo4 = it.get("carKinds")
                    val carInfo5 = it.get("carColor")
                    val carInfo6 = it.get("carSize")
                    val totalAmount = "${(amount.toString().toInt()) + (pickupDeliCost.toString().toInt())}"
                    omDate.set("${it.get("orderDate")}")
                    washingType.set("${it.get("orderType")}")
                    carInfo.set("$carInfo1 $carInfo2 $carInfo3 $carInfo4 $carInfo5 $carInfo6")
                    washingAmount.set("$amount")
                    pickupDeliveryPrice.set("$pickupDeliCost")
                    totalPrice.set(totalAmount)
                }
        }
    }

    fun wmBankInfo() {
        ioScope {
            firebaseRepository.getFirebaseFireStore().collection("WasherMember")
                .addSnapshotListener { querySnapshot, _ ->
                    val bankInfo = mutableListOf<WmBankInfo>()
                    for (info in querySnapshot!!.documentChanges) {
                        var dc = info.document.toObject(WmBankInfo::class.java)
                        bankInfo.add(dc)
                    }

                    val getBankItem =
                        bankInfo.firstOrNull { it.wmCompanyName == companyName.get() }
                    wmAccountNameInfo.set("${getBankItem?.wmAccountName}")
                    wmBankNameInfo.set("${getBankItem?.wmBankName}")
                    wmBankAccountNrInfo.set("${getBankItem?.wmAccountNr}")
                    wmPhoneNumberInfo.set("${getBankItem?.phoneNumber}")
                    viewStateChanged(OmOrderStateViewState.WasherMemberPhoneNr)

                }
        }
    }



    fun routeHistory() {
        viewStateChanged(OmOrderStateViewState.RouteHistory)
    }

//    fun washerMemberPhoneNr() {
//        viewStateChanged(OmOrderStateViewState.WasherMemberPhoneNr)
//    }

}

@Parcelize
data class WmBankInfo(
    var wmCompanyName : String = "",
    var wmAccountName : String = "",
    var wmBankName : String = "",
    var wmAccountNr : String ="",
    var phoneNumber : String ="",
) : Parcelable


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
