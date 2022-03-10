package com.mod_int.carwash.ui.pickup_manager

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentPickupManagerHomeBinding

class PickupManagerHomeFragment : BaseFragment<FragmentPickupManagerHomeBinding>(
    R.layout.fragment_pickup_manager_home) {

    private lateinit var pickupManagerActivity: PickupManagerActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is PickupManagerActivity) pickupManagerActivity = context

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //정산요청으로 이동
        binding.btnSettlementRequest.setOnClickListener {
            pickupManagerActivity.settlementRequest()
            val toastCenter = Toast.makeText(pickupManagerActivity,"정산요청을 진행하세요", Toast.LENGTH_SHORT)
            toastCenter.setGravity(Gravity.CENTER,0,0)
            toastCenter.show()
        }
    }
}