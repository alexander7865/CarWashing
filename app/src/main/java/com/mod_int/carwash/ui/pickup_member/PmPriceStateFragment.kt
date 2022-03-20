package com.mod_int.carwash.ui.pickup_member

import android.content.Context
import android.os.Bundle
import android.view.View
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentPmPriceStatusBinding

class PmPriceStateFragment : BaseFragment<FragmentPmPriceStatusBinding>(
    R.layout.fragment_pm_price_status) {

    private lateinit var pmActivity: PmActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is PmActivity) pmActivity = context

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //정산요청으로 이동
//        binding.btnJoin.setOnClickListener {
//            ownerActivity.joinRegistration()
//            val toastCenter = Toast.makeText(context,"정확한 정보를 입력하세요!",Toast.LENGTH_SHORT)
//            toastCenter.setGravity(Gravity.CENTER,0,0)
//            toastCenter.show()
//        }
    }

}