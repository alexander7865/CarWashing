package com.mod_int.carwash.ui.owner_member.om_price

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentOmPriceStatusBinding
import com.mod_int.carwash.manage.findwasher.OmFindWasherFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OmPriceStateFragment : BaseFragment<FragmentOmPriceStatusBinding>(
    R.layout.fragment_om_price_status) {

    private val omPriceStateViewModel by viewModels<OmPriceStateViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
    private fun initUi() {


    }
    private fun initViewModel() {
        binding.viewModel = omPriceStateViewModel
        omPriceStateViewModel.viewStateLiveData.observe(viewLifecycleOwner){viewState->
            (viewState as? OmPriceStateViewState)?.let {
                onChangedPriceViewState(viewState)
            }
        }
    }
    private fun onChangedPriceViewState(viewState: OmPriceStateViewState) {
        when(viewState){
            is OmPriceStateViewState.BackStep -> {
                requireActivity().onBackPressed()
            }
        }
    }
}