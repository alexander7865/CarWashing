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

    fun getHeadWasher() {
        ioScope {
            firebaseRepository.getFirebaseFireStore().collection("Washer").document("HeadWasher")
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        if (it.result.exists()) {
                            val getResult: ArrayList<HashMap<String, String>>? =
                                it.result.get("list") as ArrayList<HashMap<String, String>>?
                            val toResultList = getResult?.map { it.toWasherInfo() }
                            if (!toResultList.isNullOrEmpty()) {
                                viewStateChanged(
                                    OwnerFindWasherViewState.GetHeadWashers(
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
    data class GetHeadWashers(val list: List<WasherInfo>) : OwnerFindWasherViewState()
}

