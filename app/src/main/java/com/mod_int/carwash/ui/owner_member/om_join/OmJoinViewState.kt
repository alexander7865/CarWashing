package com.mod_int.carwash.ui.owner_member.om_join

import com.mod_int.carwash.base.ViewState


sealed class OmJoinViewState : ViewState {
    object OmInfoSave : OmJoinViewState()
}