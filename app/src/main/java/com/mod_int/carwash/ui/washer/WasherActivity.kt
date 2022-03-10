package com.mod_int.carwash.ui.washer

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.mod_int.carwash.CustomDialogFragment
import com.mod_int.carwash.MainViewModel
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityWasherBinding

class WasherActivity : BaseActivity<ActivityWasherBinding>(R.layout.activity_washer) {

    private val mainViewModel by lazy { ViewModelProvider(
        this, defaultViewModelProviderFactory).get(MainViewModel::class.java)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //최초 보여줄 화면
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.washer_frag, WasherHomeFragment())
        transaction.commit()
        transaction.addToBackStack("워셔 홈")

        //하단 바텀네비게이션에서 탭레이아웃으로 변경함
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val transaction = supportFragmentManager.beginTransaction()
                when(tab?.text) {
                    "워셔 홈" -> transaction.replace(R.id.washer_frag, WasherHomeFragment()).commit()
                    "정보등록" -> transaction.replace(R.id.washer_frag, WasherRegistrationFragment()).commit()
                    "단가등록" -> transaction.replace(R.id.washer_frag, WasherRegistrationPriceFragment()).commit()
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
        transaction.replace(R.id.washer_frag,WasherPaymentFragment())
        transaction.addToBackStack("뒤로가기")
        transaction.commit()
    }

    fun goBlackScreen() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.washer_frag,WasherBlankFragment())
        transaction.addToBackStack("뒤로가기")
        transaction.commit()
    }

    fun goDetailOrderWasher() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.washer_frag,WasherOrderStatusFragment())
        transaction.addToBackStack("디테일페이지이동")
        transaction.commit()
    }

    fun goListOrderWasher() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.washer_frag,WasherOrderListFragment())
        transaction.addToBackStack("리스트페이지이동")
        transaction.commit()
    }

    fun backStep () {
        onBackPressed()
    }

    fun orderCfmDialog() {
        val customDialog = CustomDialogFragment()
        customDialog.show(supportFragmentManager, "CustomDialog")

    }
}

