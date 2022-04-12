package com.mod_int.carwash.ui.washer_member.wm_price

import com.mod_int.carwash.base.ViewState

sealed class WmRegistrationPriceViewState : ViewState {
    object RouteBackStep : WmRegistrationPriceViewState()
    object PriceInfoSave : WmRegistrationPriceViewState()
    data class ErrorMsg(val message: String) : WmRegistrationPriceViewState()
    data class EnableInput(val isEnable : Boolean) : WmRegistrationPriceViewState()
}