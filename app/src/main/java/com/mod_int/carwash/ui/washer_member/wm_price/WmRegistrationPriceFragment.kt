package com.mod_int.carwash.ui.washer_member.wm_price

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentWmRegistrationPriceBinding
import com.mod_int.carwash.ext.showToast
import com.mod_int.carwash.ui.owner_member.om_join.OmJoinViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WmRegistrationPriceFragment : BaseFragment<FragmentWmRegistrationPriceBinding>(
    R.layout.fragment_wm_registration_price) {

    private val wmRegistrationPriceViewModel by viewModels<WmRegistrationPriceViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()



    }
    private fun initUi(){


    }

    private fun initViewModel(){
        binding.viewModel = wmRegistrationPriceViewModel
        wmRegistrationPriceViewModel.viewStateLiveData.observe(viewLifecycleOwner){viewState ->
            (viewState as? WmRegistrationPriceViewState)?.let {
                onChangeRegistrationPriceViewState(viewState)
            }
        }

    }
    private fun onChangeRegistrationPriceViewState(viewState: WmRegistrationPriceViewState){
        when(viewState){
            is WmRegistrationPriceViewState.EnableInput -> {
                enableSetting(viewState.isEnable)
            }
            is WmRegistrationPriceViewState.ErrorMsg -> {
                showToast(message = viewState.message)
            }
            is WmRegistrationPriceViewState.RouteBackStep -> {
                requireActivity().onBackPressed()
            }
        }
    }


    private fun enableSetting(isEnable: Boolean) {
        with(binding) {
            in1.isEnabled = isEnable
            in2.isEnabled = isEnable
            in3.isEnabled = isEnable
            in4.isEnabled = isEnable
            time1.isEnabled = isEnable
            out1.isEnabled = isEnable
            out2.isEnabled = isEnable
            out3.isEnabled = isEnable
            out4.isEnabled = isEnable
            inout1.isEnabled = isEnable
            inout2.isEnabled = isEnable
            inout3.isEnabled = isEnable
            inout4.isEnabled = isEnable
            time2.isEnabled = isEnable
            inout1.isEnabled = isEnable
            inout2.isEnabled = isEnable
            inout3.isEnabled = isEnable
            inout4.isEnabled = isEnable
            time3.isEnabled = isEnable
            addCost.isEnabled = isEnable
            intro.isEnabled = isEnable
        }
    }
}

