package com.mod_int.carwash.ui.pickup_member

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentPmHomeBinding

class PmHomeFragment : BaseFragment<FragmentPmHomeBinding>(
    R.layout.fragment_pm_home) {

    private lateinit var pmActivity: PmActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is PmActivity) pmActivity = context

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        workSelect()

        //정산요청으로 이동
        binding.btnSettlementRequest.setOnClickListener {
            pmActivity.settlementRequest()
//            val toastCenter = Toast.makeText(pickupManagerActivity,"정산요청을 진행하세요", Toast.LENGTH_SHORT)
//            toastCenter.setGravity(Gravity.CENTER,0,0)
//            toastCenter.show()
        }
    }

    //스피너구현
    private fun workSelect() {
        val brand = resources.getStringArray(R.array.workSettingSelect)
        val brandAdapter = ArrayAdapter (requireContext(),
            R.layout.custom_washer_spinner, brand)

        with(binding){
            workSetting.adapter = brandAdapter
            workSetting.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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