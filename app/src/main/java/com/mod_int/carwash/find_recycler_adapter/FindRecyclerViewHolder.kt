package com.mod_int.carwash.find_recycler_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.R
import com.mod_int.carwash.databinding.ItemModelBinding

class FindRecyclerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_model, parent, false)){

    private val binding = DataBindingUtil.bind<ItemModelBinding>(itemView)
    val fullscreen : LinearLayout = itemView.findViewById(R.id.fullscreen)
    val expandableLayout : LinearLayout = itemView.findViewById(R.id.expandableLayout)
    val btnOrder : Button = itemView.findViewById(R.id.btn_washingOrder)

    fun bind(item: WasherInfo, itemClickListener: (item : WasherInfo) -> Unit) {
        binding?.let {
            with(it){
                nameWasher.text = item.name
                countOrder.text = item.count.toString()
                pointWasher.text = item.point.toString()
                deliPriceWasher.text = item.deliPrice.toString()
                policyPriceWasher.text = item.policyPrice.toString()
                locationWasher.text = item.location
                playTimeWasher.text = item.playTime
            }
        }

        itemView.setOnClickListener {
            itemClickListener(item)

        }
    }
}