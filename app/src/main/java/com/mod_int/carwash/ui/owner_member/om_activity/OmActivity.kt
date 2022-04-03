package com.mod_int.carwash.ui.owner_member.om_activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mod_int.carwash.CustomDialogFragment
import com.mod_int.carwash.CustomDialogListener
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.base.ViewState
import com.mod_int.carwash.databinding.ActivityOmBinding
import com.mod_int.carwash.databinding.FragmentWmPaymentBindingImpl
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
    private val tabConfigurationStrategy =
        TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = resources.getStringArray(R.array.array_content)[position]
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
        initViewModel()

    }

    @SuppressLint("WrongConstant")
    private fun initUi() {
        val list = listOf(
            OmHomeFragment(),
            OmOrderStateFragment(),
            OmManagementHistoryFragment(),
            OmFindWasherFragment()
        )

        val pagerAdapter = FragmentPagerAdapter(list, this)

        with(binding) {
            viewPager.adapter = pagerAdapter
            viewPager.offscreenPageLimit = OFF_SCREEN_COUNT
            viewPager.isUserInputEnabled = false
            TabLayoutMediator(tabLayout, viewPager, tabConfigurationStrategy).attach()
        }
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
    }

    companion object {
        private const val OFF_SCREEN_COUNT = 5
    }
}


class FragmentPagerAdapter(
    private val fragmentList: List<Fragment>,
    fragmentActivity: FragmentActivity
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = fragmentList.size
    override fun createFragment(position: Int) = fragmentList[position]

}





