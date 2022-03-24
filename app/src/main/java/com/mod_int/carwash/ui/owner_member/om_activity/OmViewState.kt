package com.mod_int.carwash.ui.owner_member.om_activity

import com.mod_int.carwash.base.ViewState

sealed class OmViewState : ViewState {
    object RouteHome : OmViewState()
    object RouteJoin : OmViewState()
    object RouteOrderState : OmViewState()
    object RouteMenu : OmViewState()

}