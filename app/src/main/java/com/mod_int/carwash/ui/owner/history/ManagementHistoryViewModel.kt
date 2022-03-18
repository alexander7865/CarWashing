package com.mod_int.carwash.ui.owner.history

import android.app.Application
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagementHistoryViewModel
@Inject constructor(app: Application, private val firebaseRepository: FirebaseRepository) :
    BaseViewModel(app) {




}