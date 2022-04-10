package com.mod_int.carwash.manage.findwasher

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.mod_int.carwash.CustomDialogOrderFragment
import com.mod_int.carwash.CustomDialogOrderListener
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentOmFindWasherBinding
import com.mod_int.carwash.ui.owner_member.om_state.OmOrderStateFragment
import com.mod_int.carwash.manage.findwasher.adapter.ClickType
import com.mod_int.carwash.manage.findwasher.adapter.FindRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OmFindWasherFragment :
    BaseFragment<FragmentOmFindWasherBinding>(R.layout.fragment_om_find_washer) {

    private val omFindWasherViewModel by viewModels<OmFindWasherViewModel>()
    private val findAdapter = FindRecyclerAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        washingTypeSpinner()
        initUi()
        initViewModel()

    }

    private fun initViewModel() {
        omFindWasherViewModel.getWasherMember()
        omFindWasherViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            (viewState as? OmFindWasherViewState)?.let {
                onChangedOmFindWasherViewState(
                    viewState
                )
            }
        }
    }

    private fun onChangedOmFindWasherViewState(viewState: OmFindWasherViewState) {
        when (viewState) {
            is OmFindWasherViewState.GetWasherInfo -> {
                findAdapter.addAll(viewState.wmInfo)
            }
        }
    }

    private fun initUi() {
        with(binding) {
            findWasherRecycler.adapter = findAdapter
        }

        //초기 리사이클러뷰 구현.
        with(findAdapter) {
            setItemClickListener { item, clickType ->
                when (clickType) {
                    is ClickType.Expand -> {
                        toggleExpand(item)
                    }

                    is ClickType.Route -> {
                        orderDialog()
                    }
                }
            }
        }
    }


    //커스텀 다이얼로그 만들었습니다.
    private fun orderDialog() {
        val dialog = CustomDialogOrderFragment.CustomDialogOrderBuilder()
            .setTitle("세차작업을 의뢰하시겠습니까?")
            .setRequest("")
            .setNoBtn("아니요")
            .setYesBtn("세차의뢰")
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


    //타입별로 필터링 구현했습니다 데이터 연결이후 문제가 생깁니다.
    private fun washingTypeSpinner() {
        val data = resources.getStringArray(R.array.washingType)
        val spinnerAdapter = ArrayAdapter (requireContext(),
            R.layout.custom_find_spinner, data)

        with(binding){
            searchSpinner.adapter = spinnerAdapter
            searchSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> {
                            findAdapter.filter.filter("픽업손세차")

                        }
                        1 -> {
                            findAdapter.filter.filter("손세차예약")

                        }
                        2 -> {
                            findAdapter.filter.filter("출장손세차")

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