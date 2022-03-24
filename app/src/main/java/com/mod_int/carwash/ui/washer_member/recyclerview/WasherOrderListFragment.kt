package com.mod_int.carwash.ui.washer_member.recyclerview

import android.os.Bundle
import android.view.View
import com.mod_int.carwash.CustomDialogFragment
import com.mod_int.carwash.CustomDialogListener
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentWmOrderListBinding
import com.mod_int.carwash.model.OrderList
import com.mod_int.carwash.ui.washer_member.wm_activity.WmActivity
import com.mod_int.carwash.ui.washer_member.wm_state.WmOrderStateFragment
import com.mod_int.carwash.ui.washer_member.recyclerview.adapter.OrderListRecyclerAdapter

class WasherOrderListFragment : BaseFragment<FragmentWmOrderListBinding>(
    R.layout.fragment_wm_order_list) {

    private val orderListAdapter = OrderListRecyclerAdapter()
    lateinit var wmActivity: WmActivity


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }


    private fun initUi() {

        //새로고침 구현
        with(binding) {
            pullToRefreshOrd.setOnRefreshListener {
                pullToRefreshOrd.isRefreshing = false
                orderListAdapter.addAll(mockList)
            }

            recyclerOrderListWasher.adapter = orderListAdapter
        }

        //초기 리사이클러뷰 구현.
        with(orderListAdapter) {
            addAll(mockList)
            setItemClickListener {
                orderCfmDialog()
            }
        }
    }


    companion object {

        private val mockData = OrderList( //리사이클러뷰 초기값 설정
            dateOrderListWasher = "2022.02.02",
            namePickupManager = "알렉스",
            washTypeOrderListWasher = "내부/외부 세차(외제차)",
            carNumberOrderListWasher = "123허1234",
            brandOrderListWasher = "벤츠",
            styleNameOrderListWasher = "GLC220",
            carKindsOrderListWasher = "SUV",
            carColorOrderListWasher = "BLACK",
            carSizeOrderListWasher = "준중형",
            ownerAddressOrderListWasher = "서울특별시 서초구 잠원동 10-7 101호",
        )

        private val mockList = mutableListOf<OrderList>().apply {
            for (i in 0..100) {
                add(mockData.copy(namePickupManager = "홍길동 $i"))
            }
        }
    }

    //커스텀 다이얼로그 만들었습니다.
    private fun orderCfmDialog() {
        val dialog = CustomDialogFragment.CustomDialogBuilder()
            .setTitle("세차 작업이 가능한가요?")
            .setQuestion("콜 이후 취소를 할 경우 서비스가 제한되며, 취소는 고객센터로 문의 주세요")
            .setNoBtn("불가능해요")
            .setYesBtn("가능해요")
            .setBtnClickListener(object : CustomDialogListener {
                override fun onClickNegativeBtn() {
                    //불가능한경우 리스트상 안보이게 하는 로직 구현해야함
                }

                override fun onClickPositiveBtn() {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .add(R.id.orderListWasher, WmOrderStateFragment())
                        .addToBackStack("").commit()
                }
            })
            .create()
        dialog.show(childFragmentManager, dialog.tag)
    }
}
