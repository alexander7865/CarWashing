package com.mod_int.carwash.manage.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.firestore.admin.v1.Index
import com.mod_int.carwash.ui.dialog.CustomDialogFragment
import com.mod_int.carwash.ui.dialog.CustomDialogListener
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentOmManagementHistoryBinding
import com.mod_int.carwash.manage.history.adapter.HistoryRecyclerAdapter
import com.mod_int.carwash.model.HistoryInfo
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

    private fun initUi() {
        with(binding) {
            recyclerHistory.adapter = historyAdapter
            historyAdapter.setItemClickListener {info->
                orderCfmDialog(info)
            }
        }
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
            is OmManagementHistoryViewState.GetHistoryOrder -> {
                historyAdapter.addAll(viewState.list)

            }
        }
    }

    //커스텀 다이얼로그
    private fun orderCfmDialog(info: HistoryInfo) {
        val dialog = CustomDialogFragment.CustomDialogBuilder()
            .setTitle("관리기록을 삭제할까요?")
            .setQuestion("기록을 삭제하시면 복구가 불가능합니다.")
            .setNoBtn("아니요")
            .setYesBtn("네")
            .setBtnClickListener(object : CustomDialogListener {
                override fun onClickNegativeBtn() {

                }

                //삭제를 구현했으나 position 값을 못 넣네요 그냥 터져버리네요 일단 0으로 넣어놨습니다ㅋㅋㅋ
                override fun onClickPositiveBtn() {
                    historyAdapter.removeItem(info)
                    binding.recyclerHistory.adapter = historyAdapter

                }
            })
            .create()
        dialog.show(childFragmentManager, dialog.tag)
    }
}