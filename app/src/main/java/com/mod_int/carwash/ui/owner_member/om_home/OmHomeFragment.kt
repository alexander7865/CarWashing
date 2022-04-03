package com.mod_int.carwash.ui.owner_member.om_home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
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
        initViewModel()
        initUi()
    }

    private fun initUi() {

    }

    private fun initViewModel() {
        //뷰모델과 바인딩 연결
        binding.viewModel = omHomeViewModel
        omHomeViewModel.homeInfo()
        omHomeViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            (viewState as? OmHomeViewState)?.let {
                onChangedHomeViewState(
                    viewState
                )
            }
        }
    }

    private fun onChangedHomeViewState(viewState: ViewState) {
        when (viewState) {
            is OmHomeViewState.ChangeDate -> {

            }

            is OmHomeViewState.ChangePhoneNr -> {

            }

            is OmHomeViewState.ChangeCarInfo -> {

            }

            is OmHomeViewState.ChangeCarLocation -> {

            }

            is OmHomeViewState.RouteOmJoin -> {
                routeOmJoinFragment()
            }
        }
    }


    private fun routeOmJoinFragment() {
        parentFragmentManager.beginTransaction().add(R.id.container_om_home, OmJoinFragment())
            .addToBackStack("OmJoinFragment")
            .commit()
    }
}






