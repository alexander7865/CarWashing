package com.mod_int.carwash.ui.dialog.order

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.mod_int.carwash.R
import com.mod_int.carwash.data.source.local.TestRoomViewModel
import com.mod_int.carwash.databinding.FragmentCustomDialogOrderBinding
import com.mod_int.carwash.ext.highFun
import com.mod_int.carwash.ext.wmOrderCount
import com.mod_int.carwash.ui.owner_member.om_activity.OmActivity
import com.mod_int.carwash.ui.owner_member.om_state.OmOrderStateFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomDialogOrderFragment : DialogFragment() {
    private val customDialogOrderViewModel by viewModels<CustomDialogOrderViewModel>()
    private val testRoomViewModel by viewModels<TestRoomViewModel>()
    lateinit var binding: FragmentCustomDialogOrderBinding
    lateinit var ownerActivity: OmActivity

    var noBtn: String? = null
    var yesBtn: String? = null
    var companyName: String? = null
    var listener: CustomDialogOrderListener? = null

    class CustomDialogOrderBuilder {
        private val dialog = CustomDialogOrderFragment()

        fun setNoBtn(noBtn: String): CustomDialogOrderBuilder {
            dialog.noBtn = noBtn
            return this
        }

        fun setYesBtn(yesBtn: String): CustomDialogOrderBuilder {
            dialog.yesBtn = yesBtn
            return this
        }

        fun setBtnClickListener(listener: CustomDialogOrderListener): CustomDialogOrderBuilder {
            dialog.listener = listener
            return this
        }

        fun setCompanyName(name: String): CustomDialogOrderBuilder {
            dialog.companyName = name
            return this
        }

        fun create(): CustomDialogOrderFragment {
            return dialog
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OmActivity) ownerActivity = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomDialogOrderBinding.inflate(inflater, container, false)
        val view = binding.root
        isCancelable = false

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        with(binding) {
            timeSelect()
            orderType1()
            initUi()
            initViewModel()
            customDialogOrderViewModel.getCarInfo()


            tvNo.text = noBtn
            tvNo.setOnClickListener {
                listener?.onClickNegativeBtn()
                dismiss()
            }
            tvYes.text = yesBtn
            tvYes.setOnClickListener {
                customDialogOrderViewModel.saveOmInfo()
                listener?.onClickPositiveBtn()
                dismiss()

                // 버튼 클릭시 다른 프래그먼트로 데이터 보내기~
                val result = "$companyName"
                setFragmentResult("request", bundleOf("senderKey" to result))
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container_owner_find_washer, OmOrderStateFragment())
                    .commit()
            }
            return view
        }
    }

    private fun initUi() {
        lifecycle.addObserver(customDialogOrderViewModel)
        customDialogOrderViewModel.companyName.set(companyName)

        //고차함수 연습중
        highFun(10){

        }

        wmOrderCount(20){
            Log.d("고차함수", "initUi: $companyName 의 $it")

        }
    }

    private fun initViewModel() {
        binding.viewModel = customDialogOrderViewModel
        customDialogOrderViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            (viewState as? CustomDialogOrderViewState)?.let {
                onChangedCustomDialogOrderViewState(viewState)
            }
        }
    }

    private fun onChangedCustomDialogOrderViewState(viewState: CustomDialogOrderViewState) {
        when (viewState) {
            is CustomDialogOrderViewState.CheckPickupDelivery -> {
//                pickupDeliCostCheck()


            }
        }
    }

    //다이얼로그에 스피너 구현
    private fun timeSelect() {
        val time = resources.getStringArray(R.array.timeSelect)
        val timeAdapter = ArrayAdapter(
            requireContext(),
            R.layout.custom_order_spinner, time
        )

        with(binding) {
            timeSelect.adapter = timeAdapter
            timeSelect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedTime = time[position]
                    customDialogOrderViewModel.ordTime.set(selectedTime)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    view!!.setBackgroundColor(Color.TRANSPARENT)
                }
            }
        }
    }

    private fun orderType1() {
        val type1 = resources.getStringArray(R.array.orderType1)
        val type1Adapter = ArrayAdapter(
            requireContext(),
            R.layout.custom_order_spinner, type1
        )

        with(binding) {
            orderType1.adapter = type1Adapter
            orderType1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedType = type1[position]
                    customDialogOrderViewModel.ordType.set(selectedType)
                    customDialogOrderViewModel.getCarInfo()

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    view!!.setBackgroundColor(Color.TRANSPARENT)
                }
            }
        }
    }
}

interface CustomDialogOrderListener {
    fun onClickNegativeBtn()
    fun onClickPositiveBtn()
}




