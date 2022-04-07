package com.mod_int.carwash.ui.washer_member.wm_price

import android.app.Application
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.databinding.ObservableField
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ext.ioScope
import com.mod_int.carwash.ext.showToast
import com.mod_int.carwash.ui.owner_member.om_join.OmJoinViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class WmRegistrationPriceViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {
    val insideWashingKoreaCarXS = ObservableField("")
    val insideWashingKoreaCarS = ObservableField("")
    val insideWashingKoreaCarM = ObservableField("")
    val insideWashingKoreaCarL = ObservableField("")
    val outsideWashingKoreaCarXS = ObservableField("")
    val outsideWashingKoreaCarS = ObservableField("")
    val outsideWashingKoreaCarM = ObservableField("")
    val outsideWashingKoreaCarL = ObservableField("")
    val inOutsideWashingKoreaCarXS = ObservableField("")
    val inOutsideWashingKoreaCarS = ObservableField("")
    val inOutsideWashingKoreaCarM = ObservableField("")
    val inOutsideWashingKoreaCarL = ObservableField("")
    val insideWashingForeignCarXS = ObservableField("")
    val insideWashingForeignCarS = ObservableField("")
    val insideWashingForeignCarM = ObservableField("")
    val insideWashingForeignCarL = ObservableField("")
    val outsideWashingForeignCarXS = ObservableField("")
    val outsideWashingForeignCarS = ObservableField("")
    val outsideWashingForeignCarM = ObservableField("")
    val outsideWashingForeignCarL = ObservableField("")
    val inOutsideWashingForeignCarXS = ObservableField("")
    val inOutsideWashingForeignCarS = ObservableField("")
    val inOutsideWashingForeignCarM = ObservableField("")
    val inOutsideWashingForeignCarL = ObservableField("")
    val insideWashingTime = ObservableField("")
    val outsideWashingTime = ObservableField("")
    val inOutsideWashingTime = ObservableField("")
    val deliveryCost = ObservableField("")
    val polishCost = ObservableField("")
    val washerIntroduce = ObservableField("")

    fun washerPriceInfo() {
        ioScope {
            val insideWashingKoreaCarXSCheck = async { insideWashingKoreaCarXSCheck() }
            val insideWashingKoreaCarSCheck = async { insideWashingKoreaCarSCheck() }
            val insideWashingKoreaCarMCheck = async { insideWashingKoreaCarMCheck() }
            val insideWashingKoreaCarLCheck = async { insideWashingKoreaCarLCheck() }
            val outsideWashingKoreaCarXSCheck = async { outsideWashingKoreaCarXSCheck() }
            val outsideWashingKoreaCarSCheck = async { outsideWashingKoreaCarSCheck() }
            val outsideWashingKoreaCarMCheck = async { outsideWashingKoreaCarMCheck() }
            val outsideWashingKoreaCarLCheck = async { outsideWashingKoreaCarLCheck() }
            val inOutsideWashingKoreaCarXSCheck = async { inOutsideWashingKoreaCarXSCheck() }
            val inOutsideWashingKoreaCarSCheck = async { inOutsideWashingKoreaCarSCheck() }
            val inOutsideWashingKoreaCarMCheck = async { inOutsideWashingKoreaCarMCheck() }
            val inOutsideWashingKoreaCarLCheck = async { inOutsideWashingKoreaCarLCheck() }
            val insideWashingForeignCarXSCheck = async { insideWashingForeignCarXSCheck() }
            val insideWashingForeignCarSCheck = async { insideWashingForeignCarSCheck() }
            val insideWashingForeignCarMCheck = async { insideWashingForeignCarMCheck() }
            val insideWashingForeignCarLCheck = async { insideWashingForeignCarLCheck() }
            val outsideWashingForeignCarXSCheck = async { outsideWashingForeignCarXSCheck() }
            val outsideWashingForeignCarSCheck = async { outsideWashingForeignCarSCheck() }
            val outsideWashingForeignCarMCheck = async { outsideWashingForeignCarMCheck() }
            val outsideWashingForeignCarLCheck = async { outsideWashingForeignCarLCheck() }
            val inOutsideWashingForeignCarXSCheck = async { inOutsideWashingForeignCarXSCheck() }
            val inOutsideWashingForeignCarSCheck = async { inOutsideWashingForeignCarSCheck() }
            val inOutsideWashingForeignCarMCheck = async { inOutsideWashingForeignCarMCheck() }
            val inOutsideWashingForeignCarLCheck = async { inOutsideWashingForeignCarLCheck() }
            val insideWashingTimeCheck = async { insideWashingTimeCheck() }
            val outsideWashingTimeCheck = async { outsideWashingTimeCheck() }
            val inOutsideWashingTimeCheck = async { inOutsideWashingTimeCheck() }
            val deliveryCostCheck = async { deliveryCostCheck() }
            val polishCostCheck = async { polishCostCheck() }
            val washerIntroduceCheck = async { washerIntroduceCheck() }
            checkPrice(
                insideWashingKoreaCarXSCheck.await(),
                insideWashingKoreaCarSCheck.await(),
                insideWashingKoreaCarMCheck.await(),
                insideWashingKoreaCarLCheck.await(),
                outsideWashingKoreaCarXSCheck.await(),
                outsideWashingKoreaCarSCheck.await(),
                outsideWashingKoreaCarMCheck.await(),
                outsideWashingKoreaCarLCheck.await(),
                inOutsideWashingKoreaCarXSCheck.await(),
                inOutsideWashingKoreaCarSCheck.await(),
                inOutsideWashingKoreaCarMCheck.await(),
                inOutsideWashingKoreaCarLCheck.await(),
                insideWashingForeignCarXSCheck.await(),
                insideWashingForeignCarSCheck.await(),
                insideWashingForeignCarMCheck.await(),
                insideWashingForeignCarLCheck.await(),
                outsideWashingForeignCarXSCheck.await(),
                outsideWashingForeignCarSCheck.await(),
                outsideWashingForeignCarMCheck.await(),
                outsideWashingForeignCarLCheck.await(),
                inOutsideWashingForeignCarXSCheck.await(),
                inOutsideWashingForeignCarSCheck.await(),
                inOutsideWashingForeignCarMCheck.await(),
                inOutsideWashingForeignCarLCheck.await(),
                insideWashingTimeCheck.await(),
                outsideWashingTimeCheck.await(),
                inOutsideWashingTimeCheck.await(),
                deliveryCostCheck.await(),
                polishCostCheck.await(),
                washerIntroduceCheck.await(),
                )?.let { priceInfo ->
                val email = firebaseRepository.getFirebaseAuth().currentUser!!.email
                firebaseRepository.getFirebaseFireStore().collection("WasherMember")
                    .document("$email")
                    .set(priceInfo, SetOptions.merge())
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            viewStateChanged(WmRegistrationPriceViewState.EnableInput(false))
                            viewStateChanged(WmRegistrationPriceViewState.PriceInfoSave)

                        }else{


                        }
                    }

            }
        }
    }

    private fun checkPrice(
        insideWashingKoreaCarXSCheck: Boolean,
        insideWashingKoreaCarSCheck: Boolean,
        insideWashingKoreaCarMCheck: Boolean,
        insideWashingKoreaCarLCheck: Boolean,
        outsideWashingKoreaCarXSCheck: Boolean,
        outsideWashingKoreaCarSCheck: Boolean,
        outsideWashingKoreaCarMCheck: Boolean,
        outsideWashingKoreaCarLCheck: Boolean,
        inOutsideWashingKoreaCarXSCheck: Boolean,
        inOutsideWashingKoreaCarSCheck: Boolean,
        inOutsideWashingKoreaCarMCheck: Boolean,
        inOutsideWashingKoreaCarLCheck: Boolean,
        insideWashingForeignCarXSCheck: Boolean,
        insideWashingForeignCarSCheck: Boolean,
        insideWashingForeignCarMCheck: Boolean,
        insideWashingForeignCarLCheck: Boolean,
        outsideWashingForeignCarXSCheck: Boolean,
        outsideWashingForeignCarSCheck: Boolean,
        outsideWashingForeignCarMCheck: Boolean,
        outsideWashingForeignCarLCheck: Boolean,
        inOutsideWashingForeignCarXSCheck: Boolean,
        inOutsideWashingForeignCarSCheck: Boolean,
        inOutsideWashingForeignCarMCheck: Boolean,
        inOutsideWashingForeignCarLCheck: Boolean,
        insideWashingTimeCheck: Boolean,
        outsideWashingTimeCheck: Boolean,
        inOutsideWashingTimeCheck: Boolean,
        deliveryCostCheck: Boolean,
        polishCostCheck: Boolean,
        washerIntroduceCheck: Boolean

    ): PriceInfo? {
        return if (
            insideWashingKoreaCarXSCheck
            && insideWashingKoreaCarSCheck
            && insideWashingKoreaCarMCheck
            && insideWashingKoreaCarLCheck
            && outsideWashingKoreaCarXSCheck
            && outsideWashingKoreaCarSCheck
            && outsideWashingKoreaCarMCheck
            && outsideWashingKoreaCarLCheck
            && inOutsideWashingKoreaCarXSCheck
            && inOutsideWashingKoreaCarSCheck
            && inOutsideWashingKoreaCarMCheck
            && inOutsideWashingKoreaCarLCheck
            && insideWashingForeignCarXSCheck
            && insideWashingForeignCarSCheck
            && insideWashingForeignCarMCheck
            && insideWashingForeignCarLCheck
            && outsideWashingForeignCarXSCheck
            && outsideWashingForeignCarSCheck
            && outsideWashingForeignCarMCheck
            && outsideWashingForeignCarLCheck
            && inOutsideWashingForeignCarXSCheck
            && inOutsideWashingForeignCarSCheck
            && inOutsideWashingForeignCarMCheck
            && inOutsideWashingForeignCarLCheck
            && insideWashingTimeCheck
            && outsideWashingTimeCheck
            && inOutsideWashingTimeCheck
            && deliveryCostCheck
            && polishCostCheck
            && washerIntroduceCheck
        ) {
            PriceInfo(
                insideWashingKoreaCarXS.get()!!,
                insideWashingKoreaCarS.get()!!,
                insideWashingKoreaCarM.get()!!,
                insideWashingKoreaCarL.get()!!,
                outsideWashingKoreaCarXS.get()!!,
                outsideWashingKoreaCarS.get()!!,
                outsideWashingKoreaCarM.get()!!,
                outsideWashingKoreaCarL.get()!!,
                inOutsideWashingKoreaCarXS.get()!!,
                inOutsideWashingKoreaCarS.get()!!,
                inOutsideWashingKoreaCarM.get()!!,
                inOutsideWashingKoreaCarL.get()!!,
                insideWashingForeignCarXS.get()!!,
                insideWashingForeignCarS.get()!!,
                insideWashingForeignCarM.get()!!,
                insideWashingForeignCarL.get()!!,
                outsideWashingForeignCarXS.get()!!,
                outsideWashingForeignCarS.get()!!,
                outsideWashingForeignCarM.get()!!,
                outsideWashingForeignCarL.get()!!,
                inOutsideWashingForeignCarXS.get()!!,
                inOutsideWashingForeignCarS.get()!!,
                inOutsideWashingForeignCarM.get()!!,
                inOutsideWashingForeignCarL.get()!!,
                insideWashingTime.get()!!,
                outsideWashingTime.get()!!,
                inOutsideWashingTime.get()!!,
                deliveryCost.get()!!,
                polishCost.get()!!,
                washerIntroduce.get()!!,
            )
        } else {
            null
        }
    }

    private fun insideWashingKoreaCarXSCheck(): Boolean {
        return when {
            insideWashingKoreaCarXS.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun insideWashingKoreaCarSCheck(): Boolean {
        return when {
            insideWashingKoreaCarS.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun insideWashingKoreaCarMCheck(): Boolean {
        return when {
            insideWashingKoreaCarM.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun insideWashingKoreaCarLCheck(): Boolean {
        return when {
            insideWashingKoreaCarL.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun outsideWashingKoreaCarXSCheck(): Boolean {
        return when {
            outsideWashingKoreaCarXS.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun outsideWashingKoreaCarSCheck(): Boolean {
        return when {
            outsideWashingKoreaCarS.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun outsideWashingKoreaCarMCheck(): Boolean {
        return when {
            outsideWashingKoreaCarM.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun outsideWashingKoreaCarLCheck(): Boolean {
        return when {
            outsideWashingKoreaCarL.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun inOutsideWashingKoreaCarXSCheck(): Boolean {
        return when {
            inOutsideWashingKoreaCarXS.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun inOutsideWashingKoreaCarSCheck(): Boolean {
        return when {
            inOutsideWashingKoreaCarS.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun inOutsideWashingKoreaCarMCheck(): Boolean {
        return when {
            inOutsideWashingKoreaCarM.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun inOutsideWashingKoreaCarLCheck(): Boolean {
        return when {
            inOutsideWashingKoreaCarL.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun insideWashingForeignCarXSCheck(): Boolean {
        return when {
            insideWashingForeignCarXS.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun insideWashingForeignCarSCheck(): Boolean {
        return when {
            insideWashingForeignCarS.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun insideWashingForeignCarMCheck(): Boolean {
        return when {
            insideWashingForeignCarM.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun insideWashingForeignCarLCheck(): Boolean {
        return when {
            insideWashingForeignCarL.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun outsideWashingForeignCarXSCheck(): Boolean {
        return when {
            outsideWashingForeignCarXS.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun outsideWashingForeignCarSCheck(): Boolean {
        return when {
            outsideWashingForeignCarS.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun outsideWashingForeignCarMCheck(): Boolean {
        return when {
            outsideWashingForeignCarM.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun outsideWashingForeignCarLCheck(): Boolean {
        return when {
            outsideWashingForeignCarL.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun inOutsideWashingForeignCarXSCheck(): Boolean {
        return when {
            inOutsideWashingForeignCarXS.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun inOutsideWashingForeignCarSCheck(): Boolean {
        return when {
            inOutsideWashingForeignCarS.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun inOutsideWashingForeignCarMCheck(): Boolean {
        return when {
            inOutsideWashingForeignCarM.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun inOutsideWashingForeignCarLCheck(): Boolean {
        return when {
            inOutsideWashingForeignCarL.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun insideWashingTimeCheck(): Boolean {
        return when {
            insideWashingTime.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun outsideWashingTimeCheck(): Boolean {
        return when {
            outsideWashingTime.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun inOutsideWashingTimeCheck(): Boolean {
        return when {
            inOutsideWashingTime.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun deliveryCostCheck(): Boolean {
        return when {
            deliveryCost.get()?.isEmpty() == true -> {
                false
            }
            (8000 > deliveryCost.get()!!.toInt()) -> {
                viewStateChanged(WmRegistrationPriceViewState.ErrorMsg(message = "픽업/탁송 비용은 8000원 이상 등록하세요"))
                false
            }
            else -> true
        }
    }

    private fun polishCostCheck(): Boolean {
        return when {
            polishCost.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun washerIntroduceCheck(): Boolean {
        return when {
            washerIntroduce.get()?.isEmpty() == true -> {
                false
            }
            else -> true
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