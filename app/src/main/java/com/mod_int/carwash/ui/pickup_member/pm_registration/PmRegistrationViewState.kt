package com.mod_int.carwash.ui.pickup_member.pm_registration

import com.mod_int.carwash.base.ViewState

sealed class PmRegistrationViewState : ViewState {
    object PmSaveInfo : PmRegistrationViewState()
    data class Msg(val message: String) : PmRegistrationViewState()
    data class EnableInput(val isEnable : Boolean) : PmRegistrationViewState()
}