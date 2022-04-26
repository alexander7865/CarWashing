package com.mod_int.carwash.manage.findwasher

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.base.ViewState
import com.mod_int.carwash.model.WasherInfo
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ext.ioScope
import com.mod_int.carwash.manage.history.OmManagementHistoryViewState
import com.mod_int.carwash.model.HistoryInfo
import com.mod_int.carwash.model.toWasherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class OmFindWasherViewModel
@Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository) :
    BaseViewModel(app) {

    // 체크하여 비어있지 않았을때만 진행 되게 수정할것
    fun getWasherMember() {
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

        ioScope {
            firebaseRepository.getFirebaseFireStore().collection("WasherMember")
                .addSnapshotListener { querySnapshot, _ ->
                    for (snapshot in querySnapshot!!.documents){
                        val type1 = snapshot.get("wmCheck1") as String
                        val type2 = snapshot.get("wmCheck2") as String
                        val type3 = snapshot.get("wmCheck3") as String
                        val list = WasherInfo(
                            companyLocation = "${snapshot.get("wmLocation")}",
                            companyName = "${snapshot.get("email")}",
                            count = "10",
                            point = "100",
                            deliPrice = "${snapshot.get("email")}",
                            policyPrice = "${snapshot.get("email")}",
                            washingType1 = type1,
                            washingType2 = type2,
                            washingType3 = type3,
                            inWashingCountryOfCar = "${snapshot.get("email")}",
                            outWashingCountryOfCar = "${snapshot.get("email")}",
                            inOutWashingCountryOfCar = "${snapshot.get("email")}",
                            inWashingCarSize = "${snapshot.get("email")}",
                            outWashingCarSize = "${snapshot.get("email")}",
                            inOutWashingCarSize = "${snapshot.get("email")}",
                            inWashingCost = "${snapshot.get("email")}",
                            outWashingCost = "${snapshot.get("email")}",
                            inOutWashingCost = "${snapshot.get("email")}",
                            inWashingTime = "${snapshot.get("email")}",
                            outWashingTime = "${snapshot.get("email")}",
                            inOutWashingTime = "${snapshot.get("email")}",
                            introduceText = "${snapshot.get("email")}",

                            )
                        val washerInfo = mutableListOf<WasherInfo>().apply {
                            add(list)

                        }
                        //뷰가 바뀌면 기존에 있던 리스트가 바뀌고 새로운 리스트로 변경되어야하나 기존리스트 + 새로운리스트가 함께나옴
                        viewStateChanged(OmFindWasherViewState.GetWasherMember(washerInfo))
                        onCleared()
                    }
                }


        }
    }





//    fun inputWashingType1Check() : Boolean {
//        return when {
//            washingType1.get()?.isEmpty() == true -> {
//                false
//            }
//            else -> true
//        }
//    }
//
//    fun inputWashingType2Check() : Boolean{
//        return when {
//            washingType2.get()?.isEmpty() == true -> {
//                false
//            }
//            else -> true
//        }
//    }
//
//    fun inputWashingType3Check() : Boolean{
//        return when {
//            washingType3.get()?.isEmpty() == true -> {
//                false
//            }
//            else -> true
//        }
//    }
//
}




