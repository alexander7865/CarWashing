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
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class OmJoinFragment : BaseFragment<FragmentOmJoinBinding>(R.layout.fragment_om_join){
    private val omJoinViewModel by viewModels<OmJoinViewModel>()

    private lateinit var mapView : MapView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun initUi() {
        brandSelect()
        modelSelect()
        mapView = MapView(requireActivity())
        binding.containerMap.addView(mapView)
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

    //스피너 브랜드셀렉
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

    //스피너 모델셀렉
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
                        omJoinViewModel.inputCarModelObservableField.set(selected)
                        showToast(message = "${model[position]}가 선택되었습니다.")
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    view!!.setBackgroundColor(Color.TRANSPARENT)
                }
            }
        }
    }

    private fun enableSetting(isEnable: Boolean) {
        with(binding){
            etCarNum.isEnabled = isEnable
            spCarBrand.isEnabled = isEnable
            spCarModel.isEnabled = isEnable
            tvCarKinds.isEnabled = isEnable
            tvCarSize.isEnabled = isEnable
            etCarCol.isEnabled = isEnable
            carLocation.isEnabled = isEnable
        }
    }
}


