package com.mod_int.carwash.manage.orderlist.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.R
import com.mod_int.carwash.databinding.ItemListBinding
import com.mod_int.carwash.manage.orderlist.adapter.OrderType
import com.mod_int.carwash.model.OrderList

class OrderListRecyclerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)){

    private val binding = DataBindingUtil.bind<ItemListBinding>(itemView)

    fun bind(item: OrderList, itemClickListener: (item : OrderList, orderType: OrderType) -> Unit) {
        binding?.let {
            with(it) {
                ordDate.text = item.ordDate
                ordState.text = item.ordState
                ordType.text = item.ordType
                ordCarNum.text = item.ordCarNum
                ordCarBrand.text = item.ordCarBrand
                ordCarModel.text = item.ordCarModel
                ordCarCategory.text = item.ordCarCategory
                ordCarCol.text = item.ordCarCol
                ordCarSize.text = item.ordCarSize
                ordLocation.text = item.ordLocation

                cfmOrderWasher.setOnClickListener {
                    itemClickListener(item, OrderType.Route)
                }
            }
        }
    }
}
