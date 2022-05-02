package com.mod_int.carwash.manage.findwasher

import android.app.Application
import androidx.databinding.ObservableField
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ext.ioScope
import com.mod_int.carwash.ext.washingPriceList
import com.mod_int.carwash.model.PriceList
import com.mod_int.carwash.model.WasherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OmFindWasherViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
    ) : BaseViewModel(app) {

//    var wmLocation = ObservableField("")
//    var wmCompanyName = ObservableField("")
//    var wmCount= ObservableField("")
//    var wmPoint= ObservableField("")
//    var deliveryCost= ObservableField("")
//    var polishCost= ObservableField("")
//    var washerIntroduce= ObservableField("")
      var inOutsideWashingForeignCarL = ObservableField("미등록")


    // 체크하여 비어있지 않았을때만 구현되도록 수정
    fun getWasherMember() {

        //파이어스토어의 필드값명과 데이터클레스의 필드값명과 일치하면 데이터를 쉽게 가지고 올수 있음 쿼리를 사용하면 실시간으로 업데이트됨
        //문제는 필드값이 바뀌면 기존에 있던 리스트가 바뀌고 새로운 리스트로 변경되어야하나 기존리스트 + 새로운리스트가 함께나옴 해결방법은?
        //워셔의 정보가 전부 입력되지 않았을시에 아래 함수를 발동시키지 않게하고 싶은데 잘 안되네요
        ioScope {
            firebaseRepository.getFirebaseFireStore()
                .collection("WasherMember")
                .addSnapshotListener { querySnapshot, _ ->
                    for(info in querySnapshot!!.documentChanges){
                        var document = info.document.toObject(WasherInfo::class.java)
                        val washerInfo = mutableListOf<WasherInfo>().apply { add(document) }
                        viewStateChanged(OmFindWasherViewState.GetWasherMember(washerInfo))
                    }
                }

        }
    }

    //다이얼로그 창에 등록되게 하고 싶은데 어떻게 해야 하는데 잘안되네요
    fun getWashingPrice(){
        ioScope {
            firebaseRepository.getFirebaseFireStore()
                .collection("WasherMember")
                .addSnapshotListener { querySnapshot, _ ->
                    for(info in querySnapshot!!.documentChanges){
                        inOutsideWashingForeignCarL.set(info.document.get("inOutsideWashingForeignCarL") as String)
                        viewStateChanged(OmFindWasherViewState.GetWashingPrice)

                    }
                }

        }


        ioScope {
            firebaseRepository.washingPriceList(
               inOutsideWashingForeignCarL.get().toString()
            ) {

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


    // hashMap 형태로 가지고오는 패턴
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











