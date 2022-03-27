package com.mod_int.carwash.manage.history

import android.app.Application
import android.util.Log
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ext.ioScope
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
            ioScope {
                val user = firebaseRepository.getFirebaseAuth().currentUser!!.email
                firebaseRepository.getFirebaseFireStore().collection("OwnerMember")
                    .document("$user")
                    .get()
                    .addOnSuccessListener {document ->
                        if(document .exists()) {
                            val list = HistoryInfo(
                            date = "2022워 2월 2일",
                            washType = document.get("phoneNumber") as String,
                            carInfo = "${document.get("carNumber") as String} " +
                                    "${document.get("carBrand") as String} " +
                                    "${document.get("carModel") as String} " +
                                    "${document.get("carKinds") as String} " +
                                    "${document.get("carSize") as String} " +
                                    "${document.get("carColor") as String} "
                            )
                            val finishedList = mutableListOf<HistoryInfo>().apply {
                                add(list)
                            }
                            if (!finishedList.isNullOrEmpty()) {
                                viewStateChanged(
                                    OmManagementHistoryViewState.GetFinishedOrder(
                                        finishedList
                                    )
                                )
                            }
                            Log.d("리스트값", finishedList.toString())
                        }
                    }

            }
        }

}
