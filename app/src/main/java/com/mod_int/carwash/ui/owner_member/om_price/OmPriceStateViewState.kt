package com.mod_int.carwash.ui.owner_member.om_price

import com.mod_int.carwash.base.ViewState

sealed class OmPriceStateViewState : ViewState {
    object BackStep : OmPriceStateViewState()
    object GetWashingPrice : OmPriceStateViewState()
}