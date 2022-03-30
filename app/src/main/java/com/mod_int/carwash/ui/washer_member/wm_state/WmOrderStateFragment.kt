package com.mod_int.carwash.ui.washer_member.wm_state

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mod_int.carwash.CustomDialogFragment
import com.mod_int.carwash.CustomDialogListener
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentWmOrderStatusBinding
import com.mod_int.carwash.ui.washer_member.wm_activity.WmActivity
import com.mod_int.carwash.ui.washer_member.wm_home.WmHomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WmOrderStateFragment : BaseFragment<FragmentWmOrderStatusBinding>(
    R.layout.fragment_wm_order_status){

    private val wmOrderStateViewModel by viewModels<WmOrderStateViewModel>()
    lateinit var wmActivity: WmActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is WmActivity) wmActivity = context
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //디테일 페이지에서 리스트로 돌아가게 되었을시 리프레쉬가 안됩니다. notifyDataSetChanged() 없어서 그런듯 합니다
        //일단 그전 페이지 이동은 이렇게 해놨습니다. 상관이 없을듯 합니다.
        binding.btnBackToOrderList.setOnClickListener {
            wmActivity.goListOrderWasher()
        }

        binding.tvPhoneNumber.setOnClickListener {
            var intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:01082277865")
            startActivity(intent)
        }


        //커스텀 다이얼로그를 직접 구현했습니다 문제가 뒤로가기 클릭후 페이지로 들어왔을시 프로그래스바가 먼저 발동 되네요
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
//                val getTime = Date()
//                val formatType = SimpleDateFormat("HH:mm:ss")
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
//                        btnPickUp.text = formatType.format(getTime)
//                        위 스트링값 변경시 현재시간으로도 표현가능함 혹시나해서 가지고 있음
                        }
                    })
                    .create()
                dialog.show(childFragmentManager, dialog.tag)
            }
        }
    }
}