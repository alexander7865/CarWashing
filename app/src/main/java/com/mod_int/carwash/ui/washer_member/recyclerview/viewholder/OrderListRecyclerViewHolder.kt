package com.mod_int.carwash.ui.washer_member.recyclerview.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.CustomDialogFragment
import com.mod_int.carwash.R
import com.mod_int.carwash.databinding.ItemListBinding
import com.mod_int.carwash.model.OrderList

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