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
    //최종 세차가 끝나고나서 생성되는 데이터 입니다. 현재는 임의로 만들었습니다.
    fun getFinishedOrder() {
        var num = 0
        firebaseRepository.getFirebaseFireStore().collection("OwnerMember")
            .addSnapshotListener { querySnapshot, _ ->
                for (snapshot in querySnapshot!!.documentChanges){
                    val list = HistoryInfo(
                        date = "2022년 02월 02일 ${num++}",
                        washType = "내부 + 외부세차(외제차)",
                        carInfo = "000호000 벤츠 GLC220 SUV 준중형 BLACK",
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

