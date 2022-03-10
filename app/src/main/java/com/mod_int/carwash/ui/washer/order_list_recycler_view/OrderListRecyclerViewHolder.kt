package com.mod_int.carwash.ui.washer.order_list_recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.R
import com.mod_int.carwash.databinding.ItemListBinding
import com.mod_int.carwash.ui.washer.WasherActivity

class OrderListRecyclerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)){
    private val binding = DataBindingUtil.bind<ItemListBinding>(itemView)
    lateinit var washerActivity: WasherActivity

    fun bind(item: OrderList, itemClickListener: (item : OrderList) -> Unit) {
        binding?.let {

            with(it){
                dateOrderListWasher.text = item.dateOrderListWasher
                namePickupManager.text = item.namePickupManager
                washTypeOrderListWasher.text = item.washTypeOrderListWasher
                carNumberOrderListWasher.text = item.carNumberOrderListWasher
                brandOrderListWasher.text = item.brandOrderListWasher
                styleNameOrderListWasher.text = item.styleNameOrderListWasher
                carKindsOrderListWasher.text = item.carKindsOrderListWasher
                carColorOrderListWasher.text = item.carColorOrderListWasher
                carSizeOrderListWasher.text = item.carSizeOrderListWasher
                ownerAddressOrderListWasher.text = item.ownerAddressOrderListWasher
            }
        }

        itemView.setOnClickListener {
            itemClickListener(item)

        }
    }
}