package com.mod_int.carwash.ui.owner.find_recycler_view

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.model.WasherInfo

class FindRecyclerAdapter : RecyclerView.Adapter<FindRecyclerViewHolder>(), Filterable {

    private val washerList = mutableListOf<WasherInfo>()
    private lateinit var itemClickListener: (item : WasherInfo, ClickType) -> Unit
    private var unFilteredList = washerList
    private var filteredList = washerList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindRecyclerViewHolder =
        FindRecyclerViewHolder(parent)

    override fun onBindViewHolder(holder: FindRecyclerViewHolder, position: Int) {
        holder.bind(filteredList[position], itemClickListener)
    }

    override fun getItemCount(): Int  = filteredList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(listF: List<WasherInfo>) {
        filteredList.addAll(listF)
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

    //워싱 타입별로 필터를 만들었습니다. 문제는 새로고침을 할경우 전체 데이터가 나오네요 (일단은 리프레쉬가 필요없어 주석처리 했습니다)
    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                filteredList = if (charString.isEmpty()) {
                    unFilteredList
                }else {
                    val filteringList = ArrayList<WasherInfo>()
                    for (item in unFilteredList) {
                        if (item.name == charString) filteringList.add(item)
                        Log.d("테스트", charString)
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
}