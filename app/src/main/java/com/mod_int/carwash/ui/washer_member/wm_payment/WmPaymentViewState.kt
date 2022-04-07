package com.mod_int.carwash.ui.washer_member.wm_payment

import com.mod_int.carwash.base.ViewState

sealed class WmPaymentViewState : ViewState {
    object RouteBackStep : WmPaymentViewState()
}
