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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()

    }
    private fun initUi(){

    }
    private fun initViewModel(){
        binding.viewModel = pmSettlementRequestViewModel
        pmSettlementRequestViewModel.viewStateLiveData.observe(viewLifecycleOwner){viewState->
            (viewState as? PmSettlementRequestViewState)?.let {
                onChangedPmSettlement(viewState)
            }
        }
    }
    private fun onChangedPmSettlement(viewState: PmSettlementRequestViewState){
        when(viewState){
            is PmSettlementRequestViewState.RouteBackStep->{
                requireActivity().onBackPressed()
            }
        }
    }
}