package com.mod_int.carwash.manage.history.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.ui.dialog.CustomDialogFragment
import com.mod_int.carwash.R
import com.mod_int.carwash.databinding.ItemHistoryBinding
import com.mod_int.carwash.model.HistoryInfo

class HistoryRecyclerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)){

    private val binding = DataBindingUtil.bind<ItemHistoryBinding>(itemView)

    fun bind(item: HistoryInfo, itemClickListener: (Button : CustomDialogFragment) -> Unit) {
        binding?.let {
            with(it){
                dateHistory.text = item.date
                washTypeHistory.text = item.washType
                carInfo.text = item.carInfo


                btnCancelHistory.setOnClickListener {
                    val button = CustomDialogFragment()
                    itemClickListener(button)

                }
            }
        }
    }
}