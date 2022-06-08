package com.mod_int.carwash.ui.owner_member.om_home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.base.ViewState
import com.mod_int.carwash.databinding.FragmentOmHomeBinding
import com.mod_int.carwash.ui.owner_member.om_join.OmJoinFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OmHomeFragment : BaseFragment<FragmentOmHomeBinding>(R.layout.fragment_om_home) {
    private val omHomeViewModel by viewModels<OmHomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        omHomeViewModel.omHomeInfo()
        initViewModel()
        initUi()
    }

    private fun initUi(){
    }

    private fun initViewModel(){
        //뷰모델과 바인딩 연결
        binding.viewModel = omHomeViewModel
        omHomeViewModel.viewStateLiveData.observe(viewLifecycleOwner){ viewState ->
            (viewState as? OmHomeViewState)?.let{
                onChangedHomeViewState(viewState)
            }
        }
    }

    private fun onChangedHomeViewState(viewState: ViewState){
        when(viewState) {

            is OmHomeViewState.ChangeInfo -> {
                omHomeViewModel.omHomeInfo()
            }

            is OmHomeViewState.RouteOmJoin -> {
                routeOmJoinFragment()
            }

            is OmHomeViewState.RouteWebViewSuggestOm1 -> {
                routeWebViewSuggestOm1()
            }

            is OmHomeViewState.RouteWebViewSuggestOm2 -> {
                routeWebViewSuggestOm2()
            }
        }
    }

    private fun routeOmJoinFragment() {
        parentFragmentManager.beginTransaction().add(R.id.container_om_home, OmJoinFragment())
            .addToBackStack("OmJoinFragment")
            .commit()
    }

    private fun routeWebViewSuggestOm1() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.9block.co.kr"))
        view?.context?.startActivity(intent)
    }

    private fun routeWebViewSuggestOm2() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://gil.seoul.go.kr/walk/main.jsp"))
        view?.context?.startActivity(intent)
    }
}






