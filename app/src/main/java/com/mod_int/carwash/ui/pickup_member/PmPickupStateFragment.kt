package com.mod_int.carwash.ui.pickup_member

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.mod_int.carwash.CustomDialogFragment
import com.mod_int.carwash.CustomDialogListener
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentPmPickupStatusBinding

class PmPickupStateFragment : BaseFragment<FragmentPmPickupStatusBinding>(
    R.layout.fragment_pm_pickup_status) {

    private lateinit var pmActivity: PmActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is PmActivity) pmActivity = context

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBackToPickupList.setOnClickListener {
            pmActivity.goListOrderPickup()
        }

        binding.tvPhoneNumberPickupManager.setOnClickListener {
            var intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:01082277865")
            startActivity(intent)
        }

        with(binding) {

            btnPickUp.setOnClickListener {
                val dialog = CustomDialogFragment.CustomDialogBuilder()
                    .setTitle("차량을 픽업 하셨나요?")
                    .setQuestion("차량을 확인 후 픽업하셨다면, 작업장으로 차량을 인도하여 주세요!")
                    .setNoBtn("미확인")
                    .setYesBtn("픽업완료")
                    .setBtnClickListener(object : CustomDialogListener {
                        override fun onClickNegativeBtn() {

                        }

                        override fun onClickPositiveBtn() {
                            progressBar.progress = 1
                            btnDeliver.isEnabled = true
                            //헤드워셔가 세차완료를 컴펌하면 아래 버튼 활성화 +
                            btnDeliver.setBackgroundResource(R.drawable.button_action_lorange)
                            //헤드워셔가 차량을 전달하여 입고컴펌하면 아래 텍스트로 변경
                            tvCfmStatus.text = "워셔가 차량 인도를 확인하였습니다. 세차자가 완료되면 탁송을 시작하세요!"
                            btnPickUp.isEnabled = false
                            btnPickUp.setBackgroundColor(Color.TRANSPARENT)
                            btnPickUp.setTextColor(Color.parseColor("#FFA83E"))
                            btnPickUp.setTextSize(5, 2.7F)
                            btnPickUp.text = "픽업완료"
//                        btnPickUp.text = formatType.format(getTime)
//                        위 스트링값 변경시 현재시간으로도 표현가능함
                        }
                    })
                    .create()
                dialog.show(childFragmentManager, dialog.tag)
            }

            btnDeliver.setOnClickListener {
                val dialog = CustomDialogFragment.CustomDialogBuilder()
                    .setTitle("탁송을 시작 하시나요?")
                    .setQuestion("조심히 운전하여 고객 또는 지정된 장소에 차량을 인도 하여주세요!")
                    .setNoBtn("미확인")
                    .setYesBtn("탁송시작")
                    .setBtnClickListener(object : CustomDialogListener {
                        override fun onClickNegativeBtn() {

                        }

                        override fun onClickPositiveBtn() {
                            progressBar.progress = 2
                            btnFinished.isEnabled = true
                            //완료되면 아래내용 활성화 & 알람
                            btnFinished.setBackgroundResource(R.drawable.button_action_lorange)
                            tvCfmStatus.text = "탁송완료 후 차량의 외부/내부 이미지를 캡쳐하여 차주에게 보내주세요!"
                            btnDeliver.isEnabled = false
                            btnDeliver.setBackgroundColor(Color.TRANSPARENT)
                            btnDeliver.setTextColor(Color.parseColor("#FFA83E"))
                            btnDeliver.setTextSize(5,2.7F)
                            btnDeliver.text = "탁송시작"
//                        btnPickUp.text = formatType.format(getTime)
//                        위 스트링값 변경시 현재시간으로도 표현가능함
                        }
                    })
                    .create()
                dialog.show(childFragmentManager, dialog.tag)
            }

            btnFinished.setOnClickListener {
                val dialog = CustomDialogFragment.CustomDialogBuilder()
                    .setTitle("탁송을 완료 하셨나요?")
                    .setQuestion("최종 탁송완료를 확인해주세요!")
                    .setNoBtn("미확인")
                    .setYesBtn("탁송완료")
                    .setBtnClickListener(object : CustomDialogListener {
                        override fun onClickNegativeBtn() {

                        }

                        override fun onClickPositiveBtn() {
                            progressBar.progress = 3
                            btnFinished.isEnabled = false
                            tvCfmStatus.text = "축하합니다. 픽업/탁송이 완료 되었습니다"
                            btnFinished.setBackgroundColor(Color.TRANSPARENT)
                            btnFinished.setTextColor(Color.parseColor("#FFA83E"))
                            btnFinished.setTextSize(5,2.7F)
                            btnFinished.text = "탁송완료"

                            //아래 기능 구현시 앱이 터지는 현상이 발생함
//                            pickupManagerActivity.goBlankTipScreen()

//                        btnPickUp.text = formatType.format(getTime)
//                        위 스트링값 변경시 현재시간으로도 표현가능함
                        }
                    })
                    .create()
                dialog.show(childFragmentManager, dialog.tag)
            }
        }
    }
}