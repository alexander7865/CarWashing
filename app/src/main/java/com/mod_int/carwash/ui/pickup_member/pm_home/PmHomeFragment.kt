package com.mod_int.carwash.ui.pickup_member.pm_home

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
import com.mod_int.carwash.databinding.FragmentPmHomeBinding
import com.mod_int.carwash.ui.pickup_member.pm_settle.PmSettlementRequestFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PmHomeFragment : BaseFragment<FragmentPmHomeBinding>(
    R.layout.fragment_pm_home) {

    private val pmHomeViewModel by viewModels<PmHomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        workSelect()
        initUi()
        initViewModel()

    }

    private fun initUi() {
        pmHomeViewModel.pmHomeInfo()
    }

    private fun initViewModel() {
        binding.viewModel = pmHomeViewModel
        pmHomeViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            (viewState as? PmHomeViewState)?.let {
                onChangedPmHomeViewState(viewState)
            }
        }
    }

    private fun onChangedPmHomeViewState(viewState: PmHomeViewState) {
        when(viewState){
            is PmHomeViewState.RouteSettlement -> {
                routeSettlement()
            }
            is PmHomeViewState.RouteWebViewSuggestPm1 -> {
                routeWebViewSuggestPm1()
            }
            is PmHomeViewState.RouteWebViewSuggestPm2 -> {
                routeWebViewSuggestPm2()
            }
        }
    }

    //스피너구현
    private fun workSelect() {
        val brand = resources.getStringArray(R.array.workSettingSelect)
        val brandAdapter = ArrayAdapter (requireContext(),
            R.layout.custom_washer_spinner, brand)

        with(binding){
            workSetting.adapter = brandAdapter
            workSetting.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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
    private fun routeSettlement() {
        parentFragmentManager.beginTransaction().add(R.id.container_pm_home, PmSettlementRequestFragment())
            .addToBackStack("PmSettlementRequestFragment")
            .commit()
    }

    private fun routeWebViewSuggestPm1() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.9block.co.kr"))
        view?.context?.startActivity(intent)
    }

    private fun routeWebViewSuggestPm2() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://gil.seoul.go.kr/walk/main.jsp"))
        view?.context?.startActivity(intent)
    }
}