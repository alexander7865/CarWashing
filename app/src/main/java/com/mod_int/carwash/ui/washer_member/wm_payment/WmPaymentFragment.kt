package com.mod_int.carwash.ui.washer_member.wm_payment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentWmPaymentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WmPaymentFragment : BaseFragment<FragmentWmPaymentBinding>(R.layout.fragment_wm_payment) {
    private val wmPaymentViewModel by viewModels<WmPaymentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initUi(){

    }

    private fun initViewModel() {
        binding.viewModel = wmPaymentViewModel
        wmPaymentViewModel.viewStateLiveData.observe(viewLifecycleOwner){viewState ->
            (viewState as? WmPaymentViewState)?.let{
                onChangedWmPayment(viewState)
            }
        }
    }

    private fun onChangedWmPayment(viewState: WmPaymentViewState) {
        when(viewState) {
            is WmPaymentViewState.RouteBackStep ->{
                requireActivity().onBackPressed()

            }
        }
    }
}