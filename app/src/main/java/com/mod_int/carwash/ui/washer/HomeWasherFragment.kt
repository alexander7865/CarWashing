package com.mod_int.carwash.ui.washer

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mod_int.carwash.databinding.FragmentHomeWasherBinding

class HomeWasherFragment : Fragment() {

    lateinit var binding: FragmentHomeWasherBinding
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
        binding = FragmentHomeWasherBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPaymentWahser.setOnClickListener {
            washerActivity.paymentWasher()
            val toastCenter = Toast.makeText(context,"결제를 진행하셔야 활동을 하실수 있습니다.", Toast.LENGTH_SHORT)
            toastCenter.setGravity(Gravity.CENTER,0,0)
            toastCenter.show()
        }
    }
}