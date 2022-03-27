package com.mod_int.carwash.manage.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mod_int.carwash.CustomDialogFragment
import com.mod_int.carwash.CustomDialogListener
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentOmManagementHistoryBinding
import com.mod_int.carwash.manage.history.adapter.HistoryRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OmManagementHistoryFragment : BaseFragment<FragmentOmManagementHistoryBinding>(
    R.layout.fragment_om_management_history) {

    private val omManagementHistoryViewModel by viewModels<OmManagementHistoryViewModel>()
    private val historyAdapter = HistoryRecyclerAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun initViewModel() {
        omManagementHistoryViewModel.getFinishedOrder()
        omManagementHistoryViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            (viewState as? OmManagementHistoryViewState)?.let {
                onChangedFinishedOrderViewState(
                    viewState
                )
            }
        }
    }

    private fun onChangedFinishedOrderViewState(viewState: OmManagementHistoryViewState){
        when (viewState) {
            is OmManagementHistoryViewState.GetFinishedOrder -> {
                historyAdapter.addAll(viewState.list)

            }
        }
    }

    private fun initUi() {
        with(binding) {
            recyclerHistory.adapter = historyAdapter
            historyAdapter.setItemClickListener {
                orderCfmDialog()
            }
        }
    }


    //커스텀 다이얼로그
    private fun orderCfmDialog() {
        val dialog = CustomDialogFragment.CustomDialogBuilder()
            .setTitle("관리기록을 삭제할까요?")
            .setQuestion("기록을 삭제하시면 복구가 불가능합니다.")
            .setNoBtn("아니요")
            .setYesBtn("네")
            .setBtnClickListener(object : CustomDialogListener {
                override fun onClickNegativeBtn() {

                }

                //삭제를 구현했으나 position 값을 못 넣네요 ㅋㅋㅋ
                override fun onClickPositiveBtn() {
                    historyAdapter.removeItem()
                    binding.recyclerHistory.adapter = historyAdapter

                }
            })
            .create()
        dialog.show(childFragmentManager, dialog.tag)
    }
}