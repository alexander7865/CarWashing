package com.mod_int.carwash.ui.washer_member.wm_payment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentWmPaymentBinding
import com.mod_int.carwash.ui.washer_member.wm_activity.WmActivity
import com.mod_int.carwash.ui.washer_member.wm_home.WmHomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WmPaymentFragment : BaseFragment<FragmentWmPaymentBinding>(R.layout.fragment_wm_payment) {

    private val wmPaymentViewModel by viewModels<WmPaymentViewModel>()
    lateinit var wmActivity: WmActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is WmActivity) wmActivity = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.BtnBackToRegistration.setOnClickListener {

        }
    }
}