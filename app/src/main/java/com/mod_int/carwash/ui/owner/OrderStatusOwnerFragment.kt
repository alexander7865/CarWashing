package com.mod_int.carwash.ui.owner

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentOrderStatusOwnerBinding
import java.text.SimpleDateFormat

class OrderStatusOwnerFragment : BaseFragment<FragmentOrderStatusOwnerBinding>(
    R.layout.fragment_order_status_owner) {

    lateinit var ownerActivity: OwnerActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OwnerActivity) ownerActivity = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val hi = arguments?.getString("hi")
        Log.d("값", "onViewCreated: $hi")

        super.onViewCreated(view, savedInstanceState)

        binding.tvPhoneNumberOwner.setOnClickListener {
            var intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:01082277865")
            startActivity(intent)
        }

        binding.btnGoHistory.setOnClickListener {
            val builder = AlertDialog.Builder(context,R.style.AppCompatAlertDialog)

            builder.setTitle("세차가 완료 되었습니다")
            builder.setCancelable(false)
            builder.setMessage("세차를 확인하셨다면 '확인' 버튼을 클릭해주세요! 세차이력은 [관리현황] 에서 확인 할 수 있습니다.")
            builder.setPositiveButton("확인") {
                    dialogInterface: DialogInterface, i: Int ->
                ownerActivity.goBlankPage()
            }

            builder.setNegativeButton("미확인") {
                    dialogInterface: DialogInterface, i: Int ->
            }
            builder.show()

        }

//            val formatType = SimpleDateFormat("HH:mm:ss")
//                tvFinished.visibility = View.VISIBLE
//                btnFinished.visibility = View.VISIBLE
//                btnDeliver.isEnabled = false
//                btnDeliver.setBackgroundColor(Color.TRANSPARENT)
//                btnDeliver.setTextColor(Color.parseColor("#FFA83E"))
//                btnDeliver.setTextSize(5,2.7F)
//                btnDeliver.text = formatType.format(getTime)


    }
}