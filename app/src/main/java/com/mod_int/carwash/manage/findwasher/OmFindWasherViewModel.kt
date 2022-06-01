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

    private val washerInfo = mutableListOf<WasherInfo>()
    // 체크하여 비어있지 않았을때만 구현되도록 수정
    fun getWasherMember() {
        ioScope {
            firebaseRepository.getFirebaseFireStore()
                .collection("WasherMember")
                .addSnapshotListener { querySnapshot, _ ->
                    for (info in querySnapshot!!.documentChanges) {
                        val document = info.document.toObject(WasherInfo::class.java)
                        washerInfo.add(document)
                    }
                    viewStateChanged(OmFindWasherViewState.GetWasherMember(washerInfo))
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
}











