package com.mod_int.carwash.ui.washer_member.wm_activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityWmBinding
import com.mod_int.carwash.manage.orderlist.OrderListFragment
import com.mod_int.carwash.ui.washer_member.wm_home.WmHomeFragment
import com.mod_int.carwash.ui.washer_member.wm_registration.WmRegistrationFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WmActivity : BaseActivity<ActivityWmBinding>(R.layout.activity_wm) {
    private val wmViewModel by viewModels<WmViewModel>()
    private val tabConfigurationStrategy =
        TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = resources.getStringArray(R.array.array_wm)[position]
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        initViewModel()

    }

    @SuppressLint("WrongConstant")
    private fun initUI(){
        val list = listOf(
            WmHomeFragment(),
            WmRegistrationFragment(),
            OrderListFragment(),
        )

        val pagerAdapter = FragmentPagerAdapterWm(list, this)

        with(binding) {
            viewPager.adapter = pagerAdapter
            viewPager.offscreenPageLimit = OFF_SCREEN_COUNT
            viewPager.isUserInputEnabled = false
            TabLayoutMediator(tabLayout, viewPager, tabConfigurationStrategy).attach()
        }
    }

    private fun initViewModel(){
        binding.viewModel= wmViewModel
        wmViewModel.viewStateLiveData.observe(this){viewState ->
            (viewState as? WmViewState)?.let {
                onChangedWmState(viewState)
            }
        }
    }

    private fun onChangedWmState(viewState: WmViewState){

    }

    //???????????? ???????????? ??????
//    fun paymentWasher() {
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.washer_frag, WmPaymentFragment())
//        transaction.addToBackStack("")
//        transaction.commit()
//    }
//
//    fun goListOrderWasher() {
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.washer_frag, WasherOrderListFragment())
//        transaction.addToBackStack("")
//        transaction.commit()
//    }
//
//    fun goBlankScreen() {
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.washer_frag, WmBlankFragment())
//        transaction.addToBackStack("")
//        transaction.commit()
//    }
//
//    fun backStep () {
//        onBackPressed()
//    }

    companion object {
        private const val OFF_SCREEN_COUNT = 5
    }
}

class FragmentPagerAdapterWm(
    private val fragmentList: List<Fragment>,
    fragmentActivity: FragmentActivity
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = fragmentList.size
    override fun createFragment(position: Int) = fragmentList[position]

}

