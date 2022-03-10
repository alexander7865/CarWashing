package com.mod_int.carwash.ui.pickup_manager

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.mod_int.carwash.MainViewModel
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityPickupManagerBinding
import com.mod_int.carwash.ui.washer.WasherBlankFragment

class PickupManagerActivity : BaseActivity<ActivityPickupManagerBinding>(R.layout.activity_pickup_manager) {

    private val mainViewModel by lazy { ViewModelProvider(
        this, defaultViewModelProviderFactory).get(MainViewModel::class.java)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       val transaction = supportFragmentManager.beginTransaction()
       transaction.add(R.id.pickupManager_frag, PickupManagerHomeFragment())
       transaction.commit()
        transaction.addToBackStack("픽업매니저")

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val transaction = supportFragmentManager.beginTransaction()
                when(tab?.text) {
                    "픽업매니저" -> transaction.replace(R.id.pickupManager_frag, PickupManagerHomeFragment()).commit()
                    "정보등록" -> transaction.replace(R.id.pickupManager_frag, PickupManagerRegistrationFragment()).commit()
                    "단가현황" -> transaction.replace(R.id.pickupManager_frag, PickupManagerPriceStatusFragment()).commit()
                    "작업현황" -> transaction.replace(R.id.pickupManager_frag, PickupManagerPickupListFragment()).commit()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    fun settlementRequest() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.pickupManager_frag, PickupManagerSettlementRequestFragment())
        transaction.addToBackStack("뒤로가기")
        transaction.commit()
    }

    fun goBlackTipScreen() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.pickupManager_frag, PickupManagerBlankFragment())
        transaction.addToBackStack("뒤로가기")
        transaction.commit()
    }

    fun goDetailOrderPickup() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.pickupManager_frag, PickupManagerPickupStatusFragment())
        transaction.addToBackStack("디테일페이지이동")
        transaction.commit()
    }

    fun goListOrderPickup() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.pickupManager_frag, PickupManagerPickupListFragment())
        transaction.addToBackStack("리스트페이지이동")
        transaction.commit()
    }

    fun backStep () {
        onBackPressed()
    }
}