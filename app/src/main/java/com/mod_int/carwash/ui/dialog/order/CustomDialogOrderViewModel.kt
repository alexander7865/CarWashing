package com.mod_int.carwash.ui.dialog.order

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ext.ioScope
import com.mod_int.carwash.model.OrderOmInfo
import com.mod_int.carwash.model.PriceItem
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CustomDialogOrderViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app), LifecycleEventObserver {
    private val current: LocalDateTime = LocalDateTime.now()

    var carSize = ObservableField("")
    var orderDate = ObservableField("${current.year}년 ${current.monthValue}월 ${current.dayOfMonth}일")
    var time = ObservableField("")
    var type = ObservableField("")
    var amount = ObservableField("")
    var orderMsg = ObservableField("")
    var companyName = ObservableField("")

    fun getCarInfo() {
        ioScope {
            val email = firebaseRepository.getFirebaseAuth().currentUser!!.email
            firebaseRepository.getFirebaseFireStore().collection("OwnerMember")
                .document("$email")
                .get()
                .addOnSuccessListener { document ->
                    carSize.set(document.get("carSize") as String)
                    getTotalPrice(document)
                }
        }
    }

    fun saveOmInfo() {
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
                        Log.d("가지고온값", "$data")
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

    private fun getTotalPrice(documentSnapshot: DocumentSnapshot) {

        val carSize = documentSnapshot.get("carSize") as String
        val isForeignCar  = (documentSnapshot.get("carOrigin") as String) == "외제차"
        
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

                    when (type.get()) {
                        "내부+외부세차" -> {
                            if (carSize == "경차") {
                                if (isForeignCar) {
                                    amount.set((getPriceItem?.inOutsideWashingCarXS!!.toInt()
                                            + getPriceItem?.addCost!!.toInt()
                                            + getPriceItem?.addCost!!.toInt()).toString())
                                } else {
                                    amount.set(getPriceItem?.inOutsideWashingCarXS)
                                }
                            }
                            if (carSize == "소형차") {
                                if (isForeignCar) {
                                    amount.set((getPriceItem?.inOutsideWashingCarS!!.toInt()
                                            + getPriceItem?.addCost!!.toInt()
                                            + getPriceItem?.addCost!!.toInt()).toString())
                                } else {
                                    amount.set(getPriceItem?.inOutsideWashingCarS)
                                }
                            }
                            if (carSize == "중형차") {
                                if (isForeignCar) {
                                    amount.set((getPriceItem?.inOutsideWashingCarM!!.toInt()
                                            + getPriceItem?.addCost!!.toInt()
                                            + getPriceItem?.addCost!!.toInt()).toString())
                                } else {
                                    amount.set(getPriceItem?.inOutsideWashingCarM)
                                }
                            }
                            if (carSize == "대형차") {
                                if (isForeignCar) {
                                    amount.set((getPriceItem?.inOutsideWashingCarL!!.toInt()
                                            + getPriceItem?.addCost!!.toInt()
                                            + getPriceItem?.addCost!!.toInt()).toString())
                                } else {
                                    amount.set(getPriceItem?.inOutsideWashingCarL)
                                }
                            }
                        }

                        "외부세차" -> {
                            if (carSize == "경차") {
                                if (isForeignCar) {
                                    amount.set((getPriceItem?.outsideWashingCarXS!!.toInt()
                                            + getPriceItem?.addCost!!.toInt()).toString())
                                } else {
                                    amount.set(getPriceItem?.outsideWashingCarXS)
                                }
                            }
                            if (carSize == "소형차") {
                                if (isForeignCar) {
                                    amount.set((getPriceItem?.outsideWashingCarS!!.toInt()
                                            + getPriceItem?.addCost!!.toInt()).toString())
                                } else {
                                    amount.set(getPriceItem?.outsideWashingCarS)
                                }
                            }
                            if (carSize == "중형차") {
                                if (isForeignCar) {
                                    amount.set((getPriceItem?.outsideWashingCarM!!.toInt()
                                            + getPriceItem?.addCost!!.toInt()).toString())
                                } else {
                                    amount.set(getPriceItem?.outsideWashingCarM)
                                }
                            }
                            if (carSize == "대형차") {
                                if (isForeignCar) {
                                    amount.set((getPriceItem?.outsideWashingCarL!!.toInt()
                                            + getPriceItem?.addCost!!.toInt()).toString())
                                } else {
                                    amount.set(getPriceItem?.outsideWashingCarL)
                                }
                            }
                        }

                        "내부세차" -> {
                            if (carSize == "경차") {
                                if (isForeignCar) {
                                    amount.set((getPriceItem?.insideWashingCarXS!!.toInt()
                                            + getPriceItem?.addCost!!.toInt()).toString())
                                } else {
                                    amount.set(getPriceItem?.insideWashingCarXS)
                                }
                            }
                            if (carSize == "소형차") {
                                if (isForeignCar) {
                                    amount.set((getPriceItem?.insideWashingCarS!!.toInt()
                                            + getPriceItem?.addCost!!.toInt()).toString())
                                } else {
                                    amount.set(getPriceItem?.insideWashingCarS)
                                }
                            }
                            if (carSize == "중형차") {
                                if (isForeignCar) {
                                    amount.set((getPriceItem?.insideWashingCarM!!.toInt()
                                            + getPriceItem?.addCost!!.toInt()).toString())
                                } else {
                                    amount.set(getPriceItem?.insideWashingCarM)
                                }
                            }
                            if (carSize == "대형차") {
                                if (isForeignCar) {
                                    amount.set((getPriceItem?.insideWashingCarL!!.toInt()
                                            + getPriceItem?.addCost!!.toInt()).toString())
                                } else {
                                    amount.set(getPriceItem?.insideWashingCarL)
                                }
                            }
                        }

                        else -> {}
                    }
                }
        }
    }
}