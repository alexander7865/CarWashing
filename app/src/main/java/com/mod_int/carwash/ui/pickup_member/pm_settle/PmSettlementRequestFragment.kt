package com.mod_int.carwash.ui.pickup_member.pm_settle

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentPmSettlementRequestBinding
import com.mod_int.carwash.ui.pickup_member.pm_activity.PmActivity
import com.mod_int.carwash.ui.pickup_member.pm_registration.PmRegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PmSettlementRequestFragment : BaseFragment<FragmentPmSettlementRequestBinding>(
    R.layout.fragment_pm_settlement_request) {

    private val pmSettlementRequestViewModel by viewModels<PmSettlementRequestViewModel>()
    private lateinit var pmActivity: PmActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is PmActivity) pmActivity = context

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            btnBackToRegistrationPickupManager.setOnClickListener {
                pmActivity.backStep()

            }
        }
    }
}