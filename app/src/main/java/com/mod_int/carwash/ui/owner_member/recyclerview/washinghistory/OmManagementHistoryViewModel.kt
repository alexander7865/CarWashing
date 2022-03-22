package com.mod_int.carwash.ui.owner_member.recyclerview.washinghistory

import android.app.Application
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.base.ViewState
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ext.ioScope
import com.mod_int.carwash.model.HistoryInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OmManagementHistoryViewModel
@Inject constructor(app: Application, private val firebaseRepository: FirebaseRepository) :
    BaseViewModel(app) {

    fun getFinishedWashing() {

        ioScope {
        firebaseRepository.getFirebaseFireStore().collection("PickupMember")
            .get()
            .addOnSuccessListener {
                it.documents.forEach{ _ ->
                    val items = mutableListOf<HistoryInfo>()
                    for (document in items) {
                        Log.d("히스토리", "$items")

                    }
                }
            }
        }
    }
}

sealed class FinishedWashingViewState : ViewState {
    data class FinishedWashing(val list: List<HistoryInfo>) : FinishedWashingViewState()
}


