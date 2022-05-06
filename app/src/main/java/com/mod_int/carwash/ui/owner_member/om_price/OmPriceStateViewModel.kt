package com.mod_int.carwash.ui.owner_member.om_price

import android.app.Application
import androidx.databinding.ObservableField
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ext.ioScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OmPriceStateViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository,
) : BaseViewModel(app) {

    var insideWashingKoreaCarXS = ObservableField("")
    var insideWashingKoreaCarS = ObservableField("")
    var insideWashingKoreaCarM = ObservableField("")
    var insideWashingKoreaCarL = ObservableField("")
    var outsideWashingKoreaCarXS = ObservableField("")
    var outsideWashingKoreaCarS = ObservableField("")
    var outsideWashingKoreaCarM = ObservableField("")
    var outsideWashingKoreaCarL = ObservableField("")
    var inOutsideWashingKoreaCarXS = ObservableField("")
    var inOutsideWashingKoreaCarS = ObservableField("")
    var inOutsideWashingKoreaCarM = ObservableField("")
    var inOutsideWashingKoreaCarL = ObservableField("")
    var insideWashingForeignCarXS = ObservableField("")
    var insideWashingForeignCarS = ObservableField("")
    var insideWashingForeignCarM = ObservableField("")
    var insideWashingForeignCarL = ObservableField("")
    var outsideWashingForeignCarXS = ObservableField("")
    var outsideWashingForeignCarS = ObservableField("")
    var outsideWashingForeignCarM = ObservableField("")
    var outsideWashingForeignCarL = ObservableField("")
    var inOutsideWashingForeignCarXS = ObservableField("")
    var inOutsideWashingForeignCarS = ObservableField("")
    var inOutsideWashingForeignCarM = ObservableField("")
    var inOutsideWashingForeignCarL = ObservableField("")
    var insideWashingTime = ObservableField("")
    var outsideWashingTime = ObservableField("")
    var inOutsideWashingTime = ObservableField("")


    //단가의 경우 리사이클러뷰 에서 넘어온 넘어온 워셔의 이메일 여부를 확인하여 데이터들을 가지고와서 set을 하고 싶습니다
    //하지만 리사이클러뷰에서 워셔의 해당 이메일값을 확인할 방법을 모르겠습니다 어떻게 할까요?
    fun getWashingPrice() {

        ioScope {
            firebaseRepository.getFirebaseFireStore()
                .collection("WasherMember")
                .document("hh@hh.com") //임의로 이메일값을 넣어 두었습니다. 발동될때마다 해당 워셔의 저장 값으로 변경되어야 합니다.
                .get()
                .addOnSuccessListener { document ->
                    inOutsideWashingForeignCarL.set("${document.get("inOutsideWashingForeignCarL")}")
                    viewStateChanged(OmPriceStateViewState.GetWashingPrice)

                }

        }
    }

    fun routeBackStep(){
        viewStateChanged(OmPriceStateViewState.BackStep)
    }
}
