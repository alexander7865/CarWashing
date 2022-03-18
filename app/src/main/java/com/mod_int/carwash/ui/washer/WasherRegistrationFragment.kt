package com.mod_int.carwash.ui.washer

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentWasherRegistrationBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class WasherRegistrationFragment : BaseFragment<FragmentWasherRegistrationBinding>(
    R.layout.fragment_washer_registration) {

    lateinit var washerActivity: WasherActivity
    private var fireStore: FirebaseFirestore? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is WasherActivity) washerActivity = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fireStore = FirebaseFirestore.getInstance()

        bankSelect()

//        pickupManagerCount()

        with(binding){
            btnSaveWasherInfo.setOnClickListener {
                saveInfoWasher()
            }
        }
    }

    private fun saveInfoWasher() {
        val washerInfo = WasherInfo(
            accountName = binding.accountName.text.toString(),
            bankName = binding.bankName.toString(),
            accountNumber = binding.accountNumber.text.toString(),
            tvPhoneNoWasher = binding.tvPhoneNoWasher.text.toString(),
            tvPickupManagerNum = binding.tvPickupManagerNum.text.toString(),
            washingLocation = binding.washingLocation.text.toString()
        )



        CoroutineScope(Dispatchers.Main).launch {
            val accountNameInputCheck = async { accountNameInputCheck() }
            val bankNameInputCheck = async { bankNameInputCheck() }
            val accountNumberInputCheck = async { accountNumberInputCheck() }
            val washingLocationInputCheck = async { washingLocationInputCheck() }
            if (accountNameInputCheck.await() && bankNameInputCheck.await() && accountNumberInputCheck.await() && washingLocationInputCheck.await()) {
                val user = Firebase.auth.currentUser
                user?.let {
                    val email = user.email
                    fireStore?.collection(email.toString())?.document(
                        "WasherInfo"
                    )?.set(washerInfo)?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            enableSetting(false)
                            val toastCenter =
                                Toast.makeText(washerActivity, "정보가 저장되었습니다", Toast.LENGTH_SHORT)
                            toastCenter.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, 0)
                            toastCenter.show()
                        } else {
                            val toastCenter =
                                Toast.makeText(washerActivity, "정보가 저장되지 않았습니다", Toast.LENGTH_SHORT)
                            toastCenter.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, 0)
                            toastCenter.show()
                        }
                    }
                }
            }else{
                val toastCenter =
                    Toast.makeText(washerActivity, "정보를 모두 입력하세요", Toast.LENGTH_SHORT)
                toastCenter.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, 0)
                toastCenter.show()

            }
        }
    }


    private fun enableSetting(isEnable: Boolean) {
        with(binding) {
            accountName.isEnabled = isEnable
            bankName.isEnabled = isEnable
            accountNumber.isEnabled = isEnable
        }
    }

    //헤드워셔가 픽업인원 설정을 구현했으나 구지 필요없을듯함
//    private fun pickupManagerCount() {
//        with(binding) {
//            var num = 1
//            tvPickupManagerNum.text = num.toString()
//
//            btnPickupManagerPlus.setOnClickListener {
//                num ++
//                tvPickupManagerNum.text = num.toString()
//            }
//
//            btnPickupManagerMinus.setOnClickListener {
//                if(num > 1) {
//                    num --
//                    tvPickupManagerNum.text = num.toString()
//                }else{
//                    num = 1
//                    tvPickupManagerNum.text = num.toString()
//                    val toastCenter = Toast.makeText(washerActivity,"픽업워셔는 1명 이상 등록하셔야 합니다", Toast.LENGTH_SHORT)
//                    toastCenter.setGravity(Gravity.CENTER,0,0)
//                    toastCenter.show()
//                }
//            }
//        }
//    }

    private fun accountNameInputCheck() : Boolean {
        val accountName = binding.accountName.text.toString()
        return when{
            accountName.isEmpty() -> {
                false
            }
            else -> true
        }
    }

    private fun bankNameInputCheck() : Boolean {
        val bankName = binding.bankName.toString()
        return when{
            bankName.isEmpty() -> {
                false
            }
            else -> true
        }
    }

    private fun accountNumberInputCheck() : Boolean {
        val accountNumber = binding.accountNumber.text.toString()
        return when{
            accountNumber.isEmpty() -> {
                false
            }
            else -> true
        }
    }

    private fun washingLocationInputCheck() : Boolean {
        val washingLocation = binding.accountName.text.toString()
        return when{
            washingLocation.isEmpty() -> {
                false
            }
            else -> true
        }
    }


    data class WasherInfo (
        var accountName : String = "",
        var bankName : String = "",
        var accountNumber : String = "",
        var tvPhoneNoWasher : String = "",
        var tvPickupManagerNum : String = "",
        var washingLocation : String = ""
    )

    private fun bankSelect() {
        val brand = resources.getStringArray(R.array.bankSelect)
        val brandAdapter = ArrayAdapter (requireContext(),
            R.layout.custom_owner_spinner, brand)

        with(binding){
            bankName.adapter = brandAdapter
            bankName.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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