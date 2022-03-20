package com.mod_int.carwash.ui.owner_member.recyclerview.findwasher

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
import com.mod_int.carwash.ui.owner_member.OmOrderStateFragment
import com.mod_int.carwash.ui.owner_member.find_recycler_view.ClickType
import com.mod_int.carwash.ui.owner_member.find_recycler_view.FindRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OwnerFindWasherFragment :
    BaseFragment<FragmentOmFindWasherBinding>(R.layout.fragment_om_find_washer) {

    private val ownerFindWasherViewModel by viewModels<OwnerFindWasherViewModel>()
    private val findAdapter = FindRecyclerAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        washingTypeSpinner()
        initUi()
        initViewModel()

    }

    private fun initViewModel() {
        ownerFindWasherViewModel.getWasherMember()
        ownerFindWasherViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            (viewState as? OwnerFindWasherViewState)?.let {
                onChangedOwnerFindWasherViewState(
                    viewState
                )
            }
        }
    }

    private fun onChangedOwnerFindWasherViewState(viewState: OwnerFindWasherViewState) {
        when (viewState) {
            is OwnerFindWasherViewState.GetWasherMember -> {
//                findAdapter.addAll()
                findAdapter.addAll(viewState.list)
//                Log.d("결과", viewState.list.toString())
            }
        }
    }

    private fun initUi() {

        with(binding) {
            findWasherRecycler.adapter = findAdapter
        }

        //초기 리사이클러뷰 구현.
        with(findAdapter) {
//            addAll(mockList)
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

//    companion object {
//
//        private val mockData = WasherInfo( //리사이클러뷰 초기값 설정
//            name = "홍길동",
//            count = "5",
//            point = "90",
//            deliPrice = "6000",
//            policyPrice = "20000",
//            location = "압구정동 3동",
//            washingType = "픽업손세차",
//            inWashingCountryOfCar = "내부세차(외제차)",
//            outWashingCountryOfCar = "외부세차(외제차)",
//            inOutWashingCountryOfCar = "내부+외부세차(외제차)",
//            inWashingCarSize = "준중형 :",
//            outWashingCarSize = "준중형 :",
//            inOutWashingCarSize = "준중형 :",
//            inWashingCost = "12000",
//            outWashingCost = "15000",
//            inOutWashingCost = "25000",
//            inWashingTime = "20",
//            outWashingTime = "20",
//            inOutWashingTime = "30",
//            introduceText = "반가워요 슈퍼카 전문 손세차입니다!!",
//
//        )
//
//        private val mockList = mutableListOf<WasherInfo>().apply {
//            for (i in 0..100) {
//                add(mockData.copy(name = "홍길동$i"))
//            }
//        }
//
//    }

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