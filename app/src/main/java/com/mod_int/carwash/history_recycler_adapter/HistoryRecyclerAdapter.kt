package com.mod_int.carwash.history_recycler_adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HistoryRecyclerAdapter : RecyclerView.Adapter<HistoryRecyclerViewHolder>(){

    private val historyList = mutableListOf<HistoryInfo>()
    private lateinit var itemClickListener: (item : HistoryInfo) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryRecyclerViewHolder =
        HistoryRecyclerViewHolder(parent)

    override fun onBindViewHolder(holder: HistoryRecyclerViewHolder, position: Int) {
        holder.bind(historyList[position], itemClickListener)

    }

    override fun getItemCount(): Int  = historyList.size

    fun addAll(listH: List<HistoryInfo>) {
        historyList.addAll(listH)
        notifyDataSetChanged()
    }

    fun setItemClickListener(listener: (item : HistoryInfo) -> Unit ){
        itemClickListener = listener
    }
}