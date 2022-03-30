package com.mod_int.carwash.ui.owner_member.om_state

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentOmOrderStatusBinding
import com.mod_int.carwash.ui.owner_member.om_activity.OmActivity
import com.mod_int.carwash.ui.owner_member.om_join.OmJoinViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OmOrderStateFragment : BaseFragment<FragmentOmOrderStatusBinding>(
    R.layout.fragment_om_order_status) {

    private val omOrderStateViewModel by viewModels<OmOrderStateViewModel>()
    lateinit var ownerActivity: OmActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OmActivity) ownerActivity = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        washingPointSelect()
        pickupPointSelect()



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

    //스피너 구현
    private fun washingPointSelect() {
        val brand = resources.getStringArray(R.array.washingPickupPointSelect)
        val brandAdapter = ArrayAdapter (requireContext(),
            R.layout.custom_find_spinner, brand)

        with(binding){
            washingPoint.adapter = brandAdapter
            washingPoint.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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
    }

    private fun pickupPointSelect() {
        val brand = resources.getStringArray(R.array.washingPickupPointSelect)
        val brandAdapter = ArrayAdapter (requireContext(),
            R.layout.custom_find_spinner, brand)

        with(binding){
            pickupPoint.adapter = brandAdapter
            pickupPoint.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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
    }


}