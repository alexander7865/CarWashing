package com.mod_int.carwash.manage.orderlist

import android.app.Application
import com.google.firebase.firestore.ktx.toObject
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ext.ioScope
import com.mod_int.carwash.model.HistoryInfo
import com.mod_int.carwash.model.OrderList
import com.mod_int.carwash.model.WasherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderListViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {
    fun getOrderInfo(){
        ioScope {
            firebaseRepository.getFirebaseFireStore()
                .collection("OwnerMember")
                .addSnapshotListener { querySnapshot, _ ->

                    for (info in querySnapshot!!.documentChanges) {
                        val orderInfo = OrderList(
                            email = "${info.document.get("email")}",
                            ordDate = "${info.document.get("orderDate")}",
                            ordState = "세차대기중",
                            ordType = "${info.document.get("orderType")}",
                            ordCarNum = "${info.document.get("carNumber")}",
                            ordCarBrand = "${info.document.get("carBrand")}",
                            ordCarModel = "${info.document.get("carModel")}",
                            ordCarCategory = "${info.document.get("carKinds")}",
                            ordCarSize = "${info.document.get("carSize")}",
                            ordCarCol = "${info.document.get("carColor")}",
                            ordLocation = "${info.document.get("carDetailLocation")}",
                            ordTime = "${info.document.data["orderReservationTime"]}",
                        )

                        val ordInfo = mutableListOf<OrderList>().apply {
                            add(orderInfo)
                        }

                        viewStateChanged(OrderListViewState.GetOrderInfo(ordInfo))
                    }
                }
        }
    }
}