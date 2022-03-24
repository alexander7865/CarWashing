package com.mod_int.carwash.ui.owner_member.recyclerview.washinghistory

import android.app.Application
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.base.ViewState
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ext.ioScope
import com.mod_int.carwash.model.HistoryInfo
import com.mod_int.carwash.ui.owner_member.recyclerview.findwasher.OmFindWasherViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OmManagementHistoryViewModel
@Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository) :
    BaseViewModel(app) {
    private val itemList = mutableListOf<HistoryInfo>()

    //뷰모델 적용하니 자꾸 터지는 현상이 일어나네요
    fun getFinishedWashing() {
        ioScope {
            firebaseRepository.getFirebaseFireStore().collection("WasherMember")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        itemList.clear()
                        val list = HistoryInfo(
                            email = document.get("email") as String,
                            phoneNumber = document.get("phoneNumber") as String,
                            type = document.get("type") as String,
//                        carBrand = document.get("carBrand") as String,
//                        carModel = document.get("carModel") as String,
//                        carKinds = document.get("carKinds") as String,
//                        carColor = document.get("carColor") as String
                        )
                        val testList = mutableListOf<HistoryInfo>().apply {
                            add(list)
                        }
                        if (!testList.isNullOrEmpty()) {
                            viewStateChanged(
                                FinishedWashingViewState.FinishedWashing(
                                    testList
                                )
                            )
                        }
                    }
                }

        }
    }
}

sealed class FinishedWashingViewState : ViewState {
    data class FinishedWashing(val list: MutableList<HistoryInfo>) : FinishedWashingViewState()
}


