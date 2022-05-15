package com.mod_int.carwash.ui.owner_member.om_join

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.mod_int.carwash.R
import com.mod_int.carwash.base.BaseFragment
import com.mod_int.carwash.databinding.FragmentOmJoinBinding
import com.mod_int.carwash.ext.hasPermission
import com.mod_int.carwash.ext.showToast
import com.mod_int.carwash.ui.owner_member.om_activity.OmViewModel
import com.mod_int.carwash.ui.owner_member.om_activity.OmViewState
import com.mod_int.carwash.util.GpsTracker
import com.mod_int.carwash.util.Result
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class OmJoinFragment : BaseFragment<FragmentOmJoinBinding>(R.layout.fragment_om_join) {
    private val omJoinViewModel by viewModels<OmJoinViewModel>()
    private val omViewModel by activityViewModels<OmViewModel>()

    private lateinit var mapView: MapView
    private lateinit var gpsTracker : GpsTracker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        gpsTracker = GpsTracker(context = requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun initUi() {
        brandSelect()
        modelSelect()
//        mapView = MapView(requireActivity())
//        binding.containerMap.addView(mapView)
//        locationRequest()
    }

    private fun getCurrentLocation() {
        when (val result = gpsTracker.getLocation()) {
            is Result.Success -> {
                result.data.addOnCompleteListener {
                    val location = it.result
                    mapView.setMapCenterPoint(
                        MapPoint.mapPointWithGeoCoord(
                            location.latitude,
                            location.longitude
                        ), true
                    )
                }
                showToast("현재위치 가져오기 성공.")
            }

            is Result.Error -> {
                showToast("현재위치 가져오기 실패.")
            }
        }
    }

    private fun locationRequest() {
        val permissionApproved =
            requireActivity().hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)

        if (permissionApproved) {
            getCurrentLocation()
        } else {
            val provideRationale = shouldShowRequestPermissionRationale(
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
            if (provideRationale) {
                initUi()
            } else {
                requireActivity().requestPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ),
                    REQUEST_FINE_LOCATION_PERMISSIONS_REQUEST_CODE
                )
            }
        }
    }

    companion object {
        const val REQUEST_FINE_LOCATION_PERMISSIONS_REQUEST_CODE = 34
    }


    private fun initViewModel() {
        binding.viewModel = omJoinViewModel
        omJoinViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            (viewState as? OmJoinViewState)?.let {
                onChangedJoinViewState(
                    viewState
                )
            }
        }

        omViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            (viewState as? OmViewState)?.let {
                onChangedOmViewState(
                    viewState
                )
            }
        }
    }

    private fun onChangedJoinViewState(viewState: OmJoinViewState) {
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

    private fun onChangedOmViewState(viewState: OmViewState) {
        when (viewState) {
            is OmViewState.PermissionGrant -> {
                showToast(message = "권한 OK")
                getCurrentLocation()
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
                        omJoinViewModel.inputCarBrand.set(selected)
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
                        omJoinViewModel.inputCarModel.set(selected)
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
        with(binding) {
            etCarNum.isEnabled = isEnable
            spCarBrand.isEnabled = isEnable
            spCarModel.isEnabled = isEnable
            tvCarKinds.isEnabled = isEnable
            tvCarSize.isEnabled = isEnable
            etCarCol.isEnabled = isEnable
            carLocation.isEnabled = isEnable
            etDetailLocation.isEnabled =isEnable
        }
    }
}


