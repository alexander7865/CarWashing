package com.mod_int.carwash.ui.owner

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.mod_int.carwash.MainViewModel
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityOwnerBinding

class OwnerActivity : BaseActivity<ActivityOwnerBinding>(R.layout.activity_owner) {

    private val mainViewModel by lazy { ViewModelProvider(
        this, defaultViewModelProviderFactory).get(MainViewModel::class.java)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //최초 화면
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.owner_frag, HomeOwnerFragment())
        transaction.commit()
        transaction.addToBackStack("차주 홈")


        //하단 바텀네비게이션에서 탭레이아웃으로 변경함
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val transaction = supportFragmentManager.beginTransaction()
                when(tab?.text) {
                    "차주 홈" -> transaction.replace(R.id.owner_frag, HomeOwnerFragment()).commit()
                    "워셔찾기" -> transaction.replace(R.id.owner_frag, FindWasherOwnerFragment()).commit()
                    "세차현황" -> transaction.replace(R.id.owner_frag, OrderStatusOwnerFragment()).commit()
                    "관리현황" -> transaction.replace(R.id.owner_frag, ManagementHistoryOwnerFragment()).commit()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    //조인페이지 등록 페이지
    fun joinRegistration () {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.owner_frag, JoinOwnerFragment())
        transaction.commit()
        transaction.addToBackStack("가입하기")
    }

    fun goBlankPage () {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.owner_frag, BlankOwnerFragment())
        transaction.commit()
        transaction.addToBackStack("세차현황")

    }


    fun backStep () {
        onBackPressed()
    }

    //세차작업 의뢰버튼 클릭시 세차현황으로 이동이 되어야하는데 구현이 안됩니다 (리사이클러뷰의 홀더 안에 버튼을 구현했음)
}





