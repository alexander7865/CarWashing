package com.mod_int.carwash.ui.pickup_member.pm_activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityPmBinding
import com.mod_int.carwash.ui.pickup_member.pm_home.PmHomeFragment
import com.mod_int.carwash.ui.pickup_member.pm_registration.PmRegistrationFragment
import com.mod_int.carwash.manage.pickuplist.PickupManagerPickupListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PmActivity : BaseActivity<ActivityPmBinding>(R.layout.activity_pm) {

    private val pmViewModel by viewModels<PmViewModel>()
    private val tabConfigurationStrategy =
        TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = resources.getStringArray(R.array.array_pm)[position]
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        initViewModel()
    }

    @SuppressLint("WrongConstant")
    private fun initUI() {
        val list = listOf(
            PmHomeFragment(),
            PmRegistrationFragment(),
            PickupManagerPickupListFragment(),
        )

        val pagerAdapter = FragmentPagerAdapterPm(list, this)
        with(binding) {
            viewPager.adapter = pagerAdapter
            viewPager.offscreenPageLimit = OFF_SCREEN_COUNT
            viewPager.isUserInputEnabled = false
            TabLayoutMediator(tabLayout, viewPager, tabConfigurationStrategy).attach()
        }
    }

    private fun initViewModel(){
        binding.viewModel = pmViewModel
        pmViewModel.viewStateLiveData.observe(this){viewState->
            (viewState as? PmViewState)?.let {
                onChangedPmState(viewState)
            }
        }
    }

    private fun onChangedPmState(viewState: PmViewState){

    }



//    fun settlementRequest() {
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.pickupManager_frag, PmSettlementRequestFragment())
//        transaction.addToBackStack("")
//        transaction.commit()
//    }
//
//    fun goBlankTipScreen() {
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.pickupManager_frag, PmBlankFragment())
//        transaction.addToBackStack("")
//        transaction.commit()
//    }
//
//    fun goDetailOrderPickup() {
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.pickupManager_frag, PmPickupStateFragment())
//        transaction.addToBackStack("")
//        transaction.commit()
//    }
//
//    fun goListOrderPickup() {
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.pickupManager_frag, PickupManagerPickupListFragment())
//        transaction.addToBackStack("")
//        transaction.commit()
//    }
//    fun backStep () {
//        onBackPressed()
//    }
    //커스텀 다이얼로그 만들었습니다.
//    fun pickupCfmDialog() {
//        val dialog = CustomDialogFragment.CustomDialogBuilder()
//            .setTitle("픽업 가능한가요?")
//            .setQuestion("000허0000 벤츠 GLC220 SUV BLACK\n내부+외부 준중형 (외제차)")
//            .setNoBtn("불가능해요")
//            .setYesBtn("가능해요")
//            .setBtnClickListener(object : CustomDialogListener {
//                override fun onClickNegativeBtn() {
//                    //불가능한경우 행동
//                }
//
//                override fun onClickPositiveBtn() {
////                    goDetailOrderPickup()
//                }
//            })
//            .create()
//        dialog.show(supportFragmentManager, dialog.tag)
//    }

    companion object {
        private const val OFF_SCREEN_COUNT = 5
    }
}
class FragmentPagerAdapterPm(
    private val fragmentList: List<Fragment>,
    fragmentActivity: FragmentActivity
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = fragmentList.size
    override fun createFragment(position: Int) = fragmentList[position]

}