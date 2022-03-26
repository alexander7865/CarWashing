package com.mod_int.carwash.ui.owner_member.recyclerview.washinghistory

import com.mod_int.carwash.base.ViewState
import com.mod_int.carwash.model.HistoryInfo

sealed class OmManagementHistoryViewState : ViewState {

    data class GetFinishedOrder(val list : MutableList<HistoryInfo>) : OmManagementHistoryViewState()
}