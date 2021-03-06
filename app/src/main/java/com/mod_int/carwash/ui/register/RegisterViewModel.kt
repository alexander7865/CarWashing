package com.mod_int.carwash.ui.register

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ext.checkRegister
import com.mod_int.carwash.ext.ioScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {

    val inputEmailLiveData = MutableLiveData<String>()
    val inputPasswordLiveData = MutableLiveData<String>()
    val inputPasswordOkLiveData = MutableLiveData<String>()

    val typeObservableField = ObservableField<String>()


    fun register() {
        ioScope {
            val checkEmail = async { checkEmail() }
            val checkPassword = async { checkPassword() }
            val checkPasswordOk = async { checkPasswordOk() }
            checkUser(
                checkEmail.await(),
                checkPassword.await(),
                checkPasswordOk.await(),
            )?.let { person ->
                firebaseRepository.checkRegister(
                    person.email,
                    person.password,
                    typeObservableField.get()!!
                ) { isSuccess ->
                    viewStateChanged(RegisterViewState.EnableInput(false))
                    if (isSuccess) {
                        when (typeObservableField.get()) {
                            "ownerMember" -> {
                                viewStateChanged(RegisterViewState.RouteOwnerMember)

                            }
                            "washerMember" -> {
                                viewStateChanged(RegisterViewState.RouteWasherMember)
                            }

                            "pickupMember" -> {
                                viewStateChanged(RegisterViewState.RoutePickupMember)
                            }
                            else -> {
                                viewStateChanged(RegisterViewState.EnableInput(true))
                                viewStateChanged(RegisterViewState.Error("??????????????? ?????????????????????."))
                            }
                        }
                    } else {
                        viewStateChanged(RegisterViewState.EnableInput(true))
                        viewStateChanged(RegisterViewState.Error("??????????????? ?????????????????????."))
                    }
                }
            }
        }
    }

    //LoginViewState -> RegisterViewState ???????????????
    fun cancel() {
        viewStateChanged(RegisterViewState.Cancel)
    }

    private fun checkUser(
        checkEmail: Boolean,
        checkPassword: Boolean,
        checkPasswordOk: Boolean
    ): Person? {
        return if (checkEmail && checkPassword && checkPasswordOk) {
            Person(inputEmailLiveData.value!!, inputPasswordLiveData.value!!)
        } else {
            null
        }
    }

    private fun checkEmail(): Boolean {
        return when {
            !checkEmailRegister() -> {
                viewStateChanged(RegisterViewState.EnableInput(false))
                viewStateChanged(RegisterViewState.Error("???????????? ????????? ?????? ????????????.\n????????? ????????????!"))
                false
            }
            inputEmailLiveData.value.isNullOrEmpty() -> {
                viewStateChanged(RegisterViewState.EnableInput(false))
                viewStateChanged(RegisterViewState.Error("???????????? ??????????????? ????????? ?????????."))
                false
            }
            else -> true
        }
    }

    private fun checkPassword(): Boolean {
        return when {
            inputPasswordLiveData.value.isNullOrEmpty() -> {
                viewStateChanged(RegisterViewState.EnableInput(false))
                viewStateChanged(RegisterViewState.Error("???????????? ??????????????? ????????? ?????????."))
                false
            }

            inputPasswordLiveData.value!!.length <= 7 -> {
                viewStateChanged(RegisterViewState.EnableInput(false))
                viewStateChanged(RegisterViewState.Error("??????????????? 8?????? ???????????? ??????????????? ?????????.????????? ????????????!"))
                false
            }
            else -> true
        }
    }

    private fun checkPasswordOk(): Boolean {
        return when {
            inputPasswordLiveData.value != inputPasswordLiveData.value -> {
                viewStateChanged(RegisterViewState.EnableInput(false))
                viewStateChanged(RegisterViewState.Error("???????????? ????????? ???????????? ????????? ?????????."))
                false
            }
            else -> true
        }
    }

    private fun checkEmailRegister(): Boolean {
        var emailValidation =
            "^[A-Za-z0-9]([._-]?[A-Za-z0-9])*@[A-Za-z0-9]([._-]?[A-Za-z0-9])*\\.[A-Za-z]{2,}$"
        var email = inputEmailLiveData.value?.trim() //????????????
        val p = Pattern.matches(emailValidation, email) // ???????????? ??????????????? ????????? ???????????? ??????
        return if (p) {
            //????????? ????????? ????????? ??????
            Log.d("???????????????", "?????? ???????????????")
            true
        } else {
            Log.d("???????????????", "?????? ????????? ????????????")
            false
        }
    }
    data class Person(
        val email: String,
        val password: String
    )
}