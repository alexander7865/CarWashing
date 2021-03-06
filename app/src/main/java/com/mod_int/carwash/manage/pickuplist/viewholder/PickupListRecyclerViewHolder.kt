package com.mod_int.carwash.manage.pickuplist.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.ui.dialog.CustomDialogFragment
import com.mod_int.carwash.R
import com.mod_int.carwash.databinding.ItemPickupBinding
import com.mod_int.carwash.model.PickupList

class PickupListRecyclerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_pickup, parent, false)){

    private val binding = DataBindingUtil.bind<ItemPickupBinding>(itemView)

    fun bind(item: PickupList, itemClickListener: (Button : CustomDialogFragment) -> Unit) {
        binding?.let {
            with(it){
                datePickupListWasher.text = item.datePickupListWasher
                namePickupManager.text = item.namePickupManager
                washTypeOrderListWasher.text = item.washTypeOrderListWasher
                carNumberOrderListWasher.text = item.carNumberOrderListWasher
                brandOrderListWasher.text = item.brandOrderListWasher
                styleNameOrderListWasher.text = item.styleNameOrderListWasher
                carKindsOrderListWasher.text = item.carKindsOrderListWasher
                carColorOrderListWasher.text = item.carColorOrderListWasher
                carSizeOrderListWasher.text = item.carSizeOrderListWasher
                ownerAddressOrderListWasher.text = item.ownerAddressOrderListWasher

                cfmOrderPickupManager.setOnClickListener {
                    val button = CustomDialogFragment()
                    itemClickListener(button)

                }

            }
        }
    }
}