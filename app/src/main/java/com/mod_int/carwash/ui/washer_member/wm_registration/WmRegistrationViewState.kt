package com.mod_int.carwash.ui.washer_member.wm_registration

import com.mod_int.carwash.base.ViewState

sealed class WmRegistrationViewState : ViewState {
    object WmSaveInfo : WmRegistrationViewState()
    data class Msg(val message: String) : WmRegistrationViewState()
    data class EnableInput(val isEnable : Boolean) : WmRegistrationViewState()
}