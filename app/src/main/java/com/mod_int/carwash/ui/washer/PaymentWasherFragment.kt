package com.mod_int.carwash.ui.washer

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mod_int.carwash.databinding.FragmentPaymentWasherBinding

class PaymentWasherFragment : Fragment() {

    lateinit var binding: FragmentPaymentWasherBinding
    lateinit var washerActivity: WasherActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is WasherActivity) washerActivity = context
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentWasherBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.BtnBackToRegistration.setOnClickListener {
            washerActivity.backStep()
        }
    }
}