package com.mod_int.carwash.ui.owner_member.om_home

import com.mod_int.carwash.base.ViewState

sealed class OmHomeViewState : ViewState {
    object ChangeDate : OmHomeViewState()
    object ChangePhoneNr : OmHomeViewState()
    data class ChangeCarInfo(val carInfo : String) : OmHomeViewState()
    object ChangeCarLocation : OmHomeViewState()

    object RouteOmJoin : OmHomeViewState()
    object RouteWebViewSuggestOm1 : OmHomeViewState()
    object RouteWebViewSuggestOm2 : OmHomeViewState()
}


