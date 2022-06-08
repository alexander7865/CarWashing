package com.mod_int.carwash.ui.dialog.order

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.room.Delete
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.data.repo.UserRepository
import com.mod_int.carwash.ext.ioScope
import com.mod_int.carwash.model.OrderOmInfo
import com.mod_int.carwash.model.PriceItem
import com.mod_int.carwash.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CustomDialogOrderViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository,
    private val userRepository: UserRepository
) : BaseViewModel(app), LifecycleEventObserver {
    private val current: LocalDateTime = LocalDateTime.now()

    var carSize = ObservableField("")
    var orderDate =
        ObservableField("${current.year}년 ${current.monthValue}월 ${current.dayOfMonth}일")
    var ordTime = ObservableField("")
    var ordType = ObservableField("")
    var ordAmount = ObservableField("")
    var orderMsg = ObservableField("")
    var companyName = ObservableField("")
    var pickupDeliCost = ObservableField("미설정")
    var carOrigin = ObservableField("")

    fun getCarInfo() {
        ioScope {
            val email = firebaseRepository.getFirebaseAuth().currentUser!!.email!!
            when (val result = userRepository.getUserInfo(email)) {
                is Result.Success -> {
                    //룸데이터 가지고 오는방법
                    carSize.set(result.data.carSize)
                    carOrigin.set(result.data.carOrigin)
                    Log.d("결과", result.data.carSize)
                    Log.d("결과", result.data.carOrigin)
                    Log.d("결과", result.data.userEmail)
                    getTotalPrice()
                }

                is Result.Error -> {
                    Log.d("결과", "getCarInfo 실패.")
                }
            }
        }
    }

    fun saveOmInfo() {
        ioScope {
            val data = OrderOmInfo(
                orderDate.get()!!,
                ordTime.get()!!,
                ordType.get()!!,
                ordAmount.get()!!,
                pickupDeliCost.get()!!,
                orderMsg.get()!!,
            )
            val email = firebaseRepository.getFirebaseAuth().currentUser!!.email
            firebaseRepository.getFirebaseFireStore().collection("OwnerMember")
                .document("$email")
                .set(data, SetOptions.merge())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        firebaseRepository.getFirebaseFireStore().collection("OwnerMember")
                            .document("$email")
                            .set(emptyMap<String, OrderOmInfo>(), SetOptions.merge())
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    firebaseRepository.getFirebaseFireStore()
                                        .collection("OwnerMember")
                                        .document("$email")
                                        .update("OrderList", FieldValue.arrayUnion(data))
                                        .addOnCompleteListener {
                                            viewStateChanged(CustomDialogOrderViewState.SaveOmInfo)
                                        }
                                }
                            }
                    }
                }
        }
    }


    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
            }
        }
    }


    fun getTotalPrice() {
        val isForeignCar = carOrigin.get() == "외제차"

        ioScope {
            firebaseRepository.getFirebaseFireStore().collection("WasherMember")
                .addSnapshotListener { querySnapshot, _ ->
                    val priceList = mutableListOf<PriceItem>()
                    for (info in querySnapshot!!.documentChanges) {
                        var document = info.document.toObject(PriceItem::class.java)
                        priceList.add(document)
                    }
                    val getPriceItem =
                        priceList.firstOrNull { it.wmCompanyName == companyName.get() }

                    if (pickupDeliCost.get()?.isNotEmpty() == true) {
                        pickupDeliCost.set(getPriceItem?.pickupDeliveryCost)
                        Log.d("픽업비용", "getTotalPrice: ${pickupDeliCost.get()}")
                    }
                    viewStateChanged(CustomDialogOrderViewState.CheckPickupDelivery)

                    when (ordType.get()) {
                        "내부+외부세차" -> {
                            if (carSize.get() == "경차") {
                                if (isForeignCar) {
                                    ordAmount.set(
                                        (getPriceItem?.inOutsideWashingCarXS!!.toInt()
                                                + getPriceItem.addCost.toInt()
                                                + getPriceItem.addCost.toInt()).toString()
                                    )

                                } else {
                                    ordAmount.set(getPriceItem?.inOutsideWashingCarXS)
                                }
                            }
                            if (carSize.get() == "소형차") {
                                if (isForeignCar) {
                                    ordAmount.set(
                                        (getPriceItem?.inOutsideWashingCarS!!.toInt()
                                                + getPriceItem.addCost.toInt()
                                                + getPriceItem.addCost.toInt()).toString()
                                    )
                                } else {
                                    ordAmount.set(getPriceItem?.inOutsideWashingCarS)
                                }
                            }
                            if (carSize.get() == "중형차") {
                                if (isForeignCar) {
                                    ordAmount.set(
                                        (getPriceItem?.inOutsideWashingCarM!!.toInt()
                                                + getPriceItem.addCost.toInt()
                                                + getPriceItem.addCost.toInt()).toString()
                                    )
                                } else {
                                    ordAmount.set(getPriceItem?.inOutsideWashingCarM)
                                }
                            }
                            if (carSize.get() == "대형차") {
                                if (isForeignCar) {
                                    ordAmount.set(
                                        (getPriceItem?.inOutsideWashingCarL!!.toInt()
                                                + getPriceItem.addCost.toInt()
                                                + getPriceItem.addCost.toInt()).toString()
                                    )
                                } else {
                                    ordAmount.set(getPriceItem?.inOutsideWashingCarL)
                                }
                            }
                        }

                        "외부세차" -> {
                            if (carSize.get() == "경차") {
                                if (isForeignCar) {
                                    ordAmount.set(
                                        (getPriceItem?.outsideWashingCarXS!!.toInt()
                                                + getPriceItem.addCost.toInt()).toString()
                                    )
                                } else {
                                    ordAmount.set(getPriceItem?.outsideWashingCarXS)
                                }
                            }
                            if (carSize.get() == "소형차") {
                                if (isForeignCar) {
                                    ordAmount.set(
                                        (getPriceItem?.outsideWashingCarS!!.toInt()
                                                + getPriceItem.addCost.toInt()).toString()
                                    )
                                } else {
                                    ordAmount.set(getPriceItem?.outsideWashingCarS)
                                }
                            }
                            if (carSize.get() == "중형차") {
                                if (isForeignCar) {
                                    ordAmount.set(
                                        (getPriceItem?.outsideWashingCarM!!.toInt()
                                                + getPriceItem.addCost.toInt()).toString()
                                    )
                                } else {
                                    ordAmount.set(getPriceItem?.outsideWashingCarM)
                                }
                            }
                            if (carSize.get() == "대형차") {
                                if (isForeignCar) {
                                    ordAmount.set(
                                        (getPriceItem?.outsideWashingCarL!!.toInt()
                                                + getPriceItem.addCost.toInt()).toString()
                                    )
                                } else {
                                    ordAmount.set(getPriceItem?.outsideWashingCarL)
                                }
                            }
                        }

                        "내부세차" -> {
                            if (carSize.get() == "경차") {
                                if (isForeignCar) {
                                    ordAmount.set(
                                        (getPriceItem?.insideWashingCarXS!!.toInt()
                                                + getPriceItem.addCost.toInt()).toString()
                                    )
                                } else {
                                    ordAmount.set(getPriceItem?.insideWashingCarXS)
                                }
                            }
                            if (carSize.get() == "소형차") {
                                if (isForeignCar) {
                                    ordAmount.set(
                                        (getPriceItem?.insideWashingCarS!!.toInt()
                                                + getPriceItem.addCost.toInt()).toString()
                                    )
                                } else {
                                    ordAmount.set(getPriceItem?.insideWashingCarS)
                                }
                            }
                            if (carSize.get() == "중형차") {
                                if (isForeignCar) {
                                    ordAmount.set(
                                        (getPriceItem?.insideWashingCarM!!.toInt()
                                                + getPriceItem.addCost.toInt()).toString()
                                    )
                                } else {
                                    ordAmount.set(getPriceItem?.insideWashingCarM)
                                }
                            }
                            if (carSize.get() == "대형차") {
                                if (isForeignCar) {
                                    ordAmount.set(
                                        (getPriceItem?.insideWashingCarL!!.toInt()
                                                + getPriceItem.addCost.toInt()).toString()
                                    )
                                } else {
                                    ordAmount.set(getPriceItem?.insideWashingCarL)
                                }
                            }
                        }

                        else -> {}
                    }
                }
        }
    }
}