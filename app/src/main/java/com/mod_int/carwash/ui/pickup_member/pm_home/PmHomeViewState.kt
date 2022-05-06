package com.mod_int.carwash.ui.pickup_member.pm_home

import com.mod_int.carwash.base.ViewState

sealed class PmHomeViewState : ViewState {
    object RouteSettlement : PmHomeViewState()
    object RouteWebViewSuggestPm1 : PmHomeViewState()
    object RouteWebViewSuggestPm2 : PmHomeViewState()
}