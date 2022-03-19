package com.mod_int.carwash.ui.login

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ext.ioScope
import com.mod_int.carwash.ext.loginAndGetUserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {

    val inputEmailLiveData = MutableLiveData<String>()
    val inputPasswordLiveData = MutableLiveData<String>()

    fun login() {
        ioScope {
            val checkEmail = async { checkEmail() }
            val checkPassword = async { checkPassword() }
            checkUser(checkEmail.await(), checkPassword.await())?.let { person ->
                firebaseRepository.loginAndGetUserInfo(
                    person.email,
                    person.password,
                ) { user ->
                    user?.let {
                        viewStateChanged(LoginViewState.EnableInput(false))
                        when (user.type) {
                            "ownerMember" -> {
                                viewStateChanged(LoginViewState.RouteOwner)
                            }
                            "washerMember" -> {
                                viewStateChanged(LoginViewState.RouteWasher)
                            }

                            "pickupMember" -> {
                                viewStateChanged(LoginViewState.RoutePickupManager)
                            }
                            else -> {
                                viewStateChanged(LoginViewState.Error("로그인이 실패하였습니다."))
                            }
                        }
                    }
                        ?: viewStateChanged(LoginViewState.Error("로그인이 실패하였습니다."))
                }
            }
            viewStateChanged(LoginViewState.EnableInput(true))
        }
    }

    fun cancel() {
        viewStateChanged(LoginViewState.Cancel)
    }


    private fun checkUser(
        checkEmail: Boolean,
        checkPassword: Boolean,
    ): Person? {
        return if (checkEmail && checkPassword) {
            Person(inputEmailLiveData.value!!, inputPasswordLiveData.value!!)

        } else {
            null
        }
    }

    private fun checkEmail(): Boolean {
        return when {
            inputEmailLiveData.value.isNullOrEmpty() -> {
                viewStateChanged(LoginViewState.Error("이메일과 비밀번호를 입력해 주세요."))
                false
            }
            else -> true
        }
    }

    private fun checkPassword(): Boolean {
        return when {
            inputPasswordLiveData.value.isNullOrEmpty() -> {
                viewStateChanged(LoginViewState.Error("이메일과 비밀번호를 입력해 주세요."))
                false
            }
            else -> true
        }
    }

    data class Person(
        val email: String,
        val password: String
    )

}