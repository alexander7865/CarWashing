package com.mod_int.carwash.ui.washer_member.wm_registration

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentWmRegistrationBinding
import com.mod_int.carwash.ext.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WmRegistrationFragment : BaseFragment<FragmentWmRegistrationBinding>(
    R.layout.fragment_wm_registration) {
    private val wmRegistrationViewModel by viewModels<WmRegistrationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun initUi(){
        bankSelect()
        with(binding){
            pickupWashing.setOnCheckedChangeListener(checkBoxListener)
            handWashing.setOnCheckedChangeListener(checkBoxListener)
            tripWashing.setOnCheckedChangeListener(checkBoxListener)
        }
    }

    private fun initViewModel(){
        binding.viewModel = wmRegistrationViewModel
        wmRegistrationViewModel.viewStateLiveData.observe(viewLifecycleOwner){viewState ->
            (viewState as? WmRegistrationViewState)?.let {
                onChangedWmViewState(viewState)
            }
        }
    }

    private fun onChangedWmViewState(viewState: WmRegistrationViewState){
        when (viewState){
            is WmRegistrationViewState.EnableInput -> {
                enableSetting(viewState.isEnable)
            }
            is WmRegistrationViewState.Msg -> {
                showToast(message = viewState.message)
            }
        }
    }

    private fun enableSetting(isEnable: Boolean) {
        with(binding) {
            accountName.isEnabled = isEnable
            bankName.isEnabled = isEnable
            accountNumber.isEnabled = isEnable
            pickupWashing.isEnabled = isEnable
            handWashing.isEnabled = isEnable
            tripWashing.isEnabled = isEnable
        }
    }

    //스피너 구현
    private fun bankSelect() {
        val bank = resources.getStringArray(R.array.bankSelect)
        val bankAdapter = ArrayAdapter (requireContext(),
            R.layout.custom_owner_spinner, bank)

        with(binding){
            bankName.adapter = bankAdapter
            bankName.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                @SuppressLint("ResourceAsColor")
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position > 0) {
                        val selected = bank[position]
                        wmRegistrationViewModel.wmBankName.set(selected)
                        showToast(message = "${bank[position]}가 선택되었습니다.")
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    view!!.setBackgroundColor(Color.TRANSPARENT)
                }
            }
        }
    }

    //체크박스 리스너 구현 체크박스 클릭시 값을 저장해야합니다
    private val checkBoxListener = CompoundButton.OnCheckedChangeListener { checkBtn, isChecked ->
        if (isChecked){
            when(checkBtn.id){
                R.id.pickupWashing -> {}
                R.id.handWashing -> {}
                R.id.tripWashing -> {}
            }
        }else{

        }
    }
}

