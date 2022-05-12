package com.mod_int.carwash.manage.findwasher

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ext.ioScope
import com.mod_int.carwash.model.PriceItem
import com.mod_int.carwash.model.WasherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OmFindWasherViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {

    //    var wmLocation = ObservableField("")
    // 체크하여 비어있지 않았을때만 구현되도록 수정
    fun getWasherMember() {


        //다이얼로그 창에 해당업체의 비용이 나오게 구현하고 싶은데 어떻게 해야 하는데 잘안되네요
        ioScope {
            firebaseRepository.getFirebaseFireStore()
                .collection("WasherMember")
                .addSnapshotListener { querySnapshot, _ ->
                    for (info in querySnapshot!!.documentChanges) {
                        var document = info.document.toObject(WasherInfo::class.java)
                        val washerInfo = mutableListOf<WasherInfo>().apply { add(document) }
                        viewStateChanged(OmFindWasherViewState.GetWasherMember(washerInfo))
                    }
                }

        }
    }

    fun getWasher(item: String) {
        ioScope {
            firebaseRepository.getFirebaseFireStore().collection("WasherMember")
                .addSnapshotListener { querySnapshot, _ ->

                    val priceList = mutableListOf<PriceItem>()

                    for (info in querySnapshot!!.documentChanges) {
                        var document = info.document.toObject(PriceItem::class.java)
                        priceList.add(document)

                    }
                    val getPriceItem = priceList.firstOrNull { it.wmCompanyName == item }

                    getPriceItem?.let { item ->
                        viewStateChanged(OmFindWasherViewState.GetPriceItem(item))
                    }
                }
        }
    }


    //아래코드는 USER 로부터 데이터를 입력 받았을시만 체크가 가능함
//    private fun wmDataInfoCheck(
//        wmLocationCheck : Boolean,
//        wmCompanyNameCheck : Boolean,
//        wmCountCheck : Boolean,
//        wmPointCheck : Boolean,
//        deliveryCostCheck : Boolean,
//        polishCostCheck : Boolean,
//        washerIntroduceCheck: Boolean,
//    ) : WasherInfo? {
//        return if(
//            wmLocationCheck
//            && wmCompanyNameCheck
//            && wmCountCheck
//            && wmPointCheck
//            && deliveryCostCheck
//            && polishCostCheck
//            && washerIntroduceCheck
//        ) {
//            WasherInfo(
//                wmLocation.get()!!,
//                wmCompanyName.get()!!,
//                wmCount.get()!!,
//                wmPoint.get()!!,
//                deliveryCost.get()!!,
//                polishCost.get()!!,
//                washerIntroduce.get()!!,
//            )
//        } else {
//            null
//        }
//    }
//
//
//
//    private fun wmLocation(): Boolean {
//        return when {
//            wmLocation.get()?.isEmpty() == true -> {
//                false
//            }
//            else -> true
//        }
//    }
//
//    private fun wmCompanyName(): Boolean {
//        return when {
//            wmCompanyName.get()?.isEmpty() == true -> {
//                false
//            }
//            else -> true
//        }
//    }
//
//    private fun wmCount(): Boolean {
//        return when {
//            wmCount.get()?.isEmpty() == true -> {
//                false
//            }
//            else -> true
//        }
//    }
//
//    private fun wmPoint(): Boolean {
//        return when {
//            wmPoint.get()?.isEmpty() == true -> {
//                false
//            }
//            else -> true
//        }
//    }
//
//    private fun deliveryCost(): Boolean {
//        return when {
//            deliveryCost.get()?.isEmpty() == true -> {
//                false
//            }
//            else -> true
//        }
//    }
//
//    private fun polishCost(): Boolean {
//        return when {
//            polishCost.get()?.isEmpty() == true -> {
//                false
//            }
//            else -> true
//        }
//    }
//
//    private fun washerIntroduce(): Boolean {
//        return when {
//            washerIntroduce.get()?.isEmpty() == true -> {
//                false
//            }
//            else -> true
//        }
//    }


    // hashMap 형태로 가지고오는 패턴 오더 진행시 해쉬맵으로 바꾸고 지우고 할것임
    //        ioScope {
//            firebaseRepository.getFirebaseFireStore().collection("WasherMember")
//                .document("User")
//                .get()
//                .addOnCompleteListener {
//                    if (it.isSuccessful) {
//                        if (it.result.exists()) {
//                            val getResult: ArrayList<HashMap<String, String>>? =
//                                it.result.get("WmInfo") as ArrayList<HashMap<String, String>>?
//                            val toResultList = getResult?.map { it.toWasherInfo() }
//                            if (!toResultList.isNullOrEmpty()) {
//                                viewStateChanged(
//                                    OmFindWasherViewState.GetWasherMember(
//                                        toResultList
//                                    )
//                                )
//                            } else {
//                            }
//                        } else {
//                        }
//                    }
//                }
//
//        }
}











