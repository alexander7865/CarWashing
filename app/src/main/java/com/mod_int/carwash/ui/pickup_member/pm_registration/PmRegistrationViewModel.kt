package com.mod_int.carwash.ui.pickup_member.pm_registration

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.SetOptions
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ext.ioScope
import com.mod_int.carwash.ui.washer_member.wm_registration.WmRegistrationViewModel
import com.mod_int.carwash.ui.washer_member.wm_registration.WmRegistrationViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class PmRegistrationViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {
    val pmAccountName = MutableLiveData("")
    val pmBankName = ObservableField("")
    val pmAccountNr = MutableLiveData("")
    val pmPhoneNr = MutableLiveData("010-1111-1111")
    val pmLocation = MutableLiveData("서울시 논현동 111-11 1층 주차장")

    fun pmSaveInfo() {
        ioScope {
            val pmAccountNameCheck = async { pmAccountNameCheck() }
            val pmBankNameCheck = async { pmBankNameCheck() }
            val pmAccountNrCheck = async { pmAccountNrCheck() }
            val pmPhoneNrCheck = async { pmPhoneNrCheck() }
            val pmLocationCheck = async { pmLocationCheck() }
            checkInfo(
                pmAccountNameCheck.await(),
                pmBankNameCheck.await(),
                pmAccountNrCheck.await(),
                pmPhoneNrCheck.await(),
                pmLocationCheck.await(),
            )?.let { pmInfo ->
                val email = firebaseRepository.getFirebaseAuth().currentUser!!.email
                firebaseRepository.getFirebaseFireStore().collection("PickupMember")
                    .document("$email")
                    .set(pmInfo, SetOptions.merge()).addOnCompleteListener {
                        if (it.isSuccessful) {
                            viewStateChanged(PmRegistrationViewState.EnableInput(false))
                            viewStateChanged(PmRegistrationViewState.PmSaveInfo)

                        } else {
                            viewStateChanged(PmRegistrationViewState.EnableInput(true))
                            viewStateChanged(PmRegistrationViewState.Msg(message = "정보를 모두 입력하세요!"))

                        }
                    }

            }
        }
    }

    private fun checkInfo(
        pmAccountNameCheck: Boolean,
        pmBankNameCheck: Boolean,
        pmAccountNrCheck: Boolean,
        pmPhoneNrCheck: Boolean,
        pmLocationCheck: Boolean,
    ): PmInfo? {
        return if (pmAccountNameCheck && pmBankNameCheck && pmAccountNrCheck
            && pmPhoneNrCheck && pmLocationCheck
        ) {
            PmInfo(
                pmAccountName.value!!,
                pmBankName.get()!!,
                pmAccountNr.value!!,
                pmPhoneNr.value!!,
                pmLocation.value!!,
            )

        } else {
            null
        }
    }

    private fun pmAccountNameCheck(): Boolean {
        return when {
            pmAccountName.value?.isEmpty() == true -> {
                viewStateChanged(PmRegistrationViewState.Msg(message = "정보를 모두 입력하세요!"))
                false
            }
            else -> {
                true
            }
        }
    }

    private fun pmBankNameCheck(): Boolean {
        return when {
            pmBankName.get()?.isEmpty() == true -> {
                viewStateChanged(PmRegistrationViewState.Msg(message = "정보를 모두 입력하세요!"))
                false
            }
            else -> {
                true
            }
        }
    }

    private fun pmAccountNrCheck(): Boolean {
        return when {
            pmAccountNr.value?.isEmpty() == true -> {
                viewStateChanged(PmRegistrationViewState.Msg(message = "정보를 모두 입력하세요!"))
                false
            }
            else ->  {
                true
            }
        }
    }

    private fun pmPhoneNrCheck(): Boolean {
        return when {
            pmPhoneNr.value?.isEmpty() == true -> {
                viewStateChanged(PmRegistrationViewState.Msg(message = "정보를 모두 입력하세요!"))
                false
            }
            else -> {
                true
            }
        }
    }

    private fun pmLocationCheck(): Boolean {
        return when {
            pmLocation.value?.isEmpty() == true -> {
                viewStateChanged(PmRegistrationViewState.Msg(message = "정보를 모두 입력하세요!"))
                false
            }
            else -> {
                true
            }
        }
    }

    data class PmInfo(
        var pmAccountName: String = "",
        var pmBankName: String = "",
        var pmAccountNr: String = "",
        var pmPhoneNr: String = "",
        var pmLocation: String = "",
    )

}