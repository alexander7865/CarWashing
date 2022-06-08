package com.mod_int.carwash.manage.orderlist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mod_int.carwash.ui.dialog.CustomDialogFragment
import com.mod_int.carwash.ui.dialog.CustomDialogListener
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentWmOrderListBinding
import com.mod_int.carwash.manage.findwasher.adapter.ClickType
import com.mod_int.carwash.model.OrderList
import com.mod_int.carwash.ui.washer_member.wm_activity.WmActivity
import com.mod_int.carwash.ui.washer_member.wm_state.WmOrderStateFragment
import com.mod_int.carwash.manage.orderlist.adapter.OrderListRecyclerAdapter
import com.mod_int.carwash.manage.orderlist.adapter.OrderType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderListFragment : BaseFragment<FragmentWmOrderListBinding>(
    R.layout.fragment_wm_order_list) {
    private val orderListViewModel by viewModels<OrderListViewModel>()
    private val orderListAdapter = OrderListRecyclerAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun initUi() {
        binding.recyclerOrderListWasher.adapter = orderListAdapter
        //새로고침 구현
        with(orderListAdapter) {
            setItemClickListener {item, orderType ->
                when(orderType) {
                    is OrderType.Route -> {
                        orderCfmDialog()
                    }
                }
            }
        }
    }

    private fun initViewModel(){
        orderListViewModel.getOrderInfo()
        orderListViewModel.viewStateLiveData.observe(viewLifecycleOwner){viewState->
            (viewState as? OrderListViewState)?.let {
                onChangedOrderViewState(viewState)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onChangedOrderViewState(viewState : OrderListViewState){
        when(viewState){
            is OrderListViewState.GetOrderInfo -> {
                orderListAdapter.addAll(viewState.item)
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
