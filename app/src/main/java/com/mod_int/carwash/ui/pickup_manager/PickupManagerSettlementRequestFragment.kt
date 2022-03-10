package com.mod_int.carwash.ui.pickup_manager

import android.content.Context
import android.os.Bundle
import android.view.View
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentPickupManagerSettlementRequestBinding

class PickupManagerSettlementRequestFragment : BaseFragment<FragmentPickupManagerSettlementRequestBinding>(
    R.layout.fragment_pickup_manager_settlement_request) {

    private lateinit var pickupManagerActivity: PickupManagerActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is PickupManagerActivity) pickupManagerActivity = context

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            btnBackToRegistrationPickupManager.setOnClickListener {
                pickupManagerActivity.backStep()

            }
        }
    }
}