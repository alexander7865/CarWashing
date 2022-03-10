package com.mod_int.carwash.ui.washer.order_list_recycler_view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.ui.washer.WasherActivity

class OrderListRecyclerAdapter : RecyclerView.Adapter<OrderListRecyclerViewHolder>(){

    private val orderList = mutableListOf<OrderList>()
    private lateinit var itemClickListener: (item : OrderList) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListRecyclerViewHolder =
        OrderListRecyclerViewHolder(parent)

    override fun onBindViewHolder(holder: OrderListRecyclerViewHolder, position: Int) {
        holder.bind(orderList[position], itemClickListener)

    }

    override fun getItemCount(): Int  = orderList.size

    fun addAll(list: List<OrderList>) {
        orderList.addAll(list)
        notifyDataSetChanged()
    }

    fun setItemClickListener(listener: (item : OrderList) -> Unit){
        itemClickListener = listener

    }
}