package com.mod_int.carwash.ui.owner_member.om_home

import android.app.Application
import androidx.databinding.ObservableField
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class OmHomeViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository,
) : BaseViewModel(app) {

    val date = ObservableField("") //날짜는 꼭 들어오는 값입니다.
    val phoneNr = ObservableField("") //폰넘버는 꼭 들어오는 값입니다.
    val myCar = ObservableField("미등록")
    val myLocation = ObservableField("미등록")
    private val current: LocalDateTime = LocalDateTime.now()

    fun homeInfo() {
        date.set("${current.year}년 ${current.monthValue}월 ${current.dayOfMonth}일")
        phoneNr.set("010-1111-1111")
        myLocation.set("서울시 강남구 논현동 111-11 지하주차장")
        carInfoCheck()

    }


    //데이터 베이스에서 가지고오게 구현을 하면 함수가 지속적으로 진행 되기에 화면이 깜빡이는 현상이 일어 납니다 이부분은
    //레지스터와 똑같이 인풋데이터와 등록된 데이터를 비교해서 수정등록을 하는 방법으로 처리하면 될까용? 어렵네용
    //그리고 최초에 값이 없을때는 터지네요 ㅎㅎㅎ 요부분은 바꾸면 될꺼 같습니다.
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
                                "${document.get("carNumber") as String?} " +
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
}

