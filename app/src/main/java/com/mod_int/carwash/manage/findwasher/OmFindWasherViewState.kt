package com.mod_int.carwash.manage.findwasher

import com.mod_int.carwash.base.ViewState
import com.mod_int.carwash.model.PriceList
import com.mod_int.carwash.model.WasherInfo

sealed class OmFindWasherViewState : ViewState {
    data class GetWasherMember(val list: List<WasherInfo>) : OmFindWasherViewState()
    object GetWashingPrice : OmFindWasherViewState()
}