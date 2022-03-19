package com.mod_int.carwash.ui.register

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ext.checkRegister
import com.mod_int.carwash.ext.ioScope
import com.mod_int.carwash.ui.login.LoginViewState
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
                checkPasswordOk.await()
            )?.let { person ->
                firebaseRepository.checkRegister(
                    person.email,
                    person.password,
                    typeObservableField.get()!!
                ) { isSuccess ->

                    viewStateChanged(LoginViewState.EnableInput(false))
                    if (isSuccess) {
                        when (typeObservableField.get()) {
                            "ownerMember" -> {
                                viewStateChanged(RegisterViewState.RouteOwner)
                            }
                            "washerMember" -> {
                                viewStateChanged(RegisterViewState.RouteWasher)
                            }

                            "pickupMember" -> {
                                viewStateChanged(RegisterViewState.RoutePickupManager)
                            }
                            else -> {
                                viewStateChanged(RegisterViewState.Error("회원가입이 실패하였습니다."))
                            }
                        }
                    } else {
                        viewStateChanged(RegisterViewState.Error("회원가입이 실패하였습니다."))
                    }
                }
            }
        }
    }

    //LoginViewState -> RegisterViewState 바꿨습니다
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
                viewStateChanged(RegisterViewState.Error("이메일이 형식에 맞지 않습니다.\n재작성 해주세요!"))
                false
            }

            inputEmailLiveData.value.isNullOrEmpty() -> {
                viewStateChanged(RegisterViewState.EnableInput(false))
                viewStateChanged(RegisterViewState.Error("이메일과 비밀번호를 입력해 주세요."))
                false
            }
            else -> true
        }
    }

    private fun checkPassword(): Boolean {
        return when {
            inputPasswordLiveData.value.isNullOrEmpty() -> {
                viewStateChanged(RegisterViewState.EnableInput(false))
                viewStateChanged(RegisterViewState.Error("이메일과 비밀번호를 입력해 주세요."))
                false
            }

            inputPasswordLiveData.value!!.length <= 7 -> {
                viewStateChanged(RegisterViewState.EnableInput(false))
                viewStateChanged(RegisterViewState.Error("비밀번호는 8자리 이상으로 입력하셔야 합니다.재작성 해주세요!"))
                false
            }
            else -> true
        }
    }

    private fun checkPasswordOk(): Boolean {
        return when {
            inputPasswordLiveData.value != inputPasswordLiveData.value -> {
                viewStateChanged(RegisterViewState.EnableInput(false))
                viewStateChanged(RegisterViewState.Error("비밀번호 확인을 올바르게 입력해 주세요."))
                false
            }
            else -> true
        }
    }

    private fun checkEmailRegister(): Boolean {
        var emailValidation =
            "^[A-Za-z0-9]([._-]?[A-Za-z0-9])*@[A-Za-z0-9]([._-]?[A-Za-z0-9])*\\.[A-Za-z]{2,}$"
        var email = inputEmailLiveData.value?.trim() //공백제거
        val p = Pattern.matches(emailValidation, email) // 이메일이 정규형식과 맞는지 확인하는 선언
        return if (p) {
            //이메일 형태가 정상일 경우
            Log.d("입력이메일", "정규 형식입니다")
            true
        } else {
            Log.d("입력이메일", "정규 형식이 아닙니다")
            false
        }
    }
    data class Person(
        val email: String,
        val password: String
    )


}