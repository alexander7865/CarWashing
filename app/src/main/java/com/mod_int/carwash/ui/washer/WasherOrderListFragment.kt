package com.mod_int.carwash.ui.washer

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.CustomDialogFragment
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentWasherOrderListBinding
import com.mod_int.carwash.ui.washer.order_list_recycler_view.OrderList
import com.mod_int.carwash.ui.washer.order_list_recycler_view.OrderListRecyclerAdapter

class WasherOrderListFragment : BaseFragment<FragmentWasherOrderListBinding>(
    R.layout.fragment_washer_order_list) {
    lateinit var washerActivity: WasherActivity
    private val orderListAdapter = OrderListRecyclerAdapter()
    private val orderListRecycler : RecyclerView by lazy {binding.recyclerOrderListWasher}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is WasherActivity) washerActivity = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderListRecycler.run {
            adapter = orderListAdapter
        }


        val orderListData = OrderList(
            dateOrderListWasher = "2022.02.02",
            namePickupManager = "알렉스",
            washTypeOrderListWasher = "내부/외부 세차(외제차)",
            carNumberOrderListWasher = "123허1234",
            brandOrderListWasher = "벤츠",
            styleNameOrderListWasher = "GLC220",
            carKindsOrderListWasher = "SUV",
            carColorOrderListWasher = "BLACK",
            carSizeOrderListWasher = "준중형",
            ownerAddressOrderListWasher = "서울특별시 서초구 잠원동 10-7 101호",
        )
        val orderList = mutableListOf<OrderList>().apply {
            for (i in 0..20) {
                add(orderListData)
            }
        }
        orderListAdapter.addAll(orderList)
        orderListAdapter.setItemClickListener {
            washerActivity.orderCfmDialog()

        }
    }
}
