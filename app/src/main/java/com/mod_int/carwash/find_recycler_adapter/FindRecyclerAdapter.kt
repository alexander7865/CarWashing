package com.mod_int.carwash.find_recycler_adapter

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.R
import com.mod_int.carwash.ui.owner.OrderStatusOwnerFragment
import com.mod_int.carwash.ui.owner.OwnerActivity

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

        //엑티비티에서 프래그먼트 이동이 안됩니다 알트 다이얼로그 구현도 안되네요
        holder.btnOrder.setOnClickListener {
//            val builder = AlertDialog.Builder(context)
            val orderStatusOwnerFragment = OrderStatusOwnerFragment()
            val ownerActivity = OwnerActivity()
            val bundle = Bundle()
            bundle.putString("hi","hi")
            orderStatusOwnerFragment.arguments = bundle
            ownerActivity.supportFragmentManager.beginTransaction()
                .replace(R.id.owner_frag, OrderStatusOwnerFragment()).commit()


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