package com.mod_int.carwash.ui.login

import com.mod_int.carwash.base.ViewState

sealed class LoginViewState : ViewState {
    object Cancel : LoginViewState()
    object RouteOwnerMember : LoginViewState()
    object RouteWasherMember : LoginViewState()
    object RoutePickupMember : LoginViewState()
    object RemoveAnimation : LoginViewState()
    data class Error(val message: String) : LoginViewState()
    data class EnableInput(val isEnable : Boolean) : LoginViewState()
}
