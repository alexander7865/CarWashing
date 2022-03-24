package com.mod_int.carwash.ui.owner_member.om_activity

import android.app.Application
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OmViewModel @Inject constructor(
    app : Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {



}