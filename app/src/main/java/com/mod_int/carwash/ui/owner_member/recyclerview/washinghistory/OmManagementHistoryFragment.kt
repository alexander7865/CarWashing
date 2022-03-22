package com.mod_int.carwash.ui.owner_member.recyclerview.washinghistory

import android.icu.text.Transliterator
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firestore.admin.v1.Index
import com.mod_int.carwash.CustomDialogFragment
import com.mod_int.carwash.CustomDialogListener
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentOmManagementHistoryBinding
import com.mod_int.carwash.model.HistoryInfo
import com.mod_int.carwash.ui.owner_member.recyclerview.washinghistory.adapter.HistoryRecyclerAdapter
import kotlinx.coroutines.currentCoroutineContext

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
    //등록을 안하고 나중에 데이터를 등록해도 가지고올 수 있다는 장점이 있어요
    //이제 요부분을 MVVM 패턴으로 적용 시켜야겠습니다. 몇번 하다 터져 버리더라고요 ㅋㅋㅋ
    private fun initUi() {
        db.collection("WasherMember")
            .get()
            .addOnSuccessListener { result ->
                itemList.clear()
                for(document in result) {
                    val list = HistoryInfo(
                        email = document.get("email") as String,
                        phoneNumber = document.get("phoneNumber") as String,
                        type = document.get("type") as String,
//                        carBrand = document.get("carBrand") as String,
//                        carModel = document.get("carModel") as String,
//                        carKinds = document.get("carKinds") as String,
//                        carColor = document.get("carColor") as String
                    )
                    val testList = mutableListOf<HistoryInfo>().apply {
                        add(list)
                    }
                    historyAdapter.addAll(testList)
                }
            }

        with(binding){
            recyclerHistory.adapter = historyAdapter
            historyAdapter.setItemClickListener {
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


                //나름 삭제하는 로직을 구현했습니다 그러나 전부다 삭제시 앱이 꺼지는 문제가 발생합니다 나중에 수정해야 할 듯 합니다
                //해당 포지션 값으로 넣어야 합니다 1번 값만 없어지네요 ㅎㅎ

                override fun onClickPositiveBtn() {
                    historyAdapter.removeItem()
                    binding.recyclerHistory.adapter = historyAdapter


//                    requireActivity().supportFragmentManager.beginTransaction()
//                        .add(R.id.붙여넣어야할 프래임레이아웃 아이디, 전환 되고 싶은 프래그먼트())
//                        .addToBackStack("").commit()
                }
            })
            .create()
        dialog.show(childFragmentManager, dialog.tag)
    }
}