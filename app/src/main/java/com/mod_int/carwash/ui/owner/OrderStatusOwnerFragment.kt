package com.mod_int.carwash.ui.owner

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentOwnerOrderStatusBinding

class OrderStatusOwnerFragment : BaseFragment<FragmentOwnerOrderStatusBinding>(
    R.layout.fragment_owner_order_status) {

    lateinit var ownerActivity: OwnerActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OwnerActivity) ownerActivity = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.tvPhoneNumberOwner.setOnClickListener {
            var intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:01082277865")
            startActivity(intent)
        }


        //상황 데이터 받는내용 코팅해야함 (데이터 받는 방법 스터디해야함)


        //커스텀 파일로 변경해야함

        binding.btnGoHistory.setOnClickListener {
            ownerActivity.pickupCfmDialog()

        }
    }
}