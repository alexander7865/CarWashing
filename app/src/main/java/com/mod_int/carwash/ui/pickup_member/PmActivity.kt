package com.mod_int.carwash.ui.pickup_member

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.mod_int.carwash.CustomDialogFragment
import com.mod_int.carwash.CustomDialogListener
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityPmBinding
import com.mod_int.carwash.ui.pickup_member.recyclerview.PickupManagerPickupListFragment

class PmActivity : BaseActivity<ActivityPmBinding>(R.layout.activity_pm) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       val transaction = supportFragmentManager.beginTransaction()
       transaction.add(R.id.pickupManager_frag, PmHomeFragment())
       transaction.commit()
        transaction.addToBackStack("픽업매니저")

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val transaction = supportFragmentManager.beginTransaction()
                when(tab?.text) {
                    "픽업 홈" -> transaction.replace(R.id.pickupManager_frag, PmHomeFragment()).commit()
                    "정보등록" -> transaction.replace(R.id.pickupManager_frag, PmRegistrationFragment()).commit()
                    "단가현황" -> transaction.replace(R.id.pickupManager_frag, PmPriceStateFragment()).commit()
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
        transaction.replace(R.id.pickupManager_frag, PmSettlementRequestFragment())
        transaction.addToBackStack("")
        transaction.commit()
    }

    fun goBlankTipScreen() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.pickupManager_frag, PmBlankFragment())
        transaction.addToBackStack("")
        transaction.commit()
    }

    fun goDetailOrderPickup() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.pickupManager_frag, PmPickupStateFragment())
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