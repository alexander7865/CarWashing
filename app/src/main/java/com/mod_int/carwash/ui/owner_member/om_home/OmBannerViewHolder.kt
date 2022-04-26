package com.mod_int.carwash.ui.owner_member.om_home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.R
import com.mod_int.carwash.databinding.ItemOmBannerBinding
import com.mod_int.carwash.model.BannerItem

class OmBannerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_om_banner, parent, false)) {

    private val binding = DataBindingUtil.bind<ItemOmBannerBinding>(itemView)

    fun bind (item : BannerItem){
        binding?.let {
            with(it){
                omBannerDate.text = item.date
            }
        }
    }
}