package com.mod_int.carwash.ui.register

import com.mod_int.carwash.base.ViewState

sealed class RegisterViewState : ViewState {

    object Cancel : RegisterViewState()
    object RoutePickupManager : RegisterViewState()
    object RouteOwner : RegisterViewState()
    object RouteWasher : RegisterViewState()
    data class Error(val message: String) : RegisterViewState()
    data class EnableInput(val isEnable : Boolean) : RegisterViewState()
}