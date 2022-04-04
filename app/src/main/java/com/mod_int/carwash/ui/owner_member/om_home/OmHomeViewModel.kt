package com.mod_int.carwash.ui.owner_member.om_home

import android.app.Application
import androidx.databinding.ObservableField
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ui.owner_member.om_state.OmOrderStateViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class OmHomeViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository,
) : BaseViewModel(app) {

    val nowDate = ObservableField("") //날짜는 꼭 들어오는 값입니다.
    val phoneNr = ObservableField("") //폰넘버는 꼭 들어오는 값입니다.
    val myCar = ObservableField("미등록")
    val myLocation = ObservableField("미등록")
    private val current: LocalDateTime = LocalDateTime.now()

    fun omHomeInfo() {
        nowDate.set("${current.year}년 ${current.monthValue}월 ${current.dayOfMonth}일")
        phoneNr.set("010-1111-1111")
        myLocation.set("서울시 강남구 논현동 111-11 지하주차장")
        carInfoCheck()

    }

    private fun carInfoCheck() {
        firebaseRepository.getFirebaseAuth().currentUser!!.email.let { email ->
            firebaseRepository.getFirebaseFireStore().collection("OwnerMember")
                .document(email.toString())
                .get()
                .addOnSuccessListener { document ->
                    return@addOnSuccessListener when {
                        (myCar.get().isNullOrEmpty()) -> {


                        }else -> {
                            myCar.set(
                                "${document.get("carNumber") as String?} \n" +
                                        "${(document.get("carBrand") as String?).orEmpty()} " +
                                        "${(document.get("carModel") as String?).orEmpty()} " +
                                        "${(document.get("carKinds") as String?).orEmpty()} " +
                                        "${(document.get("carSize") as String?).orEmpty()} " +
                                        (document.get("carColor") as String?).orEmpty()
                            )
                        }
                    }
                }

        }
    }


    fun routeOmJoin() {
        viewStateChanged(OmHomeViewState.RouteOmJoin)
    }

    fun routeWebViewSuggest1(){
        viewStateChanged(OmHomeViewState.RouteWebViewSuggestOm1)
    }

    fun routeWebViewSuggest2(){
        viewStateChanged(OmHomeViewState.RouteWebViewSuggestOm2)
    }
}

