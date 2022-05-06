package com.mod_int.carwash.manage.history

import com.mod_int.carwash.base.ViewState
import com.mod_int.carwash.model.HistoryInfo

sealed class OmManagementHistoryViewState : ViewState {
    data class GetHistoryOrder(val list : MutableList<HistoryInfo>) : OmManagementHistoryViewState()

}