package com.mod_int.carwash.ui.pickup_member.pm_settle

import com.mod_int.carwash.base.ViewState

sealed class PmSettlementRequestViewState : ViewState {
    object RouteBackStep : PmSettlementRequestViewState()
}