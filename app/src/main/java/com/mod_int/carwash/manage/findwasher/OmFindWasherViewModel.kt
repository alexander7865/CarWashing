package com.mod_int.carwash.manage.findwasher

import android.app.Application
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.base.ViewState
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ext.ioScope
import com.mod_int.carwash.model.WasherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OmFindWasherViewModel
@Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
    ) :
    BaseViewModel(app) {
    // 데이터가 덥어씌기가 안되게 수정했습니다.
    fun getWasherMember() {
        // hashMap 형태로 가지고오는 패턴
        ioScope {
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

            //가입된 워셔데이터를 가지고와서 리사이클러뷰에 뿌려주려합니다 잘 안되네요
            val wmData : MutableList<WasherInfo> = mutableListOf()
            firebaseRepository.getFirebaseFireStore().collection("WasherMember")
                .addSnapshotListener { querySnapshot, _ ->
                    for (snapshot in querySnapshot!!.documents) {
                        wmData.clear()
                        val wmInfo = snapshot.toObject(WasherInfo::class.java)
                        wmData.add(wmInfo!!)
                        viewStateChanged(OmFindWasherViewState.GetWasherInfo(wmData))
                    }
                }

        }
    }
}

sealed class OmFindWasherViewState : ViewState {
    data class GetWasherInfo(val wmInfo: MutableList<WasherInfo>) : OmFindWasherViewState()
}

