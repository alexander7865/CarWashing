package com.mod_int.carwash.ui.owner_member.om_join

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.SetOptions
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.data.repo.UserRepository
import com.mod_int.carwash.ext.ioScope
import com.mod_int.carwash.room.entity.UserEntity
import com.mod_int.carwash.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class OmJoinViewModel @Inject constructor(
    app: Application,
    private val userRepository: UserRepository,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {
    val inputCarNumber = MutableLiveData("")
    val inputCarBrand = ObservableField("")
    val inputCarModel = ObservableField("")
    val inputCarKinds = ObservableField("SUV")
    val inputCarSize = ObservableField("중형차")
    val inputCarOrigin = ObservableField("외제차")
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
                val email = firebaseRepository.getFirebaseAuth().currentUser?.email!!
                firebaseRepository.getFirebaseFireStore().collection("OwnerMember")
                    .document("$email")
                    .set(ownerInfo, SetOptions.merge()).addOnCompleteListener {
                        ioScope {
                            if (userRepository.registerUser(ownerInfo.toUserEntity(email))) {
                                Log.d("결과", "로컬등록 o")
                            } else {
                                Log.d("결과", "로컬등록 x")
                            }
                        }
                    }.addOnSuccessListener {
                        viewStateChanged(OmJoinViewState.EnableInput(false))
                        viewStateChanged(OmJoinViewState.OmInfoSave)
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
        carDetailLocation: Boolean,
    ): OwnerInfo? {
        return if (carNumInputCheck
            && carBrandInputCheck
            && carModelInputCheck
            && carColInputCheck
            && carDetailLocation
        ) {
            OwnerInfo(
                inputCarNumber.value!!,
                inputCarBrand.get()!!,
                inputCarModel.get()!!,
                inputCarKinds.get()!!,
                inputCarSize.get()!!,
                inputCarOrigin.get()!!,
                inputCarColor.value!!,
                inputDetailLocation.value!!,
            )
        } else {
            null
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
            inputCarBrand.get()?.isEmpty() == true -> {
                viewStateChanged(OmJoinViewState.ErrorMsg(message = "브랜드를 선택하세요."))
                false
            }
            else -> true
        }
    }

    private fun carModelInputCheck(): Boolean {
        return when {
            inputCarModel.get()?.isEmpty() == true -> {
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

    private fun carDetailLocationCheck(): Boolean {
        return when {
            inputDetailLocation.value?.isEmpty() == true -> {
                viewStateChanged(OmJoinViewState.ErrorMsg(message = "상호명을 입력하세요."))
                false
            }
            else -> true
        }
    }

    data class OwnerInfo(
        var carNumber: String = "",
        var carBrand: String = "",
        var carModel: String = "",
        var carKinds: String = "",
        var carSize: String = "",
        var carOrigin: String = "",
        var carColor: String = "",
        var carDetailLocation: String = "",
    ) {
        fun toUserEntity(email: String): UserEntity {
            return UserEntity(
                userEmail = email,
                carNum = carNumber,
                carBrand = carBrand,
                carCategory = carKinds,
                carColor = carColor,
                carLocation = carDetailLocation,
                carModelName = carModel,
                carOrigin = carOrigin,
                carSize = carSize
            )
        }
    }
}


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