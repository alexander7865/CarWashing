package com.mod_int.carwash.ui.washer

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.mod_int.carwash.CustomDialogFragment
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentWasherOrderStatusBinding
import java.text.SimpleDateFormat
import java.util.*

class WasherOrderStatusFragment : BaseFragment<FragmentWasherOrderStatusBinding>(
    R.layout.fragment_washer_order_status) {

    lateinit var washerActivity: WasherActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is WasherActivity) washerActivity = context
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //디테일 페이지에서 리스트로 돌아가게 되었을시 리프레쉬가 안됩니다. notifyDataSetChanged() 없어서 그런듯 합니다
        //일단 그전 페이지 이동은 이렇게 해놨습니다. 상관이 없을듯 합니다.
        binding.btnBackToOrderList.setOnClickListener {
            washerActivity.goListOrderWasher()
        }

        binding.tvPhoneNumber.setOnClickListener {
            var intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:01082277865")
            startActivity(intent)
        }


        with(binding) {
            btnPickUp.setOnClickListener {
                val builder = AlertDialog.Builder(context,R.style.AppCompatAlertDialog)
                val getTime = Date()
                val formatType = SimpleDateFormat("HH:mm:ss")

                builder.setTitle("차량이 입고 되었나요?")
                builder.setCancelable(false)
                builder.setMessage("확인 버튼을 클릭하시고 신속히 세차 작업을 진행하여 주시기 바랍니다")
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

                builder.setTitle("세차작업이 완료되었나요?")
                builder.setCancelable(false)
                builder.setMessage("확인 버튼을 클릭하시고 픽업 매니저에게 차량을 인계하여 주시기 바랍니다")
                builder.setPositiveButton("네") {
                        dialogInterface: DialogInterface, i: Int ->
                    with(binding){
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
        }
    }
}