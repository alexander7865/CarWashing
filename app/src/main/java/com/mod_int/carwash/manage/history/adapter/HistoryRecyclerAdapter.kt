package com.mod_int.carwash.manage.history.adapter

import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.firestore.admin.v1.Index
import com.mod_int.carwash.manage.history.OmManagementHistoryViewModel
import com.mod_int.carwash.ui.dialog.CustomDialogFragment
import com.mod_int.carwash.manage.history.viewholder.HistoryRecyclerViewHolder
import com.mod_int.carwash.model.HistoryInfo

class HistoryRecyclerAdapter : RecyclerView.Adapter<HistoryRecyclerViewHolder>() {

    private val historyList = mutableListOf<HistoryInfo>()
    private lateinit var itemClickListener: (item: HistoryInfo) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryRecyclerViewHolder =
        HistoryRecyclerViewHolder(parent)

    override fun onBindViewHolder(holder: HistoryRecyclerViewHolder, position: Int) {
        holder.bind(historyList[position], itemClickListener)

    }

    override fun getItemCount(): Int = historyList.size

    fun addAll(list: MutableList<HistoryInfo>) {
        historyList.addAll(list)
        notifyDataSetChanged()
    }

    fun removeItem(info: HistoryInfo) {
        if (historyList.contains(info)) {
            val position = historyList.indexOfFirst { it == info }
            historyList.removeAt(position)
            notifyDataSetChanged()
        }
    }

    fun setItemClickListener(listener: (item: HistoryInfo) -> Unit) {
        itemClickListener = listener
    }
}
