package com.mod_int.carwash.manage.pickuplist.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.CustomDialogFragment
import com.mod_int.carwash.model.PickupList
import com.mod_int.carwash.manage.pickuplist.viewholder.PickupListRecyclerViewHolder

class PickupListRecyclerAdapter : RecyclerView.Adapter<PickupListRecyclerViewHolder>(){

    private val orderList = mutableListOf<PickupList>()
    private lateinit var itemClickListener: (button : CustomDialogFragment) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickupListRecyclerViewHolder =
        PickupListRecyclerViewHolder(parent)

    override fun onBindViewHolder(holder: PickupListRecyclerViewHolder, position: Int) {
        holder.bind(orderList[position], itemClickListener)

    }

    override fun getItemCount(): Int  = orderList.size

    fun addAll(list: List<PickupList>) {
        orderList.addAll(list)
        notifyDataSetChanged()
    }

    fun setItemClickListener(listener: (button : CustomDialogFragment) -> Unit ){
        itemClickListener = listener
    }
}