package com.mod_int.carwash.ui.owner_member.om_activity

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayout
import com.mod_int.carwash.CustomDialogFragment
import com.mod_int.carwash.CustomDialogListener
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityOmBinding
import com.mod_int.carwash.ui.blank.OmBlankFragment
import com.mod_int.carwash.ui.owner_member.om_join.OmJoinFragment
import com.mod_int.carwash.ui.owner_member.om_state.OmOrderStateFragment
import com.mod_int.carwash.ui.owner_member.om_home.OmHomeFragment
import com.mod_int.carwash.ui.owner_member.recyclerview.findwasher.OmFindWasherFragment
import com.mod_int.carwash.ui.owner_member.recyclerview.washinghistory.OmManagementHistoryFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OmActivity : BaseActivity<ActivityOmBinding>(R.layout.activity_om) {

    private val omViewModel by viewModels<OmViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.owner_frag, OmHomeFragment())
        transaction.commit()
        transaction.addToBackStack("차주 홈")


        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val transaction = supportFragmentManager.beginTransaction()
                when(tab?.text) {
                    "차주 홈" -> transaction.replace(R.id.owner_frag, OmHomeFragment()).commit()
                    "세차현황" -> transaction.replace(R.id.owner_frag, OmOrderStateFragment()).commit()
                    "관리현황" -> transaction.replace(R.id.owner_frag, OmManagementHistoryFragment()).commit()
                    "워셔찾기" -> transaction.replace(R.id.owner_frag, OmFindWasherFragment()).commit()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    //조인페이지 등록 페이지
    fun joinRegistration() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.owner_frag, OmJoinFragment())
        transaction.commit()
        transaction.addToBackStack("가입하기")
    }

    fun goBlankPage() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.owner_frag, OmBlankFragment())
        transaction.commit()
        transaction.addToBackStack("블랭크")
    }

    fun backStep() {
        onBackPressed()
    }

    fun pickupCfmDialog() {
        val dialog = CustomDialogFragment.CustomDialogBuilder()
            .setTitle("차량을 확인하셨나요?")
            .setQuestion("차량을 확인하셨다면 '확인' 버튼을 클릭해주세요! 세차이력은 [관리현황] 에서 확인 할 수 있습니다.")
            .setNoBtn("나중에 확인")
            .setYesBtn("확인완료")
            .setBtnClickListener(object : CustomDialogListener {
                override fun onClickNegativeBtn() {
                    //불가능한경우 행동
                }

                override fun onClickPositiveBtn() {
                    goBlankPage()
                }
            })
            .create()
        dialog.show(supportFragmentManager, dialog.tag)
    }




    //뷰모델 관찰자 초기화
    private fun initViewModel() {

    }

    //변경될경우 행동
    private fun onChangedOmViewState(viewState: OmViewState) {

    }
}




