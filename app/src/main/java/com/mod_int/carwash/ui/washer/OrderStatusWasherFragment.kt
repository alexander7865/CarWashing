package com.mod_int.carwash.ui.washer

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import androidx.fragment.app.setFragmentResult
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentHomeWasherBinding
import com.mod_int.carwash.databinding.FragmentOrderStatusWasherBinding
import com.mod_int.carwash.ui.owner.HomeOwnerFragment
import com.mod_int.carwash.ui.owner.OwnerActivity
import java.util.*

class OrderStatusWasherFragment : BaseFragment<FragmentOrderStatusWasherBinding>(
    R.layout.fragment_order_status_washer) {

    lateinit var washerActivity: WasherActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is WasherActivity) washerActivity = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvPhoneNumber.setOnClickListener {
            var intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:821082277865")
            startActivity(intent)
        }


        with(binding) {
            btnOrdCfm.setOnClickListener {
                val builder = AlertDialog.Builder(context,R.style.AppCompatAlertDialog)
                val getTime = Date()
                val formatType = SimpleDateFormat("HH:mm:ss")

                builder.setTitle("세차 작업이 가능합니까?")
                builder.setCancelable(false)
                builder.setMessage("가능하다면 신속히 차량의 위치로 이동 하여 차주와 연락을 취해 해주세요!")
                builder.setPositiveButton("네") {
                        dialogInterface: DialogInterface, i: Int ->
                    with(binding){
                        tvPickUp.visibility = View.VISIBLE
                        btnPickUp.visibility = View.VISIBLE
                        btnOrdCfm.isEnabled = false
                        btnOrdCfm.setBackgroundColor(Color.TRANSPARENT)
                        btnOrdCfm.setTextColor(Color.parseColor("#FFA83E"))
                        btnOrdCfm.setTextSize(5,2.8F)
                        btnOrdCfm.text = formatType.format(getTime)
                    }
                }

                builder.setNegativeButton("아니요") {
                        dialogInterface: DialogInterface, i: Int ->
                  // 아니오 클릭시 화면이동입니다 (그리고 최초 화면으로 만들고 싶습니다 어떻게 해야 할까요?)
                    washerActivity.goBlackScreen()
                }
                builder.show()

            }

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
                        btnPickUp.setTextSize(5,2.8F)
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
                        btnDeliver.setTextSize(5,2.8F)
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
                        btnFinished.setTextSize(5,2.8F)
                        btnFinished.text = formatType.format(getTime)
                        washerActivity.goBlackScreen()
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