package com.mod_int.carwash.ui.owner_member.om_activity

import android.app.Application
import com.google.android.material.tabs.TabLayout
import com.mod_int.carwash.CustomDialogFragment
import com.mod_int.carwash.CustomDialogListener
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.manage.findwasher.OmFindWasherFragment
import com.mod_int.carwash.manage.history.OmManagementHistoryFragment
import com.mod_int.carwash.ui.blank.OmBlankFragment
import com.mod_int.carwash.ui.owner_member.om_home.OmHomeFragment
import com.mod_int.carwash.ui.owner_member.om_join.OmJoinFragment
import com.mod_int.carwash.ui.owner_member.om_state.OmOrderStateFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OmViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository,
) : BaseViewModel(app) {

}



