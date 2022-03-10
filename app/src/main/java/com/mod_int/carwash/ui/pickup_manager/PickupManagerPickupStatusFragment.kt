package com.mod_int.carwash.ui.pickup_manager

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentPickupManagerPickupStatusBinding
import java.text.SimpleDateFormat
import java.util.*

class PickupManagerPickupStatusFragment : BaseFragment<FragmentPickupManagerPickupStatusBinding>(
    R.layout.fragment_pickup_manager_pickup_status) {

    private lateinit var pickupManagerActivity: PickupManagerActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is PickupManagerActivity) pickupManagerActivity = context

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBackToPickupList.setOnClickListener {
            pickupManagerActivity.goListOrderPickup()
        }

        binding.tvPhoneNumberPickupManager.setOnClickListener {
            var intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:01082277865")
            startActivity(intent)
        }

        with(binding) {
            btnPickUp.setOnClickListener {
                val builder = AlertDialog.Builder(context,R.style.AppCompatAlertDialog)
                val getTime = Date()
                val formatType = SimpleDateFormat("HH:mm:ss")

                builder.setTitle("픽업을 완료 하셨나요?")
                builder.setCancelable(false)
                builder.setMessage("픽업전 본인의 휴대폰으로 차량의 외부/내부 이미지 캡쳐하여 차주에게 보내주세요!")
                builder.setPositiveButton("네") {
                        dialogInterface: DialogInterface, i: Int ->
                    with(binding){
                        tvDeliver.visibility = View.VISIBLE
                        btnDeliver.visibility = View.VISIBLE
                        btnPickUp.isEnabled = false
                        btnPickUp.setBackgroundColor(Color.TRANSPARENT)
                        btnPickUp.setTextColor(Color.parseColor("#FFA83E"))
                        btnPickUp.setTextSize(5,2.7F)
                        btnPickUp.text = formatType.format(getTime)
                    }
                }

                builder.setNegativeButton("아니요") {
                        dialogInterface: DialogInterface, i: Int ->
                }
                builder.show()

            }

            btnDeliver.setOnClickListener {
                val builder = AlertDialog.Builder(context,R.style.AppCompatAlertDialog)
                val getTime = Date()
                val formatType = SimpleDateFormat("HH:mm:ss")

                builder.setTitle("탁송을 시작 하셨나요?")
                builder.setCancelable(false)
                builder.setMessage("탁송을 시작하시면 다음 세차 작업건을 받을 수 있습니다. 조심히 안전하게 운전해주세요!")
                builder.setPositiveButton("네") {
                        dialogInterface: DialogInterface, i: Int ->
                    with(binding){
                        tvFinished.visibility = View.VISIBLE
                        btnFinished.visibility = View.VISIBLE
                        btnDeliver.isEnabled = false
                        btnDeliver.setBackgroundColor(Color.TRANSPARENT)
                        btnDeliver.setTextColor(Color.parseColor("#FFA83E"))
                        btnDeliver.setTextSize(5,2.7F)
                        btnDeliver.text = formatType.format(getTime)
                    }
                }

                builder.setNegativeButton("아니요") {
                        dialogInterface: DialogInterface, i: Int ->
                }
                builder.show()

            }

            btnFinished.setOnClickListener {
                val builder = AlertDialog.Builder(context,R.style.AppCompatAlertDialog)
                val getTime = Date()
                val formatType = SimpleDateFormat("HH:mm:ss")

                builder.setTitle("탁송을 완료 하셨나요?")
                builder.setCancelable(false)
                builder.setMessage("모든 작업이 완료 되었습니다. 마지막으로 탁송 후 차량의 외부/내부 이미지를 캡쳐하여 차주에게 보내주세요!")
                builder.setPositiveButton("네") {
                        dialogInterface: DialogInterface, i: Int ->
                    with(binding){
                        btnFinished.isEnabled = false
                        btnFinished.setBackgroundColor(Color.TRANSPARENT)
                        btnFinished.setTextColor(Color.parseColor("#FFA83E"))
                        btnFinished.setTextSize(5,2.7F)
                        btnFinished.text = formatType.format(getTime)
                        pickupManagerActivity.goBlackTipScreen()
                    }
                }

                builder.setNegativeButton("아니요") {
                        dialogInterface: DialogInterface, i: Int ->
                }
                builder.show()

            }
        }
    }
}