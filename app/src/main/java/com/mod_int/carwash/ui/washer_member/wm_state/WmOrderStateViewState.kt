package com.mod_int.carwash.ui.washer_member.wm_state

import com.mod_int.carwash.base.ViewState

sealed class WmOrderStateViewState : ViewState {
    object RouteOrderList : WmOrderStateViewState()
}