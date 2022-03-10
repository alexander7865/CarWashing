package com.mod_int.carwash.ui.owner.find_recycler_view

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FindRecyclerAdapter : RecyclerView.Adapter<FindRecyclerViewHolder>() {

    private val washerList = mutableListOf<WasherInfo>()
    private lateinit var itemClickListener: (item : WasherInfo) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindRecyclerViewHolder =
        FindRecyclerViewHolder(parent)

    override fun onBindViewHolder(holder: FindRecyclerViewHolder, position: Int) {
        holder.bind(washerList[position], itemClickListener)

        //expandable 기능으로 구현 하였으나 클릭을 하여 오픈하고 닫지 않은상태에서 스크롤하였을때 전부 오픈되는 오류 발생함
        val isExpandable: Boolean = washerList[position].expandable
        holder.expandableLayout.visibility = if (isExpandable) View.VISIBLE else View.GONE
        holder.fullscreen.setOnClickListener {
            val washerInfo = washerList[position]
            washerInfo.expandable = !washerInfo.expandable
            notifyItemChanged(position)
        }
    }


    override fun getItemCount(): Int  = washerList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(listF: List<WasherInfo>) {
        washerList.addAll(listF)
        notifyDataSetChanged()
    }

    fun setItemClickListener(listener: (item : WasherInfo) -> Unit ){
        itemClickListener = listener
    }
}