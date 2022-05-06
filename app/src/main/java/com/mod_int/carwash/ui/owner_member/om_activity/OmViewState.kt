package com.mod_int.carwash.ui.owner_member.om_activity

import com.mod_int.carwash.base.ViewState

sealed class OmViewState : ViewState {
    object PermissionGrant : OmViewState()
}