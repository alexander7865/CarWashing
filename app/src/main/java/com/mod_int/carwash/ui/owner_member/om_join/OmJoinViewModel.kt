package com.mod_int.carwash.ui.owner_member.om_join

import android.app.Application
import android.view.Gravity
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.SetOptions
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ext.ioScope
import com.mod_int.carwash.ui.register.RegisterViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class OmJoinViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {
    val inputCarNumber = MutableLiveData<String>()
    val inputCarBrand = MutableLiveData<String>()
    val inputCarModel = MutableLiveData<String>()
    val inputCarKinds = MutableLiveData<String>()
    val inputCarSize = MutableLiveData<String>()
    val inputCarColor = MutableLiveData<String>()
    val inputCarLocation = MutableLiveData<String>()

    fun omSaveInfo(){
        ioScope {
            val carNumInputCheck = async { carNumInputCheck() }
            val carBrandInputCheck = async { carBrandInputCheck() }
            val carModelInputCheck = async { carModelInputCheck() }
            val carKindsInputCheck = async { carKindsInputCheck() }
            val carSizeInputCheck = async { carSizeInputCheck() }
            val carLocationCheck = async { carLocationCheck() }
            val carColInputCheck = async { etCarColInputCheck() }
            checkInfo(
                carNumInputCheck.await(),
                carBrandInputCheck.await(),
                carModelInputCheck.await(),
                carKindsInputCheck.await(),
                carSizeInputCheck.await(),
                carLocationCheck.await(),
                carColInputCheck.await(),
                )?.let{
                firebaseRepository.getFirebaseAuth().currentUser!!.email.let { email ->
                    firebaseRepository.getFirebaseFireStore().collection("OwnerMember")
                        .document("$email")
                        .set("ownerInfo", SetOptions.merge()).addOnCompleteListener {
                            if (it.isSuccessful) {
                                viewStateChanged(OmJoinViewState.OmInfoSave)
                //                            val toastCenter =
                //                                Toast.makeText(ownerActivity, "정보가 저장되었습니다", Toast.LENGTH_SHORT)
                //                            toastCenter.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, 0)
                //                            toastCenter.show()
                            } else {
                //                            val toastCenter =
                //                                Toast.makeText(ownerActivity, "정보가 저장되지 않았습니다", Toast.LENGTH_SHORT)
                //                            toastCenter.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, 0)
                //                            toastCenter.show()
                            }
                        }
                }
            }
        }
    }

    private fun checkInfo(
        carNumInputCheck: Boolean,
        carBrandInputCheck: Boolean,
        carModelInputCheck: Boolean,
        carKindsInputCheck: Boolean,
        carSizeInputCheck: Boolean,
        carLocationCheck: Boolean,
        carColInputCheck: Boolean
    ): OwnerInfo? {
        return if (carNumInputCheck && carBrandInputCheck && carModelInputCheck && carKindsInputCheck
            && carSizeInputCheck && carLocationCheck && carColInputCheck) {
            OwnerInfo(inputCarNumber.value!!, inputCarBrand.value!!,
                inputCarModel.value!!, inputCarKinds.value!!, inputCarSize.value!!,
//                inputCarColor.value!!, inputCarLocation.value!!
            )
        } else {
            null
        }
    }


    private fun carNumInputCheck() : Boolean {
        return when{
            inputCarNumber.value?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun carBrandInputCheck() : Boolean {
        return when{
            inputCarBrand.value?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }
    private fun carModelInputCheck() : Boolean {
        return when{
            inputCarModel.value?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun carKindsInputCheck() : Boolean {
        return when{
            inputCarKinds.value?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun carSizeInputCheck() : Boolean {
        return when{
            inputCarSize.value?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun etCarColInputCheck() : Boolean {
        return when{
            inputCarColor.value?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    private fun carLocationCheck() : Boolean {
        return when{
            inputCarLocation.value?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

//    private fun enableSetting(isEnable: Boolean) {
//        inputCarNumber.value = isEnable.toString()
//        inputCarBrand.value = isEnable.toString()
//        inputCarModel.value = isEnable.toString()
//        inputCarKinds.value = isEnable.toString()
//        inputCarSize.value = isEnable.toString()
//        inputCarColor.value = isEnable.toString()
//        inputCarLocation.value = isEnable.toString()
//    }





    data class OwnerInfo (
        var carNumber : String = "",
        var carBrand : String = "",
        var carModel : String = "",
//        var carKinds : String = "",
//        var carSize : String = "",
        var carColor : String = "",
        var carLocation : String = ""

    )
}

