package com.mod_int.carwash.ui.pickup_manager

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentPickupManagerRegistrationBinding

class PickupManagerRegistrationFragment : BaseFragment<FragmentPickupManagerRegistrationBinding>(
    R.layout.fragment_pickup_manager_registration) {

    lateinit var pickupManagerActivity: PickupManagerActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is PickupManagerActivity) pickupManagerActivity = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bankSelect()
    }





    //뱅크 셀렉
    private fun bankSelect() {
        val brand = resources.getStringArray(R.array.bankSelect)
        val brandAdapter = ArrayAdapter (requireContext(),
            R.layout.custom_owner_spinner, brand)

        with(binding){
            bankNamePickupManager.adapter = brandAdapter
            bankNamePickupManager.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    view!!.setBackgroundColor(Color.TRANSPARENT)
                }
            }
        }
    }

}