package com.mod_int.carwash.manage.findwasher.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.model.WasherInfo
import com.mod_int.carwash.manage.findwasher.viewholder.FindRecyclerViewHolder


class FindRecyclerAdapter : RecyclerView.Adapter<FindRecyclerViewHolder>(), Filterable {

    private lateinit var itemClickListener: (item : WasherInfo, ClickType) -> Unit
    private val washerList = mutableListOf<WasherInfo>()
    private var unFilteredList = washerList
    private var filteredList = washerList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindRecyclerViewHolder =
        FindRecyclerViewHolder(parent)

    override fun onBindViewHolder(holder: FindRecyclerViewHolder, position: Int) {
        holder.bind(filteredList[position], itemClickListener)
    }

    override fun getItemCount(): Int  = filteredList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(list: List<WasherInfo>) {
        filteredList.addAll(list)
        notifyDataSetChanged()
    }

    fun setItemClickListener(listener: (item : WasherInfo, ClickType) -> Unit ){
        itemClickListener = listener
    }

    fun toggleExpand(item: WasherInfo) {
        if (filteredList.contains(item)) {
            val index = filteredList.indexOf(item)
            filteredList[index] = item.copy(expandable = (!item.expandable.toBoolean()).toString())
            notifyItemChanged(index)
        }
    }

    //리사이클러뷰 데이터 연결이후 작동이 안됩니다. 왜 이런현상이 일어나는 건가요?
    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                Log.d("테스트", charString)
                filteredList = if (charString.isEmpty()) {
                    unFilteredList
                    //전에는 구동이 되었으나 지금은 안되네요
                    //로그테스트결과 item.wmCheck1 , charString 데이터가 안넘어옵니다 여기서 문제가 발생한듯 합니다.
                    
                }else {
                    val filteringList = ArrayList<WasherInfo>()
                    for (item in unFilteredList) {
                        if (item.wmCheck1 == charString) filteringList.add(item)
                    }
                    filteringList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults

            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as ArrayList<WasherInfo>
                notifyDataSetChanged()
            }
        }
    }
}

sealed class ClickType {
    object Expand : ClickType()
    object Route : ClickType()
    object RoutePriceState : ClickType()
}