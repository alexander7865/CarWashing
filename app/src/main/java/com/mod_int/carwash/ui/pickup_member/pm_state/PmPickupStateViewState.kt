package com.mod_int.carwash.ui.pickup_member.pm_state

import com.mod_int.carwash.base.ViewState

sealed class PmPickupStateViewState : ViewState {
    object RouteOrderList : PmPickupStateViewState()
}