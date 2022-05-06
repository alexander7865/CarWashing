package com.mod_int.carwash.ui.washer_member.wm_state

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mod_int.carwash.ui.dialog.CustomDialogFragment
import com.mod_int.carwash.ui.dialog.CustomDialogListener
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentWmOrderStatusBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WmOrderStateFragment : BaseFragment<FragmentWmOrderStatusBinding>(
    R.layout.fragment_wm_order_status){

    private val wmOrderStateViewModel by viewModels<WmOrderStateViewModel>()

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun initUi() {
        ordCfmDialog()
        wmOrderStateViewModel.orderStateInfo()
        with(binding){
            tvPhoneNumber.setOnClickListener {
                callPickupMember()
            }
        }
    }

    private fun initViewModel() {
        binding.viewModel = wmOrderStateViewModel
        wmOrderStateViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            (viewState as? WmOrderStateViewState)?.let {
                onChangedOrderSateViewState(
                    viewState
                )
            }
        }
    }

    private fun onChangedOrderSateViewState(viewState: WmOrderStateViewState) {
        when (viewState) {
            is WmOrderStateViewState.RouteOrderList -> {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun ordCfmDialog(){
        with(binding) {
            btnInputCar.setOnClickListener {
                val dialog = CustomDialogFragment.CustomDialogBuilder()
                    .setTitle("차량이 입고 되었나요?")
                    .setQuestion("입고확인 등록을 하시고 신속히 세차 작업을 진행하여 주시기 바랍니다.")
                    .setNoBtn("미입고")
                    .setYesBtn("입고완료")
                    .setBtnClickListener(object : CustomDialogListener {
                        override fun onClickNegativeBtn() {
                        }

                        override fun onClickPositiveBtn() {
                            progressBar.progress = 1
                            tvStatus.text = "세차를 진행하시고 진행이 완료되면 '작업확인' 등록을 해주세요!"
                            btnWashingFinished.setBackgroundResource(R.drawable.button_action_lorange)
                            btnWashingFinished.isEnabled = true
                            btnInputCar.isEnabled = false
                            btnInputCar.setBackgroundColor(Color.TRANSPARENT)
                            btnInputCar.setTextColor(Color.parseColor("#FFA83E"))
                            btnInputCar.setTextSize(5, 2.7F)
                            btnInputCar.text = "입고완료"
                        }
                    })
                    .create()
                dialog.show(childFragmentManager, dialog.tag)
            }

            btnWashingFinished.setOnClickListener {
                val dialog = CustomDialogFragment.CustomDialogBuilder()
                    .setTitle("세차작업이 완료되었나요?")
                    .setQuestion("작업완료를 등록하시면, 곧 픽업매니저가 도착할 예정입니다.")
                    .setNoBtn("작업중")
                    .setYesBtn("작업완료")
                    .setBtnClickListener(object : CustomDialogListener {
                        override fun onClickNegativeBtn() {

                        }

                        override fun onClickPositiveBtn() {
                            progressBar.progress = 2
                            tvStatus.text = "수고하셨습니다. 모든 작업이 완료되었습니다."
                            btnWashingFinished.isEnabled = false
                            btnWashingFinished.setBackgroundColor(Color.TRANSPARENT)
                            btnWashingFinished.setTextColor(Color.parseColor("#FFA83E"))
                            btnWashingFinished.setTextSize(5,2.7F)
                            btnWashingFinished.text = "세차완료"
                        }
                    })
                    .create()
                dialog.show(childFragmentManager, dialog.tag)
            }
        }
    }

    private fun callPickupMember() {
        var intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:01082277865")
        startActivity(intent)
    }
}