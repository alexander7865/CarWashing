package com.mod_int.carwash.ui.owner_member.om_state

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.core.os.HandlerCompat.postDelayed
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentOmOrderStatusBinding
import com.mod_int.carwash.ext.showSpinner
import com.mod_int.carwash.model.PriceItem
import com.mod_int.carwash.ui.blank.OmBlankFragment
import com.mod_int.carwash.ui.dialog.CustomDialogFragment
import com.mod_int.carwash.ui.dialog.CustomDialogListener
import com.mod_int.carwash.ui.dialog.WmPriceDialogViewModel
import com.mod_int.carwash.ui.dialog.order.CustomDialogOrderViewModel
import com.mod_int.carwash.ui.dialog.order.CustomDialogOrderViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OmOrderStateFragment : BaseFragment<FragmentOmOrderStatusBinding>(
    R.layout.fragment_om_order_status
) {
    private val omOrderStateViewModel by viewModels<OmOrderStateViewModel>()

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
        omOrderStateViewModel.orderStateInfo()

    }

    private fun initUi() {
        washingPointSelect()
        pickupPointSelect()
        setFragmentResultListener("request"){ _, bundle ->
            val result = bundle.getString("senderKey")
            omOrderStateViewModel.companyName.set("$result")
            if(omOrderStateViewModel.companyName.get()?.isNotEmpty() == true ) {
                omOrderStateViewModel.wmBankInfo()

            }
        }
    }

    private fun initViewModel() {
        binding.viewModel = omOrderStateViewModel
        omOrderStateViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            (viewState as? OmOrderStateViewState)?.let {
                onChangedOrderSateViewState(viewState)
            }
        }
    }


    private fun onChangedOrderSateViewState(viewState: OmOrderStateViewState) {
        when (viewState) {
            is OmOrderStateViewState.RouteHistory -> {
                pickupCfmDialog()
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

    //데이터를 받아와서 아래 버튼 클릭 활성화시 터져버리는 현상 발생
    private fun washerMemberPhoneNr() {
        var intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("")
        startActivity(intent)

    }
}