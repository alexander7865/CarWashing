package com.mod_int.carwash.ui.washer

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentHomeWasherBinding
import com.mod_int.carwash.databinding.FragmentPaymentWasherBinding

class PaymentWasherFragment : BaseFragment<FragmentPaymentWasherBinding>(R.layout.fragment_payment_washer) {

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