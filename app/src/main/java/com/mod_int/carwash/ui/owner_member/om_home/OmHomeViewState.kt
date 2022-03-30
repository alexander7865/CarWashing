package com.mod_int.carwash.ui.owner_member.om_home

import com.mod_int.carwash.base.ViewState

sealed class OmHomeViewState : ViewState {
    object ChangeDate : OmHomeViewState()
    object ChangePhoneNr : OmHomeViewState()
    object ChangeCarInfo : OmHomeViewState()
    object ChangeCarLocation : OmHomeViewState()

}


