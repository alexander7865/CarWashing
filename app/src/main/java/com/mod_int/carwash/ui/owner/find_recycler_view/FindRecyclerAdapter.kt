package com.mod_int.carwash.ui.owner.find_recycler_view

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FindRecyclerAdapter : RecyclerView.Adapter<FindRecyclerViewHolder>() {

    private val washerList = mutableListOf<WasherInfo>()
    private lateinit var itemClickListener: (item: WasherInfo, ClickType) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindRecyclerViewHolder =
        FindRecyclerViewHolder(parent)

    override fun onBindViewHolder(holder: FindRecyclerViewHolder, position: Int) {
        holder.bind(washerList[position], itemClickListener)
    }

    override fun getItemCount(): Int = washerList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(listF: List<WasherInfo>) {
        washerList.addAll(listF)
        notifyDataSetChanged()
    }

    fun setItemClickListener(listener: (item: WasherInfo, ClickType) -> Unit) {
        itemClickListener = listener
    }

    fun toggleExpand(item: WasherInfo) {
        if (washerList.contains(item)) {
            val index = washerList.indexOf(item)
            washerList[index] = item.copy(expandable = !item.expandable)
            notifyItemChanged(index)
        }
    }
}

sealed class ClickType {
    object Expand : ClickType()
    object Route : ClickType()
}