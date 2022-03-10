package com.mod_int.carwash.ui.washer

import android.content.Context
import android.os.Bundle
import android.view.View
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentWasherPaymentBinding

class WasherPaymentFragment : BaseFragment<FragmentWasherPaymentBinding>(R.layout.fragment_washer_payment) {

    lateinit var washerActivity: WasherActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is WasherActivity) washerActivity = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.BtnBackToRegistration.setOnClickListener {
            washerActivity.backStep()
        }
    }
}