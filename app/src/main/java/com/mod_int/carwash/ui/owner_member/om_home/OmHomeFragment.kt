package com.mod_int.carwash.ui.owner_member.om_home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.base.ViewState
import com.mod_int.carwash.databinding.FragmentOmHomeBinding
import com.mod_int.carwash.ui.owner_member.om_activity.OmActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OmHomeFragment : BaseFragment<FragmentOmHomeBinding>(R.layout.fragment_om_home) {

    private val omHomeViewModel by viewModels<OmHomeViewModel>()
    private lateinit var ownerActivity: OmActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OmActivity) ownerActivity = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initUi()


        binding.btnJoin.setOnClickListener {
            ownerActivity.joinRegistration()
        }

    }

    private fun initUi(){
        omHomeViewModel.homeInfo()
    }

    private fun initViewModel(){
        //뷰모델과 바인딩 연결
        binding.viewModel = omHomeViewModel
        omHomeViewModel.viewStateLiveData.observe(viewLifecycleOwner){ viewState ->
            (viewState as? OmHomeViewState)?.let{
                onChangedHomeViewState(
                    viewState
                )
            }
        }
    }

    private fun onChangedHomeViewState(viewState: ViewState){
        when(viewState) {
            is OmHomeViewState.ChangeDate -> {

            }

            is OmHomeViewState.ChangePhoneNr -> {

            }

            is OmHomeViewState.ChangeCarInfo -> {

            }

            is OmHomeViewState.ChangeCarLocation -> {

            }
        }
    }
}






