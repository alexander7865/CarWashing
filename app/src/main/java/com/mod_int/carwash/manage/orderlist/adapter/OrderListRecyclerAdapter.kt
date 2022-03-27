package com.mod_int.carwash.manage.orderlist.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.CustomDialogFragment
import com.mod_int.carwash.model.OrderList
import com.mod_int.carwash.manage.orderlist.viewholder.OrderListRecyclerViewHolder

class OrderListRecyclerAdapter : RecyclerView.Adapter<OrderListRecyclerViewHolder>(){

    private val orderList = mutableListOf<OrderList>()
    private lateinit var itemClickListener: (dialog: CustomDialogFragment) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListRecyclerViewHolder =
        OrderListRecyclerViewHolder(parent)

    override fun onBindViewHolder(holder: OrderListRecyclerViewHolder, position: Int) {
        holder.bind(orderList[position], itemClickListener)

    }

    override fun getItemCount(): Int  = orderList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(list: List<OrderList>) {
        orderList.addAll(list)
        notifyDataSetChanged()
    }

    fun setItemClickListener(listener: (dialog: CustomDialogFragment) -> Unit){
        itemClickListener = listener

    }
}
