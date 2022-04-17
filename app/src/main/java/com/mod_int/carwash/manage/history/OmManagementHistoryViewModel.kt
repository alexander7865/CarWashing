package com.mod_int.carwash.manage.history

import android.app.Application
import android.util.Log
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.ext.ioScope
import com.mod_int.carwash.model.HistoryInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OmManagementHistoryViewModel
@Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository) :
    BaseViewModel(app){


    //파이어스토어에서 컬렉션 이하의 모든값을 가지고 오는 테스트 성공함, 체크함수 만들어서 최종 히스토리코드 및 데이터 바꿔야함
    fun getFinishedOrder() {
        firebaseRepository.getFirebaseFireStore().collection("WasherMember")
            .addSnapshotListener { querySnapshot, _ ->
                for (snapshot in querySnapshot!!.documents){
                    val list = HistoryInfo(
                        date = "2022년 02월 02일",
                        washType = snapshot.get("email") as String,
                        carInfo = snapshot.get("wmPhoneNr") as String,
                    )
                    val historyInfo = mutableListOf<HistoryInfo>().apply {
                        add(list)
                    }
                    viewStateChanged(OmManagementHistoryViewState.GetHistoryOrder(historyInfo))
                }
            }

    }

    private fun checkInfo(){

    }

    private fun dateCheck(){

    }

    private fun washTypeCheck(){

    }

    private fun carInfoCheck(){

    }
}

