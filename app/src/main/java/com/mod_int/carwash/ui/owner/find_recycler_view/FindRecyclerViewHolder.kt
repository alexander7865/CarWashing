package com.mod_int.carwash.ui.owner.find_recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.R
import com.mod_int.carwash.databinding.ItemModelBinding

class FindRecyclerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_model, parent, false)){

    private val binding = DataBindingUtil.bind<ItemModelBinding>(itemView)
    val fullscreen : LinearLayout = itemView.findViewById(R.id.fullscreen)
    val expandableLayout : LinearLayout = itemView.findViewById(R.id.expandableLayout)
    private val btnOrder : Button = itemView.findViewById(R.id.btn_washingOrder)

    fun bind(item: WasherInfo, itemClickListener: (item : WasherInfo) -> Unit) {
        binding?.let {
            with(it){
                nameWasher.text = item.name
                countOrder.text = item.count.toString()
                pointWasher.text = item.point.toString()
                deliPriceWasher.text = item.deliPrice.toString()
                policyPriceWasher.text = item.policyPrice.toString()
                locationWasher.text = item.location
            }
        }

        itemView.setOnClickListener {
            itemClickListener(item)

        }

        // 홀더안에 버튼을 클릭하면 터져버립니다 이유가 뭘까요?
        btnOrder.setOnClickListener {


        }
    }
}