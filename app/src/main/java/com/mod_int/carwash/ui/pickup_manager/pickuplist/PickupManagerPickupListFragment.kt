package com.mod_int.carwash.ui.pickup_manager.pickuplist

import android.os.Bundle
import android.view.View
import com.mod_int.carwash.CustomDialogFragment
import com.mod_int.carwash.CustomDialogListener
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentPickupManagerPickupListBinding
import com.mod_int.carwash.ui.pickup_manager.PickupManagerActivity
import com.mod_int.carwash.ui.pickup_manager.PickupManagerPickupStatusFragment
import com.mod_int.carwash.ui.pickup_manager.pickup_list_recycler_view.PickupList
import com.mod_int.carwash.ui.pickup_manager.pickup_list_recycler_view.PickupListRecyclerAdapter

class PickupManagerPickupListFragment : BaseFragment<FragmentPickupManagerPickupListBinding>(
    R.layout.fragment_pickup_manager_pickup_list) {

    private val pickupListAdapter = PickupListRecyclerAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()

    }


    private fun initUi() {

        //새로고침 구현
        with(binding) {
            pullToRefreshPickup.setOnRefreshListener {
                pullToRefreshPickup.isRefreshing = false
                pickupListAdapter.addAll(mockList)
            }

            recyclerListPickupManager.adapter = pickupListAdapter
        }

        //초기 리사이클러뷰 구현.
        with(pickupListAdapter) {
            addAll(mockList)
            setItemClickListener {
                orderCfmDialog()

            }
        }
    }

    //기능복사 했음 확인해야함
    companion object {

        private val mockData = PickupList( //리사이클러뷰 초기값 설정
            datePicupListWasher = "2022.02.02",
            namePickupManager = "픽업매니저 성함",
            washTypeOrderListWasher = "내부/외부 세차(외제차)",
            carNumberOrderListWasher = "123허1234",
            brandOrderListWasher = "벤츠",
            styleNameOrderListWasher = "GLC220",
            carKindsOrderListWasher = "SUV",
            carColorOrderListWasher = "BLACK",
            carSizeOrderListWasher = "준중형",
            ownerAddressOrderListWasher = "서울특별시 서초구 잠원동 10-7 101호",
        )

        private val mockList = mutableListOf<PickupList>().apply {
            for (i in 0..100) {
                add(mockData.copy(namePickupManager = "홍길동 $i"))
            }
        }
    }

    //커스텀 다이얼로그 만들었습니다.
    private fun orderCfmDialog() {
        val dialog = CustomDialogFragment.CustomDialogBuilder()
            .setTitle("픽업이 가능하신가요??")
            .setQuestion("콜 이후 취소를 할 경우 서비스가 제한되며, 취소는 고객센터로 문의 주세요")
            .setNoBtn("불가능해요")
            .setYesBtn("가능해요")
            .setBtnClickListener(object : CustomDialogListener {
                override fun onClickNegativeBtn() {
                    //불가능한경우 리스트상 안보이게 하는 로직 구현해야함
                }

                override fun onClickPositiveBtn() {

                    requireActivity().supportFragmentManager.beginTransaction()
                        .add(R.id.pickupListPickupManager, PickupManagerPickupStatusFragment())
                        .addToBackStack("").commit()
                }
            })
            .create()
        dialog.show(childFragmentManager, dialog.tag)
    }
}