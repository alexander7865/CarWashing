package com.mod_int.carwash.ui.washer.order_list_recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.mod_int.carwash.CustomDialogFragment
import com.mod_int.carwash.R
import com.mod_int.carwash.databinding.ItemListBinding

class OrderListRecyclerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)){
    private val binding = DataBindingUtil.bind<ItemListBinding>(itemView)

    fun bind(item: OrderList, itemClickListener: (dialog: CustomDialogFragment) -> Unit) {
        binding?.let {
            with(it) {
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

                cfmOrderWasher.setOnClickListener {
                    val button = CustomDialogFragment()
                    itemClickListener(button)
                }
            }
        }
    }
}