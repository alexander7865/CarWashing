package com.mod_int.carwash.ui.owner.history_recycler_view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.CustomDialogFragment

class HistoryRecyclerAdapter : RecyclerView.Adapter<HistoryRecyclerViewHolder>(){

    private val historyList = mutableListOf<HistoryInfo>()
    private lateinit var itemClickListener: (button : CustomDialogFragment) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryRecyclerViewHolder =
        HistoryRecyclerViewHolder(parent)

    override fun onBindViewHolder(holder: HistoryRecyclerViewHolder, position: Int) {
        holder.bind(historyList[position], itemClickListener)

    }

    override fun getItemCount(): Int  = historyList.size

    fun addAll(list: MutableList<HistoryInfo>) {
        historyList.addAll(list)
        notifyDataSetChanged()
    }

    fun setItemClickListener(listener: (button : CustomDialogFragment) -> Unit ){
        itemClickListener = listener
    }
}