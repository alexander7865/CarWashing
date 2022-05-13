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
    val insideWashingCarXS1 = ObservableField("")
    val insideWashingCarS2 = ObservableField("")
    val insideWashingCarM3 = ObservableField("")
    val insideWashingCarL4 = ObservableField("")
    val outsideWashingCarXS1 = ObservableField("")
    val outsideWashingCarS2 = ObservableField("")
    val outsideWashingCarM3 = ObservableField("")
    val outsideWashingCarL4 = ObservableField("")
    val inOutsideWashingCarXS1 = ObservableField("")
    val inOutsideWashingCarS2 = ObservableField("")
    val inOutsideWashingCarM3 = ObservableField("")
    val inOutsideWashingCarL4 = ObservableField("")
    val insideWashingTime1 = ObservableField("")
    val outsideWashingTime2 = ObservableField("")
    val inOutsideWashingTime3 = ObservableField("")
    val addCost = ObservableField("")
    val washerIntroduce1 = ObservableField("")

    fun washerPriceInfo() {
        ioScope {
            val insideWashingCarXSCheck = async { insideWashingCarXSCheck() }
            val insideWashingCarSCheck = async { insideWashingCarSCheck() }
            val insideWashingCarMCheck = async { insideWashingCarMCheck() }
            val insideWashingCarLCheck = async { insideWashingCarLCheck() }
            val outsideWashingCarXSCheck = async { outsideWashingCarXSCheck() }
            val outsideWashingCarSCheck = async { outsideWashingCarSCheck() }
            val outsideWashingCarMCheck = async { outsideWashingCarMCheck() }
            val outsideWashingCarLCheck = async { outsideWashingCarLCheck() }
            val inOutsideWashingCarXSCheck = async { inOutsideWashingCarXSCheck() }
            val inOutsideWashingCarSCheck = async { inOutsideWashingCarSCheck() }
            val inOutsideWashingCarMCheck = async { inOutsideWashingCarMCheck() }
            val inOutsideWashingCarLCheck = async { inOutsideWashingCarLCheck() }
            val insideWashingTimeCheck = async { insideWashingTimeCheck() }
            val outsideWashingTimeCheck = async { outsideWashingTimeCheck() }
            val inOutsideWashingTimeCheck = async { inOutsideWashingTimeCheck() }
            val pickupDeliveryCostCheck = async { pickupDeliveryCostCheck() }
            val washerIntroduceCheck = async { washerIntroduceCheck() }
            checkPrice(
                insideWashingCarXSCheck.await(),
                insideWashingCarSCheck.await(),
                insideWashingCarMCheck.await(),
                insideWashingCarLCheck.await(),
                outsideWashingCarXSCheck.await(),
                outsideWashingCarSCheck.await(),
                outsideWashingCarMCheck.await(),
                outsideWashingCarLCheck.await(),
                inOutsideWashingCarXSCheck.await(),
                inOutsideWashingCarSCheck.await(),
                inOutsideWashingCarMCheck.await(),
                inOutsideWashingCarLCheck.await(),
                insideWashingTimeCheck.await(),
                outsideWashingTimeCheck.await(),
                inOutsideWashingTimeCheck.await(),
                pickupDeliveryCostCheck.await(),
                washerIntroduceCheck.await()
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
        insideWashingCarXSCheck: Boolean,
        insideWashingCarSCheck: Boolean,
        insideWashingCarMCheck: Boolean,
        insideWashingCarLCheck: Boolean,
        outsideWashingCarXSCheck: Boolean,
        outsideWashingCarSCheck: Boolean,
        outsideWashingCarMCheck: Boolean,
        outsideWashingCarLCheck: Boolean,
        inOutsideWashingCarXSCheck: Boolean,
        inOutsideWashingCarSCheck: Boolean,
        inOutsideWashingCarMCheck: Boolean,
        inOutsideWashingCarLCheck: Boolean,
        insideWashingTimeCheck: Boolean,
        outsideWashingTimeCheck: Boolean,
        inOutsideWashingTimeCheck: Boolean,
        addCostCheck: Boolean,
        washerIntroduceCheck: Boolean

    ): PriceInfo? {
        return if (
            insideWashingCarXSCheck
            && insideWashingCarSCheck
            && insideWashingCarMCheck
            && insideWashingCarLCheck
            && outsideWashingCarXSCheck
            && outsideWashingCarSCheck
            && outsideWashingCarMCheck
            && outsideWashingCarLCheck
            && inOutsideWashingCarXSCheck
            && inOutsideWashingCarSCheck
            && inOutsideWashingCarMCheck
            && inOutsideWashingCarLCheck
            && insideWashingTimeCheck
            && outsideWashingTimeCheck
            && inOutsideWashingTimeCheck
            && addCostCheck
            && washerIntroduceCheck
        ) {
            PriceInfo(
                insideWashingCarXS1.get()!!,
                insideWashingCarS2.get()!!,
                insideWashingCarM3.get()!!,
                insideWashingCarL4.get()!!,
                outsideWashingCarXS1.get()!!,
                outsideWashingCarS2.get()!!,
                outsideWashingCarM3.get()!!,
                outsideWashingCarL4.get()!!,
                inOutsideWashingCarXS1.get()!!,
                inOutsideWashingCarS2.get()!!,
                inOutsideWashingCarM3.get()!!,
                inOutsideWashingCarL4.get()!!,
                insideWashingTime1.get()!!,
                outsideWashingTime2.get()!!,
                inOutsideWashingTime3.get()!!,
                addCost.get()!!,
                washerIntroduce1.get()!!,
            )
        } else {
            null
        }
    }

    private fun insideWashingCarXSCheck(): Boolean {
        return when {
            insideWashingCarXS1.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun insideWashingCarSCheck(): Boolean {
        return when {
            insideWashingCarS2.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun insideWashingCarMCheck(): Boolean {
        return when {
            insideWashingCarM3.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun insideWashingCarLCheck(): Boolean {
        return when {
            insideWashingCarL4.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun outsideWashingCarXSCheck(): Boolean {
        return when {
            outsideWashingCarXS1.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun outsideWashingCarSCheck(): Boolean {
        return when {
            outsideWashingCarS2.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun outsideWashingCarMCheck(): Boolean {
        return when {
            outsideWashingCarM3.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun outsideWashingCarLCheck(): Boolean {
        return when {
            outsideWashingCarL4.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun inOutsideWashingCarXSCheck(): Boolean {
        return when {
            inOutsideWashingCarXS1.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun inOutsideWashingCarSCheck(): Boolean {
        return when {
            inOutsideWashingCarS2.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun inOutsideWashingCarMCheck(): Boolean {
        return when {
            inOutsideWashingCarM3.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun inOutsideWashingCarLCheck(): Boolean {
        return when {
            inOutsideWashingCarL4.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun insideWashingTimeCheck(): Boolean {
        return when {
            insideWashingTime1.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun outsideWashingTimeCheck(): Boolean {
        return when {
            outsideWashingTime2.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun inOutsideWashingTimeCheck(): Boolean {
        return when {
            inOutsideWashingTime3.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun pickupDeliveryCostCheck(): Boolean {
        return when {
            addCost.get()?.isEmpty() == true -> {
                false
            }
            (8000 > addCost.get()!!.toInt()) -> {
                viewStateChanged(WmRegistrationPriceViewState.ErrorMsg(message = "픽업/탁송 비용은 8000원 이상 등록하세요"))
                false
            }
            else -> true
        }
    }

    private fun washerIntroduceCheck(): Boolean {
        return when {
            washerIntroduce1.get()?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    fun routeBackStep(){
        viewStateChanged(WmRegistrationPriceViewState.RouteBackStep)
    }




    data class PriceInfo(
        var insideWashingCarXS: String = "",
        var insideWashingCarS: String = "",
        var insideWashingCarM: String = "",
        var insideWashingCarL: String = "",
        var outsideWashingCarXS: String = "",
        var outsideWashingCarS: String = "",
        var outsideWashingCarM: String = "",
        var outsideWashingCarL: String = "",
        var inOutsideWashingCarXS: String = "",
        var inOutsideWashingCarS: String = "",
        var inOutsideWashingCarM: String = "",
        var inOutsideWashingCarL: String = "",
        var insideWashingTime: String = "",
        var outsideWashingTime: String = "",
        var inOutsideWashingTime: String = "",
        var addCost: String = "",
        var washerIntroduce: String = ""
    )
}