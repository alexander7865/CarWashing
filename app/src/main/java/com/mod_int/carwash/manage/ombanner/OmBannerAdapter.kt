package com.mod_int.carwash.manage.ombanner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.R
import com.mod_int.carwash.databinding.ItemOmBannerBinding
import com.mod_int.carwash.model.BannerItem

class OmBannerAdapter : RecyclerView.Adapter<OmBannerAdapter.CustomViewHolder>(){

    private lateinit var itemClickListener: (item : BannerItem, BannerType) -> Unit
    private val itemList = mutableListOf<BannerItem>()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder = CustomViewHolder(parent)

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(itemList[position], itemClickListener)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    class CustomViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_om_banner, parent, false))
    {
        private val binding = DataBindingUtil.bind<ItemOmBannerBinding>(itemView)
        fun bind(item: BannerItem, itemClickListener :(item:BannerItem, bannerType:BannerType) -> Unit) {
            binding?.let {
                with(it){


                }
            }
        }
    }

    fun addAll(list: List<BannerItem>) {
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    fun setItemClickListener(listener: (item : BannerItem, BannerType) -> Unit ){
        itemClickListener = listener
    }
}

sealed class BannerType {
    object Route1 : BannerType()
    object Route2 : BannerType()
}