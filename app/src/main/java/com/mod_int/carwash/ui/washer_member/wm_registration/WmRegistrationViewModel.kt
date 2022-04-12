package com.mod_int.carwash.ui.washer_member.wm_registration

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.SetOptions
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ext.ioScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class WmRegistrationViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {
    val wmAccountName = MutableLiveData("")
    val wmBankName = ObservableField("")
    val wmAccountNr = MutableLiveData("")
    val wmPhoneNr = MutableLiveData("010-1111-1111")
    val wmLocation = MutableLiveData("서울시 논현동 111-11 1층 주차장")

    fun wmSaveInfo() {
        ioScope {
            val wmAccountNameCheck = async { wmAccountNameCheck() }
            val wmBankNameCheck = async { wmBankNameCheck() }
            val wmAccountNrCheck = async { wmAccountNrCheck() }
            val wmPhoneNrCheck = async { wmPhoneNrCheck() }
            val wmLocationCheck = async { wmLocationCheck() }
            checkInfo(
                wmAccountNameCheck.await(),
                wmBankNameCheck.await(),
                wmAccountNrCheck.await(),
                wmPhoneNrCheck.await(),
                wmLocationCheck.await(),
            )?.let { wmInfo ->
                val email = firebaseRepository.getFirebaseAuth().currentUser!!.email
                firebaseRepository.getFirebaseFireStore().collection("WasherMember")
                    .document("$email")
                    .set(wmInfo, SetOptions.merge()).addOnCompleteListener {
                        if (it.isSuccessful) {
                            viewStateChanged(WmRegistrationViewState.EnableInput(false))
                            viewStateChanged(WmRegistrationViewState.WmSaveInfo)

                        } else {
                            viewStateChanged(WmRegistrationViewState.EnableInput(true))
                            viewStateChanged(WmRegistrationViewState.Msg(message = "정보를 모두 입력하세요!"))

                        }
                    }

            }
        }
    }

    private fun checkInfo(
        wmAccountNameCheck: Boolean,
        wmBankNameCheck: Boolean,
        wmAccountNrCheck: Boolean,
        wmPhoneNrCheck: Boolean,
        wmLocationCheck: Boolean,
    ): WmInfo? {
        return if (wmAccountNameCheck && wmBankNameCheck && wmAccountNrCheck
            && wmPhoneNrCheck && wmLocationCheck
        ) {
            WmInfo(
                wmAccountName.value!!,
                wmBankName.get()!!,
                wmAccountNr.value!!,
                wmPhoneNr.value!!,
                wmLocation.value!!,
            )

        } else {
            null
        }
    }

    private fun wmAccountNameCheck(): Boolean {
        return when {
            wmAccountName.value?.isEmpty() == true -> {
                viewStateChanged(WmRegistrationViewState.Msg(message = "정보를 모두 입력하세요!"))
                false
            }
            else -> {
                true
            }
        }
    }

    private fun wmBankNameCheck(): Boolean {
        return when {
            wmBankName.get()?.isEmpty() == true -> {
                viewStateChanged(WmRegistrationViewState.Msg(message = "정보를 모두 입력하세요!"))
                false
            }
            else -> {
                true
            }
        }
    }

    private fun wmAccountNrCheck(): Boolean {
        return when {
            wmAccountNr.value?.isEmpty() == true -> {
                viewStateChanged(WmRegistrationViewState.Msg(message = "정보를 모두 입력하세요!"))
                false
            }
            else ->  {
                true
            }
        }
    }

    private fun wmPhoneNrCheck(): Boolean {
        return when {
            wmPhoneNr.value?.isEmpty() == true -> {
                viewStateChanged(WmRegistrationViewState.Msg(message = "정보를 모두 입력하세요!"))
                false
            }
            else -> {
                true
            }
        }
    }

    private fun wmLocationCheck(): Boolean {
        return when {
            wmLocation.value?.isEmpty() == true -> {
                viewStateChanged(WmRegistrationViewState.Msg(message = "정보를 모두 입력하세요!"))
                false
            }
            else -> {
                true
            }
        }
    }

    data class WmInfo(
        var accountName: String = "",
        var bankName: String = "",
        var accountNr: String = "",
        var wmPhoneNr: String = "",
        var wmLocation: String = "",
    )
}