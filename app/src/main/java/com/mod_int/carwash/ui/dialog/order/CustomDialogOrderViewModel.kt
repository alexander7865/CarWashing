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

    //해쉬맵으로 저장을 해야 하는데 데이터구조를 잘 못 만든거 같습니다.
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
                            if (carSize == "중형차") {
                                if (isForeignCar) {
                                    amount.set((getPriceItem?.inOutsideWashingCarM!!.toInt() + 4000).toString())
                                } else {
                                    amount.set(getPriceItem?.inOutsideWashingCarM)
                                }
                            }
                        }

                        "외부세차" -> {
                            if (carSize == "중형차") {
                                if (isForeignCar) {
                                    amount.set((getPriceItem?.outsideWashingCarM!!.toInt() + 2000).toString())
                                } else {
                                    amount.set(getPriceItem?.outsideWashingCarM)
                                }
                            }
                        }

                        "내부세차" -> {
                            if (carSize == "중형차") {
                                if (isForeignCar) {
                                    amount.set((getPriceItem?.insideWashingCarM!!.toInt() + 2000).toString())
                                } else {
                                    amount.set(getPriceItem?.insideWashingCarM)
                                }
                            }
                        }

                        else -> {}
                    }
                }
        }
    }

}