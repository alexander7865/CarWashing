package com.mod_int.carwash.ui.owner_member.recyclerview.washinghistory

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.mod_int.carwash.CustomDialogFragment
import com.mod_int.carwash.CustomDialogListener
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentOmManagementHistoryBinding
import com.mod_int.carwash.model.HistoryInfo
import com.mod_int.carwash.ui.owner_member.recyclerview.washinghistory.adapter.HistoryRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OmManagementHistoryFragment : BaseFragment<FragmentOmManagementHistoryBinding>(
    R.layout.fragment_om_management_history) {

    private val omManagementHistoryViewModel by viewModels<OmManagementHistoryViewModel>()
    private val historyAdapter = HistoryRecyclerAdapter()
//    private val auth = FirebaseAuth.getInstance()
//    private val db = FirebaseFirestore.getInstance()


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

    //다른아이디로 접근시 앱이 터지는 현상 발생 null 익셉션 발생하는데 어떻게 해야 할까요?
    private fun initUi() {

        //전에 했던 코드
//        val user = auth.currentUser!!.email
//        user!!.let {
//            db.collection("OwnerMember")
//                .document("$user")
//                .get()
//                .addOnSuccessListener { document ->
//                    if(document.exists()) {
//                        val list = HistoryInfo( //가지고 오는 아이템명과 일치해야함
//                            email = "2022년 2월 2일",
//                            phoneNumber = document.get("phoneNumber") as String,
//                            type = "${document.get("carNumber") as String} " +
//                                    "${document.get("carBrand") as String} " +
//                                    "${document.get("carModel") as String} " +
//                                    "${document.get("carKinds") as String} " +
//                                    "${document.get("carSize") as String} " +
//                                    "${document.get("carColor") as String} "
//                        )
//                        val finishedList = mutableListOf<HistoryInfo>().apply {
//                            add(list)
//                        }
//                        historyAdapter.addAll(finishedList)
//                    }else{
//
//                    }
//                }

            with(binding) {
                recyclerHistory.adapter = historyAdapter
                historyAdapter.setItemClickListener {
                    orderCfmDialog()
                }
            }
//        }
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

                //나름 삭제하는 로직을 구현했습니다 그러나 전부다 삭제시 앱이 꺼지는 문제가 발생합니다 나중에 수정해야 할 듯 합니다
                //해당 포지션 값으로 넣어야 합니다 1번 값만 없어지네요 ㅎㅎ
                override fun onClickPositiveBtn() {
                    historyAdapter.removeItem()
                    binding.recyclerHistory.adapter = historyAdapter

                }
            })
            .create()
        dialog.show(childFragmentManager, dialog.tag)
    }
}