package com.mod_int.carwash.ui.washer_member.wm_payment

import android.app.Application
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WmPaymentViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {


    fun routeBackStep() {
        viewStateChanged(WmPaymentViewState.RouteBackStep)
    }
}