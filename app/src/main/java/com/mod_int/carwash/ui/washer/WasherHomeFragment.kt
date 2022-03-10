package com.mod_int.carwash.ui.washer

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentWasherHomeBinding

class WasherHomeFragment : BaseFragment<FragmentWasherHomeBinding>(R.layout.fragment_washer_home) {

    lateinit var washerActivity: WasherActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is WasherActivity) washerActivity = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //픽업/탁송비용 정산
        binding.btnPaymentWahser.setOnClickListener {
            washerActivity.paymentWasher()
            val toastCenter = Toast.makeText(washerActivity,"픽업/탁송 비용을 정산하세요", Toast.LENGTH_SHORT)
            toastCenter.setGravity(Gravity.CENTER,0,0)
            toastCenter.show()
        }
    }
}