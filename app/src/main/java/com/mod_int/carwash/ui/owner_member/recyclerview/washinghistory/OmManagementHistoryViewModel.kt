package com.mod_int.carwash.ui.owner_member.recyclerview.washinghistory

import android.app.Application
import android.util.Log
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.model.HistoryInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OmManagementHistoryViewModel
@Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository) :
    BaseViewModel(app){

        fun getFinishedOrder() {
            val user = firebaseRepository.getFirebaseAuth().currentUser!!.email
            firebaseRepository.getFirebaseFireStore().collection("OwnerMember")
                .document("$user")
                .get()
                .addOnSuccessListener {document ->
                    if(document.exists()) {
                        val list = HistoryInfo(
                            email = document.get("email") as String,
                            phoneNumber = document.get("phoneNumber") as String,
                            type = document.get("type") as String
                        )
                        val finishedList = mutableListOf<HistoryInfo>().apply {
                            add(list)
                        }
                        if (!finishedList.isNullOrEmpty()) {
                            OmManagementHistoryViewState.GetFinishedOrder(
                                finishedList
                            )
                        }
                        Log.d("리스트값", finishedList.toString())
                    }
                }

        }

}
