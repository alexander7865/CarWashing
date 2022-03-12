package com.mod_int.carwash.ui.owner.find_recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.R
import com.mod_int.carwash.databinding.ItemModelBinding

class FindRecyclerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_model, parent, false)
) {

    private val binding = DataBindingUtil.bind<ItemModelBinding>(itemView)

    fun bind(
        item: WasherInfo,
        itemClickListener: (item: WasherInfo, clickType: ClickType) -> Unit
    ) {
        binding?.let {
            with(it) {
                nameWasher.text = item.name
                countOrder.text = item.count.toString()
                pointWasher.text = item.point.toString()
                deliPriceWasher.text = item.deliPrice.toString()
                policyPriceWasher.text = item.policyPrice.toString()
                locationWasher.text = item.location
                btnWashingOrder.setOnClickListener {
                    itemClickListener(item, ClickType.Route)
                }
                expandableLayout.isVisible = item.expandable
            }
        }

        itemView.setOnClickListener {
            itemClickListener(item, ClickType.Expand)
        }

        //expandable 기능으로 구현 하였으나 클릭을 하여 오픈하고 닫지 않은상태에서 스크롤하였을때 전부 오픈되는 오류 발생함
//        val isExpandable: Boolean = washerList[position].expandable
//        holder.expandableLayout.visibility = if (isExpandable) View.VISIBLE else View.GONE
//        holder.fullscreen.setOnClickListener {
//            val washerInfo = washerList[position]
//            washerInfo.expandable = !washerInfo.expandable
//            notifyItemChanged(position)
//        }
    }
}