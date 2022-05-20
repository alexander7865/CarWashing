package com.mod_int.carwash.manage.findwasher.viewholder

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.R
import com.mod_int.carwash.model.WasherInfo
import com.mod_int.carwash.databinding.ItemModelBinding
import com.mod_int.carwash.manage.findwasher.OmFindWasherViewModel
import com.mod_int.carwash.manage.findwasher.adapter.ClickType

class FindRecyclerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_model, parent, false)){

    private val binding = DataBindingUtil.bind<ItemModelBinding>(itemView)

    @SuppressLint("SetTextI18n")
    fun bind(item: WasherInfo, itemClickListener: (item : WasherInfo, clickType: ClickType) -> Unit) {
        binding?.let {
            with(it){
                companyLocation.text = item.wmLocation
                wmCompanyName.text = item.wmCompanyName
                countOrder.text = "${item.wmCount} 건"
                pointWasher.text = "${ item.wmPoint } 점"
                washingType1.text = item.wmCheck1
                washingType2.text = item.wmCheck2
                washingType3.text = item.wmCheck3
                introduceText.text = item.washerIntroduce

                routePriceState.setOnClickListener {
                    itemClickListener(item, ClickType.RoutePriceState)
                }

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