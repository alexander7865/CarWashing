package com.mod_int.carwash.ui.washer_member.wm_home

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentWmHomeBinding
import com.mod_int.carwash.ui.washer_member.wm_payment.WmPaymentFragment
import com.mod_int.carwash.ui.washer_member.wm_price.WmRegistrationPriceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WmHomeFragment : BaseFragment<FragmentWmHomeBinding>(R.layout.fragment_wm_home) {
    private val wmHomeViewModel by viewModels<WmHomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        workSelect()
        initUi()
        initViewModel()

    }

    private fun initUi() {
        wmHomeViewModel.wmHomeInfo()

    }

    private fun initViewModel() {
        binding.viewModel = wmHomeViewModel
        wmHomeViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            (viewState as? WmHomeViewState)?.let {
                onChangedWmHomeViewState(viewState)
            }
        }
    }

    private fun onChangedWmHomeViewState(viewState: WmHomeViewState) {
        when (viewState) {
            is WmHomeViewState.RoutePriceRegistration -> {
                routePayment()
            }
            is WmHomeViewState.RouteWebViewSuggestWm1 -> {
                routeWebViewSuggestWm1()
            }
            is WmHomeViewState.RouteWebViewSuggestWm2 -> {
                routeWebViewSuggestWm2()
            }
        }
    }

    //스피너
    private fun workSelect() {
        val brand = resources.getStringArray(R.array.workSettingSelect)
        val brandAdapter = ArrayAdapter(
            requireContext(),
            R.layout.custom_washer_spinner, brand
        )

        with(binding) {
            workSetting.adapter = brandAdapter
            workSetting.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    view!!.setBackgroundColor(Color.TRANSPARENT)
                }
            }
        }
    }

    private fun routePayment() {
        parentFragmentManager.beginTransaction().add(R.id.container_wm_home, WmRegistrationPriceFragment())
            .addToBackStack("WmRegistrationPriceFragment")
            .commit()
    }

    private fun routeWebViewSuggestWm1() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.9block.co.kr"))
        view?.context?.startActivity(intent)
    }

    private fun routeWebViewSuggestWm2() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://gil.seoul.go.kr/walk/main.jsp"))
        view?.context?.startActivity(intent)
    }
}