package com.mod_int.carwash.ui.owner_member.recyclerview.washinghistory.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.CustomDialogFragment
import com.mod_int.carwash.R
import com.mod_int.carwash.databinding.ItemHistoryBinding
import com.mod_int.carwash.model.HistoryInfo

class HistoryRecyclerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)){

    private val binding = DataBindingUtil.bind<ItemHistoryBinding>(itemView)

    fun bind(item: HistoryInfo, itemClickListener: (Button : CustomDialogFragment) -> Unit) {
        binding?.let {
            with(it){
                dateHistory.text = item.historyDate
                washTypeHistory.text = item.washTypeCategory
                carNumberHistory.text = item.carNumber
                brandHistory.text = item.brandHistory
                styleNameHistory.text = item.styleNameHistory
                carKindsHistory.text = item.carKindsHistory
                carColorHistory.text = item.carColorHistory

                btnCancelHistory.setOnClickListener {
                    val button = CustomDialogFragment()
                    itemClickListener(button)

                }
            }
        }
    }
}