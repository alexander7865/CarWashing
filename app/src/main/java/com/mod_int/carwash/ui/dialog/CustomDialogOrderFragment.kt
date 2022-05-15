package com.mod_int.carwash.ui.dialog

import android.app.Application
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
import android.widget.EditText
import androidx.databinding.ObservableField
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.firebase.firestore.SetOptions
import com.google.firestore.v1.StructuredQuery
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.base.ViewState
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.databinding.FragmentCustomDialogOrderBinding
import com.mod_int.carwash.ext.ioScope
import com.mod_int.carwash.ui.owner_member.om_activity.OmActivity
import com.mod_int.carwash.ui.owner_member.om_state.OmOrderStateFragment
import com.mod_int.carwash.ui.pickup_member.pm_registration.PmRegistrationViewModel
import com.mod_int.carwash.ui.pickup_member.pm_registration.PmRegistrationViewState
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import java.time.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
class CustomDialogOrderFragment : DialogFragment() {
    private val customDialogOrderViewModel by viewModels<CustomDialogOrderViewModel>()
    lateinit var binding: FragmentCustomDialogOrderBinding
    lateinit var ownerActivity: OmActivity

    var noBtn : String? = null
    var yesBtn : String? = null
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
        fun create(): CustomDialogOrderFragment {
            return dialog
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OmActivity) ownerActivity = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { binding = FragmentCustomDialogOrderBinding.inflate(inflater,container,false)
        val view = binding.root
        isCancelable = false

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        with(binding){
            timeSelect()
            orderType1()
            initUi()
            initViewModel()

            tvNo.text = noBtn
            tvNo.setOnClickListener {
                listener?.onClickNegativeBtn()
                dismiss()
            }
            tvYes.text = yesBtn
            tvYes.setOnClickListener {
                listener?.onClickPositiveBtn()
                dismiss()
            }
            return view
        }
    }

    private fun initUi(){
    customDialogOrderViewModel.getCarInfo()
    }

    private fun initViewModel(){
        binding.viewModel = customDialogOrderViewModel
        customDialogOrderViewModel.viewStateLiveData.observe(viewLifecycleOwner){ viewState ->
            (viewState as? CustomDialogOrderViewState)?.let {
                onChangedCustomDialogOrderViewState(viewState)
            }
        }
    }

    private fun onChangedCustomDialogOrderViewState(viewState: CustomDialogOrderViewState){
        when(viewState){
            is CustomDialogOrderViewState.SaveOmInfo -> {
                customDialogOrderViewModel.saveOmInfo()

            }
        }
    }

    //다이얼로그에 스피너 구현
    private fun timeSelect() {
        val time = resources.getStringArray(R.array.timeSelect)
        val timeAdapter = ArrayAdapter (requireContext(),
            R.layout.custom_order_spinner, time)

        with(binding){
            timeSelect.adapter = timeAdapter
            timeSelect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedTime = time[position]
                    customDialogOrderViewModel.time.set(selectedTime)
                    Log.d("값", "$selectedTime")

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    view!!.setBackgroundColor(Color.TRANSPARENT)
                }
            }
        }
    }

    private fun orderType1() {
        val type1 = resources.getStringArray(R.array.orderType1)
        val type1Adapter = ArrayAdapter (requireContext(),
            R.layout.custom_order_spinner, type1)

        with(binding){
            orderType1.adapter = type1Adapter
            orderType1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedType = type1[position]
                    customDialogOrderViewModel.type.set(selectedType)
                    Log.d("값", "$selectedType")
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

@HiltViewModel
class CustomDialogOrderViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app){

    private val current: LocalDateTime = LocalDateTime.now()
    var orderDate = ObservableField("${current.year}년 ${current.monthValue}월 ${current.dayOfMonth}일")
    var carSize = ObservableField("")
    var time = ObservableField("")
    var type = ObservableField("")
    var amount = ObservableField("")
    var orderMsg = ObservableField("")

    fun getCarInfo(){
        ioScope {
            val email = firebaseRepository.getFirebaseAuth().currentUser!!.email
            firebaseRepository.getFirebaseFireStore().collection("OwnerMember")
                .document("$email")
                .get()
                .addOnSuccessListener { document ->
                    if (document["carSize"].toString().isNotEmpty()) {
                        carSize.set(document.get("carSize") as String)
                        Log.d("가지고온값", "getCarInfo: ${document["carSize"].toString()}")

                    }
                }

        }
    }

    fun saveOmInfo(){
        ioScope {
            val data = OrderOmInfo(
                orderDate.get()!!,
                time.get()!!,
                type.get()!!,
                amount.get()!!,
                orderMsg.get()!!,
            )
            val email = firebaseRepository.getFirebaseAuth().currentUser!!.email
            firebaseRepository.getFirebaseFireStore().collection("OwnerMember")
                .document("$email")
                .set(data, SetOptions.merge())
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        Log.d("가지고온값", "$data")

                    }
                }

        }
    }

//    private fun checkInfo(
//        timeCheck: Boolean,
//        typeCheck: Boolean,
//        amountCheck: Boolean,
//        orderMsgCheck: Boolean,
//
//    ): OrderOmInfo? {
//        return if (timeCheck && typeCheck && amountCheck && orderMsgCheck
//        ) {
//            OrderOmInfo(
//                time.get()!!,
//                type.get()!!,
//                amount.get()!!,
//                orderMsg.get()!!,
//            )
//
//        } else {
//            null
//        }
//    }

    private fun orderDate(): Boolean {
        return when {
            orderDate.get()?.isEmpty() == true -> {
                viewStateChanged(PmRegistrationViewState.Msg(message = "정보를 모두 입력하세요!"))
                false
            }
            else -> {
                true
            }
        }
    }

    private fun timeCheck(): Boolean {
        return when {
            time.get()?.isEmpty() == true -> {
                viewStateChanged(PmRegistrationViewState.Msg(message = "정보를 모두 입력하세요!"))
                false
            }
            else -> {
                true
            }
        }
    }
    private fun typeCheck(): Boolean {
        return when {
            type.get()?.isEmpty() == true -> {
                viewStateChanged(PmRegistrationViewState.Msg(message = "정보를 모두 입력하세요!"))
                false
            }
            else -> {
                true
            }
        }
    }
    private fun amountCheck(): Boolean {
        return when {
            amount.get()?.isEmpty() == true -> {
                viewStateChanged(PmRegistrationViewState.Msg(message = "정보를 모두 입력하세요!"))
                false
            }
            else -> {
                true
            }
        }
    }
    private fun orderMsgCheck(): Boolean {
        return when {
            orderMsg.get()?.isEmpty() == true -> {
                viewStateChanged(PmRegistrationViewState.Msg(message = "정보를 모두 입력하세요!"))
                false
            }
            else -> {
                true
            }
        }
    }
}

sealed class CustomDialogOrderViewState : ViewState {
    data class SaveOmInfo (val item : List<OrderOmInfo>) : CustomDialogOrderViewState()
}

data class OrderOmInfo(
    var orderDate : String = "",
    var orderReservationTime : String = "",
    var orderType : String = "",
    var orderAmount : String = "",
    var orderMassage : String = "",

)