package com.mod_int.carwash.ui.washer_member

import android.content.Context
import android.os.Bundle
import android.view.View
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentWmPaymentBinding

class WmPaymentFragment : BaseFragment<FragmentWmPaymentBinding>(R.layout.fragment_wm_payment) {

    lateinit var wmActivity: WmActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is WmActivity) wmActivity = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.BtnBackToRegistration.setOnClickListener {
            wmActivity.backStep()
        }
    }
}