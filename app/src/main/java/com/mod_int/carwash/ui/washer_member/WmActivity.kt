package com.mod_int.carwash.ui.washer_member

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityOmBinding
import com.mod_int.carwash.ui.washer_member.recyclerview.WasherOrderListFragment

class WmActivity : BaseActivity<ActivityOmBinding>(R.layout.activity_wm) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //최초 보여줄 화면
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.washer_frag, WmHomeFragment())
        transaction.commit()
        transaction.addToBackStack("워셔 홈")

        //하단 바텀네비게이션에서 탭레이아웃으로 변경함
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val transaction = supportFragmentManager.beginTransaction()
                when(tab?.text) {
                    "워셔 홈" -> transaction.replace(R.id.washer_frag, WmHomeFragment()).commit()
                    "정보등록" -> transaction.replace(R.id.washer_frag, WmRegistrationFragment()).commit()
                    "단가등록" -> transaction.replace(R.id.washer_frag, WmRegistrationPriceFragment()).commit()
                    "작업현황" -> transaction.replace(R.id.washer_frag, WasherOrderListFragment()).commit()
                }
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
        transaction.replace(R.id.washer_frag,WmPaymentFragment())
        transaction.addToBackStack("뒤로가기")
        transaction.commit()
    }

    fun goListOrderWasher() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.washer_frag, WasherOrderListFragment())
        transaction.addToBackStack("리스트페이지이동")
        transaction.commit()
    }

    fun goBlankScreen() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.washer_frag,WmBlankFragment())
        transaction.addToBackStack("뒤로가기")
        transaction.commit()
    }

    fun backStep () {
        onBackPressed()
    }

}

