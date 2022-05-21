package com.mod_int.carwash.ui.dialog.order

import com.mod_int.carwash.base.ViewState
import com.mod_int.carwash.model.OrderOmInfo

sealed class CustomDialogOrderViewState : ViewState {
    object SaveOmInfo : CustomDialogOrderViewState()
    data class GetOmInfo(val info: List<OrderOmInfo>) : CustomDialogOrderViewState()
}
