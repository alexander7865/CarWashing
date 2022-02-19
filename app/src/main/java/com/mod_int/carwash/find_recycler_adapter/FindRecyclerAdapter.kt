package com.mod_int.carwash.find_recycler_adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FindRecyclerAdapter : RecyclerView.Adapter<FindRecyclerViewHolder>() {

    private val washerList = mutableListOf<WasherInfo>()
    private lateinit var itemClickListener: (item : WasherInfo) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindRecyclerViewHolder =
        FindRecyclerViewHolder(parent)

    override fun onBindViewHolder(holder: FindRecyclerViewHolder, position: Int) {
        Log.d("로그", "onBindViewHolder: ${position}")

        val isExpandable: Boolean = washerList[position].expandable

        //expandable 기능으로 구현 하지만 클릭을 하여 오픈하고 닫지 않은상태에서 스크롤하였을때 전부 오픈되는 오류 발생함
        holder.bind(washerList[position], itemClickListener)
        holder.expandableLayout.visibility = if (isExpandable) View.VISIBLE else View.GONE
        holder.fullscreen.setOnClickListener {
            val washerInfo = washerList[position]
            washerInfo.expandable = !washerInfo.expandable
            notifyItemChanged(position)
        }

        //엑티비티에서 프래그먼트 이동이 안됩니다
        holder.btnOrder.setOnClickListener {

        }
    }

    override fun getItemCount(): Int  = washerList.size

    fun addAll(listF: List<WasherInfo>) {
        washerList.addAll(listF)
        notifyDataSetChanged()
    }

    fun setItemClickListener(listener: (item : WasherInfo) -> Unit ){
        itemClickListener = listener
    }


}