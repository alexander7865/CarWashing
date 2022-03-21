package com.mod_int.carwash.ui.owner_member.recyclerview.washinghistory

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.google.firebase.firestore.FirebaseFirestore
import com.mod_int.carwash.CustomDialogFragment
import com.mod_int.carwash.CustomDialogListener
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.databinding.FragmentOmManagementHistoryBinding
import com.mod_int.carwash.model.HistoryInfo
import com.mod_int.carwash.ui.owner_member.recyclerview.washinghistory.adapter.HistoryRecyclerAdapter

class OmManagementHistoryFragment : BaseFragment<FragmentOmManagementHistoryBinding>(
    R.layout.fragment_om_management_history) {

    private val omManagementHistoryViewModel by viewModels<OmManagementHistoryViewModel>()
    private val historyAdapter = HistoryRecyclerAdapter()
    private val db = FirebaseFirestore.getInstance()
    private val itemList = mutableListOf<HistoryInfo>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
//        initViewModel()
    }

//    private fun initViewModel() {
//        omManagementHistoryViewModel.getFinishedWashing()
//        omManagementHistoryViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
//            (viewState as? FinishedWashingViewState)?.let {
//                onChangedFinishedWashingViewState(
//                    viewState
//                )
//            }
//        }
//    }
//
//    private fun onChangedFinishedWashingViewState(viewState: FinishedWashingViewState){
//        when (viewState) {
//            is FinishedWashingViewState.FinishedWashing -> {
//                historyAdapter.addAll(viewState.list as MutableList<HistoryInfo>)
//
//            }
//        }
//    }


    //파이어베이스 컬렉션에 필드값만 가지고 왔습니다 이렇게도 되네요 ㅋㅋㅋㅋ 조금 무식하지만 이렇게 가지고오면 처음부터
    //등록을 안하고 나중에 데이터를 등록하고 가지고 오고 싶은것만 가지고 와도되는 장점이 있어요
    private fun initUi() {
        with(binding) {
            recyclerHistory.adapter = historyAdapter
            db.collection("PickupMember")
                .get()
                .addOnSuccessListener { result ->
                    itemList.clear()
                    for(document in result) {
                        val list = HistoryInfo(
                            email = document.get("email") as String,
                            phoneNumber = document.get("phoneNumber") as String,
                            type = document.get("type") as String
                        )

                        val testList = mutableListOf<HistoryInfo>().apply {
                            add(list)
                        }
                        historyAdapter.addAll(testList)

//                        Log.d("완료값", "$email")

                    }

                }

        }

        //초기 리사이클러뷰 구현.
        with(historyAdapter) {
            setItemClickListener {
                orderCfmDialog()

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