package com.mod_int.carwash.ui.owner.history_recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.R
import com.mod_int.carwash.databinding.ItemHistoryBinding

class HistoryRecyclerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)){

    private val binding = DataBindingUtil.bind<ItemHistoryBinding>(itemView)

    fun bind(item: HistoryInfo, itemClickListener: (item : HistoryInfo) -> Unit) {
        binding?.let {
            with(it){
                dateHistory.text = item.historyDate
                washTypeHistory.text = item.washTypeCategory
                carNumberHistory.text = item.carNumber
                brandHistory.text = item.brandHistory
                styleNameHistory.text = item.styleNameHistory
                carKindsHistory.text = item.carKindsHistory
                carColorHistory.text = item.carColorHistory
            }
        }

        itemView.setOnClickListener {
            itemClickListener(item)

        }
    }
}