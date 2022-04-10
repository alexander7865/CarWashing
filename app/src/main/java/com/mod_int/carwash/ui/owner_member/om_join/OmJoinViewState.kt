package com.mod_int.carwash.ui.owner_member.om_join

import com.mod_int.carwash.base.ViewState


sealed class OmJoinViewState : ViewState {
    object RouteCarLocation : OmJoinViewState()
    object OmInfoSave : OmJoinViewState()
    object BackStep : OmJoinViewState()
    data class ErrorMsg(val message: String) : OmJoinViewState()
    data class EnableInput(val isEnable : Boolean) : OmJoinViewState()
}