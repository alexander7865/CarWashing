package com.mod_int.carwash.ui.washer_member.wm_activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityWmBinding
import com.mod_int.carwash.ui.blank.WmBlankFragment
import com.mod_int.carwash.ui.washer_member.wm_home.WmHomeFragment
import com.mod_int.carwash.ui.washer_member.wm_payment.WmPaymentFragment
import com.mod_int.carwash.ui.washer_member.wm_registration.WmRegistrationFragment
import com.mod_int.carwash.ui.washer_member.wm_price.WmRegistrationPriceFragment
import com.mod_int.carwash.manage.orderlist.WasherOrderListFragment
import com.mod_int.carwash.ui.pickup_member.pm_state.PmPickupStateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WmActivity : BaseActivity<ActivityWmBinding>(R.layout.activity_wm) {

    private val wmViewModel by viewModels<WmViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //최초 보여줄 화면
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.washer_frag, WmHomeFragment())
        transaction.commit()
        transaction.addToBackStack("")

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val transaction = supportFragmentManager.beginTransaction()
                when(tab?.position) {
                    0 -> transaction.replace(R.id.washer_frag, WmHomeFragment())
                    1 -> transaction.replace(R.id.washer_frag, WmRegistrationFragment())
                    2 -> transaction.replace(R.id.washer_frag, WmRegistrationPriceFragment())
                    3 -> transaction.replace(R.id.washer_frag, WasherOrderListFragment())
                }
                transaction.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    //페이먼트 페이지로 이동
    fun paymentWasher() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.washer_frag, WmPaymentFragment())
        transaction.addToBackStack("")
        transaction.commit()
    }

    fun goListOrderWasher() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.washer_frag, WasherOrderListFragment())
        transaction.addToBackStack("")
        transaction.commit()
    }

    fun goBlankScreen() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.washer_frag, WmBlankFragment())
        transaction.addToBackStack("")
        transaction.commit()
    }

    fun backStep () {
        onBackPressed()
    }

}

