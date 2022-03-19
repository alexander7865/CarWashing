package com.mod_int.carwash.ui.owner.findwasher

import android.app.Application
import android.util.Log
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



    // 데이터가 덥어씌기가 되어 수정했습니다.
    fun getWasherMember() {
        ioScope {
            firebaseRepository.getFirebaseFireStore().collection("WasherMember").document("info")
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        if (it.result.exists()) {
                            val getResult: ArrayList<HashMap<String, String>>? =
                                it.result.get("list") as ArrayList<HashMap<String, String>>?
                            val toResultList = getResult?.map { it.toWasherInfo() }

                            Log.d("결과값", "$toResultList")
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
}

sealed class OwnerFindWasherViewState : ViewState {
    data class GetWasherMember(val list: List<WasherInfo>) : OwnerFindWasherViewState()
}

