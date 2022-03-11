package com.mod_int.carwash.ui.login

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ext.getUserInfo
import com.mod_int.carwash.ext.ioScope
import com.mod_int.carwash.ext.loginAndGetUserInfo
import com.mod_int.carwash.ext.loginUser
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
            viewStateChanged(LoginViewState.EnableInput(false))
            val checkEmail = async { checkEmail() }
            val checkPassword = async { checkPassword() }

            checkUser(checkEmail.await(), checkPassword.await())?.let { person ->
                firebaseRepository.loginAndGetUserInfo(
                    inputEmailLiveData.value!!,
                    inputPasswordLiveData.value!!
                ) { user ->
                    user?.let {
                        when (user.type) {
                            "owner" -> {
                                viewStateChanged(LoginViewState.RouteOwner)
                            }
                            "headWasher" -> {
                                viewStateChanged(LoginViewState.RouteWasher)
                            }

                            "pickupWasher" -> {
                                viewStateChanged(LoginViewState.RoutePickupManager)
                            }
                            else -> {
                                viewStateChanged(LoginViewState.Error("로그인이 실패하였습니다."))
                            }
                        }
                    } ?: viewStateChanged(LoginViewState.Error("로그인이 실패하였습니다."))
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
                viewStateChanged(LoginViewState.Error("이메일을 입력해 주세요."))
                false
            }
            else -> true
        }
    }

    private fun checkPassword(): Boolean {
        return when {
            inputPasswordLiveData.value.isNullOrEmpty() -> {
                viewStateChanged(LoginViewState.Error("비밀번호를 입력해 주세요."))
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