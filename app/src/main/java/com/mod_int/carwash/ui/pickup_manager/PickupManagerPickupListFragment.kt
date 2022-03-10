package com.mod_int.carwash.ui.pickup_manager

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentPickupManagerPickupListBinding
import com.mod_int.carwash.ui.pickup_manager.pickup_list_recycler_view.PickupList
import com.mod_int.carwash.ui.pickup_manager.pickup_list_recycler_view.PickupListRecyclerAdapter

class PickupManagerPickupListFragment : BaseFragment<FragmentPickupManagerPickupListBinding>(
    R.layout.fragment_pickup_manager_pickup_list) {
    private lateinit var pickupManagerActivity: PickupManagerActivity
    private val pickupListAdapter = PickupListRecyclerAdapter()
    private val pickupListRecycler: RecyclerView by lazy { binding.recyclerPickupListPickupManager }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is PickupManagerActivity) pickupManagerActivity = context

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pickupListRecycler.run {
            adapter = pickupListAdapter
        }

        val pickupListData = PickupList(
            dateOrderListWasher = "2022.02.02",
            namePickupManager = "픽업매니저 성함",
            washTypeOrderListWasher = "내부/외부 세차(외제차)",
            carNumberOrderListWasher = "123허1234",
            brandOrderListWasher = "벤츠",
            styleNameOrderListWasher = "GLC220",
            carKindsOrderListWasher = "SUV",
            carColorOrderListWasher = "BLACK",
            carSizeOrderListWasher = "준중형",
            ownerAddressOrderListWasher = "서울특별시 서초구 잠원동 10-7 101호",
        )

        val pickupList = mutableListOf<PickupList>().apply {
            for (i in 0..20) {
                add(pickupListData)
            }
        }
        pickupListAdapter.addAll(pickupList)
        pickupListAdapter.setItemClickListener { item ->
            pickupManagerActivity.goDetailOrderPickup()
//            val toastCenter = Toast.makeText(pickupManagerActivity,"디테일 확인을 해주세요", Toast.LENGTH_SHORT)
//            toastCenter.setGravity(Gravity.CENTER,0,0)
//            toastCenter.show()
        }
    }
}