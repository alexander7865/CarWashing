package com.mod_int.carwash.ui.washer_member.wm_home

import com.mod_int.carwash.base.ViewState

sealed class WmHomeViewState : ViewState {
    object RoutePriceRegistration : WmHomeViewState()
    object RouteWebViewSuggestWm1 : WmHomeViewState()
    object RouteWebViewSuggestWm2 : WmHomeViewState()
}