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
            insideKoreaXS.isEnabled = isEnable
            insideKoreaS.isEnabled = isEnable
            insideKoreaM.isEnabled = isEnable
            insideKoreaL.isEnabled = isEnable
            outsideKoreaXS.isEnabled = isEnable
            outsideKoreaS.isEnabled = isEnable
            outsideKoreaM.isEnabled = isEnable
            outsideKoreaL.isEnabled = isEnable
            inOutsideKoreaXS.isEnabled = isEnable
            inOutsideKoreaS.isEnabled = isEnable
            inOutsideKoreaM.isEnabled = isEnable
            inOutsideKoreaL.isEnabled = isEnable
            insideForeignXS.isEnabled = isEnable
            insideForeignS.isEnabled = isEnable
            insideForeignM.isEnabled = isEnable
            insideForeignL.isEnabled = isEnable
            outsideForeignXS.isEnabled = isEnable
            outsideForeignS.isEnabled = isEnable
            outsideForeignM.isEnabled = isEnable
            outsideForeignL.isEnabled = isEnable
            inOutsideForeignXS.isEnabled = isEnable
            inOutsideForeignS.isEnabled = isEnable
            inOutsideForeignM.isEnabled = isEnable
            inOutsideForeignL.isEnabled = isEnable
            insideTime.isEnabled = isEnable
            outsideTime.isEnabled = isEnable
            inOutsideTime.isEnabled = isEnable
            deliveryCost.isEnabled = isEnable
            polishCost.isEnabled = isEnable
            washerIntroduce.isEnabled = isEnable
        }
    }
}

