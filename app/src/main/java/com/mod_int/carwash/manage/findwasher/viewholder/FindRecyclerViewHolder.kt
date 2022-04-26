package com.mod_int.carwash.manage.findwasher.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.R
import com.mod_int.carwash.model.WasherInfo
import com.mod_int.carwash.databinding.ItemModelBinding
import com.mod_int.carwash.manage.findwasher.adapter.ClickType

class FindRecyclerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_model, parent, false)){

    private val binding = DataBindingUtil.bind<ItemModelBinding>(itemView)

    fun bind(item: WasherInfo, itemClickListener: (item : WasherInfo, clickType: ClickType) -> Unit) {
        binding?.let {
            with(it){
                companyLocation.text = item.companyLocation
                companyName.text = item.companyName
                countOrder.text = item.count
                pointWasher.text = item.point
                deliPriceWasher.text = item.deliPrice
                policyPriceWasher.text = item.policyPrice
                washingType1.text = item.washingType1
                washingType2.text = item.washingType2
                washingType3.text = item.washingType3
                inWashingCountryOfCar.text = item.inWashingCountryOfCar
                outWashingCountryOfCar.text = item.outWashingCountryOfCar
                inOutWashingCountryOfCar.text = item.inOutWashingCountryOfCar
                inWashingCarSize.text = item.inWashingCarSize
                outWashingCarSize.text = item.outWashingCarSize
                inOutWashingCarSize.text = item.inOutWashingCarSize
                inWashingCost.text = item.inWashingCost
                outWashingCost.text = item.outWashingCost
                inOutWashingCost.text = item.inOutWashingCost
                inWashingTime.text = item.inWashingTime
                outWashingTime.text = item.outWashingTime
                inOutWashingTime.text = item.inOutWashingTime
                introduceText.text = item.introduceText

                btnWashingOrder.setOnClickListener {
                    itemClickListener(item, ClickType.Route)
                }
                expandableLayout.isVisible = item.expandable.toBoolean()
            }
        }

        itemView.setOnClickListener {
            itemClickListener(item, ClickType.Expand)

        }
    }
}