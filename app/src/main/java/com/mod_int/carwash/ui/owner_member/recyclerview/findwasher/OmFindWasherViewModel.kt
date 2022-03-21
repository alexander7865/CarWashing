package com.mod_int.carwash.ui.owner_member.recyclerview.findwasher

import android.app.Application
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.base.ViewState
import com.mod_int.carwash.model.WasherInfo
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ext.ioScope
import com.mod_int.carwash.model.toWasherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OmFindWasherViewModel
@Inject constructor(app: Application, private val firebaseRepository: FirebaseRepository) :
    BaseViewModel(app) {

    // 데이터가 덥어씌기가 안되게 수정했습니다.
    fun getWasherMember() {
        // hasMap 형태로 가지고오는 패턴
        ioScope {
            firebaseRepository.getFirebaseFireStore().collection("WasherMember")
                .document("User")
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        if (it.result.exists()) {
                            val getResult: ArrayList<HashMap<String, String>>? =
                                it.result.get("list") as ArrayList<HashMap<String, String>>?
                            val toResultList = getResult?.map { it.toWasherInfo() }
                            if (!toResultList.isNullOrEmpty()) {
                                viewStateChanged(
                                    OmFindWasherViewState.GetWasherMember(
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

}

sealed class OmFindWasherViewState : ViewState {
    data class GetWasherMember(val list: List<WasherInfo>) : OmFindWasherViewState()
}

