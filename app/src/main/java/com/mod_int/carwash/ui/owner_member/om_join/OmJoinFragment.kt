package com.mod_int.carwash.ui.owner_member.om_join

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentOmJoinBinding
import com.mod_int.carwash.ext.showToast
import com.mod_int.carwash.ui.owner_member.om_activity.OmActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OmJoinFragment : BaseFragment<FragmentOmJoinBinding>(R.layout.fragment_om_join){
    private val omJoinViewModel by viewModels<OmJoinViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun initUi() {
        brandSelect()
        modelSelect()
    }

    private fun initViewModel(){
        binding.viewModel = omJoinViewModel
        omJoinViewModel.viewStateLiveData.observe(viewLifecycleOwner){ viewState ->
            (viewState as? OmJoinViewState)?.let{
                onChangedJoinViewState(
                    viewState)
            }
        }
    }

    private fun  onChangedJoinViewState(viewState: OmJoinViewState){
        when (viewState) {
            is OmJoinViewState.ErrorMsg -> {
                showToast(message = viewState.message)
            }

            is OmJoinViewState.EnableInput -> {
                enableSetting(viewState.isEnable)
            }

            is OmJoinViewState.BackStep -> {
                requireActivity().onBackPressed()
            }
        }
    }


    //브랜드별 차량 종류를 구현하려 하는데 사실 무식하게 하면 할 수 있을꺼 같은데 더 좋은 방법이 있을까요? ㅋㅋㅋ
    //관리자 페이지 만들어서 새로운 모델 변경된 모델이 나오면 변경해주는 방식으로 진행하려합니다
    //스피너를 뷰모델로 옮기고 싶은데 잘 안되네요
    private fun brandSelect() {
        val brand = resources.getStringArray(R.array.carBrandSelect)
        val brandAdapter = ArrayAdapter(
            requireContext(),
            R.layout.custom_owner_spinner, brand
        )

        with(binding) {
            spCarBrand.adapter = brandAdapter
            spCarBrand.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                @SuppressLint("ResourceAsColor")
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position > 0) {
                        val selected = brand[position]
                        omJoinViewModel.inputCarBrandObservableField.set(selected)
                        showToast(message = "${brand[position]}가 선택되었습니다.")
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    view!!.setBackgroundColor(Color.TRANSPARENT)
                }
            }
        }
    }

    //스피너
    private fun modelSelect() {
        val model = resources.getStringArray(R.array.carModelSelect)
        val modelAdapter = ArrayAdapter(
            requireContext(),
            R.layout.custom_owner_spinner, model
        )

        with(binding) {
            spCarModel.adapter = modelAdapter
            spCarModel.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position > 0) {
                        var selected = model[position]
                        carModel.text = selected
                        showToast(message = "${model[position]}가 선택되었습니다.")
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    view!!.setBackgroundColor(Color.TRANSPARENT)
                }
            }
        }
    }

    //비활성화가 되어야하는데 어떻게 코드를 만들까용?
    private fun enableSetting(isEnable: Boolean) {
        with(binding){
            etCarNum.isEnabled = isEnable
            carModel.isEnabled = isEnable
            tvCarKinds.isEnabled = isEnable
            tvCarSize.isEnabled = isEnable
            etCarCol.isEnabled = isEnable
            carLocation.isEnabled = isEnable
        }
    }
}


