package com.mod_int.carwash.ui.owner.history

import android.os.Bundle
import android.view.View
import com.mod_int.carwash.CustomDialogFragment
import com.mod_int.carwash.CustomDialogListener
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentOwnerManagementHistoryBinding
import com.mod_int.carwash.model.HistoryInfo
import com.mod_int.carwash.ui.owner.history_recycler_view.HistoryRecyclerAdapter

class OwnerManagementHistoryFragment : BaseFragment<FragmentOwnerManagementHistoryBinding>(
    R.layout.fragment_owner_management_history){

    private val historyAdapter = HistoryRecyclerAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }


    private fun initUi() {

        //새로고침 구현
        with(binding) {
            pullToRefreshHistory.setOnRefreshListener {
                pullToRefreshHistory.isRefreshing = false
                historyAdapter.addAll(mockList)
            }

            recyclerHistory.adapter = historyAdapter


        }

        //초기 리사이클러뷰 구현.
        with(historyAdapter) {
            addAll(mockList)
            setItemClickListener {
                orderCfmDialog()

            }
        }
    }

    //기능복사 했음 확인해야함
    companion object {

        private val mockData = HistoryInfo( //리사이클러뷰 초기값 설정
            historyDate = "2022.02.02",
            washTypeCategory = "내부/외부 세차(외제차)",
            carNumber = "123허1234",
            brandHistory = "벤츠",
            styleNameHistory = "GLC220",
            carKindsHistory = "SUV",
            carColorHistory = "BLACK",
        )

        private val mockList = mutableListOf<HistoryInfo>().apply {
            for (i in 0..100) {
                add(mockData.copy(brandHistory = "벤츠 $i"))
            }
        }
    }

    //커스텀 다이얼로그 만들었습니다.
    private fun orderCfmDialog() {
        val dialog = CustomDialogFragment.CustomDialogBuilder()
            .setTitle("관리기록을 삭제할까요?")
            .setQuestion("기록을 삭제하시면 복구가 불가능합니다.")
            .setNoBtn("아니요")
            .setYesBtn("네")
            .setBtnClickListener(object : CustomDialogListener {
                override fun onClickNegativeBtn() {

                }


                override fun onClickPositiveBtn() {
                    //삭제 하는 로직구현하고
                    //리스트상 안보이게 하는 로직

//                    requireActivity().supportFragmentManager.beginTransaction()
//                        .add(R.id.붙여넣어야할 프래임레이아웃 아이디, 전환 되고 싶은 프래그먼트())
//                        .addToBackStack("").commit()
                }
            })
            .create()
        dialog.show(childFragmentManager, dialog.tag)
    }
}