package com.mod_int.carwash.ui.owner_member.om_state

import com.mod_int.carwash.base.ViewState

sealed class OmOrderStateViewState : ViewState {
    object RouteHistory : OmOrderStateViewState()
    object WasherMemberPhoneNr : OmOrderStateViewState()
}