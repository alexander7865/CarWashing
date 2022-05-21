package com.mod_int.carwash.manage.findwasher

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentOmFindWasherBinding
import com.mod_int.carwash.manage.findwasher.adapter.ClickType
import com.mod_int.carwash.manage.findwasher.adapter.FindRecyclerAdapter
import com.mod_int.carwash.model.PriceItem
import com.mod_int.carwash.ui.dialog.*
import com.mod_int.carwash.ui.dialog.order.CustomDialogOrderFragment
import com.mod_int.carwash.ui.dialog.order.CustomDialogOrderListener
import com.mod_int.carwash.ui.owner_member.om_state.OmOrderStateFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OmFindWasherFragment :
    BaseFragment<FragmentOmFindWasherBinding>(R.layout.fragment_om_find_washer) {
    private val omFindWasherViewModel by viewModels<OmFindWasherViewModel>()
    private val findAdapter = FindRecyclerAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
        washingTypeSpinner()
    }

    private fun initUi() {
        binding.findWasherRecycler.adapter = findAdapter
        //초기 리사이클러뷰 구현.
        with(findAdapter) {
            setItemClickListener { item, clickType ->
                when (clickType) {
                    is ClickType.Expand -> {
                        toggleExpand(item)
                    }

                    is ClickType.Route -> {
                        orderDialog(item.wmCompanyName)
                    }

                    is ClickType.RoutePriceState -> {
                        omFindWasherViewModel.getWasher(item.wmCompanyName)
                    }
                }
            }
        }
    }

    private fun initViewModel() {
        omFindWasherViewModel.getWasherMember()
        omFindWasherViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            (viewState as? OmFindWasherViewState)?.let {
                onChangedOmFindWasherViewState(viewState)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onChangedOmFindWasherViewState(viewState: OmFindWasherViewState) {
        when (viewState) {
            is OmFindWasherViewState.GetWasherMember -> {
                findAdapter.addAll(viewState.list)
            }

            is OmFindWasherViewState.GetPriceItem -> {
                priceDialog(viewState.item)
            }
        }
    }

    //커스텀 다이얼로그 만들었습니다.
    private fun orderDialog(companyName : String) {
        val dialog = CustomDialogOrderFragment.CustomDialogOrderBuilder()
            .setNoBtn("나중에 할께요")
            .setYesBtn("세차의뢰")
            .setCompanyName(companyName)
            .setBtnClickListener(object : CustomDialogOrderListener {
                override fun onClickNegativeBtn() {

                }

                override fun onClickPositiveBtn() {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .add(R.id.container_owner_find_washer, OmOrderStateFragment())
                        .addToBackStack("").commit()
                }
            })
            .create()
        dialog.show(childFragmentManager, dialog.tag)
    }

    private val wmPriceDialogCallbackListener = object : WmPriceDialogListener {
        override fun onClickNegativeBtn() {
            Log.d("결과", "눌림")
        }
    }

    private fun priceDialog(item: PriceItem) {
        val priceDialog = WmPriceDialogFragment.WmPriceDialogBuilder()
            .setBtnClickListener(wmPriceDialogCallbackListener)
            .setPriceItem(item)
            .create()
        priceDialog.show(childFragmentManager, priceDialog.tag)
    }


    //타입별로 필터링 구현했습니다 데이터 연결이후 문제가 생깁니다.
    private fun washingTypeSpinner() {
        val data = resources.getStringArray(R.array.washingType)
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            R.layout.custom_find_spinner, data
        )

        with(binding) {
            searchSpinner.adapter = spinnerAdapter
            searchSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> {
                            findAdapter.filter.filter("  픽업손세차  ")
                        }
                        1 -> {
                            findAdapter.filter.filter("  손세차예약  ")
                        }
                        2 -> {
                            findAdapter.filter.filter("  출장손세차  ")
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    view!!.setBackgroundColor(Color.TRANSPARENT)
                }
            }
        }
    }
}