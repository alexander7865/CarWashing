package com.mod_int.carwash.ui.owner_member.om_activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.mod_int.carwash.BuildConfig
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseActivity
import com.mod_int.carwash.databinding.ActivityOmBinding
import com.mod_int.carwash.ext.showToast
import com.mod_int.carwash.manage.findwasher.OmFindWasherFragment
import com.mod_int.carwash.manage.history.OmManagementHistoryFragment
import com.mod_int.carwash.ui.owner_member.om_home.OmHomeFragment
import com.mod_int.carwash.ui.owner_member.om_join.OmJoinFragment
import com.mod_int.carwash.ui.owner_member.om_state.OmOrderStateFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OmActivity : BaseActivity<ActivityOmBinding>(R.layout.activity_om) {

    private val omViewModel by viewModels<OmViewModel>()
    private val tabConfigurationStrategy =
        TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = resources.getStringArray(R.array.array_om)[position]
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

        //뷰페이저로 할경우 0 -> 4으로 이동할경우 중간 프래그먼트가 보이는 오류가 발생 해결방법이 뭐가 있을까요?
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

    /**
     * GPS 권한 결과에 대한 처리.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == OmJoinFragment.REQUEST_FINE_LOCATION_PERMISSIONS_REQUEST_CODE) {

            when {

                /**
                 * GPS 권한 x
                 */
                grantResults.isEmpty() -> {
                    showToast(message = "권한이 없습니다.")
                }

                /**
                 * GPS 권한 o
                 */
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
//                    showToast(message = "권한이 허용되었습니다.")
                    omViewModel.permissionGrant()
                }

                /**
                 * GPS 권한 시스템 실행.
                 */
                else -> {
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts(
                        "package",
                        BuildConfig.APPLICATION_ID,
                        null
                    )
                    intent.data = uri
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
            }
        }
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





