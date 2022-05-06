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
    val wmLocation = MutableLiveData("서울시 논현동 111-11 1층 주차장")
    val wmCompanyName = MutableLiveData("")
    val wmCheck1 = ObservableField("")
    val wmCheck2 = ObservableField("")
    val wmCheck3 = ObservableField("")

    fun wmSaveInfo() {
        ioScope {
            val wmAccountNameCheck = async {wmAccountNameCheck()}
            val wmBankNameCheck = async {wmBankNameCheck()}
            val wmAccountNrCheck = async {wmAccountNrCheck()}
            val wmLocationCheck = async {wmLocationCheck()}
            val wmCompanyNameCheck = async {wmCompanyNameCheck()}
            val wmCheck1Click = async {wmCheck1()}
            val wmCheck2Click = async {wmCheck2()}
            val wmCheck3Click = async {wmCheck3()}
            checkInfo(
                wmAccountNameCheck.await(),
                wmBankNameCheck.await(),
                wmAccountNrCheck.await(),
                wmLocationCheck.await(),
                wmCompanyNameCheck.await(),
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
        wmLocationCheck: Boolean,
        wmCompanyNameCheck: Boolean,
        wmCheck1Click : Boolean,
        wmCheck2Click : Boolean,
        wmCheck3Click : Boolean,

    ): WmInfo? {
        return if (wmAccountNameCheck && wmBankNameCheck && wmAccountNrCheck
            && wmLocationCheck && wmCompanyNameCheck && (wmCheck1Click || wmCheck2Click || wmCheck3Click)
        ) {
            WmInfo(
                wmAccountName.value!!,
                wmBankName.get()!!,
                wmAccountNr.value!!,
                wmLocation.value!!,
                wmCompanyName.value!!,
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

    private fun wmCompanyNameCheck(): Boolean {
        return when {
            wmCompanyName.value?.isEmpty() == true -> {
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
        var wmAccountName: String = "",
        var wmBankName: String = "",
        var wmAccountNr: String = "",
        var wmLocation: String = "",
        var wmCompanyName: String = "",
        var wmCheck1: String = "",
        var wmCheck2: String = "",
        var wmCheck3: String = "",
        var wmCount :String = "",
        var wmPoint : String ="",

    )
}
