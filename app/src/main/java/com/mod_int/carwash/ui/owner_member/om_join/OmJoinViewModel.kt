package com.mod_int.carwash.ui.owner_member.om_join

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.SetOptions
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ext.ioScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class OmJoinViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {
    val inputCarNumber = MutableLiveData("")
    val inputCarBrand = MutableLiveData("")
    val inputCarModel = MutableLiveData("")
    val inputCarColor = MutableLiveData("")

    fun omSaveInfo(){
        ioScope {
            val carNumInputCheck = async { carNumInputCheck() }
            val carBrandInputCheck = async { carBrandInputCheck() }
            val carModelInputCheck = async { carModelInputCheck() }
            val carColInputCheck = async { etCarColInputCheck() }
            checkInfo(
                carNumInputCheck.await(),
                carBrandInputCheck.await(),
                carModelInputCheck.await(),
                carColInputCheck.await(),
                )?.let{
                val email = firebaseRepository.getFirebaseAuth().currentUser!!.email
                val ownerInfo = OwnerInfo(
                    "${inputCarNumber.value}",
                    "${inputCarBrand.value}",
                    "${inputCarModel.value}",
                    "${inputCarColor.value}",
                )
                firebaseRepository.getFirebaseFireStore().collection("OwnerMember")
                    .document("$email")
                    .set(ownerInfo, SetOptions.merge()).addOnCompleteListener {
                        if (it.isSuccessful) {
                            viewStateChanged(OmJoinViewState.EnableInput(false))
                            viewStateChanged(OmJoinViewState.OmInfoSave)

                        } else {
                            viewStateChanged(OmJoinViewState.ErrorMsg(message = "정보를 모두 입력하세요"))

                        }
                    }

            }
        }
    }

    private fun checkInfo(
        carNumInputCheck: Boolean,
        carBrandInputCheck: Boolean,
        carModelInputCheck: Boolean,
        carColInputCheck: Boolean
    ): OwnerInfo? {
        return if (carNumInputCheck && carBrandInputCheck && carModelInputCheck &&
            carColInputCheck) {
            OwnerInfo(inputCarNumber.value!!, inputCarBrand.value!!, inputCarModel.value!!,
                inputCarColor.value!!
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

    private fun etCarColInputCheck() : Boolean {
        return when{
            inputCarColor.value?.isEmpty() == true -> {
                false
            }
            else -> true
        }
    }

    data class OwnerInfo (
        var carNumber : String = "",
        var carBrand : String = "",
        var carModel : String = "",
        var carColor : String = "",
    )
}



//                    //HashMap 형태 아닌 다른 방법 저장
//                    fireStore?.collection("OwnerMember")?.document("$email"
//                    )?.set(ownerInfo, SetOptions.merge())?.addOnCompleteListener {
//                        if (it.isSuccessful) {
//                            enableSetting(false)
//                            val toastCenter =
//                                Toast.makeText(ownerActivity, "정보가 저장되었습니다", Toast.LENGTH_SHORT)
//                            toastCenter.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, 0)
//                            toastCenter.show()
//                        } else {
//                            val toastCenter =
//                                Toast.makeText(ownerActivity, "정보가 저장되지 않았습니다", Toast.LENGTH_SHORT)
//                            toastCenter.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, 0)
//                            toastCenter.show()
//                        }
//                    }

//해쉬맵 형태로 가지고 올때 피요함
//fun HashMap<String, String>.toOwnerInfo(): OwnerInfo =
//    OwnerInfo(
//        carNumber = getValue("carNumber"),
//        carBrand = getValue("carBrand"),
//        carModel = getValue("carModel"),
//        carKinds = getValue("carKinds"),
//        carSize = getValue("carSize"),
//        carColor = getValue("carColor"),
//        carLocation = getValue("carLocation"),
//    )