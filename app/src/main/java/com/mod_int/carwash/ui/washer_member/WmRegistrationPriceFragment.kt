package com.mod_int.carwash.ui.washer_member

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentWmRegistrationPriceBinding

class WmRegistrationPriceFragment : BaseFragment<FragmentWmRegistrationPriceBinding>(
    R.layout.fragment_wm_registration_price) {

    lateinit var wmActivity: WmActivity
    private var fireStore: FirebaseFirestore? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is WmActivity) wmActivity = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fireStore = FirebaseFirestore.getInstance()

        //가격저장 버튼 구현
        with(binding){
            btnPriceSave.setOnClickListener {
                priceSave()

            }
        }
    }

    private fun priceSave() {
        val priceInfo = PriceInfo(
            insideWashingKoreaCarXS = binding.insideKoreaXS.text.toString(),
            insideWashingKoreaCarS = binding.insideKoreaS.text.toString(),
            insideWashingKoreaCarM = binding.insideKoreaM.text.toString(),
            insideWashingKoreaCarL = binding.insideKoreaL.text.toString(),
            outsideWashingKoreaCarXS = binding.outsideKoreaXS.text.toString(),
            outsideWashingKoreaCarS = binding.outsideKoreaS.text.toString(),
            outsideWashingKoreaCarM = binding.outsideKoreaM.text.toString(),
            outsideWashingKoreaCarL = binding.outsideKoreaL.text.toString(),
            inOutsideWashingKoreaCarXS = binding.inOutsideKoreaXS.text.toString(),
            inOutsideWashingKoreaCarS = binding.inOutsideKoreaS.text.toString(),
            inOutsideWashingKoreaCarM = binding.inOutsideKoreaM.text.toString(),
            inOutsideWashingKoreaCarL = binding.inOutsideKoreaL.text.toString(),
            insideWashingForeignCarXS = binding.insideForeignXS.text.toString(),
            insideWashingForeignCarS = binding.insideForeignS.text.toString(),
            insideWashingForeignCarM = binding.insideForeignM.text.toString(),
            insideWashingForeignCarL = binding.insideForeignL.text.toString(),
            outsideWashingForeignCarXS = binding.outsideForeignXS.text.toString(),
            outsideWashingForeignCarS = binding.outsideForeignS.text.toString(),
            outsideWashingForeignCarM = binding.outsideForeignM.text.toString(),
            outsideWashingForeignCarL = binding.outsideForeignL.text.toString(),
            inOutsideWashingForeignCarXS = binding.inOutsideForeignXS.text.toString(),
            inOutsideWashingForeignCarS = binding.inOutsideForeignS.text.toString(),
            inOutsideWashingForeignCarM = binding.inOutsideForeignM.text.toString(),
            inOutsideWashingForeignCarL = binding.inOutsideForeignL.text.toString(),
            insideWashingTime = binding.insideTime.text.toString(),
            outsideWashingTime = binding.outsideTime.text.toString(),
            inOutsideWashingTime = binding.inOutsideTime.text.toString(),
            deliveryCost = binding.deliveryCost.text.toString(),
            polishCost = binding.polishCost.text.toString(),
            washerIntroduce = binding.washerIntroduce.text.toString()

        )
        val user = Firebase.auth.currentUser
        user?.let {
            val email = user.email
            fireStore?.collection("WasherMember")?.document(
                "$email")?.set(priceInfo)?.addOnCompleteListener{
                if (it.isSuccessful) {
                    enableSetting(false)
                    val toastCenter = Toast.makeText(wmActivity, "정보가 저장되었습니다", Toast.LENGTH_SHORT)
                    toastCenter.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, 0)
                    toastCenter.show()
                }else{
                    val toastCenter = Toast.makeText(wmActivity, "정보가 저장되지 않았습니다", Toast.LENGTH_SHORT)
                    toastCenter.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, 0)
                    toastCenter.show()
                }
            }
            Log.d("사용자", email.toString())
        }
    }

    private fun enableSetting(isEnable: Boolean) {
        with(binding) {
            insideKoreaXS.isEnabled = isEnable
            insideKoreaS.isEnabled = isEnable
            insideKoreaM.isEnabled = isEnable
            insideKoreaL.isEnabled = isEnable
            outsideKoreaXS.isEnabled = isEnable
            outsideKoreaS.isEnabled = isEnable
            outsideKoreaM.isEnabled = isEnable
            outsideKoreaL.isEnabled = isEnable
            inOutsideKoreaXS.isEnabled = isEnable
            inOutsideKoreaS.isEnabled = isEnable
            inOutsideKoreaM.isEnabled = isEnable
            inOutsideKoreaL.isEnabled = isEnable
            insideForeignXS.isEnabled = isEnable
            insideForeignS.isEnabled = isEnable
            insideForeignM.isEnabled = isEnable
            insideForeignL.isEnabled = isEnable
            outsideForeignXS.isEnabled = isEnable
            outsideForeignS.isEnabled = isEnable
            outsideForeignM.isEnabled = isEnable
            outsideForeignL.isEnabled = isEnable
            inOutsideForeignXS.isEnabled = isEnable
            inOutsideForeignS.isEnabled = isEnable
            inOutsideForeignM.isEnabled = isEnable
            inOutsideForeignL.isEnabled = isEnable
            insideTime.isEnabled = isEnable
            outsideTime.isEnabled = isEnable
            inOutsideTime.isEnabled = isEnable
            deliveryCost.isEnabled = isEnable
            polishCost.isEnabled = isEnable
            washerIntroduce.isEnabled = isEnable
        }
    }


    data class PriceInfo(
        var insideWashingKoreaCarXS: String = "",
        var insideWashingKoreaCarS: String = "",
        var insideWashingKoreaCarM: String = "",
        var insideWashingKoreaCarL: String = "",
        var outsideWashingKoreaCarXS: String = "",
        var outsideWashingKoreaCarS: String = "",
        var outsideWashingKoreaCarM: String = "",
        var outsideWashingKoreaCarL: String = "",
        var inOutsideWashingKoreaCarXS: String = "",
        var inOutsideWashingKoreaCarS: String = "",
        var inOutsideWashingKoreaCarM: String = "",
        var inOutsideWashingKoreaCarL: String = "",
        var insideWashingForeignCarXS: String = "",
        var insideWashingForeignCarS: String = "",
        var insideWashingForeignCarM: String = "",
        var insideWashingForeignCarL: String = "",
        var outsideWashingForeignCarXS: String = "",
        var outsideWashingForeignCarS: String = "",
        var outsideWashingForeignCarM: String = "",
        var outsideWashingForeignCarL: String = "",
        var inOutsideWashingForeignCarXS: String = "",
        var inOutsideWashingForeignCarS: String = "",
        var inOutsideWashingForeignCarM: String = "",
        var inOutsideWashingForeignCarL: String = "",
        var insideWashingTime: String = "",
        var outsideWashingTime: String = "",
        var inOutsideWashingTime: String = "",
        var deliveryCost: String = "",
        var polishCost: String = "",
        var washerIntroduce: String = ""
    )
}

