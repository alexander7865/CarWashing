package com.mod_int.carwash.ui.owner_member.om_price

import com.mod_int.carwash.base.ViewState
import com.mod_int.carwash.model.PriceList

sealed class OmPriceStateViewState : ViewState {
    object BackStep : OmPriceStateViewState()
    object GetWashingPrice : OmPriceStateViewState()
}