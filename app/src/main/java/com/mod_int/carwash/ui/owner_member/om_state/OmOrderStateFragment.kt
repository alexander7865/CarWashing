package com.mod_int.carwash.ui.owner_member.om_state

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import com.mod_int.carwash.CustomDialogFragment
import com.mod_int.carwash.CustomDialogListener
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentOmOrderStatusBinding
import com.mod_int.carwash.ext.showSpinner
import com.mod_int.carwash.ui.blank.OmBlankFragment
import com.mod_int.carwash.ui.owner_member.om_join.OmJoinFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OmOrderStateFragment : BaseFragment<FragmentOmOrderStatusBinding>(
    R.layout.fragment_om_order_status
) {
    private val omOrderStateViewModel by viewModels<OmOrderStateViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()

        //상황 데이터 받는내용 코팅해야함 (데이터 받는 방법 스터디예정)
    }

    private fun initUi() {
        omOrderStateViewModel.orderStateInfo()
        washingPointSelect()
        pickupPointSelect()

    }

    private fun initViewModel() {
        binding.viewModel = omOrderStateViewModel
        omOrderStateViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            (viewState as? OmOrderStateViewState)?.let {
                onChangedOrderSateViewState(
                    viewState
                )
            }
        }
    }

    private fun onChangedOrderSateViewState(viewState: OmOrderStateViewState) {
        when (viewState) {
            is OmOrderStateViewState.RouteHistory -> {
                pickupCfmDialog()
            }

            is OmOrderStateViewState.WasherMemberPhoneNr -> {
                washerMemberPhoneNr()

            }
        }
    }


    private fun pickupCfmDialog() {
        val dialog = CustomDialogFragment.CustomDialogBuilder()
            .setTitle("차량을 확인하셨나요?")
            .setQuestion("차량을 확인하셨다면 '확인' 버튼을 클릭해주세요! 세차 이력은 [관리현황] 에서\n확인 할 수 있습니다.")
            .setNoBtn("나중에 확인")
            .setYesBtn("확인완료")
            .setBtnClickListener(object : CustomDialogListener {
                override fun onClickNegativeBtn() {
                    //불가능한경우 행동
                }

                override fun onClickPositiveBtn() {
                    routeOmBlackFragment()
                }
            }).create()
        dialog.show(requireActivity().supportFragmentManager, dialog.tag)
    }

    private fun routeOmBlackFragment() {
        parentFragmentManager.beginTransaction().add(
            R.id.container_om_order_state,
            OmBlankFragment()
        ).addToBackStack("OmBlankFragment").commit()
    }

    //스피너 세차점수
    private fun washingPointSelect() {
        showSpinner(
            binding.washingPoint,
            resources.getStringArray(R.array.washingPickupPointSelect)
        ).onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                view!!.setBackgroundColor(Color.TRANSPARENT)
            }
        }
    }

    //스피너 픽업점수
    private fun pickupPointSelect() {
        showSpinner(
            binding.pickupPoint,
            resources.getStringArray(R.array.washingPickupPointSelect)
        ).onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                view!!.setBackgroundColor(Color.TRANSPARENT)
            }
        }
    }

    private fun washerMemberPhoneNr() {
        //워셔 전화번호로 input 해야함 (임시로 만들어둠)
        var intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:01082277865")
        startActivity(intent)
    }
}