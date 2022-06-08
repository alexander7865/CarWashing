package com.mod_int.carwash.manage.orderlist.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.manage.findwasher.adapter.ClickType
import com.mod_int.carwash.ui.dialog.CustomDialogFragment
import com.mod_int.carwash.model.OrderList
import com.mod_int.carwash.manage.orderlist.viewholder.OrderListRecyclerViewHolder
import com.mod_int.carwash.model.WasherInfo

class OrderListRecyclerAdapter : RecyclerView.Adapter<OrderListRecyclerViewHolder>(){

    private val orderList = mutableListOf<OrderList>()
    private lateinit var itemClickListener: (item : OrderList, OrderType) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListRecyclerViewHolder =
        OrderListRecyclerViewHolder(parent)

    override fun onBindViewHolder(holder: OrderListRecyclerViewHolder, position: Int) {
        holder.bind(orderList[position], itemClickListener)

    }

    override fun getItemCount(): Int  = orderList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(item: List<OrderList>) {
        orderList.addAll(item)
        notifyDataSetChanged()
    }

    fun setItemClickListener(listener: (item : OrderList, OrderType) -> Unit){
        itemClickListener = listener

    }
}

sealed class OrderType {
    object Route : OrderType()
}
