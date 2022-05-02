package com.mod_int.carwash.ui.owner_member.om_join

import android.app.Application
import androidx.databinding.ObservableField
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
    val inputCarBrandObservableField = ObservableField("")
    val inputCarModelObservableField = ObservableField("")
    val inputCarColor = MutableLiveData("")
    val inputDetailLocation = MutableLiveData("")

    fun omSaveInfo() {
        ioScope {
            val carNumInputCheck = async { carNumInputCheck() }
            val carBrandInputCheck = async { carBrandInputCheck() }
            val carModelInputCheck = async { carModelInputCheck() }
            val carColInputCheck = async { carColInputCheck() }
            val carDetailLocation = async { carDetailLocationCheck() }
            checkInfo(
                carNumInputCheck.await(),
                carBrandInputCheck.await(),
                carModelInputCheck.await(),
                carColInputCheck.await(),
                carDetailLocation.await(),
            )?.let { ownerInfo ->
                val email = firebaseRepository.getFirebaseAuth().currentUser!!.email
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

    fun routeBackStep() {
        viewStateChanged(OmJoinViewState.BackStep)
    }

    private fun checkInfo(
        carNumInputCheck: Boolean,
        carBrandInputCheck: Boolean,
        carModelInputCheck: Boolean,
        carColInputCheck: Boolean,
        companyNameCheck: Boolean,
    ): OwnerInfo? {
        return if (carNumInputCheck
            && carBrandInputCheck
            && carModelInputCheck
            && carColInputCheck
            && companyNameCheck
        ) {
            OwnerInfo(
                inputCarNumber.value!!,
                inputCarBrandObservableField.get()!!,
                inputCarModelObservableField.get()!!,
                inputCarColor.value!!,
                inputDetailLocation.value!!,
            )
        } else {
            null
        }
    }

    private fun carDetailLocationCheck(): Boolean {
        return when {
            inputDetailLocation.value?.isEmpty() == true -> {
                viewStateChanged(OmJoinViewState.ErrorMsg(message = "상호명을 입력하세요."))
                false
            }
            else -> true
        }
    }

    private fun carNumInputCheck(): Boolean {
        return when {
            inputCarNumber.value?.isEmpty() == true -> {
                viewStateChanged(OmJoinViewState.ErrorMsg(message = "차량번호를 입력하세요."))
                false
            }
            else -> true
        }
    }

    private fun carBrandInputCheck(): Boolean {
        return when {
            inputCarBrandObservableField.get()?.isEmpty() == true -> {
                viewStateChanged(OmJoinViewState.ErrorMsg(message = "브랜드를 선택하세요."))
                false
            }
            else -> true
        }
    }

    private fun carModelInputCheck(): Boolean {
        return when {
            inputCarModelObservableField.get()?.isEmpty() == true -> {
                viewStateChanged(OmJoinViewState.ErrorMsg(message = "모델명을 선택하세요."))
                false
            }
            else -> true
        }
    }

    private fun carColInputCheck(): Boolean {
        return when {
            inputCarColor.value?.isEmpty() == true -> {
                viewStateChanged(OmJoinViewState.ErrorMsg(message = "차량색상을 입력하세요"))
                false
            }
            else -> true
        }
    }

    data class OwnerInfo(
        var carNumber: String = "",
        var carBrand: String = "",
        var carModel: String = "",
        var carColor: String = "",
        var carDetailLocation: String = "",
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

//해쉬맵 형태로 가지고 올때 필요함
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