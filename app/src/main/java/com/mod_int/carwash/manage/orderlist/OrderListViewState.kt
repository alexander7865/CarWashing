package com.mod_int.carwash.manage.orderlist

import com.mod_int.carwash.base.ViewState
import com.mod_int.carwash.model.OrderList
import com.mod_int.carwash.model.WasherInfo

sealed class OrderListViewState : ViewState {
    data class GetOrderInfo(val item : List<OrderList>) : OrderListViewState()
}