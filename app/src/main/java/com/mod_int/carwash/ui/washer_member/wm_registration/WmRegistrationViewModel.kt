package com.mod_int.carwash.ui.washer_member.wm_registration

import android.app.Application
import android.media.browse.MediaBrowser
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.SetOptions
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ext.ioScope
import com.mod_int.carwash.ext.showToast
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
    val wmCheck1 = ObservableField("")
    val wmCheck2 = ObservableField("")
    val wmCheck3 = ObservableField("")

    fun wmSaveInfo() {
        ioScope {
            val wmAccountNameCheck = async {wmAccountNameCheck()}
            val wmBankNameCheck = async {wmBankNameCheck()}
            val wmAccountNrCheck = async {wmAccountNrCheck()}
            val wmPhoneNrCheck = async {wmPhoneNrCheck()}
            val wmLocationCheck = async {wmLocationCheck()}
            val wmCheck1Click = async {wmCheck1()}
            val wmCheck2Click = async {wmCheck2()}
            val wmCheck3Click = async {wmCheck3()}
            checkInfo(
                wmAccountNameCheck.await(),
                wmBankNameCheck.await(),
                wmAccountNrCheck.await(),
                wmPhoneNrCheck.await(),
                wmLocationCheck.await(),
                wmCheck1Click.await(),
                wmCheck2Click.await(),
                wmCheck3Click.await(),

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

            } ?:viewStateChanged(WmRegistrationViewState.Msg(message = "정보를 모두 입력하세요!"))
        }
    }


    private fun checkInfo(
        wmAccountNameCheck: Boolean,
        wmBankNameCheck: Boolean,
        wmAccountNrCheck: Boolean,
        wmPhoneNrCheck: Boolean,
        wmLocationCheck: Boolean,
        wmCheck1Click : Boolean,
        wmCheck2Click : Boolean,
        wmCheck3Click : Boolean,

    ): WmInfo? {
        return if (wmAccountNameCheck && wmBankNameCheck && wmAccountNrCheck
            && wmPhoneNrCheck && wmLocationCheck && (wmCheck1Click || wmCheck2Click || wmCheck3Click)
        ) {
            WmInfo(
                wmAccountName.value!!,
                wmBankName.get()!!,
                wmAccountNr.value!!,
                wmPhoneNr.value!!,
                wmLocation.value!!,
                wmCheck1.get()!!,
                wmCheck2.get()!!,
                wmCheck3.get()!!,
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

    //기존에 가입된 폰넘버 가지고 오면됨
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

    private fun wmCheck1(): Boolean {
        return when {
            wmCheck1.get()?.isEmpty() == true -> {
                false
            }
            else -> {
                true
            }
        }
    }
    private fun wmCheck2(): Boolean {
        return when {
            wmCheck2.get()?.isEmpty() == true -> {
                false
            }
            else -> {
                true
            }
        }
    }
    private fun wmCheck3(): Boolean {
        return when {
            wmCheck3.get()?.isEmpty() == true -> {
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
        var wmCheck1: String = "",
        var wmCheck2: String = "",
        var wmCheck3: String = "",

    )
}
