package com.mod_int.carwash.ui.pickup_member.pm_registration

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentPmRegistrationBinding
import com.mod_int.carwash.ext.showToast
import com.mod_int.carwash.ui.washer_member.wm_registration.WmRegistrationViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PmRegistrationFragment : BaseFragment<FragmentPmRegistrationBinding>(
    R.layout.fragment_pm_registration) {
    private val pmRegistrationViewModel by viewModels<PmRegistrationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun initUi(){
        bankSelect()
    }
    private fun initViewModel(){
        binding.viewModel = pmRegistrationViewModel
        pmRegistrationViewModel.viewStateLiveData.observe(viewLifecycleOwner){viewState ->
            (viewState as? PmRegistrationViewState)?.let {
                onChangedPmViewState(viewState)
            }
        }
    }
    private fun onChangedPmViewState(viewState: PmRegistrationViewState){
        when (viewState){
            is PmRegistrationViewState.EnableInput -> {
                enableSetting(viewState.isEnable)
            }
            is PmRegistrationViewState.Msg -> {
                showToast(message = viewState.message)
            }
        }
    }

    private fun enableSetting(isEnable: Boolean) {
        with(binding){
            accountNamePickManager.isEnabled = isEnable
            bankNamePickupManager.isEnabled = isEnable
            accountNumberPickupManager.isEnabled = isEnable
        }
    }

    //뱅크 셀렉 스피너
    private fun bankSelect() {
        val bank = resources.getStringArray(R.array.bankSelect)
        val bankAdapter = ArrayAdapter (requireContext(),
            R.layout.custom_owner_spinner, bank)

        with(binding){
            bankNamePickupManager.adapter = bankAdapter
            bankNamePickupManager.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                @SuppressLint("ResourceAsColor")
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position > 0) {
                        val selected = bank[position]
                        pmRegistrationViewModel.pmBankName.set(selected)
                        showToast(message = "${bank[position]}가 선택되었습니다.")
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    view!!.setBackgroundColor(Color.TRANSPARENT)
                }
            }
        }
    }
}