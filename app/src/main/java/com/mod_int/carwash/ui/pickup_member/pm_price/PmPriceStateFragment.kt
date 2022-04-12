package com.mod_int.carwash.ui.pickup_member.pm_price

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentPmPriceStatusBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PmPriceStateFragment : BaseFragment<FragmentPmPriceStatusBinding>(
    R.layout.fragment_pm_price_status) {

    private val pmPriceStateViewModel by viewModels<PmPriceStateViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()


    }
    private fun initUi() {

    }
    private fun initViewModel() {
        binding.viewModel = pmPriceStateViewModel
        pmPriceStateViewModel.viewStateLiveData.observe(viewLifecycleOwner){viewState->
            (viewState as? PmPriceStateViewState)?.let {
                onChangedPriceViewState(viewState)
            }
        }
    }
    private fun onChangedPriceViewState(viewState: PmPriceStateViewState) {
        when(viewState){

        }
    }
}