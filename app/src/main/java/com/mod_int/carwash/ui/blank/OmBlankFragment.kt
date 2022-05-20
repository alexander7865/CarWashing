package com.mod_int.carwash.ui.blank

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.base.BaseViewModel
import com.mod_int.carwash.base.ViewState
import com.mod_int.carwash.data.repo.FirebaseRepository
import com.mod_int.carwash.databinding.FragmentOmBlankBinding
import com.mod_int.carwash.ui.owner_member.om_home.OmHomeViewModel
import com.mod_int.carwash.ui.owner_member.om_state.OmOrderStateFragment
import com.mod_int.carwash.ui.owner_member.om_state.OmOrderStateViewState
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@AndroidEntryPoint
class OmBlankFragment : BaseFragment<FragmentOmBlankBinding>(R.layout.fragment_om_blank) {
    private val omBlankViewModel by viewModels<OmBlankViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()

    }


    private fun initUi(){

    }

    private fun initViewModel(){
        binding.viewModel = omBlankViewModel
        omBlankViewModel.viewStateLiveData.observe(viewLifecycleOwner){ viewState->
            (viewState as? OmBlankViewState)
            onChangedPageViewState(viewState)
        }
    }

    private fun onChangedPageViewState(viewState : ViewState){
        when(viewState){
            is OmBlankViewState.RouteOrderState -> {

            }
        }
    }
}



@HiltViewModel
class OmBlankViewModel @Inject constructor(
    app: Application,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel(app) {

    //워셔가 오더의뢰 승인시에 의뢰현황으로 변경되게 코딩을 짜야하는데 어떻게 하면 좋을까요??
    //여기서 오더를 컴펌했다면 그이후 오더현황을 보여주고 싶습니다.


}



sealed class OmBlankViewState : ViewState {
    object RouteOrderState : OmBlankViewState()
}