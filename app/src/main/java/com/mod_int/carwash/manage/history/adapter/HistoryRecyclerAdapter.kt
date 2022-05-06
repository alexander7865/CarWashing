package com.mod_int.carwash.manage.history.adapter

import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.manage.history.OmManagementHistoryViewModel
import com.mod_int.carwash.ui.dialog.CustomDialogFragment
import com.mod_int.carwash.manage.history.viewholder.HistoryRecyclerViewHolder
import com.mod_int.carwash.model.HistoryInfo

class HistoryRecyclerAdapter : RecyclerView.Adapter<HistoryRecyclerViewHolder>(){

    private val historyList = mutableListOf<HistoryInfo>()
    private lateinit var itemClickListener: (button : CustomDialogFragment) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryRecyclerViewHolder =
        HistoryRecyclerViewHolder(parent)

    override fun onBindViewHolder(holder: HistoryRecyclerViewHolder, position: Int) {
        holder.bind(historyList[position], itemClickListener)


    }

    override fun getItemCount(): Int  = historyList.size

    fun addAll(list: MutableList<HistoryInfo>) {
        historyList.addAll(list)
        notifyDataSetChanged()
    }

    //여기서 포지션값을 전달하고 싶은데 안되네요 일단 0을 넣었습니다.
    fun removeItem(){
        historyList.removeAt(0) //이렇게 하니 첫번째만 없어지는 현상이 발생합니다.
        notifyDataSetChanged()

    }

    fun setItemClickListener(listener: (button : CustomDialogFragment) -> Unit ){
        itemClickListener = listener
    }


}
