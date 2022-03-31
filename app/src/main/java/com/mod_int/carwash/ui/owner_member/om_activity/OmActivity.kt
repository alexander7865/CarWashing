package com.mod_int.carwash.ui.owner_member.om_activity

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayout
import com.mod_int.carwash.CustomDialogFragment
import com.mod_int.carwash.CustomDialogListener
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.base.ViewState
import com.mod_int.carwash.databinding.ActivityOmBinding
import com.mod_int.carwash.ui.blank.OmBlankFragment
import com.mod_int.carwash.ui.owner_member.om_join.OmJoinFragment
import com.mod_int.carwash.ui.owner_member.om_state.OmOrderStateFragment
import com.mod_int.carwash.ui.owner_member.om_home.OmHomeFragment
import com.mod_int.carwash.manage.findwasher.OmFindWasherFragment
import com.mod_int.carwash.manage.history.OmManagementHistoryFragment
import com.mod_int.carwash.manage.pickuplist.PickupManagerPickupListFragment
import com.mod_int.carwash.ui.pickup_member.pm_home.PmHomeFragment
import com.mod_int.carwash.ui.pickup_member.pm_price.PmPriceStateFragment
import com.mod_int.carwash.ui.pickup_member.pm_registration.PmRegistrationFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OmActivity : BaseActivity<ActivityOmBinding>(R.layout.activity_om) {

    private val omViewModel by viewModels<OmViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
        initViewModel()

//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.add(R.id.owner_frag, OmHomeFragment()).commit()
//        transaction.addToBackStack("")
//
//        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                val transaction = supportFragmentManager.beginTransaction()
//                when (tab?.position){
//                    0 -> transaction.replace(R.id.owner_frag, OmHomeFragment())
//                    1 -> transaction.replace(R.id.owner_frag, OmOrderStateFragment())
//                    2 -> transaction.replace(R.id.owner_frag, OmManagementHistoryFragment())
//                    3 -> transaction.replace(R.id.owner_frag, OmFindWasherFragment())
//                }
//                transaction.commit()
//            }
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//
//            }
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//
//            }
//        })
    }

    private fun initUi() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.owner_frag, OmHomeFragment()).commit()
        transaction.addToBackStack("")

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val transaction = supportFragmentManager.beginTransaction()
                when (tab?.position){
                    0 -> transaction.replace(R.id.owner_frag, OmHomeFragment())
                    1 -> transaction.replace(R.id.owner_frag, OmOrderStateFragment())
                    2 -> transaction.replace(R.id.owner_frag, OmManagementHistoryFragment())
                    3 -> transaction.replace(R.id.owner_frag, OmFindWasherFragment())
                }

                transaction.commit()
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private fun initViewModel() {
        binding.viewModel = omViewModel
        omViewModel.viewStateLiveData.observe(this) { viewState ->
            (viewState as? OmViewState)?.let {
                onChangedOmViewState(
                    viewState
                )
            }
        }
    }

    private fun onChangedOmViewState(viewState: OmViewState) {
//        when(viewState){
//            is OmViewState.RouteJoin -> {
//                val transaction = supportFragmentManager.beginTransaction()
//                transaction.replace(R.id.owner_frag, OmJoinFragment()).commit()
//                transaction.addToBackStack("")
//
//            }
//            is OmViewState.RouteBlank -> {
//                val transaction = supportFragmentManager.beginTransaction()
//                transaction.replace(R.id.owner_frag, OmBlankFragment()).commit()
//                transaction.addToBackStack("")
//
//            }
//            is OmViewState.RouteBack -> {
//                onBackPressed()
//
//            }
//            is OmViewState.RouteDialog -> {
//                val dialog = CustomDialogFragment.CustomDialogBuilder()
//                    .setTitle("차량을 확인하셨나요?")
//                    .setQuestion("차량을 확인하셨다면 '확인' 버튼을 클릭해주세요! 세차이력은 [관리현황] 에서 확인 할 수 있습니다.")
//                    .setNoBtn("나중에 확인")
//                    .setYesBtn("확인완료")
//                    .setBtnClickListener(object : CustomDialogListener {
//                        override fun onClickNegativeBtn() {
//                            //불가능한경우 행동
//                        }
//
//                        override fun onClickPositiveBtn() {
//                            goBlankPage()
//                        }
//                    })
//                    .create()
//                dialog.show(supportFragmentManager, dialog.tag)
//            }
//        }
    }


    //조인페이지 등록 페이지
    fun joinRegistration() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.owner_frag, OmJoinFragment()).commit()
        transaction.addToBackStack("")
    }

    fun goBlankPage() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.owner_frag, OmBlankFragment()).commit()
        transaction.addToBackStack("")
    }

    fun backStep() {
        onBackPressed()
    }

    fun pickupCfmDialog() {
        val dialog = CustomDialogFragment.CustomDialogBuilder()
            .setTitle("차량을 확인하셨나요?")
            .setQuestion("차량을 확인하셨다면 '확인' 버튼을 클릭해주세요! 세차 이력은 [관리현황] 에서\n확인 할 수 있습니다.")
            .setNoBtn("나중에 확인")
            .setYesBtn("확인완료")
            .setBtnClickListener(object : CustomDialogListener {
                override fun onClickNegativeBtn() {
                    //불가능한경우 행동
                }

                override fun onClickPositiveBtn() {
                    goBlankPage()
                }
            })
            .create()
        dialog.show(supportFragmentManager, dialog.tag)
    }
}





