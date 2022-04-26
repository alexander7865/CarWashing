package com.mod_int.carwash.ui.owner_member.om_home

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.model.BannerItem

class OmBannerPagerAdapter : RecyclerView.Adapter<OmBannerViewHolder>() {

    private lateinit var itemClickListener: (item : BannerItem) -> Unit
    private var bannerList = ArrayList<BannerItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OmBannerViewHolder {
       return OmBannerViewHolder(parent)
    }

    override fun onBindViewHolder(holder: OmBannerViewHolder, position: Int) {
        holder.bind(bannerList[position])
    }

    override fun getItemCount(): Int {
        return bannerList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(list: List<BannerItem>) {
        bannerList.addAll(list)
        notifyDataSetChanged()
    }

    fun setItemClickListener(listener: (item : BannerItem) -> Unit) {
        itemClickListener = listener
    }
}


