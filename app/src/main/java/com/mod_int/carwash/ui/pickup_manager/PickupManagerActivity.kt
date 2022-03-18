package com.mod_int.carwash.ui.pickup_manager

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.mod_int.carwash.CustomDialogFragment
import com.mod_int.carwash.CustomDialogListener
import com.mod_int.carwash.MainViewModel
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityPickupManagerBinding
import com.mod_int.carwash.ui.pickup_manager.pickuplist.PickupManagerPickupListFragment

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
                    "픽업 홈" -> transaction.replace(R.id.pickupManager_frag, PickupManagerHomeFragment()).commit()
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
        transaction.addToBackStack("")
        transaction.commit()
    }

    fun goBlankTipScreen() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.pickupManager_frag, PickupManagerBlankFragment())
        transaction.addToBackStack("")
        transaction.commit()
    }

    fun goDetailOrderPickup() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.pickupManager_frag, PickupManagerPickupStatusFragment())
        transaction.addToBackStack("")
        transaction.commit()
    }

    fun goListOrderPickup() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.pickupManager_frag, PickupManagerPickupListFragment())
        transaction.addToBackStack("")
        transaction.commit()
    }

    fun backStep () {
        onBackPressed()
    }
    //커스텀 다이얼로그 만들었습니다.
    fun pickupCfmDialog() {
        val dialog = CustomDialogFragment.CustomDialogBuilder()
            .setTitle("픽업 가능한가요?")
            .setQuestion("000허0000 벤츠 GLC220 SUV BLACK\n내부+외부 준중형 (외제차)")
            .setNoBtn("불가능해요")
            .setYesBtn("가능해요")
            .setBtnClickListener(object : CustomDialogListener {
                override fun onClickNegativeBtn() {
                    //불가능한경우 행동
                }

                override fun onClickPositiveBtn() {
                    goDetailOrderPickup()
                }
            })
            .create()
        dialog.show(supportFragmentManager, dialog.tag)
    }
}