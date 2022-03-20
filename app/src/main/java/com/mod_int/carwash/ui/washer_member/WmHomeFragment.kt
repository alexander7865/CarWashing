package com.mod_int.carwash.ui.washer_member

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentWmHomeBinding

class WmHomeFragment : BaseFragment<FragmentWmHomeBinding>(R.layout.fragment_wm_home) {

    lateinit var wmActivity: WmActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is WmActivity) wmActivity = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        workSelect()

        //픽업/탁송비용 정산
        binding.btnPaymentWahser.setOnClickListener {
            wmActivity.paymentWasher()
//            val toastCenter = Toast.makeText(washerActivity,"픽업/탁송 비용을 정산하세요", Toast.LENGTH_SHORT)
//            toastCenter.setGravity(Gravity.CENTER,0,0)
//            toastCenter.show()
        }
    }

    //스피너
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