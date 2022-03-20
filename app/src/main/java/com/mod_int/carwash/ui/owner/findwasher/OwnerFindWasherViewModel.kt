package com.mod_int.carwash.ui.owner.findwasher

import android.app.Application
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.base.ViewState
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ext.ioScope
import com.mod_int.carwash.model.WasherInfo
import com.mod_int.carwash.model.toWasherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OwnerFindWasherViewModel
@Inject constructor(app: Application, private val firebaseRepository: FirebaseRepository) :
    BaseViewModel(app) {



    // 데이터가 덥어씌기가 안되게 수정했습니다.
    fun getWasherMember() {
        // hasMap 형태로 가지고오는 패턴
        ioScope {
            firebaseRepository.getFirebaseFireStore().collection("WasherMember").document("info")
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        if (it.result.exists()) {
                            val getResult: ArrayList<HashMap<String, String>>? =
                                it.result.get("list") as ArrayList<HashMap<String, String>>?
                            val toResultList = getResult?.map { it.toWasherInfo() }
                            if (!toResultList.isNullOrEmpty()) {
                                viewStateChanged(
                                    OwnerFindWasherViewState.GetWasherMember(
                                        toResultList
                                    )
                                )
                            } else {
                            }
                        } else {
                        }
                    }
                }

        }
    }


              // 방법2 다른형태로 document의 모든문서 가지고오기 이유는 리사이클러뷰 뿐만 아니라 다른화면에서도 데이터가
              // 필요하기 때문입니다. 구현하다 실패하였습니다.

//            var washerMember : ArrayList<WasherInfo> = arrayListOf()
//            firebaseRepository.getFirebaseFireStore().collection("WasherMember")
//                .addSnapshotListener {querySnapshot, firebaseFirestoreException ->
//                    washerMember.clear()
//                    for (snapshot in querySnapshot!!.documents) {
//                        var item = snapshot.toObject(WasherInfo::class.java)
//
//
//
//                    }
//                }
//        }


}

sealed class OwnerFindWasherViewState : ViewState {
    data class GetWasherMember(val list: List<WasherInfo>) : OwnerFindWasherViewState()
}

